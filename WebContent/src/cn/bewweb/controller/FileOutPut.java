package cn.bewweb.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.bewweb.dao.GoodsMapper;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.mongo.CategoryDao_I;
import cn.bewweb.redis.ProofDao_I;
import cn.bewweb.service.ControlService_I;
import cn.bewweb.service.FileSysService_I;
import cn.bewweb.service.TradeService;
import cn.bewweb.service.TradeService_I;
import cn.bewweb.service.UserService_I;
import cn.bewweb.util.PathTools;

@Controller
@RequestMapping("/file")
public class FileOutPut {
	private static final Logger log = LoggerFactory.getLogger(FileOutPut.class);
	@Autowired
	Environment evn;
	@Autowired
	private FileSysService_I fileSysService;
	@Autowired
	private UserService_I us;
	@Autowired
	private ControlService_I control;
	@Autowired
	private ProofDao_I proofRDDao;
	@Autowired
	private TradeService_I trade;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public void display(HttpServletRequest request, HttpServletResponse response) {
		String filePath = null;
		String name = (String) request.getParameter("name");
		String size = (String) request.getParameter("size");
		String find = (String) request.getParameter("find");
		if (null != find && "up".equals(find)) {
			List<String> flist = fileSysService.find(evn.getProperty("uploads"), name, "1", true, false, true, true);
			if (null == flist || flist.isEmpty()) {
				log.info("没有指定文件");
				return;
			}
			filePath = flist.get(0);
			log.info(filePath);
		} else if (null != find && "userhead".equals(find)) {
			List<String> flist = fileSysService.find(evn.getProperty("userHome"), name, "1", true, true, false, true);
			if (null == flist || flist.isEmpty()) {
				log.info("没有指定文件");
				return;
			}
			filePath = flist.get(0);
			log.info(filePath);
		} else {
			filePath = evn.getProperty("fileDataBase");
			name = (null == name || "".equals(name)) ? "x" : name.trim();
			size = (null == size || "".equals(size)) ? "s" : size.trim();
			filePath = PathTools.getFaceImg(filePath + name, size);
		}
		String split = "/";
		if (filePath.lastIndexOf("\\") > 0) {
			split = "\\";
		}
		String fileName = filePath.substring(filePath.lastIndexOf(split) + 1, filePath.length());
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		
		//response.setHeader("Content-type", "image/*");
		/*
		response.setHeader("Location", "url");
		try {
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			log.info("UnsupportedEncodingException 异常");
			e2.printStackTrace();
		}*/
		//处理长度
		File objFile = new File(filePath);
		long length = objFile.length();
		response.setContentLengthLong(length);
		try {
			inputStream = new FileInputStream(objFile);
			outputStream = response.getOutputStream();
			long totalSize = inputStream.available();
			int bufferSize = 1024;
			if (totalSize > 1024 * 1024 * 10) {// >10M
				bufferSize = 1024 * 8;// 8K
			} else if (totalSize > 1024 * 1024 * 100) {
				bufferSize = 1024 * 64;// 64K
			}
			byte[] buffer = new byte[bufferSize];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
				outputStream.flush();
			}
			outputStream.flush();
		} catch (FileNotFoundException e1) {
			log.error("无此文件:" + filePath);
		} catch (NullPointerException e) {
			log.error("空指针异常");
		} catch (ClientAbortException e) {
			log.error("下载链接请求超时");
		} catch (Exception e) {
			log.error("未知错误");
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(Long goodsId,String path, String name, String point,  HttpServletRequest request,
			HttpServletResponse response) throws IOException, InterruptedException {
		log.info("book download");
		
		//处理下载量 和 下载类型和
		User user = us.getNowUser(request);
		if(user!=null){
			Long userId = user.getUserId();
			control.proofAutoAdd(userId, "downSum", 1l, null);
			if(goodsId!=null){
				Goods goods = trade.getOneGoods(goodsId);
				Long label = 0l ;
				if(goods.getGoodsLabel()!=null){
					label =  Long.valueOf(goods.getGoodsLabel());
					control.proofAutoAdd(userId, "downTypeSum", label , null);
				}else{
					log.warn("没有统计出下载类型");
				}
			}else{
				log.warn("没有统计出下载类型");
			}
		}
		
		String basePath = evn.getProperty("fileDataBase");
		sendOutPutStream(basePath, path,  name,  point,  "down",  request, response);
	}
	
	@RequestMapping(value = "/reader", method = RequestMethod.GET)
	public void reader(String path, String name, String point,  HttpServletRequest request,
			HttpServletResponse response) throws IOException, InterruptedException {
		log.info("book download");
		String basePath = evn.getProperty("fileDataBase");
		sendOutPutStream(basePath, path, name,  point, null,  request, response);
	}
	
	
	/**
	 * 处理流
	 * @param basePath 基础路径
	 * @param path 请求路径
	 * @param name 文件名
	 * @param point 断点处
	 * @param contentType content-Type
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void sendOutPutStream(String basePath,String path, String name, String point, String contentType, HttpServletRequest request,
			HttpServletResponse response) throws IOException, InterruptedException {
		boolean nothing = false;
		if (path == null) {
			path = basePath;
		} else {
			path = basePath + path;
		}
		// 文件名
		name = name == null ? null : name.trim();
		if (name == null) {
			log.info("没有该文件");
			nothing = true;
		}
		// 搜索
		log.info("path&name--"+path+"&"+name);
		List<String> plist = fileSysService.find(path, name, "1", false, true, false, true);
		path = plist == null || plist.size() == 0 ? null : plist.get(0);
		log.info("finded path--"+path);
		if (null == path) {
			log.error("没有该文件");
			nothing = true;
		}
		if (nothing) {
			try {
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.print("<h1>咦？文件给弄丢了！</h1>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		//请求头
		response.setHeader("Accept-Ranges", "bytes");
		//标识时间
		response.setHeader("Date", ""+System.currentTimeMillis());
		//标识MIME
		if ("down".equals(contentType)) {
			try {
				response.setHeader("Location", "url");
				response.setHeader("Content-type", "application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + URLEncoder.encode(fileSysService.getName(path), "UTF-8"));
			} catch (UnsupportedEncodingException e2) {
				log.info("UnsupportedEncodingException 异常");
				e2.printStackTrace();
			}
		}else if("text/html".equals(contentType)){
			response.setHeader("Content-type", "text/html;charset=UTF-8");
		}else if(contentType!=null){
			response.setHeader("Content-type", contentType);
		}else{
			//没有描述,默认无描述头
		}
		
		
		File file = new File(path);
		long length = file.length();
		response.setContentLengthLong(length);
		
		OutputStream out = response.getOutputStream();
		
		
		//动态调整传输大小
		int bufferSize = 1024;
		if (length > 1024 * 1024 * 10) {// >10M
			bufferSize = 1024 * 8;// 8K
		} else if (length > 1024 * 1024 * 100) {
			bufferSize = 1024 * 64;// 64K
		}
		
		
		
		// 首次请求
		if (point == null || "".equals(point.trim())) {
			FileInputStream stream = new FileInputStream(file);
			int count = -1;
			byte[] buffer = new byte[bufferSize];
			while ((count = stream.read(buffer)) != -1) {
				// Thread.sleep(50);
				Thread.sleep(5);
				out.write(buffer, 0, count);
				out.flush();
			}
			stream.close();
			out.close();
		} else {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(Long.valueOf(point));
			int count = -1;
			byte[] buffer = new byte[bufferSize];
			while ((count = raf.read(buffer)) != -1) {
				//Thread.sleep(50);
				Thread.sleep(5);
				out.write(buffer, 0, count);
				out.flush();
			}
			raf.close();
			out.close();
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////
	/////////////测试
	/////////////
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public void play(String name, String point, HttpServletRequest request,
			HttpServletResponse response) throws IOException, InterruptedException {
		String basePath  = evn.getProperty("video");
		boolean nothing = false;
		name = name == null ? null : name.trim();
		if (name == null) {
			log.info("没有该文件");
			nothing = true;
		}
		List<String> plist = fileSysService.find(basePath, name, "1", false, true, false, true);
		basePath = plist == null || plist.size() == 0 ? null : plist.get(0);
		log.info("finded path--"+basePath);
		if (null == basePath) {
			log.error("没有该文件");
			nothing = true;
		}
		
		if (nothing) {
			try {
				PrintWriter out = response.getWriter();
				out.print("<h1>咦？文件给弄丢了！</h1>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		File file = new File(basePath);
		long length = file.length();
		OutputStream out = response.getOutputStream();
		
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Date", ""+System.currentTimeMillis());
		response.setHeader("Content-type", "video/mp4");
		response.setHeader("Content-Length", ""+length);
		response.setHeader("Content-Range",length/2+"-"+length+"/"+length);
		
		//动态调整传输大小
		int bufferSize = 1024;
		if (length > 1024 * 1024 * 10) {// >10M
			bufferSize = 1024 * 8;// 8K
		} else if (length > 1024 * 1024 * 100) {
			bufferSize = 1024 * 64;// 64K
		}
		
		// 首次请求
		if (point == null || "".equals(point.trim())) {
			FileInputStream stream = new FileInputStream(file);
			int count = -1;
			byte[] buffer = new byte[bufferSize];
			while ((count = stream.read(buffer)) != -1) {
				Thread.sleep(2);
				out.write(buffer, 0, count);
				out.flush();
			}
			stream.close();
			out.close();
		} else {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			raf.seek(Long.valueOf(point));
			int count = -1;
			byte[] buffer = new byte[1024 * 8];
			while ((count = raf.read(buffer)) != -1) {
				Thread.sleep(2);
				out.write(buffer, 0, count);
				out.flush();
			}
			raf.close();
			out.close();
		}
	}

}

package cn.bewweb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class FileSysService implements FileSysService_I {
	private static Logger log = LoggerFactory.getLogger(FileSysService.class);

	@Override
	public List<String> find(String basePath, String name, String mode, Boolean isStrict, Boolean isLike,
			Boolean isWapper,Boolean quick) {
		List<String> files = new ArrayList<String>();
		isLike = null == isLike ? false : isLike;
		isStrict = null == isStrict ? false : isStrict;
		isWapper = null == isWapper ? false : isWapper;
		files = find(files, new File(basePath), name, mode, isStrict, isLike, isWapper,quick);
		return files;
	}

	@Override
	public boolean creatPath(String path, String fileName) {
		File file = new File(path);
		boolean result = false;
		if (!file.exists()) {
			if (file.mkdirs()) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = true;
		}
		if (null != fileName) {
			file = new File(path + "/" + fileName);
			try {
				if (file.createNewFile()) {
					result = true;
				} else
					result = false;
			} catch (IOException e) {
				result = false;
				log.error("创建文件失败");
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean delete(String path) {
		long count = 0;
		File file = new File(path);
		count = del(file, count);
		log.info("删除-" + count);
		if (file.exists()) {
			return false;
		}
		return true;
	}

	@Override
	public long getSize(String path) {
		return sumSize(new File(path), 0);
	}

	private long sumSize(File path, long size) {
		if (path.exists()) {
			if (path.isFile()) {
				size += path.length();
			} else {
				File[] files = path.listFiles();
				if (null != files) {
					for (File file : files) {
						size = sumSize(file, size);
					}
				} else {
					log.warn("目录无法访问-" + path.getAbsolutePath());
				}
			}
		} else {
			log.error("无此文件-" + path.getAbsolutePath());
		}
		return size;
	}

	private long del(File file, long count) {
		if (file.exists()) {
			if (file.isFile()&&file.delete()) {
				++count;
			} else {
				File[] files = file.listFiles();
				if (files != null) {
					for (File f : files) {
						count = del(f, count);
					}
				} else {
					log.info("没有访问权限-" + file.getAbsolutePath());
				}
				// 删除目录自己
				file.delete();
				++count;
			}
		}
		return count;
	}

	private List<String> find(List<String> rs, File path, String name, String mode, Boolean isStrict, Boolean isLike,
			Boolean isWapper, Boolean quick) {
		String abPath = path.getAbsolutePath();
		String input = getName(abPath);
		Pattern reg = null;
		Matcher matcher = null;
		if (isStrict) {
			reg = Pattern.compile(name);
			matcher = reg.matcher(input);
		} else {
			if (!isWapper) {
				reg = Pattern.compile(name.toLowerCase());
				matcher = reg.matcher(input.toLowerCase());
			} else {
				name = name.toLowerCase();
				input = input.toLowerCase();
			}
		}
		if (path.exists()) {
			if (path.isFile()) {
				if ("1".equals(mode) || "3".equals(mode)) {
					if (isLike) {// 模糊查询
						if (!isWapper) {
							if (matcher.find()) {
								rs.add(abPath);
							}
						} else {
							if (name.matches(input)) {
								rs.add(abPath);
							}
						}
					} else {// 精确查询
						if (isStrict && input.equals(name)) {
							rs.add(abPath);
						} else if (!isStrict && input.equalsIgnoreCase(name)) {
							rs.add(abPath);
						} else {

						}
					}
				} else {

				}
			} else {// 目录
				if ("2".equals(mode) || "3".equals(mode)) {
					if (isLike) {// 模糊查询
						if (!isWapper) {
							if (matcher.find()) {
								rs.add(abPath);
							}
						} else {
							if (name.matches(input)) {
								rs.add(abPath);
							}
						}
					} else {// 精确查询
						if (isStrict && input.equals(name)) {
							rs.add(abPath);
						} else if (!isStrict && input.equalsIgnoreCase(name)) {
							rs.add(abPath);
						} else {

						}
					}
				}
				if(!rs.isEmpty()&&quick){
					return rs;
				}
				File[] files = path.listFiles();
				if (null != files) {
					for (File file : files) {
						rs = find(rs, file, name, mode, isStrict, isLike, isWapper,quick);
					}
				} else {
				}
			}
		} else {
		}
		return rs;
	}

	@Override
	public String getName(String absolutePath) {
		int p = 0;
		Pattern pa = Pattern.compile("/");
		Matcher ma = pa.matcher(absolutePath);
		if (ma.find()) {
			p = absolutePath.lastIndexOf("/");
		} else {
			p = absolutePath.lastIndexOf("\\");
		}
		++p;
		return absolutePath.substring(p, absolutePath.length());
	}

	@Override
	public boolean copyFile(String src, String dest ,String newName) {
		return copyFile(src,dest,newName,"3");
	}
	
	private boolean copyFile(String src, String dest ,String newName,String mode) {
		mode = null == mode ? "1" : mode;
		InputStream in = null;
		OutputStream out = null;
		File srcfile = new File(src);
		File destfile = new File(dest);
		if(!srcfile.isFile()){
			log.error("原路径中没有文件");
			return false;
		}
		if(!destfile.isDirectory()){
			if(!creatPath(dest, null)){
				log.error("创建目标目录失败");
				return false;
			}
		}
		String originName = getName(src);
		newName = null == newName ? originName : newName;
		if(src.equals(dest+"/"+newName) ||src.equals(dest+"\\"+newName) ){//有相同文件
			if(mode.equals("2")){//避让处理
				return true;
			}else if(mode.equals("3")){//另名处理
				dest = dest+"/"+"copy_"+newName;
			}else{
				dest = dest+"/"+newName;//默认处理
			}
		}else {
			dest = dest+"/"+newName;
		}
		try {
			in = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			log.error("没有找到指定文件");
			return false;
		}
		try {
			out = new FileOutputStream(dest);
		} catch (FileNotFoundException e) {
			log.error("无法找到目的路径");
			return false;
		}
		int curSize =0 ;
		int partSize = 8*1024;
		long length = 0;
		if(srcfile.exists()){
			length = srcfile.length();
			if(length>10*1024*1024){
				partSize = 64*1024;
			}else if(length>100*1024*1024){
				partSize = 128*1024;
			}else if(length>1000*1024*1024){
				partSize = 256*1024;
			}
		}
		long totalSize = 0;
		byte[] buffer =new  byte[partSize];
		try {
			while((curSize=in.read(buffer, 0, partSize))!=-1){
				totalSize += curSize;
				out.write(buffer, 0, curSize);
			}
		} catch (IOException e) {
			log.error("读写错误");
			e.printStackTrace();
		} finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				log.info("流关闭错误");
			} catch (NullPointerException e){
				log.info("没有获取到流对象");
			}
		}
		if(totalSize==length){//完整性检查
			return true;
		}
		return false;
	}

	@Override
	public boolean moveFile(String src, String dest,String newName) {
		if(src.equals(dest+"/"+newName) ||src.equals(dest+"\\"+newName) ){
			return true;
		}
		if(copyFile(src,dest,newName,"1")&&delete(src)){
			return true;
		}
		return false;
	}

	@Override
	public boolean renameFile(String src, String newName) {
		String destDir = null;
		int p = 0;
		if(src.lastIndexOf("/")>=0){
			p = src.lastIndexOf("/");
		}else{
			p = src.lastIndexOf("\\");
		}
		destDir =src.substring(0,p);
		if(src.equals(destDir+"/"+newName) ||src.equals(destDir+"\\"+newName) ){
			return true;
		}
		if(moveFile(src,destDir,newName)){
			return true;
		}
		return false;
	}

	@Override
	public String[] genNewName(Long id, String contentType, String oldName,String newName,String mode) {
		mode = null == mode ?"1":mode;
		String []  path= new String[2];
		String contentFirst = null;
		String contentSecond = null;
		StringBuilder sbuffer = new StringBuilder();
		int pos = 0;
		if(contentType.indexOf("/")>0){
			pos = contentType.indexOf("/");
		}else if(contentType.indexOf("\\")>0){
			pos = contentType.indexOf("\\");
		}else {
			return path;
		}
		contentFirst = contentType.substring(0, pos);
		contentSecond = contentType.substring(pos+1,contentType.length());
		sbuffer.append(id);
		if("1".equals(mode)){
			sbuffer.append("_");
		}else{
			sbuffer.append("/");
		}
		sbuffer.append(contentFirst);
		if("1".equals(mode)){
			sbuffer.append("_");
		}else{
			sbuffer.append("/");
		}
		path[0] = sbuffer.toString();
		sbuffer.delete(0, sbuffer.length());
		//处理文件名
		sbuffer.append(newName);
		sbuffer.append(System.currentTimeMillis());
		if(null==oldName){
			sbuffer.append(".");
			sbuffer.append(contentSecond);
			path[1] = sbuffer.toString();
		}else{//oldName存在
			sbuffer.append("_");
			boolean isHadDot = false;
			if(oldName.lastIndexOf(".")>0){//存在扩展名
				isHadDot = true;
			}
			char[] chars = oldName.toCharArray();
			for (char c : chars) {
				if(isHadDot&&c=='.'){
					sbuffer.append(c);
				}
				if(c<0||(c>='0'&&c<='9')||(c>='A'&&c<='Z')||(c>='a'&&c<='z')||c>127){
					sbuffer.append(c);
				}
			}
			
			if(!isHadDot){
				sbuffer.append(".");
				sbuffer.append(contentSecond);
			}
			path[1] = sbuffer.toString();
		}
		if("1".equals(mode)){
			path[1] = path[0]+path[1];
			path[0] = null;
		}
		return path;
	}

	@Override
	public boolean copy(String src, String dest) {
		File srcfile = new File(src);
		String spilt = "/";
		if(src.indexOf("\\")>0){
			spilt = "\\";
		}
		if(!srcfile.exists()){
			log.warn("源不存在");
			return false;
		}
		if(srcfile.isFile()){
			copyFile(src, dest, null);
		}else{
			File[] files = srcfile.listFiles();
			if(null==files){
				log.info("目录不能访问");
				return false;
			}
			List<File> flist = new LinkedList<File>();
			for (File file2 : files) {
				flist.add(file2);
			}
			Iterator<File> it = flist.iterator();
			File cur = null;
			String curstr = null;
			StringBuilder sbd = new StringBuilder();
			while(it.hasNext()){
				cur = it.next();
				curstr = cur.getAbsolutePath();
				if(cur.isFile()){
					sbd.append(dest);
					sbd.append(spilt);
					sbd.append(curstr.substring(src.length(),curstr.length()-cur.getName().length()-1));
					//System.err.println(sbd.toString());
					copyFile(curstr, sbd.toString(), null);
					sbd.delete(0, sbd.length());
					it.remove();
				}else{
					files = cur.listFiles();
					it.remove();
					if(null==files){
						log.warn("存在目录无法访问");
						continue;
					}
					for (File file : files) {
						flist.add(file);
					}
					it = flist.iterator();
				}
			}
		}
		return true;
	}

	@Override
	public boolean move(String src, String dest) {
		if(copy(src,dest)){
			if(delete(src)){
				return true;
			}
		}
		return false;
	}

	
	
	@Autowired
	Environment env;
	@Override
	public boolean tranToHeads(Long id) {
		List<String> flist = find(env.getProperty("uploads"), ""+id, "1", false, true, false, true);
		if(null == flist || flist.isEmpty())
			return false;
		String compName = flist.get(0);
		String simpleName = null;
		int p =-1;
		if((p=compName.lastIndexOf("."))>0){
			simpleName = id+compName.substring(p,compName.length());
		}else{
			simpleName = "" + id;
		}
		if(!moveFile(flist.get(0), env.getProperty("userHome")+id+"/headPic/",simpleName)){
			return false;
		}
		return true;
	}


	
	
}

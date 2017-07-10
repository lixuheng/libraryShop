package cn.bewweb.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PathTools {
	private static final Logger log = LoggerFactory.getLogger(PathTools.class);
	
	public static String getDownPath(String path){
		path= (null==path||"".equals(path))? "x" : path.trim();
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(path);
		sbuffer.append("/");
		sbuffer.append(path.substring(path.lastIndexOf("/")+1 , path.length()));
		sbuffer.append(".pdf");
		if(!(new File(sbuffer.toString())).exists()){
			sbuffer.replace(sbuffer.length()-4, sbuffer.length(), ".txt");
		}
		return sbuffer.toString();
	}
	
	public static String getFaceImg(String path,String size){
		return getImgPath(path,"face",size,null);
	}
	public static String getIntroImg(String path,Integer n){
		return getImgPath(path,"intro",null,n);
	}
	public static String getImgPath(String path , String way , String size ,Integer n ){
		path= (null==path||"".equals(path))? "x" : path.trim();
		way= (null==way||"".equals(way))? "face" : way.trim();
		size = (null == size || "".equals(size)) ? "s" : size.trim();
		n = (null == n || n==1) ? 1 : n;
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(path);
		sbuffer.append("/img/");
		sbuffer.append(way);
		sbuffer.append("/");
		sbuffer.append(path.substring(path.lastIndexOf("/")+1 , path.length()));
		if("face".equals(way)){
			sbuffer.append("_");
			sbuffer.append(size);
			sbuffer.append(".jpg");
			if(!(new File(sbuffer.toString())).exists()){
				sbuffer.replace(sbuffer.length()-4, sbuffer.length(), ".png");
			}
		}
		 else if("intro".equals(way)){
			sbuffer.append("_");
			sbuffer.append(n);
			sbuffer.append(".jpg");
			if(!(new File(sbuffer.toString())).exists()){
				sbuffer.replace(sbuffer.length()-4, sbuffer.length(), ".png");
			}
		}else{
			log.error("没有指定查询方式");
		}
		
		return sbuffer.toString();
	}
	
}

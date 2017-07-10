package cn.bewweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenKey {
	private static final Logger log = LoggerFactory.getLogger(GenKey.class);
	/**
	 * 生成主键方法
	 * @param 一个类的字节码
	 * @return 生成的主键
	 */
	public static Long genKey(Class clazz){
		Long key = null;
		int t =0;
		char[] names = clazz.getSimpleName().toCharArray();
		for(int i=0;i<6;i++){
			if(i<names.length){
				t+=((int)names[i])*i*10;
			}else{
				t+=t*10;
			}
		}
		t%=1000000;
		key = t*10000000000L+System.currentTimeMillis();
		log.info("key:"+key);
		return key;
	}
	

}

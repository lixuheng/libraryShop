package cn.bewweb.beans;

import java.util.List;

public class Json{
	/**
	 * code :
	 * 0 flase 
	 * 1 true
	 * 2 类型错误
	 * 3  缺少参数
	 * 4  超出范围
	 * 5 语法错误
	 * 100 未知错误
	 */
	private Code code=null;
	private List list=null;
	public Code getCode() {
		return code;
	}
	public Json setCode(Code code) {
		this.code = code;
		return this;
	}
	public List getList() {
		return list;
	}
	public Json setList(List list) {
		this.list = list;
		return this;
	}
	
}

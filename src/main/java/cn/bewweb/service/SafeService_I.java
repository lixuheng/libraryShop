package cn.bewweb.service;

/**
 * 安全服务
 * 
 * @author 孟盛能
 */
public interface SafeService_I {

	/**
	 * 获取随机吗
	 * 
	 * @param scope
	 *            范围，1纯数字，2纯字母，3数字字母特殊符号
	 * @param length
	 *            要生成的位数
	 * @return 随机码
	 */
	String getRandomCode(int scope, int length);

	/**
	 * 获取随机数
	 * 
	 * @param length
	 *            随机数的长度
	 * @param begin
	 *            始区间
	 * @param end
	 *            末区间
	 * @return 随机数
	 */
	int[] randomNUM(int length, int begin, int end);

	
	/**
	 * 处理前端密码
	 * @param hexStr 异或的16进制密文
	 * @param rand 随机码
	 * @return 密码经过DES加密后的摘要，达到与数据库数据处理层次形同的字符串
	 */
	String pretreatClientPwd(String hexStr, String rand);

	/**
	 * DES加密工具
	 * 
	 * @param text
	 * @param key
	 *            密码，长度要是8的倍数
	 * @return 16进制密文串
	 */
	String encrypt(String text, String key);

	/**
	 * DES加密工具
	 * @param datasource
	 * @param password
	 * @return 密文字节
	 */
	byte[] encrypt(byte[] datasource, String password);

	/**
	 * DES解密工具
	 * 
	 * @param text
	 * @param key
	 *            密码，长度要是8的倍数
	 * @return 明文
	 */
	String decrypt(String text, String key);

	/**
	 * DES解密工具
	 * @param src
	 * @param password
	 * @return
	 */
	byte[] decrypt(byte[] src, String password);

	/**
	 * 求摘要算法
	 * 
	 * @param text 需求摘要的文本
	 * @param mode
	 *            可选项：MD5， SHA-1， SHA-256， SHA-384， SHA-512，
	 * @param split
	 *            分隔符 默认是无
	 * @return 文本的16进制字符串表示的摘要
	 */
	String getDigest(String text, String mode, String split);

	/**
	 * 求摘要算法
	 * @param text 需求摘要的文本
	 * @param mode mode
	 *            可选项：MD5， SHA-1， SHA-256， SHA-384， SHA-512，
	 * @return 文本的16进制字符串表示的摘要
	 */
	String getDigest(String text, String mode);

	/**
	 * 获取摘要字节
	 * 
	 * @param text
	 * @param mode
	 * @return 摘要字节数组
	 */
	byte[] getDigestByte(String text, String mode);

	/**
	 * 二进制转16进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @param hasSplit
	 *            是否添加分割符
	 * @return 16进制摘要
	 */
	String bytesToHexString(byte[] b, String split);

	String bytesToHexString(byte[] b);

	/**
	 * 转换十六进制字符串为字节数组
	 * 
	 * @param hexstr
	 *            十六进制字符串
	 * @return 字节数组
	 */
	byte[] hexStringToBytes(String hexstr);

	/**
	 * 求异或运算
	 * 
	 * @param text
	 *            想保持的文本
	 * @param key
	 *            临时的公用的随机数
	 * @return 异或结果
	 */
	String xor(String text, String key);

	byte[] xor(byte[] cp, String rand);

}

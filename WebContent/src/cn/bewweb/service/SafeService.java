package cn.bewweb.service;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SafeService implements SafeService_I {
	private static Logger log = LoggerFactory.getLogger(SafeService.class);
	@Autowired
	private Environment env;
	/*
	 * 获取随机吗 scope 范围，1纯数字，2纯字母，3数字字母特殊符号 length 要生成的位数
	 */
	@Override
	public String getRandomCode(int scope, int length) {
		String rs = null;
		switch (scope) {
		case 1:
			rs = randomCode(length, 48, 57);
			break;
		case 3:
			rs = randomCode(length, 33, 125);
			break;
		case 2:
			char[] big = randomCode(length, 65, 90).toCharArray();
			char[] small = randomCode(length, 97, 122).toCharArray();
			for (int i = 0; i < length; i++) {
				if ((i + 1) % 2 == 0) {
					big[i] = small[i];
				}
			}
			rs = String.copyValueOf(big);
			break;
		default:
			return null;
		}
		return rs;
	}

	private String randomCode(int length, int asciiBegin, int asciiEnd) {
		StringBuffer sbuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sbuffer.append((char) (asciiBegin + Math.random() * (asciiEnd - asciiBegin + 1)));
		}
		return sbuffer.toString();
	}

	@Override
	public int[] randomNUM(int length, int begin, int end) {
		int[] rs = new int[length];
		for (int i = 0; i < length; i++) {
			rs[i] = (int) (begin + Math.random() * (end - begin + 1));
		}
		return rs;
	}

	@Override
	public String encrypt(String text, String key) {
		return bytesToHexString(encrypt(text.getBytes(), key));
	}

	@Override
	public String decrypt(String text, String key) {
		return new String(decrypt(hexStringToBytes(text), key));
	}

	@Override
	public String getDigest(String text, String mode, String split) {
		split = null == split ? "" : split;
		byte[] rs = getDigestByte(text, mode);
		if (rs != null)
			return bytesToHexString(rs, split);
		return null;
	}

	@Override
	public byte[] getDigestByte(String text, String mode) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(mode);
			messageDigest.update(text.getBytes());
			return messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			log.error("不支持求此摘要方式，可选项：MD5 SHA-1 SHA-256 SHA-384 SHA-512 ");
		}
		return null;
	}

	@Override
	public String getDigest(String text, String mode) {
		return getDigest(text, mode, "");
	}

	@Override
	public String xor(String clientPwd, String rand) {
		if (clientPwd == null || rand == null) {
			return null;
		}
		byte[] cp = clientPwd.getBytes();
		byte[] rd = rand.getBytes();
		byte[] rs = new byte[cp.length];
		if (cp.length > rd.length) {
			for (int i = 0, j = 0; i < cp.length; i++) {
				rs[i] = (byte) (cp[i] ^ rd[j]);
				++j;
				if (j == rd.length) {
					j = 0;
				}
			}
		} else {
			for (int i = 0; i < cp.length; i++) {
				rs[i] = (byte) (cp[i] ^ rd[i]);
			}
		}
		return new String(rs);
	}

	@Override
	public byte[] xor(byte[] cp, String rand) {
		if (cp == null || rand == null) {
			return null;
		}
		byte[] rd = rand.getBytes();
		byte[] rs = new byte[cp.length];
		if (cp.length > rd.length) {
			for (int i = 0, j = 0; i < cp.length; i++) {
				rs[i] = (byte) (cp[i] ^ rd[j]);
				++j;
				if (j == rd.length) {
					j = 0;
				}
			}
		} else {
			for (int i = 0; i < cp.length; i++) {
				rs[i] = (byte) (cp[i] ^ rd[i]);
			}
		}
		return rs;
	}
	
	
	@Override
	public String pretreatClientPwd(String hexStr, String rand) {
		if (null == hexStr || null == rand) {
			return null;
		}
		hexStr = hexStr.toUpperCase();
		// 解bytes
		byte[] bytes = hexStringToBytes(hexStr);
		// 解异或的明文密码
		String pwd = xor(new String(bytes), rand);
		// 加密处理
		pwd = encrypt(pwd, env.getProperty("desPwd"));
		//摘要处理
		return getDigest(pwd, env.getProperty("digestMethod"));
	}


	@Override
	public byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			// e.printStackTrace();
			log.info("加密失败");
		}
		return null;
	}

	@Override
	public byte[] decrypt(byte[] src, String password) {
		// DES算法要求有一个可信任的随机数源
		if (src == null) {
			return null;
		}
		SecureRandom random = new SecureRandom();
		byte[] rs = null;
		try {
			// 创建一个DESKeySpec对象
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// 将DESKeySpec对象转换成SecretKey对象
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// 真正开始解密操作
			rs = cipher.doFinal(src);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public String bytesToHexString(byte[] b, String split) {
		StringBuffer sb = new StringBuffer();
		int len = 0;
		if (null == b || null == split) {
			return null;
		}
		for (int n = 0; n < b.length; n++) {
			len = sb.length();
			sb.append((java.lang.Integer.toHexString(b[n] & 0XFF)));
			if (sb.length() - len == 1) {
				sb.insert(sb.length() - 1, "0");
			}
			if ((!"".equals(split)) && (n < b.length - 1)) {
				sb.append(split);
			}
		}
		return sb.toString().toUpperCase();
	}

	public String bytesToHexString(byte[] b) {
		return bytesToHexString(b, "");
	}

	public byte[] hexStringToBytes(String hexstr) {
		String str = "0123456789ABCDEF";
		byte[] bytes = new byte[hexstr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexstr.charAt(2 * i)) * 16;
			n += str.indexOf(hexstr.charAt(2 * i + 1));
			bytes[i] = (byte) (n & 0xff);
		}
		return bytes;
	}

	public static String byte2hex(byte[] b) // 二行制转字符串方式2
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

}

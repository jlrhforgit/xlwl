package com.jlrh.heagle.server.utils;
import java.security.MessageDigest;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码明文加密工具
 * @author Administrator
 *
 */
public class SHA1Util {

	private static Logger logger = LoggerFactory.getLogger("SHA1Util");

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static final int BYTE_LEN = 20;
	/**
	 * 操作员初始密码
	 */
	private static final String DEFAULT_PASSWORD = "000000";

	/** 默认密码长度 */
	private static final int DEFAULT_PASSWORD_LEN = 40;

	/** 重置密码 */
	private static final String RESET_PASSWORD = "000000";

	/**
	 * 获取操作员的初始密码
	 * 
	 * @return
	 */
	public static String getInitPassword() {
		logger.info("========== 获取操作员的初始密码   ===========");
		String result = SHA1.hex_sha1(DEFAULT_PASSWORD);
		result += SHA1Util.encodePWD(result);
		return result;
	}

	/**
	 * 重置操作员密码
	 */
	public static String getResetPassword() {
		String result = SHA1.hex_sha1(RESET_PASSWORD);
		result += SHA1Util.encodePWD(result);
		return result;
	}

	/**
	 * 获取加密密码
	 * 
	 * @param str
	 * @return
	 */
	public static String encodePassword(String password) {
		String result = password.trim();
		result += SHA1Util.encodePWD(result);
		return result;
	}

	/**
	 * 校验密码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkedPassword(String password, String operPIN) {
		boolean result = false;
		String passSha1 = operPIN.substring(0, SHA1Util.DEFAULT_PASSWORD_LEN);
		String pass = operPIN.substring(SHA1Util.DEFAULT_PASSWORD_LEN);
		if (password.equals(passSha1) && SHA1Util.CheckPWD(password, pass)) {
			return true;

		}

		return result;

	}

	private static byte[] generateFixBytes() {
		Random random = new Random();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	public static String encode(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encodePWD(String src) {
		if (src == null || "".equals(src) || src.length() != (2 * BYTE_LEN)) {
			return null;
		}
		byte[] pwdB = hex2Byte(src);
		byte[] generateB = generateFixBytes();
		int len = 10;
		for (int i = 0; i < len; i++) {
			pwdB = hmac(pwdB, generateB);
		}
		String pwdStr = getFormattedText(pwdB);
		String keyStr = getFormattedText(generateB);
		return (pwdStr + keyStr);
	}

	public static boolean CheckPWD(String pwd, String srcPWD) {
		if (pwd == null || "".equals(pwd) || pwd.length() != (2 * BYTE_LEN)) {
			return false;
		}

		if (srcPWD == null || "".equals(srcPWD) || srcPWD.length() != (4 * BYTE_LEN)) {
			return false;
		}

		String keyStr = srcPWD.substring(40, 80);
		String pwdStr = srcPWD.substring(0, 40);
		byte[] keyB = hex2Byte(keyStr);
		byte[] checkPWD = hex2Byte(pwd);
		int len = 10;
		for (int i = 0; i < len; i++) {
			checkPWD = hmac(checkPWD, keyB);
		}
		String result = getFormattedText(checkPWD);

		return result.equals(pwdStr);

	}

	private static byte[] hmac(byte[] checkPWD, byte[] keyB) {

		byte[] k_ipad = new byte[84];
		byte[] k_opad = new byte[84];
		System.arraycopy(keyB, 0, k_ipad, 0, BYTE_LEN);
		System.arraycopy(keyB, 0, k_opad, 0, BYTE_LEN);
		int i = 64;
		do {
			i--;
			k_ipad[i] = (byte) (k_ipad[i] ^ 0x36);
			k_ipad[i] = (byte) (k_opad[i] ^ 0x5C);

		} while (i != 0);

		System.arraycopy(checkPWD, 0, k_ipad, 64, BYTE_LEN);
		byte[] resuult1 = SHA1Util.encode(k_ipad);
		System.arraycopy(resuult1, 0, k_opad, 64, BYTE_LEN);

		return SHA1Util.encode(k_opad);
	}

	private static byte[] encode(byte[] ming) {
		if (ming == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(ming);
			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	private static byte[] hex2Byte(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < BYTE_LEN; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);

		}
		return bytes;

	}

	public static void main(String[] args) {
//		System.out.println();
		String str = SHA1Util.getInitPassword();
		System.out.println(str);
		System.out.println(str.length());
		System.out.println(SHA1.hex_sha1("000000"));
		String ss = "c984aed014aec7623a54f0591da07a85fd4b762d";
		System.out.println(SHA1Util.checkedPassword(ss, str));
	}
}

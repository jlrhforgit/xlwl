package com.jlrh.heagle.server.utils;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 简单的图形验证码生成器
 * 
 * @author Administrator
 *
 */
public class DynamicPasswordBeab {
	private static String keyValue = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
	private static String keyValue2 = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
	private static String numKey = "1234567890";
	private static int seed = 0;

	public static String getPassword(int[] keySerial, String inputPin) {
		if (keySerial == null) {
			return inputPin;
		}
		int len = keySerial.length;
		if (len > 10) {
			StringBuffer buf1 = new StringBuffer();
			StringBuffer buf2 = new StringBuffer();
			for (int i = 0; i < len; i++) {
				buf1.append(keyValue.charAt(i));
				buf2.append(keyValue2.charAt(i));

			}
			StringBuffer targetPin = new StringBuffer();
			for (int i = 0; i < inputPin.length(); i++) {
				char ch = inputPin.charAt(i);
				int idx = keyValue.indexOf(ch);
				if (idx == -1) {
					idx = keyValue2.indexOf(ch);
					targetPin.append(buf2.charAt(idx));
				} else {
					targetPin.append(buf1.charAt(idx));
				}
			}
			return targetPin.toString();
		} else {
			StringBuffer targetPin = new StringBuffer();
			for (int i = 0; i < inputPin.length(); i++) {
				char ch = inputPin.charAt(i);
				int idx = (ch - '0');
				idx = keySerial[idx];
				targetPin.append(numKey.charAt(idx));
			}
			return targetPin.toString();
		}
	}

	/**
	 * 
	 * @param request
	 * @param inPassword
	 * @return
	 * @throws Exception
	 */

	public static String gegPassword(HttpServletRequest request, String inPassword) throws Exception {
		int[] imageOrder;
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new Exception("Session timeout or not established");
		}
		imageOrder = (int[]) session.getAttribute("ctpPassword");
		if (imageOrder == null) {
			throw new Exception("Internal error ,session not initialized!");

		}
		String mimaCode = "";
		for (int i = 0; i < inPassword.length(); i++) {
			int idx = inPassword.charAt(i) - '0';
			mimaCode = mimaCode + String.valueOf(imageOrder[idx]);
		}
		return mimaCode;
	}

	/**
	 * 
	 * @param request
	 * @param inPin
	 * @return
	 * @throws Exception
	 */
	public static boolean isVerifyPinOk(HttpServletRequest request, String inPin) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new Exception("Session timeout or not established");
		}
		String certifyPin = (String) session.getAttribute("ctpCertifyPin");
		if (certifyPin == null) {
			throw new Exception("error request!");
		}
		if (inPin.equals(certifyPin)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param len
	 * @param verifychars
	 * @return
	 */
	public String generateCertifyPin(int len, String verifyChars) {
		Random random = new Random(System.currentTimeMillis() + seed++);
		int charLen = verifyChars.length();
		String value = "";
		for (int i = 0; i < len; i++) {
			int iV = random.nextInt(charLen);
			value = value + verifyChars.charAt(iV);
		}
		return value;
	}

	public int[] generateRadomSerial(int maxValue) {
		if (maxValue < 1) {
			return null;
		}
		int serialValue[] = new int[maxValue];
		Random random = new Random(System.currentTimeMillis() + seed++);
		for (int i = 0; i < maxValue; i++) {
			serialValue[i] = i;

		}
		for (int i = 0; i < maxValue; i++) {
			int idx1 = random.nextInt(maxValue);
			int idx2 = random.nextInt(maxValue);
			int tmp = serialValue[idx1];
			serialValue[idx1] = serialValue[idx2];
			serialValue[idx2] = tmp;
		}
		return serialValue;
	}

}

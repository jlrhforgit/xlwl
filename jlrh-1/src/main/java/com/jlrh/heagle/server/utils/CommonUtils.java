package com.jlrh.heagle.server.utils;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonUtils {
	/**
	 * 获取随机数
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String getRandomCode(String userName, String channel) throws UnsupportedEncodingException {

		Calendar calendar = Calendar.getInstance();

		String time = String.valueOf(calendar.getTimeInMillis());

		String encodeString = userName + channel + time + Math.round(10);

		return DigestUtils.md5Hex(encodeString.getBytes("UTF-8"));
	}

}

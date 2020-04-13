package com.jlrh.heagle.update.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
/**
 * 更新文件配置
 * @author zzw  at 2020/4/9
 *
 */
public class UpdateResource {
	private static HashMap<String, MessageFormat> formats = new HashMap<String, MessageFormat>();
	private static ResourceBundle rsBundle = ResourceBundle.getBundle("com/jlrh/heagle/update/updateResource");

	public static String getMessage(String msgKey, Object[] param) {
		if (param != null && param.length > 0) {
			synchronized (formats) {
				MessageFormat messageFormat = formats.get(msgKey);
				String value = rsBundle.getString(msgKey);
				if (value == null || value.length() <= 0) {
					return value;
				}
				if (messageFormat == null) {
					messageFormat = new MessageFormat(value);
					formats.put(msgKey, messageFormat);
				}
				return messageFormat.format(param);
			}
		} else {
			return getMessage(msgKey);
		}
	}

	public static String getMessage(String msgKey) {
		return rsBundle.getString(msgKey);
	}

	public static String getMessage(long msgKey) {
		return rsBundle.getString(String.valueOf(msgKey));
	}

	public static String getMessage(int msgKey) {
		return rsBundle.getString(String.valueOf(msgKey));
	}

	public static String getMessage(long msgKey, Object[] param) {
		return getMessage(String.valueOf(msgKey), param);
	}

}

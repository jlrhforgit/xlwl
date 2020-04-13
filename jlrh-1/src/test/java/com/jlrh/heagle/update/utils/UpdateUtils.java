package com.jlrh.heagle.update.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author zzw
 * 
 */

public class UpdateUtils {
	/**
	 * 得到系统日期
	 */
	public static String getCurrentDate() {
		return null;
	}

	/**
	 * 得到需要更新的工程
	 */
	public static List<String> getUpdateProjects() {
		List<String> projects = new ArrayList<>();
		projects.add(ProjectConstants.MARKET_COMMON);
		projects.add(ProjectConstants.MARKET_DOMAIN);
		projects.add(ProjectConstants.MARKET_TRUST);
		projects.add(ProjectConstants.MARKET_STATIC);
		projects.add(ProjectConstants.MARKET_RESOURCE);
		return projects;
	}

	/**
	 * 获取文件名
	 */
	public static List<String> getFileProjects() {
		List<String> projects = new ArrayList<>();
		projects.add(ProjectConstants.MARKET_COMMON);
		projects.add(ProjectConstants.MARKET_DOMAIN);
		projects.add(ProjectConstants.MARKET_TRUST);
		projects.add(ProjectConstants.MARKET_STATIC);
		projects.add(ProjectConstants.MARKET_RESOURCE);
		return projects;
	}

	public static void main(String[] args) {
		Random rd = new Random();
		int current = rd.nextInt();
		System.out.println(current);
	}

}

package com.jlrh.heagle.update.utils;

import com.alibaba.druid.util.StringUtils;
import com.jlrh.heagle.utils.DateUtils;

public interface ProjectConstants {

	public static final String WORK_BASE = System.getProperty("user.dir").substring(0,
			System.getProperty("user.dir").lastIndexOf("\\"));
	public static final String PROJECT_DOC = "heagle_doc";
	public static final String PROJECT_DOMAIN = "marketDomain";
	public static final String PROJECT_COMMON = "market_common";
	public static final String PROJECT_TRUST = "jlrh-1";
	public static final String PROJECT_STATIC = "marketStatic";
	public static final String PROJECT_MARKET_RESOURCE = "market_resource";

	// 模板目录名
	public static final String UPDATE_TEMPLATE_DIR = "1template";
	// 更新脚本
	public static final String UPDATE_SCRIPT_FILE = (StringUtils
			.isEmpty(UpdateResource.getMessage(ProjectKeyConstants.UPDATE_SCRIPT_FILE))) ? "更新脚本.txt"
					: UpdateResource.getMessage(ProjectKeyConstants.UPDATE_SCRIPT_FILE);
	// 更新日期
	public static final String UPDATE_DATE = (StringUtils
			.isEmpty(UpdateResource.getMessage(ProjectKeyConstants.UPDATE_CURRCT_DATE_KEY)))
					? DateUtils.getCurrentTimeStr()
					: UpdateResource.getMessage(ProjectKeyConstants.UPDATE_CURRCT_DATE_KEY);
	// 基础目录
	public static final String UPDATE_BASE_DIR = WORK_BASE + ProjectConstants.FILE_SEPRATOR + PROJECT_DOC
			+ ProjectConstants.FILE_SEPRATOR + "update" + ProjectConstants.FILE_SEPRATOR;
	// 更新目录
	public static final String UPDATE_FILE_DIR = StringUtils
			.isEmpty(UpdateResource.getMessage(ProjectKeyConstants.UPDATE_SOURCE_DIR_KEY)) ? UPDATE_BASE_DIR
					: UpdateResource.getMessage(ProjectKeyConstants.UPDATE_SOURCE_DIR_KEY);
	// 更新脚本目录
	public static final String UPDATE_SCRIPT_PATH = UpdateResource.getMessage(
			ProjectKeyConstants.TOTAL_UPDATE_SCRIPT_FILE) + ProjectConstants.FILE_SEPRATOR + UPDATE_SCRIPT_FILE;
	// 当前更新脚本目录
	public static final String CURRENT_UPDATE_FILE_DIR = UPDATE_FILE_DIR + "2020/" + UPDATE_DATE
			+ ProjectConstants.FILE_SEPRATOR;
	// 模板更新目录
	public static final String UPDATE_TEMPLATE_FILE_DIR = UPDATE_FILE_DIR + UPDATE_FILE_DIR + UPDATE_TEMPLATE_DIR
			+ ProjectConstants.FILE_SEPRATOR;
	// 打包目录
	public static final String UPDATE_TARGET_DIR = UpdateResource.getMessage(ProjectKeyConstants.UPDATE_TARGET_DIR_KEY)
			+ UPDATE_DATE + ProjectConstants.FILE_SEPRATOR;
	// 更新jar目录
	public static final String UPDATE_JAR_DIR = UpdateResource.getMessage(ProjectKeyConstants.UPDATE_JAR_DIR)
			+ ProjectConstants.FILE_SEPRATOR;

	public static final String FILE_SEPRATOR = "/";

	public static final String NEW_LINE = "\n";

	public static final String EQUE_LINE = "=";

	/**
	 * 待更新的工程项目
	 */
	public static final String MARKET_RESOURCE = "[market-resource]";
	public static final String MARKET_DOMAIN = "[market-domain]";
	public static final String MARKET_COMMON = "[market-common]";
	public static final String MARKET_TRUST = "[jlrh-1]";
	public static final String MARKET_STATIC = "[market-static]";

}

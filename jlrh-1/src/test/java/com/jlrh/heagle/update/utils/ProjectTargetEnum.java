package com.jlrh.heagle.update.utils;

public enum ProjectTargetEnum {
	MARKET_COMMON("market-common" ,getTargetPath(ProjectConstants.PROJECT_COMMON,"target" + ProjectConstants.FILE_SEPRATOR+"classes")),
	MARKET_DOMAIN("market-domain" , getTargetPath(ProjectConstants.PROJECT_DOMAIN,"target" + ProjectConstants.FILE_SEPRATOR+"classes")),
	MARKET_TRUST("jlrh-1" , getTargetPath(ProjectConstants.PROJECT_TRUST,"target" + ProjectConstants.FILE_SEPRATOR+"classes")),
	MARKET_STATIC("market-static" , getTargetPath(ProjectConstants.PROJECT_STATIC,"WebContent")),
	MARKET_RESOURCE("market-resource" , getTargetPath(ProjectConstants.PROJECT_TRUST,"target"+ProjectConstants.FILE_SEPRATOR+"classes"));
	

	private ProjectTargetEnum(String code,String name) {
		this.code=code;
		this.name=name;
		
	}
	private static String getTargetPath(String project,String path) {
		if ("heagle_market".equals(project)) {
			return "D:\\WORKSPACE" + ProjectConstants.FILE_SEPRATOR+project+ProjectConstants.FILE_SEPRATOR+path+ProjectConstants.FILE_SEPRATOR;
		}
		return ProjectConstants.WORK_BASE+ ProjectConstants.FILE_SEPRATOR+project+ProjectConstants.FILE_SEPRATOR+path+ProjectConstants.FILE_SEPRATOR;
 
	}
	
	private final String code;
	private final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	
	
	public static ProjectTargetEnum getByCode(String code) {
		for(ProjectTargetEnum projectEnum: values()) {
			if (projectEnum.getCode().equals(code)) {
				return  projectEnum; 
			}
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(ProjectTargetEnum.getByCode("heagle_static").getName());
	}

}

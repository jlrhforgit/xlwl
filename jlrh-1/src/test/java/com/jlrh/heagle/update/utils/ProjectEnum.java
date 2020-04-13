package com.jlrh.heagle.update.utils;
/**
 * 项目枚举类
 * @author Administrator
 *
 */
public enum ProjectEnum {
	MARKET_COMMON("[market-common]" , "market-commmon"),
	MARKET_DOMAIN("[market-domain]" , "market-domain"),
	MARKET_TRUST("[jlrh-1]" , "jlrh-1"),
	MARKET_STATIC("[market-static]" , "market-static"),
	MARKET_RESOURCE("[market-resource]" , "market-resource");
	
	private ProjectEnum(String code,String name) {
		this.code=code;
		this.name=name;
		
	}
	
	private final String code;
	private final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	
	public static ProjectEnum getByCode(String code) {
		for (ProjectEnum projectEnum: values()) {
			if (projectEnum.getCode().equals(code)) {
				return projectEnum;
			}
			
			
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(ProjectEnum.getByCode("[heagle-static]").getName());
	}

}

package com.jlrh.heagle.update;



import com.jlrh.heagle.update.utils.ProjectConstants;
import com.jlrh.heagle.update.utils.ProjectKeyConstants;
import com.jlrh.heagle.update.utils.UpdateResource;

/**
 * 
 * @author zzw 
 *
 */
public class MainUpdate {
	public static void main(String[] args) throws Exception {
		//根据template 创建当天必须更新的文档，如：更新脚本，更新功能文档；
		UpdateFileReader.initDiretory();
		
		//创建项目基础目录结构
		UpdateFileReader.creatBaseProject();
		
		//复制class文件
		if ("true".equals(UpdateResource.getMessage(ProjectKeyConstants.AUTO_COPOY_CLASS))) {
			UpdateFileReader.copyClassFromProject();
			
		}
		//更新文件检查，通过生成更新包
		if (UpdateFileReader.checkUpdateFiles(true)) {
			UpdateFileReader.getUpdatePackages();
		}else {
			System.out.println("***********更新清单检查未通过**********");
		}
//		、更新jar包 
		UpdateFileReader.updateJar();
		//打开文件夹
		String targetDoc = ProjectConstants.UPDATE_TARGET_DIR;
		Runtime.getRuntime().exec("cmd.exe /c start "+ targetDoc);
		
		
	}

}

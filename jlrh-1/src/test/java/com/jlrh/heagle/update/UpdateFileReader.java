package com.jlrh.heagle.update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.alibaba.druid.util.StringUtils;
import com.jlrh.heagle.update.utils.ProjectConstants;
import com.jlrh.heagle.update.utils.ProjectEnum;
import com.jlrh.heagle.update.utils.ProjectTargetEnum;
import com.jlrh.heagle.update.utils.UpdateUtils;
import com.jlrh.heagle.utils.DateUtils;

import javassist.ClassPath;

public class UpdateFileReader {

	public static void initDiretory() throws IOException {
		FileUtils.deleteDirectory(new File(ProjectConstants.UPDATE_TARGET_DIR));
		FileUtils.deleteDirectory(new File(ProjectConstants.CURRENT_UPDATE_FILE_DIR));

	}

	/**
	 * 删除文件夹及其以下的所有文件
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean srccess = deleteDir(new File(dir, children[i]));
				if (!srccess) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 根据template创建，当天必须的更新文档，如：更新脚本，更新功能点脚本、
	 */
	public static void createUpdateTemplate() throws IOException {
		String srcFile = ProjectConstants.UPDATE_TEMPLATE_FILE_DIR.replace("/", "\\");
		String targetDoc = ProjectConstants.CURRENT_UPDATE_FILE_DIR.replace("/", "\\");
		/**
		 * 先判断目标文件夹下，是否已包含模板中的文件，包含，不覆盖，否则覆盖； D:
		 */
		File projectFile = new File(srcFile);
		File[] files = projectFile.listFiles();
		for (File file : files) {
			File targetFile = new File(targetDoc + file.getName());
			if (!targetFile.exists()) {
				// 执行shell命令
				String command = "cmd /c xcopy " + srcFile + file.getName() + " " + targetDoc + " " + "/s /Y";
				Process process = Runtime.getRuntime().exec(command);

			}
		}
		System.out.println("模板问价创建完成");

	}

	/**
	 * 得到项目的list列表
	 */
	public static Map<String, List<String>> getProjectListMap() throws Exception {
		String line = null;
		Map<String, List<String>> projectMap = new HashMap<String, List<String>>();
		List<String> projects = UpdateUtils.getUpdateProjects();
		File file = new File(ProjectConstants.UPDATE_SCRIPT_PATH);
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String project : projects) {
			List<String> list = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (line.startsWith("[") && line.endsWith("]")) {
					System.out.println("line:" + line);
					if (line.equals(project)) {
						continue;
					} else {
						break;
					}
				}
				if (null != line && !"".equals(line)) {
					list.add(line);
				}
				if (null != list && list.size() > 0) {
					projectMap.put(ProjectEnum.getByCode(project).getName(), list);
				}
			}

		}
		return projectMap;
	}

	/**
	 * 根据提交上来的文档，获取更新包
	 */
	public static void getUpdatePackages() throws Exception {
		String targetDoc = ProjectConstants.UPDATE_TARGET_DIR;
		String srcFile = ProjectConstants.CURRENT_UPDATE_FILE_DIR;
		File projectFile = new File(srcFile);
		File[] files = projectFile.listFiles();
		String command = "";
		// 清空文件夹内容
		File targetDocFile = new File(targetDoc);
		if (targetDocFile.exists()) {
			deleteDir(targetDocFile);
		}

		for (File file : files) {
			srcFile = srcFile.replace("/", "\\");
			targetDoc = targetDoc.replace("/", "\\");
			// 执行shell命令
			command = "cmd /c  xcopy \"" + srcFile + file.getName() + "\" \"" + targetDoc + "\" " + "/s /Y";
			exec(command);

		}
		System.out.println("拷贝项目class文件值集中目录执行完成，请检查");
		System.out.println("-------------------------------------\n");

	}
	
	
	
	
	/**
	 * 创建项目文件夹结构
	 */
	
	public static void creatBaseProject()throws Exception{
		
		String targetPath =ProjectConstants.CURRENT_UPDATE_FILE_DIR;
		Map<String ,List<String>> projectMapList = getProjectListMap();
		
		
		//创建待更新的文件夹结构‘
		for(String project:projectMapList.keySet()) {
			List<String > fileList = projectMapList.get(project);
			//先创建项目文件夹
			String projectPath = targetPath+project;
			File projectFile = new File(projectPath);
			if (!projectFile.exists()) {
				projectFile.mkdirs();
			}
			//创建项目待更新文件结构
			for(String filePath : fileList) {
				filePath= filePath.trim();
				if (StringUtils.isEmpty(filePath)) {
					continue;
					
				}
				filePath = filePath.replace("\\", "/");
				
				filePath= filePath.substring(0,filePath.lastIndexOf("/"));
				File file = new File(targetPath+project+"/"+filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				
			}
			
		}
		
		System.out.println("项目基础结构创建完成！");
		
	}
	
	
	/**
	 * 拷贝项目-class
	 */
	public static void copyClassFromProject() throws Exception{
		
		String targetPath = ProjectConstants.CURRENT_UPDATE_FILE_DIR;
		
		Map<String ,List<String>> projectMapList = getProjectListMap();
		String command ="";
		//创建得更新文件夹结构
		for(String project: projectMapList.keySet()) {
			List<String > fileList = projectMapList.get(project);
			String classPath = ProjectTargetEnum.getByCode(project).getName();
			//创建项目待更新文件结构
			for(String filePath: fileList) {
				filePath = filePath.trim();
				if (StringUtils.isEmpty(filePath)) {
					continue;
				}
				String sourceDir = classPath + filePath;
				String dirFilePath = filePath.substring(0,filePath.lastIndexOf("/"));
				String targetDir= (targetPath+project + "/"+dirFilePath+"/");
				sourceDir= sourceDir.replace("/","\\");
				targetDir= targetDir.replace("/","\\");
				
				command = "cmd /c  xcopy \"" + sourceDir + "\" \"" + targetDir + "\" " + "/s /Y";
				exec(command);
				//复制内部类
				if (sourceDir.matches(".*\\\\[A-Za-z0-9_]*\\.class$")) {
					String dir =sourceDir.substring(0,sourceDir.lastIndexOf("\\"));
					String fileName = sourceDir.substring(sourceDir.lastIndexOf("\\")+1,sourceDir.lastIndexOf("."));
					File[] list= new File(dir).listFiles();
					for(File file:list) {
						String innerClass = file.getAbsolutePath().replace("/", "\\");
						//如果是内部类
						if (innerClass.matches(".*\\\\"+ fileName+"\\$[A-Za-z0-9_]*\\.class$" )) {
							command = "cmd /c  xcopy \"" + innerClass + "\" \"" + targetDir + "\" " + "/s /Y";
							exec(command);

						}
					}
					
				}
			}
			System.out.println("拷贝项目："+project+ "编译好的class文件完毕！");
			System.out.println("-------------------------------------------\n");
		}
		
		
	}
	
	public static void  updateJar() throws Exception{
		//获取要更新的项目
		final Map<String ,List<String >> projectMapList = getProjectListMap()
				;
		final Map<String ,String> projects = new HashMap<String ,String >();
		File jarDir = new File(ProjectConstants.UPDATE_JAR_DIR);
		jarDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
			for (String project: projectMapList.keySet()) {
				if (name.startsWith(project)) {
					projects.put(project, name);
					return true;
				}
			}
				return false;
			}
		});
		
		
		//更新jar包
		File tmp = new File(ProjectConstants.UPDATE_JAR_DIR+"tmp/");
		if (!tmp.exists()) {
			tmp.mkdir();
			
		}
		for(String project: projects.keySet()) {
			String today = DateUtils.getCurrentTime();
			String fileName = projects.get(project).replace("/", "\\");
			String jarPath =(ProjectConstants.UPDATE_JAR_DIR + fileName).replace("/", "\\");
			String tempPath =(ProjectConstants.UPDATE_JAR_DIR +"tmp/"+ fileName).replace("/", "\\");
			String classesPath =(ProjectConstants.CURRENT_UPDATE_FILE_DIR + project).replace("/", "\\");
			if (!new File(jarPath).exists()) {
				System.out.println("jar包"+jarPath+"不存在");
				continue;
			}
			
			
			System.out.println();
			//复制jar文件
			exec("cmd /c copy \""+ jarPath + "\" \""+tempPath+"\" /Y");
			String command = "jar uf \"" +tempPath+"\" -C \""+ classesPath +"\" /";
			System.out.println("执行"+command+"进行中。。。");
			String logPath = tmp.getPath()+"\\"+project+"-"+today+".log";
			FileWriter out = new FileWriter(logPath);
			try {
				Process process = Runtime.getRuntime().exec(command);
				if (process!=null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
					String msg=null;
					while((msg=bufferedReader.readLine())!= null) {
						System.out.println(msg);
						if (msg.contains(".class")) {
							String log= msg.substring(0,msg.indexOf(".class")+6);
							out.write(log+"\r\n");
							
						}
					}
					bufferedReader.close();
					//正常结束
					if (process.waitFor()==0) {
						process.destroy();
						
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				
			}finally {
				out.close();
			}
			//移动文件
			String jarTargetPath = classesPath+"\\" + (fileName.substring(0,fileName.lastIndexOf('-')+1)+today.replace("-", ""))+".jar";
			exec("cmd /c copy \""+ tempPath+"\" \""+ jarTargetPath+"\" /Y");
			String logTargetPath = classesPath+"\\"+ project+ "-" +today +".log";
			exec("cmd /c copy \"" + logPath+"\" \""+ logTargetPath+"\" /Y");
			
		}
		FileUtils.deleteDirectory(tmp);
		System.out.println("jar包更新完成！");
		System.out.println("-----------------------------------------------\n");
		
		
	}
	
	
	public static void exec(String command) {
		System.out.println("执行 "+ command + "进行中。。。");
		try {
			Process process = Runtime.getRuntime().exec(command);
			if (process!=null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while(bufferedReader.readLine()!= null);
				bufferedReader.close();
				process.waitFor();
				if (process.waitFor()==0) {
					process.destroy();
					
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			System.err.println("执行command："+command+"失败！");
		}
		
	}
	
	
	/**
	 * 校验目录下的文件与文件中提交上来的文件
	 */
	public static boolean checkUpdateFiles(boolean isgenertorPackage) throws Exception{
		int javaCount =0;
		int viewCount =0;
		int jscssCount =0;
		int errmsg =0;
		int mybatis =0;
		int otherConfig =0;
		String targetPath = ProjectConstants.CURRENT_UPDATE_FILE_DIR;
		Map<String ,List<String >> projectMapList = getProjectListMap();
		List<String > errorList = new ArrayList<>();
		//创建待更新的文件夹结构
		for(String project: projectMapList.keySet()) {
			List<String > fileList = projectMapList.get(project);
			for(String filePath : fileList) {
				filePath = filePath.replace("\\","/");
				File file = new File(targetPath+ project +ProjectConstants.FILE_SEPRATOR+ filePath);
				if (!file.exists()) {
					String errorMsg = "[-----更新清单文件："+filePath+"未提交，请检查";
					errorList.add(errorMsg);
				}
				if (project.equals("market-static")) {
					if (filePath.startsWith("static")) {
						jscssCount++;
					}else if(filePath.startsWith("views")){
						viewCount++;
					}else {
						System.out.println("[---ERROR:"+filePath);
					}
				}else if (project.equals("market-resource")) {
					if (filePath.startsWith("errmsg")) {
						errmsg++;
					}else if(filePath.startsWith("mybatis")){
						mybatis++;
					}else {
						otherConfig++;
					}
				}else {
					javaCount++;
				}
			}
			
		}
		System.out.println("[-----总共更新： java 文件："+javaCount+"]");
		System.out.println("[-----总共更新： js css 文件："+jscssCount+"]");
		System.out.println("[-----总共更新： vm 文件："+viewCount+"]");
		if (errmsg>0 ) {
			System.out.println("[-----总共更新： errmsg 文件："+errmsg+"]");
		}
		if (mybatis>0 ) {
			System.out.println("[-----总共更新： mybatis 文件："+mybatis+"]");
		}
		if (otherConfig>0 ) {
			System.out.println("[-----总共更新： otherConfig 文件："+otherConfig+"]");
		}
		for(String errorMsg: errorList) {
			System.out.println(errorMsg);
		}
		System.out.println("---------------------------/n");
		if(isgenertorPackage) {
			return true;
			
		}
		if(errorList.size()>0 ) {
			
			return false;
		}
		return true;
	}
	
	public static void main (String args[]) {
		String art ="1764|1765|";
		String[] tt =art.split("\\|");
		System.out.println(tt.length);
	}
}

package com.jlrh.heagle.server.utils;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.FileOutputStream ;
import java.util.Properties ;

import org.slf4j.Logger ;
import org.slf4j.LoggerFactory ;
import org.springframework.context.annotation.PropertySource ;

import com.jcraft.jsch.Channel ;
import com.jcraft.jsch.ChannelSftp ;
import com.jcraft.jsch.JSch ;
import com.jcraft.jsch.JSchException ;
import com.jcraft.jsch.Session ;
import com.jcraft.jsch.SftpException ;

@PropertySource("classpath:config/application.properties")
public class FTPUtils {
	
	private Logger	logger		= LoggerFactory.getLogger(getClass()) ;
	
	private String	host		= "192.68.70.197" ;
	
	private String	port		= "22" ;
	
	private String	username	= "bta50" ;
	
	private String	password	= "bta50" ;
	
	private String	outpath		= "home/bta50/getproduct" ;
	
	
	public boolean download(String saveFile) {
		boolean flag = false ;
		// String todarStr = "2019-09-23" ;
		String fileName = "adfssdf.txt" ;
		ChannelSftp sftp = null ;
		Channel channel = null ;
		Session sshSession = null ;
		Integer intPort = new Integer(port) ;
		try {
			JSch jsch = new JSch() ;
			sshSession = jsch.getSession(fileName, host, intPort) ;
			sshSession.setPassword(password) ;
			Properties sshConfig = new Properties() ;
			sshConfig.put("StrictHostKeyChecking", "no") ;
			sshSession.setConfig(sshConfig) ;
			sshSession.connect() ;
			logger.info("sshSession 连接成功") ;
			channel = sshSession.openChannel("sftp") ;
			channel.connect() ;
			logger.info("channel 连接成功") ;
			sftp = (ChannelSftp) channel ;
			if (outpath != null && "".equals(outpath)) {
				sftp.cd(outpath) ;
				logger.info("cd进入：" + outpath) ;
				File file = new File(saveFile) ;
				logger.info("saveFile" + saveFile) ;
				sftp.get(fileName, new FileOutputStream(file)) ;
				logger.info("file is download successful", fileName) ;
				flag = true ;
			} else {
				logger.info("服务器路径不存在 。。。") ;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
		} finally {
			closeChannel(sftp) ;
			closeChannel(channel) ;
			closeSession(sshSession) ;
		}
		return flag ;
	}
	
	
	private boolean listFileNames(String host, int port, String username, String password, String dir, String fileName, String outFileName) {
		boolean flag = false ;
		ChannelSftp sftp = null ;
		Channel channel = null ;
		Session sshSession = null ;
		try {
			JSch jsch = new JSch() ;
			// jsch.getSession(username, host, port) ;
			sshSession = jsch.getSession(username, host, port) ;
			sshSession.setPassword(password) ;
			Properties sshConfig = new Properties() ;
			sshConfig.put("StrictHostKeyChecking", "no") ;
			sshConfig.put("PreferredAuthentications", "publickey,keyboard-interactive,password") ;
			sshSession.setConfig(sshConfig) ;
			sshSession.connect() ;
			System.out.println("连接成功") ;
			channel = sshSession.openChannel("sftp") ;
			channel.connect() ;
			sftp = (ChannelSftp) channel ;
			// Vector vector = sftp.ls("/home/docker/opt/pcre-8.40") ;
			// for (Object item : vector) {
			// ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) item ;
			// System.out.println(entry.getFilename()) ;
			// }
			sftp.cd(dir) ;
			sftp.put(new FileInputStream(new File(fileName)), outFileName) ;
			flag = true ;

		} catch (JSchException e) {
			e.printStackTrace() ;
		} catch (SftpException e) {
			e.printStackTrace() ;
		} catch (FileNotFoundException e) {
			e.printStackTrace() ;
		} finally {
			closeChannel(sftp) ;
			closeChannel(channel) ;
			closeSession(sshSession) ;
		}
		return flag ;
	}
	
	
	private static void closeChannel(Channel channel) {
		if (channel != null) {
			if (channel.isConnected()) {
				channel.disconnect() ;
			}
		}
	}
	
	
	private static void closeSession(Session session) {
	        if (session != null) {
	            if (session.isConnected()) {
	                session.disconnect();
	            }
	        }
	}
	
	
	public boolean uploadFiles(String dir, String fileName, String outFileName) {
		Integer intPort = new Integer(port) ;
		logger.info("文件路径：" + outpath) ;
		logger.info("本地文件名：" + fileName) ;
		logger.info("上传文件名：" + outFileName) ;
		return listFileNames(host, intPort, username, password, outpath, fileName, outFileName) ;
	}
}

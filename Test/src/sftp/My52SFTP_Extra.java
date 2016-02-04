package sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author YangHua 转载请注明出处：http://www.xfok.net/2009/10/124485.html
 */
public class My52SFTP_Extra {

	private static Logger logger = Logger.getLogger(My52SFTP_Extra.class);
	
	public static void main(String[] args) {
		Utils.initLog4j();
		My52SFTP_Extra sf = new My52SFTP_Extra();
		ChannelSftp sftp = null;
		try {
			String host = "192.168.251.52";
			int port = 22;
			String username = "root";
			String password = "DX-game189.cn";
			sftp = sf.connect(host, port, username, password);
			
			String extraDir = "/opt/app/egame/egame.third.open.biz/lib/";
			sf.upload(extraDir, "E:\\svn\\code\\lib\\ref\\egame.client.jar", sftp);
			sf.upload(extraDir, "E:\\svn\\code\\lib\\ref\\egame.interfaces.jar", sftp);
			sf.upload(extraDir, "E:\\svn\\code\\lib\\ref\\egame.third.open.biz.jar", sftp);
			logger.info("定时任务目录下jar包替换成功");
			
			
//			String messageCenterDir = "/opt/app/egame/mobile/egame.message.center/lib/";
//			sf.upload(messageCenterDir, "E:\\svn\\code\\lib\\ref\\egame.interfaces.jar", sftp);
//			sf.upload(messageCenterDir, "E:\\svn\\code\\lib\\ref\\egame.message.center.jar", sftp);
//			logger.info("消息中心目录下jar包替换成功");
//			
//			String clockDir = "/opt/app/egame/egame.mobile.extraction/lib/";
//			sf.upload(clockDir, "E:\\svn\\code\\lib\\ref\\egame.client.jar", sftp);
//			sf.upload(clockDir, "E:\\svn\\code\\lib\\ref\\egame.mobile.extraction.jar", sftp);
//			sf.upload(clockDir, "E:\\svn\\code\\lib\\ref\\egame.interfaces.jar", sftp);
//			logger.info("定时任务目录下jar包替换成功");
			
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(sftp!=null){
				sftp.disconnect();
			}
			System.exit(0);
		}
		
//		sf.download(directory, downloadFile, saveFile, sftp);
//		sf.delete(directory, deleteFile, sftp);
//		try {
//			sftp.cd(directory);
//			sftp.mkdir("ss");
//			System.out.println("finished");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {

		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

}
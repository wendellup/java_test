package cn.egame.common.efs.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.efs.FileSystemBase;
import cn.egame.common.efs.IFileSystem;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 
 * Description 写入文件到NFS文件系统对应的类
 * 
 * @ClassName NFSFileSystem
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author yuchao
 * 
 * @Create Date 2013-7-19
 * 
 * @Modified by none
 * 
 * @Modified Date
 */
public class NFSFileSystem extends FileSystemBase implements IFileSystem {
	Session session = null;
	ChannelSftp channel = null;
	private static NFSFileSystem instance = null;
	private static byte[] SyncRoot = new byte[1];

	private static final Logger log = Logger.getLogger(NFSFileSystem.class
			.getName());

	public static NFSFileSystem getInstance(String ip, int port, String username, String password,
			int timeout) throws ExceptionCommonBase {
		if (instance == null) {
			synchronized (SyncRoot) {
				if (instance == null) {
					instance = new NFSFileSystem(ip, port, username, password,
							timeout);
				}
			}
		}
		return instance;
	}

	public NFSFileSystem(String ip, int port, String username, String password,
			int timeout) {
		super();
		this.init(ip, port, username, password, timeout);
	}

	@Override
	public void mkdirs(String path) throws ExceptionCommonBase {
		path = path.substring(0, path.lastIndexOf("/"));
		String[] dirs = path.split("/");
		StringBuffer sb = new StringBuffer();
		if (channel == null || session == null || !channel.isConnected()
				|| !session.isConnected()) {
			this.initChannel();
		}
		try {
			for (int i = 1; i < dirs.length; i++) {
				try {
					sb.append("/" + dirs[i]);
					channel.mkdir(sb.toString());
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			log.error(NFSFileSystem.class,e);
			 throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, dirs +" :nfs mkdir error");
		} finally {
			if (channel != null) {
				session.disconnect();
			} 
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	@Override
	public int uploadFile(String desFilePath, InputStream is) throws ExceptionCommonBase {
		int fileSize = 0;
		OutputStream out = null;
		if (channel == null || session == null || !channel.isConnected()
				|| !session.isConnected()) {
			this.initChannel();
		}
		try {
			out = channel.put(desFilePath, ChannelSftp.OVERWRITE); //
			// 使用OVERWRITE模式
			if (out != null) {
				byte[] buf = new byte[1024];
				int length = 0;
				while ((length = is.read(buf)) != -1) {
					fileSize += length;
					out.write(buf, 0, length);
					// System.out.println(fileSize);
					log.info(fileSize);
				}
				return fileSize;
			}
		} catch (IOException e) {
			log.error(NFSFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :nfs upload error");
		} catch (SftpException e) {
			log.error(NFSFileSystem.class,e);
			 throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :nfs upload error");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (session !=null) {
					session.disconnect();
				} else if (channel != null) {
					channel.disconnect();
				}
			} catch (Exception e2) {
				log.error(e2);
			}
		}
		return fileSize;
	}

	@Override
	protected void init(String ip, int port, String username, String password,
			int timeout) {
		super.init(ip, port, username, password, timeout);
	}

	public void initChannel() {
		JSch jsch = new JSch(); // 创建JSch对象
		try {
			session = jsch.getSession(username, ip, port); // 根据用户名，主机ip，端口获取一个Session对象
			log.info("Session created.");
			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config); // 为Session对象设置properties
			session.setTimeout(timeout); // 设置timeout时间
			session.connect(); // 通过Session建立链接
			log.info("Session connected.");

			log.info("Opening Channel.");
			channel = (ChannelSftp) session.openChannel("sftp"); // 打开SFTP通道
			channel.connect(); // 建立SFTP通道的连接
			log.info("Connected successfully to ftpHost = " + ip
					+ ",as ftpUserName = " + username + ", returning: "
					+ channel);
			channel = (ChannelSftp) channel;
		} catch (Exception e) {
			log.error(NFSFileSystem.class,e);
			log.error(e);
		}
	}

	public static void main(String[] args) throws ExceptionCommonBase,
			FileNotFoundException {
		String ip = "192.168.10.63";
		int port = 22;
		String username="root";
		String password="test$11";
		int timeout =300;
		
		
		IFileSystem fileSystem = NFSFileSystem.getInstance(ip, port, username, password, timeout);
		String path = "/pub/2013.7.30_nfs/egame.interfaces.jar";
		fileSystem.mkdirs(path);
		File file = new File("e://egame_6_0_3_0530_zjz.apk");
		InputStream is = new FileInputStream(file);
		fileSystem.uploadFile(path, is);

	}

    @Override
    public boolean moveFile(String srcFilePath, String srcFileName, String targetFilePath, String targetFileName)
            throws ExceptionCommonBase {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getOutputStream(OutputStream outputStream, String sourceFileName) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }
}

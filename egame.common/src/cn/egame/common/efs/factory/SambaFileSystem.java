package cn.egame.common.efs.factory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import cn.egame.common.efs.FileSystemBase;
import cn.egame.common.efs.IFileSystem;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;

/**
 * 
 * Description 写入文件到SamBa文件系统的对应类
 * 
 * @ClassName SamBaFileSystem
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author yuchao
 * 
 * @Create Date 2013-7-17
 * 
 * @Modified by none
 * 
 * @Modified Date
 */
public class SambaFileSystem extends FileSystemBase implements IFileSystem {


	private String url = "";
	private SmbFile smbFile = null;
	private SmbFileOutputStream smbOut = null;

	private static SambaFileSystem instance = null;
	private static byte[] SyncRoot = new byte[1];

	/*public static SambaFileSystem getInstance(String ip, int port, String username, String password,
			int timeout) throws ExceptionCommonBase {
		if (instance == null) {
			synchronized (SyncRoot) {
				if (instance == null) {
					instance = new SambaFileSystem();
					instance.init(ip, port, username, password, timeout);
				}
			}
		}
		return instance;
	}
*/
	public SambaFileSystem(String ip, int port, String username, String password,
			int timeout) throws ExceptionCommonBase {
		super();
		// init();
		init(ip, port, username, password, timeout);
	}

	@Override
	protected void init(String ip, int port, String username, String password,
			int timeout) {
		try {

			super.init(ip, port, username, password, timeout);
			this.url = "smb://" + username + ":" + password + "@" + ip + ":"
					+ port;

			// log.println("开始连接...url：" + this.url);
			smbFile = new SmbFile(this.url);
			smbFile.connect();
			// log.println("连接成功...url：" + this.url);
		} catch (MalformedURLException e) {
			logger.error(SambaFileSystem.class,e);
		} catch (IOException e) {
			logger.error(SambaFileSystem.class,e);
		}
	}

	@Override
	public void mkdirs(String path) throws ExceptionCommonBase {
		try {
			// path格式 ： smb://dev:dev3e4e@192.168.10.14:445

			path = path.substring(0, path.lastIndexOf("/"));
			String wholePath = "smb://" + username + ":" + password + "@" + ip
					+ ":" + port + path;
			SmbFile f = new SmbFile(wholePath);

			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			logger.error(SambaFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :samba mkdir error");
		} finally {
            try {
                if (null != this.smbOut)
                    this.smbOut.close();
            } catch (Exception e2) {
                logger.error(e2);
            }
        }
	}

	@Override
	public int uploadFile(String desFilePath, InputStream is) throws ExceptionCommonBase {
		int flag = -1;
		BufferedInputStream bf = null;
		try {
			this.smbOut = new SmbFileOutputStream(this.url + desFilePath, false);
			bf = new BufferedInputStream(is);
			byte[] bt = new byte[8192];
			int n = -1;
			int length = 0;
			while ((n = bf.read(bt)) != -1) {
				this.smbOut.write(bt, 0, n);
				this.smbOut.flush();
				length += n;
				logger.info(length);
				// System.out.println(length);

			}
			flag = length;
			// log.println("文件传输结束...");
		} catch (SmbException e) {
			logger.error(SambaFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :samba upload error");
		} catch (MalformedURLException e) {
			logger.error(SambaFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :samba upload error");
		} catch (UnknownHostException e) {
			logger.error(SambaFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :samba upload error");
		} catch (IOException e) {
			logger.error(SambaFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :samba upload error");
		} finally {
			try {
				if (null != this.smbOut)
					this.smbOut.close();
				if (null != bf)
					bf.close();
			} catch (Exception e2) {
				logger.error(e2);
			}
		}
		return flag;
	}

	public static void main(String[] args) throws ExceptionCommonBase,
			FileNotFoundException {
		String ip = "192.168.10.14";
		int port = 445;
		String username="dev";
		String password="dev3e4e";
		int timeout =300;
		IFileSystem fileSystem = new SambaFileSystem(ip, port, username, password, timeout);
		String path = "/pub/2013_7_30/f/xxx.jar";
		fileSystem.mkdirs(path);
		String localFile = "e://egame_6_0_3_0530_zjz.apk"; // 本地要上传的文件
		File file = new File(localFile);
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

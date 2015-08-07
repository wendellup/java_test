package cn.egame.common.efs;

import java.io.InputStream;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;

public abstract class FileSystemBase implements IFileSystem {

	protected static Logger logger = Logger.getLogger(FileSystemBase.class);
	protected String ip;
	protected int port;
	protected String username;
	protected String password;
	protected int timeout = 0;

	@Override
	public abstract void mkdirs(String path) throws ExceptionCommonBase;;

	@Override
	public abstract int uploadFile(String desFilePath, InputStream is)
			throws ExceptionCommonBase;;

	protected void init(String ip, int port, String username, String password,
			int timeout) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.timeout = timeout;
	}

}

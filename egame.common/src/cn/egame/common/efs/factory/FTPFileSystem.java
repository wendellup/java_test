/*
 * 写入文件到ftp文件系统
 */
package cn.egame.common.efs.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;

import cn.egame.common.efs.FileSystemBase;
import cn.egame.common.efs.IFileSystem;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;

/**
 * 写入文件到ftp文件系统
 * 
 * @ClassName FTPFileSystem
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author wanmin
 * 
 * @Create Date 2013-8-12
 * 
 * @Modified by none
 * 
 * @Modified Date
 */
public class FTPFileSystem extends FileSystemBase implements IFileSystem {
    private static Logger logger = Logger.getLogger(FTPFileSystem.class);
    private FTPClient client;
    private static FTPFileSystem instance = null;
    private static byte[] SyncRoot = new byte[1];

    public static FTPFileSystem getInstance(String ip, int port,
            String username, String password, int timeout)
            throws ExceptionCommonBase {
        if (instance == null) {
            synchronized (SyncRoot) {
                if (instance == null) {
                    instance = new FTPFileSystem(ip, port, username, password,
                            timeout);
                    instance.init(ip, port, username, password, timeout);
                }
            }
        }
        return instance;
    }

    public FTPFileSystem(String ip, int port, String username, String password,
            int timeout) throws ExceptionCommonBase {
        super();
        init(ip, port, username, password, timeout);
    }

    /**
     * 登陆FTP服务器
     * 
     * @author wanmin
     * 
     * @throws IOException
     *             文件流异常
     * @throws SocketException
     *             套接字异常
     * 
     * @timer 2013-08-12
     */
    private void login() throws SocketException, IOException {
        if (client == null) {
            client = new FTPClient();
        }

        logger.info("ip: " + ip + "," + "port: " + port + "," + "username: "
                + username + "," + "password: " + password + "," + "timeout: "
                + timeout);
        client.connect(ip, port);
        FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        client.configure(config);
        client.setControlEncoding("ISO-8859-1");
        client.setDefaultTimeout(timeout);
        if (!client.login(username, password)) {
            logger.info("ftp login!the username or password is wrong");
        }
        client.setFileType(FTP.BINARY_FILE_TYPE);
    }

    /**
     * 退出FTP服务器
     * 
     * @author wanmin
     * 
     * @throws IOException
     *             文件流异常
     * 
     * @timer 2013-08-12
     */
    private void logout() throws IOException {
        if (client != null) {
            client.logout();
            client.disconnect();
            client = null;
        }
    }

    @Override
    public void mkdirs(String path) throws ExceptionCommonBase {
        try {
            // 链接，登陆ftp服务器
            this.login();

            // 传入的路径类似xxx/xxx/xxx.txt，需要截取"/",最后的"/"之后是目标名称吗，前面是目录
            path = path.substring(0, path.lastIndexOf("/"));
            String directoryName[] = path.split("/");
            String tempPath = "";
            int length = directoryName.length;
            for (int i = 0; i < length; i++) {
                tempPath = tempPath + "/" + directoryName[i];
                // 判断路径是否存在（文件夹是都已经创建），不存在则需要创建
                if (!client.changeWorkingDirectory(tempPath)) {
                    client.makeDirectory(tempPath);
                }
            }
           
        } catch (SocketException e) {
            logger.error(FTPFileSystem.class,e);
            throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        } catch (IOException e) {
            logger.error(FTPFileSystem.class,e);
            throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        }catch(Exception e){
        	 logger.error(FTPFileSystem.class,e);
        	 throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        }finally{
            // 退出ftp服务器
            try {
                this.logout();
            } catch (IOException e) {
                logger.error(e);
            }
        }

    }

    @Override
    public int uploadFile(String desFilePath, InputStream is)
            throws ExceptionCommonBase {
        int fileSize = 0;
        String filePth = desFilePath.substring(0, desFilePath.lastIndexOf("/"));
        String desFileName = desFilePath.substring(
                desFilePath.lastIndexOf("/") + 1, desFilePath.length());
        try {
            // 链接，登陆ftp服务器
            this.login();
            // 检测目标目录是否存在
            if (!client.changeWorkingDirectory(filePth)) {
                // 不存在则新建目标目录
                this.mkdirs(filePth);
                client.changeWorkingDirectory(filePth);
            } else {
                // 如果要上传的文件已经存在，首先先进行删除，然后再上传
                client.deleteFile(desFileName);
            }

            boolean uploadresult = client.storeFile(desFileName, is);
            logger.info("the result of storeFile is " + uploadresult);
            if (uploadresult) {
            	fileSize = getFileSize(client, desFilePath);
            }
        } catch (SocketException e) {
        	 logger.error(FTPFileSystem.class,e);
            throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :ftp upload error");
        } catch (IOException e) {
        	 logger.error(FTPFileSystem.class,e);
            throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :ftp upload error");
        }catch(Exception e){
        	 logger.error(FTPFileSystem.class,e);
             throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :ftp upload error");
        } finally {
            try {
                this.logout();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return fileSize;
    }

    
    public int getFileSize(FTPClient client,String desFilePath) throws ExceptionCommonBase{
    	int fileSize = 0;
    	try {
			client.sendCommand("SIZE "+desFilePath+"\r\n");
		} catch (IOException e) {
			 logger.error(FTPFileSystem.class,e);
			throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :ftp getFileSize error");
		}
        int res = client.getReplyCode();
        if(res==213){
            String msg= client.getReplyString();
            try{
                fileSize = Integer.parseInt(msg.substring(3).trim());
            }
            catch(Exception e){
            	 logger.error(FTPFileSystem.class,e);
            	 throw new ExceptionCommonBase(ErrorCodeBase.EFSUploadError, desFilePath +" :ftp getFileSize error");
            }
        }
        return fileSize;
    }
    public static void main(String[] args) throws ExceptionCommonBase,
            FileNotFoundException {
        String ip = "192.168.10.63";
        int port = 21;
        String username = "root";
        String password = "test$11";
        int timeout = 300;

        IFileSystem fileSystem = FTPFileSystem.getInstance(ip, port, username,
                password, timeout);

        String uploadpath = "/data/share/f/ftp_mobile/wm_mobile/testwm1/your sister.txt";

        fileSystem.mkdirs(uploadpath);

        File file = new File("e://test//test.txt");
        InputStream is = new FileInputStream(file);
        fileSystem.uploadFile(uploadpath, is);
    }

    @Override
    public boolean moveFile(String srcFilePath, String srcFileName,
            String targetFilePath, String targetFileName)
            throws ExceptionCommonBase {
        try {
            this.login();
            /** 检测源文件所在目录是否存在 */
            if (client.changeWorkingDirectory(srcFilePath) == false) {
                return false;
            }
            /** 检测是否存在源文件 */
            String fileNames[] = client.listNames();
            if (fileNames != null) {
                Boolean exists = false;
                for (int i = 0; i < fileNames.length; i++) {
                    if (fileNames[i].equals(srcFileName)) {
                        exists = true;
                        break;
                    }
                }
                if (exists == false) {
                    return false;
                }
            }
            /** 检测目标文件存放目录是否存在 */
            if (client.changeWorkingDirectory(targetFilePath) == false) {
                this.mkdirsWithoutLogin(targetFilePath);
            }
            client.changeWorkingDirectory(targetFilePath);
            return client.rename(srcFilePath+srcFileName, targetFilePath+targetFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.logout();
            } catch (IOException e) {
                logger.error(FTPFileSystem.class,e);
            }
        }
        return false;
    }
    

    @Override
    public boolean getOutputStream(OutputStream outputStream, String sourceFileName) throws IOException {
        try {
            this.login();
            return client.retrieveFile(sourceFileName, outputStream);
        } catch (Exception e) {
            logger.error(FTPFileSystem.class,e);
        } finally {
            if(outputStream!=null){
                outputStream.close();
            }
            this.logout();
        }
        return false;
    }
    
    private void mkdirsWithoutLogin(String path) throws ExceptionCommonBase {
        try {
            // 传入的路径类似xxx/xxx/xxx.txt，需要截取"/",最后的"/"之后是目标名称吗，前面是目录
            path = path.substring(0, path.lastIndexOf("/"));
            String directoryName[] = path.split("/");
            String tempPath = "";
            int length = directoryName.length;
            for (int i = 0; i < length; i++) {
                tempPath = tempPath + "/" + directoryName[i];
                // 判断路径是否存在（文件夹是都已经创建），不存在则需要创建
                if (!client.changeWorkingDirectory(tempPath)) {
                    client.makeDirectory(tempPath);
                }
            }
           
        } catch (SocketException e) {
            logger.error(FTPFileSystem.class,e);
            throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        } catch (IOException e) {
            logger.error(FTPFileSystem.class,e);;
            throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        }catch(Exception e){
            logger.error(FTPFileSystem.class,e);
             throw new ExceptionCommonBase(ErrorCodeBase.EFSMkdirError, path +" :ftp mkdir error");
        }

    }
}

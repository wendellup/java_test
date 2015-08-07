/**
 * 
 */
package cn.egame.common.efs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.egame.common.efs.factory.FTPFileSystem;
import cn.egame.common.efs.factory.LocalFileSystem;
import cn.egame.common.efs.factory.NFSFileSystem;
import cn.egame.common.efs.factory.SambaFileSystem;
import cn.egame.common.exception.ErrorCodeBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

/**
 * Description TODO
 * 
 * @ClassName FileSystemFactory
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

public class SFileSystemClient {
    private static Logger logger = Logger.getLogger(SFileSystemClient.class);

    private static byte[] lock = new byte[0];

    private static Map<String, IFileSystem> hash = new HashMap<String, IFileSystem>();

    /*
     * public static IFileSystem getInstance(String server) {
     * 
     * IFileSystem instance = hash.get(server); if (instance == null)
     * synchronized (lock) { instance = hash.get(server); if (instance == null)
     * { instance = init(server); hash.put(server, instance); } } return
     * instance; }
     */

    public static IFileSystem getInstance(String server) {

        IFileSystem instance = init(server);
        return instance;
    }

    private static IFileSystem init(String server) {
        try {
            String temp = Utils.stringIsNullOrEmpty(server) ? "" : "." + server;
            Properties properties = Utils.getProperties("efs.properties");
            String filesystem = properties.getProperty("filesystem" + temp,
                    "nfs");
            logger.info("filesystem" + temp + "=" + filesystem);

            if (Utils.stringCompare("nfs", filesystem)) {
                Map<String, String> serverInfo = getEfs(server, temp,
                        properties, "nfs");
                NFSFileSystem efs = new NFSFileSystem(serverInfo.get("ip"),
                        Integer.parseInt(serverInfo.get("port")),
                        serverInfo.get("username"), serverInfo.get("password"),
                        Integer.parseInt(serverInfo.get("timeout")));
                hash.put(server, efs);
                return efs;
            } else if (Utils.stringCompare("samba", filesystem)) {
                Map<String, String> serverInfo = getEfs(server, temp,
                        properties, "samba");
                SambaFileSystem efs = new SambaFileSystem(serverInfo.get("ip"),
                        Integer.parseInt(serverInfo.get("port")),
                        serverInfo.get("username"), serverInfo.get("password"),
                        Integer.parseInt(serverInfo.get("timeout")));
                hash.put(server, efs);
                return efs;
            } else if (Utils.stringCompare("local", filesystem)) {
                LocalFileSystem efs = LocalFileSystem.getInstance();
                hash.put(server, efs);
                return efs;
            } else if (Utils.stringCompare("ftp", filesystem)) {
                Map<String, String> serverInfo = getEfs(server, temp,
                        properties, "ftp");
                FTPFileSystem efs = new FTPFileSystem(serverInfo.get("ip"),
                        Integer.parseInt(serverInfo.get("port")),
                        serverInfo.get("username"), serverInfo.get("password"),
                        Integer.parseInt(serverInfo.get("timeout")));
                hash.put(server, efs);
                return efs;
            } else
                throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError,
                        "filesystem" + temp + "[nfs|samba|local|ftp]=="
                                + filesystem);
        } catch (FileNotFoundException e) {
            logger.error(null, e);
        } catch (NumberFormatException e) {
            logger.error(null, e);
        } catch (IOException e) {
            logger.error(null, e);
        }

        return null;
    }

    private static Map<String, String> getEfs(String server, String temp,
            Properties properties, String type) throws ExceptionCommonBase {
        String ip = properties.getProperty("filesystem" + temp + "." + type
                + ".ip");
        int port = Utils.toInt(
                properties.getProperty("filesystem" + temp + "." + type
                        + ".port"), 22);
        String username = properties.getProperty("filesystem" + temp + "."
                + type + ".username");
        String password = properties.getProperty("filesystem" + temp + "."
                + type + ".password");
        int timeout = Utils.toInt(
                properties.getProperty("filesystem" + temp + "." + type
                        + ".timeout"), 300);

        if (Utils.stringIsNullOrEmpty(ip))
            throw new ExceptionCommonBase(ErrorCodeBase.SysConfigError,
                    "filesystem" + temp + "." + type + ".ip=null");

        Map<String, String> map = new HashMap<String, String>();
        map.put("ip", ip);
        map.put("port", String.valueOf(port));
        map.put("username", username);
        map.put("password", password);
        map.put("timeout", String.valueOf(timeout));
        return map;
    }

    public static void main(String[] args) throws ExceptionCommonBase,
            FileNotFoundException {
        IFileSystem fileSystem = SFileSystemClient.getInstance("egame");
        String path = "/pub/2013.7.31/egame.interfaces.jar";
        fileSystem.mkdirs(path);
        String localFile = "e://egame_6_0_3_0530_zjz.apk"; // 本地要上传的文件
        File file = new File(localFile);
        InputStream is = new FileInputStream(file);
        fileSystem.uploadFile(path, is);
    }
}

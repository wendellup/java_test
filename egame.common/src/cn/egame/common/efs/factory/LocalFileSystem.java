/**
 * 
 */
package cn.egame.common.efs.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import cn.egame.common.efs.FileSystemBase;
import cn.egame.common.efs.IFileSystem;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

/**
 * Description 写入文件到本地文件系统
 * 
 * @ClassName LocalFileSystem
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

public class LocalFileSystem extends FileSystemBase implements IFileSystem {
    private static Logger log = Logger.getLogger(LocalFileSystem.class);

    private static LocalFileSystem instance = null;
    private static byte[] SyncRoot = new byte[1];

    public static LocalFileSystem getInstance() throws ExceptionCommonBase {
        if (instance == null) {
            synchronized (SyncRoot) {
                if (instance == null) {
                    instance = new LocalFileSystem();
                }
            }
        }
        return instance;
    }

    private LocalFileSystem() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.egame.common.filesystem.factory.IFileSystem#mkdirs(java.lang.String)
     */
    @Override
    public void mkdirs(String path) {
        Utils.mkdirs(path);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cn.egame.common.filesystem.factory.IFileSystem#uploadFile(java.io.InputStream
     * , java.lang.String)
     */
    @Override
    public int uploadFile(String desFilePath, InputStream is) {
        int fileSize = 0;
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(desFilePath));
            int length = 0;
            byte[] buf = new byte[1024];
            while ((length = is.read(buf)) != -1) {
                fileSize += length;
                os.write(buf, 0, length);
            }
        } catch (IOException e) {
            log.error(LocalFileSystem.class, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error(e);
                }
            }
        }
        return fileSize;
    }

    public static void main(String[] args) throws ExceptionCommonBase, FileNotFoundException {
        IFileSystem fileSystem = LocalFileSystem.getInstance();
        String path = "f:/opt/egame.interface.jar";
        // String path = "/pub/2013.7.17_nfs_18_50/egame.interfaces.jar";
        fileSystem.mkdirs(path);
        File file = new File("e://egame.interfaces.jar");
        InputStream is = new FileInputStream(file);
        fileSystem.uploadFile(path, is);

    }

    @Override
    public boolean moveFile(String srcFilePath, String srcFileName, String targetFilePath, String targetFileName)
            throws ExceptionCommonBase {
        File from_file = new File(srcFilePath, srcFileName);
        File to_file = new File(targetFilePath, targetFileName);

        // 首先确定源文件存在，并且是可读的文件
        if (!from_file.exists())
            return false;
        if (!from_file.isFile())
            return false;
        if (!from_file.canRead())
            return false;

        mkdirs(targetFilePath + targetFileName);

        // 如果目标是一个目录，则将源文件名作为目标文件名
        if (to_file.isDirectory())
            to_file = new File(to_file, from_file.getName());
        return from_file.renameTo(to_file);

    }

    @Override
    public boolean getOutputStream(OutputStream outputStream, String sourceFileName) throws IOException {
        InputStream is = null;
        try {
            File from_file = new File(sourceFileName);

            // 首先确定源文件存在，并且是可读的文件
            if (!from_file.exists())
                return false;
            if (!from_file.isFile())
                return false;
            if (!from_file.canRead())
                return false;

            is = new FileInputStream(from_file);
            byte[] arys = new byte[1024];
            int num = 0;
            while ((num = is.read(arys)) != -1) {
                outputStream.write(arys, 0, num);
            }
            return true;
        } catch (Exception e) {
            log.error(LocalFileSystem.class, e);
        } finally {
            if (is != null) {
                is.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return false;

    }

}

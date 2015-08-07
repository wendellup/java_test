package cn.egame.common.efs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.egame.common.exception.ExceptionCommonBase;

/**
 * 
 * Description 文件服务器接口类
 * 
 * @ClassName IFileSystem
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
public interface IFileSystem {
    /**
     * @Description: 根据指定的path,创建目录,可以创建子目录
     * @param path
     * @return void
     * @throws
     * @Author yuchao
     * @Create Date 2013-7-17
     * @Modified by none
     * @Modified Date
     */
    void mkdirs(String path) throws ExceptionCommonBase;

    /**
     * @Description: 上传文件
     * @param is
     *            要上传的文件流
     * @param desFilePath
     *            文件上传的目的路径(包括文件名)
     * @return
     * @return int
     * @throws
     * @Author yuchao
     * @Create Date 2013-7-17
     * @Modified by none
     * @Modified Date
     */
    int uploadFile(String desFilePath, InputStream is) throws ExceptionCommonBase;

    boolean moveFile(String srcFilePath, String srcFileName, String targetFilePath, String targetFileName) throws ExceptionCommonBase;

    public boolean getOutputStream(OutputStream outputStream, String sourceFileName) throws IOException;
}

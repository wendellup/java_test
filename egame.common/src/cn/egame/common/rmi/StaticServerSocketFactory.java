/*
 * Copyright (C) 2013 XuanCai
 * All right reserved.
 * author: qintao
 */
package cn.egame.common.rmi;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

/**
 * 
 * @ClassName StaticServerSocketFactory
 * @Copyright �Ųʻ���
 * @Project datasync
 * @Author qintao
 * @Create Date 2013-7-1
 * @Modified by none
 * @Modified Date
 */
public class StaticServerSocketFactory implements RMIServerSocketFactory,Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -37658975837202237L;
	int port;

	public StaticServerSocketFactory(int port) {
		this.port = port;
	}

	public ServerSocket createServerSocket(int port) throws IOException {
		System.out.println("creating ServerSocket on port" + port);
		return new ServerSocket(port);
	}

}

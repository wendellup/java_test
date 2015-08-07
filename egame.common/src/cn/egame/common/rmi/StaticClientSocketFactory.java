/*
 * Copyright (C) 2013 XuanCai
 * All right reserved.
 * author: qintao
 */
package cn.egame.common.rmi;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

/**
 * 
 * @ClassName StaticClientSocketFactory
 * @Copyright
 * @Project datasync
 * @Author qintao
 * @Create Date 2013-7-1
 * @Modified by none
 * @Modified Date
 */
public class StaticClientSocketFactory implements RMIClientSocketFactory,Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -4023734509881894586L;
	String ip;
	int port;
	/**
     * 
     */
	public StaticClientSocketFactory(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public Socket createSocket(String host, int port) throws IOException {
		System.out.println("creating socket to host : " + host + " on port" + port);
		return new Socket(host, port);
	}

}

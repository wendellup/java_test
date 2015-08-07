package cn.egame.common.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class EGameRMISocket extends RMISocketFactory {
	int set_port = 0;

	public EGameRMISocket(int port) {
		this.set_port = port;
	}

	public Socket createSocket(String host, int port) throws IOException {
		return new Socket(host, port);
	}

	public ServerSocket createServerSocket(int port) throws IOException {
		if (port == 0)
			port = this.set_port;
		System.out.println("rmi (regist&data)port:"+port);
		return new ServerSocket(this.set_port);
	}
}

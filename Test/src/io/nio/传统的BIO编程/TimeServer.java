package io.nio.传统的BIO编程;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//参考文章url:http://ifeve.com/netty-definitive-guide-2-1/
public class TimeServer {

	    /**
	     * @param args
	     * @throws IOException
	     */
	    public static void main(String[] args) throws IOException {
		int port = 8080;
		if (args != null && args.length > 0) {

		    try {
			port = Integer.valueOf(args[0]);
		    } catch (NumberFormatException e) {
			// 采用默认值
		    }

		}
         ServerSocket server = null;
		try {
		    server = new ServerSocket(port);
		    System.out.println("The time server is start in port : " + port);
		    Socket socket = null;
		    while (true) {
			socket = server.accept();
			new Thread(new TimeServerHandler(socket)).start();
		    }
		} finally {
		    if (server != null) {
			System.out.println("The time server close");
			server.close();
			server = null;
		    }
		}
	    }
	}
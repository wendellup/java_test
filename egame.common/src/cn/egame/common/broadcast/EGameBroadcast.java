package cn.egame.common.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.Enumeration;

import org.apache.log4j.Logger;

public class EGameBroadcast implements Runnable {
	private MulticastSocket mSocket = null;
	private InetAddress groupAddress = null;
	private int groupPort = 601;
	private byte[] SyncRoot = new byte[0];
	private Thread thread = null;
	private IRecvDatagramPacket recvDatagramPacket = null;
	private Logger logger = null;

	public EGameBroadcast(IRecvDatagramPacket recvDatagramPacket) {
		this.recvDatagramPacket = recvDatagramPacket;
		logger = Logger.getLogger(EGameBroadcast.class.getSimpleName());
	}

	public void connect(String address, int port) throws IOException {

		synchronized (SyncRoot) {
			if (mSocket == null || mSocket.isClosed()) {
				groupAddress = InetAddress.getByName(address);
				groupPort = port;
				mSocket = new MulticastSocket(port);
				mSocket.setTimeToLive(8);
				mSocket.joinGroup(groupAddress);
				thread = new Thread(this);
				thread.start();
			}
		}
	}

	public InetAddress getGroupAddress() {
		return groupAddress;
	}

	public int getGroupPort() {
		return groupPort;
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					if (mSocket != null && !mSocket.isConnected()) {
						byte[] b = new byte[2000];
						DatagramPacket recPacket = new DatagramPacket(b,
								b.length);
						mSocket.receive(recPacket);

						String senderIp = recPacket.getAddress().toString();

						String data = new String(recPacket.getData())
								.substring(0, recPacket.getLength());
						if (data != null && data.endsWith("\n")) {
							data = data.substring(0, data.length() - 1);

							if (this.recvDatagramPacket != null)
								this.recvDatagramPacket.recvDatagramPacket(
										senderIp, data);
						}
					} else
						break;
				} catch (IOException ex) {
					logger.error(null, ex);
				}
			}

		} catch (Exception ex) {
			logger.error(null, ex);
		} finally {
			thread = null;
			mSocket = null;
		}
	}

	public void send(String str) throws IOException {
		// logger.info("Send:" + str);
		if (!str.endsWith("\n"))
			str = str + "\n";
		if (mSocket == null || mSocket.isClosed()) {
			connect(groupAddress.getHostAddress(), groupPort);
		}

		DatagramPacket sendPacket = new DatagramPacket(str.getBytes(),
				str.length(), groupAddress, groupPort);
		mSocket.send(sendPacket);
	}

	public void setGroupAddress(InetAddress groupAddress) {
		this.groupAddress = groupAddress;
	}

	public void setGroupPort(int groupPort) {
		this.groupPort = groupPort;
	}

	public static void main(String[] args) {

		try {
			EGameBroadcast cs = new EGameBroadcast(null);
			cs.connect("224.10.11.11", 801);
			int i = 1000;
			while (i-- > 0) {
				cs.send("1234");
				Thread.sleep(1000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

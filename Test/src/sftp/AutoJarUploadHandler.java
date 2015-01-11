package sftp;

import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class AutoJarUploadHandler {

	public static void main(String[] args) {
		String hostname = "192.168.88.129";
		String username = "root";
		String password = "root";
		try {
			// 建立连接
			Connection conn = new Connection(hostname);
			System.out.println("set up connections");
			conn.connect();
			// 利用用户名和密码进行授权
			boolean isAuthenticated = conn.authenticateWithPassword(username,
					password);
			if (isAuthenticated == false) {
				throw new IOException("Authorication failed");
			}
			// 打开会话
			Session sess = conn.openSession();
			System.out
					.println("Execute command:/usr/bin/perl /test/discover.pl /test/meps_linux.txt");
			/*
			// 执行命令
			sess.execCommand("/usr/bin/perl /test/discover.pl /test/meps_linux.txt");
			System.out.println("The execute command output is:");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stdout));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}
			System.out.println("Exit code " + sess.getExitStatus());
			*/
			sess.close();
			conn.close();
			System.out.println("Connection closed");
		} catch (IOException e) {
			System.out.println("can not access the remote machine");
		}
	}

}

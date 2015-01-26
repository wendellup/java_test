package test.client.file;

import cn.egame.common.util.Utils;
import cn.egame.interfaces.fl.FileUtils;

public class FileTest {
	public static void main(String[] args) {
		Utils.initLog4j();
		System.out.println(Utils.getFileId("ad1daca1h1b6c4ba", 0));
	}
}

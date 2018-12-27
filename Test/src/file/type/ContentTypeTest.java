package file.type;

import java.io.File;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

public class ContentTypeTest {
	public static void main(String[] args) throws MagicParseException, MagicMatchNotFoundException, MagicException {
//		File file = new File("C:\\Users\\yuchao\\Desktop\\server.xml");
		File file = new File("E:\\apk\\egame_7_3_0_1225_dev.apk");
		MagicMatch match = Magic.getMagicMatch(file, false, true);
		String contentType = match.getMimeType();
		System.out.println(contentType);
	}
}

package test.open.url.mail;

public class MailTest {
	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("connectionCheck@163.com");
		mailInfo.setPassword("abcd1234");// 您的邮箱密码
		mailInfo.setFromAddress("connectionCheck@163.com");
		mailInfo.setToAddress("15366189928@189.cn");
		mailInfo.setSubject("彩蛋现网挂了。。。");
		mailInfo.setContent("彩蛋现网挂了。。。");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
//		sms.sendHtmlMail(mailInfo);// 发送html格式
	}
}

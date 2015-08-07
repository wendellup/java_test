package cn.egame.common.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public class DES {
    public static final String PRE_SECRET="$EG01~";
    public static final String DESKEY="egame189!QAZ@WSX";
    static Logger logger = Logger.getLogger(DES.class);
    /**
     * DES 加密
     * 
     * @param String
     *            souce=源字符串
     * 
     * @param String
     *            key=加密密钥
     * 
     * @return String=DES 加密的字符串
     * 
     * @author Mac
     * 
     * @timer 2011-01-10
     */
    public static String encryptForDES(String souce, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key1 = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key1, sr);
        // 现在，获取数据并加密
        byte encryptedData[] = cipher.doFinal(souce.getBytes());
        // 通过BASE64位编码成字符创形式
        String base64Str = cn.egame.common.util.Base64.encode(encryptedData);

        return base64Str;
    }

    /**
     * DES 解密
     * 
     * @param String
     *            souce=DES 加密字符串
     * 
     * @param String
     *            key=解密密钥
     * 
     * @return String=解密后字符串
     * 
     * @author Mac
     * 
     * @timer 2011-01-10
     */
    public static String decryptForDES(String souce, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException,
            IllegalBlockSizeException, BadPaddingException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key1 = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key1, sr);
        // 将加密报文用BASE64算法转化为字节数组
        byte[] encryptedData =cn.egame.common.util.Base64.decode(souce);
        // 用DES算法解密报文
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return new String(decryptedData,"UTF-8");
    }

    
    public static String getSecretSource(String secretStr) {

        if (!Utils.stringIsNullOrEmpty(secretStr)) {
            if (secretStr.startsWith(DES.PRE_SECRET)) {
                secretStr = secretStr.substring(DES.PRE_SECRET.length(), secretStr.length());
                long start = System.currentTimeMillis();
                try {
                    String source = DES.decryptForDES(secretStr, DES.DESKEY);
                    logger.info("decryptForDES------speend time:  " + (System.currentTimeMillis() - start));
                    return source;
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
        return secretStr;
    }
}

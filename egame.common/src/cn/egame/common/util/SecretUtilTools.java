package cn.egame.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * SecretUtilTools 是一个字符串加解密工具类<br/>
 * 
 * 目前整合了DES 加解密，MD5 加密，BASE64<br/>
 * 
 * @Copyright lenovo
 * 
 * @Project egame-core-api
 * 
 * @Author Mac
 * 
 * @timer 2011-01-10
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 6.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class SecretUtilTools {

    /** 无参的构造方法 */
    public SecretUtilTools() {
        super();
    }

    /**
     * MD5加密算法
     * 
     * @param String
     *            souce=源字符串
     * 
     * @return String=MD5 加密的字符串
     * 
     * @author Mac
     * 
     * @timer 2011-01-10
     */
    public static String encryptForMD5(String souce) {
        if (souce == null || souce.length() == 0) {
            System.err.println("警告 ： 空字符串不可以作MD5加密 !");
            return null;
        }
        StringBuffer hexStringBuf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(souce.getBytes());
            byte hash[] = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexStringBuf.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexStringBuf.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            String hexStr = hexStringBuf.toString();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(hexStr);
            hexStr = m.replaceAll("");
            return hexStr;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("当请求特定的加密算法<MD5>而它在该环境中不可用时抛出此异常");
        }
        return null;
    }

    /**
     * 消息签名
     * 
     * @param source
     * @param key
     * @return
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @Author qintao
     */
    public static String checkSum(String source, String key) throws IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException {
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
        byte encryptedData[] = cipher.doFinal(source.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(encryptedData);
            byte hash[] = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("当请求特定的加密算法<MD5>而它在该环境中不可用时抛出此异常");
        }
        return null;
    }

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
    public static String encryptForDES(String souce, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
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
        String base64Str = new BASE64Encoder().encode(encryptedData);

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
    public static String decryptForDES(String souce, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, NoSuchPaddingException, IOException, IllegalBlockSizeException,
            BadPaddingException {
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
        byte[] encryptedData = new BASE64Decoder().decodeBuffer(souce);
        // 用DES算法解密报文
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    /**
     * BASE64 编码
     * 
     * @param String
     *            souce=源字符串
     * 
     * @return String=BASE64 编码后的字符串
     * 
     * @throws FileNotFoundException
     * 
     * @author Mac
     * 
     * @time 2011-01-10
     */
    public static String encryptForBase64(String souce) throws FileNotFoundException, IOException {
        // 初始化文件输入流
        InputStream fin = null;
        // 输入缓冲流
        BufferedInputStream bin = null;
        // 二进制输出流
        ByteArrayOutputStream bout = null;
        try {
            fin = new FileInputStream(souce);
            bin = new BufferedInputStream(fin);
            bout = new ByteArrayOutputStream();
            // 每次读取8192字节
            byte temp[] = new byte[8192];
            int len = 0;
            while ((len = bin.read(temp, 0, 8192)) != -1) {
                bout.write(temp, 0, len);
            }
            return new BASE64Encoder().encode(bout.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bout != null) {
                try {
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * BASE64 解码
     * 
     * @param String
     *            souce=BASE64 编码后的字符串
     * 
     * @return String=BASE64 解码后的字符串
     * 
     * @author Mac
     * 
     * @timer 2007-05-01
     */
    public static String decryptForBase64(String souce) throws FileNotFoundException, IOException {
        String base64Str = SecretUtilTools.encryptForBase64(souce);
        byte data[] = null;
        // 字节数组输入流
        ByteArrayInputStream bain = null;
        // 输入缓冲流
        BufferedInputStream bin = null;
        // 文件输出流
        FileOutputStream fout = null;
        // 文件输出缓冲流
        BufferedOutputStream bout = null;
        try {
            data = new BASE64Decoder().decodeBuffer(base64Str);
            bain = new ByteArrayInputStream(data);
            bin = new BufferedInputStream(bain);
            fout = new FileOutputStream(souce);
            bout = new BufferedOutputStream(fout);
            byte temp[] = new byte[8192];
            int len = 0;
            while ((len = bin.read(temp, 0, 8192)) != -1) {
                bout.write(temp, 0, len);
                bout.flush();
            }
            return fout.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        } finally {
            if (bain != null) {
                try {
                    bain.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bout != null) {
                try {
                    bout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 3DES 编码
     * 
     * @param byte[] souce=源字节码
     * 
     * @param byte[] keybyte=Key
     * 
     * @return String=3DES 编码后的字符串
     * 
     * @author ZengZs
     * 
     * @time 2011-09-01
     */
    public static byte[] encryptFor3DES(byte[] souce, byte[] keybyte) {
        String algorithm = "DESede";
        try {
            byte[] keyBytes = new byte[24];
            int i = 0;
            int j = 0;
            for (i = 0, j = 0; i < 24; i++, j++) {
                if (j >= keybyte.length) {
                    j = 0;
                }
                keyBytes[i] = keybyte[j];
            }
            // 根据给定的字节数组和算法构造一个密钥
            SecretKey deskey = new SecretKeySpec(keyBytes, algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(souce);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * CBC加密
     * 
     * @param key
     *            密钥
     * @param keyiv
     *            IV(init vector)
     * @param data
     *            明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] encryptFor3DESCBC(byte[] key, byte[] keyiv, byte[] data) {
        Key deskey = null;
        DESedeKeySpec spec = null;
        try {
            spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] bOut = cipher.doFinal(data);
            return bOut;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * CBC解密
     * 
     * @param key
     *            密钥
     * @param keyiv
     *            IV
     * @param data
     *            Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] decryptFor3DESCBC(byte[] key, byte[] keyiv, byte[] data) {
        Key deskey = null;
        DESedeKeySpec spec = null;
        try {
            spec = new DESedeKeySpec(key);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(keyiv);
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] bOut = cipher.doFinal(data);
            return bOut;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 3DES 解码
     * 
     * @param String
     *            souce=3DES 编码后的字符串
     * 
     * @return String=3DES 解码后的字符串
     * 
     * @author ZengZs
     * 
     * @timer 2011-09-01
     */
    public static byte[] decryptFor3DES(byte[] souce, byte[] keybyte) {
        String algorithm = "DESede";
        try {
            byte[] keyBytes = new byte[24];
            int i = 0;
            int j = 0;
            for (i = 0, j = 0; i < 24; i++, j++) {
                if (j >= keybyte.length) {
                    j = 0;
                }
                keyBytes[i] = keybyte[j];
            }
            SecretKey deskey = new SecretKeySpec(keyBytes, algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(souce);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * BASE64 编码
     * 
     * @param byte[] souce=源字节码
     * 
     * @return String=BASE64 编码后的字节码
     * 
     * @author ZengZs
     * 
     * @time 2011-09-01
     */
    public static byte[] encryptForBase64(byte[] souce) {
        return Base64.encodeBase64(souce);
    }

    /**
     * BASE64 解码
     * 
     * @param String
     *            souce=BASE64 编码后的字节码
     * 
     * @return String=BASE64 解码后的字节码
     * 
     * @author ZengZs
     * 
     * @timer 2011-09-01
     */
    public static byte[] decryptForBase64(byte[] souce) {
        return Base64.decodeBase64(souce);
    }

    /**
     * SHA1加密
     * 
     * @param source
     * 
     * @return
     * 
     * @author ZengZS
     * 
     * @time 2011-9-2
     * 
     */
    public static final String encryptForSHA1(final String source) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        try {
            byte[] strTemp = source.getBytes("UTF-8");
            MessageDigest mdTemp = MessageDigest.getInstance("SHA-1");
            mdTemp.update(strTemp);
            // MessageDigest.getInstance(algorithm)
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}

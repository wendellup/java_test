package cn.egame.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sun.misc.BASE64Encoder;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class Utils {
    public static class tests {
        public static int t(int a, int b, Object... args) {
            return a + b;
        }
    }

    static Logger logger = Logger.getLogger(Utils.class);

    private static String appConfigRoot = "config";
    private static String appRoot = null;
    public final static long DEFAULT_DATE_LONG_1800_01_01 = Long.parseLong("-5364662400000");
    public final static Date DEFAULT_DATE = new Date(DEFAULT_DATE_LONG_1800_01_01); // "1800-1-1 8:00:00"
    public static final long DEFAULT_DATE_LONG_1900_01_01 = Long.parseLong("-2209017600000");
    public static final long DEFAULT_DATE_LONG_1970_01_01 = Long.parseLong("-28800000");
    private static final Pattern patternParam = Pattern.compile("\\{('([a-z1-9]+)':'([^']*)')\\}");
    public final static Pattern regEmail = Pattern.compile("(([^@]+)@(([a-z0-9A-Z]+([a-z0-9A-Z_-]+)?\\.)+[a-zA-Z]{2,}))");
    public final static Pattern regMobilePhone = Pattern.compile("(\\+|86|\\+86|0|)(1[0-9]{10})");
    public final static Pattern regRMIConfig = Pattern.compile("//([0-9\\.]+):([0-9]+)/(.*)");
    public final static Pattern regFileCRC = Pattern.compile("h([a-f0-9]+)([a-f0-9]{1})");

    public final static long SPER_HOUR_MILLISECOND = 3600000L;

    public final static String parttern = "yyyy-MM-dd HH:mm:ss";

    public static Date getMaxDate() {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat(parttern);
            return formatter.parse("4000-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return null;
    }

    public static int bitAnd(int a, int b) {
        return a & b;
    }

    public static int bitExclude(int status, int status2) {
        return (status | status2) - status2;
    }

    public static int bitExclusiveOR(int a, int b) {
        return a ^ b;
    }

    public static int bitOr(int a, int b) {
        return a | b;
    }

    public static String emailDomain(String email) {
        if (Utils.stringIsNullOrEmpty(email))
            return null;
        java.util.regex.Matcher m = regEmail.matcher(email);
        if (m.matches()) {
            String name = m.group(3);
            if (!Utils.stringIsNullOrEmpty(name)) {
                return name.toLowerCase();
            }
            return name;
        }
        return null;
    }

    // copy
    public static <T extends Object> List<T> collectionsCopy(List<T> list1) {
        List<T> result = new ArrayList<T>();
        if (list1 != null && !list1.isEmpty())
            result.addAll(list1);
        return result;
    }

    // set
    public static <T extends Object> void collectionsSetNullPoistion(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null)
            return;

        int j = 0;
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) == null) {
                list1.set(i, list2.get(j++));
                if (j >= list2.size())
                    return;
            }
        }
    }

    // 交集
    public static <T extends Object> List<T> collectionsRemoveNull(List<T> list) {
        List<T> result = new ArrayList<T>();
        if (list != null && !list.isEmpty()) {
            for (T item : list) {
                if (item != null)
                    result.add(item);
            }
        }
        return result;
    }

    // 交集,按照list1排序
    public static <T extends Object> List<T> collectionsRetain(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        // Map<T, Boolean> map = new HashMap<T, Boolean>();
        // for (int i = 0; i < list2.size(); i++) {
        // map.put(list2.get(i), true);
        // }
        Set<T> set = new HashSet<T>(list2);
        for (T item : list1) {
            if (set.contains(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // 并集
    public static <T extends Object> List<T> collectionsAdd(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();

        result.addAll(list1);

        for (T item : list2) {
            if (!list1.contains(item))
                result.add(item);
        }
        return result;
    }

    // 非并集
    public static <T extends Object> List<T> nonCollectionsAdd(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        for (T item : list2) {
            if (!list1.contains(item))
                result.add(item);
        }
        return result;
    }

    // 删除
    public static <T extends Object> List<T> collectionRemove(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>(list1);
        result.removeAll(list2);
        return result;
    }

    public static String emailName(String email) {
        if (Utils.stringIsNullOrEmpty(email))
            return null;
        java.util.regex.Matcher m = regEmail.matcher(email);
        if (m.matches()) {
            String name = m.group(2);
            if (!Utils.stringIsNullOrEmpty(name) && name.length() > 1) {
                name = name.toLowerCase();
                name = String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1);
                ;

            }
            return name;
        }
        return null;
    }

    public static String encryptMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        StringBuffer buf = new StringBuffer();
        md5.update(str.getBytes());
        byte bytes[] = md5.digest();
        for (int i = 0; i < bytes.length; i++) {
            String s = Integer.toHexString(bytes[i] & 0xff);
            if (s.length() == 1) {
                buf.append("0");
            }
            buf.append(s);
        }
        return buf.toString();
    }

    public static byte[] fileToBytes(String file) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream byteStream = null;
        try {
            fis = new FileInputStream(file);
            byteStream = new ByteArrayOutputStream();
            int readLength = -1;
            int bufferSize = 1024;
            byte bytes[] = new byte[bufferSize];
            while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
                byteStream.write(bytes, 0, readLength);
            }

            byte[] in = byteStream.toByteArray();
            return in;
        } finally {
            if (fis != null)
                fis.close();
            if (byteStream != null)
                byteStream.close();
        }
    }

    public static void formatSortAsc(List<Integer> list) {
        if (list != null && !list.isEmpty()) {
            Collections.sort(list);
        }
    }

    public static String formatString(String str) {
        if (stringIsNullOrEmpty(str))
            return "";
        str = str.replaceAll("[\r\n\t]*", "");
        return str;
    }

    public static <T> List<T> formatUniq(List<T> list) {
        List<T> tempList = new ArrayList<T>();
        if (list != null && !list.isEmpty()) {
            if (list.size() > 0) {
                Map<T, T> hash = new TreeMap<T, T>();

                for (T i : list) {
                    if (!hash.containsKey(i)) {
                        hash.put(i, i);
                        tempList.add(i);
                    }
                }
            }
            // else {
            // tempList.addAll(list);
            // formatSortAsc(tempList);
            // int i = tempList.size() - 1;
            // int v = tempList.get(i);
            // i -= 1;
            // for (; i >= 0;) {
            // int temp = tempList.get(i);
            // if (v == temp)
            // tempList.remove(i);
            // else {
            // v = temp;
            // i--;
            // }
            // }
            // }
        }
        return tempList;
    }

    public static String getAppRoot() {
        if (appRoot == null) {
            String userDir = System.getProperty("user.dir");
            String webAppDir = System.getProperty("webapp.root");
            String config = "config";
            String d = null;
            if (d == null && isExist(userDir + File.separator + config))
                d = userDir + File.separator + config;
            if (d == null && isExist(webAppDir + File.separator + config))
                d = webAppDir + File.separator + config;

            if (d == null)
                d = getConfig(Utils.class, config);
            if (d == null)
                d = getConfig(Thread.currentThread().getContextClassLoader(), config);

            if (d == null)
                config = "conf";

            if (d == null && isExist(userDir + File.separator + config))
                d = userDir + File.separator + config;
            if (d == null && isExist(webAppDir + File.separator + config))
                d = webAppDir + File.separator + config;
            if (d == null)
                d = getConfig(Utils.class, config);
            if (d == null)
                d = getConfig(Thread.currentThread().getContextClassLoader(), config);

            if (d == null) {
                d = Utils.class.getResource("/").getPath();
                appConfigRoot = "config";
            } else
                appConfigRoot = config;

            File f = new File(d);
            appRoot = f.getParent();

            System.out.println("AppRoot(" + appConfigRoot + "): " + appRoot);
        }
        return appRoot;
    }

    private static String getConfig(Class c, String config) {
        URL url = c.getResource(File.separator + config);
        if (url == null)
            url = c.getResource("/");
        if (url != null) {
            String d = url.getPath();
            d = d.replace("\\", File.separator);
            d = d.replace("/", File.separator);

            if (File.separator.equals("\\") && d.startsWith(File.separator))
                d = d.substring(1);
            System.out.println("getConfig(Class," + config + "):" + d);
            if (d.endsWith(File.separator + config + File.separator) || d.endsWith(File.separator + config))
                return d;
        }
        return getConfig(c.getClassLoader(), config);
    }

    private static String getConfig(ClassLoader c, String config) {
        URL url = c.getResource("");
        if (url != null) {
            String d = url.getPath();
            d = d.replace("\\", File.separator);
            d = d.replace("/", File.separator);

            if (File.separator.equals("\\") && d.startsWith(File.separator))
                d = d.substring(1);
            System.out.println("getConfig(Class," + config + "):" + d);
            if (d.endsWith(File.separator + config + File.separator) || d.endsWith(File.separator + config))
                return d;
        }
        return null;
    }

    public static String getConfigFile(String filename) {
        return getAppRoot() + File.separator + appConfigRoot + File.separator + filename;
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static long getCurrentTime() {
        return Calendar.getInstance().getTime().getTime();
    }

    public static java.util.Date getDefaultDate() {
        return DEFAULT_DATE;
    }

    public static long getDefaultDateLong() {
        return DEFAULT_DATE_LONG_1800_01_01;
    }

    public static String getFileExtName(String file) {
        if (Utils.stringIsNullOrEmpty(file))
            return ".";

        file = file.replace(File.separator, "/");
        String[] fs = file.split("/");

        if (fs != null && fs.length > 0)
            file = fs[fs.length - 1];

        int pos = file.lastIndexOf(".");
        if (pos >= 0)
            return file.substring(pos);

        return ".";
    }

    public static String getFileName(String path) {
        File f = new File(path);
        return f.getName();
    }

    public static String getJsonParam(String name, String message) {
        if (Utils.stringIsNullOrEmpty(name))
            return null;

        // {('([a-z]+)':'([^:']*)')(,'([a-z]+)':'([^:']*)')*}
        Matcher matcher = patternParam.matcher(message);
        while (matcher.find()) {
            String n = matcher.group(2);
            if (name.equalsIgnoreCase(n)) {
                return matcher.group(3);

            }
        }
        return null;
    }

    public static Properties getProperties(String filename) throws FileNotFoundException {
        Properties pro = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(Utils.getConfigFile(filename));
            pro.load(is);
            return pro;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getRandom(int maxValue) {
        return (int) (Math.random() * maxValue);
    }

    public static int getRandom(int minValue, int maxValue) {
        if (maxValue < minValue) {
            int tmp = maxValue;
            maxValue = minValue;
            minValue = tmp;
        }

        return minValue + (int) Math.round(Math.random() * (maxValue - minValue));
    }

    public static int getRandom(int minValue, int maxValue, int exclude) {
        int id = getRandom(minValue, maxValue);
        if (id == exclude) {
            if (id == maxValue)
                id = minValue + 1;
            else
                id = id + 1;
        }
        return id;
    }

    public static String getRMIHost(String rmi) {
        java.util.regex.Matcher m = regRMIConfig.matcher(rmi);
        if (m.find())
            return m.group(1);
        return null;
    }

    public static String getRMIName(String rmi) {
        java.util.regex.Matcher m = regRMIConfig.matcher(rmi);
        if (m.find())
            return m.group(3);
        return null;
    }

    public static int getRMIPort(String rmi) {
        java.util.regex.Matcher m = regRMIConfig.matcher(rmi);
        if (m.find())
            return Integer.valueOf(m.group(2));
        return 1099;
    }

    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static long getTimeStamp() {
        Date d = new Date();
        return d.getTime();
    }

    public static void initLog4j() {
        Properties pro;
        try {
            pro = getProperties("log4j.properties");
            if (pro != null) {
                String key = "log4j.appender.R.File";
                String file = String.valueOf(pro.get(key));
                if (!Utils.stringIsNullOrEmpty(file)) {
                    file = file.replace("../logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    System.out.println("******log4j.appender.R.File*****"+file);
                    file = file.replace("./logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    pro.setProperty(key, file);
                }
                String key2 = "log4j.appender.D.File";
                String file2 = String.valueOf(pro.get(key2));
                if (!Utils.stringIsNullOrEmpty(file2)) {
                    file2 = file2.replace("../logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    file2 = file2.replace("./logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    pro.setProperty(key2, file2);
                }

                String key3 = "log4j.appender.E.File";
                String file3 = String.valueOf(pro.get(key3));
                if (!Utils.stringIsNullOrEmpty(file3)) {
                    file3 = file3.replace("../logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    file3 = file3.replace("./logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    pro.setProperty(key3, file3);
                }

                String key4 = "log4j.appender.F.File";
                String file4 = String.valueOf(pro.get(key4));
                if (!Utils.stringIsNullOrEmpty(file4)) {
                    file4 = file4.replace("../logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    file4 = file4.replace("./logs/", Utils.getAppRoot() + File.separator + "logs" + File.separator);
                    pro.setProperty(key4, file4);
                }

                PropertyConfigurator.configure(pro);
                Logger.getLogger("Util").info("logfile:" + file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEmail(String email) {
        if (Utils.stringIsNullOrEmpty(email))
            return false;
        java.util.regex.Matcher m = regEmail.matcher(email);
        return m.matches();
    }

    public static boolean isExist(String file) {
        File f = new File(file);
        return f.isFile() || f.isDirectory();
    }

    public static boolean isMobilePhone(String phone) {
        if (Utils.stringIsNullOrEmpty(phone))
            return false;
        java.util.regex.Matcher m = regMobilePhone.matcher(phone);
        return m.matches();
    }

    public static void mkdirs(String path) {
        Utils.mkdirs(path, false);
    }

    public static void mkdirs(String path, boolean overwrite) {
        File file = new File(path);
        if (!file.isDirectory())
            file = new File(file.getParent());

        if (!file.exists()) {
            file.mkdirs();
        } else {
            if (overwrite) {
                file.delete();
                file.mkdirs();
            }
        }
    }

    public static Object objectRead(byte[] bs) throws IOException, ClassNotFoundException {
        ByteArrayInputStream stream = null;
        ObjectInputStream ois = null;

        try {
            stream = new ByteArrayInputStream(bs);
            ois = new ObjectInputStream(stream);
            return ois.readObject();

        } finally {
            if (ois != null)
                ois.close();
            if (stream != null)
                stream.close();
        }
    }

    public static byte[] objectWrite(Object obj) throws IOException {
        ByteArrayOutputStream stream = null;
        ObjectOutputStream oos = null;
        byte[] bs = null;
        try {
            stream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(stream);
            oos.writeObject(obj);
            bs = stream.toByteArray();
        } finally {
            if (oos != null)
                oos.close();
            if (stream != null)
                stream.close();
        }
        return bs;
    }

    public static int page(int max, int rowsOfPage) {
        if (rowsOfPage < 1)
            rowsOfPage = 10;

        return (Integer) (max / rowsOfPage) + ((max % rowsOfPage > 0) ? 1 : 0);
    }

    public static <T> List<T> page(List<T> list, int page, int rowsOfPage) {
        List<T> result = new ArrayList<T>();
        if (list == null)
            return result;

        int pos = page * rowsOfPage;
        int end = pos + rowsOfPage;
        if (end > list.size())
            end = list.size();

        for (; pos < end; pos++)
            result.add(list.get(pos));
        return result;
    }

    public static java.util.Date parseDate(String date, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.parse(date);
    }

    public static java.util.Date parseDate(String date) throws ParseException {
        return parseDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static void printStack() {
        printStack("Stack");
    }

    public static void printStack(String message) {
        try {
            throw new Exception();
        } catch (Exception ex) {
            Logger.getLogger("Stack").info(message, ex);
        }
    }

    public static void setAppRoot(String approot) {
        System.out.println("setAppRoot:" + approot);
        String config = "config";
        String d = approot + File.separator + config;
        if (isExist(d)) {
            appRoot = approot;
            appConfigRoot = config;
            return;
        }

        config = "conf";
        d = approot + File.separator + config;
        if (isExist(d)) {
            appRoot = approot;
            appConfigRoot = config;
        }

        initLog4j();

    }

    public static <T> void setMap(TreeMap<T, T> map, List<T> list) {
        if (map == null)
            map = new TreeMap<T, T>();

        if (list != null && !list.isEmpty()) {
            for (T item : list)
                map.put(item, item);
        }
    }

    public static boolean stringCompare(String string1, String string2) {
        if (string1 == null) {
            if (string2 == null)
                return true;
            else
                return false;
        }

        return string1.equalsIgnoreCase(string2);
    }

    public static String stringConcat(String split, String... args) {
        if (args == null)
            return null;

        if (args.length == 1)
            return args[0];

        String s = "";
        for (int i = 0; i < args.length; i++) {
            String item = args[i];
            if (i == 0 || item.startsWith(split))
                s = s.concat(item);
            else
                s = s.concat(split + item);
        }
        return s;
    }

    public static boolean stringIsNullOrEmpty(String string) {
        return string == null || string.trim().length() < 1;
    }

    public static String stringReplaceStartChar(String sourceString, String startChar) {
        if (!stringIsNullOrEmpty(sourceString) && !stringIsNullOrEmpty(startChar)) {
            int length = startChar.length();
            while (sourceString.startsWith(startChar)) {
                sourceString = sourceString.substring(length);
            }
        }

        return sourceString;
    }

    public static boolean stringStartWith(String string, String prefix) {
        if (stringIsNullOrEmpty(string) || stringIsNullOrEmpty(prefix))
            return false;

        return string.startsWith(prefix);
    }

    public static boolean stringStartWithIgnoreCase(String string, String prefix) {
        if (stringIsNullOrEmpty(string) || stringIsNullOrEmpty(prefix))
            return false;

        if (string.length() < prefix.length())
            return false;

        String temp = string.substring(0, prefix.length());
        return temp.equalsIgnoreCase(prefix);
    }

    public static String stringSubString(String str, int length) {
        if (str == null)
            return null;
        if (str.length() > length)
            return str.substring(0, length);
        else
            return str;
    }

    public static int[] toArrayInt(List<Integer> list) {

        if (list != null) {
            int[] l = new int[list.size()];
            int i = 0;
            for (int v : list) {
                l[i++] = v;
            }
            return l;
        } else
            return null;
    }

    public static int[] toArrayInt(String string) {
        List<Integer> list = toListInteger(string);
        return toArrayInt(list);
    }

    public static Integer[] toArrayInteger(List<Integer> list) {
        if (list != null)
            return (Integer[]) list.toArray(new Integer[list.size()]);
        else
            return null;
    }

    public static String[] toArrayString(List<String> list) {
        if (list != null) {
            return list.toArray(new String[list.size()]);
        }
        return null;
    }

    public static String[] toArrayString(String string) {
        if (Utils.stringIsNullOrEmpty(string))
            return null;
        return string.split(",");
    }

    public static byte[] toBase64DecodeByte(String code) throws Base64DecodingException {
        return Base64.decode(code);
    }

    public static ByteInputStream toBase64DecodeStream(String code) throws Base64DecodingException {
        if (Utils.stringIsNullOrEmpty(code))
            return null;
        byte[] b = Utils.toBase64DecodeByte(code);
        if (b == null)
            return null;
        ByteInputStream is = new ByteInputStream();
        is.setBuf(b);
        return is;
    }

    public static String toBase64DecodeString(String code) throws Base64DecodingException, UnsupportedEncodingException {
        byte[] bytes = Utils.toBase64DecodeByte(code);
        if (bytes == null)
            return null;
        return new String(bytes, "UTF-8");
    }

    public static String toBase64Encode(byte[] bytes) throws IOException {
        if (bytes == null)
            return null;
        return Base64.encode(bytes);
    }

    public static String toBase64Encode(InputStream stream) throws IOException {
        ByteOutputStream bos = Utils.toOutputStream(stream);
        if (bos != null)
            try {
                return Base64.encode(bos.getBytes());
            } finally {
                bos.close();
            }
        else
            return null;
    }

    public static String toBase64Encode(String str) throws IOException {
        if (Utils.stringIsNullOrEmpty(str))
            return null;
        byte[] bytes = str.getBytes("UTF-8");
        return Utils.toBase64Encode(bytes);
    }

    public static byte[] toByte(String str) throws UnsupportedEncodingException {
        if (Utils.stringIsNullOrEmpty(str))
            return null;
        return str.getBytes("UTF-8");
    }

    public static String toCRC(String hexword) {
        int l = 0;
        for (byte ch : hexword.getBytes()) {
            l += ch;
        }
        return String.format("%1$x", l % 16);
    }

    public static long toDateLong(java.sql.Date date) {
        return date.getTime();
    }

    public static long toDateLong(String date, String format) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.parse(date).getTime();
    }

    public static long toDateLong(Timestamp timestamp) {
        return timestamp.getTime();
    }

    public static String toDateString(Date date, String format) {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    public static String toDateString(long date, String format) {
        return toDateString(toUtilDate(date), format);
    }

    public static Map<Integer, Integer> toDecrease(Map<Integer, Integer> a, Map<Integer, Integer> b) {
        Set<Integer> keys = b.keySet();
        for (Integer key : keys) {
            if (a.containsKey(key))
                a.remove(key);
        }
        return a;
    }

    public static String toFileName(long id, int random) throws NoSuchAlgorithmException {
        String filename = String.format("%1$x", id);
        if (random < 0)
            random = Utils.getRandom(100000, 999999);
        String md5 = encryptMD5(random + ":" + String.valueOf(id));
        int size = 16 - 1 - filename.length();
        if (size > 0) {
            filename = md5.substring(1, size) + "h" + filename;
        }

        filename += toCRC(filename);
        return filename;
    }

    public static long getFileId(String sid, int defaultValue) {
        Matcher m = regFileCRC.matcher(sid);
        if (m.find()) {
            return Integer.valueOf(m.group(1), 16);
        }
        return defaultValue;
    }

    public static int getFileCRC(String sid, int defaultValue) {
        Matcher m = regFileCRC.matcher(sid);
        if (m.find()) {
            return Integer.valueOf(m.group(2), 16);
        }
        return defaultValue;
    }

    public static String toFileName(long id) {
        int random = (int) ((id + 177) % 999999);
        try {
            return toFileName(id, random);
        } catch (NoSuchAlgorithmException e) {
            logger.error(null, e);
        }
        return null;
    }

    public static int toInt(Object data, int defaultValue) {
        try {
            if (data == null)
                return defaultValue;
            return Integer.valueOf(String.valueOf(data));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int toInt(String data, int defaultValue) {
        try {
            return Integer.valueOf(data);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String toJson(String name, String value) {
        value = value.replace("'", "");
        value = value.replace("\"", "");
        return "{'" + name + "':'" + value + "'}";
    }

    public static <T> List<T> toList(T[] array) {
        List<T> list = new ArrayList<T>();
        if (array != null) {
            for (T v : array) {
                list.add(v);
            }
        }
        return list;
    }

    public static List<Integer> toListInteger(int[] array) {
        List<Integer> list = new ArrayList<Integer>();
        if (array != null) {
            for (int i = 0; i < array.length; i++)
                list.add(array[i]);
        }
        return list;
    }

    public static List<Integer> toListInteger(String string) {
        List<Integer> list = new ArrayList<Integer>();
        if (!Utils.stringIsNullOrEmpty(string)) {
            String[] lines = string.split(",");
            for (String item : lines) {
                list.add(Integer.valueOf(item));
            }
        }
        return list;
    }

    public static long toLong(Object data, long defaultValue) {
        try {
            if (data == null)
                return defaultValue;
            return Long.valueOf(String.valueOf(data));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toLong(String data, long defaultValue) {
        try {
            return Long.valueOf(data);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static ByteOutputStream toOutputStream(InputStream stream) throws IOException {
        if (stream == null)
            return null;
        ByteOutputStream bos = new ByteOutputStream();
        bos.write(stream);
        return bos;
    }

    public static String toPath(String separator, long id) {
        String data = String.format("%1$,d", id / 1000).replace(",", "/");
        for (int i = 12 - data.length() - 1; i >= 0; i--) {
            if (i % 4 == 0)
                data = separator + data;
            else
                data = "0" + data;

        }
        return data;
    }

    public static String toPath(long id) {
        return toPath(File.separator, id);
    }

    public static String toPath(String[] s) {
        if (s == null)
            return "";
        return toPath(s, 0, s.length);
    }

    public static String toPath(String[] s, int start) {
        if (s == null)
            return "";
        return toPath(s, start, s.length - start);
    }

    public static String toPath(String[] s, int start, int length) {
        if (s == null)
            return "";

        String p = "";
        int e = start + length;
        for (; start < e && start < s.length; start++) {
            p += File.separator + s[start];
        }
        return p;
    }

    public static java.sql.Date toSqlDate(java.util.Date date) {
        if (date == null) {
            date = getDefaultDate();
        }
        return new java.sql.Date(date.getTime());
    }

    public static java.sql.Date toSqlDate(long stamp) {
        if (stamp < DEFAULT_DATE_LONG_1800_01_01) {
            stamp = DEFAULT_DATE_LONG_1800_01_01;
        }
        return new java.sql.Date(stamp);
    }

    public static String toString(char split, Object[] objs) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            sb.append(split + String.valueOf(obj));
        }
        if (sb.toString().length() > 1)
            return sb.substring(1);
        else
            return "";
    }

    public static String toString(int[] array) {
        if (array == null)
            return null;

        String str = "";
        for (int item : array) {
            str += "," + item;
        }
        if (str.length() > 0)
            return str.substring(1);
        else
            return null;
    }

    public static <T> String toString(List<T> list) {
        return Utils.toString(',', list);
    }

    public static <T> String toString(char split, List<T> list) {
        if (list == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (T item : list) {
            sb.append(split + item.toString());
        }

        if (sb.length() > 0)
            return sb.substring(1);
        return "-1";
    }

    public static <T1, T2> String toString(char split, Map<T1, T2> map) {
        StringBuilder sb = new StringBuilder();
        Set<T1> keys = map.keySet();
        List<String> list = new ArrayList<String>();
        for (T1 key : keys) {
            T2 v = map.get(key);
            if (v != null)
                list.add(key.toString() + "=" + v.toString());
        }

        Collections.sort(list);
        for (String item : list) {
            sb.append(split + item);
        }

        if (sb.length() > 0)
            return sb.substring(1);
        return "";
    }

    public static String toString(String[] array) {
        if (array == null)
            return "";
        return toString(',', array);
    }

    public static String toString_AnonymousObject(char split, Object... objs) {
        return toString(split, objs);
    }

    public static java.sql.Timestamp toTimestamp(java.util.Date date) {
        java.sql.Timestamp s = new java.sql.Timestamp(date.getTime());
        return s;
    }

    public static Timestamp toTimestamp(long stamp) {
        return new Timestamp(stamp);
    }

    public static java.util.Date toUtilDate(java.sql.Date date) {
        if (date.getTime() < getDefaultDateLong())
            return getDefaultDate();
        return new java.util.Date(date.getTime());
    }

    public static java.util.Date toUtilDate(java.sql.Timestamp stamp) {
        if (stamp == null) {
            return null;
        }
        if (stamp.getTime() < getDefaultDateLong())
            return getDefaultDate();
        return new java.util.Date(stamp.getTime());
    }

    public static java.util.Date toUtilDate(long stamp) {
        if (stamp < getDefaultDateLong())
            return getDefaultDate();
        return new java.util.Date(stamp);
    }

    public static void writeToFile(String file, String data) throws IOException {
        File f = new File(file);
        mkdirs(file);
        if (!f.exists()) {
            mkdirs(file);
            f.createNewFile();
        }

        RandomAccessFile fil = null;
        try {
            fil = new RandomAccessFile(f, "rw");
            long fileLength = fil.length();
            fil.seek(fileLength);
            fil.write(data.getBytes("UTF-8"));
        } finally {
            if (fil != null)
                fil.close();
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourceFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        // 新建文件输出流并对它进行缓冲
        FileOutputStream output = new FileOutputStream(targetFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();

        // 关闭流
        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + File.separator + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + File.separator + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 生成随机码
     * 
     * @param codeNumber
     *            随机码的位数
     * @return
     * @Author qintao
     */
    @Deprecated
    public static String generateValidCode(int codeNumber) {
        int rdm = (int) Math.floor(Math.random() * Integer.parseInt(Integer.toBinaryString(1 << codeNumber)));
        String result = String.valueOf(rdm);
        if (result.length() > codeNumber) {
            result = result.substring(0, codeNumber);
        }
        if (result.length() < codeNumber) {
            int sub = codeNumber - result.length();
            for (int i = 0; i < sub; i++) {
                result = result + "0";
            }
        }
        return result;
    }

    public static String encryptForDES(String souce, String key) throws Exception {
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
        return new BASE64Encoder().encode(encryptedData);
    }

    public static boolean isToday(long stamp) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        long n = now.getTime().getTime();
        return stamp >= n && stamp < n + 86400000;

    }

    public static boolean isToday(Date date) {
        return isToday(date.getTime());
    }

    public static InetAddress getUnixLocalIP(String host_address_regex) throws SocketException {
        Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
        Pattern regex = null;
        if (!Utils.stringIsNullOrEmpty(host_address_regex))
            regex = Pattern.compile(host_address_regex);
        InetAddress ip = null;
        while (ifs.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) ifs.nextElement();
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (regex != null) {
                    Matcher m = regex.matcher(ip.getHostAddress());
                    if (m.find())
                        return ip;
                } else {
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        return ip;
                    }
                }
                ip = null;
            }
        }
        return null;
    }

    /**
     * 判断是否电信手机号码
     * 
     * @param mobilePhone
     * @return
     * @Author qintao
     */
    public static boolean isTelecomMobilePhone(String mobilePhone) {
        String regExp = "^1(8[09]|[35]3)\\d{8}$|^177095\\d{5}$|^170095[0-5]\\d{4}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobilePhone);
        return m.find();// boolean
    }

    /**
     * 根据生日获得年龄
     * 
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static boolean toBoolean(String val, boolean def) {
        try {
            if (val == null) {
                return def;
            }
            return Boolean.parseBoolean(val);
        } catch (Exception e) {
            return def;
        }

    }

    @Deprecated
    public static String getFileMD5String(String fileName) {
        File f = new File(fileName);
        return getFileMD5String(f);
    }

    /**
     * 计算文件的MD5，重载方法
     * 
     * @param file
     *            文件对象
     * @return
     * @throws IOException
     */
    @Deprecated
    private static String getFileMD5String(File file) {
        FileInputStream in = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messageDigest.update(byteBuffer);
            return bufferToHex(messageDigest.digest());
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 对文件全文生成MD5摘要
     * 
     * @param file
     *            要加密的文件
     * @return MD5摘要码
     */
    public static String encryptFileMD5(String fileName) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(new File(fileName));
            byte[] buffer = new byte[2048];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] b = md.digest();
            return byteToHexString(b);
            // 16位加密
            // return buf.toString().substring(8, 24);
        } catch (Exception ex) {
            logger.error(Utils.class,ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.error(Utils.class,ex);
            }
        }
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     * 
     * @param tmp
     *            要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }
    
    public static Object clone(Object obj) {
        try {
            if (obj == null)
                return null;
            byte[] bs = Utils.objectWrite(obj);
            return Utils.objectRead(bs);
        } catch (Exception ex) {
            logger.error("clone", ex);
        }
        return null;
    }

    // // 判断某个时间是否在本周内（本周从周一开始周日结束）
    public static boolean isSameWeekCn(long timeMillis) throws java.text.ParseException {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 每周从周一开始
        cd.setFirstDayOfWeek(Calendar.MONDAY);
        // 本周开始时间
        cd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String str1 = sdf.format(cd.getTime()) + " 00:00:00";
        // 本周结束时间
        cd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String str2 = sdf.format(cd.getTime()) + " 23:59:59";
        // 转换为时间戳
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startTime = sdf1.parse(str1).getTime();
        long endTime = sdf1.parse(str2).getTime();
        if (timeMillis >= startTime && timeMillis <= endTime) {
            return true;
        }
        return false;
    }

    public static boolean isSameWeek(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(new java.util.Date(timeMillis));
        // System.out.println(date);

        String date1 = sdf.format(new java.util.Date(System.currentTimeMillis()));
        // System.out.println(date1);

        java.util.Date d1 = null;
        java.util.Date d2 = null;
        try {
            d1 = sdf.parse(date);
            d2 = sdf.parse(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(1) - cal2.get(1);

        if (subYear == 0) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }

        } else if ((subYear == 1) && (cal2.get(2) == 11)) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if ((subYear == -1) && (cal1.get(2) == 11) && (cal1.get(3) == cal2.get(3))) {
            return true;
        }

        return false;
    }
    
    /**
     * 数据格式校验
     * @param regEx 校验正则
     * @param value 待校验数据
     * @return
     */
    public static boolean isValidFormat(String regEx, String value) {
    	if (Utils.stringIsNullOrEmpty(regEx) || Utils.stringIsNullOrEmpty(value)){
    		return false;
    	}
    	Pattern reg = Pattern.compile(regEx);
        java.util.regex.Matcher m = reg.matcher(value);
        return m.matches();
    }
    
    public static byte[][] encodeMany(final Object... strs) throws IOException{
        byte[][] many = new byte[strs.length][];
        for(int i=0;i<strs.length;i++){
            many[i] = objectWrite(strs[i]);
        }
        return many;
    }
}
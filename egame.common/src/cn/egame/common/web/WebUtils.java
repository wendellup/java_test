package cn.egame.common.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.util.Utils;

public class WebUtils {
	public enum HeaderProperty {
		Content_Type("Content-Type"), Connection("Connection"), User_Agent(
				"User-Agent"), Accept_Language("Accept-Language"), Refer(
				"Refer"), Cookie("Cookie"), Response("Response"),Host("Host"),req_log("req_log");

		private String value;

		private static TreeMap<Integer, HeaderProperty> _map;

		static {
			_map = new TreeMap<Integer, HeaderProperty>();
			int i = 0;
			for (HeaderProperty num : HeaderProperty.values()) {
				_map.put(new Integer(i++), num);
			}
		}

		public static HeaderProperty lookup(int value) {
			return _map.get(new Integer(value));
		}

		HeaderProperty(String value) {
			this.value = value;
		}

		public String value() {
			return this.value;
		}

		public String toString() {
			return value();
		}
	}

	static Logger logger = Logger.getLogger(WebUtils.class.getSimpleName());

	private static String getParams(HashMap<String, String> params,
			String charset) throws UnsupportedEncodingException {
		if (params != null) {
			StringBuilder sb = new StringBuilder();
			Set<String> keys = params.keySet();
			for (String key : keys) {
				sb.append( key + "="
						+ java.net.URLEncoder.encode(params.get(key), charset)+"&");
			}
			return sb.toString();
		}
		return null;
	}

	public static String uploadFile(String actionUrl, File file, String postFileName)
			throws ExceptionCommonBase {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		// String newName = "image.jpg";
		/* 设置DataOutputStream */
		DataOutputStream ds = null;
		FileInputStream fStream = null;
		HttpURLConnection con = null;
		StringBuffer b = null;
		try {
			URL url = new URL(actionUrl);
			con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + file.getName() + "\""
					+ end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			fStream = new FileInputStream(file);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
		} catch (Exception e) {
			throw ExceptionCommonBase.throwExceptionCommonBase(e);
		} finally {
			/* close streams */
			if (fStream != null) {
				try {
					fStream.close();
				} catch (IOException e) {
					throw ExceptionCommonBase.throwExceptionCommonBase(e);
				}
			}
			if (ds != null) {
				try {
					ds.flush();
					ds.close();
				} catch (IOException e) {
					throw ExceptionCommonBase.throwExceptionCommonBase(e);
				}
			}
		}

		if (b != null) {
			return b.toString();
		}
		return null;
	}
	
	
	public static String http(String urlStr, String method, String charset,
			HashMap<HeaderProperty, String> header,
			HashMap<String, String> params,
			HashMap<HeaderProperty, String> response)
			throws ExceptionCommonBase {

		if (Utils.stringIsNullOrEmpty(method))
			method = "POST";
		if (Utils.stringIsNullOrEmpty(charset))
			charset = "utf-8";

		HttpURLConnection conn = null;
		BufferedReader in = null;
		try {
			String data = getParams(params, charset);
			URL url = new URL(urlStr);
			if("GET".equalsIgnoreCase(method) &&!Utils.stringIsNullOrEmpty(data)){
				url = new URL(urlStr+"?"+data);
			}
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestMethod(method);

			if (header == null) {
				header = new HashMap<HeaderProperty, String>();
			} else
				header.put(HeaderProperty.Content_Type,
						"application/x-www-form-urlencoded; charset=" + charset);
			setRequestProperty(conn, header);

			conn.setUseCaches(false);

			
			if ("POST".equalsIgnoreCase(method) && !Utils.stringIsNullOrEmpty(data)) {
				conn.setDoOutput(true);
				int dataLength = data.getBytes().length;
				conn.setRequestProperty("Content-Length",
						String.valueOf(dataLength));
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), charset);
				out.write(data);
				out.flush();
				out.close();
			}

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ExceptionWeb("HttpStatus:" + conn.getResponseCode());
			}

			if (response != null) {
				String key = null;
				String cookieVal = null;
				String sessionId = "";
				for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
					// logger.info(key);
					if (key.equalsIgnoreCase("set-cookie")) {
						cookieVal = conn.getHeaderField(i);
						cookieVal = cookieVal.substring(0,
								cookieVal.indexOf(";"));
						sessionId = sessionId + cookieVal + ";";
					}
				}

				if (!Utils.stringIsNullOrEmpty(sessionId)) {
					response.put(HeaderProperty.Cookie, sessionId);
				}
			}

			String line = null;
			StringBuilder result = new StringBuilder();
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			while ((line = in.readLine()) != null) {
				result.append(line + "\r\n");
			}

			return result.toString();

		} catch (Exception ex) {
			throw ExceptionCommonBase.throwExceptionCommonBase(ex);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				conn.disconnect();
		}
	}

	public static ByteArrayOutputStream httpStreamNew(String urlStr,
			String method, String charset,
			HashMap<HeaderProperty, String> header,
			HashMap<String, String> params,
			HashMap<String, String> response)
			throws ExceptionCommonBase {
		if (Utils.stringIsNullOrEmpty(method))
			method = "POST";
		if (Utils.stringIsNullOrEmpty(charset))
			method = "utf-8";

		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestMethod(method);
			// conn.setInstanceFollowRedirects(true);

			if (header == null) {
				header = new HashMap<HeaderProperty, String>();
			} else
				header.put(HeaderProperty.Content_Type,
						"application/x-www-form-urlencoded; charset=" + charset);
			setRequestProperty(conn, header);

			conn.setUseCaches(false);

			String data = getParams(params, charset);
			if (!Utils.stringIsNullOrEmpty(data)) {
				conn.setDoOutput(true);
				int dataLength = data.getBytes().length;
				conn.setRequestProperty("Content-Length",
						String.valueOf(dataLength));
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), charset);
				out.write(data);
				out.flush();
				out.close();
			}

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ExceptionWeb("HttpStatus:" + conn.getResponseCode()
						+ " url=" + url);
			}

			if (response != null) {
				/*
				String key = null;
				String cookieVal = null;
				String sessionId = "";
				for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
					if (key.equalsIgnoreCase("set-cookie")) {
						cookieVal = conn.getHeaderField(i);
						cookieVal = cookieVal.substring(0,
								cookieVal.indexOf(";"));
						sessionId = sessionId + cookieVal + ";";
					}
				}

				if (!Utils.stringIsNullOrEmpty(sessionId)) {
					response.put(HeaderProperty.Cookie.value, sessionId);
				}
				
				*/
				Map<String,List<String>> retHeadFields = conn.getHeaderFields();
				if(retHeadFields!=null){
					Set<String> keySet = retHeadFields.keySet();
					for(String strKey : keySet){
						List<String> valList = retHeadFields.get(strKey);
						String valStrAll = "";
						for(String valStr : valList){
							valStrAll += valStr;
						}
						response.put(strKey, valStrAll);
					}
				}
			}

			is = conn.getInputStream();
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			int readLength = -1;
			int bufferSize = 1024 * 256;
			byte bytes[] = new byte[bufferSize];
			while ((readLength = is.read(bytes, 0, bufferSize)) != -1) {
				byteStream.write(bytes, 0, readLength);
				Thread.sleep(20);
			}

			return byteStream;
		} catch (Exception ex) {
			throw ExceptionCommonBase.throwExceptionCommonBase(ex);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				conn.disconnect();
		}
	}
	
	public static ByteArrayOutputStream httpStream(String urlStr,
			String method, String charset,
			HashMap<HeaderProperty, String> header,
			HashMap<String, String> params,
			HashMap<HeaderProperty, String> response)
			throws ExceptionCommonBase {

		if (Utils.stringIsNullOrEmpty(method))
			method = "POST";
		if (Utils.stringIsNullOrEmpty(charset))
			method = "utf-8";

		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestMethod(method);
			// conn.setInstanceFollowRedirects(true);

			if (header == null) {
				header = new HashMap<HeaderProperty, String>();
			} else
				header.put(HeaderProperty.Content_Type,
						"application/x-www-form-urlencoded; charset=" + charset);
			setRequestProperty(conn, header);

			conn.setUseCaches(false);

			String data = getParams(params, charset);
			if (!Utils.stringIsNullOrEmpty(data)) {
				conn.setDoOutput(true);
				int dataLength = data.getBytes().length;
				conn.setRequestProperty("Content-Length",
						String.valueOf(dataLength));
				OutputStreamWriter out = new OutputStreamWriter(
						conn.getOutputStream(), charset);
				out.write(data);
				out.flush();
				out.close();
			}

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new ExceptionWeb("HttpStatus:" + conn.getResponseCode()
						+ " url=" + url);
			}

			if (response != null) {
				String key = null;
				String cookieVal = null;
				String sessionId = "";
				for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
					if (key.equalsIgnoreCase("set-cookie")) {
						cookieVal = conn.getHeaderField(i);
						cookieVal = cookieVal.substring(0,
								cookieVal.indexOf(";"));
						sessionId = sessionId + cookieVal + ";";
					}
				}

				if (!Utils.stringIsNullOrEmpty(sessionId)) {
					response.put(HeaderProperty.Cookie, sessionId);
				}
			}

			is = conn.getInputStream();
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			int readLength = -1;
			int bufferSize = 1024 * 256;
			byte bytes[] = new byte[bufferSize];
			while ((readLength = is.read(bytes, 0, bufferSize)) != -1) {
				byteStream.write(bytes, 0, readLength);
				Thread.sleep(20);
			}

			return byteStream;
		} catch (Exception ex) {
			throw ExceptionCommonBase.throwExceptionCommonBase(ex);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				conn.disconnect();
		}
	}

	public static void main(String[] args) {

	}

	private static void setRequestProperty(HttpURLConnection conn,
			HashMap<HeaderProperty, String> header) {
		if (header == null) {
			conn.setRequestProperty(HeaderProperty.Content_Type.value(),
					"application/x-www-form-urlencoded; charset=utf-8");
			conn.setRequestProperty(HeaderProperty.Connection.value(),
					"Keep-Alive");
			conn.setRequestProperty(HeaderProperty.User_Agent.value(),
					"Mozilla/5.0 (Windows NT 5.1; rv:14.0) Gecko/20100101 Firefox/14.0.1");
			conn.setRequestProperty(HeaderProperty.Accept_Language.value(),
					"zh-CN");
		} else {

			Set<HeaderProperty> keys = header.keySet();
			for (HeaderProperty key : keys)
				conn.setRequestProperty(key.value(), header.get(key));

			if (!header.containsKey(HeaderProperty.Content_Type))
				conn.setRequestProperty(HeaderProperty.Content_Type.value(),
						"application/x-www-form-urlencoded; charset=utf-8");

			if (!header.containsKey(HeaderProperty.Connection))
				conn.setRequestProperty(HeaderProperty.Connection.value,
						"Keep-Alive");

			if (!header.containsKey(HeaderProperty.Accept_Language))
				conn.setRequestProperty(HeaderProperty.Accept_Language.value(),
						"zh-CN");

		}
	}

	public static String urlEncode(String str, String code) {
		try {
			return java.net.URLEncoder.encode(str, code);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return null;
	}

	public static String urlDecode(String str, String code) {
		try {
			return java.net.URLDecoder.decode(str, code);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		return null;
	}

	public static String urlEncode(String str) {
		return urlEncode(str, "utf-8");
	}

	public static String urlDecode(String str) {
		return urlDecode(str, "utf-8");
	}
}
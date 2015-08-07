package cn.egame.common.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.egame.common.util.Utils;

public class Language {
	public final static Pattern regCulture = Pattern
			.compile(".([A-Za-z]+-[A-Za-z]+)(\\.|)");
	public final static Pattern regParamName = Pattern
			.compile("^([A-Za-z0-9\\._-]+)=(.*)");
	private static Language instance = null;
	private static byte[] SyncRoot = new byte[0];
	private Map<String, String> hash = new TreeMap<String, String>();
	public static String DefaultUICulture = "zh-CN";
	public static Language getInstance() {
		if (instance == null)
			synchronized (SyncRoot) {
				if (instance == null) {
					// Locale.getDefault();
					Language temp = new Language();
					temp.reload();
					instance = temp;
				}
			}

		return instance;
	}

	private Logger logger = Logger.getLogger(Language.class.getSimpleName());

	public String getString(String key) {
		return getString(key, DefaultUICulture, null);
	}

	public String getString(String key, String language) {
		return getString(key, language, null);
	}

	public String getString(String key, String language, String defaultValue) {
		if (Utils.stringIsNullOrEmpty(key))
			return "";
		else
			key = key.toLowerCase();
		if (Utils.stringIsNullOrEmpty(language))
			language = DefaultUICulture;
		else
			language = language.toLowerCase();

		String k = key + "." + language;

		if (hash.containsKey(k))
			return hash.get(k);
		else {
			if (!Utils.stringIsNullOrEmpty(defaultValue))
				return defaultValue;

			if (language.equalsIgnoreCase(DefaultUICulture))
				return "";

			return getString(key, DefaultUICulture, null);
		}
	}

	public void reload() {
		try {
			Map<String, String> temp = new TreeMap<String, String>();
			String path = Utils.getConfigFile("lang");
			File root = new File(path);
			if (root.exists()) {
				File[] files = root.listFiles();
				for (File file : files) {
					setLanguageString(file, temp);
				}
				hash = temp;
			}

		} catch (Exception ex) {

		}
	}

	private void setLanguageString(File file, Map<String, String> temp)
			throws IOException {
		if (file.isFile()
				&& file.toString().toLowerCase().endsWith(".properties")) {
			String filename = file.getName();
			Matcher matcher = regCulture.matcher(filename);
			String lang = DefaultUICulture;
			if (matcher.find())
				lang = matcher.group(1).toLowerCase();

			InputStreamReader insReader = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			BufferedReader in = new BufferedReader(insReader);
			String line = null;
			while ((line = in.readLine()) != null) {
				if (!Utils.stringIsNullOrEmpty(line)) {
					matcher = regParamName.matcher(line);
					if (matcher.find()) {
						String key = matcher.group(1).trim().toLowerCase();
						String value = matcher.group(2).trim();

						if (!regCulture.matcher(key).find())
							key = key + "." + lang;

						if (!hash.containsKey(key))
							temp.put(key, value);
						else
							logger.info("Ignore culture config(exist):\""
									+ line + "\"");
					} else
						logger.info("Ignore culture config:\"" + line + "\"");
				}
			}
		}
	}
}

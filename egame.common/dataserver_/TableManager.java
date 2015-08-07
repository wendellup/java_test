package cn.egame.common.dataserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.egame.common.util.DomUtil;

public class TableManager {
	private static Logger logger = Logger.getLogger(TableManager.class);

	private Map<String, Table> myTables = new HashMap<String, Table>();// tableName->Table
	
	public TableManager(String xmlPath) {
		try {
			Document doc = DomUtil.read(xmlPath);
			Element root = DomUtil.getRootElement(doc);
			List elements = root.elements();
			for (int i = 0; i < elements.size(); i++) {
				Element el = (Element) elements.get(i);
				String tableName = el.attributeValue("name").toLowerCase();
				String dbId = el.attributeValue("dbId");
				String dbName = el.attributeValue("dbName");
				String clz = el.attributeValue("className");
				
				Table table = new Table();
				table.setTableName(tableName);
				table.setDbId(Integer.parseInt(dbId));
				table.setClz(Class.forName(clz));
				myTables.put(tableName, table);
			}
		} catch (Exception ex) {
			logger.error("载入xml异常", ex);
		}
	}

	public Table getTable(String name) {
		return myTables.get(name);
	}
}

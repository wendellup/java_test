package test.open.serial;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SearilazizeTest implements Serializable{

	private static final long serialVersionUID = 5767426158258564918L;
	private static Logger logger = Logger.getLogger(SearilazizeTest.class);
	public static void main(String[] args){
		try {
			jsonObjectTrans();
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	public static void jsonObjectTrans(){
		ResultContentExt rce = new ResultContentExt();
		List<String> refHostStatus = new ArrayList<String>();
		refHostStatus.add("aaa");
		refHostStatus.add("侠盗飞");
		rce.setRef_hot_status(refHostStatus);
		List<String> refNewStatus = new ArrayList<String>();
		refNewStatus.add("bbbb");
		refNewStatus.add("cccc");
		rce.setRef_new_status(refNewStatus);
		
		rce.setTestInt(22);
		System.out.println(rce.getTestInt());
		JSONObject jsonArray = JSONObject.fromObject(rce);
		System.out.println(jsonArray);
		
        ResultContentExt packageVersionMaps = new ResultContentExt();
        try {
        	JSONObject jsonObject = JSONObject.fromObject(jsonArray);
        	packageVersionMaps  = (ResultContentExt) JSONObject.toBean(jsonObject,ResultContentExt.class);
        } catch (Exception e) {
        	logger.error("", e);
        }
        System.out.println(packageVersionMaps);
		
	}

	public static void mainS(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		ResultContentExt rce = new ResultContentExt();
		List<String> refHostStatus = new ArrayList<String>();
		refHostStatus.add("aaa");
		refHostStatus.add("侠盗飞");
		rce.setRef_hot_status(refHostStatus);
		List<String> refNewStatus = new ArrayList<String>();
		refNewStatus.add("bbbb");
		refNewStatus.add("cccc");
		rce.setRef_new_status(refNewStatus);
		
		rce.setTestInt(22);
		System.out.println(rce.getTestInt());
		ObjectMapper jsonMapper = new ObjectMapper();
		byte[] json = jsonMapper.writeValueAsBytes(rce);
		String jsonStr = new String(json);
		System.out.println(jsonStr);
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        ResultContentExt packageVersionMaps = new ResultContentExt();
        try {
            packageVersionMaps = mapper.readValue(jsonStr, ResultContentExt.class);
        } catch (Exception e) {
        	System.out.println(e);
        }
        System.out.println(packageVersionMaps);
	}
	
	public static void main3(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		BOO boo = new BOO();
		boo.setAooStr("aaaaaaa");
		boo.setBooStr("bbbbbbb");
		ObjectMapper jsonMapper = new ObjectMapper();
		byte[] json = jsonMapper.writeValueAsBytes(boo);
		String jsonStr = new String(json);
		System.out.println(jsonStr);
		
		ObjectMapper mapper = new ObjectMapper();
        BOO retBoo = new BOO();
        try {
        	retBoo = mapper.readValue(jsonStr, BOO.class);
        } catch (Exception e) {
        	System.out.println(e);
        }
        System.out.println(retBoo);
	}

	public static void mainx(String[] args) throws Exception {
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		BOO boo = new BOO();
		boo.setAooStr("aaaaaaa");
		boo.setBooStr("bbbbbbb");
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"person.txt"));
			oos.writeObject(boo);
			
			ois = new ObjectInputStream(new FileInputStream(
					"person.txt"));
			BOO bi = (BOO) ois.readObject();
			System.out.println(bi.getBooStr());
			System.out.println(bi.getAooStr());
			
		} catch (Exception e) {
		} finally{
			if(oos!=null){
				oos.close();
			}
			if(ois!=null){
				ois.close();
			}
		}
	}
	
	public static void mainT(String[] args) throws Exception {
		
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		ResultContentExt rce = new ResultContentExt();
		List<String> refHostStatus = new ArrayList<String>();
		refHostStatus.add("aaa");
		refHostStatus.add("侠盗飞");
		rce.setRef_hot_status(refHostStatus);
		List<String> refNewStatus = new ArrayList<String>();
		refNewStatus.add("bbbb");
		refNewStatus.add("cccc");
		rce.setRef_new_status(refNewStatus);
		rce.setTestInt(22);
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					"person.txt"));
			oos.writeObject(rce);
			
			ois = new ObjectInputStream(new FileInputStream(
					"person.txt"));
			ResultContentExt retObj = (ResultContentExt) ois.readObject();
			System.out.println(retObj.toString());
			
		} catch (Exception e) {
		} finally{
			if(oos!=null){
				oos.close();
			}
			if(ois!=null){
				ois.close();
			}
		}
	}
}

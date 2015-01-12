package test.open.str;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SortJsonElement {
	private static Map<String, String> convertJsonStrToMap(String jsonStr) throws JsonProcessingException, IOException{
		Map<String, String> map = new HashMap<String, String>();
		if(jsonStr==null){
			return map;
		}
		ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode jn = jsonMapper.readTree(jsonStr);
        
        if (jn.get("data") != null) {
            Iterator<String> it = jn.get("data").getFieldNames();
            while (it.hasNext()) {
            	String key = it.next();
            	JsonNode jn2 = jn.get("data").get(key);
                map.put(key, jn2.getValueAsText());
            }
        }
		return map;
	}
	
	private static String convertMapToSortString(Map<String, String> map){
		if(map==null){
			return "";
		}
		TreeMap<String, String> sortMap = new TreeMap<String, String>(map);
        StringBuilder sb = new StringBuilder("");
		Set<String> keySet = sortMap.keySet();
		for(String key : keySet){
			sb.append(key+"="+map.get(key));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		String jsonStr = "{\"data\":{\"syncEntity\":\"game,package,platform\",\"syncField\":\"game.id,game.name,game.cpId,game.typeId,package.downUrl,platform.logoImageUrl,platform.version\",\"syncType\":1,\"dateFrom\":\"20130101000000\",\"dateTo\":\"20140101000000\",\"pageSize\":\"100\",\"pageNum\":\"1\"}}";
		
		Map<String, String> map = convertJsonStrToMap(jsonStr);
//		
//		Set<String> keySet = map.keySet();
//		for(String key : keySet){
//			System.out.println(key+":"+map.get(key));
//		}
//		
		System.out.println(convertMapToSortString(map));
	}
}

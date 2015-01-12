package test.client.cache;


import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.util.Utils;

public class FlushLocalCacheTest{

	/**@param string 
	 * @Description: TODO 
	 * @param args   
	 * @return void     
	 * @throws
	 * @Author yuchao
	 * @Create Date  2014-2-28
	 * @Modified by none
	 * @Modified Date 
	 */

	public static ICacheClient getInstance(String string) {
		return getInstance(string);
	}
	
	public static void main(String[] args) {
//		SCacheClient.getInstance().set("ALL", "ALL");
		
		//egame库和ext库跨库做事务
		
		/*
		long begin = System.currentTimeMillis();
		String key = "JAVA-listGameIdByTagId:2751-0";
		System.out.println(SCacheClient.getInstance("egame_list").get(key));
		long end = System.currentTimeMillis();
		System.out.println(end-begin);
		*/
		
//		Utils.initLog4j();
		SCacheClient.getInstance("egame").set("ALL", "test");
		SCacheClient.getInstance("egame_list").set("ALL", "test");
//		/*
//		try {
//			List<Integer> ids = EGameClientBiz.getInstance().listAppParameterIdsByParentId(0, 0, 0);
//			for(Integer id : ids){
//				AppParameter appParameter = EGameClientBiz.getInstance().getAppParameterById(0, 0, id);
//				System.out.println(appParameter.getId()+","+appParameter.getName()+","+appParameter.getParam().toString());
//				System.out.println();
				
//			}
			/*
			AppParameter appParameter = EGameClientBiz.getInstance().getAppParameterById(0, 0, 822);
			System.out.println(appParameter.getId()+","+appParameter.getName()+","+appParameter.getParam().toString());
			
			Map<AppParameterParamType, String> param = appParameter.getParam();
//			System.out.println(appParameter.getTestObject());
//			System.out.println(appParameter.getTestObject());
			for(Entry<AppParameterParamType, String> entry : param.entrySet()){
				System.out.println(entry.getKey()+","+entry.getValue());
			}
			*/
			
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		System.exit(0);
//		*/
//		System.out.println(Utils.getFileId("95ad1220h11c8a20", 0));
		/*
		String str = "%E6%A4%8D%E7%89%A9%E5%A4%A7%E6%88%98%E5%83%B5%E5%B0%B82%E6%9C%AA%E6%9D%A5%E4%B8%96%E7%95%8C%E7%89%88";
		try {
			System.out.println(URLDecoder.decode(str, "utf-8"));
			
			System.out.println(URLDecoder.decode("植物大战僵尸2未来世界版", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 */
	}

}

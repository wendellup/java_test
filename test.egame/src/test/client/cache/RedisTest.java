package test.client.cache;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.biz.EGameClientBiz;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.util.Utils;
import cn.egame.ext.gc.GameCommentInfo;
import cn.egame.ext.gc.HallFileTerminalLinkInfo;
import cn.egame.ext.me.Memory;
import cn.egame.ext.ng.GiftInfo;
import cn.egame.interfaces.EnumType.AppParameterParamType;
import cn.egame.interfaces.ck.EGameCacheKey;
import cn.egame.interfaces.ck.EGameCacheKeyV2;
import cn.egame.interfaces.gc.GameInfo;
import cn.egame.interfaces.gc.v2.TerminalFilterV2;
import cn.egame.interfaces.pu.AppParameter;

public class RedisTest {
	
	private static final Logger LOGGER = Logger.getLogger(RedisTest.class);
    
    public static ICacheClient getCache() {
        return SCacheClient.getInstance("egame");
    }
    
    public static ICacheClient getOpenCache() {
        return SCacheClient.getInstance("open");
    }
    
    public static ICacheClient getCacheList() {
        return SCacheClient.getInstance("egame_list");
    }
	
    public static ICacheClient getDataSupportCache() {
        return SCacheClient.getInstance("egame_data_support");
    }
    
    public ICacheClient getGameCache() {
        return SCacheClient.getInstance("egame");
    }
    
    @Test
	public void getOpenTest(){
		String key = "JAVA-getCaidanTagKeyById:5125"; 
		String str = RedisTest.getCache().getString(key);
		LOGGER.info(str);
	}
	
	@Test
	public void setOpenTest(){
		String key = "JAVA-getCaidanTagKeyById:5125"; 
		String val = "{\"app_list\":[{\"app_id\":1032474,\"package_name\":\"com.popcap.pvz2cthdzxyxct\",\"app_name\":\"植物大战僵尸2高清版\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/001/000/136eb007ehf45cdb.jpg\",\"app_down_load_count\":30000000,\"app_size\":\"32604461\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/001/000/732197f06hf45d48/10324741383185218764.apk\",\"version_name\":\"1.0.4\",\"version_code\":\"4\",\"show_tags\":null,\"md5_code\":\"8e12147342a45935025172660e57bf84\",\"sha1_code\":\"9bdb57d3c5da21577ad5506cd3cfdc832c5e8114\"},{\"app_id\":1000137,\"package_name\":\"com.fruitroll.game.top.best.free\",\"app_name\":\"水果大逃亡\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/701/5a17942f9hab33ae.png\",\"app_down_load_count\":945534,\"app_size\":\"6922240\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/701/c5dcf09c0hab339b/com.fruitroll.game.top.best.free.apk\",\"version_name\":\"1.5.0\",\"version_code\":\"0\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null},{\"app_id\":1004170,\"package_name\":\"cn.com.miq.army\",\"app_name\":\"军临城下3\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/788/5f89fea60hc08bd7.png\",\"app_down_load_count\":19051,\"app_size\":\"31633408\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/788/85912d6a7hc08bc3/cn.com.miq.army.apk\",\"version_name\":\"2.3.2\",\"version_code\":\"0\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null},{\"app_id\":1017714,\"package_name\":\"com.wyd.yzdd\",\"app_name\":\"一站到底（青春版）\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/001/000/d4294913chf44412.jpg\",\"app_down_load_count\":114553,\"app_size\":\"14161920\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/992/232bcb008hf25937/yzddpkb_all_1_jsct.apk\",\"version_name\":\"1.3.3\",\"version_code\":\"5\",\"show_tags\":null,\"md5_code\":\"74cccf61d49ee47347a06149400d1947\",\"sha1_code\":\"b3c4fa2b468112ce1407f3c757fcb96d081934be\"},{\"app_id\":1000107,\"package_name\":\"com.hitcents.stickmanepic\",\"app_name\":\"画个火柴人\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/700/a19afc8eehab066e.png\",\"app_down_load_count\":1256296,\"app_size\":\"50764800\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/700/3bfdbf589hab0653/com.hitcents.stickmanepic.apk\",\"version_name\":\"1.3\",\"version_code\":\"0\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null},{\"app_id\":1017713,\"package_name\":\"com.halfbrick.fruitninja\",\"app_name\":\"水果忍者\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/992/2ee1aaaadhf257e6.jpg\",\"app_down_load_count\":563910,\"app_size\":\"39418880\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/992/0cc13d98chf25865/com.halfbrick.fruitninja.apk\",\"version_name\":\"1.8.5\",\"version_code\":\"1805\",\"show_tags\":null,\"md5_code\":\"d42e16c4595825441c544fe22e06b34b\",\"sha1_code\":\"9a85d7f00d18be35dfa92e6471a6b57b83c37381\"},{\"app_id\":1032472,\"package_name\":\"com.yodo1.YS003.SkiSafari\",\"app_name\":\"滑雪大冒险\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/001/000/9d01427c8hf45b87.jpg\",\"app_down_load_count\":0,\"app_size\":\"27377220\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/001/000/32754a1b1hf45be8/10324721383184564844.apk\",\"version_name\":\"2.0.3\",\"version_code\":\"1003\",\"show_tags\":null,\"md5_code\":\"d04f9586f5b0311d80c7c6c8a7f33c91\",\"sha1_code\":\"3ec51e4dcc00a807629112353fc8808cf9c09f35\"},{\"app_id\":1014012,\"package_name\":\"com.zeptolab.monsters.free.china.chinatelecom\",\"app_name\":\"布丁怪兽（Pudding Monsters)\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/970/7f80f44d0hecd6a2.jpg\",\"app_down_load_count\":212504,\"app_size\":\"45461504\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/970/60e7267d6hecd69e/com.zeptolab.monsters.free.china.chinatelecom.apk\",\"version_name\":\"1.0.2\",\"version_code\":\"6\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null},{\"app_id\":1000018,\"package_name\":\"com.rovio.amazingalex.free\",\"app_name\":\"神奇的阿力\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/698/42b7e2498haa88c8.png\",\"app_down_load_count\":4996906,\"app_size\":\"24019968\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/698/47d56bf03haa88b1/com.rovio.amazingalex.free.apk\",\"version_name\":\"1.0.4\",\"version_code\":\"0\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null},{\"app_id\":1014011,\"package_name\":\"com.gamebox.kingaiyouxi\",\"app_name\":\"帝王三国（经典版）\",\"app_icon\":\"http://open.play.cn/f/shop/ph/000/000/970/df4b47516hecd623.jpg\",\"app_down_load_count\":310890,\"app_size\":\"12183552\",\"download_url\":\"http://open.play.cn/f/shop/gm/000/000/970/ab9b2684ehecd612/com.gamebox.kingaiyouxi.apk\",\"version_name\":\"1.36.0116\",\"version_code\":\"23\",\"show_tags\":null,\"md5_code\":null,\"sha1_code\":null}]}";
		RedisTest.getCache().set(key, val);
	}
	
	@Test
	public void getObjTest(){
		String key = "JAVA-getCaidanTagKeyById:5125"; 
		String str = RedisTest.getCache().getString(key);
		LOGGER.info(str);
	}
	
	@Test
	public void setObjTest(){
		SortBo sortBo = new SortBo(1, 1.0, 2.0, 3);
		String cacheIdKey = "sortBo-id-1";
		RedisTest.getCache().set(cacheIdKey, sortBo);
	}
	
	@Test
	public void getObjTest2(){
//		SortBo sortBo = new SortBo(1, 1.0, 2.0, 3);
		String cacheIdKey = "sortBo-id-1";
		Map<SortBo, Integer> map = new HashMap<SortBo, Integer>();
		SortBo sb1 = (SortBo) RedisTest.getCache().get(cacheIdKey);;
		SortBo sb2 = (SortBo) RedisTest.getCache().get(cacheIdKey);;
		SortBo sb3 = (SortBo) RedisTest.getCache().get(cacheIdKey);;
		map.put(sb1, 0);
		map.put(sb2, 0);
		map.put(sb3, 0);
		System.out.println(map.size());
//		System.out.println(sb1.getTagHotNum());
//		sb1.setTagHotNum(10);
//		System.out.println(sb1.getTagHotNum());
//		System.out.println(sb2.getTagHotNum());
//		System.out.println(sb3.getTagHotNum());
//		System.out.println(RedisTest.getCache().get(cacheIdKey));;
//		System.out.println(RedisTest.getCache().get(cacheIdKey));;
	}
	
	/**
	 * 获取已经举报id为2920的记录的值
	 */
	@Test
	public void getAppParameter(){
//		String key = "JAVA-appParameterById:2920"; 
		AppParameter appParameter = RedisTest.getCache().getT(AppParameter.class, EGameCacheKey.getAppParameterById(2920));
		System.out.println(appParameter);
	}
	
	/**
	 * 获取parentId为951的parameterIds集合
	 */
	@Test
	public void getAppParameterIdsByParentId(){
		String cacheKey = EGameCacheKeyV2.listAppParameterIdsByParentId(951);
		List<Integer> appParameterIds = RedisTest.getCacheList().getListInt(cacheKey);
		System.out.println(appParameterIds);
	}
	
//	JAVA-listGameIdByTagId:1667-0
	/**
	 * 获取901周排行下list
	 */
	@Test
	public void get901GameIds(){
		String cacheKey = "JAVA-listGameIdByTagId:1667-0";
		List<Integer> appParameterIds = RedisTest.getCacheList().getListInt(cacheKey);
		System.out.println(appParameterIds);
	}
	
	//
	@Test
	public void getTerminalFilterList(){
		int terminalId = 100;
//		String cacheKey = EGameCacheKeyV2.listTerminalFilterV2sByTerminalId(terminalId);
		List<TerminalFilterV2> filterInfos = getCacheList().getListT(TerminalFilterV2.class,
                EGameCacheKeyV2.listTerminalFilterV2sByTerminalId(terminalId));
		
		if(filterInfos!=null){
			for (TerminalFilterV2 filterInfo : filterInfos) {
				System.out.println(filterInfo.getgId());;
			};
		}else{
			LOGGER.info("null");
		}
	}

	//JAVA-getGameHistoryByIMSI:460031*
	/*
	  5140) "JAVA-getGameHistoryByIMSI:460031273642741"
	  5141) "JAVA-getGameHistoryByIMSI:460031223283123"
	  5142) "JAVA-getGameHistoryByIMSI:460031208146970"
	  5143) "JAVA-getGameHistoryByIMSI:460031298160366"
	  5144) "JAVA-getGameHistoryByIMSI:460031243266876"
	  5145) "JAVA-getGameHistoryByIMSI:460031267024439"
	  5146) "JAVA-getGameHistoryByIMSI:460031211844580"
	  5147) "JAVA-getGameHistoryByIMSI:460031213806136"
	  5148) "JAVA-getGameHistoryByIMSI:460031272013958"
	  5149) "JAVA-getGameHistoryByIMSI:460031545508826"
	  5150) "JAVA-getGameHistoryByIMSI:460031220440962"
	  5151) "JAVA-getGameHistoryByIMSI:460031204095564"
	  5152) "JAVA-getGameHistoryByIMSI:460031539603880"
	  5153) "JAVA-getGameHistoryByIMSI:460031248606853"
	  5154) "JAVA-getGameHistoryByIMSI:460031390120878"
	  5155) "JAVA-getGameHistoryByIMSI:460031276745515"
	  5156) "JAVA-getGameHistoryByIMSI:460031215319886"
	  5157) "JAVA-getGameHistoryByIMSI:460031222057373"
	  5158) "JAVA-getGameHistoryByIMSI:460031244208195"
	  5159) "JAVA-getGameHistoryByIMSI:460031246673670"
	  5160) "JAVA-getGameHistoryByIMSI:460031202617887"
	  5161) "JAVA-getGameHistoryByIMSI:460031250287377"
	  5162) "JAVA-getGameHistoryByIMSI:460031210577419"
	  5163) "JAVA-getGameHistoryByIMSI:460031246488251"
	  5164) "JAVA-getGameHistoryByIMSI:460031224910103"
	  5165) "JAVA-getGameHistoryByIMSI:460031392039369"
	  5166) "JAVA-getGameHistoryByIMSI:460031213958249"
	  5167) "JAVA-getGameHistoryByIMSI:460031213906596"
	  5168) "JAVA-getGameHistoryByIMSI:460031268095087"
	  5169) "JAVA-getGameHistoryByIMSI:460031265215495"
	  5170) "JAVA-getGameHistoryByIMSI:460031265772295"
	  5171) "JAVA-getGameHistoryByIMSI:460031693196622"
	  5172) "JAVA-getGameHistoryByIMSI:460031216404709"
	  5173) "JAVA-getGameHistoryByIMSI:460031223488027"
	  5174) "JAVA-getGameHistoryByIMSI:460031225172526"
	  5175) "JAVA-getGameHistoryByIMSI:460031267383072"
	  5176) "JAVA-getGameHistoryByIMSI:460031390154252"
	  5177) "JAVA-getGameHistoryByIMSI:460031218412809"
	  5178) "JAVA-getGameHistoryByIMSI:460031214349454"
	  5179) "JAVA-getGameHistoryByIMSI:460031204231358"
	  5180) "JAVA-getGameHistoryByIMSI:460031249606521"
	  5181) "JAVA-getGameHistoryByIMSI:460031249437925"
	  5182) "JAVA-getGameHistoryByIMSI:460031024183015"
	  5183) "JAVA-getGameHistoryByIMSI:460031549898251"
	  5184) "JAVA-getGameHistoryByIMSI:460031267411834"
	  5185) "JAVA-getGameHistoryByIMSI:460031221097478"
	  5186) "JAVA-getGameHistoryByIMSI:460031220783572"
	  5187) "JAVA-getGameHistoryByIMSI:460031245107767"
	  5188) "JAVA-getGameHistoryByIMSI:460031267994875"
	  5189) "JAVA-getGameHistoryByIMSI:460031549154445"
	  5190) "JAVA-getGameHistoryByIMSI:460031247289099"
	  5191) "JAVA-getGameHistoryByIMSI:460031245179715"
	  5192) "JAVA-getGameHistoryByIMSI:460031547277060"
	  5193) "JAVA-getGameHistoryByIMSI:460031226215385"
	  5194) "JAVA-getGameHistoryByIMSI:460031202762943"
	  5195) "JAVA-getGameHistoryByIMSI:460031269077477"
	  5196) "JAVA-getGameHistoryByIMSI:460031219695401"
	  5197) "JAVA-getGameHistoryByIMSI:460031207565209"
	  5198) "JAVA-getGameHistoryByIMSI:460031243587846"
	  5199) "JAVA-getGameHistoryByIMSI:460031205102551"
	  5200) "JAVA-getGameHistoryByIMSI:460031541703928"
	  5201) "JAVA-getGameHistoryByIMSI:460031218435072"
	  5202) "JAVA-getGameHistoryByIMSI:460031390017605"
	  5203) "JAVA-getGameHistoryByIMSI:460031276759351"
	  5204) "JAVA-getGameHistoryByIMSI:460031207638936"
	  5205) "JAVA-getGameHistoryByIMSI:460031216897647"
	  5206) "JAVA-getGameHistoryByIMSI:460031304643620"
	  5207) "JAVA-getGameHistoryByIMSI:460031209777337"
	  5208) "JAVA-getGameHistoryByIMSI:460031224171157"
	  5209) "JAVA-getGameHistoryByIMSI:460031025039577"
	  5210) "JAVA-getGameHistoryByIMSI:460031210699736"
	  5211) "JAVA-getGameHistoryByIMSI:460031245340448"
	  5212) "JAVA-getGameHistoryByIMSI:460031393867672"
	  5213) "JAVA-getGameHistoryByIMSI:460031390572595"
	  5214) "JAVA-getGameHistoryByIMSI:460031267641812"
	  5215) "JAVA-getGameHistoryByIMSI:460031247514676"
	  5216) "JAVA-getGameHistoryByIMSI:460031245402228"
	  5217) "JAVA-getGameHistoryByIMSI:460031201613337"
	  5218) "JAVA-getGameHistoryByIMSI:460031337909746"
	  5219) "JAVA-getGameHistoryByIMSI:460031220363209"
	  5220) "JAVA-getGameHistoryByIMSI:460031390599363"
	  5221) "JAVA-getGameHistoryByIMSI:460031693182872"
	  5222) "JAVA-getGameHistoryByIMSI:460031273140342"
	  5223) "JAVA-getGameHistoryByIMSI:460031215716074"
	  5224) "JAVA-getGameHistoryByIMSI:460031268190999"
	  5225) "JAVA-getGameHistoryByIMSI:460031242318416"
	  5226) "JAVA-getGameHistoryByIMSI:460031272896828"
	  5227) "JAVA-getGameHistoryByIMSI:460031243729352"
	  5228) "JAVA-getGameHistoryByIMSI:460031337640517"
	  5229) "JAVA-getGameHistoryByIMSI:460031547236936"
	  5230) "JAVA-getGameHistoryByIMSI:460031336425507"
	  5231) "JAVA-getGameHistoryByIMSI:460031271703978"
	  5232) "JAVA-getGameHistoryByIMSI:460031211018865"
	  5233) "JAVA-getGameHistoryByIMSI:460031250884337"
	  5234) "JAVA-getGameHistoryByIMSI:460031390099625"
	  5235) "JAVA-getGameHistoryByIMSI:460031204090083"
	  5236) "JAVA-getGameHistoryByIMSI:460031212500090"
	  5237) "JAVA-getGameHistoryByIMSI:460031209400604"
	  5238) "JAVA-getGameHistoryByIMSI:460031273802879"
	  5239) "JAVA-getGameHistoryByIMSI:460031337298202"
	  5240) "JAVA-getGameHistoryByIMSI:460031539873575"
	  5241) "JAVA-getGameHistoryByIMSI:460031025044927"
	  5242) "JAVA-getGameHistoryByIMSI:460031241444981"
	  5243) "JAVA-getGameHistoryByIMSI:460031213000157"
	  5244) "JAVA-getGameHistoryByIMSI:460031246686950"
	  5245) "JAVA-getGameHistoryByIMSI:460031548003927"
	  5246) "JAVA-getGameHistoryByIMSI:460031210268240"
	  5247) "JAVA-getGameHistoryByIMSI:460031390106028"
	  5248) "JAVA-getGameHistoryByIMSI:460031390006472"
	  5249) "JAVA-getGameHistoryByIMSI:460031269212826"
	  5250) "JAVA-getGameHistoryByIMSI:460031209505885"
	  5251) "JAVA-getGameHistoryByIMSI:460031242298453"
	  5252) "JAVA-getGameHistoryByIMSI:460031241031768"
	  5253) "JAVA-getGameHistoryByIMSI:460031549677474"
	*/
	@Test
	public void getGameHistoryByIMSI(){
//		String cacheKey = EGameCacheKeyV2.listTerminalFilterV2sByTerminalId(terminalId);
		List<Integer> gameHistoryList = 
				getDataSupportCache().getListInt(EGameCacheKeyV2.getGameHistoryByIMSI("460031242298453"));
		for(Integer i : gameHistoryList){
			System.out.println(i);
		}
	}
	
	@Test
	public void getGameSimilarityByGameId(){
		Map<Integer, Double> tempMap = getDataSupportCache().getT(Map.class,
				EGameCacheKeyV2.getGameSimilarityByGameId(248943));
		LOGGER.info(tempMap.size());
	}
	
	
	

	@Test
	public void setGameInfo() throws RemoteException{
		int gId = 251141;
		GameInfo domain = EGameClientBiz.getInstance().getGameInfoById(0, 0, gId);
		if(domain!=null){
			domain.setGameName(domain.getGameName()+"1");
		}
//		EGameClientBiz.getInstance().setGameInfo(0, 0, domain);
	}
	
	
	@Test
	public void listGIdbyCpId() throws RemoteException{
		int cpId = 1000;
		List<Integer> listGIdbyCpId = getCacheList().getListInt(EGameCacheKeyV2.listGIdbyCpId(cpId));
		LOGGER.info(listGIdbyCpId);
	}
	
	@Test
	public void listGameIdByTagId() throws RemoteException{
//		int tagId = 1560;
		int tagId = 1667;
//		List<Integer> listGIdbyCpId = getCacheList().getListInt(EGameCacheKeyV2.listGIdbyCpId(cpId));
		List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(tagId));
		System.out.println(gameIds.size());
		LOGGER.info(gameIds);
		
		getCacheList().set(EGameCacheKeyV2.listGameIdByTagId(tagId), new ArrayList<Integer>());
		gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(tagId));
		System.out.println(gameIds.size());
		LOGGER.info(gameIds);
	}
	
	@Test
	public void listGameIdByGroupId() throws RemoteException{
		int groupId = 30;
//		List<Integer> listGIdbyCpId = getCacheList().getListInt(EGameCacheKeyV2.listGIdbyCpId(cpId));
		List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGIdByGroupId(groupId));
		LOGGER.info(gameIds);
	}
	
	@Test
	public void listAllGiftInfo() throws RemoteException{
		String cacheKey = EGameCacheKeyV2.listAllGifts();
		List<GiftInfo> giftInfos = getCacheList().getListT(GiftInfo.class, cacheKey);
		System.out.println(giftInfos);
		if(giftInfos!=null){
			for (GiftInfo giftInfo : giftInfos) {
				System.out.println(giftInfo.getId()+"-------"+giftInfo.getPickCount());
            }
		}
	}
	
//	boolean cacheSwitch = Utils.toBoolean(properties.getProperty("cache" + temp + ".redis.switch"), true);
	
	public static void main(String[] args) {
		System.out.println("xxxxxxxxxxxx");
		boolean cacheSwitch = Utils.toBoolean(("ture"), true);
		System.out.println("-----------"+toBoolean("ture"));
		System.out.println(cacheSwitch);
		
	}
	 private static boolean toBoolean(String name) { 
			return ((name != null) && name.equalsIgnoreCase("true"));
		    }
	 
	 @Test
	 public void testListGidsByRuleAndType(){
		 String cacheKey = EGameCacheKeyV2.listGidsByRuleAndType(730964, 1, 1);
		 List<Integer> result = getCacheList().getListInt(cacheKey);
//		 if (result == null) {
//			 result = dao.listGidsByRuleAndType(739064, 1, 1);
//			 getCacheList().set(cacheKey, result);
//		 }
		 System.out.println(result);
	 }
	 
	 @Test
	 public void listGameIdByTagId1561(){
		List<Integer> gameIds = getCacheList().getListInt(
				EGameCacheKeyV2.listGameIdByTagId(1561));
		System.out.println(gameIds);
		
	 }
	 
	 @Test
	 public void getGameInfoById() throws RemoteException{
			
//			int gId = 5028283;
			int gId = 245945;
			String cacheIdKey = EGameCacheKey.getGameInfoById(gId);
	        GameInfo domain = getGameCache().getT(GameInfo.class, cacheIdKey);
	        System.out.println(domain);
	 }
	 
	 @Test
	 public void listMemoryByAdvertType() throws RemoteException{
//			int gId = 5028283;
			String cacheIdKey = EGameCacheKeyV2.listMemoryByAdvertType(1);
			List<Memory> listMemory = getCacheList().getListT(Memory.class, EGameCacheKeyV2.listMemoryByAdvertType(1));
	        System.out.println(listMemory);
	 }
	 
	 @Test
	 public void listHallFileTerminalLink() throws RemoteException{
		 List<HallFileTerminalLinkInfo> listHallFileTerminalLink = getCacheList().getListT(HallFileTerminalLinkInfo.class,
	             EGameCacheKeyV2.listHallFileTerminalLink());
		 System.out.println(listHallFileTerminalLink);
	 }
	 
	@Test
	public void getCommentInfoById() throws RemoteException {
		long commentId = 102914;
		String cacheIdKey = EGameCacheKeyV2.getCommentInfoById(commentId);
		GameCommentInfo domain = getGameCache().getT(GameCommentInfo.class,
				cacheIdKey);
		System.out.println(domain);

	}

	@Test
	public void getAppParameter2(){
//		int parentId = 836;
//		 String cacheKey = EGameCacheKeyV2.listAppParameterIdsByParentId(parentId);
//	        List<Integer> appParameterIds = getCacheList().getListInt(cacheKey);
//	        System.out.println(appParameterIds);
//	        for(int paramId :appParameterIds){
//	        	AppParameter appParameter = RedisTest.getCache().getT(AppParameter.class, EGameCacheKey.getAppParameterById(paramId));
//	        	System.out.println(appParameter.getParam());
//	            System.out.println(appParameter.getParam().get(AppParameterParamType.sdk_ref_tag_id));
//	        }
	        
//	        AppParameter appParameter = RedisTest.getCache().getT(AppParameter.class, EGameCacheKey.getAppParameterById(11283));
//	        AppParameter appParameter = RedisTest.getCache().getT(AppParameter.class, EGameCacheKey.getAppParameterById(11265));
		AppParameter appParameter = RedisTest.getCache().getT(AppParameter.class, EGameCacheKey.getAppParameterById(11284));
	        System.out.println(appParameter);
        	System.out.println(appParameter.getParam().containsKey(AppParameterParamType.sdk_ref_tag_id));
        appParameter.getParam().get(AppParameterParamType.sdk_ref_tag_id) ;
        	
//            System.out.println(Integer.parseInt(appParameter.getParam().get(AppParameterParamType.sdk_ref_tag_id)));
            System.out.println("-------");
	}
	
	
}

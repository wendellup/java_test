package test.client.cache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import cn.egame.client.EGameClientExt;
import cn.egame.client.EGameClientExtV2;
import cn.egame.client.EGameClientV2;
import cn.egame.client.biz.EGameClientBiz;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.client.EGameClientBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.model.PageData;
import cn.egame.common.util.Utils;
import cn.egame.ext.EnumTypeExt.AdvertType;
import cn.egame.ext.gc.HallFileTerminalLinkInfo;
import cn.egame.ext.gc.IGameServiceExt;
import cn.egame.ext.me.Memory;
import cn.egame.ext.ng.OnlineGameServiceInfo;
import cn.egame.interfaces.EnumType;
import cn.egame.interfaces.EnumType.AppParameterParamType;
import cn.egame.interfaces.EnumType.DateType;
import cn.egame.interfaces.EnumType.GameTagType;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.EnumType.MobileNetworkType;
import cn.egame.interfaces.EnumType.SearchSortType;
import cn.egame.interfaces.EnumType.SysParameterType;
import cn.egame.interfaces.ck.EGameCacheKey;
import cn.egame.interfaces.ck.EGameCacheKeyV2;
import cn.egame.interfaces.gc.ExtraInfo;
import cn.egame.interfaces.gc.GameFileInfo;
import cn.egame.interfaces.gc.GameInfo;
import cn.egame.interfaces.gc.IGameService;
import cn.egame.interfaces.pu.AppParameter;
import cn.egame.message.MessageEnum.MessageAction;
import cn.egame.message.MessageEnum.MsgBusinessType;
import cn.egame.message.MessageEnum.UserMessageType;
import cn.egame.message.MessageInfo;
import cn.egame.message.UserMessageEvent;
import cn.egame.message.client.MessageClient;
import cn.egame.search.client.EGameGameSearchClient;
import cn.egame.search.client.biz.EGameGameSearchClientBiz;

public class ClientCallTest extends EGameClientBase {
	
	private static Logger logger = Logger.getLogger(ClientCallTest.class);
	
	@Test
	public void pageGameIdByChannelId() throws RemoteException {
		PageData pageData = EGameClientV2.getInstance().pageGameIdByChannelId(
				0, 0, GameType.mobile, 1, 701, 0, 20,
				SearchSortType.downCountWeek,new ExtraInfo());
//		int total = pageData.getTotal();
//		List<Integer> ids = (List<Integer>) pageData.getContent();
//		if (ids != null) {
//			System.out.println(ids);
//		}

	}
	
	@Test
	public void testList() throws RemoteException{
		List<Integer> notFirstTagIds = EGameClientV2.getInstance().listGameTagIdByGameTagType(0, 0, GameTagType.searchTag);
		System.out.println(notFirstTagIds);
	}
	
	
	public ICacheClient getGameCache() {
        return SCacheClient.getInstance("egame");
    }
	
	public ICacheClient getGameListCache() {
        return SCacheClient.getInstance("egame_list");
    }
	
	public ICacheClient getGameDataSupport() {
        return SCacheClient.getInstance("egame_data_support");
    }
	
	private IGameService getGameService() throws RemoteException {
        return (IGameService) super.getService("game_service");
    }
	
	private IGameServiceExt getGameServiceExt() throws RemoteException {
        return (IGameServiceExt) super.getService("game_service_ext");
    }
	@Test
    public void getGameInfoById() throws RemoteException {
		GameInfo gameInfo = null;
		int gId = 5011054;
        try {
            // 先从缓存中取数据，如果没有去数据库中的
            String key = EGameCacheKey.getGameInfoById(gId);
            gameInfo = getGameCache().getT(GameInfo.class, key);
            if (gameInfo == null) {
            	gameInfo = getGameService().getGameInfoById(0, 0, gId);
            }
        } catch (RemoteException ex) {
            release(ex, getGameService());
            throw ex;
        }
        
//        System.out.println(gameInfo.getGameStatus().value());
        System.out.println(gameInfo);
        System.out.println(gameInfo.getIsFreeInstall());
    }
	
	@Test
    public void setGameInfoById() throws RemoteException {
		GameInfo gameInfo = null;
		int gId = 5011054;
        try {
            // 先从缓存中取数据，如果没有去数据库中的
            String key = EGameCacheKey.getGameInfoById(gId);
            gameInfo = getGameCache().getT(GameInfo.class, key);
            if (gameInfo == null) {
            	gameInfo = getGameService().getGameInfoById(0, 0, gId);
            }
        } catch (RemoteException ex) {
            release(ex, getGameService());
            throw ex;
        }
        
//        gameInfo.setGameStatus(GameStatus.offLine);
//        gameInfo.setIsFreeInstall(10);
        gameInfo.setGameName(gameInfo.getGameName()+"xx");
        getGameService().setGameInfo(0, 0, gameInfo);
    }
	
	public ICacheClient getCacheList() {
        return SCacheClient.getInstance("egame_list");
    }
	
	@Test
    public void listGIdBySortType() throws RemoteException {
		List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGIdBySortType(SearchSortType.firstOnlineTime.getValue()));
		System.out.println(gameIds);
	}
	
	@Test
    public void getGameFileInfoById() throws RemoteException {
		GameFileInfo gameFileInfo = EGameClientV2.getInstance().getGameFileInfoById(0, 0, 5003729);
		if(gameFileInfo!=null){
			System.out.println(gameFileInfo.getgId());
		}
	}
	
	@Test
	public void pageOnlineGameServiceByAppId() throws ExceptionCommonBase {
		try {
			int total = 0;
			PageData pd = getGameServiceExt().pageOnlineGameServiceByAppId(0, 0, 927, 0, 20);
			if (pd != null) {
	            total = pd.getTotal();
	            Map<String, List<OnlineGameServiceInfo>> map = (HashMap<String, List<OnlineGameServiceInfo>>) pd.getContent();
	            if (map != null) {
	                for (DateType dt : DateType.values()) {
	                    List<OnlineGameServiceInfo> list = map.get(dt.getValue());
	                    if (list == null || list.size() == 0) {
	                        continue;
	                    }
	                    System.out.println(dt);
	                    for(OnlineGameServiceInfo ogsi : list){
	                    	System.out.println(ogsi.getgId());
	                    }
	                }
	            }
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
    }
	
	
	
	@Test
	public void listGameIdByTagId() throws ExceptionCommonBase {
		/*
		int tagId = 488390;
        List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(tagId));
        int filterTagId = 488392;
        List<Integer> filterGameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(filterTagId));
        System.out.println(gameIds);
        System.out.println(filterGameIds);
        */
		
		List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(1561));
		System.out.println(gameIds);
		System.out.println(gameIds.size());
		System.out.println(gameIds.contains(250792));
		System.out.println(gameIds.contains(237978));
		System.out.println(gameIds.contains(5023691));
    }
	
	@Test
	public void searchByName() throws RemoteException {
		String[] keyWords = new String[]{"跑酷","捕鱼","益智","纪念碑谷","僵尸"
				,"小黄人","我的世界","消除","摩托","飞机"
				,"益智,跑得快","炮妹","斗地主","忍者,美女","忍者"
				,"麻将","塔防","塔防,益智","果宝三国","熊出没"};
		//门户调用老搜索
		System.out.println("门户调用老搜索开始----------------->:");
		for(int i=0; i<keyWords.length; i++){
			List<Integer> list = EGameGameSearchClient.getInstance().searchByName(keyWords[i], 1, 1105);
			System.out.println(keyWords[i]+":"+list.size());	
		}
		System.out.println("门户调用老搜索结束----------------->:");
		
		//搜索标签(search_type=1)
		System.out.println("搜索标签开始----------------->:");
		for(int i=0; i<keyWords.length; i++){
			PageData pd = EGameGameSearchClientBiz.getInstance().searchByName(0,0,keyWords[i], 1, 0, 20, 1105,
	                1, null);
			if(pd==null){
				System.out.println(keyWords[i] + ":" + 0);
			}else{
				System.out.println(keyWords[i] + ":" + pd.getTotal());
			}
		}
		System.out.println("搜索标签结束----------------->:");
		
		// 最新搜索(search_type=0)
		System.out.println("最新搜索开始----------------->:");
		for (int i = 0; i < keyWords.length; i++) {
			PageData pd = EGameGameSearchClientBiz.getInstance().searchByName(
					0, 0, keyWords[i], 1, 0, 20, 1105, 0, null);
			if(pd==null){
				System.out.println(keyWords[i] + ":" + 0);
			}else{
				System.out.println(keyWords[i] + ":" + pd.getTotal());
			}
		}
		System.out.println("最新搜索结束----------------->:");
		
		// 搜索下拉列表(searchSuggest)
		System.out.println("搜索下拉列表(searchSuggest)开始----------------->:");
		for (int i = 0; i < keyWords.length; i++) {
			PageData pd = EGameGameSearchClientBiz.getInstance().getSuggestionKeyWord(0,
							0, keyWords[i],
							1, 0,
							20, new ExtraInfo(MobileNetworkType.DIANXIN, 0, 762));
			if(pd==null){
				System.out.println(keyWords[i] + ":" + 0);
			}else{
				System.out.println(keyWords[i] + ":" + pd.getTotal());
			}
		}
		System.out.println("搜索下拉列表(searchSuggest)结束----------------->:");
	}
	
	
	public static void main(String[] args){
		Utils.initLog4j();
		logger.info("111");
		MessageInfo messageInfo = new MessageInfo();
        long longMillis = System.currentTimeMillis()-1000*30;
        messageInfo.setInsertTime(longMillis);
        messageInfo.setEffectBeginTime(longMillis);
        messageInfo.setBusinessId(new Long(11).intValue());
        messageInfo.setMsgBusinessType(MsgBusinessType.commentReplyMsg.getValue());
        UserMessageEvent userMessageEvent = new UserMessageEvent(messageInfo, 611, MessageAction.add);
        MessageClient.getInstance().pushMessageEvent(0, 4, userMessageEvent);
        logger.info("111");
        System.exit(1);
	}

	@Test
	public void getMemoryByAdvertTypeAndIdV2() throws RemoteException{
//		MemoryAdvLink memoryAdvLink = EGameClientExtV2.getInstance().getMemoryAdvLinkByMemoryId(0, 0,
//				1079);
		Memory memoryInfo = EGameClientExt.getInstance().getMemoryByAdvertTypeAndIdV2(730964, 0,
                AdvertType.game, (long) 0, "", 1, "");
		System.out.println(memoryInfo);
//		memoryInfo.setTitle(memoryInfo.getTitle()+"_update");
//		EGameClientExt.getInstance().setMemoryInfo(730964, 0, memoryInfo);
	}
	
	@Test
	public void hallFileTerminalLinkInfo() throws RemoteException{
		HallFileTerminalLinkInfo hallFileTerminalLinkInfo = EGameClientExtV2.getInstance().getHallFileInfoByTerminalIdAndVersionV2(
				730964, 0, 1, "752", 8888007);
		System.out.println(hallFileTerminalLinkInfo);
		System.out.println(hallFileTerminalLinkInfo.getHallFileId());
	}
	
	@Test
	public void parameterApp() throws RemoteException{
		AppParameter app = EGameClientBiz.getInstance().getAppParameterById(0, 0L, 11214);
		System.out.println(app);
		Map<EnumType.AppParameterParamType,String> map = new HashMap<EnumType.AppParameterParamType,String>();
		map.put(AppParameterParamType.sdk_ref_tag_id, "888");
		app.setParam(map);;
		EGameClientBiz.getInstance().setAppParameter(0, 0L, app);
		app = EGameClientBiz.getInstance().getAppParameterById(0, 0L, 11214);
		System.out.println(app);
	}
	
	@Test
	public void testListGameFileIdByPackageNameAndStatus(){
		String packageName = "com.hugenstar.tdzmclient.ayx";
		List<Integer> gameFileInfoIds = null;
		try {
			gameFileInfoIds = EGameClientV2.getInstance().listGameFileIdByPackageNameAndStatus(0, 0L,
			        packageName, -1);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println(gameFileInfoIds);
	}
	
	@Test
	public void pageOnlineGameServiceByAppId2() throws RemoteException{
		PageData pd = EGameClientExt.getInstance().pageOnlineGameServiceByAppId(
				0, 0L, 927, 0, 20);
		Map<String, List<OnlineGameServiceInfo>> map = (HashMap<String, List<OnlineGameServiceInfo>>) pd.getContent();
		System.out.println(map);
	}
	
	@Test
    public void setGameHistoryByIMSI() throws RemoteException {
		String key = "JAVA-getGameHistoryByIMSI:123";
		List<Integer> gameIds = new ArrayList<Integer>();
		gameIds.add(250792);
		getGameDataSupport().set(key, gameIds);
		
	}
	
	@Test
    public void listCommentReplyMsgByUserId() throws IOException {
		String key = "JAVA-listCommentReplyMsgByUserId:978748";
		List<MessageInfo> messageInfos = (List<MessageInfo>) getGameListCache().get(key);
		System.out.println(messageInfos.size());
		System.out.println(Utils.objectWrite(messageInfos).length);
		
		
	}
	
	@Test
    public void setGameSimilarityByGameId() throws RemoteException {
		String key = "JAVA-listSimilarityGidsByGameId:1";
		
		List<Integer> ids = new ArrayList<Integer>();
//		ids.add(1);
//		ids.add(2);
		String str = "";
		getGameListCache().set(key, str);
	}
	
	@Test
    public void getGameSimilarityByGameId() throws RemoteException {
//		String key = "JAVA-getGameSimilarityByGameId:5010177";
		String key = "JAVA-listSimilarityGidsByGameId:1";
		
		
		System.out.println(getGameDataSupport().get(key));;
	}
	
	@Test
    public void setSysParameter() throws RemoteException {
		SysParameterType type = SysParameterType.COMMENT_ADUIT_SWITCH;
//		SysParameterType type = SysParameterType.COMMENT_MAIN_SWITCH;
		String cacheKey = EGameCacheKeyV2.getSysParameter(type.value());
        getGameCache().set(cacheKey, "0");;
	}
	
	@Test
    public void getInfo() throws RemoteException {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("E:\\git_github\\wendellup\\java_test\\test.egame\\out.txt"), "utf-8"));
			for(int gId=1; gId<5057868; gId++){
				pw.println(gId);
				System.out.println(gId);
				List<Integer> gameIds = EGameClientV2.getInstance().listSimilarityGidsByGameId(0, 0, gId);
				if(gameIds!=null && gameIds.size()==1){
				}
			}
		}catch( Exception e){
			logger.error("", e);
		}finally{
			pw.flush();
			pw.close();
		}
	}
	
	@Test
	public void getGameInfo(){
		
		try {
			GameInfo gameInfo = EGameClientBiz.getInstance().getGameInfoByIdAndStatus(8888043, 0L,730011, null);
			System.out.println(gameInfo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void sendMsg(){
		/*
		logger.info("111");
		MessageInfo messageInfo = new MessageInfo();
        long longMillis = System.currentTimeMillis()-1000*30;
        messageInfo.setInsertTime(longMillis);
        messageInfo.setEffectBeginTime(longMillis);
        messageInfo.setBusinessId(new Long(11).intValue());
        messageInfo.setMsgBusinessType(MsgBusinessType.commentReplyMsg.getValue());
        UserMessageEvent userMessageEvent = new UserMessageEvent(messageInfo, 2, MessageAction.add);
        MessageClient.getInstance().pushMessageEvent(0, 2, userMessageEvent);
        logger.info("111");
        */
		
		Utils.initLog4j();
		logger.info("111");
		MessageInfo messageInfo = new MessageInfo();
        long longMillis = System.currentTimeMillis()-1000*30;
        messageInfo.setInsertTime(longMillis);
        messageInfo.setEffectBeginTime(longMillis);
        messageInfo.setBusinessId(new Long(11).intValue());
        messageInfo.setMsgBusinessType(MsgBusinessType.commentTopMsg.getValue());
        UserMessageEvent userMessageEvent = new UserMessageEvent(messageInfo, 811, MessageAction.add);
        MessageClient.getInstance().pushMessageEvent(0, 4, userMessageEvent);
        logger.info("111");
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
	}
	
	@Test
	public void getMsg(){
//		PageData pageData = MessageClient.getInstance().pageMessageInfoByKey(0, 2,
//                2 + "", UserMessageType.userSysMessage.getValue(), 0, 50);
		PageData pageData = MessageClient.getInstance().pageMessageInfoByKey(0, 2,
                61 + "", UserMessageType.userCommentReply.getValue(), 0, 50);
		
		if (pageData != null && pageData.getContent() != null && pageData.getContent() instanceof List) {
            List<MessageInfo> messageInfos = (List<MessageInfo>) pageData.getContent();
            for (MessageInfo mI : messageInfos) {
            	System.out.println(mI.getBusinessId());
            }
		}
	}
}

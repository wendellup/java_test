package test.client.cache;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.client.EGameClientBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.model.PageData;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.EnumType.MobileNetworkType;
import cn.egame.interfaces.EnumType.SearchSortType;
import cn.egame.interfaces.ck.EGameCacheKey;
import cn.egame.interfaces.ck.EGameCacheKeyV2;
import cn.egame.interfaces.gc.ExtraInfo;
import cn.egame.interfaces.gc.GameFileInfo;
import cn.egame.interfaces.gc.GameInfo;
import cn.egame.interfaces.gc.IGameService;
import cn.egame.search.client.EGameGameSearchClient;
import cn.egame.search.client.biz.EGameGameSearchClientBiz;

public class ClientCallTest extends EGameClientBase {
	@Test
	public void pageGameIdByChannelId() throws RemoteException {
		PageData pageData = EGameClientV2.getInstance().pageGameIdByChannelId(
				0, 0, GameType.mobile, 1, 701, 0, 20,
				SearchSortType.lookup(1105),null);
		int total = pageData.getTotal();
		List<Integer> ids = (List<Integer>) pageData.getContent();
		if (ids != null) {
			System.out.println(ids);
		}

	}
	
	public ICacheClient getGameCache() {
        return SCacheClient.getInstance("egame");
    }
	
	private IGameService getGameService() throws RemoteException {
        return (IGameService) super.getService("game_service");
    }
	
	@Test
    public void getGameInfoById() throws RemoteException {
		GameInfo gameInfo = null;
		int gId = 232857;
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
        
        System.out.println(gameInfo.hashCode());
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
	public void listGameIdByTagId() throws ExceptionCommonBase {
		int tagId = 488390;
        List<Integer> gameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(tagId));
        
        int filterTagId = 488392;
        List<Integer> filterGameIds = getCacheList().getListInt(EGameCacheKeyV2.listGameIdByTagId(filterTagId));
        System.out.println(gameIds);
        System.out.println(filterGameIds);
    }
	
	@Test
	public static void searchByName() throws RemoteException {
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
		try {
			searchByName();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		GameFileInfo gameFileInfo;
//		try {
//			gameFileInfo = EGameClientV2.getInstance().getGameFileInfoById(0, 0, 5003729);
//			if(gameFileInfo!=null){
//				System.out.println(gameFileInfo.getgId());
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

package test.client.cache;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.common.cache.ICacheClient;
import cn.egame.common.cache.SCacheClient;
import cn.egame.common.client.EGameClientBase;
import cn.egame.common.exception.ExceptionCommonBase;
import cn.egame.common.model.PageData;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.EnumType.SearchSortType;
import cn.egame.interfaces.ck.EGameCacheKey;
import cn.egame.interfaces.ck.EGameCacheKeyV2;
import cn.egame.interfaces.gc.GameFileInfo;
import cn.egame.interfaces.gc.GameInfo;
import cn.egame.interfaces.gc.IGameService;

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
	
	public static void main(String[] args) {
		GameFileInfo gameFileInfo;
		try {
			gameFileInfo = EGameClientV2.getInstance().getGameFileInfoById(0, 0, 5003729);
			if(gameFileInfo!=null){
				System.out.println(gameFileInfo.getgId());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

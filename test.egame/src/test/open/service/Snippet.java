package test.open.service;

import java.rmi.RemoteException;

import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.interfaces.ErrorCode;
import cn.egame.interfaces.ExceptionCommon;
import cn.egame.interfaces.gc.xx.GameInfo;

public class Snippet {
	
	private int appId = 0;
	private long loginUserId = 0L;

	@Test
	public void testGetGameInfo() throws RemoteException{
		int gameId = 249871;
		GameInfo gameInfo = EGameClientV2.getInstance().getGameInfoById(appId, loginUserId, gameId);
		if (gameInfo == null) {
			throw new ExceptionCommon(ErrorCode.ParameterError, "game_id=" + gameId + "对应的gameinfo=" + gameInfo);
		}
		System.out.println("----------------");
		System.out.println(gameInfo.getGameCode());
	}
}


package test.client.file.dao;

import org.apache.log4j.Logger;

import cn.egame.common.exception.ExceptionCommonBase;

public class GameService {
	private static GameService instance = null;
	private static byte[] lock = new byte[0];
	private Logger log = Logger.getLogger(GameService.class);
	public static GameService getInstance() throws ExceptionCommonBase {
		if (instance == null) {
			synchronized (lock) {
				if (instance == null) {
					instance = new GameService();
				}
			}
		}
		return instance;
	}

	private GameFileDao gameFiledao = null;
	private GameImageDao gameImageDao = null;
	
	public GameService() throws ExceptionCommonBase {
		gameFiledao = new GameFileDao();
		gameImageDao = new GameImageDao();
	}
	
	public int getGIdByEfsIdFromGameImage(long efsId) throws ExceptionCommonBase {
		return gameImageDao.getGIdByEfsIdFromGameImage(efsId);
	}
	
	public int getGIdByEfsIdFromGameFile(long efsId) throws ExceptionCommonBase {
		return gameFiledao.getGIdByEfsIdFromGameFile(efsId);
	}
	
}

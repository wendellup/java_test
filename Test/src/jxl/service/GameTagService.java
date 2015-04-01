package jxl.service;

import jxl.dao.GameTagLinkDao;
import cn.egame.common.exception.ExceptionCommonBase;

public class GameTagService {
	private volatile static GameTagService instance = null;
    private static byte[] lock = new byte[0];
    private GameTagLinkDao dao;
    
    static public GameTagService getInstance() throws ExceptionCommonBase {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GameTagService();
                }
            }
        }
        return instance;
    }
    
    public GameTagService() throws ExceptionCommonBase {
        super();
        dao = new GameTagLinkDao();
    }
    
    public int getGameDownloadPoint(int gId) throws ExceptionCommonBase {
		return dao.getGameDownloadPoint(gId);
	}
}

package test.client.file.dao;

import org.apache.log4j.Logger;

import cn.egame.common.data.BaseDao;
import cn.egame.common.exception.ExceptionCommonBase;

public class GameFileDao extends BaseDao{
	private static Logger logger = Logger.getLogger(GameFileDao.class);
    
    public GameFileDao() throws ExceptionCommonBase {
        super("egame_game");
    }

    public int getGIdByEfsIdFromGameFile(long efsId) throws ExceptionCommonBase {
        String sql = " select g_id from t_game_file where efs_id = "+ efsId;
        logger.info(sql);
		return this.getInt(sql);
    }
}

package test.client.file.dao;

import org.apache.log4j.Logger;

import cn.egame.common.data.BaseDao;
import cn.egame.common.exception.ExceptionCommonBase;

public class GameImageDao extends BaseDao {
	private static Logger logger = Logger.getLogger(GameImageDao.class);

	public GameImageDao() throws ExceptionCommonBase {
		super("egame_game");
	}

	public int getGIdByEfsIdFromGameImage(long efsId) throws ExceptionCommonBase {
		String sql = " select g_id from t_game_image where efs_id = " + efsId;
		logger.info(sql);
		return this.getInt(sql);
	}
}

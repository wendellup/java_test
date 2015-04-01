package jxl.dao;

import cn.egame.common.data.BaseDao;
import cn.egame.common.exception.ExceptionCommonBase;

public class GameTagLinkDao extends BaseDao {

	public GameTagLinkDao() throws ExceptionCommonBase {
		super("egame_game");
	}

	public int getGameDownloadPoint(int gId) throws ExceptionCommonBase {
		String sql = " select game_sort_no from t_game_tag_link where tag_id = 488382 and g_id = "
				+ gId;
		return getInt(sql);
	}
}

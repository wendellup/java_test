package test.client.cache;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import cn.egame.client.EGameClientV2;
import cn.egame.common.model.PageData;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.EnumType.SearchSortType;

public class ClientCallTest {
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
}

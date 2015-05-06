package test.client.service;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

import cn.egame.common.model.PageData;
import cn.egame.search.client.biz.EGameGameSearchClientBiz;

public class SearchTest {
	
	@Test
	public void testSearchByName() throws RemoteException{
//		String key = "JAVA-appParameterById:2920";
		String keyWord = "捕鱼";
		PageData pd = EGameGameSearchClientBiz.getInstance().searchByName(keyWord, 0, 0, 20, 1105);
		
		if (pd != null && pd.getContent() != null && pd.getContent() instanceof List) {
            List<Integer> gids = (List<Integer>) pd.getContent();
            System.out.println(gids);
        }
	}
}

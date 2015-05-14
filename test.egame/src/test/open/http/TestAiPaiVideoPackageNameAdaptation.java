package test.open.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import test.open.utils.BaseUtils;
import cn.egame.client.EGameClientV2;
import cn.egame.common.util.Utils;
import cn.egame.interfaces.EnumType.GameType;
import cn.egame.interfaces.gc.GameFileInfo;
import cn.egame.interfaces.gc.GameInfo;

public class TestAiPaiVideoPackageNameAdaptation {
	private static String ALL_GAME_URL = "http://shouyou.aipai.com/aipai/api/games?sign=d96a0cbc3601e0424298386f1c260816"; 
	public static String FILE_PATH="C:\\Users\\yuchao\\Desktop\\aipaiadapter_20150512.txt";
	
	
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		long beginMillis = System.currentTimeMillis();
		int appId = 0;
		long loginUserId = 0L;
		PrintWriter pw = null;
		FileOutputStream fos = null;
		int num = 0;
		int matchCnt = 0;
		try {
//			fos = new FileOutputStream(new File(FILE_PATH), true);
//			pw = new PrintWriter(fos);
			pw = new PrintWriter(new FileOutputStream(new File(FILE_PATH), true));
			
			String url = ALL_GAME_URL;
			String jsonStr = BaseUtils.httpGet(url);
			System.out.println("xx");
			
			if(jsonStr!=null){
//			jsonStr = jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf(")"));
				ObjectMapper jsonMapper = new ObjectMapper();
				JsonNode jn = jsonMapper.readTree(jsonStr);
				Iterator<JsonNode> it = jn.iterator();
				
				while(it.hasNext() && num <=200){
					
					num++;
					JsonNode item = it.next();
					if(item.get("id")!=null
							&& item.get("name")!=null
							&& item.get("package")!=null){
						String gId = item.get("id").getTextValue();
						int gIdNum = Utils.toInt(gId, 0);
						if(gIdNum==0){
							continue;
						}
						String name = item.get("name").getTextValue();
						String packageName = item.get("package").getTextValue();
						pw.println("******gameId:"+gIdNum+", name:"+name+", packageName"+packageName+"******");
						System.out.println("num:"+num+"******gameId:"+gIdNum+", name:"+name+", packageName"+packageName+"******");
						
						//查询该package_name在我们平台是否有与爱拍对应的游戏
						List<GameType> gameTypeList = new ArrayList<GameType>();
			            gameTypeList.add(GameType.mobile);
						List<Integer> gameFileInfoIds = EGameClientV2.getInstance().listGameFileIdsByPackageNameAndGameType(0,
			                    0, packageName, gameTypeList);
			            if (gameFileInfoIds == null || gameFileInfoIds.size() <= 0) {
			                continue;
			            }
			            for (Integer gameFileInfoId : gameFileInfoIds) {
			            	List<Integer> gameIds = new ArrayList<Integer>();
			                GameFileInfo gameFileInfo = EGameClientV2.getInstance().getGameFileInfoById(appId, loginUserId, gameFileInfoId);
			                if (gameFileInfo == null)
			                    continue;
			                int currentGId = gameFileInfo.getgId();
			                GameInfo gameInfo = EGameClientV2.getInstance().getGameInfoById(appId, loginUserId, currentGId);
			                if(gameInfo!=null && gameInfo.getGameStatus()!=null
			                		&& gameInfo.getGameStatus().value()==6 && gameInfo.getGameType().value()==2){
			                	gameIds.add(gameInfo.getGId());
			                }
			                if(gameIds.size()>0){
			                	pw.println("----"+gameIds);
			                	matchCnt++;
			                }
			            }
			           
					}
				}
			}
			System.out.println("cost:"+(System.currentTimeMillis() - beginMillis));
			System.out.println("matchCnt:"+matchCnt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
//			if(fos!=null){
//				fos.flush();
//				fos.close();
//			}
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
		System.out.println("end");
	}
	
	public static String getMd5(String key){
//		String str = "xbgpyjwacUNZ5NxMiBav";
		String encodeStr = "";
		try {
			encodeStr = Utils.encryptMD5(key);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
}

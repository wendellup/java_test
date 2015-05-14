package test.open.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import test.open.utils.BaseUtils;
import cn.egame.common.util.Utils;

public class TestAiPaiVideoUrl {
	private static String ALL_GAME_URL = "http://shouyou.aipai.com/aipai/api/games?sign=d96a0cbc3601e0424298386f1c260816"; 
	public static String FILE_PATH="C:\\Users\\yuchao\\Desktop\\aipaiVideo_20150512.txt";
	
	private static Logger LOG = Logger.getLogger(TestAiPaiVideoUrl.class);
	static int not200StatusCode = 0;
	static int gameHasVideosNum = 0;
	static int videosNum = 0;
	
	public static void _main(String[] args) {
		String url = "http://fc14.aipai.com/user/43/4132043/100048/card/10373373/card.mp4?ip=1";
		System.out.println(httpGetCode(url));
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(FILE_PATH), true), "utf-8") );
			
			String url = ALL_GAME_URL;
			String jsonStr = BaseUtils.httpGet(url);
			if(jsonStr!=null){
//			jsonStr = jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf(")"));
				ObjectMapper jsonMapper = new ObjectMapper();
				JsonNode jn = jsonMapper.readTree(jsonStr);
				Iterator<JsonNode> it = jn.iterator();
				int num = 0;
				while(it.hasNext()){
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
						pw.println("********************gameId:"+gIdNum+", name:"+name+", packageName"+packageName+"***************");
						
						
						String gameVideoDetailUrl = "http://shouyou.aipai.com/aipai/api/ayx/videos?game_id="
								+ gIdNum + "&sign=" + getMd5(gIdNum+"xbgpyjwacUNZ5NxMiBav");
						System.out.println("第:"+num+"个游戏,详情url:"+gameVideoDetailUrl);
						String gameDetailStr = BaseUtils.httpGet(gameVideoDetailUrl);
						if(null!=gameDetailStr && ""!=gameDetailStr){
							
							ObjectMapper gameDetailStrJsonMap = new ObjectMapper();
							JsonNode videoInfoNodeList = gameDetailStrJsonMap.readTree(gameDetailStr);
							if(videoInfoNodeList!=null){
								Iterator<JsonNode> videoInfoNodeIt = videoInfoNodeList.iterator();
								int i=1;
								int curVideoNum = 0;
								while(videoInfoNodeIt.hasNext()){
									JsonNode videoNode = videoInfoNodeIt.next();
									if(videoNode.get("game_id")!=null
											&& videoNode.get("mp4")!=null
											&& videoNode.get("thumbnail")!=null
											&& videoNode.get("title")!=null
											&& videoNode.get("description")!=null
											&& videoNode.get("author")!=null){
										String gameId = videoNode.get("game_id").getTextValue();	
										String mp4 = videoNode.get("mp4").getTextValue();
										if(""!=mp4){
											curVideoNum++;
										}
										String thumbnail = videoNode.get("thumbnail").getTextValue();
										String title = videoNode.get("title").getTextValue();
										String description = videoNode.get("description").getTextValue();
										String author = videoNode.get("author").getTextValue();
										pw.println("---------------第"+(i++)+"个视频信息------------------------------------------");
										pw.println("game_id    :"+gameId);
										pw.println("mp4        :"+mp4);
										int code = httpGetCode(mp4);
										if(code!=200){
											not200StatusCode++;
											pw.println("非200 game_id  :"+gameId);
											pw.println("非200 mp4  :"+mp4);
										}
										System.out.println("not200StatusCode:"+not200StatusCode);
										pw.println("code       :"+code);
										pw.println("thumbnail  :"+thumbnail);
										pw.println("title      :"+title);
										pw.println("description:"+description);
										pw.println("author     :"+author);
										pw.println("");
									}
								}
								if(curVideoNum!=0){
									gameHasVideosNum++;
									videosNum = videosNum+curVideoNum;
								}
								System.out.println("gameHasVideosNum:"+gameHasVideosNum);
								System.out.println("videosNum:"+videosNum);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
//			if (jn.get("data") != null && jn.get("data").get(pkgName)!=null) {
//				JsonNode node = jn.get("data").get(pkgName);
//				String videoId = node.get("video_id")==null ? null: node.get("video_id").getTextValue();
//				String urlVal = node.get("url")==null ? null: node.get("url").getTextValue();
//				String letv_id = node.get("letv_id")==null ? null: node.get("letv_id").getTextValue();
//				
//				int originalUrlSize = node.get("originUrl")==null ? 0: node.get("originUrl").size();
//				pw.println("videoId    :"+videoId);
//				pw.println("url        :"+urlVal);
//				pw.println("letv_id    :"+letv_id);
//				pw.println("originalUrl:");
//				for(int i=0; i<originalUrlSize; i++){
//					pw.println("\t"+node.get("originUrl").get(i));
//				}
//			}
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
	
	public static int httpGetCode(String url) {
		CloseableHttpClient httpclient = null;
		HttpGet httpGet = null;
		try {
		    httpclient = HttpClients.createDefault();
		    httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			// System.out.println("-------------------------------------");
			// System.out.println(response.getStatusLine());
			// System.out.println("-------------------------------------");
			return response.getStatusLine().getStatusCode();
//			if (entity != null
//					&& entity.getContentType() != null
//					&& entity.getContentType().toString().trim()
//							.contains("application/json")) {
//				returnStr = EntityUtils.toString(entity);
//				;
//				EntityUtils.consume(entity);
//			}
		} catch (Exception ex) {
			return -1;
		} finally{
		    if(httpGet!=null){
		        httpGet.releaseConnection();
		    }
		    if(httpclient!=null){
		        try {
                    httpclient.close();
                } catch (IOException e) {
                	LOG.error("", e);
                }
		    }
		}
	}
}

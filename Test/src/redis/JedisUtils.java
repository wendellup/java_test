package redis;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.egame.common.util.Utils;


public class JedisUtils {
	private static Logger logger = Logger.getLogger(JedisUtils.class);
	
	private static final String REDIS_SERVER = "192.168.70.149";
	private static final int REDIS_PORT = 6379;
	private static final int MaxActive = 500;
	private static final int MaxIdle = 10;
	private static final long MaxWait = 5000l;
//	private Jedis jedis;// 非切片额客户端连接
	private JedisPool jedisPool;// 非切片连接池
	private static JedisUtils instance;
	private JedisUtils() {
		initialPool();
	}
	private void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(MaxActive);
		config.setMaxIdle(MaxIdle);
		config.setMaxWait(MaxWait);
		//config.setWhenExhaustedAction(whenExhaustedAction)
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, REDIS_SERVER, REDIS_PORT);
	}
	public synchronized static JedisUtils getInstance() {
		if (instance == null) {
			instance = new JedisUtils();
		}
		return instance;
	}
//	public void doOperation(JedisOperation operation) {
//		jedis = jedisPool.getResource();
//		operation.doProcee(jedis);
//		jedisPool.returnResource(jedis);
//		jedis = null;
//	}

	public static abstract class JedisOperation {
		public static final String PREFIX = "realtime";
		public static final String DETELITER = ",";
		public static final String REPLACE = "&;&";
		public abstract void doProcee(Jedis jedis);
		public abstract String buildPrefix();
		protected String buildRedisKey(String key) {
			if (key == null || "".equals(key)) {
				return PREFIX + DETELITER + buildPrefix();
			}
			return PREFIX + DETELITER + buildPrefix() + DETELITER + key;
		}
	}
	
	public String setString(String key, String val){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				return jedis.set(key, val);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		
		return null;
	}
	
	public Set<String> keys(String patternStr) {
		Jedis jedis = null;
		Set<String> matchKeys = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				matchKeys = jedis.keys(patternStr);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
		return matchKeys;
	}
	
	public int getInt(String key, int defaultValue) {
		Jedis jedis = null;
		Object obj = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				obj = jedis.get(key);
				if (obj == null) {
					return defaultValue;
				}
				return Integer.valueOf(String.valueOf(obj)).intValue();
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return defaultValue;
	}
	
	public List<Integer> mGetInteger(String... keys) {
		Jedis jedis = null;
		List<String> list;
		List<Integer> intList = new ArrayList<Integer>();
		if(keys==null){
			return intList;
		}
			
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				list = jedis.mget(keys);
				if(list!=null){
					for(String str : list){
						intList.add(Utils.toInt(str, 0));
					}
				}
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return intList;
	}
	
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		List<String> list = null;
		try {
			jedis = jedisPool.getResource();
			if(jedis!=null){
				list = jedis.hmget(key, fields);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
				jedis = null;
			}
		}
		return list;
	}
	
	public List<String> getMapValue(String key) {
		Jedis jedis = null;
        List<String> list = null;
            
        try {
            jedis = jedisPool.getResource();
            if(jedis!=null){
                list = jedis.hvals(key);
            }
        } catch (Exception ex) {
            logger.error("", ex);
        } finally{
            if(jedis!=null){
                jedisPool.returnResource(jedis);
                jedis = null;
            }
        }
        return list;
    }
	
	public static void main(String[] args) throws ParseException {
		Utils.initLog4j();
		
		String ret = JedisUtils.getInstance().setString("702", "{\"code\":0,\"text\":\"success\",\"ext\":{\"ref_active_mark_hidden\":[{\"game_id\":5008336},{\"game_id\":5013035},{\"game_id\":5015246},{\"game_id\":5013102},{\"game_id\":5018117},{\"game_id\":5023064}],\"ref_classic_status\":[{\"game_id\":5008336},{\"game_id\":5013102}],\"ref_limitfree_status\":[],\"ref_new_status\":[],\"ref_hot_status\":[{\"game_id\":249871},{\"game_id\":5007326},{\"game_id\":5016755},{\"game_id\":5017441},{\"game_id\":5022850},{\"game_id\":5018117}],\"channel_page_type\":1,\"ref_active_mark\":[{\"game_id\":5008336},{\"game_id\":5013102},{\"game_id\":5018117},{\"game_id\":5023064}],\"ref_tag_list\":[],\"main\":{\"content\":{\"game_list\":[{\"version\":\"3.0.5\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5008336,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5008336\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"刀塔传奇（抢钻石红包）\",\"game_size\":182430683,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/956/adf5c128h1ddb2d7.jpg\",\"game_label\":\"减压,卡牌,角色扮演,策略,经典\",\"game_download_count\":784076,\"package_name\":\"sh.lilith.dgame.aiyouxi\",\"version_code\":\"36265\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5008336\",\"game_recommend_word\":\"龙骑士魂匣来袭，挑战永生梦境\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356001900\",\"last_online_time\":\"2014-10-24 16:27:10\",\"game_download_count_week\":55347,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"d0d231fcf910436e60fe09566558ec76\",\"is_free_install\":0},{\"version\":\"1.0.4\",\"class_name\":\"动作冒险\",\"price\":0,\"network_type\":11,\"game_id\":5013035,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5013035\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"铠甲勇士之英雄传说\",\"game_size\":40971926,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/943/b5dc8160h1da961b.png\",\"game_label\":\"卡通,竖版跑酷,综艺影视\",\"game_download_count\":1276998,\"package_name\":\"com.joym.armorhero\",\"version_code\":\"6\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5013035\",\"game_recommend_word\":\"完美剧情，《铠甲勇士》正义出击\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256002468\",\"last_online_time\":\"2014-09-29 09:42:32\",\"game_download_count_week\":96860,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1005,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1005&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/949/6a7fbf56h1dbf916.jpg\",\"game_file_md5\":\"a56378c4decca265d58c61c3ad657b84\",\"is_free_install\":1},{\"version\":\"1.21\",\"class_name\":\"益智休闲\",\"price\":0,\"network_type\":11,\"game_id\":249871,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=249871\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"开心消消乐\",\"game_size\":22908438,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/945/56dd8373h1db12ab.jpg\",\"game_label\":\"卡通,减压,消除,热门,年度大作\",\"game_download_count\":2276735,\"package_name\":\"com.happyelements.AndroidAnimal\",\"version_code\":\"21\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=249871\",\"game_recommend_word\":\"萌动雪怪出击，春节拜年送红包！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110222438027\",\"last_online_time\":\"2014-04-03 20:52:51\",\"game_download_count_week\":247395,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1007,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1007&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"bba1416f4f49624694ceb16153909d14\",\"is_free_install\":1},{\"version\":\"1.0.7\",\"class_name\":\"益智休闲\",\"price\":0,\"network_type\":11,\"game_id\":5007326,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5007326\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"捕鱼达人3\",\"game_size\":35515746,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/979/854452b1h1e33a48.jpg\",\"game_label\":\"减压,圣诞活动,年度大作\",\"game_download_count\":2255622,\"package_name\":\"org.cocos2d.fishingjoy3\",\"version_code\":\"107\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5007326\",\"game_recommend_word\":\"全新美术风格，尽享3D视觉盛宴\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256000261\",\"last_online_time\":\"2014-07-06 09:43:07\",\"game_download_count_week\":122187,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1007,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1007&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/979/22da2fd9h1e33a69.jpg\",\"game_file_md5\":\"a9fe89a0226475241bb9613a25468954\",\"is_free_install\":0},{\"version\":\"2.5.0\",\"class_name\":\"动作冒险\",\"price\":0,\"network_type\":12,\"game_id\":5016755,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5016755\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"剑魂之刃（无限连击）\",\"game_size\":138650941,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/884/217d3eb1h1cc2d20.png\",\"game_label\":\"动作,角色扮演,冒险,竞技,精品\",\"game_download_count\":184736,\"package_name\":\"com.SmartSpace.TheSoulOfSwordFury.Android.egameuser\",\"version_code\":\"2500\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5016755\",\"game_recommend_word\":\"暴爽连招，打造横版格斗新纪元\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356003882\",\"last_online_time\":\"2015-01-06 10:01:44\",\"game_download_count_week\":35181,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1005,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1005&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"20fc4ae8f9390eccb4f61c50e22d3e96\",\"is_free_install\":1},{\"version\":\"1.3\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":11,\"game_id\":249926,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=249926\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"果宝三国\",\"game_size\":37454254,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/961/cd2dcc28h1deec5c.jpg\",\"game_label\":\"休闲,角色扮演,卡通,三国,Q萌\",\"game_download_count\":3018525,\"package_name\":\"com.centurysoft.fruityrobo\",\"version_code\":\"4\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=249926\",\"game_recommend_word\":\"经典完美还原，果宝特工玩穿越\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110222438099\",\"last_online_time\":\"2014-04-04 16:47:05\",\"game_download_count_week\":112322,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/991/c0b9b5d8h1e6302a.jpg\",\"game_file_md5\":null,\"is_free_install\":0},{\"version\":\"V1.0.3\",\"class_name\":\"益智休闲\",\"price\":0,\"network_type\":11,\"game_id\":5017441,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5017441\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"喜羊羊快跑\",\"game_size\":27827262,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/971/b39b6d3eh1e167b0.png\",\"game_label\":\"竖版跑酷,休闲,Q萌,精品,中国风\",\"game_download_count\":305881,\"package_name\":\"com.cmge.xyykp\",\"version_code\":\"3\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5017441\",\"game_recommend_word\":\"草原狂奔！和喜羊羊一起玩跑酷\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256004139\",\"last_online_time\":\"2014-12-16 09:56:29\",\"game_download_count_week\":134084,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1007,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1007&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/963/37b19825h1df5cd4.jpg\",\"game_file_md5\":\"b6086fe84e61ff5a909329ce1370cc6e\",\"is_free_install\":1},{\"version\":\"1.0.1\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5021826,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5021826\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"铁血九州\",\"game_size\":113134389,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/964/ae32c472h1dfb3c6.jpg\",\"game_label\":\"热血,RPG,刺激,历史\",\"game_download_count\":3730,\"package_name\":\"nine.sky.wzol.app_txjz_yd\",\"version_code\":\"101\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5021826\",\"game_recommend_word\":\"一款超越经典的传奇之作！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356005708\",\"last_online_time\":\"2015-02-17 09:28:54\",\"game_download_count_week\":5578,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"2d6c8f6b737d00d0b412e1650bf3f4c0\",\"is_free_install\":0},{\"version\":\"1.12.3\",\"class_name\":\"跑酷赛车\",\"price\":0,\"network_type\":11,\"game_id\":240056,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=240056\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"神庙逃亡2\",\"game_size\":35764232,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/504/f2e52e26h16f55a1.jpg\",\"game_label\":\"竖版跑酷,年度大作\",\"game_download_count\":10388367,\"package_name\":\"com.imangi.templerun2.paid\",\"version_code\":\"4526\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=240056\",\"game_recommend_word\":\"新入萌宠、装扮系统，跑酷无极限\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110221817701\",\"last_online_time\":\"2014-04-18 15:59:50\",\"game_download_count_week\":260874,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1014,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1014&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/991/99583db5h1e629ad.jpg\",\"game_file_md5\":\"b2784704bd495ac9ef31b7c8f0d4d9d3\",\"is_free_install\":1},{\"version\":\"2.4.9\",\"class_name\":\"动作冒险\",\"price\":0,\"network_type\":11,\"game_id\":5012899,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5012899\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"饥饿鲨进化\",\"game_size\":60674500,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/938/56b20280h1d9537e.jpg\",\"game_label\":\"冒险,3D,奇幻,养成,精品\",\"game_download_count\":282638,\"package_name\":\"com.fgol\",\"version_code\":\"39\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5012899\",\"game_recommend_word\":\"3D深海，弱肉强食的游戏\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256004412\",\"last_online_time\":\"2015-01-15 09:41:56\",\"game_download_count_week\":77861,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1005,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1005&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"92f74c8272e2e75c2361104f0cfc9308\",\"is_free_install\":1},{\"version\":\"1.2.0\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5021422,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5021422\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"全民奇迹（官方版）\",\"game_size\":213407346,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/957/91d1c909h1dde0f0.png\",\"game_label\":\"角色扮演,3D,经典,动作,竞技\",\"game_download_count\":112477,\"package_name\":\"com.tianmashikong.qmqj.ewan.lovegame\",\"version_code\":\"120\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5021422\",\"game_recommend_word\":\"强势回归，MU奇迹正版授权\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356005329\",\"last_online_time\":\"2015-01-21 09:48:48\",\"game_download_count_week\":32549,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"659a4364fbbef8591f0620af09f5e809\",\"is_free_install\":0},{\"version\":\"2.3.0\",\"class_name\":\"跑酷赛车\",\"price\":0,\"network_type\":11,\"game_id\":5015246,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5015246\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"奔跑吧兄弟：我是车神\",\"game_size\":33792684,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/979/65947c4ah1e33b52.jpg\",\"game_label\":\"赛车,热门,综艺影视,年度大作\",\"game_download_count\":687265,\"package_name\":\"com.coco.entertainment.immortalracer\",\"version_code\":\"13\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5015246\",\"game_recommend_word\":\"Runningman上演新年飙车大戏！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256003782\",\"last_online_time\":\"2014-11-21 10:24:06\",\"game_download_count_week\":90540,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1014,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1014&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/979/22775e96h1e33b34.jpg\",\"game_file_md5\":\"4a1e10449551f1e044b79299e238b1f9\",\"is_free_install\":0},{\"version\":\"1.0.2\",\"class_name\":\"跑酷赛车\",\"price\":0,\"network_type\":11,\"game_id\":5022850,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5022850\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"熊出没2\",\"game_size\":49605231,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/002/019/2346d1d4h1ed1e15.jpg\",\"game_label\":\"精品,动漫,首发,跑酷,刺激\",\"game_download_count\":340206,\"package_name\":\"com.joym.xiongchumo2\",\"version_code\":\"4\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5022850\",\"game_recommend_word\":\"官方正版，熊大带你奔跑带你飞\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256005891\",\"last_online_time\":\"2015-02-06 16:13:01\",\"game_download_count_week\":177456,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1014,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1014&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/977/f305fcb1h1e2bfc5.jpg\",\"game_file_md5\":\"08f959c6436c7078998b4d1237f57035\",\"is_free_install\":1},{\"version\":\"1.0.0\",\"class_name\":\"策略塔防\",\"price\":0,\"network_type\":11,\"game_id\":5022663,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5022663\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"植物战僵尸王2\",\"game_size\":8422892,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/968/468c1a0eh1e0a825.jpg\",\"game_label\":\"休闲,益智,僵尸,精品,卡通\",\"game_download_count\":45426,\"package_name\":\"com.egame.zwzjsw2\",\"version_code\":\"1\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5022663\",\"game_recommend_word\":\"希望之战！击败最恐怖的僵尸王\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256005674\",\"last_online_time\":\"2015-02-05 09:43:49\",\"game_download_count_week\":40253,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1002,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1002&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"79b03fe5193f9e341d743d51d1dbdb50\",\"is_free_install\":1},{\"version\":\"1.3.0\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5013102,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5013102\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"去吧皮卡丘（全民宝贝）\",\"game_size\":70960732,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/989/555cc0c4h1e5b0c4.jpg\",\"game_label\":\"卡通,卡牌,二次元,改编\",\"game_download_count\":757446,\"package_name\":\"net.crimoon.pm.ayx\",\"version_code\":\"130\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5013102\",\"game_recommend_word\":\"皮卡丘来了！激动人心的卡牌对战\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356003343\",\"last_online_time\":\"2014-11-13 09:51:05\",\"game_download_count_week\":75221,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/989/20a0af9fh1e5b299.jpg\",\"game_file_md5\":\"ac6024beffc0e8c6471da76a815a074b\",\"is_free_install\":0},{\"version\":\"1.5.0\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5018117,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5018117\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"征途（爱游戏版）\",\"game_size\":168444392,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/929/4d878518h1d71d9f.png\",\"game_label\":\"角色扮演,仙侠,RPG,经典,竞技\",\"game_download_count\":191032,\"package_name\":\"com.ztgame.ztmobiletest.egame\",\"version_code\":\"1010\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5018117\",\"game_recommend_word\":\"经典网游移植，真实模拟国战纷争\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356004134\",\"last_online_time\":\"2014-12-30 10:00:34\",\"game_download_count_week\":18607,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":null,\"game_file_md5\":\"3f0e18282410cd1987482a1e5c2a446b\",\"is_free_install\":1},{\"version\":\"1.3.8\",\"class_name\":\"体育竞技\",\"price\":0,\"network_type\":11,\"game_id\":5007874,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5007874\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"3D终极狂飙4\",\"game_size\":13458687,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/972/3567b713h1e18cbe.jpg\",\"game_label\":\"赛车,3D,刺激,精品,速度\",\"game_download_count\":240851,\"package_name\":\"com.xiaoao.car3d4single\",\"version_code\":\"38\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5007874\",\"game_recommend_word\":\"人气经典续作，飙车党不容错过！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256002869\",\"last_online_time\":\"2014-10-22 11:27:58\",\"game_download_count_week\":51648,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1009,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1009&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/973/0f09b0bbh1e1d877.jpg\",\"game_file_md5\":\"b452803792e63d720212c0a06baf323e\",\"is_free_install\":1},{\"version\":\"1.0.5.5\",\"class_name\":\"角色扮演\",\"price\":0,\"network_type\":12,\"game_id\":5023064,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5023064\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"高达战争\",\"game_size\":165967322,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/977/64350fcdh1e2b8bb.jpg\",\"game_label\":\"\",\"game_download_count\":122816,\"package_name\":\"com.wuyou.gundam.igame\",\"version_code\":\"11\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5023064\",\"game_recommend_word\":\"爆种觉醒！自由正义火力全开！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"120356005884\",\"last_online_time\":\"2015-02-05 18:26:58\",\"game_download_count_week\":65497,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1003,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1003&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/977/45761f08h1e2b8c2.jpg\",\"game_file_md5\":\"d27ce4e652954f88d8e9d6d6cefa5493\",\"is_free_install\":1},{\"version\":\"1.8\",\"class_name\":\"益智休闲\",\"price\":0,\"network_type\":11,\"game_id\":248461,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=248461\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"植物大作战菜园版\",\"game_size\":11106726,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/547/7672f4c9h179d430.jpg\",\"game_label\":\"塔防,精品,休闲,蔬菜,经典\",\"game_download_count\":409050,\"package_name\":\"com.zj.whackmole2\",\"version_code\":\"9\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=248461\",\"game_recommend_word\":\"保卫战打响，农药味十足！\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110222416109\",\"last_online_time\":\"2014-10-30 10:26:10\",\"game_download_count_week\":3552,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1007,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1007&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/949/ede3aaebh1dbf166.jpg\",\"game_file_md5\":null,\"is_free_install\":1},{\"version\":\"3.2.5\",\"class_name\":\"跑酷赛车\",\"price\":0,\"network_type\":11,\"game_id\":5019854,\"game_detail_url\":\"http://open.play.cn/api/v2/mobile/game_detail.json?terminal_id=100&game_id=5019854\",\"game_type\":\"2\",\"tcl_app_id\":null,\"game_name\":\"神偷奶爸：小黄人快跑（果酱工厂版）\",\"game_size\":86194901,\"game_icon\":\"http://cdn.play.cn/f/pkg/ph/000/001/951/d2299715h1dc55d5.jpg\",\"game_label\":\"竖版跑酷,休闲,精品,Q萌,3D\",\"game_download_count\":261683,\"package_name\":\"com.gameloft.android.ANMP.GloftDMCN\",\"version_code\":\"3250000\",\"game_download_url\":\"http://open.play.cn/api/v2/mobile/down.json?terminal_id=100&game_id=5019854\",\"game_recommend_word\":\"超萌小黄人，“火山岛”进驻果酱工厂\",\"is_game_adaptation\":1,\"is_charged\":0,\"game_code\":\"110256005096\",\"last_online_time\":\"2015-01-28 20:37:20\",\"game_download_count_week\":158825,\"is_mouse\":0,\"is_keyboard\":0,\"is_remote_control\":0,\"is_handle\":0,\"is_sense\":0,\"is_mobile_control\":0,\"class_id\":1014,\"class_url\":\"http://open.play.cn/api/v2/mobile/channel/content.json?channel_id=1014&terminal_id=100&current_page=0&rows_of_page=20&order_id=1105\",\"game_adv_photo\":\"http://cdn.play.cn/f/pkg/ad/000/001/978/11f1e9a8h1e30b47.jpg\",\"game_file_md5\":\"ffe7b6c1b49ae89df613a5f9ec7784e4\",\"is_free_install\":1}],\"channel_description\":null,\"channel_title\":\"推荐\",\"channel_tag_name\":null},\"total\":4210,\"rows_of_page\":20,\"current_page\":0},\"ref_open_status\":[],\"ref_gift_status\":[],\"ref_game_quality\":null,\"ref_game_status\":{\"game_gift\":[{\"game_id\":5008336},{\"game_id\":5013035},{\"game_id\":5007326},{\"game_id\":5016755},{\"game_id\":5021422},{\"game_id\":5015246},{\"game_id\":5022850},{\"game_id\":5013102},{\"game_id\":5018117},{\"game_id\":5007874},{\"game_id\":5023064}],\"game_new_server\":[{\"game_id\":5008336},{\"game_id\":5021422},{\"game_id\":5013102}],\"game_new_version\":[],\"game_raiders\":[{\"game_id\":5008336},{\"game_id\":5007326},{\"game_id\":5013102}],\"game_awards\":[],\"game_match\":[],\"game_test\":[]},\"ref_network_status\":[],\"ref_first_status\":[],\"ref_chinese_status\":[]}}");
		System.out.println(ret);
    }
		
}

/**
 * 
 */
package cn.egame.common.cache;

import redis.clients.jedis.JedisPubSub;

/**
 * Description TODO
 * 
 * @ClassName ICacheMessagePubSub
 * 
 * @Copyright 炫彩互动
 * 
 * @Project egame.common
 * 
 * @Author baifan
 * 
 * @Create Date 2013-12-25
 * 
 * @Modified by none
 * 
 * @Modified Date
 */

public interface ICacheMessagePubSub {

    public void subscribe(JedisPubSub pubsub, String... channel);

    public void psubscribe(JedisPubSub pubsub, String... partten);

    public void publishMessage(String channel, String message);

}

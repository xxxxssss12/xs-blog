package com.xs.blog.redis;

import com.xs.blog.logging.LoggerWithMDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by xs on 2018/7/10
 */
@Component
public class JedisConnector {
    private static LoggerWithMDC logger = LoggerWithMDC.getLogger(JedisConnector.class,"redis");

    private static JedisPool pool;

    private static Integer poolMaxActive;
    private static Integer poolMaxIdle;
    private static Boolean poolTestOnBorrow;
    private static Boolean poolTestOnReturn;
    private static Integer poolTimeout;
    private static String ip;
    private static Integer port;
    private static String password;

    @Value("${jedis.pool.maxActive}")
    public void setPoolMaxActive(Integer poolMaxActive) {
        JedisConnector.poolMaxActive = poolMaxActive;
    }

    @Value("${jedis.pool.maxIdle}")
    public void setPoolMaxIdle(Integer poolMaxIdle) {
        JedisConnector.poolMaxIdle = poolMaxIdle;
    }

    @Value("${jedis.pool.testOnBorrow}")
    public void setPoolTestOnBorrow(Boolean poolTestOnBorrow) {
        JedisConnector.poolTestOnBorrow = poolTestOnBorrow;
    }

    @Value("${jedis.pool.testOnReturn}")
    public void setPoolTestOnReturn(Boolean poolTestOnReturn) {
        JedisConnector.poolTestOnReturn = poolTestOnReturn;
    }

    @Value("${jedis.pool.timeout}")
    public void setPoolTimeout(Integer poolTimeout) {
        JedisConnector.poolTimeout = poolTimeout;
    }

    @Value("${jedis.ip}")
    public void setIp(String ip) {
        JedisConnector.ip = ip;
    }

    @Value("${jedis.port}")
    public void setPort(Integer port) {
        JedisConnector.port = port;
    }

    @Value("${jedis.password}")
    public void setPassword(String password) {
        if (StringUtils.isEmpty( password)) return;
        JedisConnector.password = password;
    }

    public JedisConnector() {}

    public static Jedis getJedis(){

        return getPool().getResource();
    }

    public static JedisPool getPool() {
        if (pool == null) {
            synchronized (JedisConnector.class) {
                if (pool == null) {
                    logger.info("jedisConnector init start!");
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(poolMaxActive);
                    config.setMaxIdle(poolMaxIdle);
                    config.setTestOnBorrow(poolTestOnBorrow);
                    config.setTestOnReturn(poolTestOnReturn);
                    if (StringUtils.isEmpty(password)) {
                        pool = new JedisPool(config, ip, port, poolTimeout);
                    } else {
                        pool = new JedisPool(config, ip, port, poolTimeout, password);
                    }
                    logger.info("jedisConnector init over!host={}:{}", ip, port);
                }
            }
        }
        return pool;
    }

    public static boolean set(String key,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            String result = jedis.set(key,value);
            if (result.equals("OK")){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }

    public static boolean set(String key,int expire,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            String result = jedis.setex(key,expire,value);
            if (result.equals("OK")){
                return true;
            }
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }

    public static boolean set(String key, String value, String nxxx, String expx, int time){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            String result = jedis.set(key,value,nxxx,expx,time);
            if (result.equals("OK")){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }



    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try{
            jedis=getJedis();
            result = jedis.get(key);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return result;
    }

    public static Set<String> keys(String pattern){
        Jedis jedis =null;
        Set<String> result = null;
        try{
            jedis=getJedis();
            result = jedis.keys(pattern);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return result;
    }


    public static boolean del(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            if(jedis.del(key)>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }

    public static boolean exists(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return jedis.exists(key);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }

    /**
     * 头入队列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long  lpush(String key,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return   jedis.lpush(key, value);
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 未入队列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long rpush(String key,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return   jedis.rpush(key, value);
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 从左出队列
     *
     * @param key
     * @return
     */
    public static String lpop(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return   jedis.lpop(key);
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 从右出队列
     *
     * @param key
     * @return
     */
    public static String rpop(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return   jedis.rpop(key);
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * lrange quue length
     *
     * @param key
     * @return
     */
    public static Long llen(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return   jedis.llen(key);
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 有序集合
     *
     * @param key
     * @param value
     * @return
     */
    public static void addSet(String key,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            Long score = jedis.zcard(key);
            jedis.zadd(key, ++score, value);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 得到有序集合
     *
     * @param key
     * @return
     */
    public static Set<String> getSet(String key){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            Set<String> sets = jedis.zrange(key, 0, -1);
            return  sets;
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    public static String loadScript(String script){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return  jedis.scriptLoad(script);
        }catch (Exception e){
            logger.error(e);
        }finally{
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }

    /**
     * 分页得到有序集合
     *
     * @param key
     * @param size
     * @return
     */
    public static Set<String> zrange(String key, int size) {
        Jedis jedis =null;
        try{
            jedis=getJedis();
            Set<String> sets = jedis.zrange(key, 0, size);
            return  sets;
        }catch (Exception e){
            logger.error(e);
            return null;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    /**
     * 删除指定的元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long zrem(String key, String... values) {
        Jedis jedis =null;
        try{
            jedis=getJedis();
            Long count = jedis.zrem(key, values);
            return  count;
        }catch (Exception e){
            logger.error(e);
            return 0l;
        }finally {
            if(jedis!=null)
                jedis.close();
        }
    }

    public static Map<String, String> hget(String key){
        Jedis jedis =null;
        try{
            jedis=getJedis();
            return jedis.hgetAll(key);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }

    public static String hget(String key,String field){
        Jedis jedis =null;
        try{
            jedis=getJedis();
            return jedis.hget(key, field);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return null;
    }

    public static long hset(String key,String field,String value){
        Jedis jedis = null;
        try{
            jedis=getJedis();
            return jedis.hset(key, field, value);
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return -1L;
    }

    /**
     * @param key
     * @param fields
     * @return 0 is success, -1 is failed
     */
    public static long hset(String key,Map<String,String> fields){
        Jedis jedis =null;
        try{
            jedis=getJedis();
            if(jedis.hmset(key, fields).equalsIgnoreCase("OK")){
                return 0L;
            }
        }catch (Exception e){
            logger.error(e);
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return -1L;
    }

    public static long expire(String key, int expire){
        Jedis jedis = null;
        try{
            jedis =getJedis();
            return jedis.expire(key, expire);
        }catch(Exception e){
            logger.error(e);
        }finally{
            if(jedis !=null)
                jedis.close();
        }
        return -1L;
    }

    /**
     *
     * value++
     *
     * @param key
     * @return 自增后的值
     * @author chenqingwu
     *
     * @date 2016-3-11 16:03:46
     */
    public static long incr(String key){
        Jedis jedis = null;
        try{
            jedis =getJedis();
            return jedis.incr(key);
        }catch(Exception e){
            logger.error(e);
            return -1L;
        }finally{
            if(jedis !=null)
                jedis.close();
        }
    }

    /**
     *
     * value++
     *
     * @param key
     * @return 自增后的值
     * @author chenqingwu
     *
     * @date 2016-3-11 16:03:46
     */
    public static long decr(String key){
        Jedis jedis = null;
        try{
            jedis =getJedis();
            return jedis.decr(key);
        }catch(Exception e){
            logger.error(e);
            return -1L;
        }finally{
            if(jedis !=null)
                jedis.close();
        }
    }


    public static long batchSet(List<String> keys, List<String> values){
        Jedis jedis = null;
        try{
            jedis = getJedis();
            Pipeline pipeline = jedis.pipelined();
            for(int i=0; i< keys.size(); i++){
                pipeline.set(keys.get(i), values.get(i));
            }
            pipeline.sync();
            return 0L;
        }catch(Exception e){
            logger.error(e);
            return -1L;
        }finally {
            if(jedis != null)
                jedis.close();
        }
    }

    /**
     * 单个jedis链接批量插入
     *
     * @param count
     */
    public static void single(int count){
        Jedis jedis = getJedis();
        for(int i=0; i<count; i++){
            jedis.set(i+"", 2*i + "");
        }
        jedis.close();
    }

    /**
     * 使用pipe批量插入
     *
     * @param count
     */
    public static void batch(int count){
        Jedis jedis = getJedis();
        Pipeline p = jedis.pipelined();

        for(int i=0; i<count; i++){
            p.set(i+ "", 2*i + "");
        }
        p.sync();
        jedis.close();
    }

//    public static Object eval(String script, List<String> keys, List<String> args) {
//        Jedis jedis = null;
//        try {
//            jedis = getJedis();
//            return jedis.eval(script, keys, args);
//        } catch (Exception e) {
//            logger.error(e);
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//        return null;
//    }
}

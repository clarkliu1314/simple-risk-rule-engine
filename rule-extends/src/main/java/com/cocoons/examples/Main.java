package com.cocoons.examples;

import redis.clients.jedis.Jedis;

/**
 *
 * @author qinguofeng
 */
public class Main {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost",6379);
		jedis.lpush("fooo".getBytes(), "bar".getBytes());
		String value = new String(jedis.rpop("fooo".getBytes()));
		System.out.println(value);
	}
}

package com.cocoons.examples;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.cocoons.util.queue.RedisQueue;

/**
 *
 * @author qinguofeng
 */
public class RedisQueueTest {
	public static void main(String[] args) {
		JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
		final int COUNT = 100;
		final int SIZE = 50;
		final String KEY = "bvmbnmvbnmgfhjfghjutyiyusghfgxcvbxcvbdfagrewtqertq";
		final RedisQueue queue = new RedisQueue(pool, KEY);
		Thread t[] = new Thread[2 * SIZE];
		for (int i = 0; i < SIZE; i++) {
			t[i] = new Thread(new Runnable() {
				public void run() {
					try {
						long ts = System.currentTimeMillis();
						for (int i = 0; i < COUNT; i++) {
							String str = new String(queue.take());
//							 System.out.println(str);
						}
						long te = System.currentTimeMillis();
						System.out.println("Take:" + (te - ts));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		for (int i = 0; i < SIZE; i++) {
			t[i + SIZE] = new Thread(new Runnable() {
				public void run() {
					try {
						long ts = System.currentTimeMillis();
						for (int i = 0; i < COUNT; i++) {
							queue.put(("Test:" + i).getBytes());
						}
						long te = System.currentTimeMillis();
						System.out.println("Put:" + (te - ts));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		for (int i = 0; i < 2 * SIZE; i++) {
			t[i].start();
		}

		try {
			for (int i = 0; i < 2 * SIZE; i++) {
				t[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(pool.getResource().llen(KEY.getBytes()));
		
		System.out.println("END.");
	}
}

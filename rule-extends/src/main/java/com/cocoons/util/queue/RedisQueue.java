package com.cocoons.util.queue;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author qinguofeng
 */
public class RedisQueue {

	private JedisPool mJedis;
	private byte[] mKey;

	private final AtomicLong mCount;
	private final ReentrantLock mTakeLock = new ReentrantLock();
	private final Condition mNotEmpty = mTakeLock.newCondition();
	private final ReentrantLock mPutLock = new ReentrantLock();
	private final Condition mNotFull = mPutLock.newCondition();

	private final int mCapacity;

	public RedisQueue(JedisPool pool, String key) {
		mJedis = pool;
		mKey = key.getBytes();

		Jedis jedis = mJedis.getResource();
		try {
			mCount = new AtomicLong(jedis.llen(mKey));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		mCapacity = Integer.MAX_VALUE;
	}

	private void signalNotFull() {
		mPutLock.lock();
		try {
			mNotFull.signal();
		} finally {
			mPutLock.unlock();
		}
	}

	private void signalNotEmpty() {
		mTakeLock.lock();
		try {
			mNotEmpty.signal();
		} finally {
			mTakeLock.unlock();
		}
	}

	/**
	 * 获取并移除此队列的头部，在元素变得可用之前一直等待
	 * 
	 * @throws InterruptedException
	 * */
	public byte[] take() throws InterruptedException {
		final AtomicLong count = mCount;
		final ReentrantLock takeLock = mTakeLock;
		final Condition notEmpty = mNotEmpty;
		final int capacity = mCapacity;

		long size = -1;
		byte[] value = null;

		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) {
				notEmpty.await();
			}

			Jedis jedis = mJedis.getResource();
			try {
				value = jedis.rpop(mKey);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}

			size = count.getAndDecrement();
			if (size > 1) {
				notEmpty.signal();
			}
		} finally {
			takeLock.unlock();
		}

		if (size == capacity) {
			signalNotFull();
		}

		return value;
	}

	/**
	 * 将指定元素插入到此队列的尾部，在成功时返回 true, 在位置变的可用之前阻塞
	 * */
	public void put(byte[] t) throws InterruptedException {
		if (t == null)
			throw new NullPointerException();

		final AtomicLong count = mCount;
		final ReentrantLock putLock = mPutLock;
		final Condition notFull = mNotFull;
		final int capacity = mCapacity;
		final byte[] key = mKey;

		long size = -1;

		putLock.lockInterruptibly();
		try {
			while (count.get() == capacity) {
				notFull.await();
			}

			Jedis jedis = mJedis.getResource();
			try {
				jedis.lpush(key, t);
			} finally {
				if (jedis != null) {
					jedis.close();
				}
			}

			size = count.getAndIncrement();
			if (size < (capacity - 1)) {
				notFull.signal();
			}
		} finally {
			putLock.unlock();
		}

		if (size == 0) {
			signalNotEmpty();
		}
	}
}

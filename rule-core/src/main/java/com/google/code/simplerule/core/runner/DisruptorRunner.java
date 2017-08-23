package com.google.code.simplerule.core.runner;

import java.util.Map;
import java.util.concurrent.Executors;

import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;
import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.MultiThreadedClaimStrategy;
import com.lmax.disruptor.MultiThreadedLowContentionClaimStrategy;
import com.lmax.disruptor.NoOpEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.SingleThreadedClaimStrategy;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Disruptor规则执行类
 * @author drizzt
 *
 */
public class DisruptorRunner implements ProcessRunner {
	final EventHandler<ProcessEvent> handler = new EventHandler<ProcessEvent>() {
	    public void onEvent(final ProcessEvent event, final long sequence, final boolean endOfBatch) throws Exception {
	    	event.setResult(event.getInterface().processRule(event.getParam()));
	    	event.complete = true;
	    }
	};
			
	private RingBuffer<ProcessEvent> ringBuffer = null;
	private WaitStrategy waitStrategy = null;
	Disruptor<ProcessEvent> disruptor = null;
	
	public DisruptorRunner(int buffer) {
		waitStrategy = new SleepingWaitStrategy();
		disruptor = new Disruptor<ProcessEvent>(ProcessEvent.FACTORY, 
						  Executors.newFixedThreadPool(buffer), 
				          new MultiThreadedLowContentionClaimStrategy(buffer),
				          new BusySpinWaitStrategy());
		disruptor.handleEventsWith(handler);
		ringBuffer = disruptor.start();
	}

	@Override
	public RiskResult run(RiskInterface inter, Map param) {
		long sequence = ringBuffer.next();
		ProcessEvent event = ringBuffer.get(sequence);
		event.setInterface(inter);
		event.setParam(param);
		
		ringBuffer.publish(sequence);
		
		while (!event.complete) {
			Thread.yield();
		}

		return ringBuffer.get(sequence).getResult();
	}
}

package com.google.code.simplerule.core.runner;

import java.util.Map;

import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * jactor规则执行类
 * @author drizzt
 *
 */
public class ActorRunner implements ProcessRunner {
	protected Logger logger = LoggerFactory.getLogger(ActorRunner.class);
	protected MailboxFactory mailboxFactory = null;
	
	public ActorRunner(int threads) {
		mailboxFactory = JAMailboxFactory.newMailboxFactory(threads);
	}

	@Override
	public RiskResult run(RiskInterface inter, Map param) {
		Mailbox mailbox = mailboxFactory.createMailbox();
		ProcessActor actor = new ProcessActor(inter, param);
		try {
			actor.initialize(mailbox);
		
			JAFuture future = new JAFuture();
			return (RiskResult)ProcessRequest.req.send(future, actor);
		} catch (Exception e) {
			logger.error("Actor running error.", e);
			return RiskCode.simpleResult(RiskCode.Exception, "Execute error.");
		}
	}

}

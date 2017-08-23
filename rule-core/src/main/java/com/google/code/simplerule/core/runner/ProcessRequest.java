package com.google.code.simplerule.core.runner;

import org.agilewiki.jactor.Actor;
import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.lpc.JLPCActor;
import org.agilewiki.jactor.lpc.Request;

public class ProcessRequest extends Request<Object, ProcessActor> {
	public static final ProcessRequest req = new ProcessRequest();
	
	public void processRequest(JLPCActor targetActor, RP rp) throws Exception {
		ProcessActor a = (ProcessActor)targetActor;
		a.start();
		rp.processResponse(a.getResult());
	}
	
	@Override
	public boolean isTargetType(Actor targetActor) {
		return targetActor instanceof ProcessActor;
	}

}

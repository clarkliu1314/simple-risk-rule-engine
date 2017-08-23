package com.google.code.simplerule.proxy.risk.monitor.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.google.code.simplerule.core.processor.RuleProcessor;

/**
 * thrift监控服务器
 * @author drizzt
 *
 */
public class ThriftMonitorServer {
	protected TServer server = null;
	public ThriftMonitorServer(int port, RuleProcessor processor) throws TTransportException {
		TServerTransport serverTransport = new TServerSocket(port);
		server = new TSimpleServer(new Args(serverTransport).processor(getProcessor(processor)));
	}
	
	public void start() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				server.serve();
			}
		};
		
		Thread t = new Thread(r);
	    t.start();
	}
	
	public void stop() {
		server.stop();
	}

	private TProcessor getProcessor(RuleProcessor processor) {
		return new RuleMonitorService.Processor<RuleMonitorServiceHandler>(new RuleMonitorServiceHandler(processor));
	}
}

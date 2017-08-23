package com.google.code.simplerule.proxy.risk.dao;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;

public class RiskSqlSessionFactory {
	private SqlSessionFactory sessionFactory;
	private SqlSession session = null;
	
	private final String defaultEnvironment = "risk";
	
	public RiskSqlSessionFactory(DataSource dataSource, Resource r) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setConfigLocation(r);
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactory = sessionFactoryBean.getObject();
		session = new SqlSessionTemplate(sessionFactory);
		
		//sessionFactory = new SqlSessionFactoryBuilder().build(getConfiguration(dataSource, r));
		//session = sessionFactory.openSession(true);
	}
	
	private Configuration getConfiguration(DataSource dataSource, Resource r) throws IOException {
		XMLConfigBuilder parser = new XMLConfigBuilder(r.getInputStream(), null, null);
		Configuration conf = parser.parse();
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();//定义事务工厂
		Environment environment = new Environment(defaultEnvironment, transactionFactory, dataSource);
		
		conf.setEnvironment(environment);
		return conf;
	}

	public SqlSession createSession() {
		return session;
	}
}

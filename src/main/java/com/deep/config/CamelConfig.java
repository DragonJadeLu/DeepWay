package com.deep.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.annotation.ProfileValueSourceConfiguration;

import com.deep.process.DefaultProcess;

/**
 * Camel配置
 * @author lwh
 *
 */
@Configuration
public class CamelConfig {
	private static final Logger logger = Logger.getLogger(CamelConfig.class);

	@Bean
	public CamelContext camelContext() throws Exception {
		logger.info("CamelConfig");
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.getRegistry();
		return camelContext;

	}
	
//	@Bean
//	public Jaxb2Marshaller jaxb2Marshaller() {
//		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
////		jaxb2Marshaller.setClassesToBeBound(classesToBeBound);
//		return jaxb2Marshaller;
//	}
}

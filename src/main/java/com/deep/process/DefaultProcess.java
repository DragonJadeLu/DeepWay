package com.deep.process;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.http.common.HttpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deep.model.DeepRoute;
import com.deep.model.ProcessRecord;
import com.deep.service.DeepRouteService;
import com.deep.service.ProcessRecordService;
/**
 * 默认的处理器
 * @author lwh
 *
 */
@Component("defaultProcess")
public class DefaultProcess implements Processor {
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ProcessRecordService processRecordService;
	@Autowired
	public DeepRouteService deepRouteService;

	public void process(Exchange exchange) throws Exception {
		String routeid = exchange.getFromRouteId();
		log.info(String.format("RouteID %s ", routeid));
		Message message = exchange.getIn();
//		InputStream fin = message.getBody(InputStream.class);
		if (message instanceof HttpMessage) {
			HttpMessage httpMessage = (HttpMessage) message;
			String body = httpMessage.getBody(String.class);			
			System.out.println(body);
			HttpServletRequest request = httpMessage.getRequest();
			HttpServletResponse response = httpMessage.getResponse();
			ProcessRecord record = new ProcessRecord();
			record.setProcesstime(new Date());
			record.setMsg(String.format("RouteID %s ; ExchangeId:%s ", routeid, exchange.getExchangeId()));
			processRecordService.save(record);
		}else{
			GenericFile genericFile = message.getBody(GenericFile.class);
			log.info(String.format("RouteID %s ; FileName:%s ", routeid, genericFile.getAbsoluteFilePath()));
			ProcessRecord record = new ProcessRecord();
			record.setFilename(genericFile.getAbsoluteFilePath());
			record.setFilenameonly(genericFile.getFileNameOnly());
			record.setProcesstime(new Date());
			DeepRoute deeproute = deepRouteService.findById(routeid);
			record.setDeepRoute(deeproute);
			processRecordService.save(record);
		}
	}
}
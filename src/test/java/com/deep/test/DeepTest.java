package com.deep.test;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.ServiceStatus;
import org.apache.camel.model.RouteDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.deep.config.RootConfig;
import com.deep.model.DeepRoute;
import com.deep.service.DeepRouteService;
//QQ:646761506
//这里提供了简单的测试
@RunWith(SpringJUnit4ClassRunner.class)  
//@SpringApplicationConfiguration(classes = Application.class) // 使用spring 应用环境
@WebAppConfiguration(value = "src/main/WebRoot")
@ContextConfiguration(classes=RootConfig.class)//代码配置环境
public class DeepTest{
//extends AbstractJUnit4SpringContextTests {
	Logger logger = LoggerFactory.getLogger(DeepTest.class);
//	
	
	@Autowired
	private DeepRouteService deepRouteService;
	
//	@Autowired
//	private ExcelHelper excelHelper;
	

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private CamelContext camelContext;
	
//	
//	@Test
//	public void mongodbTest() {
//		mongoService.findMongodbSpecs();
//	}
	
	@Test
	public void routeTest() throws Exception {
		DeepRoute myRoute = new DeepRoute();
		myRoute.setRouteid("TestRoute");//id不能为空
////		myRoute.setFrom("ftp://lwh:123@172.20.32.26/test?stepwise=true&ignoreFileNotFoundOrPermissionError=true&delete=true&consumer.delay=10s");
//		myRoute.setCfrom("file:D:/test1/?delete=true");
//		myRoute.setCto("file:E:/temp/outbox/");
//		myRoute.setBackpath("file:E:/temp/outbox1/");//做个也是需要的
//		myRoute.setProcessRef("myProcess");
////	myRoute.setRouteid("abcdddd");
		myRoute.setCfrom("timer://report?fixedRate=true&delay=0&period=1000");
//		myRoute.setCfrom("timer://report?fixedRate=true&delay=0");
//		myRoute.setCfrom("timer://report?fixedRate=false");
//		myRoute.setCfrom("direct:start");
//		Object sslContextParameters = appContext.getBean("sslContextParameters");
		myRoute.setCto("https4://www.oschina.net/p/deepway?sslContextParametersRef=sslContextParameters");
		logger.info("++++++++++++++++++++++++++");
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.from(myRoute.getCfrom());
//		routeDefinition.process(myRoute.getProcessRef());
//		routeDefinition.setHeader("User-Agent:Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		routeDefinition.to(myRoute.getCto());
		routeDefinition.to("file://E:/Temp/outbox1/?fileName=${date:now:yyyy}/baidu.com.${date:now:yyyyMMddhhmmss}.html");//自动修改文件名
		camelContext.addRouteDefinition(routeDefinition);
		camelContext.start();//
		logger.info("============================");
	}
	
	//国际招标公司公告
	@Test
	public void shabiddingTest() throws Exception {
		DeepRoute myRoute = new DeepRoute();
		myRoute.setRouteid("TestRoute");//id不能为空
////		myRoute.setFrom("ftp://lwh:123@172.20.32.26/test?stepwise=true&ignoreFileNotFoundOrPermissionError=true&delete=true&consumer.delay=10s");
//		myRoute.setCfrom("file:D:/test1/?delete=true");
//		myRoute.setCto("file:E:/temp/outbox/");
//		myRoute.setBackpath("file:E:/temp/outbox1/");//做个也是需要的
//		myRoute.setProcessRef("myProcess");
////	myRoute.setRouteid("abcdddd");
		myRoute.setCfrom("timer://report?fixedRate=true&delay=0&period=1000");
//		myRoute.setCfrom("timer://report?fixedRate=true&delay=0");
//		myRoute.setCfrom("timer://report?fixedRate=false");
//		myRoute.setCfrom("direct:start");
//		Object sslContextParameters = appContext.getBean("sslContextParameters");
		myRoute.setCto("http://www.shabidding.com/Chinese/ywxx_more.asp?id=%E9%A1%B9%E7%9B%AE%E5%85%AC%E5%91%8A");
		logger.info("++++++++++++++++++++++++++");
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.from(myRoute.getCfrom());
//		routeDefinition.process(myRoute.getProcessRef());
//		routeDefinition.setHeader("User-Agent:Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		routeDefinition.to(myRoute.getCto());
		routeDefinition.to("file://E:/Temp/outbox1/?fileName=${date:now:yyyy}/baidu.com.${date:now:yyyyMMddhhmmss}.html");//自动修改文件名
		camelContext.addRouteDefinition(routeDefinition);
		camelContext.start();//
		logger.info("============================");
	}
	
	
	@Test
	public void routeHttpsTest() throws Exception {
		DeepRoute myRoute = new DeepRoute();
		myRoute.setRouteid("TestRoute");//id不能为空
//		myRoute.setCfrom("timer://report?fixedRate=false");
		myRoute.setCfrom("timer://simpleTimer?delay=2s&repeatCount=1");// Delay and RepeatCount timer options
//		myRoute.setCfrom("direct:start");
//		myRoute.setCto("https://www.oschina.net/p/deepway");
		myRoute.setCto("https4://www.oschina.net/p/deepway");
		logger.info("++++++++++++++++++++++++++");
		RouteDefinition routeDefinition = new RouteDefinition();
		routeDefinition.from(myRoute.getCfrom());
		Processor gjzbProcess = (Processor)appContext.getBean("gjzbProcess");
		
		routeDefinition.setHeader("user-agent").simple("Mozilla/5.0",String.class);//这是关键
		routeDefinition.to(myRoute.getCto());
		routeDefinition.process(gjzbProcess);
		routeDefinition.to("file://E:/Temp/outbox1/?fileName=${date:now:yyyy}/oschina_${date:now:yyyyMMddhhmmss}.html");//自动修改文件名
		
		camelContext.addRouteDefinition(routeDefinition);
		camelContext.start();//
		logger.info("============================");
	}
	
	
	@Test
	public void saveRoute() throws Exception {
		DeepRoute myRoute = new DeepRoute();
		myRoute.setRouteid("TestRoute");//id不能为空
		myRoute.setCfrom("file:D:/test1/?delete=true");
		myRoute.setCto("file:E:/temp/outbox/");
		String backpath = "file://E:/temp/outbox1/?fileName=${date:now:yyyy}/${date:now:yyyyMMdd}/${file:name}";//
		myRoute.setBackpath(backpath);//做个也是需要的
		
		deepRouteService.save(myRoute);
		logger.info("++++++++++++++++++++++++++");
		logger.info("============================");
	}
	
	@Test
	public void startRout() throws Exception {
		deepRouteService.startRoute();//启动全部路由
		logger.info("============================");
		
		List<DeepRoute>  routes = deepRouteService.findAll();
		for (DeepRoute deepRoute : routes) {
			String routeId = deepRoute.getRouteid();
			ServiceStatus serviceStatus = camelContext.getRouteStatus( routeId );
			logger.info(String.format("Route ID:%s|Status:%s",routeId, serviceStatus));
			if (ServiceStatus.Suspended.equals(serviceStatus)) {// 暂停
				camelContext.resumeRoute(routeId);
			} else if (ServiceStatus.Started.equals(serviceStatus)) {// 启动
				camelContext.suspendRoute(routeId);//暂停路由
			}
			logger.info(String.format("Route ID:%s|Status:%s",routeId,  camelContext.getRouteStatus( routeId )));
		}
		
	}
}

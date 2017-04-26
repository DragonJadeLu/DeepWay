package com.deep.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deep.service.DeepRouteService;
import com.deep.util.WebAppUtils;


public class ServletContextLoaderListener implements ServletContextListener{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("初始化Servlet环境。开始");
		ServletContext servletContext = servletContextEvent.getServletContext();
		DeepRouteService routeService = (DeepRouteService)WebAppUtils.getBean("deepRouteService", servletContext);
		try {
			log.info("路由启动开始");
			routeService.startRoute();
			log.info("路由启动完成");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		log.info("初始化Servlet环境。结束");
	}

}

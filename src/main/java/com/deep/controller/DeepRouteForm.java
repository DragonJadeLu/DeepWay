package com.deep.controller;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.ServiceStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deep.model.DeepRoute;
import com.deep.service.DeepRouteService;
import com.deep.service.UserService;

@Controller
@RequestMapping("/deeproute")
public class DeepRouteForm {
	private static final Logger logger = Logger.getLogger(DeepRouteForm.class);
	
	@Autowired  
	private UserService userService;
	
	@Autowired  
	private DeepRouteService deepRouteService;
	
	@Autowired
	private CamelContext camelContext;
	
	@RequestMapping(value = { "/list","/"}  )
	public String list(Model model) throws Exception {
		List<DeepRoute> routes = deepRouteService.findAll();
		List<Route>  camelRoutes = camelContext.getRoutes();
		
		for (DeepRoute deepRoute : routes) {
			if(!isContainRoute(camelRoutes,deepRoute.getRouteid())){
				deepRouteService.addRouteDefinition(deepRoute);
			}
			ServiceStatus serviceStatus = camelContext.getRouteStatus( deepRoute.getRouteid() );
			deepRoute.setServiceStatus(serviceStatus);//状态
		}
		model.addAttribute("routes", routes);
		return "/deeproute/list";
	}
	
	@RequestMapping("/suspendRoute/{routeid}")
	public String  suspendRoute(@PathVariable("routeid") String routeId, Model model) throws Exception {
		ServiceStatus serviceStatus = camelContext.getRouteStatus( routeId );
		if (ServiceStatus.Suspended.equals(serviceStatus)) {// 暂停
			camelContext.resumeRoute(routeId);
		} else if (ServiceStatus.Started.equals(serviceStatus)) {// 启动
			camelContext.suspendRoute(routeId);//暂停路由
		}
	    return "redirect:/deeproute/list";
	}
	
	@RequestMapping(value = { "/add"}  )
	public String add(Model model) {
		 return "redirect:/deeproute/list";
	}
	/*
	 * 包含deepRouteId
	 */
	public Boolean isContainRoute(List<Route>  routes,String deepRouteId){
		for (Route route : routes) {
			String id = route.getId();
			if(id.equals(deepRouteId)){
				return true;
			}
		}
		return false;
	}
	

}

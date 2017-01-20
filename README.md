# deepway是简单的企业系统间集成的工具。
通过deepway能够方便的集成各种系统。

![数据总线](./src/introduce/数据总线.png "数据总线") 
方便路由管理

![数据总线](./src/introduce/数据交换.png "数据总线") 
方便查看数据

目前支持的协议：
ftp，sftp，file，smb（共享目录）

2017年1月16日更新

新增加了http和https协议

需要帮助可以联系QQ:646761506或者在 //138.xjee.cn/dc/ 上发留言给我！

DeepWay依赖mongodb和redis，因此如果需要启动需要安装它们，相关的配置文件在deepway.properties文件中

下面的test代码可以看出启动，暂停路由操作
		
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
	

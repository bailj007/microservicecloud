package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class DeptController {

	@Autowired
	private DeptService service;
//	@Autowired
//	private DiscoveryClient client;

//	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
//	public boolean add(@RequestBody Dept dept) {
//		return service.add(dept);
//	}

	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	//当方法调用抛出异常时，会调用@HystrixCommand标注里面fallbackMethod指定的方法
	//引申出了一个问题，每添加一个service层的方法，就会有一个对应的处理异常的方法，这样就会导致方法膨胀。
	//而且业务逻辑和处理异常的逻辑耦合程度太高,解决办法详见DeptClientServiceFallbackFactory
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = this.service.get(id);
		if (null == dept) {
			throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
		}
		return dept;
	}

	public Dept processHystrix_Get(@PathVariable("id") Long id) {
		return new Dept().setDeptno(id).setDname("该ID：" + id + "没有没有对应的信息,null--@HystrixCommand")
				.setDb_source("no this database in MySQL");
	}

//	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
//	public List<Dept> list() {
//		return service.list();
//	}

	/**
	 * 服务发现
	 * 
	 * @return
	 */
//	@RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
//	public Object discovery() {
//		List<String> list = client.getServices();
//		System.out.println("**********" + list);
//
//		List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
//		for (ServiceInstance element : srvList) {
//			System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
//					+ element.getUri());
//		}
//		return this.client;
//	}
}

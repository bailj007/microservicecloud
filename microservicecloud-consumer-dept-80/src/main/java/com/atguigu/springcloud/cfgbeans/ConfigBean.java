package com.atguigu.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;


@Configuration
public class ConfigBean {//相当于application.xml
	
	@Bean
	@LoadBalanced//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端       负载均衡的工具。
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 *  可以替代默认的ribbon轮询算法 RoundRobinRule
	 * @return
	 */
	@Bean
	public IRule myRule() {
		//return new RandomRule();//使用随算法替代默认的轮询
		return new RetryRule();//先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
	}

}

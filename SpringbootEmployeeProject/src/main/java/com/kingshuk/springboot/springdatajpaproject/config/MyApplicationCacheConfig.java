package com.kingshuk.springboot.springdatajpaproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;

@Configuration
public class MyApplicationCacheConfig {

	@Bean
	public Config cacheConfig() {
		return new Config().setInstanceName("Hazel-instance")
				.addMapConfig(new MapConfig().setName("employee-cache")
				.setTimeToLiveSeconds(5000)
				.setMaxSizeConfig(new MaxSizeConfig(300, MaxSizePolicy.FREE_HEAP_SIZE))
				.setEvictionPolicy(EvictionPolicy.LRU));
	}

}

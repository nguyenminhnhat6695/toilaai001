package com.lifecode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanCustomConfig {

	/**
	 * SpringCustomConfigurator
	 * @return
	 */
	@Bean
	public SpringCustomConfigurator springCustomConfigurator() {
		return new SpringCustomConfigurator(); // This is just to get context
	}
}
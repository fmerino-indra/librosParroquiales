package org.fmm.librosparroquiales.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class LibrosParroquialesCommonConfig {
	@Bean
	public JavaTimeModule javaTimeModule() {
		return new JavaTimeModule();
	}
	
}

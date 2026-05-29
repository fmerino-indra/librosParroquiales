package org.fmm.librosparroquiales.common.testconfig;

import org.fmm.librosparroquiales.common.config.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

@Configuration
//@EnableAutoConfiguration
@EntityScan(basePackages = {"org.fmm.librosparroquiales.common.model","org.fmm.oauth2.common.model.model"})
@EnableJpaRepositories(basePackages = {"org.fmm.librosparroquiales.common.repository","org.fmm.oauth2.common.model.repository"})
@PropertySource(value="classpath:lib-test-application.yaml", factory = YamlPropertySourceFactory.class)
public class LibrosParroquialesCommonTestConfiguration {
	@PostConstruct
	public void init() {
		System.out.println("TestPropertiesConfig ha sido cargado por Spring");
	}
}

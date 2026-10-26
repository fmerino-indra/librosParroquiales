package org.fmm.librosparroquiales.common.testconfig;

import org.fmm.librosparroquiales.common.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

//@ComponentScan(basePackages = {"org.fmm.librosparroquiales.common", "org.fmm.oauth2.common.model"})

//@EntityScan(basePackages = {"org.fmm.communitymgmt.common.model"})
//@EnableJpaRepositories(basePackages = {"org.fmm.communitymgmt.common.repository"})
//@PropertySource(value="classpath:lib-test-application.yaml", factory = YamlPropertySourceFactory.class)
//@SpringBootConfiguration
@SpringBootApplication
@ComponentScan(basePackages = {"org.fmm.librosparroquiales.common"},
	excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, 
        classes = LibrosParroquialesCommonEntityTestConfiguration.class
    ))
@PropertySource(value="classpath:lib-test-application.yaml", factory = YamlPropertySourceFactory.class)
@EnableJpaRepositories(
	    basePackages = "org.fmm.librosparroquiales.common.repository"
	)
@EntityScan(
    basePackages = "org.fmm.librosparroquiales.common.model"
)
public class LibrosParroquialesCommonTestApplication {
	@PostConstruct
	public void init() {
		System.out.println("TestPropertiesConfig ha sido cargado por Spring");
	}
/*
	public static void main(String[] args) {
		SpringApplication.run(LibrosParroquialesCommonTestApplication.class, args);
	}
*/
}

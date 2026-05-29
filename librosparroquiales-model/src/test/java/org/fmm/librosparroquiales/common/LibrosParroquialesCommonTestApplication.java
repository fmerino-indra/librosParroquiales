package org.fmm.librosparroquiales.common;

import org.springframework.boot.SpringApplication;

//@SpringBootApplication
//@EntityScan(basePackages = {"org.fmm.communitymgmt.common.model"})
//@EnableJpaRepositories(basePackages = {"org.fmm.communitymgmt.common.repository"})
//@PropertySource(value="classpath:lib-test-application.yaml", factory = YamlPropertySourceFactory.class)
public class LibrosParroquialesCommonTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrosParroquialesCommonTestApplication.class, args);
	}

}

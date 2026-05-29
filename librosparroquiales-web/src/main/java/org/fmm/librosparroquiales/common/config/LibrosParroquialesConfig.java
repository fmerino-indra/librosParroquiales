package org.fmm.librosparroquiales.common.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration // Enciende la magia de Spring (DataSource, Hibernate, etc.)
@ComponentScan(basePackages = {
    "org.fmm.librosparroquiales.common"     // Escanea tus controladores de JavaFX si hiciera falta
})
@EntityScan(basePackages = {"org.fmm.librosparroquiales.common.model"})

@EnableJpaRepositories(basePackages = "org.fmm.librosparroquiales.common.repository") // Fuerza a Spring a levantar tus repositorios JPA del modelo
public class LibrosParroquialesConfig {
}

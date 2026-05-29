package org.fmm.librosparroquiales.javafx;

import org.fmm.librosparroquiales.common.config.LibrosParroquialesConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Platform;
import javafx.stage.Stage;

public class Launcher {
	/*
	public static void main(String[] args) {
		AppFx.main(args);
//		Application.launch(AppFx.class, args);
//		Application.launch(AppFx.class);
		
	}
	*/
	
	public static ConfigurableApplicationContext springContext;
	
	public static void main(String[] args) {
		try {
            // 1. Arrancamos Spring Boot de forma clásica (desactivando el servidor web)
            // Cambia 'TuClaseApplicationDelModelo.class' por tu clase real con @SpringBootApplication
            springContext = new SpringApplicationBuilder(LibrosParroquialesConfig.class)
                    .web(org.springframework.boot.WebApplicationType.NONE)
                    .run(args);

            // 2. Una vez encendido Spring, levantamos el entorno gráfico de JavaFX		

            Platform.startup(() -> {
				try {
					AppFx2 app = new AppFx2();
					app.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
            System.err.println("Error crítico al inicializar el motor de Spring:");
            e.printStackTrace();
            System.exit(1);
        }
	}
}

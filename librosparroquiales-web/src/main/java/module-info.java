/*
module org.fmm.librosparroquiales.javafx {
	// 1. Módulos requeridos de JavaFX para que funcione la interfaz
    requires javafx.controls;
    requires javafx.fxml;
    requires librosparroquiales.common;
    
    // 2. Si tu proyecto depende del módulo hermano 'librosparroquiales-model', añádelo aquí:
    // requires librosparroquiales.model; 

    // 3. EXPORTAR: Permite que otros módulos vean las clases de este paquete
    exports org.fmm.librosparroquiales.javafx;
    exports org.fmm.librosparroquiales.javafx.controller;

    // 4. ABRIR (CRUCIAL): El motor de JavaFX (FXMLLoader) necesita usar reflexión 
    // para buscar, instanciar y "cablear" tu controlador con el archivo FXML.
    opens org.fmm.librosparroquiales.javafx to javafx.graphics, javafx.fxml;
    opens org.fmm.librosparroquiales.javafx.controller to javafx.fxml;	
}
*/
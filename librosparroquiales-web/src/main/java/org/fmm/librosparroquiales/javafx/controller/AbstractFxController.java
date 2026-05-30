package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.javafx.Launcher;

public class AbstractFxController {

	public BaptismRequestService getBean() {
		BaptismRequestService baptismRequestService = null;
        // 2. Cargar datos simulados (Esto se sustituirá por: baptismService.findLatestRequests())
        baptismRequestService = (BaptismRequestService)Launcher.springContext.getBean("BaptismRequestService");
        return baptismRequestService;
		
	}
}

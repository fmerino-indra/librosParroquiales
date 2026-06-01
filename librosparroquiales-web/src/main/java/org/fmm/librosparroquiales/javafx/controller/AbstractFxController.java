package org.fmm.librosparroquiales.javafx.controller;

import org.fmm.librosparroquiales.common.service.BaptismRequestService;
import org.fmm.librosparroquiales.common.service.PersonService;
import org.fmm.librosparroquiales.javafx.Launcher;

public class AbstractFxController {

	public BaptismRequestService getBaptismRequestServiceBean() {
		BaptismRequestService baptismRequestService = null;
        // 2. Cargar datos simulados (Esto se sustituirá por: baptismService.findLatestRequests())
        baptismRequestService = (BaptismRequestService)Launcher.springContext.getBean("BaptismRequestService");
        return baptismRequestService;
	}
	public PersonService getPersonServiceBean() {
		PersonService personService = null;
        // 2. Cargar datos simulados (Esto se sustituirá por: baptismService.findLatestRequests())
        personService = (PersonService)Launcher.springContext.getBean("PersonService");
        return personService;
	}
}

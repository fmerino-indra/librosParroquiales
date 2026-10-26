package org.fmm.librosparroquiales.common.service;

import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.repository.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "RelativeService")
public class RelativeServiceImpl implements RelativeService {

	@Autowired
	RelativeRepository relativeRepository;
	
	@Override
	public Relative saveRelative(Relative relative) {
		return relativeRepository.save(relative);
	}

}

package org.fmm.librosparroquiales.common.service;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.repository.BaptismRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service(value = "BaptismRequestService")
public class BaptismRequestServiceImpl implements BaptismRequestService {

	@Autowired
	private BaptismRequestRepository baptismRequestRepository;

	@Override
	public Page<BaptismRequest> getBaptismRequest(String criterio, LocalDate from, LocalDate to) {
		int pagina = 0;
		int tamanoPagina=20;
		
		Pageable ordenacionYPagina = PageRequest.of(pagina, tamanoPagina, Sort.by("br.appointment").descending());
		return baptismRequestRepository.listBaptismRequestByCriterioAndDate(criterio, from, to,ordenacionYPagina);
	}

	@Override
	public BaptismRequest baptismRequestCreate(BaptismRequest baptismRequest) {
		// TODO Auto-generated method stub
		return baptismRequest;
	}
	
}

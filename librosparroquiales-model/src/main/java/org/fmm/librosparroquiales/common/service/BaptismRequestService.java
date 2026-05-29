package org.fmm.librosparroquiales.common.service;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.springframework.data.domain.Page;

public interface BaptismRequestService {
    public Page<BaptismRequest> getBaptismRequest(String criterio, LocalDate from, LocalDate to);
    public BaptismRequest baptismRequestCreate(BaptismRequest baptismRequest);
}
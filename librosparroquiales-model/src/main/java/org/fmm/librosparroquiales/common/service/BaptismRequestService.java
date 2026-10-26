package org.fmm.librosparroquiales.common.service;

import java.time.LocalDate;
import java.util.Map;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.springframework.data.domain.Page;

public interface BaptismRequestService {
    public Page<BaptismRequest> getBaptismRequest(String criterio, LocalDate from, LocalDate to);
    public BaptismRequest baptismRequestCreate(BaptismRequest baptismRequest);
    public byte[] exportToPdf(String reportPath, Map<String, Object> params, BaptismRequest baptism) throws Exception;
}
package org.fmm.librosparroquiales.common.repository;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix merino
 */
@Repository
public interface BaptismRequestRepository extends JpaRepository<BaptismRequest, Integer> {
	/*
    @Query("SELECT br DISTINCT BaptismRequest br"
            + " LEFT JOIN FETCH br.candidate c"
            + " LEFT JOIN FETCH c.personData pD"
            + " LEFT JOIN FETCH p.emailAccounts ea "
            + " WHERE pD.name = :name"
            + " AND (:from IS NULL OR br.appointment >= :from)"
            + " AND (:to IS NULL OR br.appointment <= :to)"
            + " ORDER BY br.appointment")
    Page<BaptismRequest> listBaptismRequestByNameAndDate(@Param("name") String name, Pageable pageRequest);
    */
    @Query("SELECT br DISTINCT FROM BaptismRequest br"
            + " LEFT JOIN FETCH br.candidate c"
            + " LEFT JOIN FETCH c.personData pD "
            + " WHERE ("
            + " LOWER(CONCAT(pD.name, ' ', pD.surname1, ' ', pD.surname2))"
            + " LIKE LOWER(CONCAT('%', :texto, '%')))"
            + " AND (CAST(:from AS localdate) IS NULL OR br.appointment >= :from)"
            + " AND (CAST(:to AS localdate) IS NULL OR br.appointment <= :to)"
            + " ORDER BY br.appointment")
    Page<BaptismRequest> listBaptismRequestByCriterioAndDate(
    		@Param("texto") String texto,
    		@Param("from") LocalDate from,
    		@Param("to") LocalDate to, 
    		Pageable pageRequest);

    @Query("SELECT br DISTINCT FROM BaptismRequest br"
            + " LEFT JOIN FETCH br.candidate c"
            + " LEFT JOIN FETCH c.personData pD "
            + " WHERE (:id = br.id)")
    BaptismRequest findBaptismById(@Param("id") Integer id);
}

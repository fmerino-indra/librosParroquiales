package org.fmm.librosparroquiales.common.repository;

import org.fmm.librosparroquiales.common.model.Relative;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix Merino
 */
@Repository
public interface RelativeRepository extends JpaRepository<Relative, Integer> {
    @Query("SELECT f FROM Relative f"
            + " LEFT JOIN FETCH f.personData p"
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " WHERE f.id = :id"
            + " AND f.relativeType = 'FATHER'")
    Father findFullFatherById(@Param("id") Integer id);

    @Query("SELECT f FROM Relative f"
            + " LEFT JOIN FETCH f.personData p"
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " WHERE p.name = :name"
            + " AND p.surname1 = :surname1"
            + " AND f.relativeType = 'FATHER'")
    Father findFullFatherByName(@Param("name") String name, @Param("surname1") String surname1);

	
}

package org.fmm.librosparroquiales.common.repository;

import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix merino
 */
@Repository
public interface ParishRepository extends JpaRepository<Parish, Integer> {
    @Query("SELECT p FROM Parish p"
    		+ " LEFT JOIN FETCH p.parishPriestList pp"
            + " WHERE p.id = :id")
    Optional<Parish> findFullParish(@Param("id") Integer id);
}

package org.fmm.librosparroquiales.common.repository;

import org.fmm.librosparroquiales.common.model.ParishPriest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix merino
 */
@Repository
public interface ParishPriestRepository extends JpaRepository<ParishPriest, Integer> {
    @Query("SELECT pp FROM ParishPriest pp"
            + " LEFT JOIN FETCH pp.parish p"
            + " WHERE p.id = :id")
    ParishPriest findFullParishPriest(@Param("id") Integer id);
}

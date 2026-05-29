package org.fmm.librosparroquiales.common.repository;

import org.fmm.librosparroquiales.common.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix Merino
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a"
    		+ " WHERE a.id = :id")
    Address findFullParish(@Param("id") Integer id);
}

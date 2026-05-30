package org.fmm.librosparroquiales.common.repository;

import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
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
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT DISTINCT p FROM Person p "
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " ORDER BY p.id ASC")
    List<Person> listAllPerson();
    
    @Query("SELECT p FROM Person p"
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " WHERE p.id = :id")
    Person findFullPerson(@Param("id") Integer id);

    @Query("SELECT p FROM Person p"
            + " WHERE (:name IS NULL OR p.name = :name)"
            + " AND (:surname1 IS NULL OR p.surname1 = :surname1)"
            + " ORDER BY p.surname1, p.name"
    		)
    Page<Person> findPersonByNameAndSurname1(@Param("name") String name, @Param("surname1") String surname1, Pageable pageable);
}

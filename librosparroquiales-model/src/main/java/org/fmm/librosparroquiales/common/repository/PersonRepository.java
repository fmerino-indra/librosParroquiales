package org.fmm.librosparroquiales.common.repository;

import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
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

    List<Person> findByNameContainingIgnoreCaseOrSurname1ContainingIgnoreCaseOrSurname2ContainingIgnoreCase(@Param("name") String name, @Param("surname1") String surname1, @Param("surname2") String surname2);
    List<Person> findByNameContainingIgnoreCaseAndGenderOrSurname1ContainingIgnoreCaseAndGenderOrSurname2ContainingIgnoreCaseAndGender(@Param("name") String name, @Param("gender1") GenderEnum gender1, @Param("surname1") String surname1, @Param("gender2") GenderEnum gender2, @Param("surname2") String surname2, @Param("gender3") GenderEnum gender3);    
}

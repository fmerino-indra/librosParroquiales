package org.fmm.librosparroquiales.common.repository;

import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
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

    /* 
     * Con respecto a acollyte es diferente, aquí un person puede tener más de un socialUserId (facebook, google, etc.)
     * Además aquí no hay -de momento- userId, mi idea es que siempre sea socialUserId para que no tenga que hacer esa gestión
     * 
    @Query("SELECT p FROM Person p"
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " WHERE p.userId = :userId")
    Person findFullPersonByUserId(@Param("userId") String userId);


    @Query("SELECT p FROM Person p"
            + " LEFT JOIN FETCH p.mobileNumbers mn"
            + " LEFT JOIN FETCH p.emailAccounts ea"
            + " WHERE p.socialUserId = :socialUserId")
    Person findFullPersonBySocialUserId(@Param("socialUserId") Integer socialUserId);

    @Deprecated
    @Query("SELECT p FROM Person p"
    		+ " LEFT JOIN FETCH p.appRoles r"
    		+ " WHERE p.socialUserId = :socialUserId")
    Person findPersonPermissionsBySocialUserId(@Param("socialUserId") Integer socialUserId);

    @Query("SELECT p FROM Person p"
    		+ " LEFT JOIN FETCH p.appRoles r"
    		+ " WHERE p.socialUserId = :socialUser")
    Person findPersonPermissionsBySocialUser(@Param("socialUser") SocialUser socialUser);
    */
}

package org.fmm.librosparroquiales.common.service;

import org.fmm.librosparroquiales.common.model.Person;
import org.springframework.data.domain.Page;

public interface PersonService {
    public Page<Person> findPerson(String name, String surname1);
}
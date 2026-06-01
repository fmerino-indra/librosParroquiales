package org.fmm.librosparroquiales.common.service;

import java.util.List;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

public interface PersonService {
    public List<Person> findPerson(String name, String surname1, String surname2);
	public List<Person> findPersonByGender(String name, String surname1, String surname2, GenderEnum gender);
    public Father findFatherByPerson(Person person);
}
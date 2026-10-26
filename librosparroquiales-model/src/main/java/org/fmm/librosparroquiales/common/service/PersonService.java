package org.fmm.librosparroquiales.common.service;

import java.util.List;
import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;

public interface PersonService {
    public List<Person> findPerson(String name, String surname1, String surname2);
	public List<Person> findPersonByGender(String name, String surname1, String surname2, GenderEnum gender);
    public Optional<Father> findFatherByPerson(Person person);
    public Optional<Mother> findMotherByPerson(Person person);
    public Optional<GrandFather> findGrandFatherByPerson(Person person);
	public Optional<GrandMother> findGrandMotherByPerson(Person person);
}
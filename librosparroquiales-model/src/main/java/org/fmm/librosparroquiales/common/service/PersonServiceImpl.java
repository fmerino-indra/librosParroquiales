package org.fmm.librosparroquiales.common.service;

import java.util.List;
import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
import org.fmm.librosparroquiales.common.repository.RelativeRepository;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "PersonService")
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RelativeRepository relativeRepository;
	
	@Override
	public List<Person> findPerson(String name, String surname1, String surname2) {
		return personRepository.findByNameContainingIgnoreCaseOrSurname1ContainingIgnoreCaseOrSurname2ContainingIgnoreCase(name, surname1, surname2);
	}

	@Override
	public List<Person> findPersonByGender(String name, String surname1, String surname2, GenderEnum gender) {
		if (gender == null)
			return personRepository.findByNameContainingIgnoreCaseOrSurname1ContainingIgnoreCaseOrSurname2ContainingIgnoreCase(
					name, surname1, surname2);
		return personRepository.findByNameContainingIgnoreCaseAndGenderOrSurname1ContainingIgnoreCaseAndGenderOrSurname2ContainingIgnoreCaseAndGender(
				name, gender,
				surname1, gender,
				surname2, gender);
	}

	@Override
	public Optional<Father> findFatherByPerson(Person person) {
		Optional<Father> optFather = null;
		optFather = relativeRepository.findFullFatherByPersonId(person.getId());
		return optFather;
	}

	@Override
	public Optional<Mother> findMotherByPerson(Person person) {
		Optional<Mother> optMother = null;
		optMother = relativeRepository.findFullMotherByPersonId(person.getId());
		return optMother;
	}

	@Override
	public Optional<GrandFather> findGrandFatherByPerson(Person person) {
		Optional<GrandFather> optGrandFather = null;
		optGrandFather = relativeRepository.findFullGrandFatherByPersonId(person.getId());
		return optGrandFather;
	}

	@Override
	public Optional<GrandMother> findGrandMotherByPerson(Person person) {
		Optional<GrandMother> optGrandMother = null;
		optGrandMother = relativeRepository.findFullGrandMotherByPersonId(person.getId());
		return optGrandMother;
	}
}

package org.fmm.librosparroquiales.common.service;

import java.util.List;
import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
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
		return personRepository.findByNameContainingIgnoreCaseAndGenderOrSurname1ContainingIgnoreCaseAndGenderOrSurname2ContainingIgnoreCaseAndGender(
				name, gender,
				surname1, gender,
				surname2, gender);
	}

	@Override
	public Father findFatherByPerson(Person person) {
		Optional<Father> optFather = null;
		Father father = null;
		optFather = relativeRepository.findFullFatherByPersonId(person.getId());
		if (optFather.isPresent())
			father = optFather.get();
		else {
			father = new Father();
			father.setPersonData(person);
		}
		return father;
	}

}

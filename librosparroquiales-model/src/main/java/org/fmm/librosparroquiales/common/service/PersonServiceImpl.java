package org.fmm.librosparroquiales.common.service;

import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Page<Person> findPerson(String name, String surname1) {
		int pagina = 0;
		int tamanoPagina=20;
		Pageable pageRequest = PageRequest.of(pagina, tamanoPagina);
		return personRepository.findPersonByNameAndSurname1(name, surname1, pageRequest);
	}

}

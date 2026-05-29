package org.fmm.librosparroquiales.common.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.fmm.librosparroquiales.common.model.EmailAccount;
import org.fmm.librosparroquiales.common.model.MobileNumber;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.repository.BaptismRequestRepository;
import org.fmm.librosparroquiales.common.repository.BaptismalCandidateRepository;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
import org.fmm.librosparroquiales.common.repository.RelativeRepository;
import org.fmm.librosparroquiales.common.testconfig.LibrosParroquialesCommonTestConfiguration;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

// Esto configura springboot completo para tests integrados.
// Para JPA es mejor @DataJPATest
//@SpringBootTest(classes = {CommunityMgmtCommonTestApplication.class})
//@EntityScan(basePackages = {"org.fmm.communitymgmt.common.model","org.fmm.oauth2.common.model.model"})
//@EnableJpaRepositories(basePackages = {"org.fmm.communitymgmt.common.repository","org.fmm.oauth2.common.model.repository"})

@Rollback(false)
@DataJpaTest
// No reemplaces la base de datos real por una embebida.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(CommunityMgmtCommonTestConfiguration.class)
@ContextConfiguration(classes = LibrosParroquialesCommonTestConfiguration.class)
class LibrosParroquialesBaptismInsertionsTests {

    @Autowired
    private BaptismalCandidateRepository baptismalCandidateRepository;
	@Autowired
	private RelativeRepository relativeRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private BaptismRequestRepository baptismRequestRepository;
//	@Test
	//@Transactional
	public void addFelixBaptism() {
		BaptismalCandidate baptismalCandidate = null;
		Father father = null;
		Mother mother = null;
		Person godFather = null;
		Person godMother = null;
		GrandFather fGrandFather = null;
		GrandMother fGrandMother = null;
		GrandFather mGrandFather = null;
		GrandMother mGrandMother = null;
		
		baptismalCandidate=addFelixBaptismCandidate();
		father= addFelixFather();
		mother = addFelixMother(father.getAddress());
		
		godFather = addFelixGodFather();
		godMother = addFelixGodMother();
		addBaptsimRequest(baptismalCandidate, father, mother, godFather, godMother,
				fGrandFather, fGrandMother, mGrandFather, mGrandMother, true, LocalDate.of(2010, 4, 4));
	}

	@Test
	public void addTeresaBaptism() {
		BaptismalCandidate baptismalCandidate = null;
		Father father = null;
		Mother mother = null;
		Person godFather = null;
		Person godMother = null;
		GrandFather fGrandFather = null;
		GrandMother fGrandMother = null;
		GrandFather mGrandFather = null;
		GrandMother mGrandMother = null;
		
		father= searchFelixFather();
		baptismalCandidate=addTeresaBaptismCandidate();
//		mother = searchFelixMother();
		
//		godFather = addTeresaGodFather();
//		godMother = addTeresaGodMother();
//		addBaptsimRequest(baptismalCandidate, father, mother, godFather, godMother,
//				fGrandFather, fGrandMother, mGrandFather, mGrandMother, true, LocalDate.of(2013, 3, 31));
	}

	private Father searchFelixFather() {
		Father father = null;
		father = relativeRepository.findFullFatherByName("Félix", "Merino");
		return father;
	}

	private Person addGodParent(String name, String surname1, String surname2, GenderEnum gender) {
		Person p = null;
		p = createPerson(null, name, surname1, surname2, gender, false,null, null);
		p=personRepository.save(p);
		return p;
	}

	private Person addFelixGodFather() {
		return addGodParent("José Enrique", "Gutiérrez", "Rincón", GenderEnum.M);
	}

	private Person addFelixGodMother() {
		return addGodParent("Isabel", "Cabanes", "Miró", GenderEnum.F);
	}

	private void addBaptsimRequest(BaptismalCandidate baptismalCandidate, Father father, Mother mother,
			Person godFather, Person godMother, GrandFather fGrandFather, GrandMother fGrandMother,
			GrandFather mGrandFather, GrandMother mGrandMother, boolean celebrated, LocalDate appointment) {
		BaptismRequest baptismRequest = new BaptismRequest();
		baptismRequest.setCandidate(baptismalCandidate);
		baptismRequest.setFather(father);
		baptismRequest.setMother(mother);
		baptismRequest.setGodFather(godFather);
		baptismRequest.setGodMother(godMother);
		baptismRequest.setFatherGrandFather(fGrandFather);
		baptismRequest.setFatherGrandMother(fGrandMother);
		baptismRequest.setMotherGrandFather(mGrandFather);
		baptismRequest.setMotherGrandMother(mGrandMother);
		
		baptismRequest.setCelebrated(celebrated);
		baptismRequest.setAppointment(appointment);
		baptismRequestRepository.save(baptismRequest);
		assertNotNull(baptismRequest);
	}

	private BaptismalCandidate addTeresaBaptismCandidate() {
		BaptismalCandidate baptismalCandidate = new BaptismalCandidate();
		Person person = new Person();
		
		Address address = new Address();
		
		person = createPerson(LocalDate.of(2012, 8, 23),"Teresa","Merino","Cabanes",GenderEnum.F,false,
				"teresa.merino.cabanes@gmail.com","604993765");
		
		address = createAddress("Av. Pablo Neruda","49","2º","B","28018","Madrid","Madrid","España");
		
		baptismalCandidate.setPersonData(person);
		baptismalCandidate.setAddress(address);
		baptismalCandidate.setActive(true);
		baptismalCandidate.setBirthLocation("Madrid");
		baptismalCandidate=baptismalCandidateRepository.save(baptismalCandidate);
		
		assertNotNull(baptismalCandidate.getId());
		return baptismalCandidate;
	}
	

// Félix
	private BaptismalCandidate addFelixBaptismCandidate() {
		BaptismalCandidate baptismalCandidate = new BaptismalCandidate();
		Person person = new Person();
		
		Address address = new Address();
		
		person = createPerson(LocalDate.of(2009, 10, 6),"Félix","Merino","Cabanes",GenderEnum.M,false,
				"felix.merino.cabanes@gmail.com","624");
		
		address = createAddress("Av. Pablo Neruda","49","2º","B","28018","Madrid","Madrid","España");
		
		baptismalCandidate.setPersonData(person);
		baptismalCandidate.setAddress(address);
		baptismalCandidate.setActive(true);
		baptismalCandidate.setBirthLocation("Madrid");
		baptismalCandidate=baptismalCandidateRepository.save(baptismalCandidate);
		
		assertNotNull(baptismalCandidate.getId());
		return baptismalCandidate;
	}
	
	private Father addFelixFather() {
		Father father = new Father();
		Person person = new Person();
		
		Address address = new Address();
		
		person = createPerson(LocalDate.of(1970,3,21),"Félix","Merino","Martínez de Pinillos",GenderEnum.M,true,
				"felix.merino@gmail.com","660959325");
		
		address = createAddress("Av. Pablo Neruda","49","2º","B","28018","Madrid","Madrid","España");
		
		father.setAddress(address);
		father.setPersonData(person);
		father.setBirthLocation("Santa Cruz de Tenerife (Tenerife, España)");
		father.setNif("02622568Q");
		father=relativeRepository.save(father);
		return father;
	}
	
	private Mother addFelixMother(Address address) {
		Mother mother = new Mother();
		Person person = new Person();
		
		person = createPerson(LocalDate.of(1976,5,4),"María Teresa","Cabanes","Miró",GenderEnum.F,true,
				"mayte.cabanes@gmail.com","650676743");
		
//		address = createAddress("Av. Pablo Neruda","49","2º","B","28018","Madrid","Madrid","España");
		
		mother.setAddress(address);
		mother.setPersonData(person);
		mother.setBirthLocation("Sevilla (Sevilla, España)");
		mother=relativeRepository.save(mother);
		return mother;
	}
	
	private Person createPerson(LocalDate birthday, String name, String surname1, String surname2, GenderEnum gender, 
			boolean married, String email, String mobile) {
		Person person = new Person();
		person.setBirthday(birthday);
		person.setName(name);
		person.setSurname1(surname1);
		person.setSurname2(surname2);
		person.setGender(gender);
		person.setMarried(married);
		if (email !=null) 
			person.addEmailAccount(new EmailAccount(email));
		if (mobile !=null)
			person.addMobileNumber(new MobileNumber(mobile));
		return person;
	}



	private Address createAddress(String streetName, String streetNumber, String floor, String door, String postalCode, String city, String province, String country) {
		Address address = new Address();
		address.setStreetName(streetName);
		address.setStreetNumber(streetNumber);
		address.setFloor(floor);
		address.setDoor(door);
		address.setPostalCode(postalCode);
		address.setCity(city);
		address.setProvince(province);
		address.setCountry(country);
		
		return address;
	}

}
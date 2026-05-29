package org.fmm.librosparroquiales.common.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.common.model.MobileNumber;
import org.fmm.librosparroquiales.common.model.Parish;
import org.fmm.librosparroquiales.common.model.ParishPriest;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.repository.AddressRepository;
import org.fmm.librosparroquiales.common.repository.ParishPriestRepository;
import org.fmm.librosparroquiales.common.repository.ParishRepository;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
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
class LibrosParroquialesParishInsertionsTests {

    @Autowired
    private ParishRepository parishRepository;
	
    @Autowired
    private AddressRepository addressRepository;
	
    @Autowired
    private ParishPriestRepository parishPriestRepository;
	
    @Autowired
    private PersonRepository personRepository;
    
	@Test
	//@Transactional
	public void addMasterTypes() {
		addParish();
		addParishPriest();
	}

	private void addParish() {
		Parish parish = new Parish();
		Address address = new Address();
		
		parish.setName("San Pedro Regalado y San José de Calasanz");
		
		address.setStreetName("Plaza Peña Gudina");
		address.setStreetNumber("1");
		address.setPostalCode("28018");
		address.setCity("Madrid");
		address.setProvince("Madrid");
		address.setCountry("España");
		parish.setAddress(address);
		
		parish.setNif(null);
		parish.setTfno("917862230");
		address = addressRepository.save(address);
		parish.setAddress(address);
		parish = parishRepository.save(parish);
		assertNotNull(parish.getId());
	}
	
	private void addParishPriest() {
		Optional<Parish> parishOptional = null;
		Parish parish = null;
		Person personData = new Person();
		MobileNumber mobile = new MobileNumber();
		
		ParishPriest parishPriest = new ParishPriest();
		parishOptional = parishRepository.findFullParish(1);
		if (parishOptional.isPresent()) {
			parish = parishOptional.get();

			if (parish.getParishPriestList() != null && !parish.getParishPriestList().isEmpty()) 
				return; 
			mobile.setMobileNumber("610904743");
			parishPriest.setActive(true);
			
			personData.setName("Edgar Pablo");
			personData.setSurname1("Mena");
			personData.setSurname2("Alcázar");
			personData.setGender(GenderEnum.M);
			personData.setMarried(false);
			personData.addMobileNumber(mobile);
			
			personData = personRepository.save(personData);
			
			parishPriest.setPersonData(personData);
			parishPriest.setParish(parish);
			parishPriest = parishPriestRepository.save(parishPriest);
		}
	}
}
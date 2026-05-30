package org.fmm.librosparroquiales.common.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.repository.BaptismRequestRepository;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
import org.fmm.librosparroquiales.common.repository.RelativeRepository;
import org.fmm.librosparroquiales.common.testconfig.LibrosParroquialesCommonTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
class LibrosParroquialesQueryTests {

    @Autowired
    private Environment environment;
	@Autowired
	private RelativeRepository relativeRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BaptismRequestRepository baptismRequestRepository;
    
    
//	@Test
	void testConfig() {
		assertNotNull(environment.getProperty("server.port"), "Las propiedades no se han cargado");
	}
//	@Test
	void testInvitationByPerson() {
		Person person = null;
		person = personRepository.findFullPerson(13);
		assertNotNull(person);
		
		Father father = null;
		father = relativeRepository.findFullFatherByName("Félix", "Merino");
		assertNotNull(father);
	}
	
	@Test
	void getBaptismRequest() {
		String criterio = "Félix";
		LocalDate from=LocalDate.of(2010, 01, 01);
		LocalDate to=null;
		int pagina = 0;
		int tamanoPagina=20;
		Page<BaptismRequest> baptismRequestPage = null;
		Pageable ordenacionYPagina = PageRequest.of(pagina, tamanoPagina);
		baptismRequestPage=baptismRequestRepository.listBaptismRequestByCriterioAndDate(criterio, from, null,ordenacionYPagina);
		assertNotNull(baptismRequestPage);
		assertTrue(baptismRequestPage.getTotalElements()>0);

		criterio = "merino";
		baptismRequestPage=baptismRequestRepository.listBaptismRequestByCriterioAndDate(criterio, from, null,ordenacionYPagina);
		assertNotNull(baptismRequestPage);
		assertTrue(baptismRequestPage.getTotalElements()>0);
	}
	
}
/*

SELECT br DISTINCT 
FROM BaptismRequest br 
LEFT JOIN FETCH br.candidate c 
LEFT JOIN FETCH c.personData pD 
WHERE (LOWER(CONCAT(pD.name, ' ', pD.surname1, ' ', pD.surname2)) LIKE LOWER(CONCAT('%', :texto, '%'))) 
AND (:from IS NULL OR br.appointment >= :from) 
AND (:to IS NULL OR br.appointment <= :to) 
ORDER BY br.appointment, br.appointment desc

select br1_0.id,br1_0.appointment,c1_0.id,c1_0.active,c1_0.address_id,c1_0.birth_location,pd1_0.id,pd1_0.birthday,pd1_0.gender,pd1_0.married,pd1_0.name,pd1_0.nickname,pd1_0.surname1,pd1_0.surname2,br1_0.celebrated,br1_0.father_id,br1_0.father_grandfather_id,br1_0.father_grandmother_id,br1_0.godfather_id,br1_0.godmother_id,br1_0.mother_id,br1_0.mother_grandfather_id,br1_0.mother_grandmother_id from baptism_request br1_0 left join baptismal_candidate c1_0 on c1_0.id=br1_0.candidate left join person pd1_0 on pd1_0.id=c1_0.person_data where (lower((pd1_0.name||' '||pd1_0.surname1||' '||pd1_0.surname2)) like lower(('%'||?||'%')) escape '') and (? is null or br1_0.appointment>=?) and (? is null or br1_0.appointment<=?) order by br1_0.appointment,br1_0.appointment desc offset ? rows fetch first ? rows only] [n/a]
*/
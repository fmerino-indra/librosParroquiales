package org.fmm.librosparroquiales.common.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fmm.librosparroquiales.common.model.Address;
import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.Parish;
import org.fmm.librosparroquiales.common.model.ParishPriest;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.repository.BaptismRequestRepository;
import org.fmm.librosparroquiales.common.testconfig.LibrosParroquialesCommonTestApplication;
import org.fmm.librosparroquiales.common.util.enums.GenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//@ActiveProfiles("test")
//@PropertySource(value="classpath:application-test.yaml", factory = YamlPropertySourceFactory.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)


@SpringBootTest // (classes= { LibrosParroquialesCommonConfig.class})
@ContextConfiguration(classes = LibrosParroquialesCommonTestApplication.class)
class LibrosParroquialesServicesTests {

    @Autowired
    private BaptismRequestService baptismRequestService;
    @Autowired
    private BaptismRequestRepository baptismRequestRepository;
    
	@Test
	//@Transactional
	public void testReport() throws Exception {
		BaptismRequest br = null;
		byte[] pdf = null;
		br=baptismRequestRepository.findBaptismById(3);
		
		
		Map<String, Object> parameters = new HashMap<>();
		
		// 3. Convertir el objeto único en una colección de un solo elemento
        List<BaptismRequest> dataSourceElement = Collections.singletonList(br);

        // 4. Crear el DataSource desde cero pasando la lista de Beans
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataSourceElement);
        Parish p = createSampleParish();
        parameters.put("Parish", p);
        parameters.put("ParishPriest", p.getParishPriestActive());
        parameters.put("BaptismRequest", br);
		pdf = baptismRequestService.exportToPdf("/reports/PartidaBautismo-11-v2.1.jrxml", parameters, br);
		savePdf(pdf, "./fichero-text.pdf");
		
	}
	public void savePdf(byte[] pdfBytes, String filePath) throws IOException {
	    Path path = Paths.get(filePath);
	    Files.createDirectories(path.getParent()); // por si no existe la carpeta
	    Files.write(path, pdfBytes);
	}
	private Parish createSampleParish() {
		Parish p = new Parish();
		p.setName("San Pedro Regalado y San José de Calasanz");
		p.setNif("0123456789F");
		p.setTfno("917862230");

		ParishPriest pp = new ParishPriest();
//		pp.setActive(true);
		pp.setInitDate(LocalDate.of(2024, 9, 1));
		
		Person ppp = new Person();
		ppp.setGender(GenderEnum.M);
		ppp.setName("Edgar Pablo");
		ppp.setSurname1("Mena");
		ppp.setSurname2("Alcázar");

		Address address = new Address();
		address.setStreetName("Plaza Peña Gudina");
		address.setStreetNumber("1");
		address.setPostalCode("28018");
		address.setCity("Madrid");
		address.setProvince("Madrid");
		address.setCountry("España");
		
		p.setAddress(address);
		
		pp.setPersonData(ppp);
		p.addParishPriest(pp);

		return p;
	}
}
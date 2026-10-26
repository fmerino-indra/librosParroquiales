package org.fmm.librosparroquiales.common.service;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.fmm.librosparroquiales.common.model.BaptismRequest;
import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.fmm.librosparroquiales.common.model.Person;
import org.fmm.librosparroquiales.common.model.baptism.Father;
import org.fmm.librosparroquiales.common.model.baptism.GrandFather;
import org.fmm.librosparroquiales.common.model.baptism.GrandMother;
import org.fmm.librosparroquiales.common.model.baptism.Mother;
import org.fmm.librosparroquiales.common.repository.BaptismRequestRepository;
import org.fmm.librosparroquiales.common.repository.BaptismalCandidateRepository;
import org.fmm.librosparroquiales.common.repository.PersonRepository;
import org.fmm.librosparroquiales.common.repository.RelativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service(value = "BaptismRequestService")
public class BaptismRequestServiceImpl implements BaptismRequestService {

	@Autowired
	private BaptismRequestRepository baptismRequestRepository;
    @Autowired
    private BaptismalCandidateRepository baptismalCandidateRepository;
	@Autowired
	private RelativeRepository relativeRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Page<BaptismRequest> getBaptismRequest(String criterio, LocalDate from, LocalDate to) {
		int pagina = 0;
		int tamanoPagina=20;
		
		Pageable ordenacionYPagina = PageRequest.of(pagina, tamanoPagina, Sort.by("br.appointment").descending());
		return baptismRequestRepository.listBaptismRequestByCriterioAndDate(criterio, from, to,ordenacionYPagina);
	}

	@Override
	public BaptismRequest baptismRequestCreate(BaptismRequest baptismRequest) {
		BaptismalCandidate candidate = baptismRequest.getCandidate();
		Father father = baptismRequest.getFather();
		Mother mother = baptismRequest.getMother();
		GrandFather fatherGrandFather = baptismRequest.getFatherGrandFather();
		GrandMother fatherGrandMother = baptismRequest.getFatherGrandMother();
		GrandFather motherGrandFather = baptismRequest.getMotherGrandFather();
		GrandMother motherGrandMother = baptismRequest.getMotherGrandMother();
		Person godfather = baptismRequest.getGodFather();
		Person godmother = baptismRequest.getGodMother();
		Person celebrant = baptismRequest.getMinister();
		
		candidate = baptismalCandidateRepository.save(candidate);
		
		Optional<Person> aux = null;
		Person auxPerson = null;
		if (father.getPersonData().getId() != null) {
			aux = personRepository.findById(father.getPersonData().getId());
			if (aux.isPresent()) {
				auxPerson = aux.get();
				father.setPersonData(auxPerson);
			}
		}
		if (father != null)
			relativeRepository.save(father);
		if (mother != null)
			relativeRepository.save(mother);
		if (fatherGrandFather != null) {
			auxPerson = personRepository.save(fatherGrandFather.getPersonData());
			fatherGrandFather.setPersonData(auxPerson);
			relativeRepository.save(fatherGrandFather);
		}
		if (fatherGrandMother != null) {
			auxPerson = personRepository.save(fatherGrandMother.getPersonData());
			fatherGrandMother.setPersonData(auxPerson);
			relativeRepository.save(fatherGrandMother);
		}
		if (motherGrandFather != null) {
			auxPerson = personRepository.save(motherGrandFather.getPersonData());
			motherGrandFather.setPersonData(auxPerson);
			relativeRepository.save(motherGrandFather);
		}
		if (motherGrandMother != null) {
			auxPerson = personRepository.save(motherGrandMother.getPersonData());
			motherGrandMother.setPersonData(auxPerson);
			relativeRepository.save(motherGrandMother);
		}
		if (godfather != null)
			godfather=personRepository.save(godfather);
		if (godmother != null)
			godmother=personRepository.save(godmother);
		
		if (celebrant !=null)
			celebrant = personRepository.save(celebrant);
		
		baptismRequest.setGodFather(godfather);
		baptismRequest.setGodMother(godmother);
		baptismRequest.setMinister(celebrant);
		baptismRequestRepository.save(baptismRequest);
		
		return baptismRequest;
	}

	@Override
	public byte[] exportToPdf(String reportPath, Map<String, Object> params, BaptismRequest baptism) throws Exception {
		byte[] retorno = null;
        // 1. Cargar el archivo JRXML desde los recursos
        InputStream reportStream = getClass().getResourceAsStream(reportPath);
        if (reportStream == null) {
            throw new IllegalArgumentException("No se encontró el reporte en la ruta: " + reportPath);
        }

        System.out.println(
        	    JasperCompileManager.class
        	        .getProtectionDomain()
        	        .getCodeSource()
        	        .getLocation()
        	);        
        
        System.out.println(
        	    getClass().getResource("/imgs/logo-san-pedro-original.png")
        	);
        
        System.out.println(
        	    getClass().getResource("imgs/logo-san-pedro-original.png")
        	);
        System.out.println(
        	    getClass().getClassLoader()
        	        .getResource("jasperreports_extension.properties")
        	);
        
        System.out.println(
        		baptism.getCandidate().getBirthday().format(DateTimeFormatter.ofPattern("dd")) + "/" +
        		baptism.getCandidate().getBirthday().format(DateTimeFormatter.ofPattern("MMMM")) + "/" +
        		baptism.getCandidate().getBirthday().format(DateTimeFormatter.ofPattern("yyyy"))
        	);
        
        
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();

        Arrays.stream(ge.getAvailableFontFamilyNames())
              .filter(f -> f.contains("Roman"))
              .forEach(System.out::println);
        
        // 2. Compilar el reporte (si usas el .jrxml directamente)
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        List<BaptismRequest> baptismList = new ArrayList<>();
        baptismList.add(baptism);
        
        // 3. Rellenar el reporte con los parámetros y la conexión a la BD
        // Nota: Si usas objetos Java en vez de SQL, cambiar "dataSource.getConnection()" por "new JRBeanCollectionDataSource(listaObjetos)"
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, 
        		new JRBeanCollectionDataSource(baptismList));

        // 4. Exportar a un array de bytes (PDF)
        retorno= JasperExportManager.exportReportToPdf(jasperPrint);
        return retorno;
	}
}

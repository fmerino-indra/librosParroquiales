package org.fmm.librosparroquiales.common.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.fmm.librosparroquiales.common.testconfig.LibrosParroquialesCommonTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@Rollback(false)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = LibrosParroquialesCommonTestConfiguration.class)
class LibrosParroquialesConfigTests {

    @Autowired
    private Environment environment;

    //	@Test
	void testConfig() {
		assertNotNull(environment.getProperty("server.port"), "Las propiedades no se han cargado");
	}
}
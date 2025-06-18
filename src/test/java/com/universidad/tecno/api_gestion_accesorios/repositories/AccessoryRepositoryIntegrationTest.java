package com.universidad.tecno.api_gestion_accesorios.repositories;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// Elimina @Import, y en su lugar usa @EnableJpaRepositories si es necesario
// Pero en general @DataJpaTest ya detecta tus repositorios en el paquete base
class AccessoryRepositoryIntegrationTest {
 
}


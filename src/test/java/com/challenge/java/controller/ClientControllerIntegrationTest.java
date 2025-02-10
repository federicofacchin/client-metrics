package com.challenge.java.controller;

import com.challenge.java.model.Client;
import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.utils.TestClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
//configuracion utilizada para prevenir los request http y asi limitar el overhead
// ya que los test de integracion son mas costosos
@Testcontainers
@AutoConfigureMockMvc
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Client defaultClient;
    private ClientDTO invalidAgeClientDTO;

    private ClientDTO clientDTO;

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:16.3");

    //utilizado para sobreescribir las propiedades de la conexion de a la base de datos.
    //para utilizar las propiedades del contenedor
    @DynamicPropertySource
    public static void overrideDBProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",container::getJdbcUrl);
        registry.add("spring.datasource.password",container::getPassword);
        registry.add("spring.datasource.username",container::getUsername);

    }
    //with reuse permite tener siempre levantada la base de datos ya que esta se detiene y vuelve a ejecutarse
    //en cada test de integracion que utilicemos


    @BeforeEach
    public void setUp() {
        defaultClient = TestClientFactory.createDefaultClient();
        clientDTO = TestClientFactory.createClientDTOWithCustomValues("John", "Doe", 30, LocalDate.of(1994,1,6));
        invalidAgeClientDTO = TestClientFactory.createClientDTOWithCustomValues("John Exception","Doe" , -3 , LocalDate.of(1994,1,6));
    }

    @Test
    void ShouldCreateClientSuccesfullyTest() throws Exception {
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void ShouldObtainAllClientsWithPaginationSuccessfully() throws Exception {
        mockMvc.perform(get("/clients")
                        .requestAttr("page",0)
                        .requestAttr("size", 10))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.[0].clientDTO.name").value("John"))
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}



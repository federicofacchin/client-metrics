package com.challenge.java.repository;

import com.challenge.java.model.Client;
import com.challenge.java.model.DTO.MetricsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    //en este caso se utiliza una funcion propia de JPQL
    //la desviacion estandar es calculada de la siguiente manera
    // https://es.khanacademy.org/math/probability/data-distributions-a1/summarizing-spread-distributions/a/calculating-standard-deviation-step-by-step
    @Query("SELECT new com.challenge.java.model.DTO.MetricsResponseDTO(AVG(c.age), STDDEV(c.age)) FROM Client c")
    MetricsResponseDTO calculateMetrics();
}

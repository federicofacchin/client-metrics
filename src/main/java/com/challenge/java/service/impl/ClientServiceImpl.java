package com.challenge.java.service.impl;

import com.challenge.java.exception.InvalidAgeException;
import com.challenge.java.model.Client;
import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.model.DTO.ClientWithLifeExpectancyDTO;
import com.challenge.java.model.DTO.MetricsResponseDTO;
import com.challenge.java.repository.ClientRepository;
import com.challenge.java.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        //falta validar datos vacios
        validateClientAge(clientDTO.getAge(),clientDTO.getBirthDate());
        //falta validar si el cliente ya existe por ejemplo agregando un Dni
        Client clientToBeCreated = buildClient(clientDTO);
        return buildClientDTO(clientRepository.save(clientToBeCreated));
    }

    @Override
    public MetricsResponseDTO calculateMetrics() {
        return this.clientRepository.calculateMetrics();
    }

    private void validateClientAge(int age, LocalDate birthDate) {
        if( age != calculateAgeByBirthDate(birthDate)){
            throw new InvalidAgeException("The given " + age + " doesn't match with the birth date  " + birthDate + " please submit a valid one");
        }
    }

    private Client buildClient(ClientDTO clientDTO) {
        return Client.builder().age(clientDTO.getAge())
                .birthDate(clientDTO.getBirthDate())
                .name(clientDTO.getName())
                .lastName(clientDTO.getLastName())
                .build();
    }

    private ClientDTO buildClientDTO(Client client) {
        return ClientDTO.builder().age(client.getAge())
                .birthDate(client.getBirthDate())
                .name(client.getName())
                .lastName(client.getLastName())
                .build();
    }

    private ClientWithLifeExpectancyDTO buildClientWithLifeExpectancyDTO(ClientDTO clientDTO) {
        return ClientWithLifeExpectancyDTO.builder().clientDTO(clientDTO)
                .lifeExpectancy(Client.calculateLifeExpectancy(clientDTO.getAge()))
                .build();
    }

    private int calculateAgeByBirthDate(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        LocalDate birthdayThisYear = birthDate.withYear(today.getYear());
        int age =  Period.between(birthDate, today).getYears();
        return today.isBefore(birthdayThisYear) ? age : age -1 ;

    }

    @Override
    public Page<ClientWithLifeExpectancyDTO> getClientsWithLifeExpectancy(Pageable pageable) {
        // falta validar que la lista no este vacia
        return this.clientRepository.findAll(pageable)
                .map(client -> {
                    ClientDTO clientDTO = buildClientDTO(client);
                    return buildClientWithLifeExpectancyDTO(clientDTO);
                });
    }
}

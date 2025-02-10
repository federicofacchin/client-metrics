package com.challenge.java.controller;

import com.challenge.java.exception.InvalidAgeException;
import com.challenge.java.model.DTO.ClientDTO;
import com.challenge.java.model.DTO.ClientWithLifeExpectancyDTO;
import com.challenge.java.model.DTO.MetricsResponseDTO;
import com.challenge.java.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @Operation(
            summary = "Create a new client",
            description = "This endpoint creates a new client using the provided client data.",
            responses = {@ApiResponse(responseCode = "201", description = "Client created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidAgeException.class)))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Body Request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClientDTO.class))
            )
    )
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO){
        return new ResponseEntity<>(clientService.createClient(clientDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtain clients metrics",
            description = "calculates client metrics such as standard deviation and average age.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class))),
            }
    )
    @GetMapping("/metrics")
    public ResponseEntity<MetricsResponseDTO> obtainClientsMetrics(){
        return new ResponseEntity<>(clientService.calculateMetrics(), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtain all clients",
            description = "obtain a list of clients given page and size request attributes",
            responses =  {
                @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Page.class,
                                example = "{ \"content\": [{\"name\": \"John\", \"lastName\": \"Doe\", \"age\": 30," +
                                        " \"birthDate\": \"1994-01-06\"}], \"totalPages\": 3, \"totalElements\": 30," +
                                        " \"size\": 10, \"number\": 0 }")))
            }
    )
    @GetMapping
    public ResponseEntity<Page<ClientWithLifeExpectancyDTO>> getAllClients(Pageable pageable) {
        return new ResponseEntity<>(clientService.getClientsWithLifeExpectancy(pageable), HttpStatus.OK);
    }
}

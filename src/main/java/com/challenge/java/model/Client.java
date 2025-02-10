package com.challenge.java.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "clients")
@Entity
public class Client {

    public static final Double AVERAGE_LIFE_EXPECTANCY = 77.5;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id")
    private String id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private int age;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Version
    private Integer version;

    public static double calculateLifeExpectancy (int age) {
        return AVERAGE_LIFE_EXPECTANCY - (double) age;
    }
}

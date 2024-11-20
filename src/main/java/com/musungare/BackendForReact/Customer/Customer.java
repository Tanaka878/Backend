package com.musungare.BackendForReact.Customer;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;
    private LocalDate localDate;


    public Customer() {
    }

    public Customer(Long id, String name,
                    String surname, String email,
                     String password,
                    LocalDate localDate
                    ) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;

        this.password = password;
        this.localDate = localDate;

    }

    public Customer(String name,
                    String surname, String email,
                     String password,
                    LocalDate localDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;

        this.password = password;
        this.localDate = localDate;
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", localDate=" + localDate +
                '}';
    }
}

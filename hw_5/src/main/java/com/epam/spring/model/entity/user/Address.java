package com.epam.spring.model.entity.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String city;
    private String street;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;
}

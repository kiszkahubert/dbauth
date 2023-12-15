package com.kiszka.AuthServer.dbconnection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    private String emailAddress;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userKey;
    private String password;
    private String role;
}


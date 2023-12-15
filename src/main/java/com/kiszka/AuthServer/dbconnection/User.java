package com.kiszka.AuthServer.dbconnection;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userinfo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userkey")
    private int userKey;
    @Column(name = "emailaddress")
    private String emailAddress;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
}


package br.com.senai.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;

    @ManyToOne
    @JoinColumn(name="User_id")
    private Users user;
}
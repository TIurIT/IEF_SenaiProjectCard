package br.com.senai.controllers;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
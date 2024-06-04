package br.com.senai.controllers;

import br.com.senai.models.LoginRequest;
import br.com.senai.models.Users;
import br.com.senai.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UserController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @PostMapping(value="/createUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users createUsers(@RequestBody Users users){
        //Cria um novo objeto Users
        Users newUsers = new Users();
        newUsers.setPassword(passwordEncoder.encode(users.getPassword()));
        //Seta as propriedades
        newUsers.setUsername(users.getUsername());
        newUsers.setEmail(users.getEmail());

        //Chama o método save para salvar o objeto no banco de dados
        return usersRepository.save(newUsers);
    }

    @PutMapping(value="/updateUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users updateUsers(@RequestBody Users users){
        Users getUser = usersRepository.findById(users.getId()).orElseThrow();
        Users updateUsers = new Users();

        updateUsers.setId(users.getId());
        updateUsers.setUsername(users.getUsername());
        updateUsers.setEmail(users.getEmail());
        updateUsers.setPassword(users.getPassword());

        return usersRepository.save(updateUsers);
    }
    //Método deletar
    @DeleteMapping(value="/deleteUsers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Users deleteUsers(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Users getUsers = usersRepository.findById(id).orElseThrow();
        //chamamos o método q puxa oq sera deletado a ser deletado
        usersRepository.delete(getUsers);
        return getUsers;
    }
    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> loginUser(@RequestBody LoginRequest loginRequest) {
        // Find user by username
        Optional<Users> userOptional = usersRepository.findByUsername(loginRequest.getUsername());
        // Check if user exists
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }
        Users user = userOptional.get();
        // Validate password using PasswordEncoder
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Invalid credentials
        }
        // Login successful, return user information (optional)
        return ResponseEntity.ok(user); // You can decide what information to return in the response
    }
    // This class can be further extended to handle functionalities like generating JWT tokens for authentication
}

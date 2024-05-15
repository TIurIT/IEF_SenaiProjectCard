package br.com.senai.controllers;

import br.com.senai.models.Users;
import br.com.senai.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UsersRepository usersRepository;

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
        //Seta as propriedades do Coffee
        newUsers.setUsername(users.getUsername());
        newUsers.setEmail(users.getEmail());
        newUsers.setPassword(users.getPassword());
        //Chama o método save para salvar o objeto no banco de dados
        return usersRepository.save(newUsers);
    }

    @PutMapping(value="/updateUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users updateCoffee(@RequestBody Users users){
        Users getUser = usersRepository.findById(users.getId()).orElseThrow();
        Users updateUsers = new Users();

        updateUsers.setId(users.getId());
        updateUsers.setUsername(users.getUsername());
        updateUsers.setEmail(users.getEmail());
        updateUsers.setPassword(users.getPassword());

        return usersRepository.save(updateUsers);
    }
    //Método deletar coffee
    @DeleteMapping(value="/deleteUsers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Users deleteUsers(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Users getUsers = usersRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        usersRepository.delete(getUsers);
        return getUsers;
    }
}

package br.com.senai.controllers;

import br.com.senai.repositories.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    @Autowired
    CoffeeRepository coffeeRepository;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Coffee> getAllCoffee(){
        return coffeeRepository.findAll();
    }

    @PostMapping(value="/createCoffee",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Coffee createCoffee(@RequestBody Coffee coffee){
        //Cria um novo objeto Coffee
        Coffee newCoffee = new Coffee();
        //Seta as propriedades do Coffee
        newCoffee.setName(coffee.getName());
        newCoffee.setPrice(coffee.getPrice());
        //Chama o método save para salvar o objeto no banco de dados
        return coffeeRepository.save(newCoffee);
    }

    @PutMapping(value="/updateCoffee",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Coffee updateCoffee(@RequestBody Coffee coffee){
        Coffee getCoffee = coffeeRepository
                .findById(coffee.getId()).orElseThrow();
        Coffee updateCoffee = new Coffee();

        updateCoffee.setId(coffee.getId());
        updateCoffee.setName(coffee.getName());
        updateCoffee.setPrice(coffee.getPrice());

        return coffeeRepository.save(updateCoffee);
    }
    //Método deletar coffee
    @DeleteMapping(value="/deleteCoffee/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Coffee deleteCoffee(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Coffee getCoffee = coffeeRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        coffeeRepository.delete(getCoffee);
        return getCoffee;
    }
}

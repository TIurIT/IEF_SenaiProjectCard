package br.com.senai.controllers;


import br.com.senai.models.Partners;
import br.com.senai.models.Users;
import br.com.senai.repositories.PartnersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/parceiros")
public class PartnersController {
    @Autowired
    PartnersRepository partnersRepository;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Partners> getAllUsers(){
        return partnersRepository.findAll();
    }

    @PostMapping(value="/createPartners",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Partners createPartners(@RequestBody Partners partner){
        //Cria um novo objeto Users
        Partners newPartner = new Partners();
        //Seta as propriedades do Coffee
        newPartner.setName(partner.getName());
        newPartner.setOccupation(partner.getOccupation());
        newPartner.setCellphone(partner.getCellphone());
        newPartner.setPrice_hr(partner.getPrice_hr());
        //Chama o método save para salvar o objeto no banco de dados
        return partnersRepository.save(newPartner);
    }

    @PutMapping(value="/updatePartners",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Partners updatePartners(@RequestBody Partners partner){
        Partners getParner = partnersRepository.findById(partner.getId()).orElseThrow();
        Partners updatePartners = new Partners();

        updatePartners.setId(partner.getId());
        updatePartners.setName(partner.getName());
        updatePartners.setOccupation(partner.getOccupation());
        updatePartners.setCellphone(partner.getCellphone());
        updatePartners.setPrice_hr(partner.getPrice_hr());

        return partnersRepository.save(updatePartners);
    }
    //Método deletar coffee
    @DeleteMapping(value="/deletePartners/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Partners deletePartners(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Partners getPartner = partnersRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        partnersRepository.delete(getPartner);
        return getPartner;
    }
}

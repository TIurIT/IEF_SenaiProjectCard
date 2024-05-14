package br.com.senai.controllers;

import br.com.senai.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    BrandRepository brandRepository;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Brand> getAllBrand(){
        return brandRepository.findAll();
    }

    @PostMapping(value="/createBrand",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Brand createBrand(@RequestBody Brand brand){
        //Cria um novo objeto Brand
        Brand newBrand = new Brand();
        //Seta as propriedades do Users
        newBrand.setName(brand.getName());
        newBrand.setDescription(brand.getDescription());
        //Chama o método save para salvar o objeto no banco de dados
        return brandRepository.save(newBrand);
    }

    @PutMapping(value="/updateBrand",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Brand updateBrand(@RequestBody Brand brand){
        Brand getBrand = brandRepository
                .findById(brand.getId()).orElseThrow();
        Brand updateBrand = new Brand();

        updateBrand.setId(brand.getId());
        updateBrand.setName(brand.getName());
        updateBrand.setDescription(brand.getDescription());

        return brandRepository.save(updateBrand);
    }
    //Método deletar brand
    @DeleteMapping(value="/deletebrand/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Brand deleteBrand(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Brand getBrand = brandRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        brandRepository.delete(getBrand);
        return getBrand;
    }
}

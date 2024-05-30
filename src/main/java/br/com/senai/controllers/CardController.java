package br.com.senai.controllers;

import br.com.senai.models.Card;
import br.com.senai.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cartas")
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Card> getAllCard(){
        return cardRepository.findAll();
    }

    @PostMapping(value="/createCard",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Card createCard(@RequestBody Card card){
        //Cria um novo objeto Product
        Card newCard = new Card();
        //Seta as propriedades do Product
        newCard.setName(card.getName());
        newCard.setDescription(card.getDescription());
        newCard.setQuantity(card.getQuantity());
        //Chama o método save para salvar o objeto no banco de dados
        return cardRepository.save(newCard);
    }

    @PutMapping(value="/updateCard/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Card updateCard(@RequestBody Card card,@PathVariable long id){
        Card getCard = cardRepository.findById(id).orElseThrow();
        Card updateCard = new Card();

        updateCard.setId(id);
        updateCard.setName(getCard.getName());
        updateCard.setDescription(getCard.getDescription());
        updateCard.setQuantity(card.getQuantity());

        return cardRepository.save(updateCard);
    }
    //Método deletar product
    @DeleteMapping(value="/deleteCard/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@PathVariable pega um valor passado junto a barra de endereço
    public Card deleteCard(@PathVariable Long id){
        //Verificamos se existe o café no banco de dados procurando o id
        Card getCard = cardRepository.findById(id).orElseThrow();
        //chamamos o método .delete e passamos o café a ser deletado
        cardRepository.delete(getCard);
        return getCard;
    }
    @GetMapping(value = "/filtro/{palavra}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Card> filtrarLista(@PathVariable String palavra) {
        // Search for products using the provided 'palavra'
        List<Card> filteredCards = cardRepository.findByNameContainingIgnoreCase(palavra);

        // Check if any products were found
        if (filteredCards.isEmpty()) {
            // No products found, return an empty list
            return Collections.emptyList();
        }

        // Products found, return the filtered list
        return filteredCards;
    }
}


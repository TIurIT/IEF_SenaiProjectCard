package br.com.senai.repositories;

import br.com.senai.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CardRepository extends JpaRepository<Card,Long> {
    List<Card> findByNameContainingIgnoreCase(String palavra);
}

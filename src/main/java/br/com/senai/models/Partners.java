package br.com.senai.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name="partners")
@EqualsAndHashCode(of="id")
public class Partners implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false)
    private String celphone;

    @Column(nullable = false)
    private Double price_hr;
}

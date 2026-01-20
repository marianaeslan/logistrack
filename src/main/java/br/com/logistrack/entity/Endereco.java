package br.com.logistrack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_ENDERECO")
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String cep;
    String logradouro;
    String complemento;
    String bairro;
    String uf;


}

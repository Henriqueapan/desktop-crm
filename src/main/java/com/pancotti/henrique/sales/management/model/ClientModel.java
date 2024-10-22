package com.pancotti.henrique.sales.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientModel {
    private String nome;

    private String rg;

    private String cpf;

    private String email;

    private String telefone;

    private String celular;

    private String cep;

    private String endereco;

    private int numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;
}

//id int auto_increment primary key,
//nome varchar(100),
//rg varchar (30),
//cpf varchar (20),
//email varchar(200),
//telefone varchar(30),
//celular varchar(30),
//cep varchar(100),
//endereco varchar (255),
//numero int,
//complemento varchar (200),
//bairro varchar (100),
//cidade varchar (100),
//estado varchar (2)
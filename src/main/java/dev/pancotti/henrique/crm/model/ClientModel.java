package dev.pancotti.henrique.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Vector;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientModel {
    private int id;

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

    public ClientModel(
        String nome,
        String rg,
        String cpf,
        String email,
        String telefone,
        String celular,
        String cep,
        String endereco,
        int numero,
        String complemento,
        String bairro,
        String cidade,
        String estado
    ) {
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.celular = celular;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Vector<Object> toVector() {
        Vector<Object> v = new Vector<>();
        v.add(id);
        v.add(nome);
        v.add(rg);
        v.add(email);
        v.add(cpf);
        v.add(telefone);
        v.add(celular);
        v.add(cep);
        v.add(endereco);
        v.add(numero);
        v.add(complemento);
        v.add(bairro);
        v.add(cidade);
        v.add(estado);

        return v;
    }
}
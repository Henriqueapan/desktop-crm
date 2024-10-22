package com.pancotti.henrique.sales.management.dao;

import com.pancotti.henrique.sales.management.jdbc.ConnectionFactory;
import com.pancotti.henrique.sales.management.model.ClientModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO {
    private final Connection connection;
    private final String TABLE_NAME = "tb_clientes";
    private final String SQL_CREATE_QUERY = """
    INSERT INTO tb_clientes
    (
        NOME,
        RG,
        CPF,
        EMAIL,
        TELEFONE,
        CELULAR,
        CEP,
        ENDERECO,
        NUMERO,
        COMPLEMENTO,
        BAIRRO,
        CIDADE,
        ESTADO
    )
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
    """;

    private final String SQL_DELETE_QUERY = """
    DELETE FROM tb_clientes
    WHERE id = ?
    """;

    public ClientDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void createClient(ClientModel client) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                SQL_CREATE_QUERY
            );
            stmt.setString(1, client.getNome());
            stmt.setString(2, client.getRg());
            stmt.setString(3, client.getCpf());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getTelefone());
            stmt.setString(6, client.getCelular());
            stmt.setString(7, client.getCep());
            stmt.setString(8, client.getEndereco());
            stmt.setInt(9, client.getNumero());
            stmt.setString(10, client.getComplemento());
            stmt.setString(11, client.getBairro());
            stmt.setString(12, client.getCidade());
            stmt.setString(13, client.getEstado());

            stmt.execute();

            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar cadastrar o novo cliente: " + e.getMessage());
        }

    }

    public void deleteClient(ClientModel client) {}
}

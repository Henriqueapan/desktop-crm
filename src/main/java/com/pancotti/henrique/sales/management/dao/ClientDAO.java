package com.pancotti.henrique.sales.management.dao;

import com.mysql.cj.xdevapi.Client;
import com.pancotti.henrique.sales.management.jdbc.ConnectionFactory;
import com.pancotti.henrique.sales.management.model.ClientModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.pancotti.henrique.sales.management.util.UIUtil.getErrorDialogJTextAreaContent;

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

    private final String SQL_FIND_ALL_QUERY = """
    SELECT * FROM tb_clientes;
    """;

    private final String SQL_FIND_BY_NOME_LIKENESS = """
    SELECT * FROM tb_clientes WHERE nome LIKE CONCAT('%',?,'%');
    """;

    private final String SQL_DELETE_QUERY = """
    DELETE FROM tb_clientes
    WHERE id = ?
    """;

    public ClientDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void create(ClientModel client) {
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

    public List<ClientModel> findAll() {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ALL_QUERY);
            ResultSet allClientRs = stmt.executeQuery();
            List<ClientModel> clients = new ArrayList<>();

            while (allClientRs.next()) {
                final ClientModel client = new ClientModel(
                    allClientRs.getInt("id"),
                    allClientRs.getString("nome"),
                    allClientRs.getString("rg"),
                    allClientRs.getString("cpf"),
                    allClientRs.getString("email"),
                    allClientRs.getString("telefone"),
                    allClientRs.getString("celular"),
                    allClientRs.getString("cep"),
                    allClientRs.getString("endereco"),
                    allClientRs.getInt("numero"),
                    allClientRs.getString("complemento"),
                    allClientRs.getString("bairro"),
                    allClientRs.getString("cidade"),
                    allClientRs.getString("estado")
                );

                clients.add(client);
            }

            if (clients.isEmpty()){
                JOptionPane.showMessageDialog(null, "Não há clientes cadastrados");
                return List.of();
            }

            return clients;

        } catch (SQLException exc) {
            JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
                exc,
                "Houve um erro ao tentar buscar os clientes:"
            );

            JOptionPane.showMessageDialog(
                null,
                errorTxtArea,
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

            return List.of();
        }
    }

    public List<ClientModel> findByNome(String nome) {
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_NOME_LIKENESS);
            stmt.setString(1, nome);
            ResultSet clientRs = stmt.executeQuery();

            List<ClientModel> clients = new ArrayList<>();

            while (clientRs.next()) {
                clients.add(new ClientModel(
                    clientRs.getInt("id"),
                    clientRs.getString("nome"),
                    clientRs.getString("rg"),
                    clientRs.getString("nome"),
                    clientRs.getString("email"),
                    clientRs.getString("telefone"),
                    clientRs.getString("celular"),
                    clientRs.getString("cep"),
                    clientRs.getString("endereco"),
                    clientRs.getInt("numero"),
                    clientRs.getString("complemento"),
                    clientRs.getString("bairro"),
                    clientRs.getString("cidade"),
                    clientRs.getString("estado")
                ));
            }

            if (clients.isEmpty()){
                JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
                    "Não há clientes com nome contendo '" + nome + "' cadastrados."
                );

                JOptionPane.showMessageDialog(
                    null,
                    errorTxtArea,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );

                return List.of();
            }

            return clients;
        } catch (SQLException exc) {
            JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
                exc,
                "Houve um erro ao tentar buscar o cliente:"
            );

            JOptionPane.showMessageDialog(
                null,
                errorTxtArea,
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

            return List.of();
        }
    }

    public void deleteClient(ClientModel client) {}
}

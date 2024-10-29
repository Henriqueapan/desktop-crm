package dev.pancotti.henrique.crm.dao;

import dev.pancotti.henrique.crm.jdbc.ConnectionFactory;
import dev.pancotti.henrique.crm.model.ClientModel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    // TODO: Remover chamadas para levantar dialogo de erro do DAO
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

    private final String SQL_FIND_BY_CPF_QUERY = """
    SELECT * FROM tb_clientes WHERE CPF = ?
    """;

    private final String SQL_FIND_BY_NOME_LIKENESS_QUERY = """
    SELECT * FROM tb_clientes WHERE NOME LIKE CONCAT('%',?,'%');
    """;

    private final String SQL_UPDATE_QUERY = """
    UPDATE tb_clientes
    SET
        NOME=?,
        RG=?,
        CPF=?,
        EMAIL=?,
        TELEFONE=?,
        CELULAR=?,
        CEP=?,
        ENDERECO=?,
        NUMERO=?,
        COMPLEMENTO=?,
        BAIRRO=?,
        CIDADE=?,
        ESTADO=?
    WHERE ID=?
    """;

    private final String SQL_DELETE_QUERY = """
    DELETE FROM tb_clientes
    WHERE ID = ?
    """;

    public ClientDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void create(ClientModel client) throws SQLException {
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
    }

    public List<ClientModel> findAll() throws SQLException {
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

        return clients;
    }

    public List<ClientModel> findByNome(String nome) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_NOME_LIKENESS_QUERY);
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

        return clients;
    }

    public ClientModel findByCpf(String cpf) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_CPF_QUERY);
        stmt.setString(1, cpf);
        ResultSet clientRs = stmt.executeQuery();

        if (clientRs.next()) {
            return new ClientModel(
                clientRs.getInt("id"),
                clientRs.getString("nome"),
                clientRs.getString("rg"),
                clientRs.getString("cpf"),
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
            );
        }
        else return null;
    }

    public void update(ClientModel client) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
            SQL_UPDATE_QUERY
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

        stmt.setInt(14, client.getId());

        stmt.execute();

        // JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");

        // catch (SQLException e) {
//            JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
//                e,
//                "Erro ao tentar atualizado o cliente:"
//            );
//
//            JOptionPane.showMessageDialog(
//                null,
//                errorTxtArea,
//                "Erro",
//                JOptionPane.ERROR_MESSAGE
//            );
        // }
    }

    public void delete(ClientModel client) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
            SQL_DELETE_QUERY
        );
        stmt.setInt(1, client.getId());

        stmt.execute();

//        JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso");
//        catch (SQLException e) {
//            JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
//                e,
//                "Erro ao tentar excluir o novo cliente:"
//            );
//
//            JOptionPane.showMessageDialog(
//                null,
//                errorTxtArea,
//                "Erro",
//                JOptionPane.ERROR_MESSAGE
//            );
//        }
    }
}

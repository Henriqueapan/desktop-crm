package dev.pancotti.henrique.crm.jdbc;

import dev.pancotti.henrique.crm.dao.ClientDAO;
import dev.pancotti.henrique.crm.model.ClientModel;

import javax.swing.*;
import java.sql.SQLException;

public class ConnectionTester {
    public static void main(String[] args) {
        try {
//            new ConnectionFactory().getConnection();

            // Tentando cadastrar novo cliente
            ClientModel testClienteModel = new ClientModel(
                "Teste",
                "Teste",
                "Teste",
                "Teste",
                "Teste",
                "Teste",
                "Teste",
                "Teste",
                1,
                "Teste",
                "Teste",
                "Teste",
                "MG"
            );

            ClientDAO clientDAO = new ClientDAO();

            clientDAO.create(testClienteModel);
//            JOptionPane.showMessageDialog(null, "Conectado");
        } catch (RuntimeException runtimeException) {
            JOptionPane.showMessageDialog(null, runtimeException.getCause().getMessage());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

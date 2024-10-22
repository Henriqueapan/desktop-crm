package com.pancotti.henrique.sales.management.jdbc;

import com.pancotti.henrique.sales.management.dao.ClientDAO;
import com.pancotti.henrique.sales.management.model.ClientModel;

import javax.swing.*;

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

            clientDAO.createClient(testClienteModel);
//            JOptionPane.showMessageDialog(null, "Conectado");
        } catch (RuntimeException runtimeException) {
            JOptionPane.showMessageDialog(null, runtimeException.getCause().getMessage());
        }
    }
}

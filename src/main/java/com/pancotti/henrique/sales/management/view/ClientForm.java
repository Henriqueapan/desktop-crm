package com.pancotti.henrique.sales.management.view;

import com.pancotti.henrique.sales.management.dao.ClientDAO;
import com.pancotti.henrique.sales.management.model.ClientModel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Set;

public class ClientForm extends JFrame {
    private JPanel main;
    private JLabel headerStringLabel;
    private JTabbedPane tabbedPane1;
    private JTextField codigoTxt;
    private JLabel codigoLabel;
    private JTextField nomeTxt;
    private JLabel nomeLabel;
    private JButton pesquisarButton;
    private JLabel celularLabel;
    private JFormattedTextField celularTxt;
    private JLabel emailLabel;
    private JTextField emailTxt;
    private JFormattedTextField telefoneFixoTxt;
    private JLabel telefoneFixoLabel;
    private JComboBox<String> ufComboBox;
    private JLabel ufLabel;
    private JLabel cepLabel;
    private JFormattedTextField cepTxt;
    private JLabel enderecoLabel;
    private JTextField enderecoTxt;
    private JTextField numTxt;
    private JLabel numLabel;
    private JLabel bairroLabel;
    private JTextField bairroTxt;
    private JTextField cidadeTxt;
    private JLabel cidadeLabel;
    private JTextField complementoTxt;
    private JLabel complementoLabel;
    private JTextField rgTxt;
    private JLabel rgLabel;
    private JFormattedTextField cpfTxt;
    private JLabel cpfLabel;
    private JButton saveButton;

    private class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ClientModel client = new ClientModel(
                    nomeTxt.getText(),
                    rgTxt.getText(),
                    cpfTxt.getText(),
                    emailTxt.getText(),
                    telefoneFixoTxt.getText(),
                    celularTxt.getText(),
                    cepTxt.getText(),
                    enderecoTxt.getText(),
                    Integer.parseInt(numTxt.getText()),
                    complementoTxt.getText(),
                    bairroTxt.getText(),
                    cidadeTxt.getText(),
                    Objects.requireNonNull(ufComboBox.getSelectedItem()).toString()
                );

                new ClientDAO().createClient(client);

                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
            } catch (Exception exc) {
                JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
                    exc,
                    "Houve um erro ao cadastrar o cliente:"
                );

                JOptionPane.showMessageDialog(
                    null,
                    errorTxtArea,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private static JTextArea getErrorDialogJTextAreaContent(Exception exc, String contextMessage) {
        final String errorMessage =
            contextMessage + "\n\n" + exc.getClass().getSimpleName() + "\n\n" + exc.getLocalizedMessage();
        final int COLUMN_WIDTH = 25;

        JTextArea errorTxtArea = new JTextArea(errorMessage);
        errorTxtArea.setColumns(COLUMN_WIDTH);
        errorTxtArea.setRows(errorMessage.length() / COLUMN_WIDTH);
        errorTxtArea.setEditable(false);
        errorTxtArea.setLineWrap(true);
        errorTxtArea.setWrapStyleWord(true);
        errorTxtArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorTxtArea.setAlignmentY(Component.CENTER_ALIGNMENT);
        errorTxtArea.setOpaque(false);
        errorTxtArea.setSize(errorTxtArea.getPreferredSize().width, 1);
        errorTxtArea.setBorder(new EmptyBorder(2, 2, 2, 2));

        return errorTxtArea;
    }

    public ClientForm() {
        super("Sales Management");
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme())); // Exemplo de tema escuro
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setContentPane(main);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        final Set<String> UF_SET = Set.of(
            "AC", // Acre
            "AL", // Alagoas
            "AP", // Amapá
            "AM", // Amazonas
            "BA", // Bahia
            "CE", // Ceará
            "DF", // Distrito Federal
            "ES", // Espírito Santo
            "GO", // Goiás
            "MA", // Maranhão
            "MT", // Mato Grosso
            "MS", // Mato Grosso do Sul
            "MG", // Minas Gerais
            "PA", // Pará
            "PB", // Paraíba
            "PR", // Paraná
            "PE", // Pernambuco
            "PI", // Piauí
            "RJ", // Rio de Janeiro
            "RN", // Rio Grande do Norte
            "RS", // Rio Grande do Sul
            "RO", // Rondônia
            "RR", // Roraima
            "SC", // Santa Catarina
            "SP", // São Paulo
            "SE", // Sergipe
            "TO"  // Tocantins
        );
        UF_SET.stream().sorted().forEach(uf -> this.ufComboBox.addItem(uf));

        saveButton.addActionListener(new SaveButtonActionListener());
    }
}

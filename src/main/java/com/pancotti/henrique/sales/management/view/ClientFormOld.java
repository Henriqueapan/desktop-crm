package com.pancotti.henrique.sales.management.view;

import com.pancotti.henrique.sales.management.dao.ClientDAO;
import com.pancotti.henrique.sales.management.model.ClientModel;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Set;

public class ClientFormOld extends JFrame {
    private JPanel main;
    private JTabbedPane tabbedPane1;
    private JTextField codigo;
    private JLabel codigo_label;
    private JTextField nome;
    private JTextField email;
    private JLabel emailLabel;
    private JLabel nomeLabel;
    private JLabel celularLabel;
    private JFormattedTextField celular;
    private JFormattedTextField telefoneFixo;
    private JLabel telefoneFixoLabel;
    private JButton pesquisarNomeButton;
    private JLabel cepLabel;
    private JFormattedTextField cep;
    private JTextField endereco;
    private JLabel enderecoLabel;
    private JTextField num;
    private JLabel numLabel;
    private JTextField bairro;
    private JLabel bairroLabel;
    private JTextField cidade;
    private JLabel cidadeLabel;
    private JLabel complementoLabel;
    private JTextField complemento;
    private JComboBox<String> ufComboBox;
    private JLabel ufLabel;
    private JFormattedTextField rg;
    private JLabel rgLabel;
    private JFormattedTextField cpf;
    private JLabel cpfLabel;
    private JTextField nomeBusca;
    private JLabel nomeBuscaLabel;
    private JTable tabelaCliente;
    private JButton nomeBuscaButton;
    private JButton saveButton;

    public ClientFormOld() {
        super("Sales Management");
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme())); // Exemplo de tema escuro
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.setContentPane(main);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

//        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
//            field.setAccessible(true);
//            if (field.getType().isAssignableFrom(JTextField.class)) {
//                try {
//                    Object fieldValue = field.get(this);
//                    Method setBorderMethod = fieldValue.getClass().getMethod("setBorder", Border.class);
//
//                    setBorderMethod.invoke(fieldValue, new EmptyBorder(10));
//                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

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

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientModel client = new ClientModel(
                        nome.getText(),
                        rg.getText(),
                        cpf.getText(),
                        email.getText(),
                        telefoneFixo.getText(),
                        celular.getText(),
                        cep.getText(),
                        endereco.getText(),
                        Integer.parseInt(num.getText()),
                        complemento.getText(),
                        bairro.getText(),
                        cidade.getText(),
                        Objects.requireNonNull(ufComboBox.getSelectedItem()).toString()
                    );

                    new ClientDAO().createClient(client);

                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Houve um erro ao cadastrar o cliente. " + exc.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
//                JOptionPane.showMessageDialog(null, "Teste");
            }
        });

    }
}

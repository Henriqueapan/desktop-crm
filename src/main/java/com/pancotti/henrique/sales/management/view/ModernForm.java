package com.pancotti.henrique.sales.management.view;

import com.pancotti.henrique.sales.management.dao.ClientDAO;
import com.pancotti.henrique.sales.management.model.ClientModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class ModernForm {
    private static final int V_GAP_MAX_HEIGHT = 25;

    private JFrame frame;
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

    private JTextField consultaNomeTxt;
    private JButton consultaPesquisarButton;
    private JTable consultaTable;

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

    public void setMainFrameMinimumSize(Dimension dimension) {
        if (dimension.width < 1200) dimension.width = 1200;

        this.frame.setMinimumSize(dimension);
    }

    public void setMainPanelMaximumSize(Dimension dimension) {
        this.main.setMaximumSize(dimension);
    }

    private static void setFontForContainerChildren(Container container, Font font) {
        Arrays.stream(container.getComponents()).forEach(component -> {
            component.setFont(font);

            if (component instanceof Container) {
                setFontForContainerChildren((Container) component, font);
            }
        });
    }

    public void setFormFont(Font font) {
        setFontForContainerChildren(main, font);
    }

    public ModernForm() {
        // Inicializar componentes
        main = new JPanel(new BorderLayout());
        tabbedPane1 = new JTabbedPane();
        JPanel cadastroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.insets = new Insets(15, 10, 15, 10); // Margens entre componentes

        // Primeira linha: Código
        codigoLabel = new JLabel("Código:");

        codigoTxt = new JTextField(20);
        codigoTxt.setMinimumSize(new Dimension(180, codigoTxt.getPreferredSize().height));
        codigoTxt.setPreferredSize(new Dimension(250, codigoTxt.getPreferredSize().height));

        gbc.gridx = 0;
        gbc.gridy = 0;
        cadastroPanel.add(codigoLabel, gbc);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        cadastroPanel.add(codigoTxt, gbc);

        // Segunda linha: Nome e Pesquisar
        nomeLabel = new JLabel("Nome:");
        nomeTxt = new JTextField(20);
        nomeTxt.setMinimumSize(new Dimension(180, nomeTxt.getPreferredSize().height));
        nomeTxt.setPreferredSize(new Dimension(250, nomeTxt.getPreferredSize().height));

        pesquisarButton = new JButton("Pesquisar");

        gbc.gridy = 1;
        gbc.gridx = 0;
        cadastroPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
//        gbc.weightx = 1.0;
        cadastroPanel.add(nomeTxt, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        cadastroPanel.add(pesquisarButton, gbc);

        // Terceira linha: Email, Celular e Telefone Fixo
        emailLabel = new JLabel("E-mail:");
        emailTxt = new JTextField(20);

        emailTxt.setMinimumSize(new Dimension(180, emailTxt.getPreferredSize().height));
        emailTxt.setPreferredSize(new Dimension(250, emailTxt.getPreferredSize().height));

        celularLabel = new JLabel("Celular:");
        telefoneFixoLabel = new JLabel("Telefone Fixo:");

        try {
            celularTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("(##) #####-####"));
            celularTxt.setColumns(10);
            celularTxt.setMinimumSize(new Dimension(180, celularTxt.getPreferredSize().height));
            celularTxt.setPreferredSize(new Dimension(250, celularTxt.getPreferredSize().height));

            telefoneFixoTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("(##) ####-####"));
            telefoneFixoTxt.setColumns(9);
            telefoneFixoTxt.setMinimumSize(new Dimension(180, telefoneFixoTxt.getPreferredSize().height));
            telefoneFixoTxt.setPreferredSize(new Dimension(250, telefoneFixoTxt.getPreferredSize().height));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        cadastroPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        cadastroPanel.add(emailTxt, gbc);

        gbc.gridx = 2;
        cadastroPanel.add(celularLabel, gbc);

        gbc.gridx = 3;
        cadastroPanel.add(celularTxt, gbc);

        gbc.gridx = 4;
        cadastroPanel.add(telefoneFixoLabel, gbc);

        gbc.gridx = 5;
        cadastroPanel.add(telefoneFixoTxt, gbc);

        // Quarta linha: CEP, Endereço e Número
        cepLabel = new JLabel("CEP:");
        try {
            cepTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("#####-###"));
            cepTxt.setColumns(8);
            cepTxt.setMinimumSize(new Dimension(180, cepTxt.getPreferredSize().height));
            cepTxt.setPreferredSize(new Dimension(250, cepTxt.getPreferredSize().height));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        enderecoLabel = new JLabel("Endereço:");
        enderecoTxt = new JTextField(20);
        enderecoTxt.setMinimumSize(new Dimension(180, enderecoTxt.getPreferredSize().height));
        enderecoTxt.setPreferredSize(new Dimension(250, enderecoTxt.getPreferredSize().height));

        numLabel = new JLabel("Número:");
        numTxt = new JTextField(5);
        numTxt.setMinimumSize(new Dimension(50, numTxt.getPreferredSize().height));
        numTxt.setPreferredSize(new Dimension(120, numTxt.getPreferredSize().height));

        gbc.gridx = 0;
        gbc.gridy = 3;
        cadastroPanel.add(cepLabel, gbc);

        gbc.gridx = 1;
        cadastroPanel.add(cepTxt, gbc);

        gbc.gridx = 2;
        cadastroPanel.add(enderecoLabel, gbc);

        gbc.gridx = 3;
        cadastroPanel.add(enderecoTxt, gbc);

        gbc.gridx = 4;
        cadastroPanel.add(numLabel, gbc);

        gbc.gridx = 5;
        cadastroPanel.add(numTxt, gbc);

        // Quinta linha: Bairro, Cidade, Complemento e UF
        bairroLabel = new JLabel("Bairro:");
        bairroTxt = new JTextField(15);
        bairroTxt.setMinimumSize(new Dimension(180, bairroTxt.getPreferredSize().height));
        bairroTxt.setPreferredSize(new Dimension(250, bairroTxt.getPreferredSize().height));

        cidadeLabel = new JLabel("Cidade:");
        cidadeTxt = new JTextField(15);
        cidadeTxt.setMinimumSize(new Dimension(180, cidadeTxt.getPreferredSize().height));
        cidadeTxt.setPreferredSize(new Dimension(250, cidadeTxt.getPreferredSize().height));

        complementoLabel = new JLabel("Complemento:");
        complementoTxt = new JTextField(15);
        complementoTxt.setMinimumSize(new Dimension(180, complementoTxt.getPreferredSize().height));
        complementoTxt.setPreferredSize(new Dimension(250, complementoTxt.getPreferredSize().height));

        ufLabel = new JLabel("UF:");
        ufComboBox = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 4;
        cadastroPanel.add(bairroLabel, gbc);

        gbc.gridx = 1;
        cadastroPanel.add(bairroTxt, gbc);

        gbc.gridx = 2;
        cadastroPanel.add(cidadeLabel, gbc);

        gbc.gridx = 3;
        cadastroPanel.add(cidadeTxt, gbc);

        gbc.gridx = 4;
        cadastroPanel.add(complementoLabel, gbc);

        gbc.gridx = 5;
        cadastroPanel.add(complementoTxt, gbc);

        gbc.gridx = 6;
        cadastroPanel.add(ufLabel, gbc);

        gbc.gridx = 7;
        cadastroPanel.add(ufComboBox, gbc);

        // Sexta linha: RG, CPF
        rgLabel = new JLabel("RG:");
        rgTxt = new JTextField(10);
        rgTxt.setMinimumSize(new Dimension(180, rgTxt.getPreferredSize().height));
        rgTxt.setPreferredSize(new Dimension(250, rgTxt.getPreferredSize().height));

        cpfLabel = new JLabel("CPF:");
        try {
            cpfTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("###.###.###-##"));
            cpfTxt.setColumns(11);
            cpfTxt.setMinimumSize(new Dimension(180, cpfTxt.getPreferredSize().height));
            cpfTxt.setPreferredSize(new Dimension(250, cpfTxt.getPreferredSize().height));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 5;
        cadastroPanel.add(rgLabel, gbc);

        gbc.gridx = 1;
        cadastroPanel.add(rgTxt, gbc);

        gbc.gridx = 2;
        cadastroPanel.add(cpfLabel, gbc);

        gbc.gridx = 3;
        cadastroPanel.add(cpfTxt, gbc);

        // Sétima linha: Botão Salvar
        saveButton = new JButton("Cadastrar");
        saveButton.setPreferredSize(new Dimension(150, saveButton.getPreferredSize().height));

        gbc.gridx = 0;
        gbc.gridy = 6;
//        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;

        cadastroPanel.add(saveButton, gbc);

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

        // Adicionar aba de cadastro
        tabbedPane1.addTab("Cadastro", cadastroPanel);

        // Consulta Panel
        JPanel consultaPanel = new JPanel(new BorderLayout(10, 10));
        JPanel consultaTopPanel = new JPanel(new FlowLayout());

        consultaNomeTxt = new JTextField(15);
        consultaPesquisarButton = new JButton("Pesquisar");

        consultaTopPanel.add(new JLabel("Nome:"));
        consultaTopPanel.add(consultaNomeTxt);
        consultaTopPanel.add(consultaPesquisarButton);

        // Tabela de consulta
        String[] colunas = {"Código", "Nome", "Celular", "E-mail"};
        DefaultTableModel model = new DefaultTableModel(null, colunas);
        consultaTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(consultaTable);
        consultaPanel.add(consultaTopPanel, BorderLayout.NORTH);
        consultaPanel.add(scrollPane, BorderLayout.CENTER);

        tabbedPane1.addTab("Consulta", consultaPanel);

        main.add(tabbedPane1, BorderLayout.CENTER);
    }

    /**
     * Creates and shows a new visible ModernForm JFrame.
     * @return The created ModernForm object
     */
    public void createFrameAndShowModernForm() {
        this.frame = new JFrame("Modern Form");

//        ModernForm form = new ModernForm();
        this.frame.setContentPane(this.main);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
}

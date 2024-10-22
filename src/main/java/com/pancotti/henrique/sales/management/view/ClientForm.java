package com.pancotti.henrique.sales.management.view;

import com.pancotti.henrique.sales.management.dao.ClientDAO;
import com.pancotti.henrique.sales.management.model.ClientModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class ClientForm {
    private static final int V_GAP_MAX_HEIGHT = 25;

    private JFrame frame;
    private JPanel main;
    private JLabel headerStringLabel;
    private JTabbedPane tabbedPane;
    private JPanel cadastroPanel;
    private JPanel cadastroInputPanel;
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
    private JPanel saveButtonPanel;
    private JButton saveButton;

    private JTextField consultaNomeTxt;
    private JButton consultaPesquisarButton;
    private JTable consultaTable;

    private static final DocumentFilter digitsOnlyDocumentFilter = new DocumentFilter() {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException
        {
            if (string.matches("\\d+")) {  // Verifica se todos os caracteres são dígitos
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
            throws BadLocationException
        {
            if (text.matches("\\d+")) {  // Verifica se todos os caracteres são dígitos
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    };

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

    private static void setFontForChildComponents(Container parentContainer, Font font) {
        Arrays.stream(parentContainer.getComponents()).forEach(component -> {
            component.setFont(font);

            if (component instanceof Container) {
                setFontForChildComponents((Container) component, font);
            }
        });
    }

    public void setFormFont(Font font) {
        setFontForChildComponents(main, font);
    }

    public ClientForm() {
        // Inicializar componentes
        main = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
        cadastroPanel = new JPanel(new BorderLayout());
        cadastroInputPanel = new JPanel(new GridBagLayout());
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
        cadastroInputPanel.add(codigoLabel, gbc);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        cadastroInputPanel.add(codigoTxt, gbc);

        // Segunda linha: Nome e Pesquisar
        nomeLabel = new JLabel("Nome:");
        nomeTxt = new JTextField(20);
        nomeTxt.setMinimumSize(new Dimension(180, nomeTxt.getPreferredSize().height));
        nomeTxt.setPreferredSize(new Dimension(250, nomeTxt.getPreferredSize().height));

        pesquisarButton = new JButton("Pesquisar");

        gbc.gridy = 1;
        gbc.gridx = 0;
        cadastroInputPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
//        gbc.weightx = 1.0;
        cadastroInputPanel.add(nomeTxt, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        cadastroInputPanel.add(pesquisarButton, gbc);

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

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) celularTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);

            telefoneFixoTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("(##) ####-####"));
            telefoneFixoTxt.setColumns(9);
            telefoneFixoTxt.setMinimumSize(new Dimension(180, telefoneFixoTxt.getPreferredSize().height));
            telefoneFixoTxt.setPreferredSize(new Dimension(250, telefoneFixoTxt.getPreferredSize().height));

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) telefoneFixoTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        cadastroInputPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(emailTxt, gbc);

        gbc.gridx = 2;
        cadastroInputPanel.add(celularLabel, gbc);

        gbc.gridx = 3;
        cadastroInputPanel.add(celularTxt, gbc);

        gbc.gridx = 4;
        cadastroInputPanel.add(telefoneFixoLabel, gbc);

        gbc.gridx = 5;
        cadastroInputPanel.add(telefoneFixoTxt, gbc);

        // Quarta linha: CEP, Endereço e Número
        cepLabel = new JLabel("CEP:");
        try {
            cepTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("#####-###"));
            cepTxt.setColumns(8);
            cepTxt.setMinimumSize(new Dimension(180, cepTxt.getPreferredSize().height));
            cepTxt.setPreferredSize(new Dimension(250, cepTxt.getPreferredSize().height));

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) cepTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        enderecoLabel = new JLabel("Endereço:");
        enderecoTxt = new JTextField(20);
        enderecoTxt.setMinimumSize(new Dimension(180, enderecoTxt.getPreferredSize().height));
        enderecoTxt.setPreferredSize(new Dimension(250, enderecoTxt.getPreferredSize().height));

        numLabel = new JLabel("Número:");
        numTxt = new JTextField(7);
        numTxt.setMinimumSize(new Dimension(80, numTxt.getPreferredSize().height));
        numTxt.setPreferredSize(new Dimension(130, numTxt.getPreferredSize().height));

        // Configurar o filtro de documento para permitir apenas dígitos
        ((AbstractDocument) numTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);

        gbc.gridx = 0;
        gbc.gridy = 3;
        cadastroInputPanel.add(cepLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(cepTxt, gbc);

        gbc.gridx = 2;
        cadastroInputPanel.add(enderecoLabel, gbc);

        gbc.gridx = 3;
        cadastroInputPanel.add(enderecoTxt, gbc);

        gbc.gridx = 4;
        cadastroInputPanel.add(numLabel, gbc);

        gbc.gridx = 5;
        cadastroInputPanel.add(numTxt, gbc);

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
        cadastroInputPanel.add(bairroLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(bairroTxt, gbc);

        gbc.gridx = 2;
        cadastroInputPanel.add(cidadeLabel, gbc);

        gbc.gridx = 3;
        cadastroInputPanel.add(cidadeTxt, gbc);

        gbc.gridx = 4;
        cadastroInputPanel.add(complementoLabel, gbc);

        gbc.gridx = 5;
        cadastroInputPanel.add(complementoTxt, gbc);

        gbc.gridx = 6;
        cadastroInputPanel.add(ufLabel, gbc);

        gbc.gridx = 7;
        cadastroInputPanel.add(ufComboBox, gbc);

        // Sexta linha: RG, CPF
        rgLabel = new JLabel("RG:");
        rgTxt = new JTextField(10);
        rgTxt.setMinimumSize(new Dimension(180, rgTxt.getPreferredSize().height));
        rgTxt.setPreferredSize(new Dimension(250, rgTxt.getPreferredSize().height));

        // Configurar o filtro de documento para permitir apenas dígitos
        ((AbstractDocument) rgTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);

        cpfLabel = new JLabel("CPF:");
        try {
            cpfTxt = new JFormattedTextField(new javax.swing.text.MaskFormatter("###.###.###-##"));
            cpfTxt.setColumns(11);
            cpfTxt.setMinimumSize(new Dimension(180, cpfTxt.getPreferredSize().height));
            cpfTxt.setPreferredSize(new Dimension(250, cpfTxt.getPreferredSize().height));

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) cpfTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 5;
        cadastroInputPanel.add(rgLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(rgTxt, gbc);

        gbc.gridx = 2;
        cadastroInputPanel.add(cpfLabel, gbc);

        gbc.gridx = 3;
        cadastroInputPanel.add(cpfTxt, gbc);

        cadastroPanel.add(cadastroInputPanel, BorderLayout.CENTER);

        // Borda SUL: Botão Salvar
        saveButton = new JButton("Cadastrar");
        saveButton.setPreferredSize(new Dimension(150, saveButton.getPreferredSize().height));
        saveButton.setMaximumSize(new Dimension(400, saveButton.getPreferredSize().height));

        saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        saveButtonPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        saveButtonPanel.add(saveButton);

        cadastroPanel.add(saveButtonPanel, BorderLayout.SOUTH);

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
        tabbedPane.addTab("Cadastro", cadastroPanel);

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

        tabbedPane.addTab("Consulta", consultaPanel);

        main.add(tabbedPane, BorderLayout.CENTER);

        main.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                final int windowHeight = main.getHeight();
                final float saveButtonMarginCoeficient = windowHeight > 800 ? .1F : .05F;
                final int saveButtonBottomMargin = (int) (windowHeight*saveButtonMarginCoeficient);

                saveButtonPanel.setBorder(new EmptyBorder(0, 0, saveButtonBottomMargin, 0));

                main.revalidate();
            }
        });
    }

    /**
     * Creates and shows a new visible JFrame with this component as content.
     */
    public void createFrameAndShowClientForm(String frameTitle) {
        this.frame = new JFrame(frameTitle);

        this.frame.setContentPane(this.main);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
}

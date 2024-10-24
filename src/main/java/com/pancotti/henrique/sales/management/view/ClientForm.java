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
import java.util.*;
import java.util.List;

import static com.pancotti.henrique.sales.management.util.UIUtil.getErrorDialogJTextAreaContent;

public class ClientForm {
    private static final int V_GAP_MAX_HEIGHT = 25;

    private static final ClientDAO clientDao = new ClientDAO();

    private JFrame frame;
    private final JPanel main;
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
    private JFormattedTextField rgTxt;
    private JLabel rgLabel;
    private JFormattedTextField cpfTxt;
    private JLabel cpfLabel;
    private JPanel saveButtonPanel;
    private JButton saveButton;

    private JTextField consultaNomeTxt;
    private JButton consultaPesquisarButton;
    private JTable consultaTable;
    private DefaultTableModel consultaTableModel;

    private static final class LimitedLengthDocumentFilter extends DocumentFilter {
        private final int maxLength;

        public LimitedLengthDocumentFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (fb.getDocument().getLength() + string.length() <= maxLength) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (fb.getDocument().getLength() + text.length() - length <= maxLength) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

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
            final Map<String, String> fieldTextValues = new java.util.HashMap<>();

            fieldTextValues.put(nomeTxt.getName(), getTextIfValid(nomeTxt, false));
            fieldTextValues.put(rgTxt.getName(), getTextIfValid(rgTxt, false));
            fieldTextValues.put(cpfTxt.getName(), getTextIfValid(cpfTxt, false));
            fieldTextValues.put(emailTxt.getName(), getTextIfValid(emailTxt, false));
            fieldTextValues.put(telefoneFixoTxt.getName(), getTextIfValid(telefoneFixoTxt, false));
            fieldTextValues.put(celularTxt.getName(), getTextIfValid(celularTxt, false));
            fieldTextValues.put(cepTxt.getName(), getTextIfValid(cepTxt, false));
            fieldTextValues.put(enderecoTxt.getName(), getTextIfValid(enderecoTxt, false));
            fieldTextValues.put(numTxt.getName(), getTextIfValid(numTxt, false));
            fieldTextValues.put(complementoTxt.getName(), getTextIfValid(complementoTxt, false));
            fieldTextValues.put(bairroTxt.getName(), getTextIfValid(bairroTxt, false));
            fieldTextValues.put(cidadeTxt.getName(), getTextIfValid(cidadeTxt, false));

            StringBuilder stringBuilder = new StringBuilder();

            fieldTextValues.forEach((key, value) -> {
                if (stringBuilder.isEmpty() && value == null)
                    stringBuilder.append("O(s) seguinte(s) campo(s) não foram adequadamente preenchidos:\n\n");

                if (value == null)
                    stringBuilder.append(key).append(", ");
            });

            if (!stringBuilder.isEmpty()) {
                JTextArea errorTxtArea = getErrorDialogJTextAreaContent(
                    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()).toString()
                );

                JOptionPane.showMessageDialog(
                    null,
                    errorTxtArea,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            try {
                ClientModel client = new ClientModel(
                    fieldTextValues.get(nomeTxt.getName()),
                    fieldTextValues.get(rgTxt.getName()),
                    fieldTextValues.get(cpfTxt.getName()),
                    fieldTextValues.get(emailTxt.getName()),
                    fieldTextValues.get(telefoneFixoTxt.getName()),
                    fieldTextValues.get(celularTxt.getName()),
                    fieldTextValues.get(cepTxt.getName()),
                    fieldTextValues.get(enderecoTxt.getName()),
                    Integer.parseInt(fieldTextValues.get(numTxt.getName())),
                    fieldTextValues.get(complementoTxt.getName()),
                    fieldTextValues.get(bairroTxt.getName()),
                    fieldTextValues.get(cidadeTxt.getName()),
                    Objects.requireNonNull(ufComboBox.getSelectedItem()).toString()
                );

                clientDao.create(client);

//                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
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

    private class FormSearchButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cpfTextValue = getTextIfValid(cpfTxt, false);

            if (cpfTextValue == null || cpfTextValue.isBlank()) return;

            ClientModel client = clientDao.findByCpf(cpfTextValue);

            if(client == null)
                return;

            codigoTxt.setText(String.valueOf(client.getId()));
            nomeTxt.setText(client.getNome());
            emailTxt.setText(client.getEmail());
            celularTxt.setValue(client.getCelular());
            telefoneFixoTxt.setValue(client.getTelefone());
            cepTxt.setValue(client.getCep());
            enderecoTxt.setText(client.getEndereco());
            numTxt.setText(String.valueOf(client.getNumero()));
            bairroTxt.setText(client.getBairro());
            cidadeTxt.setText(client.getCidade());
            complementoTxt.setText(client.getComplemento());
            ufComboBox.setSelectedItem(client.getEstado());
            rgTxt.setValue(client.getRg());
        }
    }

    private class TableSearchButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            consultaTableModel.setRowCount(0);

            String consultaNomeTextValue = consultaNomeTxt.getText();

            List<ClientModel> clients = (consultaNomeTextValue == null || consultaNomeTextValue.isBlank())
                ? clientDao.findAll()
                : clientDao.findByNome(consultaNomeTextValue);

            if(clients.isEmpty())
                return;

            clients.forEach(client -> consultaTableModel.addRow(client.toVector()));
        }
    }

    private static String getTextIfValid(JTextComponent textComponent, boolean acceptEmpty) {
        if (textComponent instanceof JFormattedTextField) {
            try {
                // Tenta obter o valor formatado, lançará ParseException se estiver incompleto
                ((JFormattedTextField)textComponent).commitEdit();
                return ((JFormattedTextField)textComponent).getValue().toString();
            } catch (ParseException e) {
                return null;
            }
        }

        // JTextField
        String textContent = textComponent.getText();
        return (acceptEmpty || !textContent.trim().isEmpty()) ? textContent : null;
    }


    public void setMainFrameMinimumSize(Dimension dimension) {
        if (dimension.width < 1200) dimension.width = 1200;

        this.frame.setMinimumSize(dimension);
    }

    public void setMainPanelMaximumSize(Dimension dimension) {
        this.main.setMaximumSize(dimension);
    }

    private static void setFontForChildComponents(Container parentContainer, Font font) {
        parentContainer.setFont(font);

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

    public void setTableFont(Font font) {
        setFontForChildComponents(consultaTable, font);
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
        codigoTxt.setName("Código");
        codigoTxt.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        cadastroInputPanel.add(codigoLabel, gbc);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        cadastroInputPanel.add(codigoTxt, gbc);

        // Segunda linha: CPF e Pesquisar
        cpfLabel = new JLabel("CPF:");
        try {
            MaskFormatter cpfTxtMaskFormatter = new MaskFormatter("###.###.###-##");
            cpfTxtMaskFormatter.setValueContainsLiteralCharacters(false);
            cpfTxt = new JFormattedTextField(cpfTxtMaskFormatter);
            cpfTxt.setColumns(11);
            cpfTxt.setMinimumSize(new Dimension(180, cpfTxt.getPreferredSize().height));
            cpfTxt.setPreferredSize(new Dimension(250, cpfTxt.getPreferredSize().height));
            cpfTxt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
            cpfTxt.setName("CPF");

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) cpfTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);

            // Configurar o filtro de documento para limitar comprimento do texto
            ((AbstractDocument) cpfTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pesquisarButton = new JButton("Pesquisar");
        pesquisarButton.addActionListener(new FormSearchButtonActionListener());

        gbc.gridy = 1;
        gbc.gridx = 0;
        cadastroInputPanel.add(cpfLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(cpfTxt, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        cadastroInputPanel.add(pesquisarButton, gbc);

        // Terceira linha: Nome
        nomeLabel = new JLabel("Nome:");
        nomeTxt = new JTextField(20);
        nomeTxt.setMinimumSize(new Dimension(180, nomeTxt.getPreferredSize().height));
        nomeTxt.setPreferredSize(new Dimension(250, nomeTxt.getPreferredSize().height));
        nomeTxt.setName("Nome");

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) nomeTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(150));


        gbc.gridy = 2;
        gbc.gridx = 0;
        cadastroInputPanel.add(nomeLabel, gbc);

        gbc.gridx = 1;
//        gbc.weightx = 1.0;
        cadastroInputPanel.add(nomeTxt, gbc);

        // Quarta linha: Email, Celular e Telefone Fixo
        emailLabel = new JLabel("E-mail:");
        emailTxt = new JTextField(20);
        emailTxt.setMinimumSize(new Dimension(180, emailTxt.getPreferredSize().height));
        emailTxt.setPreferredSize(new Dimension(250, emailTxt.getPreferredSize().height));
        emailTxt.setName("E-mail");

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) emailTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(150));

        celularLabel = new JLabel("Celular:");
        telefoneFixoLabel = new JLabel("Telefone Fixo:");

        try {
            MaskFormatter celularTxtMaskFormatter = new MaskFormatter("(##) #####-####");
            celularTxtMaskFormatter.setValueContainsLiteralCharacters(false);
            celularTxt = new JFormattedTextField(celularTxtMaskFormatter);
            celularTxt.setColumns(10);
            celularTxt.setMinimumSize(new Dimension(180, celularTxt.getPreferredSize().height));
            celularTxt.setPreferredSize(new Dimension(250, celularTxt.getPreferredSize().height));
            celularTxt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
            celularTxt.setName("Celular");

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) celularTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
            // Configurar o filtro de documento para limitar comprimento do texto
            ((AbstractDocument) celularTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(12));

            MaskFormatter telefoneFixoTxtMaskFormatter = new MaskFormatter("(##) ####-####");
            telefoneFixoTxtMaskFormatter.setValueContainsLiteralCharacters(false);
            telefoneFixoTxt = new JFormattedTextField(telefoneFixoTxtMaskFormatter);
            telefoneFixoTxt.setColumns(9);
            telefoneFixoTxt.setMinimumSize(new Dimension(180, telefoneFixoTxt.getPreferredSize().height));
            telefoneFixoTxt.setPreferredSize(new Dimension(250, telefoneFixoTxt.getPreferredSize().height));
            telefoneFixoTxt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
            telefoneFixoTxt.setName("Telefone Fixo");

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) telefoneFixoTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
            // Configurar o filtro de documento para limitar comprimento do texto
            ((AbstractDocument) telefoneFixoTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 3;
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

        // Quinta linha: CEP, Endereço e Número
        cepLabel = new JLabel("CEP:");
        try {
            MaskFormatter cepTxtMaskFormatter = new MaskFormatter("#####-###");
            cepTxtMaskFormatter.setValueContainsLiteralCharacters(false);
            cepTxt = new JFormattedTextField(cepTxtMaskFormatter);
            cepTxt.setColumns(8);
            cepTxt.setMinimumSize(new Dimension(180, cepTxt.getPreferredSize().height));
            cepTxt.setPreferredSize(new Dimension(250, cepTxt.getPreferredSize().height));
            cepTxt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
            cepTxt.setName("CEP");

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) cepTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        enderecoLabel = new JLabel("Endereço:");
        enderecoTxt = new JTextField(20);
        enderecoTxt.setMinimumSize(new Dimension(180, enderecoTxt.getPreferredSize().height));
        enderecoTxt.setPreferredSize(new Dimension(250, enderecoTxt.getPreferredSize().height));
        enderecoTxt.setName("Endereço");


        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) enderecoTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(255));

        numLabel = new JLabel("Número:");
        numTxt = new JTextField(7);
        numTxt.setMinimumSize(new Dimension(80, numTxt.getPreferredSize().height));
        numTxt.setPreferredSize(new Dimension(130, numTxt.getPreferredSize().height));
        numTxt.setName("Número");

        // Configurar o filtro de documento para permitir apenas dígitos
        ((AbstractDocument) numTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);
        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) numTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(10));

        gbc.gridx = 0;
        gbc.gridy = 4;
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

        // Sexta linha: Bairro, Cidade, Complemento e UF
        bairroLabel = new JLabel("Bairro:");
        bairroTxt = new JTextField(15);
        bairroTxt.setMinimumSize(new Dimension(180, bairroTxt.getPreferredSize().height));
        bairroTxt.setPreferredSize(new Dimension(250, bairroTxt.getPreferredSize().height));
        bairroTxt.setName("Bairro");

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) bairroTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(100));

        cidadeLabel = new JLabel("Cidade:");
        cidadeTxt = new JTextField(15);
        cidadeTxt.setMinimumSize(new Dimension(180, cidadeTxt.getPreferredSize().height));
        cidadeTxt.setPreferredSize(new Dimension(250, cidadeTxt.getPreferredSize().height));
        cidadeTxt.setName("Cidade");

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) cidadeTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(100));

        complementoLabel = new JLabel("Complemento:");
        complementoTxt = new JTextField(15);
        complementoTxt.setMinimumSize(new Dimension(180, complementoTxt.getPreferredSize().height));
        complementoTxt.setPreferredSize(new Dimension(250, complementoTxt.getPreferredSize().height));
        complementoTxt.setName("Complemento");

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) complementoTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(200));

        ufLabel = new JLabel("UF:");
        ufComboBox = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 5;
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

        // Sétima linha: RG, CPF
        rgLabel = new JLabel("RG:");
        try {
            MaskFormatter rgMaskFormatter = new MaskFormatter("UU-##.###.###*");
            rgMaskFormatter.setValueContainsLiteralCharacters(false);

            rgTxt = new JFormattedTextField(rgMaskFormatter);
            rgTxt.setMinimumSize(new Dimension(180, rgTxt.getPreferredSize().height));
            rgTxt.setPreferredSize(new Dimension(250, rgTxt.getPreferredSize().height));
            rgTxt.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
            rgTxt.setName("RG");

            // Configurar o filtro de documento para permitir apenas dígitos
            ((AbstractDocument) rgTxt.getDocument()).setDocumentFilter(digitsOnlyDocumentFilter);

            // Configurar o filtro de documento para limitar comprimento do texto
            ((AbstractDocument) rgTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(30));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        gbc.gridx = 0;
        gbc.gridy = 6;
        cadastroInputPanel.add(rgLabel, gbc);

        gbc.gridx = 1;
        cadastroInputPanel.add(rgTxt, gbc);

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

        FlowLayout consultaTopPanelFlowLayout = new FlowLayout();
        consultaTopPanelFlowLayout.setHgap(20);
        consultaTopPanelFlowLayout.setAlignment(FlowLayout.LEADING);

        JPanel consultaTopPanel = new JPanel(consultaTopPanelFlowLayout);
        consultaTopPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel consultaNomeLabel = new JLabel("Nome:");
        consultaNomeLabel.setLabelFor(consultaNomeTxt);

        consultaNomeTxt = new JTextField(20);

        // Configurar o filtro de documento para limitar comprimento do texto
        ((AbstractDocument) consultaNomeTxt.getDocument()).setDocumentFilter(new LimitedLengthDocumentFilter(150));

        consultaPesquisarButton = new JButton("Pesquisar");
        consultaPesquisarButton.setPreferredSize(new Dimension(
                150, consultaNomeTxt.getPreferredSize().height + 5
            )
        );

        consultaPesquisarButton.addActionListener(new TableSearchButtonActionListener());

        consultaTopPanel.add(consultaNomeLabel);
        consultaTopPanel.add(consultaNomeTxt);
        consultaTopPanel.add(consultaPesquisarButton);

        // Tabela de consulta
        String[] colunas =
            {"Código", "Nome", "RG", "E-mail", "Tel.", "Cel.", "CEP", "Endereço", "N°", "Compl.", "Bairro", "Cidade", "UF"};

        consultaTableModel = new DefaultTableModel(null, colunas);

        consultaTable = new JTable(consultaTableModel);
        consultaTable.setShowHorizontalLines(true);

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

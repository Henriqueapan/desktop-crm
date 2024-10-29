import javax.swing.*;

import dev.pancotti.henrique.crm.view.ClientForm;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));

        ClientForm modernForm = new ClientForm();
        modernForm.createFrameAndShowClientForm("Cadastro de Clientes");

        modernForm.setMainFrameMinimumSize(new Dimension(1200, 500));
        modernForm.setMainPanelMaximumSize(new Dimension(10, 10));

        modernForm.setFormFont(new Font("Arial", Font.PLAIN, 20));
        modernForm.setTableFont(new Font("Arial", Font.PLAIN, 12));

    }
}

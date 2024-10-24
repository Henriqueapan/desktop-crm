package com.pancotti.henrique.sales.management.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UIUtil {
    private static final int COLUMN_WIDTH = 25;

    public static JTextArea getErrorDialogJTextAreaContent(Exception exc, String contextMessage) {
        final String errorMessage =
            contextMessage + "\n\n" + exc.getClass().getSimpleName() + "\n\n" + exc.getLocalizedMessage();

        return getErrorDialogJTextAreaContent(errorMessage);
    }

    public static JTextArea getErrorDialogJTextAreaContent(String errorMessage) {

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
}

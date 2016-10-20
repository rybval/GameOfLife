package app.GUI.forms;

import app.logic.Game;

import javax.swing.*;
import java.awt.event.*;

public class GameParametersDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinner_height;
    private JSpinner spinner_width;

    private Game game;

    public GameParametersDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // Call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        game = new Game(((SpinnerNumberModel) spinner_width.getModel()).getNumber().intValue(),
                ((SpinnerNumberModel) spinner_height.getModel()).getNumber().intValue());
        dispose();
    }

    private void onCancel() {
        dispose();
        System.exit(0);
    }

    public Game getGame() {
        return game;
    }

    private void createUIComponents() {
        SpinnerNumberModel model_width = new SpinnerNumberModel(10, 10, 100, 1);
        SpinnerNumberModel model_height = new SpinnerNumberModel(10, 10, 100, 1);
        spinner_height = new JSpinner(model_height);
        spinner_width = new JSpinner(model_width);
    }
}

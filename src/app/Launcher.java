package app;

import app.GUI.GameParametersDialog;
import app.GUI.MainWindow;
import app.logic.Game;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        // Set system-like look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                UnsupportedLookAndFeelException |
                IllegalAccessException |
                InstantiationException e) {
            e.printStackTrace();
        }

        GameParametersDialog dialog = new GameParametersDialog();
        dialog.pack();
        dialog.setVisible(true);

        Game game = dialog.getGame();

        MainWindow main_window = new MainWindow(game);
    }
}

package app.GUI;

import app.logic.Game;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {
    private final Game game;
    private int cell_border;

    Canvas(Game game) {
        this.game = game;
    }

    void updatePreferredSize() {
        int cell_width = this.getParent().getWidth() / game.getFieldWidth();
        int cell_height = this.getParent().getHeight() / game.getFieldHeight();

        cell_border = (cell_width > cell_height) ? (cell_height) : (cell_width);

        this.setPreferredSize(new Dimension(cell_border * game.getFieldWidth(), cell_border * game.getFieldHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(240,240,240));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int x = 0; x < game.getFieldWidth(); x++) {
            for (int y = 0; y < game.getFieldHeight(); y++) {
                if (game.isUnitAlive(x, y)) {
                    g.setColor(new Color(33, 176, 77));
                    g.fillRect(x * cell_border, y * cell_border, cell_border - 2, cell_border - 2);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * cell_border, y * cell_border, cell_border - 2, cell_border - 2);
                }
                g.setColor(Color.BLACK);
                g.drawRect(x * cell_border, y * cell_border, cell_border - 2, cell_border - 2);
            }
        }
    }
}

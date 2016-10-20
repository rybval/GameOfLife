package app.GUI.forms;

import app.GUI.components.Canvas;
import app.GUI.components.PlayPauseButton;
import app.logic.Game;

import javax.swing.*;
import java.awt.event.*;

import static app.GUI.components.PlayPauseButton.ButtonState.BUTTON_PAUSE;
import static app.GUI.components.PlayPauseButton.ButtonState.BUTTON_PLAY;

public class MainWindow extends JFrame {
    private final Game game;
    private JPanel content_panel;
    private JButton button_stop;
    private Canvas canvas;
    private JButton button_start;
    private JSlider slider_speed;
    private JPanel canvas_panel;

    private final Timer update_timer;
    private boolean game_started;

    public MainWindow(Game game) {
        this.game = game;
        setContentPane(content_panel);

        update_timer = new Timer(slider_speed.getModel().getMaximum() - slider_speed.getModel().getValue(),
                e -> UpdateCanvas());

        createListeners();

        pack();
    }

    private void UpdateCanvas() {
        if (!game.doStep()) {
            update_timer.stop();
            game_started = false;
            ((PlayPauseButton) button_start).setState(BUTTON_PLAY);
        }
        canvas.repaint();
    }

    private void onQuit() {
        System.exit(0);
    }

    private void createListeners() {
        // call onQuit() when window closing
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onQuit();
            }
        });

        // listeners for buttons
        button_stop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                game.reset();
                UpdateCanvas();
            }
        });

        button_start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!game_started) {
                    update_timer.start();
                    game_started = true;
                    ((PlayPauseButton) button_start).setState(BUTTON_PAUSE);
                } else {
                    update_timer.stop();
                    game_started = false;
                    ((PlayPauseButton) button_start).setState(BUTTON_PLAY);
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    super.mouseClicked(e);
                    int cell_width = canvas.getWidth() / game.getFieldWidth();
                    int cell_height = canvas.getHeight() / game.getFieldHeight();
                    int cell_border = (cell_width > cell_height) ? (cell_height) : (cell_width);

                    int x = e.getX() / cell_border;
                    int y = e.getY() / cell_border;
                    if (!game.isUnitAlive(x, y))
                        game.bornUnit(x, y);
                    else
                        game.killUnit(x, y);
                    canvas.repaint();
                }
            }
        });

        slider_speed.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            update_timer.setDelay(source.getMaximum() - source.getValue());
        });

        canvas_panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                canvas.updatePreferredSize();

                SwingUtilities.updateComponentTreeUI(e.getComponent());
            }
        });
    }

    private void createUIComponents() {
        canvas = new Canvas(game);
        slider_speed = new JSlider(JSlider.HORIZONTAL, 100, 1000, 750);

        button_start = new PlayPauseButton("res/play.png", "res/pause.png");
        button_stop = new JButton(new ImageIcon(ClassLoader.getSystemResource("res/stop.png")));
    }
}

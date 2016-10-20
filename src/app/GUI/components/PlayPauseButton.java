package app.GUI.components;

import javax.swing.*;

/**
 * Created by Pyotr Shurgalin on 19.10.2016.
 **/
public class PlayPauseButton extends JButton {
    private Icon play;
    private Icon pause;

    public enum ButtonState {
        BUTTON_PLAY, BUTTON_PAUSE
    }

    public PlayPauseButton(String play, String pause) {
        this.play = new ImageIcon(ClassLoader.getSystemResource(play));
        this.pause = new ImageIcon(ClassLoader.getSystemResource(pause));
        this.setIcon(this.play);
    }

    public void setState(ButtonState state) {
        switch (state) {
            case BUTTON_PLAY:
                this.setIcon(play);
                break;
            case  BUTTON_PAUSE:
                this.setIcon(pause);
                break;
        }
    }
}

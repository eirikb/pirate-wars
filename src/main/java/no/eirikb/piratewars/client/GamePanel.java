/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client;

import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.List;
import no.eirikb.piratewars.client.image.ImageHandler;
import no.eirikb.piratewars.client.shared.Game;
import no.eirikb.piratewars.client.shared.Ship;
import no.eirikb.piratewars.client.shared.User;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class GamePanel extends VerticalPanel {

    private Game game;
    private MapPanel mapPanel;
    private ImageHandler imageHandler;
    private Piratewars piratewars;

    public GamePanel(Piratewars piratewars, Game game, ImageHandler imageHandler,
            int width, int height, int frameWidth, int frameHeigth) {
        this.piratewars = piratewars;
        this.game = game;
        this.imageHandler = imageHandler;
        mapPanel = new MapPanel(this, imageHandler, game.getMap(), width, height,
                frameWidth, frameHeigth);
        add(mapPanel);
        mapPanel.drawMap();
    }

    public List<User> getUsers() {
        return game.getUsers();
    }

    public boolean myTurn() {
        return piratewars.getMe().getMoves() > 0;
    }

    public void move() {
        piratewars.getMe().decMoves();

        // HACK!
        if (piratewars.getMe().getMoves() == 0) {
            resetMoves();
        } else {
            boolean allDone = true;
            for (Ship s : piratewars.getMe().getShips()) {
                if (s.isMovable()) {
                    allDone = false;
                    break;
                }
            }
            if (allDone) {
                resetMoves();
            }
        }
    }

    public void resetMoves() {
        piratewars.getMe().resetMoves();
        for (Ship s : piratewars.getMe().getShips()) {
            s.setMovable(true);
        }
    }

    public boolean isMe(User user) {
        return piratewars.getMe().equals(user);
    }

    public User getMe() {
        return piratewars.getMe();
    }
}
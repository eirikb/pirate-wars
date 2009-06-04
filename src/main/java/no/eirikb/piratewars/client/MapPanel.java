/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;
import no.eirikb.piratewars.client.animation.AnimateListener;
import no.eirikb.piratewars.client.animation.AnimateMove;
import no.eirikb.piratewars.client.image.ImageHandler;
import no.eirikb.piratewars.client.logic.LandType;
import no.eirikb.piratewars.client.logic.MoveController;
import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.FrameType;
import no.eirikb.piratewars.client.shared.Map;
import no.eirikb.piratewars.client.shared.Ship;
import no.eirikb.piratewars.client.shared.User;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class MapPanel extends HorizontalPanel {

    private Canvas canvas;
    private Map map;
    private Frame lastHover;
    private ImageHandler imageHandler;
    private GamePanel gamePanel;
    private MoveController moveController;
    private AnimateMove animateMove;
    private final int WATERDELAY = 700;

    public MapPanel(GamePanel gamePanel2, ImageHandler imageHandler2,
            Map map2, int width, int height, int frameWidth, int frameHeigth) {
        this.gamePanel = gamePanel2;
        this.imageHandler = imageHandler2;
        this.map = map2;
        canvas = new Canvas(this, width, height, frameWidth, frameHeigth);
        add(canvas);

        Timer waterTimer = new Timer() {

            @Override
            public void run() {
                drawWater();
            }
        };

        waterTimer.scheduleRepeating(WATERDELAY);

        canvas.setListener(new MouseListener() {

            public void onMouseDown(Widget sender, int x, int y) {
                if (gamePanel.myTurn() && animateMove == null) {
                    if (x / canvas.getFrameWidth() % 2 != 0) {
                        y -= (canvas.getFrameHeight() / 2);
                    }
                    Frame f = getFrame(x, y);
                    if (moveController == null && f.getShip() != null && f.getShip().isMovable() &&
                            gamePanel.isMe(f.getShip().getUser())) {
                        moveController = new MoveController(map.getFrames());
                        moveController.addFrameNodes(f, f.getShip().getSteps());
                        drawWater();
                    } else if (moveController != null) {
                        final FrameNode node = moveController.getFrameNode(f);
                        if (node != null && node.getFrame().getShip() == null) {
                            final Ship ship = moveController.getStart().getShip();
                            moveController = null;
                            ship.setFrame(null);

                            final Frame end = node.getFrame();

                            animateMove = new AnimateMove(canvas, imageHandler, node, ship);
                            drawWater();

                            animateMove.setAnimateListener(new AnimateListener() {

                                public void done() {
                                    ship.setFrame(end);
                                    ship.setMovable(false);
                                    gamePanel.move();
                                    animateMove.stop();
                                    animateMove = null;
                                    drawWater();
                                }
                            });
                            animateMove.start();
                        } else {
                            moveController = null;
                            drawWater();
                        }
                    }
                }
            }

            public void onMouseEnter(Widget sender) {
            }

            public void onMouseLeave(Widget sender) {
            }

            public void onMouseMove(Widget sender, int x, int y) {
                if (x / canvas.getFrameWidth() % 2 != 0) {
                    y -= (canvas.getFrameHeight() / 2);
                }
                Frame f = getFrame(x, y);
                if (lastHover != null && !f.equals(lastHover)) {
                    canvas.drawImage(imageHandler.getWater(), lastHover, false);
                    if (lastHover.getShip() != null) {
                        hover(lastHover);
                    }
                    if (moveController != null) {
                        FrameNode node = moveController.getFrameNode(lastHover);
                        if (node != null) {
                            if (lastHover.getShip() == null) {
                                hover(lastHover, 1);
                            }
                        }
                    }
                    lastHover = null;
                }
                if (f.getType() == FrameType.WATER) {
                    hover(f);
                    lastHover = f;
                }
            }

            public void onMouseUp(Widget sender, int x, int y) {
            }
        });
    }

    private void drawWater() {
        imageHandler.animateWater();
        List<Frame> frames = map.getFrames(FrameType.WATER);
        for (Frame f : frames) {
            canvas.drawImage(imageHandler.getWater(), f, false);
        }
        if (lastHover != null) {
            hover(lastHover);
        }
        if (moveController != null) {
            drawMoveHover();
        }
        if (animateMove != null) {
            animateMove.drawShip();
        }
        drawShips();
    }

    private void drawMoveHover() {
        if (moveController != null) {
            for (FrameNode frameNode : moveController.getFrameNodes()) {
                if (frameNode.getFrame().getShip() == null && !frameNode.getFrame().equals(lastHover)) {
                    hover(frameNode.getFrame(), 1);
                }
            }
        }
    }

    public void drawMap() {
        Frame[][] frames = map.getFrames();
        for (int x = 0; x < frames.length; x++) {
            for (int y = 0; y < frames[0].length; y++) {
                Frame f = frames[x][y];
                switch (f.getType()) {
                    case WATER:
                        canvas.drawImage(imageHandler.getWater(), f, false);
                        break;
                    case LAND:
                        drawLand(f);
                        break;
                }
            }
        }
        drawShips();
    }

    private void drawLand(Frame frame) {
        LandType lt = new LandType(map.getFrames(), frame);
        lt.parse();
        canvas.drawland(imageHandler.getLand(lt.getType(), 0), frame, lt.getRotate());
        if (frame.getTown() != null) {
            canvas.drawImage(imageHandler.getTown(0, 0), frame, true);
        }
    }

    private void drawShips() {
        for (User u : gamePanel.getUsers()) {
            for (Ship s : u.getShips()) {
                if (s.getFrame() != null) {
                    hover(s.getFrame());
                }
            }
        }
    }

    private void hover(Frame frame, int hover) {
        canvas.drawImage(imageHandler.getWater(), frame, false);
        canvas.drawImage(imageHandler.getHover(gamePanel.getMe().getColorId(), hover), frame, false);
        if (frame.getShip() != null) {
            canvas.drawImage(imageHandler.getShip(frame.getShip().getImageType(), 0), frame, true);
        }
    }

    public void hover(Frame frame) {
        int hover = 0;
        if (frame.getShip() != null && gamePanel.myTurn() &&
                gamePanel.isMe(frame.getShip().getUser()) &&
                frame.getShip().isMovable() && animateMove == null &&
                moveController == null) {
            hover = 1;
        }
        hover(frame, hover);
    }

    private Frame getFrame(int x, int y) {
        return map.getFrames()[x / canvas.getFrameWidth()][y / canvas.getFrameHeight()];
    }

    public ImageHandler getImageHandler() {
        return imageHandler;
    }

    public Map getMap() {
        return map;
    }
}
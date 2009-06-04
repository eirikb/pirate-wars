/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client.animation;

import com.google.gwt.user.client.Timer;
import java.util.ArrayList;
import java.util.List;
import no.eirikb.piratewars.client.Canvas;
import no.eirikb.piratewars.client.FrameNode;
import no.eirikb.piratewars.client.image.ImageHandler;
import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.Ship;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class AnimateMove {

    private Canvas canvas;
    private ImageHandler imageHandler;
    private List<Frame> frames;
    private Timer timer;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private AnimateListener animateListener;
    private int shipOffsetX;
    private int shipOffsetY;
    private Ship ship;
    private final int TIMER = 40;
    private final int SPEED = 4;

    public AnimateMove(final Canvas canvas, final ImageHandler imageHandler, FrameNode toNode, Ship ship2) {
        this.canvas = canvas;
        this.imageHandler = imageHandler;
        this.ship = ship2;
        List<Frame> frames2 = new ArrayList<Frame>();
        frames2.add(toNode.getFrame());
        while ((toNode = toNode.getParent()) != null) {
            frames2.add(toNode.getFrame());
        }
        frames = new ArrayList<Frame>();
        for (int i = frames2.size() - 1; i >= 0; i--) {
            frames.add(frames2.get(i));
        }
        x1 = x2 = y1 = y2 = -1;

        shipOffsetX = (canvas.getFrameWidth() - imageHandler.getShip(ship.getImageType(), 0).getWidth()) / 2;
        shipOffsetY = (canvas.getFrameHeight() - imageHandler.getShip(ship.getImageType(), 0).getHeight()) / 2;
        timer = new Timer() {

            @Override
            public void run() {
                if (x1 > x2) {
                    x1 -= SPEED;
                    if (x1 < x2) {
                        x1 = x2;
                    }
                } else if (x1 < x2) {
                    x1 += SPEED;
                    if (x1 > x2) {
                        x1 = x2;
                    }
                }
                if (y1 > y2) {
                    y1 -= SPEED;
                    if (y1 < y2) {
                        y1 = y2;
                    }
                } else if (y1 < y2) {
                    y1 += SPEED;
                    if (y1 > y2) {
                        y1 = y2;
                    }
                }

                if (x1 >= 0 && y1 >= 0) {
                    ship.animate();
                    canvas.drawShip(imageHandler.getShip(ship.getImageType(), ship.getAnimation()), x1 + shipOffsetX, y1 + shipOffsetY);
                }
                if (x1 == x2 && y1 == y2) {
                    if (x1 >= 0 && y1 >= 0) {
                        frames.remove(0);
                    }

                    if (frames.size() > 1) {
                        Frame f1 = frames.get(0);
                        Frame f2 = frames.get(1);

                        x1 = f1.getX() * canvas.getFrameWidth();
                        y1 = f1.getY() * canvas.getFrameHeight();
                        x2 = f2.getX() * canvas.getFrameWidth();
                        y2 = f2.getY() * canvas.getFrameHeight();

                        y1 = f1.getX() % 2 == 0 ? y1 : y1 + (canvas.getFrameHeight() / 2);
                        y2 = f2.getX() % 2 == 0 ? y2 : y2 + (canvas.getFrameHeight() / 2);
                    } else {
                        timer.cancel();
                        if (animateListener != null) {
                            animateListener.done();
                        }
                    }
                }
            }
        };
    }

    public void start() {
        timer.scheduleRepeating(TIMER);
    }

    public void stop() {
        timer.cancel();
    }

    public void setAnimateListener(AnimateListener animateListener) {
        this.animateListener = animateListener;
    }

    public void drawShip() {
        canvas.drawShip(imageHandler.getShip(ship.getImageType(), ship.getAnimation()), x1 + shipOffsetX, y1 + shipOffsetY);
    }
}
/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client.shared;

import java.io.Serializable;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class Ship implements Serializable {

    private User user;
    private Frame frame;
    private ShipType type;
    private int animation;
    private int steps;
    public boolean movable;

    public Ship(User user, Frame frame, ShipType type, int steps) {
        this.user = user;
        this.frame = frame;
        this.type = type;
        this.steps = steps;
        user.addShip(this);
        frame.setShip(this);
        movable = true;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        if (this.frame != null) {
            this.frame.setShip(null);
        }
        if (frame != null) {
            frame.setShip(this);
        }
        this.frame = frame;
    }

    public ShipType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public int getImageType() {
        switch (type) {
            case BATTLESHIP:
                return 0;
            case SPEEDBOAT:
                return 1;
            case SUBMARINE:
                return 2;
        }
        return 0;
    }

    public int getAnimation() {
        return animation;
    }

    public void animate() {
        animation++;
        if (animation > 2) {
            animation = 1;
        }
    }

    public int getSteps() {
        return steps;
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.removeShip(this);
            if (user != null) {
                user.addShip(this);
            }
        }
        this.user = user;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }
}
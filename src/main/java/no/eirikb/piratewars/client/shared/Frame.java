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
public class Frame implements Serializable {

    private int x;
    private int y;
    private int strength;
    private Ship ship;
    private Town town;
    private FrameType type;

    public Frame() {
    }

    public Frame(int x, int y) {
        this.x = x;
        this.y = y;
        type = FrameType.WATER;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public FrameType getType() {
        return type;
    }

    public void setType(FrameType type) {
        this.type = type;
    }
}
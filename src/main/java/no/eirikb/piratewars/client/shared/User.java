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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class User implements Serializable {

    private String nick;
    private List<Ship> ships;
    private List<Town> towns;
    private Game game;
    private UserColor color;
    private int moves;
    private int totalMoves;

    public User() {
    }

    public User(String nick, int totalMoves) {
        this.nick = nick;
        this.totalMoves = totalMoves;
        moves = totalMoves;
        ships = new ArrayList<Ship>();
        towns = new ArrayList<Town>();
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addTown(Town town) {
        towns.add(town);
    }

    public void removeTown(Town town) {
        towns.remove(town);
    }

    public List<Town> getTowns() {
        return towns;
    }

    public String getNick() {
        return nick;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public UserColor getColor() {
        return color;
    }

    public void setColor(UserColor color) {
        this.color = color;
    }

    public int getColorId() {
        switch (color) {
            case RED:
                return 0;
            case BLUE:
                return 1;
            case GREEN:
                return 2;
            case YELLOW:
                return 3;
        }
        return 3;
    }

    public int getMoves() {
        return moves;
    }

    public void decMoves() {
        moves--;
    }

    public void resetMoves() {
        moves = totalMoves;
    }
}
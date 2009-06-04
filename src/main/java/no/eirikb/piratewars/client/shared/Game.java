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
public class Game implements Serializable {

    private String name;
    private List<User> users;
    private Map map;
    private User whosTurn;
    private int maxPlayers;

    public Game() {
    }

    public Game(String name, Map map, int maxPlayers) {
        this.name = name;
        this.map = map;
        this.maxPlayers = maxPlayers;
        users = new ArrayList<User>();
    }

    public Map getMap() {
        return map;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public User getWhosTurn() {
        return whosTurn;
    }

    public void setWhosTurn(User whosTurn) {
        this.whosTurn = whosTurn;
    }

    public Frame getCorrectFrame(Frame frame) {
        return map.getFrames()[frame.getX()][frame.getY()];
    }
}
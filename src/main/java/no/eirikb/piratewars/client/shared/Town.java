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
public class Town implements Serializable {

    private User user;
    private Frame frame;
    private boolean ready;
    private int size;

    public Town(Frame frame, int size) {
        this.frame = frame;
        this.size = size;
        frame.setTown(this);
    }

    public Frame getFrame() {
        return frame;
    }

    public boolean isReady() {
        return ready;
    }

    public int getSize() {
        return size;
    }

    public User getUser() {
        return user;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


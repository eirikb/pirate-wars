/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client;

import no.eirikb.piratewars.client.shared.Frame;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class FrameNode {

    private Frame frame;
    private int stepsHome;
    private FrameNode parent;

    public FrameNode(Frame frame, int stepsHome, FrameNode parent) {
        this.frame = frame;
        this.stepsHome = stepsHome;
        this.parent = parent;
    }

    public Frame getFrame() {
        return frame;
    }

    public FrameNode getParent() {
        return parent;
    }

    public int getStepsHome() {
        return stepsHome;
    }
}

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
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class Map implements Serializable {

    private Frame[][] frames;
    private java.util.Map<FrameType, List<Frame>> typeFrames;

    public Map() {
    }

    public Map(int x, int y) {
        frames = new Frame[x][y];
        typeFrames = new HashMap<FrameType, List<Frame>>();
    }

    public void setFrame(int x, int y, Frame frame) {
        frames[x][y] = frame;
        List<Frame> frameList = typeFrames.get(frame.getType());
        if (frameList == null) {
            frameList = new ArrayList<Frame>();
            typeFrames.put(frame.getType(), frameList);
        }
        frameList.add(frame);
    }

    public Frame[][] getFrames() {
        return frames;
    }

    public List<Frame> getFrames(FrameType type) {
        return typeFrames.get(type);
    }
}
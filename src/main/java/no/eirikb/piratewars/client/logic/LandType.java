/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client.logic;

import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.FrameType;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class LandType {

    private int type;
    private int rotate;
    private Frame[][] frames;
    private Frame frame;

    public LandType(Frame[][] frames, Frame frame) {
        this.frames = frames;
        this.frame = frame;
    }

    public void parse() {
        int[][] fs = FrameLogic.getFramesAround(frames, frame);
        rotate = 0;
        type = 0;
        for (int i = 0; i < fs.length; i++) {
            Frame f = frames[fs[i][0]][fs[i][1]];
            if (f.getType() == FrameType.WATER) {
                type++;
            }
        }

        //TODO fiks algoritme!
        type = 0;
    }

    public int getRotate() {
        return rotate;
    }

    public int getType() {
        return type;
    }
}
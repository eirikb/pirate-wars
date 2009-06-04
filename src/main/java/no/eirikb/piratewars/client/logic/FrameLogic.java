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

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class FrameLogic {

    public static int[][] getFramesAround(Frame[][] frames, Frame frame) {
        int[][] pos = new int[6][2];
        int i = 0;
        int starty = frame.getX() % 2 == 0 ? frame.getY() - 1 : frame.getY();
        for (int y = starty; y <= starty + 1; y++) {
            for (int x = frame.getX() - 1; x <= frame.getX() + 1; x += 2) {
                if (x >= 0 && x < frames.length &&
                        y >= 0 && y < frames[0].length) {
                    pos[i][0] = x;
                    pos[i++][1] = y;
                }
            }
        }
        for (int y = frame.getY() - 1; y <= frame.getY() + 1; y += 2) {
            if (frame.getX() >= 0 && frame.getX() < frames.length &&
                    y >= 0 && y < frames[0].length) {
                pos[i][0] = frame.getX();
                pos[i++][1] = y;
            }
        }
        return pos;
    }
}
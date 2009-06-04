/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client.logic;

import java.util.ArrayList;
import java.util.List;
import no.eirikb.piratewars.client.FrameNode;
import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.FrameType;
import no.eirikb.piratewars.client.shared.ShipType;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class MoveController {

    private Frame[][] frames;
    private List<FrameNode> frameNodes;
    private FrameNode[][] frameNodesHelp;
    private Frame start;

    public MoveController(Frame[][] frames) {
        this.frames = frames;
        frameNodes = new ArrayList<FrameNode>();
    }

    public List<FrameNode> getFrameNodes() {
        return frameNodes;
    }

    public Frame getStart() {
        return start;
    }

    public FrameNode getFrameNode(Frame frame) {
        return frameNodesHelp[frame.getX()][frame.getY()];
    }

    public void addFrameNodes(Frame start, int steps) {
        this.start = start;
        frameNodes.clear();
        frameNodesHelp = new FrameNode[frames.length][frames[0].length];
        FrameNode node = new FrameNode(start, 0, null);
        frameNodes.add(node);
        frameNodesHelp[start.getX()][start.getY()] = node;
        addFrameNodes(findNodes(start), steps);
    }

    private void addFrameNodes(List<Frame> nodes, int steps) {
        List<Frame> okFrames = new ArrayList<Frame>();
        for (Frame f : nodes) {
            FrameNode best = findBestNode(f);
            if (best != null) {
                FrameNode frameNode = new FrameNode(f, best.getStepsHome() + 1, best);
                frameNodes.add(frameNode);
                frameNodesHelp[f.getX()][f.getY()] = frameNode;
                okFrames.add(f);
            }
        }
        if (--steps > 0) {
            for (Frame f : okFrames) {
                addFrameNodes(findNodes(f), steps);
            }
        }
    }

    private FrameNode findBestNode(Frame frame) {
        if (frameNodesHelp[frame.getX()][frame.getY()] != null) {
            return null;
        }
        FrameNode best = null;
        int[][] pos = FrameLogic.getFramesAround(frames, frame);
        for (int i = 0; i < pos.length; i++) {
            best = compareNodes(best, frameNodesHelp[pos[i][0]][pos[i][1]]);
        }
        return best;
    }

    private FrameNode compareNodes(FrameNode best, FrameNode frameNode) {
        if (best != null && frameNode != null) {
            return best.getStepsHome() <= frameNode.getStepsHome() ? best : frameNode;
        } else if (best == null) {
            return frameNode;
        }
        return best;
    }

    private List<Frame> findNodes(Frame frame) {
        List<Frame> nodes = new ArrayList<Frame>();
        int[][] pos = FrameLogic.getFramesAround(frames, frame);
        for (int i = 0; i < pos.length; i++) {
            Frame f = frames[pos[i][0]][pos[i][1]];
            if (f.getType() == FrameType.WATER && (f.getShip() == null ||
                    start.getShip().getType() == ShipType.SUBMARINE)) {
                nodes.add(f);
            }
        }
        return nodes;
    }
}
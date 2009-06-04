/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
import no.eirikb.piratewars.client.logic.FrameLogic;
import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.Ship;

/**
 
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public final class Canvas extends GWTCanvas {

    private MouseListener listener;
    private MapPanel mapPanel;
    private int width;
    private int heigth;
    private int frameWidth;
    private int frameHeight;

    public Canvas(MapPanel mapPanel, int width, int height, int frameWidth, int frameHeight) {
        super(width, height);
        this.mapPanel = mapPanel;
        this.width = width;
        this.heigth = height;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        sinkEvents(Event.MOUSEEVENTS);
    }

    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);
        if (listener != null) {
            int x = event.getClientX() - getAbsoluteLeft();
            int y = event.getClientY() - getAbsoluteTop();
            switch (event.getTypeInt()) {
                case Event.ONMOUSEDOWN:
                    listener.onMouseDown(this, x, y);
                    break;
                case Event.ONMOUSEMOVE:
                    listener.onMouseMove(this, x, y);
                    break;
                case Event.ONMOUSEUP:
                    listener.onMouseUp(this, x, y);
                    break;
            }
        }
    }

    public void drawShip(ImageElement img, double offsetX, double offsetY) {
        Frame[][] frames = mapPanel.getMap().getFrames();
        int x = (int) (offsetX / frameWidth);
        int oy = (int) (x % 2 == 0 ? offsetY : offsetY - (frameHeight / 2));
        int y = (int) (oy / frameHeight);
        int[][] fs = FrameLogic.getFramesAround(frames, frames[x][y]);
        for (int i = 0; i < fs.length; i++) {
            Frame f = frames[fs[i][0]][fs[i][1]];
            drawFrame(f);
            Ship s = f.getShip();
            if (s != null) {
                mapPanel.hover(f);
            }
        }
        drawFrame(frames[x][y]);
        super.drawImage(img, offsetX, offsetY);
        Ship s = frames[x][y].getShip();
        if (s != null) {
            mapPanel.hover(frames[x][y]);
        }
    }

    public void drawland(ImageElement image, Frame frame, int rotate) {
        int y = frame.getX() % 2 == 0 ? frame.getY() * frameHeight
                : frame.getY() * frameHeight + (frameHeight / 2);
        int x = frame.getX() * frameWidth;
        int px = 0;
        int py = 0;
        switch (rotate) {
            case 1:
                px = 2 * (image.getWidth() / 3);
                py = -image.getHeight() / 3;
                break;
            case 2:
                px = image.getWidth();
                py = image.getHeight();
                break;
            case 3:
                py = image.getHeight();
                break;
        }

        saveContext();
        translate(x + px, y + py);
        rotate((Math.PI * 60 * rotate) / 180);
        drawImage(image, 0, 0);
        restoreContext();
    }

    public void drawImage(ImageElement image, Frame frame, boolean center) {
        int y = frame.getX() % 2 == 0 ? frame.getY() * frameHeight
                : frame.getY() * frameHeight + (frameHeight / 2);
        int x = frame.getX() * frameWidth;
        if (center) {
            x += (frameWidth - image.getWidth()) / 2;
            y += (frameHeight - image.getHeight()) / 2;
        }
        drawImage(image, x, y);
    }

    public void drawFrame(Frame frame) {
        int y = frame.getX() % 2 == 0 ? frame.getY() * frameHeight
                : frame.getY() * frameHeight + (frameHeight / 2);
        ImageElement image = null;
        switch (frame.getType()) {
            case LAND:
                image = mapPanel.getImageHandler().getLand(0, 0);
                break;
            case WATER:
                image = mapPanel.getImageHandler().getWater();
                break;
        }

        if (image != null) {
            drawImage(image, frame.getX() * frameWidth, y);
        }
    }

    public void setListener(MouseListener listener) {
        this.listener = listener;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameWidth() {
        return frameWidth;
    }
}

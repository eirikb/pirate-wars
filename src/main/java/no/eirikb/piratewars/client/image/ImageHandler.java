/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */
package no.eirikb.piratewars.client.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
public class ImageHandler {

    private final String WATERNAME = "water";
    private final int WATERAMOUNT = 2;
    private final String LANDNAME = "land";
    private final int LANDAMOUNT = 1;
    private final int LANDTYPES = 12;
    private final String HOVERNAME = "hover";
    private final int HOVERAMOUNT = 2;
    private final int HOVERTYPES = 4;
    private final String SHIPNAME = "ship";
    private final int SHIPAMOUNT = 3;
    private final int SHIPTYPES = 3;
    private final String TOWNNAME = "town";
    private final int TOWNTYPES = 5;
    private final int TOWNAMOUNT = 2;
    private final String EXTENSION = ".png";
    private ImageElement[] water;
    private ImageElement[][] land;
    private ImageElement[][] hover;
    private ImageElement[][] ship;
    private ImageElement[][] town;
    private ImageHandlerListener listener;
    private int totalImages;
    private int waterAnimation;

    public ImageHandler() {
        totalImages = WATERAMOUNT + (LANDAMOUNT * LANDTYPES) + (SHIPAMOUNT * SHIPTYPES) +
                (HOVERAMOUNT * HOVERTYPES) + (TOWNTYPES * TOWNAMOUNT);
    }

    public void run() {
        water = new ImageElement[WATERAMOUNT];
        for (int i = 0; i < water.length; i++) {
            final int j = i;
            String[] imageName = {GWT.getHostPageBaseURL() + "img/" + WATERNAME + j + EXTENSION};
            ImageLoader.loadImages(imageName, new ImageLoader.CallBack() {

                public void onImagesLoaded(ImageElement[] imageElements) {
                    water[j] = imageElements[0];
                    if (listener != null) {
                        listener.progress();
                    }
                }
            });
        }

        land = new ImageElement[LANDTYPES][LANDAMOUNT];
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[0].length; j++) {
                final int type = i;
                final int k = j;
                String[] imageName = {GWT.getHostPageBaseURL() + "img/" + LANDNAME + type +
                    "_" + k + EXTENSION};
                ImageLoader.loadImages(imageName, new ImageLoader.CallBack() {

                    public void onImagesLoaded(ImageElement[] imageElements) {
                        land[type][k] = imageElements[0];
                        if (listener != null) {
                            listener.progress();
                        }
                    }
                });
            }
        }

        hover = new ImageElement[HOVERTYPES][HOVERAMOUNT];
        for (int i = 0; i < hover.length; i++) {
            for (int j = 0; j < hover[0].length; j++) {
                final int type = i;
                final int k = j;
                String[] imageName = {GWT.getHostPageBaseURL() + "img/" + HOVERNAME + type +
                    "_" + k + EXTENSION};
                ImageLoader.loadImages(imageName, new ImageLoader.CallBack() {

                    public void onImagesLoaded(ImageElement[] imageElements) {
                        hover[type][k] = imageElements[0];
                        if (listener != null) {
                            listener.progress();
                        }
                    }
                });
            }
        }

        ship = new ImageElement[SHIPTYPES][SHIPAMOUNT];
        for (int i = 0; i < ship.length; i++) {
            for (int j = 0; j < ship[0].length; j++) {
                final int type = i;
                final int k = j;
                String[] imageName = {GWT.getHostPageBaseURL() + "img/" + SHIPNAME + type +
                    "_" + k + EXTENSION};
                ImageLoader.loadImages(imageName, new ImageLoader.CallBack() {

                    public void onImagesLoaded(ImageElement[] imageElements) {
                        ship[type][k] = imageElements[0];
                        if (listener != null) {
                            listener.progress();
                        }
                    }
                });
            }
        }

        town = new ImageElement[TOWNTYPES][TOWNAMOUNT];
        for (int i = 0; i < town.length; i++) {
            for (int j = 0; j < town[0].length; j++) {
                final int type = i;
                final int k = j;
                String[] imageName = {GWT.getHostPageBaseURL() + "img/" + TOWNNAME + type +
                    "_" + k + EXTENSION};
                ImageLoader.loadImages(imageName, new ImageLoader.CallBack() {

                    public void onImagesLoaded(ImageElement[] imageElements) {
                        town[type][k] = imageElements[0];
                        if (listener != null) {
                            listener.progress();
                        }
                    }
                });
            }
        }
    }

    public int getTotalImages() {
        return totalImages;
    }

    public ImageElement getLand(int type, int i) {
        return land[type][i];
    }

    public ImageElement getWater() {
        return water[waterAnimation];
    }

    public ImageElement getHover(int type, int i) {
        return hover[type][i];
    }

    public void animateWater() {
        waterAnimation++;
        if (waterAnimation >= water.length) {
            waterAnimation = 0;
        }
    }

    public ImageElement getShip(int type, int i) {
        return ship[type][i];
    }

    public ImageElement getTown(int type, int i) {
        return town[type][i];
    }

    public void setListener(ImageHandlerListener listener) {
        this.listener = listener;
    }
}
/*
 * PiratewarsEntryPoint.java
 *
 * Created on June 2, 2009, 9:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package no.eirikb.piratewars.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import no.eirikb.piratewars.client.image.ImageHandler;
import no.eirikb.piratewars.client.image.ImageHandlerListener;
import no.eirikb.piratewars.client.shared.Frame;
import no.eirikb.piratewars.client.shared.FrameType;
import no.eirikb.piratewars.client.shared.Game;
import no.eirikb.piratewars.client.shared.Map;
import no.eirikb.piratewars.client.shared.Ship;
import no.eirikb.piratewars.client.shared.ShipType;
import no.eirikb.piratewars.client.shared.Town;
import no.eirikb.piratewars.client.shared.User;
import no.eirikb.piratewars.client.shared.UserColor;

/**
 *
 * @author eirikb
 */
public class Piratewars implements EntryPoint {

    private int lol;
    private User me;
    private final String VERSION = "1.0";

    /** Creates a new instance of thevikingbayEntryPoint */
    public Piratewars() {
    }

    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        Window.setTitle("Piratewars. Version: " + VERSION);
        getProperties();
        VerticalPanel vp = new VerticalPanel();
        final ImageHandler ih = new ImageHandler();
        final Label l = new Label("Downloading images...");
        lol = 0;
        final int tot = ih.getTotalImages();
        ih.setListener(new ImageHandlerListener() {

            public void progress() {
                l.setText("Progress " + ++lol + " of total " + tot);
                if (lol == tot) {
                    l.setText("Done! Starting game...");
                    startGame(ih);
                    l.setText("Done! Play!");
                }
            }
        });
        vp.add(l);
        RootPanel.get().add(vp);
        ih.run();
    }

    private void startGame(ImageHandler ih) {

        int[] l = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0,
            0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0,
            0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0,
            0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        Map map = new Map(25, l.length / 25);

        for (int i = 0; i < l.length; i++) {
            int x = i % 25;
            int y = i / 25;
            Frame frame = new Frame(x, y);
            switch (l[i]) {
                case 0:
                    frame.setType(FrameType.WATER);
                    break;
                case 1:
                    frame.setType(FrameType.LAND);
                    break;
            }
            map.setFrame(x, y, frame);
        }

        Frame[][] frames = map.getFrames();
        me = new User("me", 5);
        me.setColor(UserColor.YELLOW);
        new Ship(me, frames[0][0], ShipType.BATTLESHIP, 5);
        new Ship(me, frames[5][2], ShipType.SPEEDBOAT, 15);
        new Ship(me, frames[5][3], ShipType.SUBMARINE, 4);
        new Ship(me, frames[6][6], ShipType.SUBMARINE, 4);

        new Town(frames[2][1], 5);

        Game game = new Game("TestGame", map, 1);
        game.getUsers().add(me);

        GamePanel gamePanel = new GamePanel(this, game, ih, 640, 480, 36, 40);
        RootPanel.get().add(gamePanel);
    }

    public User getMe() {
        return me;
    }

    private void getProperties() {
        final HashMap<String, String> properties = new HashMap<String, String>();
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, GWT.getModuleBaseURL() + "version.properties");
        try {
            rb.sendRequest("", new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    try {
                        String[] ps = response.getText().split("\\n");
                        for (String propery : ps) {
                            propery = propery.trim();
                            if (propery.charAt(0) != '#') {
                                int space = propery.indexOf("=");
                                if (space > 0) {
                                    String value = propery.substring(space + 1);
                                    propery = propery.substring(0, space);
                                    properties.put(propery.toUpperCase(), value);
                                }
                            }
                        }
                        Window.setTitle("Piratewars. Version: " + VERSION + ". Build: " + properties.get("BUILD") +
                                ". GWT: " + GWT.getVersion());
                    } catch (Exception ex) {
                    }
                }

                public void onError(Request request, Throwable exception) {
                }
            });
        } catch (RequestException ex) {
        }
    }
}

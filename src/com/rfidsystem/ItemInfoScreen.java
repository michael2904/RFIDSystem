package com.rfidsystem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ItemInfoScreen {

    private JFrame frame;
    private Timer timer;
    //1 minutes in ms
    private int timerTime = 1 * 60 * 1000;

    public ItemInfoScreen(Item item) {
        Font fontTitle = null;
        try {
            fontTitle = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("resources/myFont2.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(fontTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Font fontDescription = null;
        try {
            fontDescription = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("resources/myFont1.ttf").openStream());
            GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            genv.registerFont(fontDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Hardcoded Values
        String itemName = "Luca Del Forte";
        String itemDescription = "The monk strap gives this shoe a quality that's both casual and refined; all of the comfort of a slip-on without the formality of lace-ups.";
        String itemPrice = "200 CAD       Available Quantity: 12";
        String itemImage = "http://assets.stickpng.com/thumbs/580b57fbd9996e24bc43bf50.png";
        String field1 = "Material Upper: Leather";
        String field2 = "Lining Material: Cotton";
        String field3 = "Sole: Rubber";
        String fieldsString = "<br /><br />" + field1 + "<br />" + field2 + "<br />" + field3;

        /*
        String itemName = item.getName();
        String itemPrice = item.getPrice();
        String itemDescription = item.getDescription();
        String itemImage = "http://assets.stickpng.com/thumbs/580b57fbd9996e24bc43bf50.png";
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ScreenWidth = (int) screenSize.getWidth();
        int ScreenHeight = (int) screenSize.getHeight();

        frame = new JFrame("JFrame Example");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.WHITE);

        //Load Title
        JLabel nameLabel = new JLabel(itemName);
        if (fontTitle != null) {
            nameLabel.setFont(fontTitle.deriveFont(Font.PLAIN, 90f));
        } else {
            nameLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        }
        int titleHeight = (int) (0.15 * ScreenHeight);
        nameLabel.setPreferredSize(new Dimension(ScreenWidth, titleHeight));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        panel.add(nameLabel);

        //Load Image URL
        URL img = null;
        try {
            img = new URL(itemImage);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemInfoScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        int imageHeight = (int) (0.45 * ScreenHeight);
        int imageHeightInternal = (int) (0.35 * ScreenHeight);
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imageHeightInternal, imageHeightInternal, Image.SCALE_SMOOTH));
        JLabel picLabel = new JLabel(imgIcon);
        picLabel.setPreferredSize(new Dimension(ScreenWidth, imageHeight));
        picLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(picLabel);

        //Load Description
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" + itemDescription + fieldsString + "</div></html>");
        if (fontDescription != null) {
            descriptionLabel.setFont(fontDescription.deriveFont(Font.PLAIN, 30f));
        } else {
            descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        }
        int descriptionHeight = (int) (0.3 * ScreenHeight);
        descriptionLabel.setPreferredSize(new Dimension(ScreenWidth, descriptionHeight));
        descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 250, 0, 250));
        panel.add(descriptionLabel);

        //Load Price
        JLabel priceLabel = new JLabel("Price: " + itemPrice);
        if (fontDescription != null) {
            priceLabel.setFont(fontDescription.deriveFont(Font.PLAIN, 40f));
        } else {
            priceLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        }
        int priceHeight = (int) (0.1 * ScreenHeight);
        priceLabel.setPreferredSize(new Dimension(ScreenWidth, priceHeight));
        priceLabel.setVerticalAlignment(SwingConstants.TOP);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(priceLabel);

        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        setWindowCloseTimer(timerTime);
    }

    /**
     * Close window after a certain amount of time
     *
     * @param timeMs time after which to close the window
     */
    private void setWindowCloseTimer(int timeMs) {
        try {
            timer = new Timer();
            timer.schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    closeWindow();
                    //fadeOut();
                }
            }, timeMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Immediately close window
     */
    public void closeWindow() {
        try {
            System.out.println("Closing Item Info Screen!");
            timer.cancel();
            frame.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Immediately close window
     */
    public void showWindow() {
        try {
            System.out.println("Showing Item Info Screen!");
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.rfidsystem;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ItemInfoScreen {

    private JFrame frame;
    private Timer timer;
    //2 minutes in ms
    private int timerTime = 2 * 60 * 1000;

    public ItemInfoScreen(Item item) {
        //Hardcoded Values
        String itemName = "Nike Air Force 1 Ultra Flyknit";
        String itemDescription = "The Nike Air Force 1 Ultra Flyknit Men's Shoe weighs 50 percent less than the '82 hoops original thanks to its all-new, ultra-breathable Nike Flyknit upper. Strategically crafted Flyknit panels add dimension while remaining true to the AF1 design aesthetic.";
        String itemPrice = "200";
        String itemImage = "http://images.nike.com/is/image/DotCom/PDP_HERO/817420_003_A_PREM/air-force-1-ultra-flyknit-shoe.jpg";
        
        /*
        String itemName = item.getName();
        String itemPrice = item.getPrice();
        String itemDescription = item.getDescription();
        String itemImage = "http://images.nike.com/is/image/DotCom/PDP_HERO/817420_003_A_PREM/air-force-1-ultra-flyknit-shoe.jpg";
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
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        int titleHeight = (int) (0.2 * ScreenHeight);
        nameLabel.setPreferredSize(new Dimension(ScreenWidth, titleHeight));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nameLabel);

        //Load Image URL
        URL img = null;
        try {
            img = new URL(itemImage);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemInfoScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        int imageHeight = (int) (0.4 * ScreenHeight);
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(imageHeight, imageHeight, Image.SCALE_DEFAULT));
        JLabel picLabel = new JLabel(imgIcon);
        picLabel.setPreferredSize(new Dimension(ScreenWidth, imageHeight));
        panel.add(picLabel);

        //Load Description
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" + itemDescription + "</div></html>");
        descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        int descriptionHeight = (int) (0.2 * ScreenHeight);
        descriptionLabel.setPreferredSize(new Dimension(ScreenWidth, descriptionHeight));
        panel.add(descriptionLabel);

        //Load Price
        JLabel priceLabel = new JLabel("Price: " + itemPrice);
        priceLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        int priceHeight = (int) (0.2 * ScreenHeight);
        priceLabel.setPreferredSize(new Dimension(ScreenWidth, priceHeight));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        Border border = priceLabel.getBorder();
        Border margin = new EmptyBorder(0, 0, 0, 100);
        priceLabel.setBorder(new CompoundBorder(border, margin));
        panel.add(priceLabel);

        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        AWTUtilities.setWindowOpacity(frame, 0f);
        fadeIn();

        setWindowCloseTimer(timerTime);
    }

    private void fadeIn() {
        new javax.swing.Timer(5, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                boolean cancel = false;
                float currentOpacity = AWTUtilities.getWindowOpacity(frame);
                if (currentOpacity < 1f) {
                    float newOpacity = currentOpacity + 0.02f;
                    if (newOpacity > 1f) {
                        newOpacity = 1f;
                        cancel = true;
                    }
                    AWTUtilities.setWindowOpacity(frame, newOpacity);
                    if (cancel) {
                        ((javax.swing.Timer) evt.getSource()).stop();
                    }
                }
            }
        }
        ).start();
    }

    private void fadeOut() {
        new javax.swing.Timer(5, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                boolean cancel = false;
                float currentOpacity = AWTUtilities.getWindowOpacity(frame);
                if (currentOpacity > 0f) {
                    float newOpacity = currentOpacity - 0.01f;
                    if (newOpacity < 0) {
                        newOpacity = 0;
                        cancel = true;
                    }
                    AWTUtilities.setWindowOpacity(frame, newOpacity);
                    if (cancel) {
                        ((javax.swing.Timer) evt.getSource()).stop();
                        frame.dispose();
                    }
                }
            }
        }
        ).start();
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
                    fadeOut();
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
            timer.cancel();
            fadeOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.rfidsystem;

import com.sun.awt.AWTUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GeneralInfoScreen extends JFrame {

    private JFrame frame;

    public GeneralInfoScreen() {
        frame = new JFrame("JFrame Example");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel center = new JPanel(new GridLayout(0, 1));
        center.setBackground(Color.WHITE);
        Class cla = getClass();
        System.out.println(cla.getCanonicalName());
        URL url = cla.getResource("resources/MPact.png");
        System.out.println(url);
        Icon myImgIcon = new ImageIcon(url);
        JLabel imageLabel = new JLabel(myImgIcon);
        imageLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        JLabel textLabel = new JLabel("Pick an item to view its information!");
        textLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.TOP);

        center.add(imageLabel);
        center.add(textLabel);
        panel.add(center, BorderLayout.CENTER);
        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//        AWTUtilities.setWindowOpacity(frame, 0f);
        fadeIn(); 
    }

    private void fadeIn() {
        new javax.swing.Timer(5, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                boolean cancel = false;
                float currentOpacity = AWTUtilities.getWindowOpacity(frame);
                if (currentOpacity < 1f) {
                    float newOpacity = currentOpacity + 0.01f;
                    if (newOpacity > 1f) {
                        newOpacity = 1f;
                        cancel = true;
                    }
//                    AWTUtilities.setWindowOpacity(frame, newOpacity);
                    if (cancel) {
                        ((javax.swing.Timer) evt.getSource()).stop();
                    }
                }
            }
        }
        ).start();
    }
}

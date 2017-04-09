package com.rfidsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GeneralInfoScreen {

	private JFrame frame;

	public GeneralInfoScreen() {
		frame = new JFrame("JFrame Example");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		JPanel panel = new JPanel(new BorderLayout());

		JPanel center = new JPanel(new GridLayout(0, 1));
		center.setBackground(Color.WHITE);
		URL url = getClass().getResource("resources/MPact.png");
		Icon myImgIcon = new ImageIcon(url);
		JLabel imageLabel = new JLabel(myImgIcon);
		imageLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("resources/myFont1.ttf").openStream());
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel textLabel = new JLabel("Pick an item to view its information!");
		if (font != null) {
			textLabel.setFont(font.deriveFont(Font.PLAIN, 60f));
		} else {
			textLabel.setFont(new Font("Serif", Font.PLAIN, 50));
		}
		textLabel.setForeground(Color.BLACK);
		textLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		textLabel.setVerticalAlignment(SwingConstants.TOP);

		center.add(imageLabel);
		center.add(textLabel);
		panel.add(center, BorderLayout.CENTER);
		frame.add(panel);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

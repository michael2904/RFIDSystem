package com.rfidsystem;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static javafx.concurrent.Worker.State.FAILED;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SimpleSwingBrowser extends JFrame {

    private final String pathToGeneralHTML = "file:///C:/Users/Daka/GeneralInfoScreen/index.html";
    private final String pathToItemDataXML = "C:\\Users\\Daka\\ItemInfoScreen\\Data.xml";
    private final String pathToItemHTML = "file:///C:/Users/Daka/ItemInfoScreen/index.html";
    private final JFXPanel jfxPanel = new JFXPanel();
    private final JPanel panel = new JPanel(new BorderLayout());
    private WebEngine engine;
    private java.util.Timer timer;
    //2 minutes in ms
    private final int timerTime = 2 * 60 * 1000;

    public SimpleSwingBrowser() {
        super();
        initComponents();
        loadGeneralScreen();
        this.setVisible(true);
    }

    public void loadGeneralScreen() {
        loadURL(pathToGeneralHTML);
    }

    public void loadItemInfoScreen(Item item) {
        String title = item.getName();
        String description = item.getDescription();
        //TODO
        String imageURL = "https://sneakerbardetroit.com/wp-content/uploads/2015/11/nike-air-force-1-ultra-flyknit-multicolor-1.jpg";
        String price = item.getPrice();
        
        /* Hardcoded Values for testing
        String title = "2Nike Air Force 1 Ultra Flyknit";
        String description = "3The Nike Air Force 1 Ultra Flyknit Men's Shoe weighs 50 percent less than the '82 hoops original thanks to its all-new, ultra-breathable Nike Flyknit upper. Strategically crafted Flyknit panels add dimension while remaining true to the AF1 design aesthetic.";
        String imageURL = "https://sneakerbardetroit.com/wp-content/uploads/2015/11/nike-air-force-1-ultra-flyknit-multicolor-1.jpg";
        String price = "333";
         */
        createXMLFIle(title, description, imageURL, price);
        loadURL(pathToItemHTML);
        setWindowCloseTimer(timerTime);
    }

    /**
     * Close window after a certain amount of time
     *
     * @param timeMs time after which to close the window
     */
    private void setWindowCloseTimer(int timeMs) {
        try {
            timer = new java.util.Timer();
            timer.schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    timer.cancel();
                    loadGeneralScreen();
                }
            }, timeMs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createXMLFIle(String title, String description, String imageURL, String price) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("information");
            doc.appendChild(rootElement);

            Element titleElement = doc.createElement("title");
            titleElement.appendChild(doc.createTextNode(title));
            rootElement.appendChild(titleElement);

            Element imageElement = doc.createElement("imageURL");
            imageElement.appendChild(doc.createTextNode(imageURL));
            rootElement.appendChild(imageElement);

            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(description));
            rootElement.appendChild(descriptionElement);

            Element priceElement = doc.createElement("price");
            priceElement.appendChild(doc.createTextNode(price));
            rootElement.appendChild(priceElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(pathToItemDataXML));
            transformer.transform(source, result);

            System.out.println("File Saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        createScene();
        panel.add(jfxPanel, BorderLayout.CENTER);
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    private void createScene() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                WebView view = new WebView();
                engine = view.getEngine();

                engine.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                SimpleSwingBrowser.this.setTitle(newValue);
                            }
                        });
                    }
                });

                engine.getLoadWorker()
                        .exceptionProperty()
                        .addListener(new ChangeListener<Throwable>() {

                            @Override
                            public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                                if (engine.getLoadWorker().getState() == FAILED) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            JOptionPane.showMessageDialog(
                                                    panel,
                                                    (value != null)
                                                            ? engine.getLocation() + "\n" + value.getMessage()
                                                            : engine.getLocation() + "\nUnexpected error.",
                                                    "Loading error...",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    });
                                }
                            }
                        });

                jfxPanel.setScene(new Scene(view));
            }
        });
    }

    private void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String tmp = toURL(url);

                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }

                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }
}

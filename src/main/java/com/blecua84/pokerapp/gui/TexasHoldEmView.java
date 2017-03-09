package com.blecua84.pokerapp.gui;

import com.blecua84.pokerapp.game.actions.Strategy;

import javax.swing.*;

import static com.blecua84.pokerapp.gui.ImageManager.IMAGES_PATH;

/**
 * Clase que implementa el frame.
 *
 * @author blecua84
 */
public class TexasHoldEmView extends JFrame {

    private static final int WINDOW_HEIGHT = 800;
    private static final int WINDOW_WITH = 1280;
    private static final String WINDOW_TITLE = "J Poker";
    private static final String WINDOW_ICON = IMAGES_PATH + "poker-chip.png";

    private TexasHoldEmTablePanel jTablePanel;

    public TexasHoldEmView(Strategy delegate) {
        initComponents();
        setTitle(WINDOW_TITLE);
        setIconImage(ImageManager.INSTANCE.getImage(WINDOW_ICON));
        setBounds(0, 0, WINDOW_WITH, WINDOW_HEIGHT);
        jTablePanel.setStrategy(delegate);
    }

    public Strategy getStrategy() {
        return jTablePanel;
    }

    private void initComponents() {

        jTablePanel = new TexasHoldEmTablePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTablePanel.setPreferredSize(new java.awt.Dimension(WINDOW_WITH, WINDOW_HEIGHT));

        javax.swing.GroupLayout jTablePanelLayout = new javax.swing.GroupLayout(jTablePanel);
        jTablePanel.setLayout(jTablePanelLayout);
        jTablePanelLayout.setHorizontalGroup(
                jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, WINDOW_WITH, Short.MAX_VALUE)
        );
        jTablePanelLayout.setVerticalGroup(
                jTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, WINDOW_HEIGHT, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, WINDOW_WITH, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, WINDOW_HEIGHT, Short.MAX_VALUE)
        );
        pack();
    }
}

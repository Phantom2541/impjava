package com.mycompany.impjava;

import javax.swing.SwingUtilities;

/**
 *
 * @author Smart Care
 */
public class Impjava {

    public static void main(String[] args) {
        // Always recommended to start Swing apps on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login login = new Login();
                login.setVisible(true);
                login.pack();
                login.setLocationRelativeTo(null);
                login.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}

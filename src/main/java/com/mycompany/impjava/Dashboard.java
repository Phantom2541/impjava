
package com.mycompany.impjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private JPanel sidebar;
    private JPanel contentArea;
    private boolean isSidebarVisible = true;

    public Dashboard() {
        setTitle("Dashboard");

        // Maximize the window on start
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Content Area
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        // Top Bar with gradient background
        JPanel topBar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Create a gradient from #42009F to #834AD3
                GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3"));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topBar.setPreferredSize(new Dimension(getWidth(), 80)); // Increased height of the header

        // Welcome Label centered
        JLabel welcomeLabel = new JLabel("Welcome to IMP Library", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.setLayout(new BorderLayout());
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        // Toggle Button
        JButton toggleButton = new JButton("☰");
        toggleButton.addActionListener(e -> toggleSidebar());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(toggleButton);
        topBar.add(buttonPanel, BorderLayout.WEST);
        toggleButton.setBackground(null);

        contentArea.add(topBar, BorderLayout.NORTH);
        add(contentArea, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Create a gradient from #4E10A4 to #97579D
                GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#4E10A4"), 0, getHeight(), Color.decode("#97579D"));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, getHeight()));

        // First option: Menu List (JLabel instead of JButton)
        JLabel menuListLabel = new JLabel("Menu List");
        menuListLabel.setFont(new Font("Arial", Font.BOLD, 25));
        menuListLabel.setForeground(Color.WHITE);
        menuListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuListLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(menuListLabel);

        // Add renamed sidebar buttons as "Option 1", "Option 2", "Option 3", etc.
        panel.add(createSidebarButton("Option 1"));
        panel.add(createSidebarButton("Option 2"));
        panel.add(createSidebarButton("Option 3"));
        panel.add(createSidebarButton("Option 4"));
        panel.add(createSidebarButton("Option 5"));
        panel.add(createSidebarButton("Option 6"));
        panel.add(createSidebarButton("Option 7"));
        panel.add(createSidebarButton("Option 8"));
        panel.add(createSidebarButton("Option 9"));

        panel.add(Box.createVerticalGlue());
        
        // Modify the "Logout" button styling
        JButton logoutButton = createSidebarButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 25));  // Bold and larger font size
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Ensure the button is centered

        panel.add(logoutButton);

        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isPressed()) {
                    setBackground(new Color(70, 70, 70));  // Provide a slight background color when pressed
                }
            }
        };

        // Make button background transparent
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBackground(null);  // Make the button background transparent
        button.setForeground(Color.WHITE);  // Set text color to white
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Set a larger font (e.g., 18px)
        button.setFont(new Font("Arial", Font.PLAIN, 18));

        // Mouse hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#6B00FF"));  // Set hover color
                button.setOpaque(true);  // Ensure the background is visible during hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null);  // Reset background when mouse exits
                button.setOpaque(false);  // Make the background transparent again
            }
        });

        // Example action
        button.addActionListener(e -> JOptionPane.showMessageDialog(this, text + " clicked!"));

        return button;
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard frame = new Dashboard();
            frame.setVisible(true);
        });
    }
}

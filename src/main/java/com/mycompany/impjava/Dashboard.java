package com.mycompany.impjava;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Dashboard extends javax.swing.JFrame {
    private JPanel sidebar;
    private JPanel contentArea;
    private boolean isSidebarVisible = true;

    public Dashboard() {
        setTitle("Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Makes the window maximized
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
                GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3"));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topBar.setPreferredSize(new Dimension(getWidth(), 80));
        topBar.setLayout(new BorderLayout());

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to IMP Library", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        // Custom Toggle Button
        JButton toggleButton = new JButton("☰") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isPressed()) {
                    setBackground(new Color(90, 40, 130));
                } else if (getModel().isRollover()) {
                    setBackground(new Color(100, 50, 150));
                } else {
                    setBackground(new Color(80, 30, 120));
                }
            }
        };
        toggleButton.setFont(new Font("SansSerif", Font.BOLD, 28));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);
        toggleButton.setPreferredSize(new Dimension(60, 60));
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setToolTipText("Toggle Sidebar");
        toggleButton.addActionListener(e -> toggleSidebar());

        // Add toggle button to a padded transparent panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(toggleButton, BorderLayout.CENTER);

        topBar.add(buttonPanel, BorderLayout.WEST);
        contentArea.add(topBar, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = {"ID", "TITLE", "AUTHOR", "PUBLISHER", "COPYRIGHT", "LCN", "SECTION", "ISACTIVE", "CREATED", "UPDATED"};
        Object[][] data = {
                {"1", "Sample Book", "John Doe", "Sample Publisher", "2022", "LCN123", "Section A", "Yes", "2022-01-01", "2022-06-01"},
                {"2", "Another Book", "Jane Smith", "Another Publisher", "2023", "LCN456", "Section B", "No", "2023-02-01", "2023-07-01"},
                {"3", "Third Book", "Alice Johnson", "Third Publisher", "2024", "LCN789", "Section C", "Yes", "2024-03-01", "2024-08-01"},
                {"4", "Temp Book A", "Temp Author A", "Temp Publisher A", "2025", "LCN111", "Section D", "Yes", "2025-01-15", "2025-04-10"},
                {"5", "Temp Book B", "Temp Author B", "Temp Publisher B", "2025", "LCN222", "Section E", "No", "2025-02-20", "2025-04-11"},
                {"6", "Temp Book C", "Temp Author C", "Temp Publisher C", "2025", "LCN333", "Section F", "Yes", "2025-03-25", "2025-04-12"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Customize the table header to match the theme
        table.getTableHeader().setBackground(new Color(80, 30, 120));  // Sidebar/top bar color
        table.getTableHeader().setForeground(Color.WHITE);  // Text color (white for contrast)
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));  // Bold header text
        table.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(100, 50, 150), 2));  // Optional border

        // Create a JPanel to center the table with margin
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // Adjust the margin here
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the table panel to the content area
        contentArea.add(tablePanel, BorderLayout.CENTER);

        add(contentArea, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#4E10A4"), 0, getHeight(), Color.decode("#97579D"));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, getHeight()));

        // ✅ Add white border to the sidebar
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JLabel menuListLabel = new JLabel("Menu List");
        menuListLabel.setFont(new Font("Arial", Font.BOLD, 25));
        menuListLabel.setForeground(Color.WHITE);
        menuListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuListLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(menuListLabel);

        String[] menuItems = {
                "Create", "Edit", "Option 3", "Option 4", "Option 5",
                "Option 6", "Option 7", "Option 8", "Option 9"
        };

        for (String item : menuItems) {
            panel.add(createSidebarButton(item));
        }

        panel.add(Box.createVerticalGlue());

        JButton logoutButton = createSidebarButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 25));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoutButton);

        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isPressed()) {
                    setBackground(new Color(70, 70, 70));
                }
            }
        };

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBackground(null);
        button.setForeground(Color.WHITE);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 18));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#6B00FF"));
                button.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(null);
                button.setOpaque(false);
            }
        });

        button.addActionListener(e -> JOptionPane.showMessageDialog(this, text + " clicked!"));

        return button;
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard frame = new Dashboard();
            frame.setVisible(true);
        });
    }
}
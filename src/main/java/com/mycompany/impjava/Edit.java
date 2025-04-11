package com.mycompany.impjava;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Edit extends JFrame {
    private JPanel sidebar;
    private JPanel contentArea;
    private boolean isSidebarVisible = true;

    public Edit() {
        setTitle("Edit Entry");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        // Content Area
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.GRAY);

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
        JLabel welcomeLabel = new JLabel("Edit Library Entry", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        // Toggle Sidebar Button
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

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(toggleButton, BorderLayout.CENTER);

        topBar.add(buttonPanel, BorderLayout.WEST);
        contentArea.add(topBar, BorderLayout.NORTH);

        // Centered Form Panel with wider layout
        JPanel formPanel = createFormPanel();
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 50, 150), 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        formPanel.setMaximumSize(new Dimension(750, Integer.MAX_VALUE));

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        centerWrapper.add(formPanel, gbc);

        contentArea.add(centerWrapper, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 245, 245));

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        Dimension fieldSize = new Dimension(400, 35); // Wider and consistent

        formPanel.add(createLabeledField("Title:", labelFont, fieldFont, fieldSize, "Existing Title"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledField("Author:", labelFont, fieldFont, fieldSize, "Existing Author"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledField("Publisher:", labelFont, fieldFont, fieldSize, "Existing Publisher"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledField("Copyright:", labelFont, fieldFont, fieldSize, "2023"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledField("LCN:", labelFont, fieldFont, fieldSize, "12345"));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(createLabeledField("Section:", labelFont, fieldFont, fieldSize, "Section A"));
        formPanel.add(Box.createVerticalStrut(20));

        JButton submitButton = new JButton("Update");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(80, 30, 120));
        submitButton.setPreferredSize(new Dimension(120, 40));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Form Updated!");
        });
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(submitButton);

        // Add margin to left and right
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        paddedPanel.setBackground(new Color(245, 245, 245));
        paddedPanel.add(formPanel, BorderLayout.CENTER);

        return paddedPanel;
    }

    private JPanel createLabeledField(String labelText, Font labelFont, Font fieldFont, Dimension fieldSize, String defaultValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField field = new JTextField(defaultValue);
        field.setFont(fieldFont);
        field.setMaximumSize(fieldSize);
        field.setPreferredSize(fieldSize);
        field.setMinimumSize(fieldSize);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 150), 2));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5)); // Small spacing
        panel.add(field);
        return panel;
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

        // Add white border here
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
            JButton btn = createSidebarButton(item);

            if (item.equals("Create")) {
                btn.setBackground(Color.decode("#5416a3"));  // Matching Option 3 and Option 4 color
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Arial", Font.PLAIN, 18));
                for (MouseListener ml : btn.getMouseListeners()) {
                    btn.removeMouseListener(ml);
                }
                btn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Create clicked!"));
            }


            if (item.equals("Edit")) {
                btn.setBackground(Color.decode("#270359"));
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Arial", Font.PLAIN, 18));
                for (MouseListener ml : btn.getMouseListeners()) {
                    btn.removeMouseListener(ml);
                }
                btn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Edit clicked!"));
            }

            panel.add(btn);
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
                if (!text.equals("Edit")) {
                    button.setBackground(null);
                    button.setOpaque(false);
                }
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

            Edit frame = new Edit();
            frame.setVisible(true);

    }
}

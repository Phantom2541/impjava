package com.mycompany.impjava;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.*;

public class stuBorrow extends JFrame {
    private boolean isSidebarVisible = true;
    private JPanel sidebar;
    private JPanel mainPanel;
    private JComboBox<String> genreComboBox;
    private Map<String, JPanel> genrePanels = new HashMap<>();

    public stuBorrow() {
        setTitle("Books by Genre");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));  // Add margins to mainPanel

        JTable table = createTableWithAction();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JTable createTableWithAction() {
        String[] columns = { "column1", "column2", "column3", "column4", "column5", "Action" };
        Object[][] data = {
                { "Value 1", "Value 2", "Value 3", "Value 4", "Value 5", "Edit" },
                { "Data A", "Data B", "Data C", "Data D", "Data E", "Edit" },
                { "Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Edit" }
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only 'Action' column editable (for button)
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);

        // Header styling
        JTableHeader header = table.getTableHeader();
        TableCellRenderer headerRenderer = new CustomHeaderRenderer();
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Action button renderer and editor
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

        return table;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3"));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton toggleButton = new JButton("\u2630");
        toggleButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setPreferredSize(new Dimension(60, 60));
        toggleButton.addActionListener(e -> toggleSidebar());

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 0, 20);
        headerPanel.add(toggleButton, gbc);

        JLabel titleLabel = new JLabel("IMP Library");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        headerPanel.add(titleLabel, gbc);

        return headerPanel;
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel() {
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
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        panel.add(createSidebarButton("Browse"));
        panel.add(createSidebarButton("Borrow"));
        panel.add(createSidebarButton("Return"));
        panel.add(createSidebarButton("Logout"));

        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 18));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#6B00FF"));
                button.setOpaque(true);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(null);
                button.setOpaque(false);
            }
        });
        button.addActionListener(e -> handleNavigation(text));
        return button;
    }

    private void handleNavigation(String page) {
        switch (page) {
            case "Browse":
                stuSearchBook searchbook = new stuSearchBook();
                searchbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                searchbook.setLocationRelativeTo(null);
                searchbook.setVisible(true);
                searchbook.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Borrow":
                //already here
                break;
            case "Return":
                stuReturn returnbook = new stuReturn();
                returnbook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                returnbook.setLocationRelativeTo(null);
                returnbook.setVisible(true);
                returnbook.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();  // Close the window
                }
                break;
            default:
                System.out.println("Unknown option: " + page);
                break;
        }
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }

    // Custom Header Renderer (Optional - You can customize the header style here)
    class CustomHeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(Color.decode("#501E78"));
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Verdana", Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.CENTER);  // Center the header text
            return label;
        }
    }

    // Button Renderer for Action Button (Edit button)
    public class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(30, 25));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "Edit");
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Paint the gradient directly here
            GradientPaint gp = new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3"));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);  // Round corners with radius 15
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);  // Border with rounded corners
            g2.dispose();
        }
    }


    // Button Editor for Action Button (Edit button)
    public class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private String label;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(false);  // Set non-opaque so we can apply custom background
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Edit" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new stuBorrow().setVisible(true));
    }
}

package com.mycompany.impjava;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class stuReturn extends JFrame {
    private boolean isSidebarVisible = true;
    private JPanel sidebar;
    private JPanel mainPanel;

    public stuReturn() {
        setTitle("Borrowed Books");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40));

        JTable table = createBorrowedsTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JTable createBorrowedsTable() {
        String[] columns = { "ID", "Book ID", "User ID", "Approved ID", "Approved Date", "Borrowed Date", "Status", "Fee", "Remarks" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try (Connection conn = DBConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, bookID, userID, approvedID, approvedDate, borrowedDate, status, fee, remarks FROM borroweds");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("bookID"),
                        rs.getString("userID"),
                        rs.getString("approvedID"),
                        rs.getDate("approvedDate"),
                        rs.getDate("borrowedDate"),
                        rs.getString("status"),
                        rs.getDouble("fee"),
                        rs.getString("remarks")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        TableCellRenderer headerRenderer = new CustomHeaderRenderer();
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

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
                stuBorrow borrow = new stuBorrow();
                borrow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                borrow.setLocationRelativeTo(null);
                borrow.setVisible(true);
                borrow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Return":
                // Already here
                break;
            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
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

    class CustomHeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setBackground(Color.decode("#501E78"));
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Verdana", Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }

    // Replace with your actual DBConnection function or import it from your utility class
    private Connection DBConnection() throws SQLException {
        // Placeholder: Use your actual connection logic
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure you have the MySQL JDBC driver
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/implibrary", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!");
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new stuReturn().setVisible(true));
    }
}

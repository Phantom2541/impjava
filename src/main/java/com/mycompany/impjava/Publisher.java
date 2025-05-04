package com.mycompany.impjava;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Publisher extends JFrame {
    private JPanel sidebar, contentArea;
    private boolean isSidebarVisible = true;
    private DefaultTableModel model;
    private JTable table;

    public Publisher() {
        setTitle("Publishers");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        JPanel topBar = createTopBar();
        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "SubName", "Address", "ACTIONS"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table.setFillsViewportHeight(true);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setBackground(new Color(80, 30, 120));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        table.getColumn("ACTIONS").setCellRenderer(new ButtonRenderer());
        table.getColumn("ACTIONS").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentArea.add(tablePanel, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);

        loadPublishersFromDatabase();
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

        JLabel label = new JLabel("Menu List", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(label);

        panel.add(createSidebarButton("Home"));
        panel.add(createSidebarButton("Books"));
        panel.add(createSidebarButton("Users"));
        panel.add(createSidebarButton("Staffs"));
        panel.add(createSidebarButton("Sales"));
        panel.add(createSidebarButton("Borrows"));
        panel.add(createSidebarButton("Publishers"));
        panel.add(Box.createVerticalGlue());
        panel.add(createSidebarButton("Logout"));

        return panel;
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getModel().isPressed()) {
                    setBackground(new Color(70, 70, 70));
                }
            }
        };

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
            case "Home":
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
                dashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Books":
                Books books = new Books();
                books.setVisible(true);
                books.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Users":
                Users users = new Users();
                users.setVisible(true);
                users.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Staffs":
                Staffs staffs = new Staffs();
                staffs.setVisible(true);
                staffs.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Sales":
                Sales sales = new Sales();
                sales.setVisible(true);
                sales.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Borrows":
                Borrowed borrowed = new Borrowed();
                borrowed.setVisible(true);
                borrowed.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;
            case "Publishers":
                // Already in Publishers page
                break;
            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Login login = new Login();
                    login.setVisible(true);
                    login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    login.setLocationRelativeTo(null);
                    this.dispose();
                }
                break;
        }
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel() {
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

        JLabel welcomeLabel = new JLabel("List of Publisher", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(Color.decode("#5012a3"));
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addButton.addActionListener(e -> openAddPublisherDialog());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(addButton);

        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    private void openAddPublisherDialog() {
        JTextField nameField = new JTextField();
        JTextField subnameField = new JTextField();
        JTextField addressField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("SubName:"));
        panel.add(subnameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Publisher", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO publishers (name, subName, Address, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, subnameField.getText());
                stmt.setString(3, addressField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Publisher added successfully!");
                loadPublishersFromDatabase();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding publisher.");
            }
        }
    }

    private void openEditDialog(int row) {
        String id = model.getValueAt(row, 0).toString();
        JTextField nameField = new JTextField(model.getValueAt(row, 1).toString());
        JTextField subnameField = new JTextField(model.getValueAt(row, 2).toString());
        JTextField addressField = new JTextField(model.getValueAt(row, 3).toString());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("SubName:"));
        panel.add(subnameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Publisher", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE publishers SET name=?, subName=?, Address=?, updated_at=NOW() WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, subnameField.getText());
                stmt.setString(3, addressField.getText());
                stmt.setString(4, id);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Publisher updated successfully!");
                loadPublishersFromDatabase();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating publisher.");
            }
        }
    }

    private void deletePublisher(int row) {
        String id = model.getValueAt(row, 0).toString();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM publishers WHERE id=?");
            stmt.setString(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Publisher deleted successfully!");
            loadPublishersFromDatabase();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting publisher.");
        }
    }

    private void loadPublishersFromDatabase() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, subName, Address FROM publishers")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= 4; i++) {
                    row.add(rs.getString(i));
                }
                row.add("Actions");
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            add(editButton);
            add(deleteButton);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(e -> {
                fireEditingStopped();
                openEditDialog(currentRow);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                int confirm = JOptionPane.showConfirmDialog(Publisher.this, "Are you sure you want to delete this publisher?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deletePublisher(currentRow);
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        public Object getCellEditorValue() {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Publisher().setVisible(true));
    }
}

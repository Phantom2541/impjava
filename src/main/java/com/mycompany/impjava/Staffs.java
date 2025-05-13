package com.mycompany.impjava;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.LinkedHashMap;

public class Staffs extends JFrame {
    private JPanel sidebar, contentArea;
    private boolean isSidebarVisible = true;
    private DefaultTableModel model;
    private JTable table;

    public Staffs() {
        setTitle("Staffs");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

        JPanel topBar = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3")));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topBar.setPreferredSize(new Dimension(getWidth(), 80));
        topBar.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("List of Staffs", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        JButton toggleButton = new JButton("☰");
        toggleButton.setFont(new Font("SansSerif", Font.BOLD, 28));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFocusPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(true);
        toggleButton.setBackground(new Color(80, 30, 120));
        toggleButton.setPreferredSize(new Dimension(60, 60));
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setToolTipText("Toggle Sidebar");
        toggleButton.addActionListener(e -> toggleSidebar());

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(toggleButton, BorderLayout.CENTER);
        topBar.add(buttonPanel, BorderLayout.WEST);

        JButton addStaffButton = new JButton("+");
        addStaffButton.setFont(new Font("Arial", Font.BOLD, 16));
        addStaffButton.setForeground(Color.WHITE);
        addStaffButton.setBackground(Color.decode("#5012a3"));
        addStaffButton.setFocusPainted(false);
        addStaffButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addStaffButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addStaffButton.addActionListener(e -> openAddDialog());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(addStaffButton);
        topBar.add(rightPanel, BorderLayout.EAST);

        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Position", "Return Date", "Actions"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Actions column
            }
        };

        table.setRowHeight(40);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                return c;
            }
        });

        table.getTableHeader().setBackground(new Color(80, 30, 120));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        contentArea.add(tablePanel, BorderLayout.CENTER);

        add(contentArea, BorderLayout.CENTER);
        loadStaffsFromDatabase();
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

        JLabel menuListLabel = new JLabel("Menu List");
        menuListLabel.setFont(new Font("Arial", Font.BOLD, 25));
        menuListLabel.setForeground(Color.WHITE);
        menuListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuListLabel);

        String[] buttons = {"Home", "Books", "Users", "Staffs", "Sales", "Borrows", "Publishers", "Logout"};
        for (String label : buttons) {
            panel.add(createSidebarButton(label));
        }

        panel.add(Box.createVerticalGlue());
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
            case "Home": new Dashboard().setVisible(true); break;
            case "Books": new Books().setVisible(true); break;
            case "Users": new Users().setVisible(true); break;
            case "Sales": new Sales().setVisible(true); break;
            case "Borrows": new Borrowed().setVisible(true); break;
            case "Publishers": new Publisher().setVisible(true); break;
            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new Login().setVisible(true);
                    this.dispose();
                }
                return;
            case "Staffs": break;
        }
        this.dispose();
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }

    private void openAddDialog() {
        try (Connection conn = DBConnection.getConnection()) {
            // Load users into a map
            Map<String, String> userMap = new LinkedHashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM users");
            while (rs.next()) {
                userMap.put(rs.getString("name"), rs.getString("id"));
            }

            // Create form components
            JComboBox<String> userBox = new JComboBox<>(userMap.keySet().toArray(new String[0]));
            JTextField positionField = new JTextField();
            JTextField returnDateField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            inputPanel.add(new JLabel("User:"));
            inputPanel.add(userBox);
            inputPanel.add(new JLabel("Position:"));
            inputPanel.add(positionField);
            inputPanel.add(new JLabel("Return Date:"));
            inputPanel.add(returnDateField);

            int option = JOptionPane.showConfirmDialog(this, inputPanel, "Add Staff", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String selectedUserId = userMap.get((String) userBox.getSelectedItem());

                String sql = "INSERT INTO staffs (userId, position, returnDate) VALUES (?, ?, ?)";
                PreparedStatement stmtInsert = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmtInsert.setString(1, selectedUserId);
                stmtInsert.setString(2, positionField.getText());
                stmtInsert.setString(3, returnDateField.getText());  // Add return date
                stmtInsert.executeUpdate();

                ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
                String id = generatedKeys.next() ? generatedKeys.getString(1) : "";

                // Add to table
                Vector<Object> row = new Vector<>();
                row.add(id);
                row.add(userBox.getSelectedItem());
                row.add(positionField.getText());
                row.add(returnDateField.getText());  // Add return date to table
                row.add("Actions");
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding staff.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStaffsFromDatabase() {
        model.setRowCount(0); // Clear table first
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("""
                                                  SELECT s.id, u.name, s.position, s.returnDate 
                                                  FROM staffs s JOIN users u ON s.userId = u.id
                                              """)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("position"));
                row.add(rs.getString("returnDate"));  // Add return date to the table
                row.add("Actions");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void openEditDialog(int row) {
        String id = model.getValueAt(row, 0).toString();
        String currentName = model.getValueAt(row, 1).toString();
        String currentPosition = model.getValueAt(row, 2).toString();
        String currentReturnDate = model.getValueAt(row, 3).toString();

        try (Connection conn = DBConnection.getConnection()) {
            // Load users into a map
            Map<String, String> userMap = new LinkedHashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM users");
            while (rs.next()) {
                userMap.put(rs.getString("name"), rs.getString("id"));
            }

            JComboBox<String> userBox = new JComboBox<>(userMap.keySet().toArray(new String[0]));
            userBox.setSelectedItem(currentName);

            JTextField positionField = new JTextField(currentPosition);
            JTextField returnDateField = new JTextField(currentReturnDate);

            JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            inputPanel.add(new JLabel("User:"));
            inputPanel.add(userBox);
            inputPanel.add(new JLabel("Position:"));
            inputPanel.add(positionField);
            inputPanel.add(new JLabel("Return Date:"));
            inputPanel.add(returnDateField);

            int option = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Staff", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String selectedUserId = userMap.get(userBox.getSelectedItem().toString());

                String sql = "UPDATE staffs SET userId = ?, position = ?, returnDate = ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(sql);
                updateStmt.setString(1, selectedUserId);
                updateStmt.setString(2, positionField.getText());
                updateStmt.setString(3, returnDateField.getText());
                updateStmt.setString(4, id);
                updateStmt.executeUpdate();

                // Update UI table
                model.setValueAt(userBox.getSelectedItem(), row, 1);
                model.setValueAt(positionField.getText(), row, 2);
                model.setValueAt(returnDateField.getText(), row, 3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating staff.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Staffs().setVisible(true));
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240)));
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");
        private int currentRow = -1;  // Default value for currentRow

        public ButtonEditor(JCheckBox checkBox) {
            panel.add(editButton);
            panel.add(deleteButton);

            // Edit Button Action
            editButton.addActionListener(e -> {
                fireEditingStopped();  // Stops editing
                openEditDialog(currentRow);  // Open the edit dialog for the staff at currentRow
            });

            // Delete Button Action
            deleteButton.addActionListener(e -> {
                fireEditingStopped();  // Stops editing
                int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure to delete this entry?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String id = model.getValueAt(currentRow, 0).toString();  // Get the ID of the selected row
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM staffs WHERE id = ?");
                        stmt.setString(1, id);
                        stmt.executeUpdate();
                        model.removeRow(currentRow);  // Remove from table
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }



        @Override
        public Object getCellEditorValue() {
            return null;  // No need to return any value
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return null;
        }
    }

}

package com.mycompany.impjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;

public class Borrowed extends JFrame {
    private JPanel sidebar, contentArea;
    private boolean isSidebarVisible = true;
    private DefaultTableModel model;
    private JTable table;

    public Borrowed() {
        setTitle("Borrowed Records");
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
                GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#42009F"), getWidth(), 0, Color.decode("#834AD3"));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topBar.setPreferredSize(new Dimension(getWidth(), 80));
        topBar.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("List of Borrowed Books", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);
        topBar.add(welcomeLabel, BorderLayout.CENTER);

        JButton toggleButton = new JButton("☰");
        toggleButton.setFont(new Font("SansSerif", Font.BOLD, 28));
        toggleButton.setForeground(Color.WHITE);
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
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

        JButton addBookButton = new JButton("+");
        addBookButton.setFont(new Font("Arial", Font.BOLD, 16));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setBackground(Color.decode("#5012a3"));
        addBookButton.setFocusPainted(false);
        addBookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBookButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBookButton.addActionListener(e -> openAddBorrowDialog(model));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(addBookButton);
        topBar.add(rightPanel, BorderLayout.EAST);

        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = {
                "ID", "Book ID", "User ID", "Approved ID", "Approved Date",
                "Borrowed Date", "Returned Date", "Status", "Fee", "Remarks", "Created At", "Updated At", "ACTIONS"
        };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 12; // Only ACTIONS column editable to have buttons
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
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        contentArea.add(tablePanel, BorderLayout.CENTER);
        add(contentArea, BorderLayout.CENTER);

        loadBorrowedsFromDatabase();
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
        menuListLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(menuListLabel);

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
            case "Home":
                Dashboard dashboard = new Dashboard();
                dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dashboard.setLocationRelativeTo(null);
                dashboard.setVisible(true);
                dashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Books":
                Books books = new Books();
                books.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                books.setLocationRelativeTo(null);
                books.setVisible(true);
                books.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Users":
                Users users = new Users();
                users.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                users.setLocationRelativeTo(null);
                users.setVisible(true);
                users.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Staffs":
                Staffs staffs = new Staffs();
                staffs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                staffs.setLocationRelativeTo(null);
                staffs.setVisible(true);
                staffs.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Sales":
                Sales sales = new Sales();
                sales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sales.setLocationRelativeTo(null);
                sales.setVisible(true);
                sales.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Borrows":
                // Already on borrow side
                break;

            case "Publishers":
                Publisher publisher= new Publisher();
                publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                publisher.setLocationRelativeTo(null);
                publisher.setVisible(true);
                publisher.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
                break;

            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Login login = new Login();
                    login.setVisible(true);
                    login.pack();
                    login.setLocationRelativeTo(null);
                    login.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    this.dispose();
                }
                break;
        }
        contentArea.revalidate();
        contentArea.repaint();
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }

    private void openAddBorrowDialog(DefaultTableModel model) {
        JTextField bookIdField = new JTextField();
        JTextField userIdField = new JTextField();
        JTextField approvedIdField = new JTextField();
        JTextField approvedDateField = new JTextField();
        JTextField borrowedDateField = new JTextField(LocalDate.now().toString());
        JTextField returnedDateField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField feeField = new JTextField();
        JTextField remarksField = new JTextField();

        approvedDateField.setToolTipText("Format: yyyy-mm-dd");
        borrowedDateField.setToolTipText("Format: yyyy-mm-dd");
        returnedDateField.setToolTipText("Format: yyyy-mm-dd");

        borrowedDateField.setEditable(false); // borrowed date default to today and not editable

        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JLabel("User ID:"));
        inputPanel.add(userIdField);
        inputPanel.add(new JLabel("Approved ID:"));
        inputPanel.add(approvedIdField);
        inputPanel.add(new JLabel("Approved Date (yyyy-mm-dd):"));
        inputPanel.add(approvedDateField);
        inputPanel.add(new JLabel("Borrowed Date (yyyy-mm-dd):"));
        inputPanel.add(borrowedDateField);
        inputPanel.add(new JLabel("Returned Date (yyyy-mm-dd):"));
        inputPanel.add(returnedDateField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusField);
        inputPanel.add(new JLabel("Fee:"));
        inputPanel.add(feeField);
        inputPanel.add(new JLabel("Remarks:"));
        inputPanel.add(remarksField);

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Add Borrow Record", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate borrowedDate = LocalDate.parse(borrowedDateField.getText(), formatter);
                LocalDate returnedDate = LocalDate.parse(returnedDateField.getText(), formatter);

                long daysBetween = ChronoUnit.DAYS.between(borrowedDate, returnedDate);

                if (daysBetween < 0) {
                    JOptionPane.showMessageDialog(this, "Returned date must be after borrowed date.", "Invalid Dates", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daysBetween > 365) {
                    JOptionPane.showMessageDialog(this, "The return date must be within 1 year of the borrowed date.", "Invalid Duration", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO borroweds (bookId, userId, approvedId, approvedDate, borrowedDate, returnedDate, status, fee, remarks, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
                    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    stmt.setString(1, bookIdField.getText().trim());
                    stmt.setString(2, userIdField.getText().trim());
                    stmt.setString(3, approvedIdField.getText().trim());
                    stmt.setString(4, approvedDateField.getText().trim());
                    stmt.setString(5, borrowedDateField.getText().trim());
                    stmt.setString(6, returnedDateField.getText().trim());
                    stmt.setString(7, statusField.getText().trim());
                    stmt.setString(8, feeField.getText().trim());
                    stmt.setString(9, remarksField.getText().trim());

                    stmt.executeUpdate();

                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        int newId = rs.getInt(1);
                        Object[] row = new Object[13];
                        row[0] = newId;
                        row[1] = bookIdField.getText().trim();
                        row[2] = userIdField.getText().trim();
                        row[3] = approvedIdField.getText().trim();
                        row[4] = approvedDateField.getText().trim();
                        row[5] = borrowedDateField.getText().trim();
                        row[6] = returnedDateField.getText().trim();
                        row[7] = statusField.getText().trim();
                        row[8] = feeField.getText().trim();
                        row[9] = remarksField.getText().trim();
                        row[10] = java.time.LocalDateTime.now().toString();
                        row[11] = java.time.LocalDateTime.now().toString();
                        row[12] = "Actions";
                        model.addRow(row);
                    }
                }

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Please enter dates in the correct format (yyyy-mm-dd).", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openEditDialog(int row) {
        JTextField idField = new JTextField(model.getValueAt(row, 0).toString());
        idField.setEditable(false);

        JTextField bookIdField = new JTextField(model.getValueAt(row, 1).toString());
        JTextField userIdField = new JTextField(model.getValueAt(row, 2).toString());
        JTextField approvedIdField = new JTextField(model.getValueAt(row, 3).toString());
        JTextField approvedDateField = new JTextField(model.getValueAt(row, 4).toString());
        JTextField borrowedDateField = new JTextField(model.getValueAt(row, 5).toString());
        JTextField returnedDateField = new JTextField(model.getValueAt(row, 6).toString());
        JTextField statusField = new JTextField(model.getValueAt(row, 7).toString());
        JTextField feeField = new JTextField(model.getValueAt(row, 8).toString());
        JTextField remarksField = new JTextField(model.getValueAt(row, 9).toString());

        approvedDateField.setToolTipText("Format: yyyy-mm-dd");
        borrowedDateField.setToolTipText("Format: yyyy-mm-dd");
        returnedDateField.setToolTipText("Format: yyyy-mm-dd");

        borrowedDateField.setEditable(false); // Make borrowed date uneditable

        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JLabel("User ID:"));
        inputPanel.add(userIdField);
        inputPanel.add(new JLabel("Approved ID:"));
        inputPanel.add(approvedIdField);
        inputPanel.add(new JLabel("Approved Date (yyyy-mm-dd):"));
        inputPanel.add(approvedDateField);
        inputPanel.add(new JLabel("Borrowed Date (yyyy-mm-dd):"));
        inputPanel.add(borrowedDateField);
        inputPanel.add(new JLabel("Returned Date (yyyy-mm-dd):"));
        inputPanel.add(returnedDateField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusField);
        inputPanel.add(new JLabel("Fee:"));
        inputPanel.add(feeField);
        inputPanel.add(new JLabel("Remarks:"));
        inputPanel.add(remarksField);

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Borrow Record", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Validate dates if non-empty
                LocalDate borrowedDate = LocalDate.parse(borrowedDateField.getText(), formatter);
                LocalDate returnedDate = LocalDate.parse(returnedDateField.getText(), formatter);

                long daysBetween = ChronoUnit.DAYS.between(borrowedDate, returnedDate);

                if (daysBetween < 0) {
                    JOptionPane.showMessageDialog(this, "Returned date must be after borrowed date.", "Invalid Dates", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (daysBetween > 365) {
                    JOptionPane.showMessageDialog(this, "Returned date must be within 1 year of borrowed date.", "Invalid Duration", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE borroweds SET bookId = ?, userId = ?, approvedId = ?, approvedDate = ?, borrowedDate = ?, returnedDate = ?, status = ?, fee = ?, remarks = ?, updated_at = NOW() WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    stmt.setString(1, bookIdField.getText().trim());
                    stmt.setString(2, userIdField.getText().trim());
                    stmt.setString(3, approvedIdField.getText().trim());
                    stmt.setString(4, approvedDateField.getText().trim());
                    stmt.setString(5, borrowedDateField.getText().trim());
                    stmt.setString(6, returnedDateField.getText().trim());
                    stmt.setString(7, statusField.getText().trim());
                    stmt.setString(8, feeField.getText().trim());
                    stmt.setString(9, remarksField.getText().trim());
                    stmt.setString(10, idField.getText().trim());

                    stmt.executeUpdate();

                    // Update model
                    model.setValueAt(bookIdField.getText().trim(), row, 1);
                    model.setValueAt(userIdField.getText().trim(), row, 2);
                    model.setValueAt(approvedIdField.getText().trim(), row, 3);
                    model.setValueAt(approvedDateField.getText().trim(), row, 4);
                    model.setValueAt(borrowedDateField.getText().trim(), row, 5);
                    model.setValueAt(returnedDateField.getText().trim(), row, 6);
                    model.setValueAt(statusField.getText().trim(), row, 7);
                    model.setValueAt(feeField.getText().trim(), row, 8);
                    model.setValueAt(remarksField.getText().trim(), row, 9);
                    model.setValueAt(model.getValueAt(row, 10), row, 10); // created_at unchanged
                    model.setValueAt(java.time.LocalDateTime.now().toString(), row, 11); // updated_at set to now
                }

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Please enter dates in the correct format (yyyy-mm-dd).", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            add(editButton);
            add(deleteButton);
            editButton.setFocusable(false);
            deleteButton.setFocusable(false);
            editButton.setPreferredSize(new Dimension(55, 25));
            deleteButton.setPreferredSize(new Dimension(55, 25));
            editButton.setFont(new Font("Arial", Font.BOLD, 10));
            deleteButton.setFont(new Font("Arial", Font.BOLD, 11));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton editButton = new JButton("Edit");
        private JButton deleteButton = new JButton("Del");
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.add(editButton);
            panel.add(deleteButton);
            editButton.setFocusable(false);
            deleteButton.setFocusable(false);
            editButton.setPreferredSize(new Dimension(55, 25));
            deleteButton.setPreferredSize(new Dimension(55, 25));
            editButton.setFont(new Font("Arial", Font.BOLD, 10));
            deleteButton.setFont(new Font("Arial", Font.BOLD, 11));

            editButton.addActionListener(e -> {
                fireEditingStopped();
                openEditDialog(currentRow);
            });

            deleteButton.addActionListener(e -> {
                fireEditingStopped();
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String sql = "DELETE FROM borroweds WHERE id=?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(model.getValueAt(currentRow, 0).toString()));
                        stmt.executeUpdate();
                        model.removeRow(currentRow);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private void loadBorrowedsFromDatabase() {
        model.setRowCount(0);

        String sql = """
        SELECT 
            id, bookId, userId, approvedId, approvedDate, borrowedDate, returnedDate, status, fee, remarks, created_at, updated_at
        FROM 
            borroweds
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("bookId"));
                row.add(rs.getString("userId"));
                row.add(rs.getString("approvedId"));
                row.add(rs.getString("approvedDate"));
                row.add(rs.getString("borrowedDate"));
                row.add(rs.getString("returnedDate"));
                row.add(rs.getString("status"));
                row.add(rs.getString("fee"));
                row.add(rs.getString("remarks"));
                row.add(rs.getString("created_at"));
                row.add(rs.getString("updated_at"));
                row.add("Actions");
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Borrowed().setVisible(true));
    }
}


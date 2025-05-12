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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Books extends JFrame {
    private JPanel sidebar, contentArea;
    private boolean isSidebarVisible = true;
    private DefaultTableModel model;
    private JTable table;

    public Books() {
        setTitle("Books");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);

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

        JLabel welcomeLabel = new JLabel("List of Books", JLabel.CENTER);
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
        addBookButton.addActionListener(e -> openAddBookDialog(model));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(addBookButton);
        topBar.add(rightPanel, BorderLayout.EAST);

        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = {"ID", "TITLE", "AUTHOR", "PUBLISHER", "COPYRIGHT", "LCN", "SECTION", "ISACTIVE", "ACTIONS"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 8;
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

        loadBooksFromDatabase(); // Load data from MySQL
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
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JLabel menuListLabel = new JLabel("Menu List");
        menuListLabel.setFont(new Font("Arial", Font.BOLD, 25));
        menuListLabel.setForeground(Color.WHITE);
        menuListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuListLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.add(menuListLabel);

        // Add Menu Items
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
                //already on books side ewe
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
                Borrowed borrowed= new Borrowed();
                borrowed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                borrowed.setLocationRelativeTo(null);
                borrowed.setVisible(true); 
                borrowed.setExtendedState(JFrame.MAXIMIZED_BOTH);
                this.dispose();
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

    private void openAddBookDialog(DefaultTableModel model) {
        try (Connection conn = DBConnection.getConnection()) {
            // Load publishers into a map
            Map<String, String> publisherMap = new LinkedHashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM publishers");
            while (rs.next()) {
                publisherMap.put(rs.getString("name"), rs.getString("id"));
            }

            // UI Components
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JComboBox<String> publisherBox = new JComboBox<>(publisherMap.keySet().toArray(new String[0]));
            JTextField copyrightField = new JTextField();
            JTextField lcnField = new JTextField();
            JTextField sectionField = new JTextField();
            JComboBox<String> isActiveBox = new JComboBox<>(new String[]{"true", "false"});

            // Validation
            copyrightField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    String text = copyrightField.getText();
                    copyrightField.setBackground(text.matches("\\d*") ? Color.WHITE : Color.PINK);
                }
            });

            // Layout
            JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            inputPanel.add(new JLabel("Title:")); inputPanel.add(titleField);
            inputPanel.add(new JLabel("Author:")); inputPanel.add(authorField);
            inputPanel.add(new JLabel("Publisher:")); inputPanel.add(publisherBox);
            inputPanel.add(new JLabel("Copyright Year:")); inputPanel.add(copyrightField);
            inputPanel.add(new JLabel("Library of Congress Number:")); inputPanel.add(lcnField);
            inputPanel.add(new JLabel("Section:")); inputPanel.add(sectionField);
            inputPanel.add(new JLabel("Is Active:")); inputPanel.add(isActiveBox);

            while (true) {
                int option = JOptionPane.showConfirmDialog(null, inputPanel, "Add Book", JOptionPane.OK_CANCEL_OPTION);
                if (option != JOptionPane.OK_OPTION) return;

                // Get input values
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String copyright = copyrightField.getText().trim();
                String lcn = lcnField.getText().trim();
                String section = sectionField.getText().trim();
                String publisherId = publisherMap.get((String) publisherBox.getSelectedItem());
                String isActive = isActiveBox.getSelectedItem().equals("true") ? "1" : "0";

                // Validate inputs
                if (title.isEmpty() || author.isEmpty() || copyright.isEmpty() ||
                        lcn.isEmpty() || section.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields must be filled.");
                    continue;
                }

                if (!copyright.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Copyright must be numeric.");
                    continue;
                }

                // Insert into DB
                String sql = "INSERT INTO books (title, author, publisherId, copyright, lcn, section, isActive) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmtInsert = conn.prepareStatement(sql);
                stmtInsert.setString(1, title);
                stmtInsert.setString(2, author);
                stmtInsert.setString(3, publisherId);
                stmtInsert.setString(4, copyright);
                stmtInsert.setString(5, lcn);
                stmtInsert.setString(6, section);
                stmtInsert.setString(7, isActive);
                stmtInsert.executeUpdate();

                // Add to table model
                model.addRow(new Object[]{
                        "", title, author, publisherBox.getSelectedItem(), copyright,
                        lcn, section, isActiveBox.getSelectedItem(), "Actions"
                });

                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add book: " + ex.getMessage());
        }
    }

    private void openEditDialog(int row) {
        try (Connection conn = DBConnection.getConnection()) {
            // Load publishers
            Map<String, String> publisherMap = new LinkedHashMap<>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM publishers");
            while (rs.next()) {
                publisherMap.put(rs.getString("name"), rs.getString("id"));
            }

            // UI Components
            JTextField idField = new JTextField(model.getValueAt(row, 0).toString());
            idField.setEditable(false);
            JTextField titleField = new JTextField(model.getValueAt(row, 1).toString());
            JTextField authorField = new JTextField(model.getValueAt(row, 2).toString());
            JComboBox<String> publisherBox = new JComboBox<>(publisherMap.keySet().toArray(new String[0]));
            JTextField copyrightField = new JTextField(model.getValueAt(row, 4).toString());
            JTextField lcnField = new JTextField(model.getValueAt(row, 5).toString());
            JTextField sectionField = new JTextField(model.getValueAt(row, 6).toString());
            JComboBox<String> isActiveBox = new JComboBox<>(new String[] { "true", "false" });
            isActiveBox.setSelectedItem(model.getValueAt(row, 7).toString());

            // Set current publisher
            String currentPublisherName = model.getValueAt(row, 3).toString();
            publisherBox.setSelectedItem(currentPublisherName);

            // Validation for numeric fields
            KeyAdapter numericValidator = new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    JTextField src = (JTextField) e.getSource();
                    src.setBackground(src.getText().matches("\\d*") ? Color.WHITE : Color.PINK);
                }
            };
            copyrightField.addKeyListener(numericValidator);
            lcnField.addKeyListener(numericValidator);

            // Build panel
            JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
            inputPanel.add(new JLabel("Title:")); inputPanel.add(titleField);
            inputPanel.add(new JLabel("Author:")); inputPanel.add(authorField);
            inputPanel.add(new JLabel("Publisher:")); inputPanel.add(publisherBox);
            inputPanel.add(new JLabel("Copyright:")); inputPanel.add(copyrightField);
            inputPanel.add(new JLabel("LCN:")); inputPanel.add(lcnField);
            inputPanel.add(new JLabel("Section:")); inputPanel.add(sectionField);
            inputPanel.add(new JLabel("Is Active:")); inputPanel.add(isActiveBox);

            int option = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Book", JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) return;

            // Validate inputs
            if (titleField.getText().trim().isEmpty() || authorField.getText().trim().isEmpty()
                    || copyrightField.getText().trim().isEmpty() || lcnField.getText().trim().isEmpty()
                    || sectionField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            if (!copyrightField.getText().trim().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Copyright must be numeric.");
                return;
            }

            // Update DB
            String sql = "UPDATE books SET title=?, author=?, publisherId=?, copyright=?, lcn=?, section=?, isActive=? WHERE id=?";
            PreparedStatement stmtUpdate = conn.prepareStatement(sql);
            stmtUpdate.setString(1, titleField.getText().trim());
            stmtUpdate.setString(2, authorField.getText().trim());
            stmtUpdate.setString(3, publisherMap.get(publisherBox.getSelectedItem()));
            stmtUpdate.setString(4, copyrightField.getText().trim());
            stmtUpdate.setString(5, lcnField.getText().trim());
            stmtUpdate.setString(6, sectionField.getText().trim());
            stmtUpdate.setString(7, isActiveBox.getSelectedItem().equals("true") ? "1" : "0");
            stmtUpdate.setString(8, idField.getText().trim());
            stmtUpdate.executeUpdate();

            // Update table model
            model.setValueAt(idField.getText(), row, 0);
            model.setValueAt(titleField.getText(), row, 1);
            model.setValueAt(authorField.getText(), row, 2);
            model.setValueAt(publisherBox.getSelectedItem(), row, 3);
            model.setValueAt(copyrightField.getText(), row, 4);
            model.setValueAt(lcnField.getText(), row, 5);
            model.setValueAt(sectionField.getText(), row, 6);
            model.setValueAt(isActiveBox.getSelectedItem(), row, 7);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while editing book: " + ex.getMessage());
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
        private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        private final JButton editButton = new JButton("Edit");
        private final JButton deleteButton = new JButton("Del");
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            panel.add(editButton);
            panel.add(deleteButton);
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
                int confirm = JOptionPane.showConfirmDialog(Books.this, "Are you sure to delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String id = model.getValueAt(currentRow, 0).toString();
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
                        stmt.setString(1, id);
                        stmt.executeUpdate();
                        model.removeRow(currentRow);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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

    private void loadBooksFromDatabase() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             // Join the 'books' table with the 'publishers' table to get the publisher's name
             ResultSet rs = stmt.executeQuery("SELECT b.id, b.title, b.author, p.name, b.copyright, b.lcn, b.section, b.isActive FROM books b JOIN publishers p ON b.publisherId = p.id")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= 7; i++) {
                    row.add(rs.getString(i));  // Add columns from 'books' table
                }

                // Hardcode the 'isActive' value as true
                row.add(true);  // Always add true for isActive column

                row.add("Actions");  // Add Actions column
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Books().setVisible(true));
    }
}

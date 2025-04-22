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

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
        addBookButton.addActionListener(e -> openAddBookDialog(model));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(addBookButton);
        topBar.add(rightPanel, BorderLayout.EAST);

        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = {"BorrowID", "BookID", "UserID", "BorrowedDate", "ReturnDate", "Fee", "ACTIONS"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 6;
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
              //already on borrow side ewe
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
        JTextField[] fields = new JTextField[6];
        String[] labels = {"BorrowID", "BookID", "UserID", "BorrowedDate", "ReturnDate", "Fee"};
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField();
            inputPanel.add(fields[i]);
        }

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Add Borrow Record", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO borroweds (borrowid, bookid, userid, borroweddate, returndate, fee) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                for (int i = 0; i < 6; i++) {
                    stmt.setString(i + 1, fields[i].getText());
                }
                stmt.executeUpdate();

                Object[] row = new Object[7];
                for (int i = 0; i < 6; i++) row[i] = fields[i].getText();
                row[6] = "Actions";
                model.addRow(row);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openEditDialog(int row) {
        JTextField[] fields = new JTextField[6];
        String[] labels = {"BorrowID", "BookID", "UserID", "BorrowedDate", "ReturnDate", "Fee"};
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));

        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField(model.getValueAt(row, i).toString());
            inputPanel.add(fields[i]);
        }

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Edit Borrow Record", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE borroweds SET bookid=?, userid=?, borroweddate=?, returndate=?, fee=? WHERE borrowid=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, fields[1].getText());
                stmt.setString(2, fields[2].getText());
                stmt.setString(3, fields[3].getText());
                stmt.setString(4, fields[4].getText());
                stmt.setString(5, fields[5].getText());
                stmt.setString(6, fields[0].getText());
                stmt.executeUpdate();

                for (int i = 0; i < 6; i++) model.setValueAt(fields[i].getText(), row, i);
            } catch (Exception ex) {
                ex.printStackTrace();
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
                int confirm = JOptionPane.showConfirmDialog(Borrowed.this, "Are you sure to delete this borrow record?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String id = model.getValueAt(currentRow, 0).toString();
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM borroweds WHERE borrowid = ?");
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

    private void loadBorrowedsFromDatabase() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT borrowid, bookid, userid, borroweddate, returndate, fee FROM borroweds")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= 6; i++) {
                    row.add(rs.getString(i));
                }
                row.add("Actions");
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Borrowed().setVisible(true));
    }
}

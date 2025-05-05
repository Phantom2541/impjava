package com.mycompany.impjava;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class Sales extends JFrame {
    private JPanel sidebar, contentArea;
    private boolean isSidebarVisible = true;
    private DefaultTableModel model;
    private JTable table;

    public Sales() {
        setTitle("Sales");
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

        JLabel welcomeLabel = new JLabel("List of Sales", JLabel.CENTER);
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

        JButton addSaleButton = new JButton("+");
        addSaleButton.setFont(new Font("Arial", Font.BOLD, 16));
        addSaleButton.setForeground(Color.WHITE);
        addSaleButton.setBackground(Color.decode("#5012a3"));
        addSaleButton.setFocusPainted(false);
        addSaleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addSaleButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addSaleButton.addActionListener(e -> openAddDialog());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.add(addSaleButton);
        topBar.add(rightPanel, BorderLayout.EAST);

        contentArea.add(topBar, BorderLayout.NORTH);

        String[] columnNames = { "ID", "Staff", "Customer", "Book", "Quantity", "Amount", "ACTIONS" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        table.setRowHeight(40);
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

        loadSalesFromDatabase();
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.decode("#4E10A4"), 0, getHeight(), Color.decode("#97579D")));
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

        String[] items = {"Home", "Books", "Users", "Staffs", "Sales", "Borrows", "Publishers", "Logout"};
        for (String item : items) panel.add(createSidebarButton(item));

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
                button.setOpaque(false);
            }
        });

        button.addActionListener(e -> handleNavigation(text));
        return button;
    }

    private void handleNavigation(String page) {
        JFrame target = switch (page) {
            case "Home" -> new Dashboard();
            case "Books" -> new Books();
            case "Users" -> new Users();
            case "Staffs" -> new Staffs();
            case "Borrows" -> new Borrowed();
            case "Publishers" -> new Publisher();
            case "Logout" -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) yield new Login();
                else yield null;
            }
            default -> null;
        };
        if (target != null && !page.equals("Sales")) {
            target.setVisible(true);
            target.setLocationRelativeTo(null);
            target.setExtendedState(JFrame.MAXIMIZED_BOTH);
            target.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        }
    }

    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }

    private void openAddDialog() {
        JTextField[] fields = new JTextField[5];
        String[] labels = {"StaffID", "UserID", "BookID", "Quantity", "Amount"};
        JPanel panel = new JPanel(new GridLayout(0, 1));

        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i] + ":"));
            fields[i] = new JTextField();
            panel.add(fields[i]);
        }

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Sale", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "INSERT INTO sales (staffId, userId, bookId, quantity, amount) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                for (int i = 0; i < fields.length; i++) {
                    stmt.setString(i + 1, fields[i].getText());
                }

                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                String generatedId = "";
                if (rs.next()) {
                    generatedId = rs.getString(1);
                }

                Vector<Object> row = new Vector<>();
                row.add(generatedId);
                for (JTextField f : fields) row.add(f.getText());
                row.add("Actions");
                model.addRow(row);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openEditDialog(int row) {
        JTextField[] fields = new JTextField[6];
        JPanel panel = new JPanel(new GridLayout(0, 1));

        for (int i = 0; i < 6; i++) {
            panel.add(new JLabel(model.getColumnName(i) + ":"));
            fields[i] = new JTextField(model.getValueAt(row, i).toString());
            panel.add(fields[i]);
        }

        int option = JOptionPane.showConfirmDialog(this, panel, "Edit Sale", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE sales SET staffId=?, userId=?, bookId=?, quantity=?, amount=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                for (int i = 1; i <= 5; i++) stmt.setString(i, fields[i].getText());
                stmt.setString(6, fields[0].getText());
                stmt.executeUpdate();

                for (int i = 0; i < 6; i++) model.setValueAt(fields[i].getText(), row, i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton edit = new JButton("Edit"), del = new JButton("Del");
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.add(edit);
            panel.add(del);
            return panel;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new FlowLayout());
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
                int confirm = JOptionPane.showConfirmDialog(Sales.this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DBConnection.getConnection()) {
                        String id = model.getValueAt(currentRow, 0).toString();
                        PreparedStatement stmt = conn.prepareStatement("DELETE FROM sales WHERE id = ?");
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

    private void loadSalesFromDatabase() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, staffId, userId, bookId, quantity, amount FROM sales")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= 6; i++) row.add(rs.getString(i));
                row.add("Actions");
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Sales().setVisible(true));
    }
}

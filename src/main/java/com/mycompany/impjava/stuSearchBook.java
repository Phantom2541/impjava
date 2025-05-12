package com.mycompany.impjava;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class stuSearchBook extends JFrame {
    private boolean isSidebarVisible = true;
    private JPanel sidebar;
    private JPanel mainPanel;
    private JComboBox<String> genreComboBox;
    private Map<String, JPanel> genrePanels = new HashMap<>();

    public stuSearchBook() {
        setTitle("Books by Genre");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        mainPanel = new JPanel(new GridBagLayout());

        JPanel topPanel = new JPanel();
        genreComboBox = new JComboBox<>();
        genreComboBox.setFont(new Font("SansSerif", Font.BOLD, 16));
        genreComboBox.setPreferredSize(new Dimension(200, 30));
        genreComboBox.addActionListener(e -> filterGenrePanels());

        topPanel.add(new JLabel("Select Genre: "));
        topPanel.add(genreComboBox);

        GridBagConstraints topGbc = new GridBagConstraints();
        topGbc.gridx = 0;
        topGbc.gridy = 0;
        topGbc.gridwidth = GridBagConstraints.REMAINDER;
        topGbc.insets = new Insets(20, 0, 20, 0);
        mainPanel.add(topPanel, topGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        loadGenresAndBooksFromDB(gbc);

        gbc.weighty = 1;
        mainPanel.add(Box.createVerticalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadGenresAndBooksFromDB(GridBagConstraints gbc) {
        java.util.List<String> genreList = new ArrayList<>();
        genreList.add("All");

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT genre FROM books")) {

            while (rs.next()) {
                String genre = rs.getString("genre");
                genreList.add(genre);
                addGenrePanelFromDB(genre, gbc);
            }

            genreComboBox.setModel(new DefaultComboBoxModel<>(genreList.toArray(new String[0])));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load genres.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addGenrePanelFromDB(String genre, GridBagConstraints gbc) {
        String query = "SELECT b.id, b.title, b.author, p.name AS publisher_name, b.copyright, b.isbn, b.genre\n" +
                "FROM books b\n" +
                "JOIN publishers p ON b.publisherid = p.id\n" +
                "WHERE b.genre = ?\n";
        java.util.List<String[]> bookRows = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, genre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookRows.add(new String[]{
                            rs.getString("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("publisher_name"),
                            rs.getString("copyright"),
                            rs.getString("isbn"),
                            rs.getString("genre")
                    });

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (!bookRows.isEmpty()) {
            String[][] data = bookRows.toArray(new String[0][]);
            JPanel panel = createCollapsiblePanel(genre, data);
            genrePanels.put(genre, panel);
            mainPanel.add(panel, gbc);
        }
    }

    private void filterGenrePanels() {
        String selected = (String) genreComboBox.getSelectedItem();
        for (Map.Entry<String, JPanel> entry : genrePanels.entrySet()) {
            entry.getValue().setVisible("All".equals(selected) || selected.equals(entry.getKey()));
        }
        mainPanel.revalidate();
        mainPanel.repaint();
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

    private JPanel createCollapsiblePanel(String genre, String[][] bookData) {
        JPanel wrapper = new JPanel(new BorderLayout());

        JButton toggleButton = new RoundedButton("▶ " + genre, 20);
        toggleButton.setPreferredSize(new Dimension(0, 45));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));
        buttonPanel.add(toggleButton, BorderLayout.CENTER);

        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setVisible(false);

        String[] columns = {"Book ID", "Title", "Author", "Publisher", "Copyright", "ISBN", "Genre", "Action"};
        JTable table = new JTable(new DefaultTableModel(bookData, columns));
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(22);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(80, 30, 120));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 15));
                label.setOpaque(true);
                label.setHorizontalAlignment(CENTER);
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
                return label;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(0, table.getRowHeight() * table.getRowCount()
                + table.getTableHeader().getPreferredSize().height));
        tableContainer.add(tableScrollPane, BorderLayout.CENTER);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));

        toggleButton.addActionListener(e -> {
            boolean show = !tableContainer.isVisible();
            tableContainer.setVisible(show);
            toggleButton.setText((show ? "▼ " : "▶ ") + genre);
            wrapper.revalidate();
            wrapper.repaint();
        });

        wrapper.add(buttonPanel, BorderLayout.NORTH);
        wrapper.add(tableContainer, BorderLayout.CENTER);
        return wrapper;
    }
    private JButton createGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();

                Color color1 = Color.decode("#4E10A4");
                Color color2 = Color.decode("#97579D");
                GradientPaint gp = new GradientPaint(0, 0, color1, width, 0, color2);

                g2.setPaint(gp);
                g2.fillRect(0, 0, width, height);
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }


    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Borrow");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String bookId;
        private JDialog dialog;
        private int selectedQuantity = 1;
        private int quantityLeft = 0;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Borrow");
            button.addActionListener(e -> showBorrowDialog());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            bookId = (String) table.getValueAt(row, 0);
            return button;
        }

        private void showBorrowDialog() {
            fetchQuantityLeftFromDB();

            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(button), "Borrow Book", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JLabel quantityLabel = new JLabel("Stocky Left: " + quantityLeft);
            JLabel selectedLabel = new JLabel("Borrowing: " + selectedQuantity);

            JButton plusButton = createGradientButton("+");
            JButton minusButton = createGradientButton("-");
            JButton confirmButton = createGradientButton("Confirm");

            plusButton.addActionListener(e -> {
                if (selectedQuantity < quantityLeft) {
                    selectedQuantity++;
                    selectedLabel.setText("Borrowing: " + selectedQuantity);
                }
            });

            minusButton.addActionListener(e -> {
                if (selectedQuantity > 1) {
                    selectedQuantity--;
                    selectedLabel.setText("Borrowing: " + selectedQuantity);
                }
            });

            confirmButton.addActionListener(e -> {
                if (selectedQuantity > quantityLeft) {
                    JOptionPane.showMessageDialog(dialog, "Not enough quantity available!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get today's date
                LocalDate today = LocalDate.now();
                String formattedDate = today.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

                try (Connection conn = DBConnection.getConnection()) {
                    conn.setAutoCommit(false);

                    try (PreparedStatement psUpdate = conn.prepareStatement(
                            "UPDATE books SET qnty = qnty - ? WHERE id = ? AND qnty >= ?")) {
                        psUpdate.setInt(1, selectedQuantity);
                        psUpdate.setString(2, bookId);
                        psUpdate.setInt(3, selectedQuantity);

                        int rowsUpdated = psUpdate.executeUpdate();

                        if (rowsUpdated > 0) {
                            conn.commit();
                            JOptionPane.showMessageDialog(dialog,
                                    "Successfully borrowed " + selectedQuantity + " book(s).\nDate: " + formattedDate,
                                    "Success", JOptionPane.INFORMATION_MESSAGE);
                            dialog.dispose();

                        } else {
                            conn.rollback();
                            JOptionPane.showMessageDialog(dialog, "Failed to borrow. Quantity may have changed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Database error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });


            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            dialog.add(quantityLabel, gbc);

            gbc.gridy++;
            dialog.add(selectedLabel, gbc);

            gbc.gridy++; gbc.gridwidth = 1;
            dialog.add(minusButton, gbc);
            gbc.gridx = 1;
            dialog.add(plusButton, gbc);

            gbc.gridx = 0; gbc.gridy++;
            gbc.gridwidth = 2;
            dialog.add(confirmButton, gbc);

            dialog.pack();
            dialog.setLocationRelativeTo(button);
            dialog.setPreferredSize(new Dimension(200, 250)); // Set dialog size
            dialog.pack();
            dialog.setLocationRelativeTo(button);
            dialog.setVisible(true);
        }

        private void fetchQuantityLeftFromDB() {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT qnty FROM books WHERE id = ?")) {
                ps.setString(1, bookId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        quantityLeft = rs.getInt("qnty");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                quantityLeft = 0;
            }
        }
    }




    class RoundedButton extends JButton {
        private final int radius;

        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 18));
            setMargin(new Insets(10, 20, 10, 20));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, Color.decode("#4E10A4"), getWidth(), 0, Color.decode("#97579D"));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.WHITE);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
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

        JLabel menuListLabel = new JLabel("Hi There!");
        menuListLabel.setFont(new Font("Arial", Font.BOLD, 25));
        menuListLabel.setForeground(Color.WHITE);
        menuListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(menuListLabel);

        JLabel menuListLabel2 = new JLabel("username");
        menuListLabel2.setFont(new Font("Arial", Font.BOLD, 15));
        menuListLabel2.setForeground(Color.WHITE);
        menuListLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuListLabel2.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20));
        panel.add(menuListLabel2);

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
        if ("Logout".equals(page)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }
    private void toggleSidebar() {
        isSidebarVisible = !isSidebarVisible;
        sidebar.setVisible(isSidebarVisible);
        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new stuSearchBook().setVisible(true));
    }
}

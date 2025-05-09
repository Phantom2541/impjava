package com.mycompany.impjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
        genreComboBox = new JComboBox<>(new String[]{"All", "Horror", "Romance", "Action", "Sci-Fi","Adventure"});
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

        //-------------------------------------------This PArt is the tables---------------------------------------------------------

        addGenrePanel("Horror", new String[][]{
            {"1", "Dracula", "9780141439846", "1897", "Bram Stoker", "Penguin"},
            {"2", "The Shining", "9780307743657", "1977", "Stephen King", "Anchor"},
            {"3", "fish", "9781501134534", "1986", "Stephen King", "Scriwerr"},
            {"23", "www", "9781523442970", "1453", "Stephen Keng", "Scrwww"},
            {"28", "lol", "9781234323470", "2353", "Stephen Kong", "Scriee33r"},
            {"12", "gyatt", "9781507464645", "3462", "Stephen Kung", "Scrwerer"},
        }, gbc);
        
        addGenrePanel("Romance", new String[][]{
            {"4", "Pride and Prejudice", "9780141040349", "1813", "Jane Austen", "Penguin"},
            {"5", "The Notebook", "9780446605236", "1996", "Nicholas Sparks", "Warner Books"},
            {"6", "Me Before You", "9780143124542", "2012", "Jojo Moyes", "Penguin"},
        }, gbc);
        
        addGenrePanel("Action", new String[][]{
            {"7", "Die Hard", "9781328745486", "1988", "Roderick Thorp", "Bonanza"},
            {"8", "Mad Max", "9780451454186", "1979", "Terry Hayes", "Futura"},
        }, gbc);
        
        addGenrePanel("Sci-Fi", new String[][]{
            {"9", "Dune", "9780441013593", "1965", "Frank Herbert", "Ace"},
            {"10", "Ender's Game", "9780812550702", "1985", "Orson Scott Card", "Tor Books"},
        }, gbc);
        
        addGenrePanel("Adventure", new String[][]{
            {"11", "Journey to the Center of the Earth", "9780451532150", "1864", "Jules Verne", "Signet Classics"},
            {"12", "The Hobbit", "9780547928227", "1937", "J.R.R. Tolkien", "Mariner"},
        }, gbc);
        
        gbc.weighty = 1;
        mainPanel.add(Box.createVerticalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addGenrePanel(String genre, String[][] data, GridBagConstraints gbc) {
        JPanel panel = createCollapsiblePanel(genre, data);
        genrePanels.put(genre, panel);
        mainPanel.add(panel, gbc);
    }

    private void filterGenrePanels() {
        String selected = (String) genreComboBox.getSelectedItem();
        for (Map.Entry<String, JPanel> entry : genrePanels.entrySet()) {
            entry.getValue().setVisible(selected.equals("All") || entry.getKey().equals(selected));
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

        String[] columns = {"Book ID", "Book Title", "ISBN", "Copyright", "Author", "Publisher"};
        JTable table = new JTable(new DefaultTableModel(bookData, columns));
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

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("implibraryLogo.png"));  // the image should on same directory with this class shet
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // Resize the image
            Image img = logoIcon.getImage(); 
            Image resizedImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Resize the image (adjust size as needed)
            logoIcon = new ImageIcon(resizedImg); // Create a new ImageIcon with the resized image
    
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, -30, 0)); // Add margin around the logo
            panel.add(logoLabel);
        } else {
            System.out.println("Logo image not found or couldn't be loaded!");
        }

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
                dispose(); // Or any logout logic
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
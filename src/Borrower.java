import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Borrower {
    private Library library;

    public Borrower(Library library) {
        this.library = library;
        borrowerMenu();
    }

    private void borrowerMenu() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Borrower Menu");

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel("images/library.jpeg");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon("images/nerd.png");
        Main.setImage(panel, gbc, originalIcon);

        JLabel welcomeLabel = new JLabel("Borrower Menu");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.setPreferredSize(new Dimension(150, 50));
        borrowBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
            }
        });

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setPreferredSize(new Dimension(150, 50));
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        JButton searchBookButton = new JButton("Search Book");
        searchBookButton.setPreferredSize(new Dimension(150, 50));
        searchBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(borrowBookButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(returnBookButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(searchBookButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(backButton, gbc);

        dialog.add(panel);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void borrowBook() {
        JList<String> bookList = library.getBookList();
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int option = JOptionPane.showConfirmDialog(null, new JScrollPane(bookList), "Select Book to Borrow",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                library.borrowBook(selectedBook.split(" by ")[0]);
            }
        }
    }

    private void returnBook() {
        JList<String> borrowedBookList = library.getBorrowedBookList();
        borrowedBookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int option = JOptionPane.showConfirmDialog(null, new JScrollPane(borrowedBookList), "Select Book to Return",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String selectedBook = borrowedBookList.getSelectedValue();
            if (selectedBook != null) {
                library.returnBook(selectedBook.split(" by ")[0]);
            }
        }
    }

    private void searchBook() {
        JDialog allBooksDialog = new JDialog();
        allBooksDialog.setTitle("Books Available");

        JList<String> bookList = library.getBookList();
        JScrollPane scrollPane = new JScrollPane(bookList);

        allBooksDialog.add(scrollPane);
        allBooksDialog.setSize(400, 300);
        allBooksDialog.setLocationRelativeTo(null);
        allBooksDialog.setModal(true);
        allBooksDialog.setVisible(true);
    }
}

class BackgroundPanels extends JPanel {
    private Image backgroundImage;

    public BackgroundPanels(String filePath) {
        try {
            backgroundImage = new ImageIcon(filePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

import javax.swing.*;
import java.awt.*;

public class Borrower {
    private final Library library;

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
        borrowBookButton.addActionListener(e -> borrowBook());

        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.setPreferredSize(new Dimension(150, 50));
        returnBookButton.addActionListener(e -> returnBook());

        JButton searchBookButton = new JButton("Search Book");
        searchBookButton.setPreferredSize(new Dimension(150, 50));
        searchBookButton.addActionListener(e -> searchBook());

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> dialog.dispose());

        Admin.setButtons(panel, gbc, borrowBookButton, returnBookButton, searchBookButton, backButton);

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

        Admin.setBookList(allBooksDialog, library);
    }
}


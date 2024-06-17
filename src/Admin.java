import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class Admin {
    private final Library library;
    private static final String ADMIN_PASSWORD = "password"; // Set your desired admin password here
    private final Consumer<Library> mainMenuCallback; // Reference to the main menu method

    public Admin(Library library, Consumer<Library> mainMenuCallback) {
        this.library = library;
        this.mainMenuCallback = mainMenuCallback;
        if (login()) {
            adminMenu();
        }
    }

    private boolean login() {
        JDialog loginDialog = new JDialog();
        loginDialog.setTitle("Admin Login");

        // Custom panel with background image
        BackgroundPanel loginPanel = new BackgroundPanel("images/library.jpeg");

        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginLabel = new JLabel("Enter Admin Password:");
        loginLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        loginLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(loginLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        loginPanel.add(loginButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(cancelButton, gbc);

        final boolean[] isLoggedIn = {false};

        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (ADMIN_PASSWORD.equals(password)) {
                isLoggedIn[0] = true;
                loginDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Incorrect password. Please try again.");
                passwordField.setText("");
            }
        });

        cancelButton.addActionListener(e -> {
            loginDialog.dispose();
            mainMenuCallback.accept(library); // Call the main menu method
        });

        loginDialog.add(loginPanel);
        loginDialog.setSize(400, 300); // Set desired size
        loginDialog.setLocationRelativeTo(null); // Center the dialog
        loginDialog.setModal(true);
        loginDialog.setVisible(true);

        return isLoggedIn[0]; // Return the actual login status
    }

    private void adminMenu() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Admin Menu");

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel("images/library.jpeg");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon("images/admin.png");
        Main.setImage(panel, gbc, originalIcon);

        JLabel welcomeLabel = new JLabel("Admin Menu");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.setPreferredSize(new Dimension(150, 50));
        addBookButton.addActionListener(e -> addBook());

        JButton removeBookButton = new JButton("Remove Book");
        removeBookButton.setPreferredSize(new Dimension(150, 50));
        removeBookButton.addActionListener(e -> removeBook());

        JButton viewBorrowedBooksButton = new JButton("View Borrowed Books");
        viewBorrowedBooksButton.setPreferredSize(new Dimension(150, 50));
        viewBorrowedBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBorrowedBooks();
            }
        });

        JButton viewAllBooksButton = new JButton("View All Books");
        viewAllBooksButton.setPreferredSize(new Dimension(150, 50));
        viewAllBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllBooks();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                mainMenuCallback.accept(library); // Call the main menu method
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(addBookButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(removeBookButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(viewBorrowedBooksButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(viewAllBooksButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the back button
        panel.add(backButton, gbc);

        dialog.add(panel);
        dialog.setSize(600, 500); // Set desired size
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void addBook() {
        String bookName = JOptionPane.showInputDialog("Enter Book Name:");
        String authorName = JOptionPane.showInputDialog("Enter Author Name:");
        library.addBook(new Book(bookName, authorName));
    }

    private void removeBook() {
        JList<String> bookList = library.getBookList();
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        int option = JOptionPane.showConfirmDialog(null, new JScrollPane(bookList), "Select Book to Remove",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                library.removeBook(selectedBook.split(" by ")[0]);
            }
        }
    }

    private void viewBorrowedBooks() {
        JDialog borrowedDialog = new JDialog();
        borrowedDialog.setTitle("Borrowed Books");

        JList<String> borrowedBookList = library.getBorrowedBookList();
        JScrollPane scrollPane = new JScrollPane(borrowedBookList);

        borrowedDialog.add(scrollPane);
        borrowedDialog.setSize(400, 300);
        borrowedDialog.setLocationRelativeTo(null);
        borrowedDialog.setModal(true);
        borrowedDialog.setVisible(true);
    }

    private void viewAllBooks() {
        JDialog allBooksDialog = new JDialog();
        allBooksDialog.setTitle("All Books");

        JList<String> bookList = library.getBookList();
        JScrollPane scrollPane = new JScrollPane(bookList);

        allBooksDialog.add(scrollPane);
        allBooksDialog.setSize(400, 300);
        allBooksDialog.setLocationRelativeTo(null);
        allBooksDialog.setModal(true);
        allBooksDialog.setVisible(true);
    }
}

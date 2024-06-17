import java.io.*;
import java.util.*;
import javax.swing.*;

public class Library {
    private final List<Book> books;
    private final List<Book> borrowedBooks;
    private static final String BOOKS_FILE = "books.txt";

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new ArrayList<>();
        loadBooks();
    }


    public void addBook(Book book) {
        books.add(book);
        saveBooks();
        JOptionPane.showMessageDialog(null, "Book added: " + book.getName());
    }

    public void removeBook(String bookName) {
        boolean removed = books.removeIf(book -> book.getName().equalsIgnoreCase(bookName));
        if (removed) {
            saveBooks();
            JOptionPane.showMessageDialog(null, "Book removed: " + bookName);
        } else {
            JOptionPane.showMessageDialog(null, "Book not found: " + bookName);
        }
    }

    public void borrowBook(String bookName) {
        for (Book book : books) {
            if (book.getName().equalsIgnoreCase(bookName)) {
                books.remove(book);
                borrowedBooks.add(book);
                saveBooks();
                JOptionPane.showMessageDialog(null, "Book borrowed: " + bookName);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not available");
    }

    public void returnBook(String bookName) {
        for (Book book : borrowedBooks) {
            if (book.getName().equalsIgnoreCase(bookName)) {
                borrowedBooks.remove(book);
                books.add(book);
                saveBooks();
                JOptionPane.showMessageDialog(null, "Book returned: " + bookName);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Book not found in borrowed list");
    }


    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    books.add(new Book(parts[0], parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            // File not found, creating a new file
            try {
                new File(BOOKS_FILE).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(book.getName() + "," + book.getAuthor());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JList<String> getBookList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Book book : books) {
            model.addElement(book.getName() + " by " + book.getAuthor());
        }
        return new JList<>(model);
    }

    public JList<String> getBorrowedBookList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Book book : borrowedBooks) {
            model.addElement(book.getName() + " by " + book.getAuthor());
        }
        return new JList<>(model);
    }
}

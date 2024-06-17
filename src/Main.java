import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        mainMenu(library);
    }

    public static void mainMenu(Library library) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Library Management System");

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel("images/library.jpeg");

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale the image
        ImageIcon originalIcon = new ImageIcon("images/book.jpeg");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        JLabel welcomeLabel = new JLabel("Welcome to Library Manager");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);

        JButton adminButton = new JButton("Admin");
        adminButton.setPreferredSize(new Dimension(150, 50));
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                new Admin(library, Main::mainMenu); // Pass reference to mainMenu method
            }
        });

        JButton borrowerButton = new JButton("Borrower");
        borrowerButton.setPreferredSize(new Dimension(150, 50));
        borrowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                new Borrower(library);
                mainMenu(library); // Return to main menu after Borrower operations
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(adminButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(borrowerButton, gbc);

        dialog.add(panel);
        dialog.setSize(400, 300); // Set desired size
        dialog.setLocationRelativeTo(null); // Center the dialog
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String filePath) {
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

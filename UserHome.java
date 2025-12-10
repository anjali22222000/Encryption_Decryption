import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserHome extends JFrame implements ActionListener {

    private JButton encryptBtn, decryptBtn, logoutBtn;
    private String username;

    public UserHome(String username) {
        this.username = username;

        setTitle("Welcome, " + username);
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Gradient background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(0, 191, 255);
                Color color2 = new Color(0, 128, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));

        // Heading
        JLabel heading = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 26));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel subHeading = new JLabel("File Encryption & Decryption Dashboard", SwingConstants.CENTER);
        subHeading.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subHeading.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(heading, BorderLayout.NORTH);
        titlePanel.add(subHeading, BorderLayout.CENTER);

        // Buttons
        encryptBtn = new JButton("Encrypt File");
        encryptBtn.setBackground(new Color(46, 204, 113));  // Green
        encryptBtn.setForeground(Color.WHITE);
        encryptBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        encryptBtn.setFocusPainted(false);
        encryptBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        encryptBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        decryptBtn = new JButton("Decrypt File");
        decryptBtn.setBackground(new Color(52, 152, 219));  // Blue
        decryptBtn.setForeground(Color.WHITE);
        decryptBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        decryptBtn.setFocusPainted(false);
        decryptBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        decryptBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(231, 76, 60));  // Red
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        btnPanel.add(encryptBtn);
        btnPanel.add(Box.createVerticalStrut(25));
        btnPanel.add(decryptBtn);
        btnPanel.add(Box.createVerticalStrut(25));
        btnPanel.add(logoutBtn);

        // Footer
        JLabel footer = new JLabel("© 2025 SecureFile Technologies", SwingConstants.CENTER);
        footer.setForeground(Color.WHITE);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add components
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(btnPanel, BorderLayout.CENTER);
        backgroundPanel.add(footer, BorderLayout.SOUTH);

        add(backgroundPanel);

        // Action listeners
        encryptBtn.addActionListener(this);
        decryptBtn.addActionListener(this);
        logoutBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == encryptBtn) {
            dispose();
            new EncryptFile(username).setVisible(true);
        } else if (e.getSource() == decryptBtn) {
            dispose();
            new DecryptFile(username).setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            JOptionPane.showMessageDialog(this, "Logged out successfully!");
            dispose();
            new Main().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new UserHome("hello").setVisible(true);
    }
}

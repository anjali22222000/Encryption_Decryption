import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    JButton loginBtn, registerBtn;

    public Main() {
        setTitle("File Encryption & Decryption Tool");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Gradient Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(58, 123, 213);
                Color color2 = new Color(0, 210, 255);
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Welcome to File Security Tool", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Center Card Panel
        JPanel cardPanel = new JPanel();
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Buttons
        loginBtn = createStyledButton("Login");
        registerBtn = createStyledButton("Register");

        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(loginBtn, gbc);

        gbc.gridy = 1;
        cardPanel.add(registerBtn, gbc);

        // Footer
        JLabel footer = new JLabel("© 2025 SecureFile Technologies", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(Color.WHITE);

        backgroundPanel.add(title, BorderLayout.NORTH);
        backgroundPanel.add(cardPanel, BorderLayout.CENTER);
        backgroundPanel.add(footer, BorderLayout.SOUTH);

        add(backgroundPanel);

        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
    }

    // Custom Rounded Button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2, true),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            dispose();
            new Login().setVisible(true);
        } else if (e.getSource() == registerBtn) {
            dispose();
            new Register().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class EncryptFile extends JFrame implements ActionListener {

    private JTextField fileField, keyField;
    private JButton browseBtn, encryptBtn, backBtn;
    private String username;

    public EncryptFile(String username) {
        this.username = username;

        // ===== Frame Settings =====
        setTitle("Encrypt File - " + username);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ===== Main Gradient Panel =====
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(66, 135, 245), getWidth(), getHeight(), new Color(23, 43, 122));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(20, 20));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== Title Label =====
        JLabel heading = new JLabel("File Encryption Tool", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        backgroundPanel.add(heading, BorderLayout.NORTH);

        // ===== Form Panel =====
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel fileLabel = new JLabel("Select File:");
        fileLabel.setForeground(Color.WHITE);
        fileLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        c.gridx = 0; c.gridy = 0;
        panel.add(fileLabel, c);

        fileField = new JTextField();
        fileField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        fileField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0;
        panel.add(fileField, c);

        browseBtn = createStyledButton("Browse");
        c.gridx = 2; c.gridy = 0; c.weightx = 0;
        panel.add(browseBtn, c);

        JLabel keyLabel = new JLabel("Enter Key (16 chars):");
        keyLabel.setForeground(Color.WHITE);
        keyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        c.gridx = 0; c.gridy = 1;
        panel.add(keyLabel, c);

        keyField = new JTextField();
        keyField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        keyField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        c.gridx = 1; c.gridy = 1; c.gridwidth = 2;
        panel.add(keyField, c);

        backgroundPanel.add(panel, BorderLayout.CENTER);

        // ===== Button Panel =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);

        encryptBtn = createStyledButton("Encrypt");
        backBtn = createStyledButton("Back");

        btnPanel.add(encryptBtn);
        btnPanel.add(backBtn);
        backgroundPanel.add(btnPanel, BorderLayout.SOUTH);

        add(backgroundPanel);

        // ===== Action Listeners =====
        browseBtn.addActionListener(this);
        encryptBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }

    // ===== Custom Styled Button =====
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 102, 204));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(110, 35));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Rounded corners + hover effect
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (btn.getModel().isPressed()) {
                    g2.setColor(new Color(0, 82, 164));
                } else if (btn.getModel().isRollover()) {
                    g2.setColor(new Color(30, 120, 230));
                } else {
                    g2.setColor(btn.getBackground());
                }
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textX = (c.getWidth() - fm.stringWidth(btn.getText())) / 2;
                int textY = (c.getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(btn.getText(), textX, textY);
                g2.dispose();
            }
        });

        return btn;
    }

    // ===== Action Handling =====
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseBtn) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                fileField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        } 
        else if (e.getSource() == encryptBtn) {
            String filePath = fileField.getText().trim();
            String key = keyField.getText().trim();

            if (filePath.isEmpty() || key.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a file and enter a key!");
                return;
            }
            if (key.length() != 16) {
                JOptionPane.showMessageDialog(this, "Key must be exactly 16 characters!");
                return;
            }

            try {
                File inputFile = new File(filePath);
                File encryptedFile = new File(inputFile.getParent(), "encrypted_" + inputFile.getName());
                encryptFile(key, inputFile, encryptedFile);
                JOptionPane.showMessageDialog(this, "✅ File encrypted successfully!\nSaved as: " + encryptedFile.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } 
        else if (e.getSource() == backBtn) {
            dispose();
            new UserHome(username).setVisible(true);
        }
    }

    // ===== Encryption Logic =====
    private static void encryptFile(String key, File inputFile, File outputFile) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }

    // ===== Main (for direct testing) =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EncryptFile("TestUser").setVisible(true));
    }
}

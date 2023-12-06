import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {
    private JButton home;
    private JButton home1;
    private JButton search;
    private JButton search1;
    private JButton hospital;
    private JButton hospital1;
    private JButton profile;
    private JButton profile1;
    private JButton logout;
    private JButton logout1;
    private JButton item;
    private JButton item1;
    private Item itemPanel;
    private Profile profilePanel;
    private MyPanel panel;
    private Home homePanel;
    private Hospital hospitalPanel;
    private JPanel currentPanel;
    
    Menu(String username) {
        this.setResizable(false);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(800, 600 * 8 / 5));
        this.setSize(800, 600 * 8 / 5);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MyPanel();
        panel.setLayout(null);

        JButton logo = createButton("images/Petdabang.jpg", 250, 10, 298, 65);
        JButton logoB = createButton("images/LogoB_button1.png", 13, 110, 43, 45);
        home = createButton("images/home_button1.png", 13, 230, 43, 30);
        home1 = createButton("images/home_button2.png", 13, 230, 43, 30);
        search = createButton("images/search_button1.png", 13, 310, 43, 30);
        search1 = createButton("images/search_button2.png", 13, 310, 43, 30);
        hospital = createButton("images/hospital_button1.png", 13, 390, 43, 40);
        hospital1 = createButton("images/hospital_button2.png", 13, 390, 43, 40);
        item = createButton("images/item_button1.png", 10, 470, 50, 50);
        item1 = createButton("images/item_button2.png", 10, 470, 50, 50);
        profile = createButton("images/profile_button1.png", 13, 700, 43, 50);
        profile1 = createButton("images/profile_button2.png", 13, 700, 43, 50);
        logout = createButton("images/logout_button1.png", 10, 790, 50, 50);
        logout1 = createButton("images/logout_button2.png", 10, 790, 50, 50);

        itemPanel = new Item();
        profilePanel = new Profile(username);
        homePanel = new Home();
        hospitalPanel = new Hospital();

        panel.add(logo);
        panel.add(logoB);
        panel.add(home);
        panel.add(search);
        panel.add(hospital);
        panel.add(item);
        panel.add(profile);
        panel.add(logout);

        this.add(panel, BorderLayout.CENTER);

        item.addActionListener(createItemActionListener(item, item1, panel, itemPanel));
        profile.addActionListener(createItemActionListener(profile, profile1, panel, profilePanel));
        home.addActionListener(createItemActionListener(home, home1, panel, homePanel));
        hospital.addActionListener(createItemActionListener(hospital, hospital1, panel, hospitalPanel));
        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                home.setIcon(new ImageIcon("images/home_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                home.setIcon(new ImageIcon("images/home_button1.png"));
            }
        });

        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                search.setIcon(new ImageIcon("images/search_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                search.setIcon(new ImageIcon("images/search_button1.png"));
            }
        });

        hospital.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hospital.setIcon(new ImageIcon("images/hospital_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hospital.setIcon(new ImageIcon("images/hospital_button1.png"));
            }
        });

        profile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profile.setIcon(new ImageIcon("images/profile_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                profile.setIcon(new ImageIcon("images/profile_button1.png"));
            }
        });

        logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logout.setIcon(new ImageIcon("images/logout_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logout.setIcon(new ImageIcon("images/logout_button1.png"));
            }
        });

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setIcon(new ImageIcon("images/item_button2.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                item.setIcon(new ImageIcon("images/item_button1.png"));
            }
        });
    }

    private ActionListener createItemActionListener(JButton button, JButton button1, MyPanel panel, JPanel contentPanel) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPanel != null) {
                    panel.remove(currentPanel);
                }

                panel.repaint();

                contentPanel.setBounds(76, 81, 800, 600 * 8 / 5);

                panel.add(contentPanel);
                currentPanel = contentPanel;

                panel.repaint();
            }
        };
    }

    private JButton createButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton(new ImageIcon(imagePath));
        button.setBounds(x, y, width, height);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    class MyPanel extends JPanel {
        MyPanel() {
            setBackground(Color.WHITE);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(75, 80, 75, 600 * 8 / 5);
            g.setColor(Color.GRAY);
            g.drawLine(0, 80, 800, 80);
        }
    }
}

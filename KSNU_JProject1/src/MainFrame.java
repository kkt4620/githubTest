import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Your Application Title");
        setSize(800, 600); // Set your preferred size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Home homePanel = new Home();
        getContentPane().add(homePanel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}

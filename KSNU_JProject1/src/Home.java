import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Home extends JPanel {
    private JScrollPane scrollPane;

    public Home() {
        setBackground(Color.white);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        try {
            // Oracle JDBC 드라이버를 로드합니다.
            Class.forName("oracle.jdbc.OracleDriver");

            // Oracle 데이터베이스에 연결합니다.
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "C##SUZI", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM if ORDER BY id");

            while (rs.next()) {
                JPanel itemPanel = new JPanel(new BorderLayout());
                itemPanel.setPreferredSize(new Dimension(500, 100));

                String title = rs.getString("Title");
                String imagePath = rs.getString("ImagePath");

                // 이미지를 Oracle 데이터베이스에서 어떻게 저장했느냐에 따라 로딩 방법을 조정해야 합니다.
                // ImagePath가 BLOB 또는 CLOB로 저장되어 있다고 가정하고 ImageIcon으로 변환해야 할 수 있습니다.
                // ImagePath가 파일 경로인 경우 이미지를 다르게 로드해야 할 수 있습니다.
                // ImageIcon imageIcon = new ImageIcon(imagePath);

                JLabel titleLabel = new JLabel(title);
                JLabel imageLabel = new JLabel(); // ImageIcon을 여기에 설정해야 합니다.

                itemPanel.add(titleLabel, BorderLayout.NORTH);
                itemPanel.add(imageLabel, BorderLayout.CENTER);

                contentPanel.add(itemPanel);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}

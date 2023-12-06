import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Profile extends JPanel {
    static final String driver = "oracle.jdbc.OracleDriver";
    static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    static final String id = "C##SUZI";
    static final String pw = "1234";

    private JTextArea username;
    private JTextArea username2;
    private JTextArea phone_number;
    private JTextArea phone_number1;
    private JTextArea email;
    private JTextArea email1;
    private JButton addinformation;
    private JButton image;
    private Profile profilePanel;
    String email2;
    String phone_number2;
    String image1;
   
    int x = 100;

    public Profile(String username1) {
        setBackground(Color.white);
        setLayout(null);

        // 각 요소를 생성하고 패널에 추가합니다.
        username = createTextArea("이름", x, 170, 100, 25);
        phone_number = createTextArea("전화번호", x, 200, 100, 25);
        email = createTextArea("이메일", x, 230, 100, 25);
        username2 = createTextArea(username1, x + 120, 170, 100, 25);

        add(username);
        add(phone_number);
        add(email);
        add(username2);

        try {
            // 데이터베이스에서 정보를 가져오는 메서드를 호출합니다.
            email2 = getDataEmail(username1);
            phone_number2 = getDataPhoneNumber(username1);
            image1 = getDataimage(username1);
        } catch (Exception e) {
            // 예외가 발생한 경우 적절한 처리를 수행합니다. (로깅, 에러 메시지 표시 등)
            e.printStackTrace();
        }
     // 이미지 버튼을 생성하고 패널에 추가합니다.
        image = imageButton(image1, x + 280, 130 + 10, 200, 200);
        add(image);
        
        // 가져온 정보를 텍스트 영역에 설정하고 패널에 추가합니다.
        email1 = createTextArea(email2, x + 120, 230, 120, 25);
        phone_number1 = createTextArea(phone_number2, x + 120, 200, 120, 25);

        add(email1);
        add(phone_number1);

        // 수정 버튼을 생성하고 패널에 추가합니다.
        addinformation = createButton("수정", x + 120, 330 + 10, 100, 40);
        //add(addinformation);

        

        // 이미지 버튼에 액션 리스너를 추가합니다.
        image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이미지 선택을 처리하는 메서드를 호출합니다.
                String newImagePath = handleImageSelection(username1);
                if(newImagePath != null) {
                    remove(image);
                    image = imageButton(newImagePath, x + 280, 130 + 10, 200, 200);
                    add(image);
                    repaint(); // 패널의 그래픽을 다시 그립니다.
                }
            }
        });
        
    }

    // 텍스트 영역을 생성하는 메서드
    private JTextArea createTextArea(String text1, int x, int y, int width, int height) {
        JTextArea area = new JTextArea();
        area.setBounds(x, y, width, height);
        area.setText(text1);
        return area;
    }
    private String getDataimage(String usernameToFind) throws Exception {
        try (Connection db = DriverManager.getConnection(url, id, pw)) {
            String sql = "SELECT profile_picture FROM user_info WHERE username = ?";
            try (PreparedStatement pstmt = db.prepareStatement(sql)) {
                pstmt.setString(1, usernameToFind);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("profile_picture");
                    }
                }
            }
        }
        return null;
    }
    // 데이터베이스에서 이메일 정보를 가져오는 메서드
    private String getDataEmail(String usernameToFind) throws Exception {
        try (Connection db = DriverManager.getConnection(url, id, pw)) {
            String sql = "SELECT phone_number FROM user_info WHERE username = ?";
            try (PreparedStatement pstmt = db.prepareStatement(sql)) {
                pstmt.setString(1, usernameToFind);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("phone_number");
                    }
                }
            }
        }
        return null;
    }

    // 데이터베이스에서 전화번호 정보를 가져오는 메서드
    private String getDataPhoneNumber(String usernameToFind) throws Exception {
        try (Connection db = DriverManager.getConnection(url, id, pw)) {
            String sql = "SELECT full_name FROM user_info WHERE username = ?";
            try (PreparedStatement pstmt = db.prepareStatement(sql)) {
                pstmt.setString(1, usernameToFind);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("full_name");
                    }
                }
            }
        }
        return null;
    }

    // 버튼을 생성하는 메서드
    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(true);
        return button;
    }

    // 이미지 버튼을 생성하는 메서드
    private JButton imageButton(String imagePath, int x, int y, int width, int height) {
        JButton button = new JButton(new ImageIcon(imagePath));
        button.setBounds(x, y, width, height);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    // 이미지 선택을 처리하는 메서드
    private String handleImageSelection(String username1) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        String imagePath =null;
        int result = fileChooser.showOpenDialog(Profile.this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // 이미지를 복사할 폴더의 경로를 설정합니다.
            String destinationPath = System.getProperty("user.dir") + "\\images";

            try {
                // 이미지를 복사합니다.
                copyFile(selectedFile, new File(destinationPath, selectedFile.getName()));

                // 복사한 이미지의 경로를 데이터베이스에 저장합니다.
                
                try {
                	imagePath ="images\\" + selectedFile.getName();
                	addImageData(username1, imagePath);
                }catch(Exception ex) {
                	ex.printStackTrace();
                }
                

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Profile.this, "파일 복사 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        return imagePath;
    }

    public void addImageData(String username, String imagePath) throws Exception {
        Class.forName(driver);
        try (Connection db = DriverManager.getConnection(url, id, pw)) {
            String sql = "UPDATE user_info SET profile_picture = ? WHERE username = ?";
            try (PreparedStatement pstmt = db.prepareStatement(sql)) {
                pstmt.setString(1, imagePath);
                pstmt.setString(2, username);
                pstmt.execute();
                
                db.close();
            }
        }
    }


    // 파일을 복사하는 메서드
    private void copyFile(File sourceFile, File destFile) throws IOException {
        try (FileInputStream sourceStream = new FileInputStream(sourceFile);
             FileOutputStream destStream = new FileOutputStream(destFile);
             FileChannel sourceChannel = sourceStream.getChannel();
             FileChannel destChannel = destStream.getChannel()) {

            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }
    }
    
}

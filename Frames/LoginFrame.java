package Frames;

/*
این کد یک فریم ورود به برنامه است که به کاربران اجازه می‌دهد شماره دانشجویی و رمز عبور خود را وارد کنند
و سپس توسط برنامه بررسی شود که این اطلاعات معتبر هستند یا خیر.

 برای بررسی صحت اطلاعات، برنامه از یک فایل متنی استفاده می‌کند که شماره دانشجویی و رمز عبور کاربران را در خود نگه می‌دارد.

  در صورتی که اطلاعات وارد شده توسط کاربر با اطلاعات موجود در فایل مطابقت داشته باشد، کاربر به صفحه اصلی برنامه برای سفارش غذا هدایت می‌شود.

   در غیر این صورت، پیام خطا به کاربر نمایش داده می‌شود و از کاربر خواسته می‌شود تا اطلاعات خود را بررسی کرده و مجدداً وارد کند
    یا در صورت لزوم، از طریق گزینه هایی که برای او نمایش داده می‌شود، اقدام به بازیابی رمز عبور یا ساخت حساب جدید کند.
 */


import Classes.Theme.CustomRendererLoginHistory;
import Classes.Theme.CustomRendererOrderCount;
import Classes.Theme.StyledButtonUI;
import Frames.Order.UniversitySelfRestaurant;
import Frames.Profile.ChangePasswordFrame;
import Frames.Profile.ForgotPassword;
import Frames.Profile.NewUserFrame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.*;
import static Classes.Theme.SoundEffect.*;
import static Frames.Admin.AdminWindow.OpenAdminWindow;


public class LoginFrame extends JFrame {

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        try {
            Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    private static  JTextField idField;
    private final JPasswordField passwordField;
    private final JLabel statusLabel;
    private final JCheckBox showPasswordCheckbox;
    private String name , lastName , gender , organization;
    public static Color colorButton = new Color(255,255,255);
    public static Color colorBackground = new Color(241,255,85);
    private String tempID;





    public LoginFrame() {
        setTitle("ورود");
        setIconImage(icon.getImage());
        setSize(375, 175);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(colorBackground);

        JLabel idLabel = new JLabel(": شماره دانشجویی");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idField = new JTextField(10);
        JLabel passwordLabel = new JLabel(": کلمـــه عبــور");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('\u25cf');
        showPasswordCheckbox = new JCheckBox("نمایش کلمه عبور");
        showPasswordCheckbox.setBackground(colorBackground);
        JButton loginButton = new JButton("ورود");
        loginButton.setUI(new StyledButtonUI());
        loginButton.setBackground(colorButton);
        statusLabel = new JLabel("");

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBackground(colorBackground);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(showPasswordCheckbox);
        panel.add(loginButton);
        panel.add(statusLabel);

        JButton helpButton = new JButton("اطلاعیه ها");
        panel.add(helpButton);

        helpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame subWindow = new JFrame();
                setIconImage(icon.getImage());
                subWindow.setTitle("");
                subWindow.setSize(300, 200);
                subWindow.setVisible(true);

                try {
                    FileReader fileReader = new FileReader(HelpPath);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String line;
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.equals("***")) {
                            panel.add(new JSeparator(JSeparator.HORIZONTAL));
                        } else {
                            JLabel label = new JLabel(line);
                            label.setFont(new Font("Arial", Font.BOLD, 20));
                            label.setForeground(Color.RED);
                            panel.add(label);
                        }
                    }
                    JScrollPane scrollPane = new JScrollPane(panel);
                    subWindow.add(scrollPane);
                    bufferedReader.close();
                } catch (IOException exception) {
                }

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.add(helpButton, BorderLayout.CENTER);
                panel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        subWindow.setVisible(true);
                    }
                });

                add(panel);


                FontMetrics fm = helpButton.getFontMetrics(helpButton.getFont());
                int textWidth = fm.stringWidth(helpButton.getText());
                int textHeight = fm.getHeight();
                helpButton.setPreferredSize(new Dimension(textWidth, textHeight));
            }
        });


        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorButton);
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("حساب کاربری");
        menu.setBackground(colorButton);
        menuBar.add(menu);
        JMenuItem adminItem = new JMenuItem("ورود مدیریت");
        adminItem.setBackground(colorButton);
        menu.add(adminItem);
        JMenuItem forgotItem = new JMenuItem("فراموشی رمز");
        forgotItem.setBackground(colorButton);
        menu.add(forgotItem);
        JMenuItem changePassword = new JMenuItem("تغییر رمز");
        changePassword.setBackground(colorButton);
        menu.add(changePassword);
        JMenuItem historyLoginButton = new JMenuItem("سابقه ورود");
        historyLoginButton.setBackground(colorButton);
        menu.add(historyLoginButton);
        JMenuItem newUserItem = new JMenuItem("جدید");
        newUserItem.setBackground(colorButton);
        menu.add(newUserItem);
        JMenuItem exitItem = new JMenuItem("خروج");
        exitItem.setBackground(colorButton);
        menuBar.add(exitItem);

        adminItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OpenAdminWindow();
                } catch (Exception ex) {
                }
            }
        });


        historyLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showLoginHistory();
                } catch (Exception ex) {
                }
            }
        });

        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ChangePasswordFrame();
            }
        });

        forgotItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ForgotPassword();
            }
        });

        newUserItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new NewUserFrame();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object[] options = {"خیر", "بله"};

                        int exitResult = JOptionPane.showOptionDialog(panel, "آیا خارج می شوید؟", "خروج",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);
                        if (exitResult == JOptionPane.NO_OPTION) dispose();
                    }
                });




        add(panel);

        loginButton.addActionListener(new LoginButtonListener());
        showPasswordCheckbox.addActionListener(new ShowPasswordCheckboxListener());


        setResizable(false);
        setVisible(true);
    }

    private boolean isValidLogin(String id, String password) {
        boolean isValid = false;

        if (id.length() == 0 ) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            statusLabel.setText("لطفا شماره دانشجویی را وارد نمایید");
            statusLabel.setForeground(Color.red);
            idField.setBackground(Color.YELLOW);
            idField.setForeground(Color.black);


        }else if (! isNumeric(id)  ) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            statusLabel.setText("شماره دانشجویی باید فقط شامل عدد باشد");
            statusLabel.setForeground(Color.red);
            idField.setBackground(Color.YELLOW);
            idField.setForeground(Color.black);


        } else if (!isNumeric(id) || id.length() != 10) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            statusLabel.setText("شماره دانشجویی باید 10 رقمی باشد");
            statusLabel.setForeground(Color.red);
            idField.setBackground(Color.YELLOW);
            idField.setForeground(Color.black);

        } else if (password.length() == 0 ){
            try {
                errorSound();
            } catch (Exception ex) {

            }
            statusLabel.setText("لطفا کلمه عبور را وارد نمایید");
            statusLabel.setForeground(Color.red);
            passwordField.setBackground(Color.YELLOW);
            passwordField.setForeground(Color.black);


        } else if ((password.length() < 8 )||(password.length() > 16) ) {
            try {
                errorSound();
            } catch (Exception ex) {

            }
            statusLabel.setText("کلمه عبور باید بین 8 تا 16 کاراکتر باشد");
            statusLabel.setForeground(Color.red);
            passwordField.setBackground(Color.YELLOW);
            passwordField.setForeground(Color.black);

        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(UserPassPath));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    tempID = parts[0] ;
                    if (tempID.equals(id) && parts[1].equals(password)) {
                        name = parts[2];
                        lastName = parts[3];
                        gender = parts[4];
                        organization = parts[6];
                        isValid = true;
                        break;
                    }
                }
                reader.close();
            } catch (IOException e) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }            }
            if (!isValid) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                writeLog("ناموفق");

                statusLabel.setText("شماره دانشجویی یا کلمه عبور اشتباه است");
                statusLabel.setForeground(Color.red);
                Object[] options = {  "خیر", "بله","ساخت حساب کاربری جدید"};

                int noFoundUser = JOptionPane.showOptionDialog(this,"کاربری با این اطلاعات یافت نشد.\nآیا مایل هستید رمز خود را بازیابی کنید؟" , "خروج",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                        null , options , options[0]);
                if (noFoundUser == JOptionPane.NO_OPTION){
                    dispose();
                    new ForgotPassword();
                } else if (noFoundUser == JOptionPane.CANCEL_OPTION){
                    dispose();
                    NewUserFrame frame = new NewUserFrame();
                    frame.setVisible(true);
                }
                idField.setBackground(Color.RED);
                idField.setForeground(Color.WHITE);
                passwordField.setBackground(Color.RED);
                passwordField.setForeground(Color.WHITE);

            }
        }

        return isValid;
    }

    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e)   {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            statusLabel.setForeground(Color.black);
            idField.setBackground(Color.white);
            idField.setForeground(Color.black);
            passwordField.setBackground(Color.white);
            passwordField.setForeground(Color.black);

            if (isValidLogin(id, password)) {
                writeLog("موفق");
                if (gender.equals("MALE")) {
                    try {
                        wellcomeSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null , "آقای " + name + " " + lastName + " خوش آمدید!");
                    dispose();
                    try {
                        new UniversitySelfRestaurant(id, name + " " + lastName , organization);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    try {
                        wellcomeSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null , "خانم " + name + " " + lastName + " خوش آمدید!");
                    dispose();
                    UniversitySelfRestaurant frame = null;
                    try {
                        frame = new UniversitySelfRestaurant(id, name + " " + lastName , organization);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.setVisible(true);
                }
            }
        }
    }

    private class ShowPasswordCheckboxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (showPasswordCheckbox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u25cf');
            }
        }
    }


    public static void writeLog(String message) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateTime = new Date();
        String datetime = dateTime.toString();

        try {
            FileWriter writer = new FileWriter(LogPath, true);
            writer.write(idField.getText() + " , " + datetime + " , " + message + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginHistory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String input = (JOptionPane.showInputDialog(null , "لطفا کد دانشجویی خود را وارد کنید"));
        boolean isFind = false;


        String[] columnNames = {" تاریخ ", " نتیجه ورود "};
        ArrayList<Object[]> dataList = new ArrayList<>();

        Scanner scanner;
        try {
            scanner = new Scanner(new File(LogPath));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(" , ");

                if (fields[0].equals(input)) {
                    String datetime = fields[1];
                    String result = fields[2];
                    dataList.add(new Object[]{datetime, result});
                    isFind = true;

                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            errorSound();
            JOptionPane.showMessageDialog(null , " خطا در خواندن فایل " + e.getMessage() , "خطا !" , JOptionPane.ERROR_MESSAGE);
        }

        if (isFind) {
            Object[][] data = new Object[dataList.size()][];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }

            JTable table = new JTable(data, columnNames);
            table.setBackground(colorBackground);
            table.setDefaultRenderer(Object.class, new CustomRendererLoginHistory());
            table.getTableHeader().setReorderingAllowed(false);
            JScrollPane scrollPane = new JScrollPane(table);

            JFrame frame = new JFrame();
            frame.setBackground(colorBackground);
            frame.setTitle(" تاریخچه ورود کاربر " + input);
            frame.add(scrollPane);
            scrollPane.setPreferredSize(new Dimension(500, table.getPreferredSize().height + 50));
            frame.setResizable(false);
            frame.pack();
            frame.setVisible(true);
        } else {
            errorSound();
            JOptionPane.showMessageDialog(null ,  "شما سابقه ورود ندارید!" , "توجه!" , JOptionPane.ERROR_MESSAGE);
        }
    }

}

//   1402/03/15 15:00 p.m.

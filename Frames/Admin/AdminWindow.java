package Frames.Admin;

/*
 در این پنجره، سه تب برای مدیریت حساب کاربری، غذا و مالی وجود دارد. هر تب شامل چند دکمه برای مدیریت موارد مختلف است.

برای ورود به پنجره مدیریت، ابتدا از کاربر خواسته می‌شود که نام کاربری و رمز عبور خود را وارد کند.
 سپس با استفاده از متد `checkLogin`، مطابقت نام کاربری و رمز عبور با فایل `admin.txt` بررسی می‌شود.
  اگر نام کاربری و رمز عبور با یکی از خطوط فایل مطابقت داشته باشد، پنجره مدیریت با استفاده از کلاس `AdminWindow` ایجاد می‌شود
   و در نتیجه پنجره ورود به سیستم بسته می‌شود. در غیر این صورت، پیام خطا نمایش داده می‌شود.

   `````````````````````````````````````````````````````

It is a graphical user interface (GUI) application that implements an admin window with various tabs for different management tasks. Here is a breakdown of the code:

1. The code imports several classes and libraries, including Swing components (`JFrame`, `JPanel`, `JButton`, etc.), exception classes, and `Scanner` for file input.

2. The `AdminWindow` class extends the `JFrame` class, indicating that it represents a window frame.

3. The constructor of the `AdminWindow` class initializes the window and creates a `JTabbedPane` to contain the different tabs.

4. The code defines several panels (`panel1`, `panel2`, `panel3`, etc.) for each tab, each using a `GridBagLayout` manager.

5. For each tab, there are buttons representing different management tasks. Each button has an action listener that performs a specific action when clicked.

6. The `checkLogin` method checks the provided username and password against a file containing admin credentials. If the credentials match, the method returns true; otherwise, it returns false.

7. The `createImageIcon` method creates an `ImageIcon` object from an image file path.

8. The `OpenAdminWindow` method is a static method that displays a login dialog for the admin. If the login is successful, it plays a welcome sound and creates an instance of the `AdminWindow` class.

Overall, this code represents an admin window with tabs for different management tasks. The functionality of each button is not provided in the code snippet, but it can be implemented in separate classes for each task.

 */

import Frames.Admin.FinancialManagment.BalanceEditor;
import Frames.Admin.FinancialManagment.ChargeHistory;
import Frames.Admin.FinancialManagment.DiscoutManage;
import Frames.Admin.FoodManagment.EditFood;
import Frames.Admin.FoodManagment.OrderCounter;
import Frames.Admin.FoodManagment.OrdersManage;
import Frames.Admin.Managment.ColorChangeMenu;
import Frames.Admin.Managment.DataBaseManagment;
import Frames.Admin.NotificationManagment.Notification;
import Frames.Admin.ProfileManagment.LogHistory;
import Frames.Admin.ProfileManagment.ShowDeletedUsers;
import Frames.Admin.ProfileManagment.UserEditor;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.*;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.wellcomeSound;

public class AdminWindow extends JFrame {

    public AdminWindow() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel(new GridBagLayout());
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }

        JButton userEditorButton = new JButton("حساب های کاربری");
        userEditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new UserEditor(UserPassPath);
                } catch (Exception ex) {
                }
            }
        });

        JButton logHistoryButton = new JButton("سابقه ورود");
        logHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new LogHistory();
                } catch (Exception exception) {
                }
            }
        });

        JButton showDeletedUsersButton = new JButton("کاربران حذف شده");
        showDeletedUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new ShowDeletedUsers();
                }  catch (Exception exception) {
                }
            }
        });

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        panel1.add(userEditorButton, c);
        c.gridx = 1;
        c.gridy = 0;
        panel1.add(logHistoryButton, c);
        c.gridx = 2;
        c.gridy = 0;
        panel1.add(showDeletedUsersButton, c);
        tabbedPane.addTab("حساب کاربری", panel1);

        JPanel panel2 = new JPanel(new GridBagLayout());

        JButton ordersManageButton = new JButton("سفارشات");
        ordersManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new OrdersManage();
                } catch (Exception ex) {
                }
            }
        });

        JButton editFoodButton = new JButton("غذاها");
        editFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new EditFood();
                } catch (Exception ex) {
                }
            }
        });

        JButton foodCountButton = new JButton("آمار سفارشات");
        foodCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new OrderCounter();
                } catch (Exception ex) {
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        panel2.add(ordersManageButton, c);
        c.gridx = 1;
        c.gridy = 0;
        panel2.add(editFoodButton, c);
        c.gridx = 2;
        c.gridy = 0;
        panel2.add(foodCountButton, c);
        tabbedPane.addTab("غذا", panel2);

        JPanel panel3 = new JPanel(new GridBagLayout());

        JButton balanceEditorButton = new JButton("موجودی ها");
        balanceEditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new BalanceEditor();
                } catch (Exception ex) {
                }
            }
        });

        JButton chargeHistory = new JButton("سابقه شارژ");
        chargeHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new ChargeHistory();
                } catch (Exception ex) {

                }
            }
        });

        JButton discountManageButton = new JButton("تخفیفات");
        discountManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new DiscoutManage();
                } catch (Exception ex) {
                }
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        panel3.add(balanceEditorButton, c);
        c.gridx = 1;
        c.gridy = 0;
        panel3.add(chargeHistory, c);
        c.gridx = 2;
        c.gridy = 0;
        panel3.add(discountManageButton, c);
        tabbedPane.addTab("مالی", panel3);


        JPanel panel4 = new JPanel(new GridBagLayout());

        JButton filesManagment = new JButton("پایگاه های داده");
        filesManagment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new DataBaseManagment();
                } catch (Exception ex) {
                }
            }
        });


        c.gridx = 0;
        c.gridy = 0;
        panel4.add(filesManagment, c);

        JButton colorManagment = new JButton("رنگ صفحات");
        colorManagment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new ColorChangeMenu();
                } catch (Exception ex) {
                }
            }
        });


        c.gridx = 1;
        c.gridy = 0;
        panel4.add(colorManagment, c);

        tabbedPane.addTab("تنظیمات", panel4);


        JPanel panel5 = new JPanel(new GridBagLayout());

        JButton help = new JButton("اطلاعیه ها");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new Notification();
                } catch (Exception ex) {
                }
            }
        });


        c.gridx = 0;
        c.gridy = 0;
        panel5.add(help, c);

        tabbedPane.addTab("اطلاعیه ها", panel5);





        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menuBar.add(menu);
        JMenuItem refreshButton = new JMenuItem("تازه سازی");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    new EditFood();
                } catch (Exception ex) {
                }
            }
        });
        menu.add(refreshButton);


        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setIconImage(icon.getImage());
        frame.setLocationRelativeTo(null);
        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("مدیریت");
        frame.setVisible(true);
    }
    public static void OpenAdminWindow() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JLabel label1 = new JLabel(" نام کاربری ");
        JTextField textField1 = new JTextField();
        JLabel label2 = new JLabel(" رمز عبور ");
        JTextField textField2 = new JTextField();

        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);

        ImageIcon icon = createImageIcon( " مدیریت ");

        int result = JOptionPane.showConfirmDialog(null, panel, " ورود مدیریت ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
        if (result == JOptionPane.OK_OPTION) {
            String username = textField1.getText();
            String password = textField2.getText();

            if (checkLogin(username, password)) {
                wellcomeSound();
                new AdminWindow();
            } else {
                JOptionPane.showMessageDialog(null, "اطلاعات وارد شده اشتباه است.");
                errorSound();
            }
        }
}

    public static boolean checkLogin(String username, String password) {
        try {
            File file = new File(AdminsPath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static ImageIcon createImageIcon( String description) {
        Image image = new ImageIcon(iconPath).getImage();
        Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage, description);
    }
}

//   1402/03/29 03:39 a.m. ~ 19 - 6 - 2023
package Frames.Profile;

/*
این کد یک کلاس به نام `ChangePasswordFrame` را شامل می‌شود که یک پنجره برای تغییر رمز عبور کاربر ارائه می‌دهد.

در این کلاس، چند ورودی `JTextField` و `JPasswordField` برای ورود شناسه،
 رمز عبور قبلی، رمز عبور جدید و تکرار رمز عبور جدید ایجاد می‌شود که توسط کاربر پر می‌شوند.

پس از زدن دکمه "تغییر رمز عبور"، ورودی‌ها بررسی می‌شوند و در صورتی که هرکدام از فیلدها خالی باشند،
 یا رمز عبور جدید و تکرار آن با هم مطابقت نداشته باشند، یک پیغام خطا نمایش داده می‌شود و عمل تغییر رمز عبور انجام نمی‌شود.

در صورتی که همه ورودی‌ها به درستی پر شده باشند، فایل حاوی اطلاعات کاربران خوانده می‌شود و شناسه و رمز عبور قبلی ورودی با اطلاعات موجود در فایل مقایسه می‌شوند.

در صورتی که شناسه وجود نداشته باشد، یک پیغام خطا نمایش داده می‌شود و عمل تغییر رمز عبور انجام نمی‌شود.

در صورتی که شناسه وجود داشته باشد، اگر رمز عبور قبلی اشتباه باشد، یک پیغام خطا نمایش داده می‌شود و عمل تغییر رمز عبور انجام نمی‌شود.

در صورتی که هر دو شناسه و رمز عبور درست باشند، ردیف مربوط به کاربر با شناسه وارد شده در فایل به روز رسانی می‌شود و رمز عبور جدید در فایل ذخیره می‌شود.
 سپس یک پیغام موفقیت‌آمیز برای کاربر نمایش داده می‌شود و کاربر به صفحه ورود منتقل می‌شود.


   `````````````````````````````````````````````````````

It extends the `JFrame` class and represents a graphical user interface for changing a password. Here is a breakdown of the code:

1. The class imports various classes and libraries for GUI components, event handling, and file operations.

2. The class defines several instance variables, including text fields for entering the user ID, old password, new password, and confirm password. It also defines colors for the button and background.

3. The constructor of the class initializes the frame, sets its properties (title, icon, size, etc.), and creates the necessary GUI components (labels, text fields, button, etc.) using Swing.

4. The constructor sets up the menu bar, which contains an "Exit" item that allows the user to return to the main page.

5. The constructor adds the GUI components to a panel and sets the panel as the content pane of the frame.

6. The constructor sets the frame as visible and adds an action listener to the change password button.

7. The action listener retrieves the values entered in the text fields, performs validation checks, and then attempts to read the user password file (`UserPassPath`).

8. If the file is successfully read, the action listener searches for a matching user ID and verifies the old password. If the ID and old password are correct, the listener updates the file with the new password and displays a success message. Otherwise, it displays appropriate error messages.

9. If there are any exceptions during the file operations or sound effects, error messages are displayed.

Overall, this code provides a GUI for changing passwords, validates user input, and updates the password file accordingly.
 */

import Classes.Theme.StyledButtonUI;
import Frames.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.successSound;
import static Classes.Pathes.FilesPath.UserPassPath;

public class ChangePasswordFrame extends JFrame {

    private final JTextField idField;
    private final JTextField oldPasswordField;
    private final JPasswordField  newPasswordField;
    private final JPasswordField confirmPasswordField;
    public static Color colorButton = new Color(165,11,94);
    public static Color colorBackground = new Color(211,77,210);

    public ChangePasswordFrame() {
        setBackground(colorBackground);
        setTitle("تغییر رمز عبور");
        setIconImage(icon.getImage());
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel idLabel = new JLabel(": شناسه");
        idLabel.setForeground(Color.white);
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idField = new JTextField(10);
        JLabel oldPasswordLabel = new JLabel(": رمز عبور قبلی");
        oldPasswordLabel.setForeground(Color.white);
        oldPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        oldPasswordField = new JTextField(10);
        JLabel newPasswordLabel = new JLabel(": رمز عبورجدید");
        newPasswordLabel.setForeground(Color.white);
        newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newPasswordField = new JPasswordField(10);
        JLabel confirmPasswordLabel = new JLabel(": تکرار رمز عبور جدید");
        confirmPasswordLabel.setForeground(Color.white);
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPasswordField = new JPasswordField(10);

        JButton changePasswordButton = new JButton("تغییر رمز عبور");
        changePasswordButton.setUI( new StyledButtonUI());
        changePasswordButton.setBackground(colorButton);
        changePasswordButton.setForeground(Color.white);

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBackground(colorBackground);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(oldPasswordLabel);
        panel.add(oldPasswordField);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(changePasswordButton);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorButton);
        menuBar.setForeground(Color.white);
        setJMenuBar(menuBar);
        JMenuItem exitItem = new JMenuItem("بازگشت");
        exitItem.setBackground(colorButton);
        exitItem.setForeground(Color.white);
        menuBar.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"خیر", "بله"};

                int exitResult = JOptionPane.showOptionDialog(null, "آیا از ادامه مراحل انصراف می دهید؟", "بازگشت به صفحه اصلی",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (exitResult == JOptionPane.NO_OPTION) {
                    dispose();
                    new LoginFrame();
                }
            }
        });

        add(panel);
        setVisible(true);

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String oldPassword = oldPasswordField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (id.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "لطفاً تمامی فیلدها را پر کنید.", "خطا", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "رمز عبور جدید و تکرار آن با هم مطابقت ندارند.", "خطا", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(UserPassPath))) {
                    String line;
                    boolean idExists = false;
                    StringBuilder fileContent = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        String storedId = parts[0];
                        String password = parts[1];
                        String name = parts[2];
                        String lastName = parts[3];
                        String gender = parts[4];
                        String number = parts[5];
                        String organization = parts[6];

                        if (id.equals(storedId)) {
                            idExists = true;
                            if (!oldPassword.equals(password)) {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "رمز عبور قبلی اشتباه است.", "خطا", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else {
                                line = id + "," + newPassword + "," + name + "," + lastName + "," + gender + "," + number + "," + organization;
                            }
                        }
                        fileContent.append(line).append(System.lineSeparator());
                    }

                    if (!idExists) {
                        try {
                            errorSound();
                        } catch (Exception ex) {

                        }
                        JOptionPane.showMessageDialog(null, "شناسه‌ی وارد شده در سیستم موجود نیست.", "خطا",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(UserPassPath))) {
                        writer.write(fileContent.toString());
                    }
                    try {
                        successSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "رمز عبور با موفقیت تغییر کرد.", "تغییر رمز عبور", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginFrame();

                } catch (IOException ex) {
                    try {
                        errorSound();
                    } catch (Exception exception) {

                    }
                    JOptionPane.showMessageDialog(null, "خطا در باز کردن فایل پسوردها.", "خطا", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}


//   1402/03/15 15:00 p.m. ~ 5 - 6 - 2023

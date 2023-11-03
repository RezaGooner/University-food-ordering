package Frames.Profile;
/*
این کد  برای این است که به کاربران اجازه دهد تا رمز عبور خود را تغییر دهند.
 این برنامه با ساخت یک پنجره شروع می‌شود. در این پنجره دو فیلد رمز عبور برای رمز عبور جدید و تایید آن و یک دکمه "تغییر رمز عبور" وجود دارد.

این کلاس یک متد به نام `isValidPassword` نیز تعریف می‌کند که یک رشته را به عنوان ورودی می‌گیرد و
 بررسی می‌کند که آیا آن برای یک رمز عبور معتبر کافی است یا نه.
 شرایطی که برای یک رمز عبور معتبر تعیین شده‌اند این است که رمز عبور حداقل باید 8 کاراکتر باشد،
  شامل حداقل یک عدد، یک حرف کوچک، یک حرف بزرگ و یک کاراکتر خاص (مانند !، @، # و غیره) باشد.
   این متد در صورتی که رمز عبور تمامی شرایط را برآورده کند، `true` و در غیر این صورت `false` برمی‌گرداند.

سازنده‌ی کلاس یک رشته به نام `Id` را به عنوان ورودی دریافت می‌کند که شناسه‌ی کاربر را نشان می‌دهد.
 هنگامی که کاربر روی دکمه "تغییر رمز عبور" کلیک می‌کند، سازنده مقادیر وارد شده در فیلدهای رمز عبور را دریافت می‌کند،
  با استفاده از متد `isValidPassword` اعتبارسنجی می‌کند و آن‌ها را با یکدیگر مقایسه می‌کند تا مطمئن شود که هر دوی آن‌ها یکسان هستند.
   اگر مقادیر معتبر و مطابق باشند، سازنده یک فایل حاوی اطلاعات کاربر را خوانده و رمز عبور کاربر با شناسه‌ی وارد شده راتغییر میدهد.
    سپس یک پیام برای اطلاع کاربر از موفقیت تغییر رمز عبور نمایش داده می‌شود و پنجره فعلی بسته می‌شود. حال کاربر می‌تواند با رمز عبور جدید وارد شود.

این کد همچنین یک نوار منو با دو آیتم "بازگشت" و "خروج" ایجاد می‌کند.
 با کلیک بر روی آیتم "بازگشت"، کاربر با یک پیام تأییدی تأیید مواجه می‌شود
  تا تأیید کند که آیا می‌خواهد از پنجره‌ی فعلی خارج شده و به پنجره‌ی قبلی بازگردد یا نه.
  همچنین با کلیک بر روی آیتم "خروج"، کاربر با یک پیام تأیید مواجه می‌شود تا تأیید کند که آیا می‌خواهد از برنامه خارج شود یا نه.

   `````````````````````````````````````````````````````

It represents a graphical user interface for updating a user's password. Here is a breakdown of the code:

1. The class imports various classes and libraries for GUI components, event handling, file operations, and sound effects.

2. The class extends the `JFrame` class.

3. The class defines instance variables, including a string variable for storing the user's ID, password fields for entering the new and confirmed passwords, and a regular expression method for validating passwords.

4. The constructor of the class initializes the frame, sets its properties (title, size, icon, etc.), and creates the necessary GUI components (labels, password fields, button, etc.) using Swing.

5. The constructor sets up the menu bar, which contains "Back" and "Exit" items.

6. The constructor adds action listeners to the "Back" and "Exit" items. If the user chooses to go back, a confirmation dialog is shown, and if confirmed, the current frame is disposed, and a new `LoginFrame` is created. If the user chooses to exit, a confirmation dialog is shown, and if confirmed, the current frame is disposed.

7. The constructor adds the GUI components to a panel and sets the panel as the content pane of the frame.

8. The constructor sets the frame as visible and non-resizable.

9. The `actionPerformed` method is implemented from the `ActionListener` interface and is called when the change password button is clicked.

10. The `actionPerformed` method retrieves the values entered in the password fields, validates the input, and performs the necessary operations.

11. If any of the fields are empty, an error sound effect is played, an error message dialog is shown, and the method returns.

12. If the new password does not meet the specified criteria (numbers, lowercase and uppercase letters, special characters, minimum length), an error sound effect is played, an error message dialog is shown, and the method returns.

13. If the new password and the confirmed password do not match, an error sound effect is played, an error message dialog is shown, and the method returns.

14. The method reads the user password file (`UserPassPath`) and searches for a matching user ID.

15. If a match is found, the user's password is updated in the file, a success sound effect is played, a success message dialog is shown, the current frame is disposed, and a new `LoginFrame` is created.

16. If the user ID is not found, an error sound effect is played, an error message dialog is shown, and the method returns.

17. If there is an exception during file operations, an error sound effect is played, an error message dialog is shown, and the method returns.

Overall, this code provides a GUI for updating a user's password, validates user input, searches for a match in a password file, and handles success and error scenarios accordingly.
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


public class UpdatePassword extends JFrame {

    private String id ;
    private final JPasswordField  newPasswordField;
    private final JPasswordField confirmPasswordField;

    public static boolean isValidPassword(String str) {
        return str.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-+|!@#%^&*()-_=/.])(?=\\S+$).{8,}$");
    }

    public UpdatePassword(String Id) {
        setTitle("تغییر رمز عبور");
        setSize(400, 150);
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel newPasswordLabel = new JLabel(": رمز عبورجدید");
        newPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newPasswordField = new JPasswordField(10);
        JLabel confirmPasswordLabel = new JLabel(": تکرار رمز عبور جدید");
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPasswordField = new JPasswordField(10);

        JButton changePasswordButton = new JButton("تغییر رمز عبور");
        changePasswordButton.setUI(new StyledButtonUI());
        changePasswordButton.setBackground(ForgotPassword.colorButton);


        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBackground(ForgotPassword.colorBackground);
        panel.add(newPasswordLabel);
        panel.add(newPasswordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(changePasswordButton);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(ForgotPassword.colorButton);
        setJMenuBar(menuBar);
        JMenuItem backItem = new JMenuItem("بازگشت");
        backItem.setBackground(ForgotPassword.colorButton);
        menuBar.add(backItem);

        backItem.addActionListener(new ActionListener() {
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

        JMenuItem exitItem = new JMenuItem("خروج");
        exitItem.setBackground(ForgotPassword.colorButton);
        menuBar.add(exitItem);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"خیر", "بله"};

                int exitResult = JOptionPane.showOptionDialog(null, "آیا خارج می شوید؟", "خروج",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                if (exitResult == JOptionPane.NO_OPTION) {
                    dispose();
                }
            }
        });

        add(panel);
        setVisible(true);

        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id = Id;
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (id.isEmpty() ||  newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "لطفاً تمامی فیلدها را پر کنید.", "خطا", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (! isValidPassword(newPassword)){
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    JOptionPane.showMessageDialog(null, "رمز عبور باید حاوی اعداد و کاراکترها ، حروف بزرگ و کوجک انگلیسی باشد.", "خطا", JOptionPane.ERROR_MESSAGE);
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
                        String name = parts[2];
                        String lastName = parts[3];
                        String gender = parts[4];
                        String number = parts[5];
                        String organization = parts[6];

                        if (id.equals(storedId)) {
                            idExists = true;

                            line = id + "," + newPassword + "," + name + "," + lastName + "," + gender + "," + number + "," + organization;
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


//  1402/03/11 16:00 p.m. ~ 1 - 6 - 2023
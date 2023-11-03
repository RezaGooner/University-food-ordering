package Frames.Profile;

/*
این کد یک پنجره برای بازیابی رمز عبور کاربران فراهم می‌کند. پس از ورود شماره دانشجویی و شماره همراه کاربر و زدن دکمه "بررسی"،
 فایل حاوی اطلاعات کاربران خوانده می‌شود و شماره دانشجویی و شماره همراه ورودی با اطلاعات موجود در فایل مقایسه می‌شوند.

در صورتی که شماره دانشجویی یا شماره همراه وارد نشده باشد، یک پیغام خطا نمایش داده می‌شود و عمل بازیابی رمز عبور انجام نمی‌شود.

در صورتی که شماره دانشجویی یا شماره همراه وارد شده در فایل وجود نداشته باشد، یک پیغام خطا نمایش داده می‌شود و عمل بازیابی رمز عبور انجام نمی‌شود.

در صورتی که هر دو شماره دانشجویی و شماره همراه ورودی در فایل وجود داشته باشند،
یک پیغام تایید نمایش داده می‌شود و کاربر به صفحه تغییر رمز عبور منتقل می‌شود.

   `````````````````````````````````````````````````````

It represents a graphical user interface for handling the process of retrieving a forgotten password. Here is a breakdown of the code:

1. The class imports various classes and libraries for GUI components, event handling, file operations, and sound effects.

2. The class extends the `JFrame` class and implements the `ActionListener` interface.

3. The class defines several instance variables, including labels, text fields for entering the student ID and phone number, a result label for displaying messages, and colors for the button and background.

4. The constructor of the class initializes the frame, sets its properties (title, icon, size, etc.), and creates the necessary GUI components (labels, text fields, button, etc.) using Swing.

5. The constructor sets up the menu bar, which contains "Back" and "Exit" items.

6. The constructor adds action listeners to the "Back" and "Exit" items. If the user chooses to go back, a confirmation dialog is shown, and if confirmed, the current frame is disposed and a new `LoginFrame` is created. If the user chooses to exit, a confirmation dialog is shown, and if confirmed, the current frame is disposed.

7. The constructor adds the GUI components to a panel and sets the panel as the content pane of the frame.

8. The constructor sets the frame as visible and non-resizable.

9. The `actionPerformed` method is implemented from the `ActionListener` interface and is called when the submit button is clicked.

10. The `actionPerformed` method retrieves the values entered in the text fields, opens the user password file (`UserPassPath`), and searches for a matching student ID and phone number.

11. If a match is found, a success sound effect is played, a success message dialog is shown, the current frame is disposed, and a new `UpdatePassword` frame is created with the student ID as a parameter.

12. If the student ID field is empty, an error sound effect is played, an error message is displayed in the result label, and the student ID field is highlighted.

13. If the phone number field is empty, an error sound effect is played, an error message is displayed in the result label, and the phone number field is highlighted.

14. If no match is found or the input is invalid, an error sound effect is played, an error message is displayed in the result label, and the label's text color is set to red.

15. If there is an exception during file operations, the exception is printed.

Overall, this code provides a GUI for retrieving forgotten passwords, validates user input, searches for a match in a password file, and handles success and error scenarios accordingly.
 */

import Classes.Theme.StyledButtonUI;
import Frames.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.successSound;
import static Classes.Pathes.FilesPath.UserPassPath;

public class ForgotPassword extends JFrame implements ActionListener {
    private final JLabel resultLabel;
    private final JTextField idField;
    private final JTextField phoneField;
    public static Color colorButton = new Color(252,174,30);
    public static Color colorBackground = new Color(255,214,153);

    public ForgotPassword() {
        setTitle("فراموشی رمز");
        this.setBackground(colorBackground);
        setIconImage(icon.getImage());
        setSize(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel idLabel = new JLabel(": شماره دانشجویی");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idField = new JTextField();

        JLabel phoneLabel = new JLabel(": شماره همراه");
        phoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        phoneField = new JTextField();

        JButton submitButton = new JButton("بررسی");
        submitButton.setUI(new StyledButtonUI());
        submitButton.setBackground(colorButton);
        submitButton.addActionListener(this);

        resultLabel = new JLabel();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(submitButton);
        panel.add(resultLabel);

        panel.setBackground(colorBackground);

        add(panel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorButton);
        setJMenuBar(menuBar);
        JMenuItem backItem = new JMenuItem("بازگشت");
        backItem.setBackground(colorButton);
        menuBar.add(backItem);
        JMenuItem exitItem = new JMenuItem("خروج");
        exitItem.setBackground(colorButton);
        menuBar.add(exitItem);

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


        setVisible(true);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        String id = idField.getText();
        String phone = phoneField.getText();

        try {
            File file = new File(UserPassPath);
            Scanner scanner = new Scanner(file);
            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String storedId = parts[0];
                String storedPhone = parts[5];

                if (id.equals(storedId) && phone.equals(storedPhone)) {
                    found = true;
                    break;
                }
            }

            scanner.close();

            if (found) {
                try {
                    successSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(null , "شماره همراه شما تایید شد");
                dispose();
                new UpdatePassword(id);
            } else if (id.isEmpty()){
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                resultLabel.setText("لطفا شماره دانشجویی را وارد نمایید!");
                resultLabel.setForeground(Color.red);
                idField.setBackground(Color.yellow);
                idField.setForeground(Color.black);
            } else if (phone.isEmpty()){
                try {
                    errorSound();
                } catch (Exception ex) {

                }

                resultLabel.setText("لطفا شماره همراه را وارد نمایید!");
                resultLabel.setForeground(Color.red);
                phoneField.setBackground(Color.yellow);
                phoneField.setForeground(Color.black);
            } else {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                resultLabel.setText("شماره همراه یا شناسه نامعتبر!");
                resultLabel.setForeground(Color.red);

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }


}


//   1402/03/15 15:00 p.m. ~ 5 - 6 - 2023

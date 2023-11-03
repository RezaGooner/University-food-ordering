package Frames.Profile;

/*
این کلاس برای ایجاد حساب کاربری جدید ساخته شده است

در اغاز پنجره ای باز می شود با ورودی های firstNameField , lastNameField , idField , numberField و همچنین
genderComboBox , organizationComboBox و گزینه های Student , VIPStudent و Employee .

در ورودی های تعریف شده کاربر بایستی نام و نام خانوادگی ، شناسه و همچنین شماره همراه خود را وارد کند
شناسه باید ده عددی ده رقمی باشد .
 همچنین بسته به نوع کاربر با انتخاب هر کدام از checkBox های دانشجو و کارمند و دانشجوی ویژه باید با عددی خاص شروع شود.
شماره همراه نیز باید عددی یازده رقمی باشد.
در ضمن همه فیلد های ورودی باید پر شوند.

کاربر باید جنسیت خود را از genderComboBox انتخاب کرده و اگر تیک بخش VIPStudent را فعال کند
 بایستی وضعیت خود را در organizationComboBox مشخص کند تا تخفیف های خرید غذا شامل وی شود

 سپس بعد از وارد کردن و تایید شدن اطلاعات ، داده ها در مسیر فایل مشخص شده ، به صورت جداشده با کاما نوشته میشوند.



 */


import Classes.Roles.Gender;
import Classes.Roles.Organization;
import Classes.Theme.StyledButtonUI;
import Frames.LoginFrame;
import Frames.Order.BalanceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Theme.SoundEffect.successSound;
import static Frames.LoginFrame.isNumeric;
import static Classes.Pathes.FilesPath.UserPassPath;

public class NewUserFrame extends JFrame {

    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField idField;
    private final JTextField numberField;
    private final JComboBox<Gender> genderComboBox;
    private final JComboBox<Organization> organizationComboBox;

    private final JCheckBox employeeCheckBox;
    private final JCheckBox studentCheckBox;
    private final JCheckBox vipStudentCheckBox;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JLabel statusLabel;
    private String organization ;
    public static Color colorButton = new Color(19,56,190);
    public static Color colorBackground = new Color(136,226,242);


    public NewUserFrame() {
        setTitle("ثبت نام");
        setIconImage(icon.getImage());
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel firstNameLabel = new JLabel(": نام");
        firstNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        firstNameField = new JTextField(10);
        JLabel lastNameLabel = new JLabel(": نام خانوادگی");
        lastNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lastNameField = new JTextField(10);
        JLabel idLabel = new JLabel(": شناسه");
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        idField = new JTextField(10);
        JLabel numberLabel = new JLabel(": شماره همراه");
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberField = new JTextField(11);
        JLabel passwordLabel = new JLabel(": رمز عبور");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JPasswordField(10);
        JLabel confirmPasswordLabel = new JLabel(": تکرار رمز عبور");
        confirmPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPasswordField = new JPasswordField(10);
        JLabel genderLabel = new JLabel(": جنسیت");
        genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setBackground(colorBackground);

        employeeCheckBox = new JCheckBox("کارمند");
        employeeCheckBox.setBackground(colorBackground);
        studentCheckBox = new JCheckBox("دانشجو");
        studentCheckBox.setBackground(colorBackground);
        vipStudentCheckBox = new JCheckBox("دانشجوی ویژه");
        vipStudentCheckBox.setBackground(colorBackground);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorButton);
        setJMenuBar(menuBar);
        JMenuItem exitItem = new JMenuItem("بازگشت");
        exitItem.setForeground(Color.white);
        exitItem.setBackground(colorButton);
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


        organizationComboBox = new JComboBox<>(Organization.values());
        organizationComboBox.setBackground(colorBackground);
        organizationComboBox.setEnabled(false);

        JButton registerButton = new JButton("ثبت نام");
        registerButton.setUI(new StyledButtonUI());
        registerButton.setForeground(Color.white);
        registerButton.setBackground(colorButton);
        statusLabel = new JLabel("");


        JPanel panel = new JPanel(new GridLayout(10, 1));
        panel.setBackground(colorBackground);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(numberLabel);
        panel.add(numberField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(employeeCheckBox);
        panel.add(studentCheckBox);
        panel.add(vipStudentCheckBox);
        panel.add(organizationComboBox);
        panel.add(registerButton);
        panel.add(statusLabel);


        add(panel);
        setVisible(true);


        employeeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (employeeCheckBox.isSelected()) {
                    studentCheckBox.setSelected(false);
                    vipStudentCheckBox.setSelected(false);
                    organizationComboBox.setSelectedItem(Organization.NOT);
                    organizationComboBox.setEnabled(false);
                }
            }
        });

        studentCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (studentCheckBox.isSelected()) {
                    employeeCheckBox.setSelected(false);
                    vipStudentCheckBox.setSelected(false);
                    organizationComboBox.setSelectedItem(Organization.NOT);
                    organizationComboBox.setEnabled(false);
                }
            }
        });

        vipStudentCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (vipStudentCheckBox.isSelected()) {
                    employeeCheckBox.setSelected(false);
                    studentCheckBox.setSelected(false);
                    organizationComboBox.setEnabled(true);
                } else {
                    organizationComboBox.setEnabled(false);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String id = idField.getText();
                Gender gender = (Gender) genderComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String number = numberField.getText();


                try (BufferedWriter writer = new BufferedWriter(new FileWriter(UserPassPath, true))) {
                    String organizationInput = String.valueOf(organizationComboBox.getSelectedItem());

                    switch (organizationInput) {
                        case "سایر":
                            organization = "NOT";
                            break;
                        case "کمیته امام":
                            organization = "KOMITE";
                            break;
                        case "سازمان بهزیستی":
                            organization = "BEHZISTI";
                            break;
                        case "دانشجوی ممتاز":
                            organization = "MOMTAZ";
                            break;
                    }


                    if (isValidRegistration(firstName, lastName, id, password, confirmPassword)) {
                        if (studentCheckBox.isSelected()){
                            if ( id.startsWith("4") ) {
                                writer.write(id + ',' + password + ',' + firstName + ',' + lastName + ',' + gender + ',' + number + ',' + organization);
                                writer.newLine();
                                try {
                                    successSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "کاربر با موفقیت ثبت شد.");
                                BalanceHandler.addNewUser(id);
                                dispose();
                                new LoginFrame();

                            } else {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "شناسه دانشجو با 4 شروع می شود.");
                            }
                        }
                        if (employeeCheckBox.isSelected()) {
                            if (id.startsWith("0") ) {
                                writer.write(id + ',' + password + ',' + firstName + ',' + lastName + ',' + gender + ',' + number + ',' + organization);
                                writer.newLine();
                                try {
                                    successSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "کاربر با موفقیت ثبت شد.");
                                BalanceHandler.addNewUser(id);
                                dispose();
                                new LoginFrame();

                            } else {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "شناسه کارمند با 0 شروع می شود.");
                            }
                        }
                        if ( vipStudentCheckBox.isSelected() ){
                            if (id.startsWith("1") ) {
                                writer.write(id + ',' + password + ',' + firstName + ',' + lastName + ',' + gender + ',' + number + ',' + organization);
                                writer.newLine();
                                try {
                                    successSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "کاربر با موفقیت ثبت شد.");
                                BalanceHandler.addNewUser(id);
                                dispose();
                                new LoginFrame();
                            } else {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "شناسه دانشجوی ویژه با 1 شروع می شود.");
                            }
                        }
                    } else {
                        try {
                            errorSound();
                        } catch (Exception ex) {

                        }
                    }
                } catch (IOException ex) {
                    try {
                        errorSound();
                    } catch (Exception exception) {

                    }
                    JOptionPane.showMessageDialog(null, "خطا در ذخیره سازی اطلاعات.");
                }
            }
        });

    }

    public static boolean isPersian(String input) {

        return input.matches("^[\u0600-\u06FF\\s]+$");
    }

    private boolean isValidRegistration(String firstName, String lastName, String id, String password, String confirmPassword) {

        firstNameField.setBackground(Color.white);
        firstNameField.setForeground(Color.black);
        lastNameField.setBackground(Color.white);
        lastNameField.setForeground(Color.black);
        idField.setBackground(Color.white);
        idField.setForeground(Color.black);
        numberField.setBackground(Color.white);
        numberField.setForeground(Color.black);


        boolean isValid = true;

        if (!(employeeCheckBox.isSelected() || studentCheckBox.isSelected() || vipStudentCheckBox.isSelected())) {
            isValid = false;
            try {
                errorSound();
            } catch (Exception ex) {

            }
            JOptionPane.showMessageDialog(this, "لطفاً یکی از گزینه های دانشجو ، دانشجوی ویژه یا کارمند را انتخاب کنید.", "خطا", JOptionPane.ERROR_MESSAGE);

        } else {

            if (firstName.isEmpty() && lastName.isEmpty() && id.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                try {
                    errorSound();
                } catch (Exception ex) {

                }
                JOptionPane.showMessageDialog(this, "لطفاً تمامی فیلدها را پر کنید.", "خطا", JOptionPane.ERROR_MESSAGE);
                isValid = false;
            } else {
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    statusLabel.setText("لطفا نام و نام خانوادگی را وارد نمایید");
                    statusLabel.setForeground(Color.red);
                    firstNameField.setBackground(Color.yellow);
                    firstNameField.setForeground(Color.black);
                    lastNameField.setBackground(Color.yellow);
                    lastNameField.setForeground(Color.black);
                    isValid = false;
                }  if (! isPersian(firstName)) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    statusLabel.setText("لطفا نام را به فارسی وارد نمایید");
                    statusLabel.setForeground(Color.red);
                    firstNameField.setBackground(Color.yellow);
                    firstNameField.setForeground(Color.black);
                }  if (! isPersian(lastName)) {
                    try {
                        errorSound();
                    } catch (Exception ex) {

                    }
                    statusLabel.setText("لطفا نام خانوادگی را به فارسی وارد نمایید");
                    statusLabel.setForeground(Color.red);
                    lastNameField.setBackground(Color.yellow);
                    lastNameField.setForeground(Color.black);
                } else {
                    if (!isNumeric(id) || id.length() != 10) {
                        try {
                            errorSound();
                        } catch (Exception ex) {

                        }
                        statusLabel.setText("شماره دانشجویی باید 10 رقمی باشد");
                        statusLabel.setForeground(Color.red);
                        idField.setBackground(Color.yellow);
                        idField.setForeground(Color.black);
                        isValid = false;
                    } else {
                        if (!password.equals(confirmPassword)) {
                            try {
                                errorSound();
                            } catch (Exception ex) {

                            }
                            statusLabel.setText("رمز عبور و تکرار آن با هم مطابقت ندارند");
                            statusLabel.setForeground(Color.red);
                            passwordField.setBackground(Color.red);
                            passwordField.setForeground(Color.white);
                            isValid = false;
                        } else {
                            if (!UpdatePassword.isValidPassword(password)) {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                                JOptionPane.showMessageDialog(null, "رمز عبور باید حاوی اعداد و کاراکترها ، حروف بزرگ و کوجک انگلیسی باشد.", "خطا", JOptionPane.ERROR_MESSAGE);
                                statusLabel.setText("");
                                passwordField.setBackground(Color.yellow);
                                passwordField.setForeground(Color.black);
                                isValid = false;


                            } else {
                                if ((numberField.getText()).isEmpty()) {
                                    try {
                                        errorSound();
                                    } catch (Exception ex) {

                                    }
                                    JOptionPane.showMessageDialog(null, "شماره همراه را وارد نمایید.", "خطا", JOptionPane.ERROR_MESSAGE);
                                    statusLabel.setText("");
                                    numberField.setBackground(Color.yellow);
                                    numberField.setForeground(Color.black);
                                    isValid = false;
                                } if ((! isNumeric(numberField.getText())) || numberField.getText().length() != 11) {
                                    try {
                                        errorSound();
                                    } catch (Exception ex) {

                                    }
                                    JOptionPane.showMessageDialog(null, "شماره همراه باید عددی 11 رقمی باشد.", "خطا", JOptionPane.ERROR_MESSAGE);
                                    statusLabel.setText("");
                                    numberField.setBackground(Color.yellow);
                                    numberField.setForeground(Color.black);
                                }
                            }
                            try (Scanner scanner = new Scanner(new File(UserPassPath))) {
                                while (scanner.hasNextLine()) {
                                    String line = scanner.nextLine();
                                    String[] parts = line.split(",");
                                    String storedId = parts[0];

                                    if (id.equals(storedId)) {
                                        try {
                                            errorSound();
                                        } catch (Exception ex) {

                                        }
                                        statusLabel.setText("شناسه‌ی وارد شده تکراری است");
                                        statusLabel.setForeground(Color.red);
                                        idField.setBackground(Color.red);
                                        idField.setForeground(Color.white);
                                        isValid = false;
                                        break;
                                    }
                                }

                            } catch (FileNotFoundException e) {
                                try {
                                    errorSound();
                                } catch (Exception ex) {

                                }
                            }
                        }
                    }

                }
            }
        }
        return isValid;
    }
}


//  1402/03/15 15:00 p.m. ~ 5 - 6 - 2023

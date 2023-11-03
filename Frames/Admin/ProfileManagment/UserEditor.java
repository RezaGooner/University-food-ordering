package Frames.Admin.ProfileManagment;

/*
این یک کلاس جاوا است که یک رابط کاربری گرافیکی (GUI) برای ویرایش اطلاعات کاربر ایجاد می‌کند.
 این اجازه را به کاربر می‌دهد تا اطلاعات کاربر را که در یک فایل ذخیره شده‌اند، مشاهده، جستجو، ویرایش و حذف کند.
 رابط کاربری شامل یک JTable برای نمایش دادن اطلاعات کاربر، همچنین دکمه‌های ذخیره تغییرات و حذف رکوردها است.
  این کلاس همچنین دارای یک متد برای نوشتن رکوردهای حذف شده در  فایل  برای محفوظ نگه داشتن آن‌ها به صورت بایگانی است.

   `````````````````````````````````````````````````````

It is a graphical user interface (GUI) for editing and managing user information. Here is a summary of the class's structure and functionality:

1. Imports: The class imports various packages and classes from the Java standard library, including GUI-related classes, file I/O classes, and sound-related classes.

2. Class Declaration: The class is declared as `UserEditor` and extends the `JFrame` class. It also implements the `ActionListener` interface, indicating that it can handle action events.

3. Instance Variables: The class defines several instance variables, including a `JTable` for displaying user information, a `DefaultTableModel` for managing the table's data, `JButton` objects for saving changes and deleting users, a `File` object for specifying the data file, a `JTextField` for searching users, and a `String` variable for storing the path of the deleted users file.

4. Constructor: The class has a constructor that takes a filename as a parameter. It initializes the GUI components, loads user data from the file, and sets up event listeners.

5. GUI Components: The constructor sets up the GUI components, including a menu bar with menu items for refreshing the user list and viewing the delete history, a table for displaying user information, a scroll pane for the table, buttons for saving changes and deleting users, and a search field with a search button.

6. Loading User Data: The constructor reads user data from the specified file using a `Scanner`. The data is split into fields and added to the table model, which updates the table's content.

7. Search Functionality: The constructor sets up the search functionality by attaching an action listener to the search button. When the button is clicked, the text in the search field is used to filter the rows in the table based on a regular expression pattern.

8. Event Handling: The class implements the `actionPerformed` method from the `ActionListener` interface. It handles events triggered by the save and delete buttons. When the save button is clicked, the user data in the table is saved back to the file. When the delete button is clicked, the selected user is deleted from the table and the corresponding data is removed from the file.

9. Supporting Methods: The class also includes additional methods for writing deleted user information to a separate file, counting the number of users, and playing error sound effects.

Overall, the `UserEditor` class provides a GUI interface for editing and managing user information stored in a file. It allows users to search for specific users, save changes to the file, and delete users from the list.

 */

import Classes.Theme.CustomRendererShowUsers;
import Classes.Roles.Gender;
import Classes.Roles.Organization;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Pathes.FilesPath.UserPassPath;

public class UserEditor extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton buttonSave;
    private JButton buttonDelete;
    private File file;
    private JTextField searchField;
    public static String deletedFilePath = "E:/Programming/University/Term 2/Food Self/deletedUsers.txt" ;

    public UserEditor(String filename) throws IOException {
        super(" کاربر فعال " + countUsers());
        file = new File(filename);

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
                    new UserEditor(UserPassPath);
                } catch (Exception ex) {
                }
            }
        });
        menu.add(refreshButton);
        JMenuItem showDeleteHistoryButton = new JMenuItem("سابقه حذف");
        showDeleteHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new ShowDeletedUsers();
                }  catch (Exception exception) {
                }
            }
        });
        menu.add(showDeleteHistoryButton);

        String[] columnNames = {"شناسه", "رمز عبور", "نام", "نام خانوادگی", "شماره همراه", "جنسیت", "پوشش"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);



        buttonSave = new JButton("ذخیره تغییرات");
        buttonDelete = new JButton("حذف کاربر");
        buttonSave.addActionListener(this);
        buttonDelete.addActionListener(this);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonDelete);
        add(buttonPanel, BorderLayout.SOUTH);


        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields.length == 7) {
                    String id = fields[0];
                    String password = fields[1];
                    String firstName = fields[2];
                    String lastName = fields[3];
                    String number = fields[5];
                    Gender gender = Gender.valueOf(fields[4]);
                    Organization org = Organization.valueOf(fields[6]);
                    Object[] row = {id, password, firstName, lastName, number, gender, org};
                    tableModel.addRow(row);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        searchField = new JTextField(20);
        JButton searchButton = new JButton("جستجو");
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                if (searchText.length() == 0) {

                    table.setRowSorter(null);
                } else {

                    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
                    table.setRowSorter(sorter);
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
                }
                table.setDefaultRenderer(Object.class, new CustomRendererShowUsers());
            }
        });

        table.setDefaultRenderer(Object.class, new CustomRendererShowUsers());


        setSize(700, 500);
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == buttonSave) {

            try {
                FileWriter writer = new FileWriter(file);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String id = (String) tableModel.getValueAt(i, 0);
                    String password = (String) tableModel.getValueAt(i, 1);
                    String firstName = (String) tableModel.getValueAt(i, 2);
                    String lastName = (String) tableModel.getValueAt(i, 3);
                    String gender = (String) tableModel.getValueAt(i, 4);
                    String org = String.valueOf(tableModel.getValueAt(i, 5));
                    switch (org) {
                        case "سایر":
                            org = "NOT";
                            break;
                        case "کمیته امام":
                            org = "KOMITE";
                            break;
                        case "سازمان بهزیستی":
                            org = "BEHZISTI";
                            break;
                        case "دانشجوی ممتاز":
                            org = "MOMTAZ";
                            break;
                    }
                    String line = id + "," + password + "," + firstName + "," + lastName + "," + gender + "," + org + "\n";
                    writer.write(line);
                }
                writer.close();
                JOptionPane.showMessageDialog(this, " تغییرات با موفقیت ذخیره شد." );
            } catch (Exception e) {
                try {
                    errorSound();
                }  catch (Exception exception) {
                }
            }
        } else if (event.getSource() == buttonDelete) {

            int rowIndex = table.getSelectedRow();
            if (rowIndex >= 0) {
                String deletedFieldID = (String) table.getValueAt(rowIndex, 0);
                String deletedFieldFirstName = (String) table.getValueAt(rowIndex, 2);
                String deletedFieldLastName = (String) table.getValueAt(rowIndex, 3);
                String deletedFieldGender = (String) table.getValueAt(rowIndex, 4);
                String deletedFieldNumber = String.valueOf(tableModel.getValueAt(rowIndex, 5));
                switch (deletedFieldNumber) {
                    case "سایر":
                        deletedFieldNumber = "NOT";
                        break;
                    case "کمیته امام":
                        deletedFieldNumber = "KOMITE";
                        break;
                    case "سازمان بهزیستی":
                        deletedFieldNumber = "BEHZISTI";
                        break;
                    case "دانشجوی ممتاز":
                        deletedFieldNumber = "MOMTAZ";
                        break;
                }
                String deletedFieldOrgan = String.valueOf(table.getValueAt(rowIndex, 6));
                switch (deletedFieldOrgan) {
                    case "سایر":
                        deletedFieldOrgan = "NOT";
                        break;
                    case "کمیته امام":
                        deletedFieldOrgan = "KOMITE";
                        break;
                    case "سازمان بهزیستی":
                        deletedFieldOrgan = "BEHZISTI";
                        break;
                    case "دانشجوی ممتاز":
                        deletedFieldOrgan = "MOMTAZ";
                        break;
                }
                String deletedFieldValue = deletedFieldID + "," + deletedFieldFirstName + "," + deletedFieldLastName +
                        "," + deletedFieldGender + "," + deletedFieldNumber + "," + deletedFieldOrgan;

                try {
                    writeDeletedFieldToFile( deletedFieldValue);
                }  catch (Exception e) {
                    try {
                        errorSound();
                    }  catch (Exception exception) {
                    }
                }
                try {
                    String id = (String) tableModel.getValueAt(rowIndex, 0);
                    Scanner scanner = new Scanner(file);
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] fields = line.split(",");
                        if (fields.length == 7 && !fields[0].equals(id)) {
                            sb.append(line + "\n");
                        }
                    }
                    scanner.close();
                    PrintWriter writer = new PrintWriter(file);
                    writer.print(sb.toString());
                    writer.close();
                    // بروزرسانی محتوای جدول
                    tableModel.removeRow(rowIndex);
                }  catch (Exception e) {
                    try {
                        errorSound();
                    }  catch (Exception exception) {
                    }
                }
            }

            JOptionPane.showMessageDialog(this, "کاربر با موفقیت حذف شد.");
        }
    }

    public static void writeDeletedFieldToFile( String deletedFieldValue) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try {
            FileWriter fw = new FileWriter(deletedFilePath, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println( deletedFieldValue + "," + LocalDate.now());
            pw.close();
        }  catch (Exception e) {
            errorSound();
        }
    }
    public static int countUsers() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(UserPassPath));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
}


//   1402/03/29 04:00 a.m. ~ 19 - 6 - 2023

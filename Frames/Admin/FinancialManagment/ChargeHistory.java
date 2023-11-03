package Frames.Admin.FinancialManagment;

/*
این کد یک پنجره برای نمایش سابقه شارژ های کاربران در سیستم غذایی ایجاد می کند.
 سابقه شارژ ها با استفاده از یک فایل متنی که شامل شناسه کاربران، تاریخ، زمان و مبلغ شارژ می باشد، در جدول نمایش داده می شود.
  در این پنجره، ادمین ها می توانند با استفاده از جستجو در جدول، مورد مورد سابقه شارژ ها را پیدا کنند،
  آن ها را حذف کنند و یا با استفاده از دکمه‌ی تازه سازی، لیست سابقه شارژ ها را به روز کنند.

در متد سازنده، جدول ایجاد و داده های موجود در فایل خوانده می شوند و به جدول اضافه می شوند.
 سپس، قابلیت جستجو برای جدول اضافه می شود و دکمه‌ی حذف نیز به پنجره اضافه می شود.
  همچنین، یک منو با گزینه‌های تازه سازی، بازگشت و خروج از سیستم نیز به پنجره اضافه شده است.

در متد `saveToFile`، اطلاعات جدول به فایل متنی ذخیره می شود.
 این متد، هر خط از جدول را با استفاده از رشته "," جدا می کند و در فایل مورد نظر ذخیره می کند.

   `````````````````````````````````````````````````````

This code defines a `ChargeHistory` class that extends `JFrame` and is used to create a GUI application
for managing charge history. Here's a breakdown of the code:

    - The `ChargeHistory` constructor sets up the main components of the GUI, such as the table, search field,
      delete button, and menu bar. It also populates the table with data read from a file specified by the `ChargeHistoryPath`.
    - The `searchField` is a text field that allows the user to search for specific rows in the table based on the text entered.
    - The `deleteButton` is a button that allows the user to delete the selected row from the table.
      The corresponding row is also removed from the table model, and the changes are saved to the file using
      the `saveToFile` method.
    - The `saveToFile` method writes the data from the table model to a file specified by the `ChargeHistoryPath`.
    - The `sumCharges` method calculates the sum of charges by reading the data from the file specified by
      the `ChargeHistoryPath` and summing the values in the fourth column of each row.

The `ChargeHistory` class serves as the main entry point for the application, and when an instance of it is created,
it displays the GUI window for managing charge history.

Please note that the code assumes the existence of several referenced classes and constants, such as `Classes.Pathes.
FilesPath.ChargeHistoryPath`, `Classes.Pathes.FilesPath.icon`, and `Classes.Theme.SoundEffect.errorSound`.
To run this code successfully, you need to ensure that all the necessary classes and resources are available
and properly configured.
 */

import java.awt.*;
import java.io.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Classes.Pathes.FilesPath.ChargeHistoryPath;
import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;

public class ChargeHistory extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;

    public ChargeHistory() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super( " مجموع تراکنش ها " + sumCharges() + " تومان ");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("شناسه");
        tableModel.addColumn("تاریخ");
        tableModel.addColumn("زمان");
        tableModel.addColumn("مبلع");

        try (BufferedReader br = new BufferedReader(new FileReader(ChargeHistoryPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                tableModel.addRow(parts);
            }
        } catch (Exception e) {
            errorSound();
        }

        table = new JTable(tableModel);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
        table.setRowSorter(sorter);
        searchField = new JTextField();
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = searchField.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });

        JButton deleteButton = new JButton("حذف");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                    try {
                        saveToFile();
                    } catch (Exception ex) {
                        try {
                            errorSound();
                        } catch (Exception exc) {
                        }
                    }
                }
            }
        });

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(deleteButton, BorderLayout.EAST);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("بیشتر");
        menuBar.add(menu);
        JMenuItem refreshButton = new JMenuItem("تازه سازی");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    new ChargeHistory();
                } catch (Exception ex) {
                    try {
                        errorSound();
                    } catch (Exception exc) {
                    }
                }
            }
        });
        menu.add(refreshButton);

        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void saveToFile() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try (FileWriter fw = new FileWriter(ChargeHistoryPath)) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String line = "";
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    line += tableModel.getValueAt(i, j) + ",";
                }
                fw.write(line.trim() + "\n");
            }
        } catch (Exception e) {
            errorSound();
        }
    }
    public static int sumCharges() {
        int sum = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(ChargeHistoryPath));

            String line;
            while (true) {

                if (((line = reader.readLine()).isEmpty())) break;

                sum += Integer.parseInt(line.split(" , ")[3]);
            }
        } catch (Exception e) {
        }
        return sum;
    }
}

//  1402/03/29 04:40 a.m. ~ 19 - 6 - 2023

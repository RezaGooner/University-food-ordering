package Frames.Admin.FinancialManagment;

/*
این کد یک پنجره برای مدیریت تخفیف‌های سیستم غذایی ایجاد می کند.
تخفیف‌ها با استفاده از یک فایل متنی که شامل کد تخفیف و درصد تخفیف می‌باشد، در جدول نمایش داده می‌شوند.
 در این پنجره، مدیران می‌توانند تخفیف‌های موجود را حذف کنند یا تخفیف جدیدی با کد تخفیف و درصد مشخص شده اضافه کنند.

در متد سازنده، جدول ایجاد و داده‌های موجود در فایل خوانده می‌شوند و به جدول اضافه می‌شوند.
 سپس، قابلیت حذف تخفیف‌ها و اضافه کردن تخفیف جدید با استفاده از دکمه‌های مربوط به پنجره اضافه می‌شود.
  همچنین، برای اضافه کردن تخفیف جدید، از یک JTextField و یک JSpinner استفاده می‌شود.

در متد `saveToFile`، اطلاعات جدول به فایل متنی ذخیره می‌شود.
 این متد، هر خط از جدول را با استفاده از رشته " : " جدا می‌کند و در فایل مورد نظر ذخیره می‌کند.

    `````````````````````````````````````````````````````

The code you provided defines a `DiscountManage` class that extends `JFrame` and is used to create
a GUI application for managing discounts. Here's a breakdown of the code:

    - The `DiscountManage` constructor sets up the main components of the GUI, such as the table, text area, spinner,
      and buttons. It also populates the table with data read from a file specified by the `DiscountsPath`.
    - The `tableModel` is a `DefaultTableModel` that defines the structure of the table.
    - The `deleteButton` is a button that allows the user to delete the selected row from the table.
      The corresponding row is also removed from the table model, and the changes are saved to the file using the
      `saveToFile` method.
    - The `spinner` is a component that allows the user to select a percentage value for a discount.
    - The `addButton` is a button that adds a new row to the table with the text entered in the text area and
      the value selected in the spinner. The changes are saved to the file using the `saveToFile` method.
    - The `textArea` is a text field where the user can enter the code for a new discount.
    - The `saveToFile` method writes the data from the table model to a file specified by the `DiscountsPath`.

The `DiscountManage` class serves as the main entry point for the application, and when an instance of it is created,
it displays the GUI window for managing discounts.

Please note that the code assumes the existence of several referenced classes and constants, such as `Classes.Pathes.
FilesPath.DiscountsPath`, `Classes.Pathes.FilesPath.icon`, and `Classes.Theme.SoundEffect.errorSound`.
To run this code successfully, you need to ensure that all the necessary classes and resources are available
and properly configured.

 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static Classes.Pathes.FilesPath.DiscountsPath;
import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;

public class DiscoutManage extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField textArea;
    private JSpinner spinner;

    public DiscoutManage() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super("تخفیف ها");

        tableModel = new DefaultTableModel();
        tableModel.addColumn("کد تخفیف");
        tableModel.addColumn("درصد");

        try (BufferedReader br = new BufferedReader(new FileReader(DiscountsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" : ");
                tableModel.addRow(parts);
            }
        } catch (Exception e) {
            errorSound();
        }

        table = new JTable(tableModel);

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

        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        JButton addButton = new JButton("اضافه کردن");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int number = (int) spinner.getValue();
                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "لطفا کد تخفیف را در بخش ورودی وارد کنید.");
                } else {
                    tableModel.addRow(new Object[]{text, number});
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

        textArea = new JTextField();
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(spinner);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(textAreaScrollPane, BorderLayout.NORTH);


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
                    new DiscoutManage();
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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DiscountsPath));
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String text = (String) tableModel.getValueAt(i, 0);
                int number = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
                writer.write(text + " : " + number + "\n");
            }
            writer.close();
        } catch (Exception e) {
            errorSound();
        }
    }
}

//  1402/03/29 04:45 a.m. ~ 19 - 6 - 2023

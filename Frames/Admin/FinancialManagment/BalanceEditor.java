package Frames.Admin.FinancialManagment;

/*
این کد یک پنجره برای ویرایش موجودی کاربران در سیستم غذایی ایجاد می کند.
در این پنجره، موجودی کاربران با استفاده از یک نوار کشویی ویرایش می شود.
همچنین، شناسه کاربران با استفاده از یک جعبه متنی ویرایش شده و قابل تغییر است.
 این کد دارای دو گزینه بیشتر است که شامل تازه سازی لیست کاربران و خروج از سیستم هستند.

در متد `parseFile`، فایل موجودی کاربران با استفاده از یک نام فایل به عنوان ورودی خوانده می شود.
این فایل شامل شناسه کاربران و موجودی آنها است که با " : " از هم جدا شده اند.
 سپس این اطلاعات در یک نگاشت (`Map`) ذخیره می شوند.

در متد `saveToFile`، اطلاعات کاربران را در فایل ذخیره می کند.
 این متد، نگاشتی از شناسه کاربران و موجودی آنها را به عنوان ورودی می گیرد و در فایل مورد نظر ذخیره می کند.

در متد `displayFileContentsInTable`، محتوای فایل با استفاده از یک جدول (`JTable`) نمایش داده می شود.
در این متد، هر خط از فایل شامل شناسه کاربر و موجودی آن است که با رشته " : " از هم جدا شده اند.

   `````````````````````````````````````````````````````

This code defines a `BalanceEditor` class that extends `JFrame` and is used to create a GUI application for editing balances.
Here's a breakdown of the code:

    - The `BalanceEditor` constructor sets up the main components of the GUI, such as labels, scrollbars, buttons, and panels.
      It also configures the menu bar with menu items for refreshing and displaying the IDs.
    - The `parseFile` method reads the balances from a file specified by the `fileName` parameter
      and populates a `Map<String, Integer>` with the parsed data.
    - The `saveToFile` method writes the balances from the `data` map to a file specified by the `fileName` parameter.
    - The `displayFileContentsInTable` method creates a new `JFrame` and displays the contents of a file specified
      by the `filePath` parameter in a `JTable`.
    - The `errorSound` method is called in various exception handling blocks to play an error sound effect.

The `BalanceEditor` class serves as the main entry point for the application, and when an instance of it is created,
it displays the GUI window for editing balances.

Please note that the code assumes the existence of several referenced classes and constants,
such as `Pathes.FilesPath.icon`, `Theme.SoundEffect.errorSound`, and `Pathes.FilesPath.BalancesPath`.
To run this code successfully, you need to ensure that all the necessary classes and resources are available
and properly configured.
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static Classes.Pathes.FilesPath.icon;
import static Classes.Theme.SoundEffect.errorSound;
import static Classes.Pathes.FilesPath.BalancesPath;

public class BalanceEditor extends JFrame {
    private static final int SCROLL_BAR_UNIT_INCREMENT = 500;

    private Map<String, Integer> data;
    private JLabel idLabel;
    private JLabel balanceLabel;
    private JScrollBar scrollBar;
    private JTextField idTextField;

    public BalanceEditor() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        super("تغییر موجودی ها");

        data = parseFile(BalancesPath);

        idLabel = new JLabel("شناسه : " + data.keySet().iterator().next(), SwingConstants.CENTER);
        balanceLabel = new JLabel("موجودی : " + data.values().iterator().next(), SwingConstants.CENTER);

        scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, data.values().iterator().next(), 500, 0, 10000);
        scrollBar.setUnitIncrement(SCROLL_BAR_UNIT_INCREMENT);
        scrollBar.addAdjustmentListener(e -> {
            int newBalance = scrollBar.getValue();
            balanceLabel.setText("موجودی : " + newBalance);

            String id = data.keySet().iterator().next();
            data.put(id, newBalance);

            try {
                saveToFile(BalancesPath, data);
            } catch (Exception ex) {
                try {
                    errorSound();
                } catch (Exception exc) {
                }
            }
        });

        idTextField = new JTextField(data.keySet().iterator().next());
        JButton changeIdButton = new JButton("تغییر شناسه");
        changeIdButton.addActionListener(e -> {

            String newId = idTextField.getText().trim();
            idLabel.setText("شناسه : " + newId);
            int balance = data.remove(data.keySet().iterator().next());
            data.put(newId, balance);

            try {
                saveToFile(BalancesPath, data);
            } catch (Exception ex) {
                try {
                    errorSound();
                } catch (Exception exc) {
                }
            }
        });

        JButton saveButton = new JButton("ذخیره");
        saveButton.addActionListener(e -> {

            try {
                saveToFile(BalancesPath, data);
            } catch (Exception ex) {
                try {
                    errorSound();
                } catch (Exception exc) {
                }
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(idLabel);
        topPanel.add(balanceLabel);
        topPanel.add(idTextField);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(scrollBar, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(changeIdButton);
        bottomPanel.add(saveButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JFrame frame = new JFrame();
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
                    new BalanceEditor();
                } catch (Exception exception){

                }
            }
        });
        menu.add(refreshButton);

        JMenuItem showIDButton = new JMenuItem("مشاهده شناسه ها");
        menu.add(showIDButton);
        showIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    displayFileContentsInTable(BalancesPath);
                } catch (Exception ex) {
                    try {
                        errorSound();
                    } catch (Exception exc) {
                    }
                }
            }
        });



        setIconImage(icon.getImage());
        setContentPane(mainPanel);
        setSize(new Dimension(300, 200));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Map<String, Integer> parseFile(String fileName) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Map<String, Integer> data = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                String id = parts[0].trim();
                int balance = Integer.parseInt(parts[1].trim());
                data.put(id, balance);
            }
        } catch (Exception e) {
            errorSound();
        }

        return data;
    }

    private void saveToFile(String fileName, Map<String, Integer> data) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.newLine();
            }
        } catch (Exception e) {
            errorSound();
        }
    }
    public static void displayFileContentsInTable(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        JFrame frame = new JFrame("شناسه ها");
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("شماره");
        model.addColumn("شناسه");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String id;
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                id = line.split(" : ")[0];
                lineNumber++;
                model.addRow(new Object[]{lineNumber, id});
            }
        } catch (Exception e) {
            errorSound();
        }

        table.setModel(model);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
    }
}

//  1402/03/29 04:00 a.m. ~ 19 - 6 - 2023

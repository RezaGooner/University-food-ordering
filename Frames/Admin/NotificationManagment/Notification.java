package Frames.Admin.NotificationManagment;

/*
این کد یک پنجره برای ویرایش یک فایل متنی با استفاده از کلاس JTextPane ایجاد می کند.
در این پنجره، یک نوار ابزار با دکمه های برش، کپی، چسباندن، اتمام اطلاعیه و ذخیره فایل اضافه شده است.
 همچنین یک منوی فایل با گزینه های جدید، باز کردن، ذخیره و خروج نیز در این پنجره قابل دسترسی است.

در ابتدای کد، متغیرهایی مانند JTextPane، JFileChooser و File برای مدیریت فایل ها و محتوای آنها تعریف شده اند.
سپس در constructor کلاس، نوار ابزار و JTextPane به پنجره اضافه شده و محتوای فایل مورد نظر در JTextPane نمایش داده شده است.

در کلاس SaveAction، یک گزینه ذخیره فایل تعریف شده است که
 با کلیک کردن بر روی آن، ابتدا چک می کند که آیا فایلی انتخاب شده است یا خیر.
 اگر فایلی انتخاب نشده باشد، یک جعبه انتخاب فایل باز می شود
  و در صورت انتخاب فایل، محتوای JTextPane در فایل ذخیره می شود.
   در غیر این صورت، محتوای JTextPane به فایل قبلی ذخیره می شود.

   `````````````````````````````````````````````````````

  This code is a Java class named `Notification` that extends the `JFrame` class.
  It represents a GUI application for editing and saving text files.

Here is a breakdown of the code:

    1. The code imports various classes from different packages, including `javax.swing` for GUI components,
      `javax.swing.filechooser` for file chooser functionality, `javax.swing.text` for text-related components,
      `java.awt` for basic AWT components, and `java.awt.event` for event-related classes.

    2. The `Notification` class is defined, which extends the `JFrame` class.

    3. The constructor of the `Notification` class is defined. It sets up the main frame by setting the title, size,
       and visibility.

    4. A `JToolBar` named `toolBar` is created and added to the north (top) of the frame. It is set to be non-floatable.

    5. Several actions (`cutAction`, `copyAction`, `pasteAction`, `endAction`, `saveAction`) are created using
       pre-defined editor kit actions and custom abstract actions. These actions represent various operations that can be performed on the text.

    6. The actions are added to the toolbar using the `add()` method.

    7. A `JTextPane` named `textPane` is created and wrapped inside a scroll pane (`JScrollPane`).
       The scroll pane is added to the frame.

    8. The text content of a file is read from a specified file path (`HelpPath`) and loaded into the `textPane`.
       If an exception occurs during file reading, it is caught and printed.

    9. A `JFileChooser` named `fileChooser` is created and set to filter only text files with a "txt" extension.

    10. Several `ActionListener` instances (`openListener`, `saveListener`, `newListener`, `exitListener`) are created
        to handle the corresponding menu item actions.

    11. A menu bar (`JMenuBar`) is created and added to the frame.

    12. The `fileMenu` is created and populated with menu items (`newMenuItem`, `openMenuItem`, `saveMenuItem`, `exitMenuItem`).
        Each menu item is associated with its respective `ActionListener`.

    13. The `SaveAction` class is defined as an inner class within `Notification`. It extends `AbstractAction` and
        represents the action of saving the text to a file.

    14. The `SaveAction` class overrides the `actionPerformed` method to handle the save action. If a file is selected,
        the text from the `textPane` is written to the file. If no file is selected, a file chooser dialog is displayed
        for the user to choose a file location. The text is then written to the selected file.

Overall, this code provides a GUI application for editing and saving text files. It allows users to open, edit, and
save text files using a graphical interface.

 */
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static Classes.Pathes.FilesPath.HelpPath;

public class Notification extends JFrame {

    private JTextPane textPane;
    private JFileChooser fileChooser;
    private File selectedFile;

    public Notification() {
        setTitle("ویرایش فایل");
        setSize(500, 500);
        setVisible(true);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
        Action cutAction = new DefaultEditorKit.CutAction();
        cutAction.putValue(Action.NAME, "برش");
        toolBar.add(cutAction);
        Action copyAction = new DefaultEditorKit.CopyAction();
        copyAction.putValue(Action.NAME, "کپی");
        toolBar.add(copyAction);
        Action pasteAction = new DefaultEditorKit.PasteAction();
        pasteAction.putValue(Action.NAME, "چسباندن");
        toolBar.add(pasteAction);
        toolBar.addSeparator();
        Action endAction = new AbstractAction("اتمام اطلاعیه") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clipboard = "\n***\n";
                try {
                    textPane.replaceSelection(clipboard);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        toolBar.add(endAction);
        Action saveAction = new SaveAction();
        toolBar.add(saveAction);

        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane);

        File file = new File(HelpPath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
            textPane.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));

        ActionListener openListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(Notification.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    try {

                        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        reader.close();
                        textPane.setText(sb.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        ActionListener saveListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {

                        BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                        writer.write(textPane.getText());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        ActionListener newListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedFile = null;
                textPane.setText("");
            }
        };

        ActionListener exitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };


        JMenu fileMenu = new JMenu("فایل");
        JMenuItem newMenuItem = new JMenuItem("فایل جدید");
        newMenuItem.addActionListener(newListener);
        fileMenu.add(newMenuItem);
        JMenuItem openMenuItem = new JMenuItem("باز کردن فایل");
        openMenuItem.addActionListener(openListener);
        fileMenu.add(openMenuItem);
        JMenuItem saveMenuItem = new JMenuItem("ذخیره فایل");
        saveMenuItem.addActionListener(saveListener);
        fileMenu.add(saveMenuItem);
        JMenuItem exitMenuItem = new JMenuItem("خروج");
        exitMenuItem.addActionListener(exitListener);
        fileMenu.add(exitMenuItem);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }


    class SaveAction extends AbstractAction {
        public SaveAction() {
            super("ذخیره فایل");
        }


        public void actionPerformed(ActionEvent e) {
            if (selectedFile == null) {
                fileChooser.setCurrentDirectory(new File(HelpPath));
                int returnVal = fileChooser.showSaveDialog(Notification.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                } else {
                    return;
                }
            }
            fileChooser.setCurrentDirectory(new File(HelpPath));
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                writer.write(textPane.getText());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}

//  1402/04/01 06:00 p.m. ~ 22 - 6 - 2023
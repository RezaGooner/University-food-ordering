package Frames.Admin.Managment;

/*
این کد جاوا یک درخت JTree با سه گره اصلی "کاربران"، "مالی" و "سفارش" ایجاد می کند.
هر گره اصلی دارای چندین گره فرزند است که می توان آنها را در JTree باز یا بسته کرد.

 وقتی یک گره کلیک می شود، کد بررسی می کند کدام گره کلیک شده است و بر اساس گره کلیک شده عملی خاصی انجام می دهد.
و فایل مربوظ به همان مورد بررسی قرار میگیرد

به عنوان مثال، اگر گره "ادمین ها" کلیک شود، یک جعبه گفتگو با سه گزینه "مشاهده"، "جایگزینی فایل" و "لغو" نمایش داده می شود.
اگر کاربر گزینه "مشاهده" را انتخاب کند، کد محتوای فایل "AdminsPath" را می خواند و آن را در یک جعبه گفتگو نمایش می دهد.
 اگر کاربر گزینه "جایگزینی فایل" را انتخاب کند، یک جعبه انتخاب فایل نمایش داده می شود
  تا به کاربر اجازه دهد فایل جدیدی را انتخاب کند و جایگزین فایل موجود شود.
 */


import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

import static Classes.Pathes.FilesPath.*;
import static Classes.Theme.SoundEffect.errorSound;
import static Frames.Admin.ProfileManagment.UserEditor.deletedFilePath;
import static Frames.Order.UniversitySelfRestaurant.*;

public class DataBaseManagment {
    public DataBaseManagment () {
        JFrame frame = new JFrame("مدیریت پایگاه های داده");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode admins = new DefaultMutableTreeNode("ادمین ها");
        DefaultMutableTreeNode balances = new DefaultMutableTreeNode("موجودی ها");
        DefaultMutableTreeNode chargeHistory = new DefaultMutableTreeNode("سوابق شارژ");
        DefaultMutableTreeNode deletedUsers = new DefaultMutableTreeNode("کاربران حذف شده");
        DefaultMutableTreeNode dinners = new DefaultMutableTreeNode("شام");
        DefaultMutableTreeNode lunches = new DefaultMutableTreeNode("ناهار");
        DefaultMutableTreeNode discounts = new DefaultMutableTreeNode("تخفیفات");
        DefaultMutableTreeNode log = new DefaultMutableTreeNode("سوابق ورود");
        DefaultMutableTreeNode orders = new DefaultMutableTreeNode("سفارشات");
        DefaultMutableTreeNode userpass = new DefaultMutableTreeNode("کاربران");

        DefaultMutableTreeNode usersRoot = new DefaultMutableTreeNode("کاربران");
        usersRoot.add(admins);
        usersRoot.add(userpass);
        usersRoot.add(log);
        usersRoot.add(deletedUsers);

        DefaultMutableTreeNode financialRoot = new DefaultMutableTreeNode("مالی");
        financialRoot.add(balances);
        financialRoot.add(chargeHistory);
        financialRoot.add(discounts);

        DefaultMutableTreeNode ordersRoot = new DefaultMutableTreeNode("سفارش");
        ordersRoot.add(orders);
        ordersRoot.add(lunches);
        ordersRoot.add(dinners);


        root.add(usersRoot);
        root.add(financialRoot);
        root.add(ordersRoot);

        JTree tree = new JTree(root);

        tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selectedRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selectedPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selectedRow != -1) {

                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    if (selectedNode.equals(admins)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "ادمین ها", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(AdminsPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(AdminsPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    } else if (selectedNode.equals(balances)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "موجودی ها", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(BalancesPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(BalancesPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(balances)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "موجودی ها", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(BalancesPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(BalancesPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(chargeHistory)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "سوابق شارژ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(ChargeHistoryPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(ChargeHistoryPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }   else if (selectedNode.equals(deletedUsers)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "کاربران حذف شده", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(deletedFilePath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(deletedFilePath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }   else if (selectedNode.equals(dinners)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "شام", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(dinnerFilePath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(dinnerFilePath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }    else if (selectedNode.equals(lunches)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "ناهار", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(lunchFilePath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(lunchFilePath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(discounts)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "تخفیفات", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(DiscountsPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(DiscountsPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(log)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "سوابق ورود", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(LogPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(LogPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(orders)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "سفارشات", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(orderFilename);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(orderFilename);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }  else if (selectedNode.equals(userpass)) {

                        Object[] options = {"مشاهده", "جایگزینی فایل", "لغو"};
                        int result = JOptionPane.showOptionDialog(frame, "آیا میخواهید فایل را مشاهده یا ویرایش کنید ؟", "کاربران", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
                        if (result == JOptionPane.YES_OPTION) {

                            try {
                                File file = new File(UserPassPath);
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                StringBuilder content = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    content.append(line).append("\n");
                                }
                                reader.close();
                                JOptionPane.showMessageDialog(frame, content.toString());
                            } catch (IOException ex) {
                                try {
                                    errorSound();
                                } catch (Exception exc) {
                                }
                                JOptionPane.showMessageDialog(frame, "خطا در خواندن فایل : " + ex.getMessage());
                            }
                        } else if (result == JOptionPane.NO_OPTION) {

                            JFileChooser fileChooser = new JFileChooser();
                            int chooserResult = fileChooser.showOpenDialog(frame);
                            if (chooserResult == JFileChooser.APPROVE_OPTION) {
                                File selectedFile = fileChooser.getSelectedFile();
                                File file = new File(UserPassPath);
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
                                    Files.copy(selectedFile.toPath(), file.toPath());
                                    JOptionPane.showMessageDialog(frame, "فایل با موفقیت جابجا شد.");
                                } catch (IOException ex) {
                                    try {
                                        errorSound();
                                    } catch (Exception exc) {
                                    }
                                    JOptionPane.showMessageDialog(frame, "خطا در جایجایی فایل : " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        frame.setIconImage(icon.getImage());
        frame.add(scrollPane);
        frame.setSize(430, 315);
        frame.setVisible(true);

    }
}


//   1402/04/01 01:00 a.m. ~ 22 - 6 - 2023
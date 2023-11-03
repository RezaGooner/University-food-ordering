package Classes.Theme;

/*
این کد یک کلاس `CustomRendererShowUsers` را برای سفارشی کردن نمایش سلول‌های جدولی  تعریف می‌کند.
این کلاس از کلاس `DefaultTableCellRenderer` ارث بری می‌کند.

متد `getTableCellRendererComponent` برای سفارشی کردن نمایش هر سلول جدول استفاده می‌شود.
 این متد یک شیء `JTable`، یک شیء `Object` (مقدار سلول) و چند پارامتر دیگر را به عنوان ورودی دریافت می‌کند
 و یک شیء `Component` ( یک `JLabel`) را برمی‌گرداند.

متد `getTableCellRendererComponent` ابتدا شیء `Component` را به عنوان نمایش پیش‌فرض سلول دریافت کرده
و در متغیر `c` ذخیره می‌کند.

سپس با استفاده از `table.getValueAt(row, 0)`، مقدار سلول اول سطر فعلی را (که در اینجا مربوط به شماره کاربری است)
در متغیر `columnValue` ذخیره می‌کند.

سپس با استفاده از `startsWith`، بررسی می‌کند که آیا شماره کاربری با "4" شروع می‌شود یا با "1" شروع می‌شود یا هیجکدام.
 اگر با "4" شروع شود، رنگ پس‌زمینه `c` به رنگ سفید و رنگ متن `c` به رنگ سیاه تنظیم می‌شود،
  اگر با "1" شروع شود، رنگ پس‌زمینه `c` به رنگ خاکستری روشن و رنگ متن `c` به رنگ سفید تنظیم می‌شود،
   در غیر این صورت یعنی شروع با "0"، رنگ پس‌زمینه `c` به رنگ صورتی و رنگ متن `c` به رنگ سیاه تنظیم می‌شود.

در کل، این کلاس برای سفارشی کردن نمایش سلول‌های جدولی استفاده می‌شود،
 به گونه‌ای که با بررسی شماره کاربری،
  سلول‌های مربوط به کاربران با شماره‌های شروع شونده با "4" با رنگ پس‌زمینه سفید و رنگ متن سیاه،
  کاربران با شماره‌های شروع شونده با "1" با رنگ پس‌زمینه خاکستری روشن و رنگ متن سفید
  و سایر کاربران با رنگ پس‌زمینه صورتی و رنگ متن سیاه نمایش داده می‌شوند.

   `````````````````````````````````````````````````````

The code you provided defines a custom cell renderer class called `CustomRendererShowUsers` that
extends `DefaultTableCellRenderer`.
This renderer is used to customize the appearance of cells in a `JTable` that displays user information.

Here's a breakdown of the code:

    - The `CustomRendererShowUsers` class overrides the `getTableCellRendererComponent` method from
      the `DefaultTableCellRenderer` class.
      This method is called by the `JTable` to obtain the component that will be used to render a cell in the table.
    - The overridden `getTableCellRendererComponent` method takes several parameters:
      the `JTable` instance, the `value` of the cell, a boolean indicating whether the cell is selected,
      a boolean indicating whether the cell has focus, the `row` and `column` indices of the cell.
    - The method first calls the `super.getTableCellRendererComponent` to obtain the default component for rendering the cell.
    - It then retrieves the value from the first column (index 0) in the same row using `table.getValueAt(row, 0)`
      and converts it to a string using `String.valueOf()`. The result is assigned to `columnValue`.
    - The logic in the method checks the value of `columnValue` to determine the background and foreground colors for the cell:
      - If `columnValue` starts with "4", indicating a specific condition, the background color of the component
        is set to `Color.WHITE`, and the foreground color is set to `Color.BLACK`.
      - If `columnValue` starts with "1", indicating another condition, the background color of the component
        is set to `Color.LIGHT_GRAY`, and the foreground color is set to `Color.BLACK`.
      - For any other value of `columnValue`, the background color of the component is set to `Color.PINK`,
        and the foreground color is set to `Color.BLACK`.
    - Finally, the modified component is returned.

This custom renderer can be used by setting an instance of `CustomRendererShowUsers` as the cell renderer for
specific columns in the `JTable` displaying user information. This renderer will then be applied to the cells in
those columns, and the appearance will be customized based on the logic in the `getTableCellRendererComponent` method.
Cells with values starting with "4" will have a white background and black text, cells with values starting with "1"
will have a light gray background and black text, and other cells will have a pink background and black text.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRendererShowUsers extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String columnValue = String.valueOf(table.getValueAt(row, 0));

        if (columnValue.startsWith("4")) {
            c.setBackground(Color.white);
            c.setForeground(Color.black);
        } else if (columnValue.startsWith("1")) {
            c.setBackground(Color.lightGray);
            c.setForeground(Color.black);
        } else {
            c.setBackground(Color.pink);
            c.setForeground(Color.black);
        }

        return c;
    }
}



package Classes.Theme;

/*
این کد یک کلاس `CustomRendererLoginHistory` را برای سفارشی کردن نمایش سلول‌های جدولی تعریف می‌کند.
 این کلاس از کلاس `DefaultTableCellRenderer` ارث بری می‌کند.

متد `getTableCellRendererComponent` برای سفارشی کردن نمایش هر سلول جدول استفاده می‌شود
. این متد یک شیء `JTable`، یک شیء `Object` (مقدار سلول) و چند پارامتر دیگر را به عنوان ورودی دریافت می‌کند
 و یک شیء `Component` (معمولاً یک `JLabel`) را برمی‌گرداند.

متد `getTableCellRendererComponent` ابتدا شیء `Component` را به عنوان نمایش پیش‌فرض سلول دریافت کرده
و در متغیر `c` ذخیره می‌کند.

سپس با استفاده از `table.getValueAt(row, 1)`، مقدار سلول دوم سطر فعلی را (که در اینجا مربوط به وضعیت ورود به سیستم است)
 در متغیر `columnValue` ذخیره می‌کند.

سپس بررسی می‌کند که آیا `columnValue` مختصر شده با "ناموفق" برابر است یا خیر.
اگر برابر با "ناموفق" باشد، رنگ پس‌زمینه و رنگ متن `c` به ترتیب به قرمز و سفید تغییر می‌کند،
در غیر این صورت (برای وضعیت‌های موفق)، رنگ پس‌زمینه و رنگ متن `c` به ترتیب با رنگ سبز و متن سیاه تنظیم می‌شود.

در کل، این کلاس برای سفارشی کردن نمایش سلول‌های جدولی استفاده می‌شود،
 به گونه‌ای که وضعیت ورود به سیستم در سلول دوم هر سطر را بررسی می‌کند
 و سلول‌هایی که وضعیت ورود ناموفق بوده را با رنگ قرمز و متن سفید
 و سلول‌هایی که وضعیت ورود موفق بوده را با رنگ سبز و متن سیاه نمایش می‌دهد.

    `````````````````````````````````````````````````````
This code defines a custom cell renderer class called `CustomRendererLoginHistory` that extends `DefaultTableCellRenderer`.
 This renderer is used to customize the appearance of cells in a `JTable` that represents login history.

Here's a breakdown of the code:

    - The `CustomRendererLoginHistory` class overrides the `getTableCellRendererComponent` method from
      the `DefaultTableCellRenderer` class. This method is called by the `JTable` to obtain the component that
      will be used to render a cell in the table.
    - The overridden `getTableCellRendererComponent` method takes several parameters: the `JTable` instance,
      the `value` of the cell, a boolean indicating whether the cell is selected, a boolean indicating whether
      the cell has focus, the `row` and `column` indices of the cell.
    - The method first calls the `super.getTableCellRendererComponent` to obtain the default component for rendering the cell.
    - It then retrieves the value from the second column (index 1) in the same row using `table.getValueAt(row, 1)`
      and assigns it to `columnValue`.
    - If `columnValue` is not null and equals to the string "ناموفق" (which means "unsuccessful" in Persian),
      the background color of the component is set to `Color.RED`, and the foreground color is set to `Color.WHITE`.
    - If `columnValue` is not "ناموفق" or is null, the background color of the component is set to `Color.GREEN`,
      and the foreground color is set to `Color.BLACK`.
    - Finally, the modified component is returned.

This custom renderer can be used by setting an instance of `CustomRendererLoginHistory` as the cell renderer for
specific columns in the `JTable` representing login history. This renderer will then be applied to the cells in
those columns, and the appearance will be customized based on the logic in the `getTableCellRendererComponent` method.
Cells with the value "ناموفق" will have a red background and white text, while other cells will have a green background
and black text.

 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRendererLoginHistory extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object columnValue = table.getValueAt(row, 1);

        if (columnValue != null && columnValue.equals("ناموفق")) {
            c.setBackground(Color.RED);
            c.setForeground(Color.white);
        } else {
            c.setBackground(Color.GREEN);
            c.setForeground(Color.BLACK);
        }

        return c;
    }
}
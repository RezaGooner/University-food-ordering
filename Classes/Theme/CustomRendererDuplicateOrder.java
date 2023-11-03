package Classes.Theme;

/*
این کد یک کلاس `CustomRendererDuplicateOrder` را برای سفارشی کردن نمایش سلول‌های جدول در یک برنامه جاوا تعریف می‌کند.
 این کلاس از کلاس `DefaultTableCellRenderer` ارث بری می‌کند.

متد `getTableCellRendererComponent` برای سفارشی کردن نمایش هر سلول جدول استفاده می‌شود.
 این متد یک شیء `JTable`، یک شیء `Object` (مقدار سلول) و چند پارامتر دیگر را به عنوان ورودی دریافت می‌کند
 و یک شیء `Component` (معمولاً یک `JLabel`) را برمی‌گرداند.

متد `getTableCellRendererComponent` ابتدا شیء `Component` را به عنوان نمایش پیش‌فرض سلول دریافت کرده
 و در متغیر `c` ذخیره می‌کند.

سپس بررسی می‌کند که آیا سطر فعلی (شماره سطر) برابر با صفر است یا خیر.
 اگر برابر با صفر باشد، رنگ پس‌زمینه و رنگ متن `c` به ترتیب به قرمز و سفید تغییر می‌کند،
  در غیر این صورت (برای سطرهای دیگر)، رنگ پس‌زمینه و رنگ متن `c` به ترتیب
  با رنگ پس‌زمینه و رنگ متن جدول (`table.getBackground()` و `table.getForeground()`) تنظیم می‌شود.

در کل، این کلاس برای سفارشی کردن نمایش سلول‌های جدول استفاده می‌شود،
 به گونه‌ای که سطر اول جدول با رنگ قرمز و متن سفید نمایش داده شود و سایر سطرها با رنگ پیش‌فرض جدول نمایش داده شوند.

    `````````````````````````````````````````````````````

This code defines a custom cell renderer class called `CustomRendererDuplicateOrder`
that extends `DefaultTableCellRenderer`. This custom renderer
is used to customize the appearance of cells in a `JTable`.

Here's a breakdown of the code:

    - The `CustomRendererDuplicateOrder` class overrides the `getTableCellRendererComponent` method from
      the `DefaultTableCellRenderer` class. This method is called by the `JTable` to obtain the component that
      will be used to render a cell in the table.
    - The overridden `getTableCellRendererComponent` method takes several parameters: the `JTable` instance,
      the `value` of the cell, a boolean indicating whether the cell is selected, a boolean indicating whether
      the cell has focus, the `row` and `column` indices of the cell.
    - The method first calls the `super.getTableCellRendererComponent` to obtain
      the default component for rendering the cell.
    - Then, it checks if the `row` is 0 (first row). If it is, the background color of the component is set to
      `Color.RED`, and the foreground color is set to `Color.WHITE` (making the text white).
    - If the `row` is not 0, the background and foreground colors are set to the default colors of the table.
    - Finally, the modified component is returned.

This custom renderer can be used by setting an instance of `CustomRendererDuplicateOrder` as the cell renderer for
specific columns in the `JTable`. This renderer will then be applied to the cells in those columns,
and the appearance will be customized according to the logic in the `getTableCellRendererComponent` method.

 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRendererDuplicateOrder extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row == 0) {
            c.setBackground(Color.RED);
            c.setForeground(Color.white);
        } else {
            c.setBackground(table.getBackground());
            c.setForeground(table.getForeground());
        }

        return c;
    }
}

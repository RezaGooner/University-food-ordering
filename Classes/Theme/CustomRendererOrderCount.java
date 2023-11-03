package Classes.Theme;

/*
The code you provided defines a custom cell renderer class called `CustomRendererOrderCount` that
extends `DefaultTableCellRenderer`. This renderer is used to customize the appearance of cells in a `JTable` that
represents order counts.

Here's a breakdown of the code:

    - The `CustomRendererOrderCount` class overrides the `getTableCellRendererComponent` method from
      the `DefaultTableCellRenderer` class. This method is called by the `JTable` to obtain the component that
      will be used to render a cell in the table.
    - The overridden `getTableCellRendererComponent` method takes several parameters: the `JTable` instance,
      the `value` of the cell, a boolean indicating whether the cell is selected, a boolean indicating whether
      the cell has focus, the `row` and `column` indices of the cell.
    - The method first calls the `super.getTableCellRendererComponent` to obtain the default component for rendering the cell.
    - It then retrieves the value from the second column (index 1) in the same row using `table.getValueAt(row, 1)`
      and assigns it to `columnValue`.
    - If `columnValue` is not null and equals to the string "مجموع" (which means "total" in Persian),
      the background color of the component is set to `Color.darkGray`, and the foreground color is set to `Color.WHITE`.
    - If `columnValue` is not "مجموع" or is null, the background color of the component is set to `Color.WHITE`,
      and the foreground color is set to `Color.BLACK`.
    - Finally, the modified component is returned.

This custom renderer can be used by setting an instance of `CustomRendererOrderCount` as the cell renderer for
specific columns in the `JTable` representing order counts. This renderer will then be applied to the cells in those columns,
and the appearance will be customized based on the logic in the `getTableCellRendererComponent` method.
Cells with the value "مجموع" will have a dark gray background and white text,
while other cells will have a white background and black text.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomRendererOrderCount extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object columnValue = table.getValueAt(row, 1);

        if (columnValue != null && columnValue.equals("مجموع")) {
            c.setBackground(Color.darkGray);
            c.setForeground(Color.white);
        } else {
            c.setBackground(Color.white);
            c.setForeground(Color.BLACK);
        }

        return c;
    }
}
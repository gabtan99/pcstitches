/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Arturo III
 */
public class ListRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if (row % 2 == 0)
            setBackground(new Color(220,220,255));
        else
            setBackground(Color.WHITE);
        setBorder(null);
        setForeground(Color.black);
        return this;
    }
}

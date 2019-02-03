/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Arturo III
 */
public class ListRenderer extends JLabel implements ListCellRenderer<Object>
{
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Event e = (Event)value;
        setText(e.getName());
        setForeground(e.getColor());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10,10,10,10));

        return this;
    }
}

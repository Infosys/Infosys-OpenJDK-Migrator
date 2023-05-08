/*
* Copyright 2018 Infosys Ltd.
* Version: 1.0.0
*Use of this source code is governed by MIT license that can be found in the LICENSE file or at https://opensource.org/licenses/MIT.
*/

/*
* Date: 06-May-2019
* @version 1.0.0
* Description: 
*/

package com.infy.openjdk.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class HeaderRenderer implements TableCellRenderer {
    DefaultTableCellRenderer renderer;
    /**
     * @param table
     */
    public HeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer)
            table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        renderer.setBackground(new Color(66,176,244));
        renderer.setForeground(new Color(255,255,255));
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, col);
    }

}
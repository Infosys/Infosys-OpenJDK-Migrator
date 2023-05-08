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

package com.infy.openjdk.report;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.infy.openjdk.ui.View;
import com.infy.openjdk.util.Main;


/*********************************************************************************
 * 1.Customize Migration Report with Checkbox enabled
 *
 * 
 ***********************************************************************************/

public class JTableHeaderCheckBox {
	Object[] colNamesMain = { "", "String", "String" };
	Object[][] data = {};
	DefaultTableModel dtm;
	JTable table;
	JTable tableAssist;

	JPanel visiblePanel = new JPanel(new FlowLayout());
	Set<String> selectedFiles = new HashSet<>();
	Set<String> removedFiles = new HashSet<>();

	/**
	 * @return selectedFiles
	 */
	public Set<String> getSelectedFiles() {
		return selectedFiles;
	}
	/**
	 * @param selectedFiles
	 */
	public void setSelectedFiles(Set<String> selectedFiles) {
		this.selectedFiles = selectedFiles;
	}
	/**
	 * @return removedFiles
	 */
	public Set<String> getRemovedFiles() {
		return removedFiles;
	}
	/**
	 * @param removedFiles
	 */
	public void setRemovedFiles(Set<String> removedFiles) {
		this.removedFiles = removedFiles;
	}
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return table
	 */
	public JTable buildGUI(List<Map<String, String>> viewer2DataList,
			List<String> headerList) {
		View.logger.info("header" + headerList.size());
		Object[] colNames = { " ", "File Name" };
		dtm = new DefaultTableModel(data, colNames);
		table = new JTable(dtm);
		table.getTableHeader().setBackground(new Color(134, 38, 195));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.getColumnModel().addColumnModelListener(new WrapColListener(table));
		table.setDefaultRenderer(Object.class, new JTPRenderer());
		String filename="";
		for (int i = 0; i < viewer2DataList.size(); i++) {
			View.logger.info("File name in autamted list-->"+viewer2DataList.get(i).get(headerList.get(0)));
			if(!viewer2DataList.get(i).get(headerList.get(0)).equals(filename))
			{
				filename=viewer2DataList.get(i).get(headerList.get(0));			
			dtm.addRow(new Object[] { Boolean.valueOf(false), viewer2DataList.get(i).get(headerList.get(0))});
			}			
		}
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		TableColumn tc = table.getColumnModel().getColumn(0);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		    table.getColumnModel().getColumn(1).setMaxWidth(1100);
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = 50;
			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}
			table.setRowHeight(row, rowHeight);
		}
		tc.sizeWidthToFit();
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener()));
		table.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{				
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if(table.getValueAt(row,col).toString().trim().contains(".java")) {
					JFrame f = new JFrame(table.getValueAt(row,col).toString().trim());
					f.setBackground(new Color(255,255,255));
					f.setSize(1000, 500);
					f.setResizable(true);
					f.setLocationRelativeTo(null);
					f.setVisible(true);					
				JTableHeaderCheckBox checkbox1 = new JTableHeaderCheckBox();
				JScrollPane sp = new JScrollPane();
				f.getContentPane().remove(sp);				
				sp = new JScrollPane(checkbox1.affectedSyntax(viewer2DataList,headerList,table.getValueAt(row,col).toString()));
				sp.setBackground(new Color(255,255,255));
				f.getContentPane().add(sp);
				f.setVisible(true);				
				}
				//checkbox selected item
				else {
					View.logger.info("selected row-->"+table.getModel().getValueAt(row, 1).toString());
					if(table.getValueAt(row, col).toString().equalsIgnoreCase("true")) {
					selectedFiles.add(table.getModel().getValueAt(row, 1).toString());
					removedFiles.remove(table.getModel().getValueAt(row, 1).toString());
					}
					else {
						selectedFiles.remove(table.getModel().getValueAt(row, 1).toString());
						removedFiles.add(table.getModel().getValueAt(row, 1).toString());
					}
					View.logger.info("selectedFiles"+selectedFiles);
					View.logger.info("removedFiles"+removedFiles);
				}
				
				View.logger.info("Value in the cell clicked :" + " " + table.getValueAt(row, col).toString());

			}

		}

		);
		return table;
	}
		
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @param selectedFile
	 * @return table
	 */
	public JTable affectedSyntax(List<Map<String, String>> viewer2DataList,List<String> headerList,String selectedFile) {
		View.logger.info("header" + headerList.size());
		String lineNo = headerList.get(1);
		String incompatibility = headerList.get(2);
		String possibleReplacement = headerList.get(3);
		Object[] colNames = { lineNo,  incompatibility, possibleReplacement };
		dtm = new DefaultTableModel(data, colNames);

		table = new JTable(dtm);
		table.getTableHeader().setBackground(new Color(134, 38, 195));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.getColumnModel().addColumnModelListener(new WrapColListener(table));
		table.setDefaultRenderer(Object.class, new JTPRenderer());
		table.getTableHeader().setDefaultRenderer(new HeaderRenderer(table));
				
		for (int i = 0; i < viewer2DataList.size(); i++) {
			if(selectedFile.equals(viewer2DataList.get(i).get(headerList.get(0))))
			{
			dtm.addRow(new Object[] {  
					viewer2DataList.get(i).get(lineNo), viewer2DataList.get(i).get(incompatibility).trim(),
					viewer2DataList.get(i).get(possibleReplacement).trim() });
			}
			
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn tc = table.getColumnModel().getColumn(0);
		tc.setMaxWidth(60);
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = 50;
			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}
			table.setRowHeight(row, rowHeight);
		}
		tc.sizeWidthToFit();		
		return table;
	}
	
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @param selectedFile
	 * @return tableAssist
	 */
	public JTable affectedSyntaxAssisted(List<Map<String, String>> viewer2DataList,
			List<String> headerList,String selectedFile) {

		View.logger.info("header" + headerList.size());
		String lineNo = headerList.get(1);
		String incompatibility = headerList.get(2);
		String possibleReplacement = headerList.get(3);
		String references = headerList.get(4);
		String category = headerList.get(7);
		Object[] colNames = {lineNo,  incompatibility, possibleReplacement, references,category};
		dtm = new DefaultTableModel(data, colNames);

		tableAssist = new JTable(dtm);
		tableAssist.getTableHeader().setBackground(new Color(134, 38, 195));
		tableAssist.getTableHeader().setForeground(new Color(255,255,255));
		tableAssist.getColumnModel().addColumnModelListener(new WrapColListener(tableAssist));
		tableAssist.setDefaultRenderer(Object.class, new JTPRenderer());
		tableAssist.getTableHeader().setDefaultRenderer(new HeaderRenderer(tableAssist));
					
		for (int i = 0; i < viewer2DataList.size(); i++) {
			if(selectedFile.equals(viewer2DataList.get(i).get(headerList.get(0)))) {
				dtm.addRow(new Object[] {  
					viewer2DataList.get(i).get(lineNo),
					viewer2DataList.get(i).get(incompatibility).trim(),
					viewer2DataList.get(i).get(possibleReplacement),
					viewer2DataList.get(i).get(references),
					viewer2DataList.get(i).get(category)
				});
			}			
		}

		tableAssist.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn tc = tableAssist.getColumnModel().getColumn(0);
		tc.setMaxWidth(80);
		for (int row = 0; row < tableAssist.getRowCount(); row++) {
			int rowHeight = 50;
			for (int column = 0; column < tableAssist.getColumnCount(); column++) {
				Component comp = tableAssist.prepareRenderer(tableAssist.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}

			tableAssist.setRowHeight(row, rowHeight);
		}

		tc.sizeWidthToFit();		
		return tableAssist;
	}

	/**
	 * @return table
	 */
	public JTable buildGUIAutomatedReport() {
		Object[] colNames = { "", "FileName", "Line No", "Incompatibility", "Manual Replacement" };
		dtm = new DefaultTableModel(data, colNames);
		table = new JTable(dtm);
		table.getColumnModel().addColumnModelListener(new WrapColListener(table));
		table.setDefaultRenderer(Object.class, new JTPRenderer());

		for (int i = 0; i < 20; i++) {
			dtm.addRow(new Object[] { 
				Boolean.valueOf(false),
				"Test.java", i++,
				"import awt.List.ResorceBundles;", "Replace with OpenJDK 11 resource Bundles",
			});
		}

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn tc = table.getColumnModel().getColumn(0);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = 70;

			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}

			table.setRowHeight(row, rowHeight);
		}
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener()));
		return table;
	}

	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return tableAssist
	 */
	public JTable assistedTable(List<Map<String, String>> viewer2DataList, List<String> headerList) {	
		View.logger.info("header" + headerList.size());
		Object[] colNames = { "", "File Name"};
		dtm = new DefaultTableModel(data, colNames);

		tableAssist = new JTable(dtm);
		tableAssist.getTableHeader().setBackground(new Color(134, 38, 195));
		tableAssist.getTableHeader().setForeground(new Color(255,255,255));
		tableAssist.getColumnModel().addColumnModelListener(new WrapColListener(tableAssist));
		tableAssist.setDefaultRenderer(Object.class, new JTPRenderer());
		String filename="";
		//Created this set to get the unique list of files in Assisted remediation tab
		Set<String> fileList = new HashSet<>();
		for (int i = 0; i < viewer2DataList.size(); i++) {
			if(!viewer2DataList.get(i).get(headerList.get(0)).equals(filename) && fileList.add(viewer2DataList.get(i).get(headerList.get(0))))
			{
				filename=viewer2DataList.get(i).get(headerList.get(0));
			dtm.addRow(new Object[] { Boolean.valueOf(false), viewer2DataList.get(i).get(headerList.get(0))});
			}
			
		}
		tableAssist.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		TableColumn tc = tableAssist.getColumnModel().getColumn(0);
		tableAssist.getColumnModel().getColumn(0).setMaxWidth(60);
		tableAssist.getColumnModel().getColumn(1).setMaxWidth(1125);
		for (int row = 0; row < tableAssist.getRowCount(); row++) {
			int rowHeight = 50;

			for (int column = 0; column < tableAssist.getColumnCount(); column++) {
				Component comp = tableAssist.prepareRenderer(tableAssist.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}

			tableAssist.setRowHeight(row, rowHeight);
		}

		tc.sizeWidthToFit();
		tc.setCellEditor(tableAssist.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(tableAssist.getDefaultRenderer(Boolean.class));
		tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener1()));		
		tableAssist.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				int row = tableAssist.rowAtPoint(e.getPoint());
				int col = tableAssist.columnAtPoint(e.getPoint());
				if(tableAssist.getValueAt(row,col).toString().trim().contains(".java")) {
				Main.callStatus("Wait...");
				JFrame f = new JFrame(tableAssist.getValueAt(row,col).toString().trim());

				f.setBackground(new Color(255,255,255));
				f.setSize(1000, 500);
				f.setResizable(true);
				f.setLocationRelativeTo(null);
				f.setVisible(true);

				JTableHeaderCheckBox checkbox1 = new JTableHeaderCheckBox();
				JScrollPane sp = new JScrollPane();
				f.getContentPane().remove(sp);			
				sp = new JScrollPane(checkbox1.affectedSyntaxAssisted(viewer2DataList,headerList,tableAssist.getValueAt(row,col).toString()));
				sp.setBackground(new Color(255,255,255));
				f.getContentPane().add(sp);
				f.setVisible(true);				
				}
				View.logger.info("Value in the cell clicked :" + " " + tableAssist.getValueAt(row, col).toString());
			}

		}
		);	
		return tableAssist;
	}
		
	/**
	 * @param viewer2DataList
	 * @param headerList
	 * @return table
	 */
	public JTable buildMultiBrowseUpload(List<Map<String, String>> viewer2DataList, List<String> headerList) {
		View.logger.info("header" + headerList.size());
		Object[] colNames = { "", "ANALYZED APPLICATION LIST" };
		dtm = new DefaultTableModel(data, colNames);
		table = new JTable(dtm);
		table.getTableHeader().setBackground(new Color(66,134,244));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.getColumnModel().addColumnModelListener(new WrapColListener(table));
		table.setDefaultRenderer(Object.class, new JTPRenderer());
		for (int i = 0; i < viewer2DataList.size(); i++) {						
			dtm.addRow(new Object[] { Boolean.valueOf(false), viewer2DataList.get(i).get(headerList.get(0))});
			}			
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableColumn tc = table.getColumnModel().getColumn(0);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setMaxWidth(1100);
		for (int row = 0; row < table.getRowCount(); row++) {
			int rowHeight = 50;
			for (int column = 0; column < table.getColumnCount(); column++) {
				Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
			}
			table.setRowHeight(row, rowHeight);
		}

		tc.sizeWidthToFit();
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener()));
		table.addMouseListener(new java.awt.event.MouseAdapter()
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if(table.getValueAt(row,col).toString().trim().contains(".java")) {
					Main.callStatus("Wait...");
					JFrame fr = new JFrame(table.getValueAt(row,col).toString().trim());
					fr.setSize(1000, 500);
					fr.setResizable(true);
					fr.setLocationRelativeTo(null);
					fr.setVisible(true);								
					JTableHeaderCheckBox checkbox1 = new JTableHeaderCheckBox();
					JScrollPane sp = new JScrollPane();
					fr.getContentPane().remove(sp);				
					sp = new JScrollPane(checkbox1.affectedSyntax(viewer2DataList,headerList,table.getValueAt(row,col).toString()));
					fr.getContentPane().add(sp);
					fr.setVisible(true);
				}
				//checkbox selected item
				else
				{
					View.logger.info("selected rows-->"+table.getModel().getValueAt(row, 1).toString());
					if(table.getValueAt(row, col).toString().equalsIgnoreCase("true")) {
					selectedFiles.add(table.getModel().getValueAt(row, 1).toString());
					removedFiles.remove(table.getModel().getValueAt(row, 1).toString());
					}
					else {
						selectedFiles.remove(table.getModel().getValueAt(row, 1).toString());
						removedFiles.add(table.getModel().getValueAt(row, 1).toString());
					}
					View.logger.info("selectedFileMulti"+selectedFiles);
					View.logger.info("removedFilesMulti"+removedFiles);
				}				
				View.logger.info("Value in the cells clicked :" + " " + table.getValueAt(row, col).toString());
			}

		}
		);
		return table;
	}
		
	class MyItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			View.logger.info("checked1-->");
			Object source = e.getSource();
			if (!(source instanceof AbstractButton))
				return;
			boolean checked = e.getStateChange() == ItemEvent.SELECTED;
			//if select all is done
			if(checked) {
				selectedFiles.clear();
				selectedFiles.add("ALL");
				removedFiles.clear();
			}
			else {
				selectedFiles.clear();
				selectedFiles.add("NONE");
				removedFiles.clear();
			}
			View.logger.info("checked-->"+checked);
			View.logger.info("selectedFiles-->"+selectedFiles);
			for (int x = 0, y = table.getRowCount(); x < y; x++) {
				table.setValueAt(Boolean.valueOf(checked), x, 0);
				View.logger.info("table get session-->"+table.getValueAt(x, 0));
			}
		}
	}

	class MyItemListener1 implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			View.logger.info("checked1-->");
			Object source = e.getSource();
			if (!(source instanceof AbstractButton))
				return;
			boolean checked1 = e.getStateChange() == ItemEvent.SELECTED;
			//if select all is done
			if(checked1) {
				selectedFiles.clear();
				selectedFiles.add("ALL");
				removedFiles.clear();
			}
			else {
				selectedFiles.clear();
				selectedFiles.add("NONE");
				removedFiles.clear();
			}
			View.logger.info("checked-->"+checked1);
			View.logger.info("selectedFiles-->"+selectedFiles);
			for (int x = 0, y = tableAssist.getRowCount(); x < y; x++) {
				tableAssist.setValueAt(Boolean.valueOf(checked1), x, 0);
				View.logger.info("table get session-->"+tableAssist.getValueAt(x, 0));
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JTableHeaderCheckBox().buildGUI(null, null);
				new JTableHeaderCheckBox().buildGUIAutomatedReport();
			}
		});
	}
}

@SuppressWarnings("serial")
class CheckBoxHeader extends JCheckBox implements TableCellRenderer, MouseListener {
	protected CheckBoxHeader boxHeader;
	protected int column;
	protected boolean mousePressed = false;

	/**
	 * @param listener
	 */
	public CheckBoxHeader(ItemListener listener) {
		boxHeader = this;
		boxHeader.addItemListener(listener);
	}

	@Override
	public Component getTableCellRendererComponent(JTable jTable, Object value, boolean isSelected, boolean hasFocus,int row, int column1) {
		if (jTable != null) {
			JTableHeader jTableHeader = jTable.getTableHeader();
			if (jTableHeader != null) {
				boxHeader.setForeground(jTableHeader.getForeground());
				boxHeader.setBackground(jTableHeader.getBackground());
				boxHeader.setFont(jTableHeader.getFont());
				jTableHeader.addMouseListener(boxHeader);
			}
		}
		setColumn(column1);
		boxHeader.setText("");
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		return boxHeader;
	}

	/**
	 * @param column
	 */
	protected void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param e
	 */
	protected void handleClickEvent(MouseEvent e) {
		if (mousePressed) {
			mousePressed = false;
			JTableHeader header = (JTableHeader) (e.getSource());
			JTable jTable = header.getTable();
			TableColumnModel columnModel = jTable.getColumnModel();
			int viewColumn1 = columnModel.getColumnIndexAtX(e.getX());
			int columnObj = jTable.convertColumnIndexToModel(viewColumn1);

			if (viewColumn1 == this.column && e.getClickCount() == 1 && columnObj != -1) {
				doClick();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		handleClickEvent(e);
		((JTableHeader) e.getSource()).repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//to be implemented if required
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//to be implemented if required
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//to be implemented if required
	}
}

class JTPRenderer extends JTextPane implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText(value.toString());
		return this;
	}
}

class WrapColListener implements TableColumnModelListener {

	JTable mTable;
	WrapColListener(JTable table) {
		mTable = table;
	}

	void refreshRowHeights() {
		int nrows = mTable.getRowCount();
		int ncols = mTable.getColumnCount();
		int intercellWidth = mTable.getIntercellSpacing().width;
		int intercellHeight = mTable.getIntercellSpacing().height;
		TableColumnModel colModel = mTable.getColumnModel();
		// these null checks are due to concurrency considerations... much can change
		// between the col margin change
		// event and the call to refresh_row_heights (although not in this SSCCE...)
		if (colModel == null)
			return;
		// go through ALL rows, calculating row heights
		for (int row = 0; row < nrows; row++) {
			int prefRowHeight = 1;
			// calculate row heights from cell, setting width constraint by means of
			// setBounds...
			for (int col = 0; col < ncols; col++) {
				Object value = mTable.getValueAt(row, col);
				TableCellRenderer renderer = mTable.getCellRenderer(row, col);
				if (renderer == null)
					return;
				Component comp = renderer.getTableCellRendererComponent(mTable, value, false, false, row, col);
				if (comp == null)
					return;
				int colWidth = colModel.getColumn(col).getWidth();
				// constrain width of component
				comp.setBounds(new Rectangle(0, 0, colWidth - intercellWidth, Integer.MAX_VALUE));
				// getPreferredSize then returns "true" height as a function of attributes (e.g.
				// font) and word-wrapping
				int prefCellHeight = comp.getPreferredSize().height + intercellHeight;
				if (prefCellHeight > prefRowHeight) {
					prefRowHeight = prefCellHeight;
				}
			}
			if (prefRowHeight != mTable.getRowHeight(row)) {
				mTable.setRowHeight(row, prefRowHeight);
			}
		}
	}

	public void columnAdded(TableColumnModelEvent e) {
		refreshRowHeights();
	}

	public void columnRemoved(TableColumnModelEvent e) {		
		// probably no need to call refresh_row_heights
	}

	public void columnMoved(TableColumnModelEvent e) {		
		// probably no need to call refresh_row_heights
	}

	public void columnMarginChanged(ChangeEvent e) {
		refreshRowHeights();
	}

	public void columnSelectionChanged(ListSelectionEvent e) {		
		// probably no need to call refresh_row_heights
	}

}

class HeaderRenderer implements TableCellRenderer {
    DefaultTableCellRenderer renderer;
    /**
     * @param table
     */
    public HeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer)
            table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
    }

    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int col) {
        return renderer.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, col);
    }
}
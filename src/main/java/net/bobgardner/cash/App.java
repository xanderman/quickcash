// Copyright 2009 Bob Gardner.
//
// This file is part of QuickCash.
//
// QuickCash is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// QuickCash is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with QuickCash. If not, see <http://www.gnu.org/licenses/>.

package net.bobgardner.cash;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Hello world!
 * 
 * @author wrg007 (Bob Gardner)
 */
public class App {
  private static void addComponentsToPane(Container pane) {
    pane.setLayout(new GridBagLayout());
    JButton button;
    GridBagConstraints c;

    String[] accountStrings =
        {"Wells Fargo checking", "ING Savings", "Petty Cash", "New account..."};
    JComboBox accountList = new JComboBox(accountStrings);
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    pane.add(accountList, c);

    JButton mbutton = new JButton("Details");
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    pane.add(mbutton, c);

    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
    c = new GridBagConstraints();
    c.gridx = 2;
    c.gridy = 0;
    c.weightx = 1.0;
    c.anchor = GridBagConstraints.LINE_START;
    c.fill = GridBagConstraints.VERTICAL;
    pane.add(separator, c);

    JLabel label = new JLabel("Transactions:");
    c = new GridBagConstraints();
    c.gridx = 3;
    c.gridy = 0;
    pane.add(label, c);

    button = new JButton("Add");
    c = new GridBagConstraints();
    c.gridx = 4;
    c.gridy = 0;
    pane.add(button, c);

    button = new JButton("Transfer");
    c = new GridBagConstraints();
    c.gridx = 5;
    c.gridy = 0;
    pane.add(button, c);

    button = new JButton("Delete");
    c = new GridBagConstraints();
    c.gridx = 6;
    c.gridy = 0;
    pane.add(button, c);

    separator = new JSeparator(SwingConstants.HORIZONTAL);
    c = new GridBagConstraints();
    c.gridy = 1;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.fill = GridBagConstraints.HORIZONTAL;
    pane.add(separator, c);

    String[] columnNames =
        {"", "Date", "Category", "Payee", "Description", "Check Nr", "Amount", "Total"};
    Object[][] data =
        {
            {new ImageIcon("disclosurePanelClosed.png"), "2 June 2009", "Groceries", "Safeway",
                "Chicken", "", "(4.50)", "1234.56"},
            {new ImageIcon("disclosurePanelClosed.png"), "3 June 2009", "Tithing", "LDS",
                "2 paychecks", "101", "(1200.00)", "34.56"},
            {new ImageIcon("disclosurePanelClosed.png"), "3 June 2009", "Salary", "Google",
                "Paycheck", "", "1400.00", "1434.56"}};
    final JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane =
        new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    table.setFillsViewportHeight(true);
    table.setPreferredScrollableViewportSize(new Dimension(775, 325));
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    table.getColumnModel().getColumn(0).setMaxWidth(1);
    table.getColumnModel().getColumn(0).setCellRenderer(new MyRenderer());
    table.getColumnModel().getColumn(0).setCellEditor(null);
    table.getColumnModel().getColumn(1).setPreferredWidth(83);
    table.getColumnModel().getColumn(2).setPreferredWidth(71);
    table.getColumnModel().getColumn(3).setPreferredWidth(65);
    table.getColumnModel().getColumn(4).setPreferredWidth(339);
    table.getColumnModel().getColumn(5).setPreferredWidth(61);
    table.getColumnModel().getColumn(6).setPreferredWidth(72);
    table.getColumnModel().getColumn(7).setPreferredWidth(69);
    c = new GridBagConstraints();
    c.gridy = 2;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(0, 5, 5, 5);
    pane.add(scrollPane, c);

    mbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++) {
          System.out.println(i + ": " + table.getColumnModel().getColumn(i).getWidth());
        }
      }
    });
  }

  private static class MyRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      ImageIcon icon = (ImageIcon) value;
      setIcon(icon);
      return this;
    }
  }

  private static void createAndShowGui() {
    JFrame frame = new JFrame("Demo!!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    addComponentsToPane(frame.getContentPane());

    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGui();
      }
    });
  }
}

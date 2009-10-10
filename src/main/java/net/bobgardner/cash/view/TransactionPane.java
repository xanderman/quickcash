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

package net.bobgardner.cash.view;

import static com.google.common.base.Preconditions.checkArgument;

import net.bobgardner.cash.model.Account;
import net.bobgardner.cash.model.LineItem;
import net.bobgardner.cash.model.Transaction;

import java.awt.Dimension;
import java.util.SortedSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * Component for displaying {@link Transaction}s.
 * 
 * @author wrg007 (Bob Gardner)
 */
class TransactionPane extends JScrollPane {
  private final JTable table;
  private Account account;

  public TransactionPane(Account account) {
    super(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    this.account = account;
    table = new JTable();
    setViewportView(table);
    refresh();
  }

  public void setAccount(Account account) {
    this.account = account;
    refresh();
  }

  private void refresh() {
    table.setModel(new TransactionTableModel(account.getTransactions()));
    table.setFillsViewportHeight(true);
    table.setPreferredScrollableViewportSize(new Dimension(775, 325));
    table.getTableHeader().setReorderingAllowed(false);
    table.setRowHeight(23);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    ListSelectionListener listener = new DisclosureSelectionListener(table);
    table.getSelectionModel().addListSelectionListener(listener);
    table.getColumnModel().getSelectionModel().addListSelectionListener(listener);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
    for (int i = 0; i < Columns.values().length; i++) {
      Columns.values()[i].configureColumn(table.getColumnModel().getColumn(i));
    }
  }

  /**
   * Table model that holds a set of transactions and arrays them based on the
   * {@link Columns} enum.
   * 
   * @author wrg007 (Bob Gardner)
   */
  private static class TransactionTableModel extends AbstractTableModel {
    private final SortedSet<Transaction> rows;
    private final Columns[] columns = Columns.values();

    public TransactionTableModel(SortedSet<Transaction> rows) {
      this.rows = rows;
    }

    @Override
    public int getColumnCount() {
      return columns.length;
    }

    @Override
    public String getColumnName(int column) {
      return columns[column].getName();
    }

    @Override
    public Class<?> getColumnClass(int column) {
      return columns[column].getClass();
    }

    @Override
    public int getRowCount() {
      return rows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      return columns[columnIndex].getValue(get(rows, rowIndex));
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      // TODO!
    }

    @Override
    public boolean isCellEditable(int row, int col) {
      // Covers roll-up case
      if (getValueAt(row, col).toString().equals("...")) return false;
      // TODO: Roll-up amount should not be editable
      return columns[col].isEditable();
    }

    private static <T> T get(SortedSet<T> from, int index) {
      checkArgument(index < from.size());
      int i = 0;
      for (T item : from) {
        if (i == index) return item;
        i++;
      }
      throw new IndexOutOfBoundsException();
    }
  }

  /**
   * Custom slection listener that knows how to toggle disclosure of
   * {@link LineItem}s.
   * 
   * @author wrg007 (Bob Gardner)
   */
  private static class DisclosureSelectionListener implements ListSelectionListener {
    private final JTable table;
    private boolean isAdjusting = false;

    public DisclosureSelectionListener(JTable table) {
      this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
      if (isAdjusting) return;
      final int row = table.getSelectedRow();
      if (table.getSelectedColumn() == 0 && row >= 0) {
        isAdjusting = true;
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            ((DisclosureIcon) table.getModel().getValueAt(row, 0)).toggle();
            table.removeRowSelectionInterval(row, row);
            isAdjusting = false;
          }
        });
      }
    }
  }
}

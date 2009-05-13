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

import net.bobgardner.cash.model.Account;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.*;

/**
 * A combo box for selecting which {@link Account} to view (or to create a new
 * one).
 * 
 * @author wrg007 (Bob Gardner)
 */
class AccountListBox extends JComboBox {
  private static final JSeparator SEPARATOR = new JSeparator(SwingConstants.HORIZONTAL);
  private static final String NEW_ACCOUNT = "New account...";
  private final Set<Account> accounts;

  /**
   * @param accounts the set of {@link Account}s to display
   */
  public AccountListBox(SortedSet<Account> accounts) {
    super();
    this.accounts = accounts;
    refreshList();
    setRenderer(new AccountComboBoxRenderer());
    addActionListener(new AccountComboBoxListener(this));
  }

  /**
   * Refresh the list of accounts
   */
  private void refreshList() {
    removeAllItems();
    for (Account account : accounts) {
      addItem(account);
    }
    addItem(SEPARATOR);
    addItem(NEW_ACCOUNT);
  }

  public Account getSelectedAccount() {
    return (Account) getSelectedItem();
  }

  private static class AccountComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean cellHasFocus) {
      if (value instanceof JSeparator) {
        JSeparator separator = (JSeparator) value;
        separator.setEnabled(false);
        return separator;
      }
      return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
  }

  private static class AccountComboBoxListener implements ActionListener {
    private final JComboBox combo;
    private int lastSel = 0;

    public AccountComboBoxListener(JComboBox combo) {
      this.combo = combo;
      combo.setSelectedIndex(lastSel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (combo.getSelectedItem() instanceof JSeparator)
        combo.setSelectedIndex(lastSel);
      else
        lastSel = combo.getSelectedIndex();
    }
  }
}

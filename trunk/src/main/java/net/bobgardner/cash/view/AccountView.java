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

import net.bobgardner.cash.model.Cashbox;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

/**
 * @author wrg007 (Bob Gardner)
 * 
 */
public class AccountView extends JFrame {
  public AccountView() {
    super("QuickCash");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    addComponentsToPane(getContentPane());
    pack();
  }

  private void addComponentsToPane(Container pane) {
    pane.setLayout(new GridBagLayout());
    AbstractButton button;
    GridBagConstraints c;

    AccountListBox accountList = new AccountListBox(Cashbox.INSTANCE.getAccounts());
    c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    pane.add(accountList, c);

    button = Buttons.newAccountDetailsButton("Details");
    c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    pane.add(button, c);

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

    button = Buttons.newAddTransactionButton("Add");
    c = new GridBagConstraints();
    c.gridx = 4;
    c.gridy = 0;
    pane.add(button, c);

    button = Buttons.newAddTransferButton("Transfer");
    c = new GridBagConstraints();
    c.gridx = 5;
    c.gridy = 0;
    pane.add(button, c);

    button = Buttons.newDeleteTransactionButton("Delete");
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

    JScrollPane scrollPane = new TransactionPane(accountList.getSelectedAccount());
    c = new GridBagConstraints();
    c.gridy = 2;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.insets = new Insets(0, 5, 5, 5);
    pane.add(scrollPane, c);
  }
}

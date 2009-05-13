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

import net.bobgardner.cash.model.Account;
import net.bobgardner.cash.model.Category;
import net.bobgardner.cash.model.LineItem;
import net.bobgardner.cash.model.Transaction;
import net.bobgardner.cash.view.AccountView;

import org.joda.time.DateMidnight;

import java.math.BigDecimal;

/**
 * Main class for QuickCash.
 * 
 * @author wrg007 (Bob Gardner)
 */
public class App {
  public static void main(String[] args) {
    // TODO: Load data from database
    makeFakeData();

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new AccountView().setVisible(true);
      }
    });
  }

  private static void makeFakeData() {
    Category c0 = Category.newCategory("Name0", "Desc0");
    Category c1 = Category.newCategory("Name1", "Desc1");
    Account account =
        Account.newAccount("WF Checking", "Wells Fargo", "123456789", Account.Type.CHECKING, "");
    Transaction t = Transaction.newTransaction(account, new DateMidnight(), "Albertsons", "101");
    LineItem.newLineItem(t, BigDecimal.TEN, c0, "Line Item 0");
    LineItem.newLineItem(t, BigDecimal.TEN, c1, "Line Item 1");
    t = Transaction.newTransaction(account, new DateMidnight(), "Sams Club", "");
    LineItem.newLineItem(t, new BigDecimal("-10.00"), c1, "Line Item 0");
    Account.newAccount("WF Savings", "Wells Fargo", "234567891", Account.Type.SAVINGS, "");
  }
}

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

package net.bobgardner.cash.model;

import junit.framework.TestCase;

import org.joda.time.DateMidnight;

import java.math.BigDecimal;
import java.util.SortedSet;

/**
 * Tests for {@link Transaction}.
 * 
 * @author wrg007 (Bob Gardner)
 */
public class TransactionTest extends TestCase {
  private Account account;
  private Category category;

  @Override
  public void setUp() {
    Cashbox.INSTANCE.clearAccounts();
    Cashbox.INSTANCE.clearCategories();
    Account.resetCounter();
    Transaction.resetCounter();
    LineItem.resetCounter();
    Category.resetCounter();
    account =
        Account.newAccount(Cashbox.INSTANCE, "name", "institution", "number",
            Account.Type.CHECKING, "notes");
    category = Category.newCategory("name", "desc");
  }

  public void testInstantiation() {
    Transaction.newTransaction(account, new DateMidnight("2008-01-12"), "payee", "checkNr");
  }

  public void testInstantiation_nullDate() {
    try {
      Transaction.newTransaction(account, null, "payee", "checkNr");
      fail("NullPointerException expected for null date");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullPayee() {
    try {
      Transaction.newTransaction(account, new DateMidnight(), null, "checkNr");
      fail("NullPointerException expected for null payee");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullCheckNr() {
    try {
      Transaction.newTransaction(account, new DateMidnight(), "payee", null);
      fail("NullPointerException epxected for null check number");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testId() {
    Transaction t =
        Transaction.newTransaction(account, new DateMidnight("2008-01-12"), "payee", "checkNr");
    assertEquals(0, t.getId());
    t = Transaction.newTransaction(account, new DateMidnight("2009-12-4"), "payee", "checkNr");
    assertEquals(1, t.getId());
    t = Transaction.newTransaction(account, new DateMidnight("1998-3-4"), "payee", "checkNr");
    assertEquals(2, t.getId());
  }

  public void testDate() {
    Transaction t =
        Transaction.newTransaction(account, new DateMidnight("2010-03-25"), "payee", "checkNr");
    assertEquals(new DateMidnight("2010-03-25"), t.getDate());
    DateMidnight d = new DateMidnight("1492-08-18");

    t.setDate(d);
    assertEquals(new DateMidnight("1492-08-18"), t.getDate());

    try {
      t.setDate(null);
      fail("NullPointerException expected for null date");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testPayee() {
    Transaction t = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertEquals("payee", t.getPayee());

    t.setPayee("another one");
    assertEquals("another one", t.getPayee());

    try {
      t.setPayee(null);
      fail("NullPointerException expected for null payee");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testCheckNr() {
    Transaction t = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertEquals("checkNr", t.getCheckNr());

    t.setCheckNr("42");
    assertEquals("42", t.getCheckNr());

    try {
      t.setCheckNr(null);
      fail("NullPointerExpection expected for null check number");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testDescription() {
    Transaction t =
        Transaction.newTransaction(account, new DateMidnight("1992-04-29"), "payee", "checkNr");
    assertEquals("...", t.getDescription());
  }

  public void testItems() {
    Transaction t = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    LineItem item = LineItem.newLineItem(t, new BigDecimal("3"), category, "desc");

    SortedSet<LineItem> items = t.getItems();
    assertEquals(1, items.size());
    assertEquals(item, items.iterator().next());

    LineItem.deleteLineItem(item);
    try {
      t.addItem(item);
      fail("IllegalArgumentException expected for invalid LineItem");
    } catch (IllegalArgumentException e) {
      // exception expected
    }
    assertEquals(0, items.size());

    LineItem.newLineItem(t, new BigDecimal("1"), category, "desc");
    LineItem.newLineItem(t, BigDecimal.TEN, category, "desc");
    assertEquals(2, items.size());
  }

  public void testCompare() {
    Transaction t1 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    Transaction t2 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertTrue(t1.compareTo(t2) < 0);
    assertTrue(t2.compareTo(t1) > 0);

    Transaction.resetCounter();
    t2 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertTrue(t1.compareTo(t2) == 0);
    assertTrue(t2.compareTo(t1) == 0);

    t1 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertTrue(t1.compareTo(t2) > 0);
    assertTrue(t2.compareTo(t1) < 0);
  }

  public void testEquals() {
    Transaction t1 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    Transaction.resetCounter();
    Transaction t2 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertEquals(t1, t2);
    assertEquals(t2, t1);

    t2 = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    assertFalse(t1.equals(t2));
    assertFalse(t2.equals(t1));
  }
}

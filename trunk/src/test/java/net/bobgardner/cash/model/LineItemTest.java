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

/**
 * Tests for {@link LineItem}
 * 
 * @author wrg007 (Bob Gardner)
 */
public class LineItemTest extends TestCase {
  private Account account;
  private Transaction transaction;
  private Category category;

  @Override
  public void setUp() {
    Cashbox.INSTANCE.clearAccounts();
    Cashbox.INSTANCE.clearCategories();
    Account.resetCounter();
    Transaction.resetCounter();
    LineItem.resetCounter();
    Category.resetCounter();
    account = Account.newAccount("name", "institution", "number", Account.Type.CHECKING, "notes");
    transaction = Transaction.newTransaction(account, new DateMidnight(), "payee", "checkNr");
    category = Category.newCategory("name", "desc");
  }

  public void testInstantiation() {
    LineItem.newLineItem(transaction, new BigDecimal("7.45"), category, "desc");
  }

  public void testInstantiation_nullAmount() {
    try {
      LineItem.newLineItem(transaction, null, category, "desc");
      fail("NullPointerException expected for null amount");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullCategory() {
    try {
      LineItem.newLineItem(transaction, new BigDecimal("1"), null, "desc");
      fail("NullPointerException expected for null category");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullDescription() {
    try {
      LineItem.newLineItem(transaction, new BigDecimal("1"), category, null);
      fail("NullPointerException expected for null description");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testId() {
    LineItem item = LineItem.newLineItem(transaction, new BigDecimal("3"), category, "desc");
    assertEquals(0, item.getId());
    item = LineItem.newLineItem(transaction, new BigDecimal("4"), category, "desc");
    assertEquals(1, item.getId());
    item = LineItem.newLineItem(transaction, new BigDecimal("4"), category, "desc");
    assertEquals(2, item.getId());
  }

  public void testAmount() {
    LineItem item = LineItem.newLineItem(transaction, new BigDecimal("7"), category, "desc");
    assertEquals(new BigDecimal("7"), item.getAmount());

    item.setAmount(new BigDecimal("6"));
    assertEquals(new BigDecimal("6"), item.getAmount());

    try {
      item.setAmount(null);
      fail("NullPointerException expected for null amount");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testCategory() {
    LineItem item = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertEquals(category, item.getCategory());

    Category cat = Category.newCategory("Food", "for eating");
    item.setCategory(cat);
    assertEquals(cat, item.getCategory());

    try {
      item.setCategory(null);
      fail("NullPointerException expected for null category");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testCompare() {
    LineItem item1 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    LineItem.resetCounter();
    LineItem item2 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertEquals(0, item1.compareTo(item2));
    assertEquals(0, item2.compareTo(item1));

    item1 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertTrue(item1.compareTo(item2) > 0);
    assertTrue(item2.compareTo(item1) < 0);

    item2 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertTrue(item1.compareTo(item2) < 0);
    assertTrue(item2.compareTo(item1) > 0);
  }

  public void testEquals() {
    LineItem item1 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    LineItem.resetCounter();
    LineItem item2 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertEquals(item1, item2);
    assertEquals(item2, item1);

    item1 = LineItem.newLineItem(transaction, new BigDecimal("1"), category, "desc");
    assertFalse(item1.equals(item2));
    assertFalse(item2.equals(item1));
  }
}

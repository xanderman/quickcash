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
  public void testInstantiation() {
    new Transaction(-1, new DateMidnight("2008-01-12"));
  }

  public void testInstantiation_nullDate() {
    try {
      new Transaction(-1, null);
    } catch (NullPointerException e) {
      // exception expected
      return;
    }
    fail("NullPointerException expected for null date");
  }

  public void testId() {
    Transaction t = new Transaction(-1, new DateMidnight("2008-01-12"));
    assertEquals(-1, t.getId());
    t = new Transaction(3, new DateMidnight("2009-12-4"));
    assertEquals(3, t.getId());
    t = new Transaction(-7, new DateMidnight("1998-3-4"));
    assertEquals(-7, t.getId());
  }

  public void testDate() {
    Transaction t = new Transaction(-1, new DateMidnight("2010-03-25"));
    assertEquals(new DateMidnight("2010-03-25"), t.getDate());
    DateMidnight d = new DateMidnight("1492-08-18");
    t.setDate(d);
    assertEquals(d, t.getDate());
  }

  public void testDescription() {
    Transaction t = new Transaction(18, new DateMidnight("1992-04-29"));
    assertEquals("...", t.getDescription());
  }

  public void testItems() {
    Transaction t = new Transaction(0, new DateMidnight());
    LineItem item = new LineItem(-1, new BigDecimal("3"), new Category(-1, "name", "desc"), "desc");
    t.addItem(item);

    SortedSet<LineItem> items = t.getItems();
    assertEquals(1, items.size());
    assertEquals(item, items.iterator().next());

    boolean passed = false;
    try {
      t.addItem(new LineItem(-1, new BigDecimal("5"), new Category(-2, "name", "desc"), "desc"));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue("IllegalArgumentException expected for duplicate LineItem", passed);
    assertEquals(1, items.size());

    t.addItem(new LineItem(4, new BigDecimal("1"), new Category(9, "", ""), "desc"));
    t.addItem(new LineItem(19, BigDecimal.TEN, new Category(8, "", ""), "desc"));
    assertEquals(3, items.size());
  }

  public void testCompare() {
    Transaction t1 = new Transaction(5, new DateMidnight());
    Transaction t2 = new Transaction(-45, new DateMidnight());
    assertTrue(t1.compareTo(t2) > 0);
    assertTrue(t2.compareTo(t1) < 0);

    t2 = new Transaction(5, new DateMidnight());
    assertTrue(t1.compareTo(t2) == 0);
    assertTrue(t2.compareTo(t1) == 0);

    t2 = new Transaction(6, new DateMidnight());
    assertTrue(t1.compareTo(t2) < 0);
    assertTrue(t2.compareTo(t1) > 0);
  }

  public void testEquals() {
    Transaction t1 = new Transaction(5, new DateMidnight());
    Transaction t2 = new Transaction(5, new DateMidnight());
    assertEquals(t1, t2);
    assertEquals(t2, t1);

    t2 = new Transaction(6, new DateMidnight());
    assertFalse(t1.equals(t2));
    assertFalse(t2.equals(t1));
  }
}

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

import java.math.BigDecimal;

/**
 * Tests for {@link LineItem}
 * 
 * @author wrg007 (Bob Gardner)
 */
public class LineItemTest extends TestCase {
  public void testInstantiation() {
    new LineItem(-1, new BigDecimal("7.45"), new Category(-1, "name", "desc"));
  }

  public void testInstantiation_nullAmount() {
    boolean passed = false;
    try {
      new LineItem(5, null, new Category(3, "name", "desc"));
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_invalidAmount() {
    boolean passed = false;
    try {
      new LineItem(0, new BigDecimal("0"), new Category(-1, "name", "desc"));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);

    passed = false;
    try {
      new LineItem(1, new BigDecimal("-.3"), new Category(-1, "name", "desc"));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullCategory() {
    try {
      new LineItem(15, new BigDecimal("1"), null);
    } catch (NullPointerException e) {
      // exception expected
      return;
    }
    assertTrue(false);
  }

  public void testId() {
    LineItem item = new LineItem(-1, new BigDecimal("3"), new Category(-1, "name", "desc"));
    assertEquals(-1, item.getId());
    item = new LineItem(3, new BigDecimal("4"), new Category(-1, "name", "desc"));
    assertEquals(3, item.getId());
    item = new LineItem(-4, new BigDecimal("4"), new Category(-1, "name", "desc"));
    assertEquals(-4, item.getId());
  }

  public void testAmount() {
    LineItem item = new LineItem((-1), new BigDecimal("7"), new Category((-1), "name", "desc"));
    assertEquals(new BigDecimal("7"), item.getAmount());
    assertEquals(new BigDecimal("7"), item.getAmount());

    item.setAmount(new BigDecimal("6"));
    assertEquals(new BigDecimal("6"), item.getAmount());

    boolean passed = false;
    try {
      item.setAmount(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);

    passed = false;
    try {
      item.setAmount(new BigDecimal("0"));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);

    passed = false;
    try {
      item.setAmount(new BigDecimal("-15"));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testCategory() {
    LineItem item = new LineItem(-1, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertEquals(new Category(-1, "name", "desc"), item.getCategory());

    item.setCategory(new Category(2, "Food", "for eating"));
    assertEquals(new Category(2, "Food", "for eating"), item.getCategory());

    boolean passed = false;
    try {
      item.setCategory(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testCompare() {
    LineItem item1 = new LineItem(-1, new BigDecimal("1"), new Category(-1, "name", "desc"));
    LineItem item2 = new LineItem(-1, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertEquals(0, item1.compareTo(item2));
    assertEquals(0, item2.compareTo(item1));

    item1 = new LineItem(0, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertTrue(item1.compareTo(item2) > 0);
    assertTrue(item2.compareTo(item1) < 0);

    item1 = new LineItem(-2, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertTrue(item1.compareTo(item2) < 0);
    assertTrue(item2.compareTo(item1) > 0);
  }

  public void testEquals() {
    LineItem item1 = new LineItem(-1, new BigDecimal("1"), new Category(-1, "name", "desc"));
    LineItem item2 = new LineItem(-1, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertEquals(item1, item2);
    assertEquals(item2, item1);

    item1 = new LineItem(0, new BigDecimal("1"), new Category(-1, "name", "desc"));
    assertFalse(item1.equals(item2));
    assertFalse(item2.equals(item1));
  }
}

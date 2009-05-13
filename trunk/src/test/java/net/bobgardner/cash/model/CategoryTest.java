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

/**
 * Tests for {@link Category}
 * 
 * @author wrg007 (Bob Gardner)
 */
public class CategoryTest extends TestCase {
  @Override
  public void setUp() {
    Category.resetCounter();
    Cashbox.INSTANCE.clearCategories();
  }

  public void testInstantiation() {
    Category.newCategory("name", "desc");
  }

  public void testInstantiation_nullName() {
    try {
      Category.newCategory(null, "desc");
      fail("NullPointerException expected for null name");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullDescription() {
    try {
      Category.newCategory("name", null);
      fail("NullPointerException expected for null description");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testId() {
    Category cat = Category.newCategory("name", "desc");
    assertEquals(0, cat.getId());
    cat = Category.newCategory("name1", "desc1");
    assertEquals(1, cat.getId());
    cat = Category.newCategory("name2", "desc2");
    assertEquals(2, cat.getId());
  }

  public void testName() {
    Category cat = Category.newCategory("name", "desc");
    assertEquals("name", cat.getName());
    cat.setName("another");
    assertEquals("another", cat.getName());

    try {
      cat.setName(null);
      fail("NullPointerException expected for null name");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testDescription() {
    Category cat = Category.newCategory("name", "desc");
    assertEquals("desc", cat.getDescription());
    cat.setDescription("description");
    assertEquals("description", cat.getDescription());

    try {
      cat.setDescription(null);
      fail("NullPointerException expected for null description");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testCompare() {
    Category cat1 = Category.newCategory("a", "desc");
    Category cat2 = Category.newCategory("b", "asdf");

    assertTrue(cat1.compareTo(cat2) < 0);
    assertTrue(cat2.compareTo(cat1) > 0);

    cat1 = Category.newCategory("c", "desc");
    assertTrue(cat1.compareTo(cat2) > 0);
    assertTrue(cat2.compareTo(cat1) < 0);
  }

  public void testEquals() {
    Category cat1 = Category.newCategory("a", "desc");
    Category.resetCounter();
    Category cat2 = Category.newCategory("b", "desc");
    assertEquals(cat1, cat2);
    assertEquals(cat2, cat1);

    cat1 = Category.newCategory("b", "desc");
    assertFalse(cat1.equals(cat2));
    assertFalse(cat2.equals(cat1));
  }
}

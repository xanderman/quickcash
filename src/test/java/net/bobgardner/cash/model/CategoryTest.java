// Copyright 2008 Bob Gardner. All Rights Reserved.

package net.bobgardner.cash.model;

import junit.framework.TestCase;

/**
 * Tests for {@link Category}
 * 
 * @author bobgardner (Bob Gardner)
 */
public class CategoryTest extends TestCase {
  public void testInstantiation() {
    new Category(0, "name", "desc");
  }

  public void testInstantiation_nullName() {
    boolean passed = false;
    try {
      new Category(0, null, "desc");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullDescription() {
    boolean passed = false;
    try {
      new Category(0, "name", null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testId() {
    Category cat = new Category(0, "name", "desc");
    assertEquals(0, cat.getId());
    cat = new Category(-4, "name", "desc");
    assertEquals(-4, cat.getId());
    cat = new Category(5, "name", "desc");
    assertEquals(5, cat.getId());
  }

  public void testName() {
    Category cat = new Category(0, "name", "desc");
    assertEquals("name", cat.getName());
    cat.setName("another");
    assertEquals("another", cat.getName());

    boolean passed = false;
    try {
      cat.setName(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testDescription() {
    Category cat = new Category(0, "name", "desc");
    assertEquals("desc", cat.getDescription());
    cat.setDescription("description");
    assertEquals("description", cat.getDescription());

    boolean passed = false;
    try {
      cat.setDescription(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testCompare() {
    Category cat1 = new Category(0, "b", "desc");
    Category cat2 = new Category(0, "b", "asdf");
    assertEquals(cat1, cat2);
    assertEquals(cat2, cat1);

    cat1 = new Category(0, "a", "desc");
    assertTrue(cat1.compareTo(cat2) < 0);
    assertTrue(cat2.compareTo(cat1) > 0);

    cat1 = new Category(0, "c", "desc");
    assertTrue(cat1.compareTo(cat2) > 0);
    assertTrue(cat2.compareTo(cat1) < 0);
  }

  public void testEquals() {
    Category cat1 = new Category(0, "a", "desc");
    Category cat2 = new Category(0, "b", "desc");
    assertEquals(cat1, cat2);
    assertEquals(cat2, cat1);

    cat1 = new Category(1, "b", "desc");
    assertFalse(cat1.equals(cat2));
    assertFalse(cat2.equals(cat1));
  }
}

// Copyright 2008 Bob Gardner. All Rights Reserved.

package net.bobgardner.cash.model;

import junit.framework.TestCase;

import org.joda.time.DateMidnight;

import java.math.BigDecimal;
import java.util.SortedSet;

/**
 * Tests for {@link Transaction}.
 * 
 * @author bobgardner (Bob Gardner)
 */
public class TransactionTest extends TestCase {
  public void testInstantiation() {
    new Transaction(-1, new DateMidnight("2008-01-12"), "desc");
  }

  public void testInstantiation_nullDate() {
    boolean passed = false;
    try {
      new Transaction(-1, null, "desc");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullDescription() {
    boolean passed = false;
    try {
      new Transaction(-1, new DateMidnight("208-01-12"), null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testId() {
    Transaction t = new Transaction(-1, new DateMidnight("2008-01-12"), "desc");
    assertEquals(-1, t.getId());
    t = new Transaction(3, new DateMidnight("2009-12-4"), "desc");
    assertEquals(3, t.getId());
    t = new Transaction(-7, new DateMidnight("1998-3-4"), "desc");
    assertEquals(-7, t.getId());
  }

  public void testDate() {
    Transaction t = new Transaction(-1, new DateMidnight("2010-03-25"), "desc");
    assertEquals(new DateMidnight("2010-03-25"), t.getDate());
    DateMidnight d = new DateMidnight("1492-08-18");
    t.setDate(d);
    assertEquals(d, t.getDate());
  }

  public void testDescription() {
    Transaction t = new Transaction(18, new DateMidnight("1992-04-29"), "desc");
    assertEquals("desc", t.getDescription());
    String desc = "asdf;lkje";
    t.setDescription(desc);
    assertEquals(desc, t.getDescription());
  }

  public void testItems() {
    Transaction t = new Transaction(0, new DateMidnight(), "");
    LineItem item = new LineItem(-1, new BigDecimal("3"), new Category(-1, "name", "desc"));
    t.addItem(item);

    SortedSet<LineItem> items = t.getItems();
    assertEquals(1, items.size());
    assertEquals(item, items.iterator().next());

    boolean passed = false;
    try {
      t.addItem(new LineItem(-1, new BigDecimal("5"), new Category(-2, "name", "desc")));
    } catch (IllegalArgumentException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);

    t.addItem(new LineItem(4, new BigDecimal("1"), new Category(9, "", "")));
    t.addItem(new LineItem(19, BigDecimal.TEN, new Category(8, "", "")));
    assertEquals(3, items.size());
  }

  public void testCompare() {
    Transaction t1 = new Transaction(5, new DateMidnight(), "desc");
    Transaction t2 = new Transaction(-45, new DateMidnight(), "desc");
    assertTrue(t1.compareTo(t2) > 0);
    assertTrue(t2.compareTo(t1) < 0);

    t2 = new Transaction(5, new DateMidnight(), "desc");
    assertTrue(t1.compareTo(t2) == 0);
    assertTrue(t2.compareTo(t1) == 0);

    t2 = new Transaction(6, new DateMidnight(), "desc");
    assertTrue(t1.compareTo(t2) < 0);
    assertTrue(t2.compareTo(t1) > 0);
  }

  public void testEquals() {
    Transaction t1 = new Transaction(5, new DateMidnight(), "desc");
    Transaction t2 = new Transaction(5, new DateMidnight(), "desc");
    assertEquals(t1, t2);
    assertEquals(t2, t1);

    t2 = new Transaction(6, new DateMidnight(), "desc");
    assertFalse(t1.equals(t2));
    assertFalse(t2.equals(t1));
  }
}

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
 * Tests for {@link Account}.
 * 
 * @author wrg007 (Bob Gardner)
 */
public class AccountTest extends TestCase {
  public void testInstantiation() {
    new Account(-1, "name", "institution", "number", Account.Type.CHECKING, "notes");
  }

  public void testInstantiation_nullName() {
    boolean passed = false;
    try {
      new Account(-1, null, "institution", "number", Account.Type.CHECKING, "notes");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullInstitution() {
    boolean passed = false;
    try {
      new Account(-1, "name", null, "number", Account.Type.CHECKING, "notes");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullNumber() {
    boolean passed = false;
    try {
      new Account(-1, "name", "institution", null, Account.Type.CHECKING, "notes");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullType() {
    boolean passed = false;
    try {
      new Account(-1, "name", "institution", "number", null, "notes");
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testInstantiation_nullNotes() {
    boolean passed = false;
    try {
      new Account(-1, "name", "institution", "number", Account.Type.CHECKING, null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
  }

  public void testId() {
    Account acc = new Account(0, "name", "institution", "number", Account.Type.CHECKING, "notes");
    assertEquals(0, acc.getId());
    acc = new Account(-5, "name", "institution", "number", Account.Type.SAVINGS, "notes");
    assertEquals(-5, acc.getId());
  }

  public void testName() {
    Account acc = new Account(0, "name", "", "", Account.Type.CHECKING, "");
    assertEquals("name", acc.getName());
    acc.setName("");
    assertEquals("", acc.getName());

    boolean passed = false;
    try {
      acc.setName(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
    assertEquals("", acc.getName());
  }

  public void testInstitution() {
    Account acc = new Account(0, "", "institution", "", Account.Type.CHECKING, "");
    assertEquals("institution", acc.getInstitution());
    acc.setInstitution("");
    assertEquals("", acc.getInstitution());

    boolean passed = false;
    try {
      acc.setInstitution(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
    assertEquals("", acc.getInstitution());
  }

  public void testNumber() {
    Account acc = new Account(0, "", "", "number", Account.Type.CHECKING, "");
    assertEquals("number", acc.getNumber());
    acc.setNumber("");
    assertEquals("", acc.getNumber());

    boolean passed = false;
    try {
      acc.setNumber(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
    assertEquals("", acc.getNumber());
  }

  public void testType() {
    Account acc = new Account(0, "", "", "", Account.Type.CHECKING, "");
    assertEquals(Account.Type.CHECKING, acc.getType());
    acc.setType(Account.Type.SAVINGS);
    assertEquals(Account.Type.SAVINGS, acc.getType());

    boolean passed = false;
    try {
      acc.setType(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
    assertEquals(Account.Type.SAVINGS, acc.getType());
  }

  public void testNotes() {
    Account acc = new Account(0, "", "", "", Account.Type.CHECKING, "notes");
    assertEquals("notes", acc.getNotes());
    acc.setNotes("");
    assertEquals("", acc.getNotes());

    boolean passed = false;
    try {
      acc.setNotes(null);
    } catch (NullPointerException e) {
      // exception expected
      passed = true;
    }
    assertTrue(passed);
    assertEquals("", acc.getNotes());
  }

  public void testCompare() {
    Account acc1 = new Account(14, "b", "", "", Account.Type.CHECKING, "");
    Account acc2 = new Account(15, "c", "", "", Account.Type.SAVINGS, "");
    assertTrue(acc1.compareTo(acc2) < 0);
    assertTrue(acc2.compareTo(acc1) > 0);

    acc2 = new Account(14, "b", "", "psp", Account.Type.CHECKING, "asef");
    assertTrue(acc1.compareTo(acc2) == 0);
    assertTrue(acc2.compareTo(acc1) == 0);

    acc2 = new Account(13, "a", "", "", Account.Type.CHECKING, "");
    assertTrue(acc1.compareTo(acc2) > 0);
    assertTrue(acc2.compareTo(acc1) < 0);
  }

  public void testEquals() {
    Account acc1 = new Account(14, "b", "", "", Account.Type.CHECKING, "");
    Account acc2 = new Account(15, "c", "", "", Account.Type.SAVINGS, "");
    assertFalse(acc1.equals(acc2));
    assertFalse(acc2.equals(acc1));

    acc2 = new Account(14, "", "", "", Account.Type.CHECKING, "");
    assertEquals(acc1, acc2);
    assertEquals(acc2, acc1);
  }
}

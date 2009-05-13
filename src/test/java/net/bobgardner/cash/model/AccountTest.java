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
  @Override
  public void setUp() {
    Account.resetCounter();
    Cashbox.INSTANCE.clearAccounts();
  }

  public void testInstantiation() {
    Account account =
        Account.newAccount("name", "institution", "number", Account.Type.CHECKING, "notes");
    assertTrue(Cashbox.INSTANCE.getAccounts().contains(account));
  }

  public void testInstantiation_nullName() {
    try {
      Account.newAccount(null, "institution", "number", Account.Type.CHECKING, "notes");
      fail("NullPointerException expected for null name");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullInstitution() {
    try {
      Account.newAccount("name", null, "number", Account.Type.CHECKING, "notes");
      fail("NullPointerException expected for null institution");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullNumber() {
    try {
      Account.newAccount("name", "institution", null, Account.Type.CHECKING, "notes");
      fail("NullPointerException expected for null number");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullType() {
    try {
      Account.newAccount("name", "institution", "number", null, "notes");
      fail("NullPointerException expected for null type");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testInstantiation_nullNotes() {
    try {
      Account.newAccount("name", "institution", "number", Account.Type.CHECKING, null);
      fail("NullPointerException expected for null notes");
    } catch (NullPointerException e) {
      // exception expected
    }
  }

  public void testId() {
    Account acc =
        Account.newAccount("name", "institution", "number", Account.Type.CHECKING, "notes");
    assertEquals(0, acc.getId());
    acc = Account.newAccount("name2", "institution2", "number2", Account.Type.SAVINGS, "notes");
    assertEquals(1, acc.getId());
  }

  public void testName() {
    Account acc = Account.newAccount("name", "", "", Account.Type.CHECKING, "");
    assertEquals("name", acc.getName());
    acc.setName("");
    assertEquals("", acc.getName());

    try {
      acc.setName(null);
      fail("NullPointerException expected for null name");
    } catch (NullPointerException e) {
      // exception expected
    }
    assertEquals("", acc.getName());
  }

  public void testInstitution() {
    Account acc = Account.newAccount("", "institution", "", Account.Type.CHECKING, "");
    assertEquals("institution", acc.getInstitution());
    acc.setInstitution("");
    assertEquals("", acc.getInstitution());

    try {
      acc.setInstitution(null);
      fail("NullPointerException expected for null institution");
    } catch (NullPointerException e) {
      // exception expected
    }
    assertEquals("", acc.getInstitution());
  }

  public void testNumber() {
    Account acc = Account.newAccount("", "", "number", Account.Type.CHECKING, "");
    assertEquals("number", acc.getNumber());
    acc.setNumber("");
    assertEquals("", acc.getNumber());

    try {
      acc.setNumber(null);
      fail("NullPointerException expected for null number");
    } catch (NullPointerException e) {
      // exception expected
    }
    assertEquals("", acc.getNumber());
  }

  public void testType() {
    Account acc = Account.newAccount("", "", "", Account.Type.CHECKING, "");
    assertEquals(Account.Type.CHECKING, acc.getType());
    acc.setType(Account.Type.SAVINGS);
    assertEquals(Account.Type.SAVINGS, acc.getType());

    try {
      acc.setType(null);
      fail("NullPointerException expected for null type");
    } catch (NullPointerException e) {
      // exception expected
    }
    assertEquals(Account.Type.SAVINGS, acc.getType());
  }

  public void testNotes() {
    Account acc = Account.newAccount("", "", "", Account.Type.CHECKING, "notes");
    assertEquals("notes", acc.getNotes());
    acc.setNotes("");
    assertEquals("", acc.getNotes());

    try {
      acc.setNotes(null);
      fail("NullPointerException expected for null notes");
    } catch (NullPointerException e) {
      // exception expected
    }
    assertEquals("", acc.getNotes());
  }

  public void testCompare() {
    Account acc1 = Account.newAccount("b", "", "", Account.Type.CHECKING, "");
    Account acc2 = Account.newAccount("c", "n", "", Account.Type.SAVINGS, "");
    assertTrue(acc1.compareTo(acc2) < 0);
    assertTrue(acc2.compareTo(acc1) > 0);

    Account.resetCounter();
    Cashbox.INSTANCE.clearAccounts();
    acc2 = Account.newAccount("b", "", "psp", Account.Type.CHECKING, "asef");
    assertTrue(acc1.compareTo(acc2) == 0);
    assertTrue(acc2.compareTo(acc1) == 0);

    acc1 = Account.newAccount("s", "m", "", Account.Type.CHECKING, "");
    acc2 = Account.newAccount("m", "e", "", Account.Type.CHECKING, "");
    assertTrue(acc1.compareTo(acc2) > 0);
    assertTrue(acc2.compareTo(acc1) < 0);
  }

  public void testEquals() {
    Account acc1 = Account.newAccount("b", "", "", Account.Type.CHECKING, "");
    Account acc2 = Account.newAccount("c", "d", "", Account.Type.SAVINGS, "");
    assertFalse(acc1.equals(acc2));
    assertFalse(acc2.equals(acc1));

    Account.resetCounter();
    Cashbox.INSTANCE.clearAccounts();
    acc2 = Account.newAccount("", "c", "", Account.Type.CHECKING, "");
    assertEquals(acc1, acc2);
    assertEquals(acc2, acc1);
  }
}

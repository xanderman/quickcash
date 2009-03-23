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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

/**
 * A simple bank account with a set of transactions.
 * 
 * Setters are protected so that {@link Cashbox} can maintain package
 * invariants.
 * 
 * @author wrg007 (Bob Gardner)
 */
public final class Account implements Comparable<Account> {
  /**
   * Record identifier. A negative number means that this is a new account not
   * yet found in the database.
   */
  private final int id;
  private String name;
  private String institution;
  private String number;
  private Type type;
  private String notes;
  private final SortedSet<Transaction> transactions = Sets.newTreeSet();

  /**
   * Enumerates the types of accounts this application recognizes.
   */
  public static enum Type {
    CHECKING, SAVINGS;
  }

  /**
   * Create a new account with the given information.
   */
  protected Account(int id, String name, String institution, String number, Type type, String notes) {
    this.id = id;
    this.name = checkNotNull(name);
    this.institution = checkNotNull(institution);
    this.number = checkNotNull(number);
    this.type = checkNotNull(type);
    this.notes = checkNotNull(notes);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = checkNotNull(name);
  }

  public String getInstitution() {
    return institution;
  }

  protected void setInstitution(String institution) {
    this.institution = checkNotNull(institution);
  }

  public String getNumber() {
    return number;
  }

  protected void setNumber(String number) {
    this.number = checkNotNull(number);
  }

  public Type getType() {
    return type;
  }

  protected void setType(Type type) {
    this.type = checkNotNull(type);
  }

  public String getNotes() {
    return notes;
  }

  protected void setNotes(String notes) {
    this.notes = checkNotNull(notes);
  }

  public Set<Transaction> getTransactions() {
    return Collections.unmodifiableSortedSet(transactions);
  }

  protected void addTransaction(Transaction transaction) {
    for (Transaction t : transactions) {
      if (t.getId() == transaction.getId())
        throw new IllegalArgumentException("A transaction with this ID already exists.");
    }
    transactions.add(checkNotNull(transaction));
  }

  protected void removeTransaction(Transaction transaction) {
    // TODO mark deletion for database update
    transactions.remove(transaction);
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object) This implementation
   *      is consistent with equals only when the invariant that account names
   *      are unique is maintained.
   */
  @Override
  public int compareTo(Account o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Account)) return false;
    Account other = (Account) o;
    return this.id == other.id;
  }
}

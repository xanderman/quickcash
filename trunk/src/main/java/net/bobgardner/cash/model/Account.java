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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedSet;

/**
 * A simple bank account with a set of transactions.
 * 
 * All write operations will result in an immediate write to the database. This
 * has two effects: the operation will be slightly slower, and any operation
 * that violates a uniqueness constraint will fail with
 * {@link IllegalArgumentException}.
 * 
 * @author wrg007 (Bob Gardner)
 * 
 * @invariant id >= 0 and is unique across all valid accounts
 * @invariant name is nonempty and unique across all valid accounts
 * @invariant (institution, number) is unique across all valid accounts
 */
public final class Account extends Observable implements Comparable<Account>, Observer {
  /**
   * Record identifier.
   */
  private final int id;
  private String name;
  private String institution;
  private String number;
  private Type type;
  private String notes;
  private final SortedSet<Transaction> transactions = Sets.newTreeSet();

  /**
   * True if this account is present in the database.
   */
  private volatile boolean valid;

  /**
   * Enumerates the types of accounts this application recognizes.
   */
  public static enum Type {
    CHECKING, SAVINGS;
  }

  /**
   * Create a new account with the given information. Creates the account,
   * stores it in the database (thus retrieving an id), and adds it to
   * {@link Cashbox}.
   * 
   * @throws IllegalArgumentException if any uniqueness constraints are violated
   */
  public static Account newAccount(String name, String institution, String number, Type type,
      String notes) {
    // TODO interact with database
    Account account = new Account(id_counter++, name, institution, number, type, notes);
    account.valid = true;
    Cashbox.INSTANCE.addAccount(account);
    return account;
  }

  /**
   * Deletes an account in the database, including deletion of all its
   * transactions, and removes it from {@link Cashbox}. This invalidates the
   * account (and its transactions), and all future operations on the account
   * (or its transactions) will fail with {@link IllegalStateException}.
   * 
   * @param account the account to be deleted
   */
  protected static void deleteAccount(Account account) {
    if (!account.valid) return; // Don't delete twice!
    // TODO Interact with database
    account.valid = false;
    account.setChanged();
    account.notifyObservers();
  }

  // TODO these will go away, I just need them for current use/testing
  private static int id_counter = 0;

  protected static void resetCounter() {
    id_counter = 0;
  }

  private Account(int id, String name, String institution, String number, Type type, String notes) {
    this.id = id;
    checkNotNull(name);
    checkArgument(!"".equals(name.trim()), "Name must not be empty.");
    this.name = name.trim();
    checkNotNull(institution);
    this.institution = institution.trim();
    checkNotNull(number);
    this.number = number.trim();
    this.type = checkNotNull(type);
    checkNotNull(notes);
    this.notes = notes.trim();
  }

  public boolean isValid() {
    return valid;
  }

  public int getId() {
    checkState(valid, "This account has been deleted.");
    return id;
  }

  public String getName() {
    checkState(valid, "This account has been deleted.");
    return name;
  }

  public void setName(String name) {
    // TODO Interact with database
    checkState(valid, "This account has been deleted.");
    checkNotNull(name);
    checkArgument(!"".equals(name.trim()), "Name must not be empty.");
    this.name = name.trim();
    setChanged();
    notifyObservers();
  }

  public String getInstitution() {
    checkState(valid, "This account has been deleted.");
    return institution;
  }

  public void setInstitution(String institution) {
    // TODO Interact with database
    checkState(valid, "This account has been deleted.");
    checkNotNull(institution);
    this.institution = institution.trim();
    setChanged();
    notifyObservers();
  }

  public String getNumber() {
    checkState(valid, "This account has been deleted.");
    return number;
  }

  public void setNumber(String number) {
    // TODO Interact with database
    checkState(valid, "This account has been deleted.");
    checkNotNull(number);
    this.number = number.trim();
    setChanged();
    notifyObservers();
  }

  public Type getType() {
    checkState(valid, "This account has been deleted.");
    return type;
  }

  public void setType(Type type) {
    // TODO Interact with database
    checkState(valid, "This account has been deleted.");
    this.type = checkNotNull(type);
    setChanged();
    notifyObservers();
  }

  public String getNotes() {
    checkState(valid, "This account has been deleted.");
    return notes;
  }

  public void setNotes(String notes) {
    // TODO Interact with database
    checkState(valid, "This account has been deleted.");
    checkNotNull(notes);
    this.notes = notes.trim();
    setChanged();
    notifyObservers();
  }

  public SortedSet<Transaction> getTransactions() {
    checkState(valid, "This account has been deleted.");
    return Collections.unmodifiableSortedSet(transactions);
  }

  protected void addTransaction(Transaction transaction) {
    checkState(valid, "This account has been deleted.");
    checkNotNull(transaction);
    checkArgument(transaction.isValid(), "Transaction is invalid.");
    transaction.addObserver(this);
    addObserver(transaction);
    transactions.add(transaction);
    setChanged();
    notifyObservers();
  }

  protected void removeTransaction(Transaction transaction) {
    checkState(valid, "This account has been deleted.");
    checkNotNull(transaction);
    checkArgument(!transaction.isValid(), "Transaction is still valid.");
    transaction.deleteObserver(this);
    deleteObserver(transaction);
    transactions.remove(transaction);
    setChanged();
    notifyObservers();
  }

  @Override
  public String toString() {
    checkState(valid, "This account has been deleted.");
    return getName();
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object) This implementation
   *      is consistent with equals only when the invariant that account names
   *      are unique is maintained.
   */
  @Override
  public int compareTo(Account o) {
    checkState(valid, "This account has been deleted.");
    return this.name.compareTo(o.name);
  }

  @Override
  public boolean equals(Object o) {
    checkState(valid, "This account has been deleted.");
    if (!(o instanceof Account)) return false;
    Account other = (Account) o;
    return this.id == other.id;
  }

  @Override
  public void update(Observable o, Object arg) {
    // Observes its transactions
    if (o instanceof Transaction) {
      Transaction transaction = (Transaction) o;
      // The only change we care about is deletion
      if (!transaction.isValid()) {
        // In which case we remove the transaction from the list
        removeTransaction(transaction);
      }
    }
  }
}

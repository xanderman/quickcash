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

import org.joda.time.DateMidnight;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedSet;

/**
 * A transaction is equivalent to one receipt. Contains one or multiple line
 * items, allowing the user to split the transaction easily into multiple
 * categories.
 * 
 * All write operations will result in an immediate write to the database. This
 * has two effects: the operation will be slightly slower, and any operation
 * that violates a uniqueness constraint will fail with
 * {@link IllegalArgumentException}.
 * 
 * @author wrg007 (Bob Gardner)
 * 
 * @invariant id >= 0 and is unique across all valid transactions
 */
public class Transaction extends Observable implements Comparable<Transaction>, Observer {
  /**
   * Record identifier.
   */
  private final int id;
  private DateMidnight date;
  private String payee;
  private String checkNr;
  private final SortedSet<LineItem> items = Sets.newTreeSet();

  /**
   * True if this transaction is present in the database.
   */
  private volatile boolean valid;

  /**
   * Create a new transaction with the given information. Creates the
   * transaction, stores it in the database (thus retrieving an id), and adds it
   * to the {@link Account}.
   */
  public static Transaction newTransaction(Account account, DateMidnight date, String payee,
      String checkNr) {
    // TODO interact with database
    Transaction transaction = new Transaction(id_counter++, date, payee, checkNr);
    transaction.valid = true;
    account.addTransaction(transaction);
    return transaction;
  }

  /**
   * Deletes a transaction in the database, including deletion of all its line
   * items, and removes it from its account. This invalidates the transaction
   * (and its line items), and all future operations on the transaction (or its
   * line items) will fail with {@link IllegalStateException}.
   * 
   * @param transaction the transaction to be deleted
   */
  public static void deleteTransaction(Transaction transaction) {
    if (!transaction.valid) return;
    // TODO interact with database
    transaction.valid = false;
    transaction.setChanged();
    transaction.notifyObservers();
  }

  // TODO these will go away, I just need them for current use/testing
  protected static int id_counter = 0;

  protected static void resetCounter() {
    id_counter = 0;
  }

  private Transaction(int id, DateMidnight date, String payee, String checkNr) {
    this.id = id;
    this.date = checkNotNull(date);
    checkNotNull(payee);
    this.payee = payee.trim();
    checkNotNull(checkNr);
    this.checkNr = checkNr.trim();
  }

  // Visible for subclasses
  protected Transaction(DateMidnight date, String payee, String checkNr) {
    this(id_counter++, date, payee, checkNr);
    this.valid = true;
  }

  public boolean isValid() {
    return valid;
  }

  public int getId() {
    checkState(valid, "This transaction has been deleted.");
    return id;
  }

  public DateMidnight getDate() {
    checkState(valid, "This transaction has been deleted.");
    return date;
  }

  public void setDate(DateMidnight date) {
    // TODO Interact with database
    checkState(valid, "This transaction has been deleted.");
    this.date = checkNotNull(date);
    setChanged();
    notifyObservers();
  }

  public String getPayee() {
    checkState(valid, "This transaction has been deleted.");
    return payee;
  }

  public void setPayee(String payee) {
    // TODO Interact with database
    checkState(valid, "This transaction has been deleted.");
    checkNotNull(payee);
    this.payee = payee.trim();
    setChanged();
    notifyObservers();
  }

  public String getCheckNr() {
    checkState(valid, "This transaction has been deleted.");
    return checkNr;
  }

  public void setCheckNr(String checkNr) {
    // TODO Interact with database
    checkState(valid, "This transaction has been deleted.");
    checkNotNull(checkNr);
    this.checkNr = checkNr.trim();
    setChanged();
    notifyObservers();
  }

  public SortedSet<LineItem> getItems() {
    checkState(valid, "This transaction has been deleted.");
    return Collections.unmodifiableSortedSet(items);
  }

  protected void addItem(LineItem item) {
    checkState(valid, "This transaction has been deleted.");
    checkNotNull(item);
    checkArgument(item.isValid(), "Line item is invalid.");
    items.add(item);
    item.addObserver(this);
    addObserver(item);
    setChanged();
    notifyObservers();
  }

  protected void removeItem(LineItem item) {
    checkState(valid, "This transaction has been deleted.");
    checkNotNull(item);
    checkArgument(!item.isValid(), "Line item is still valid.");
    item.deleteObserver(this);
    deleteObserver(item);
    items.remove(item);
    setChanged();
    notifyObservers();
  }

  public String getDescription() {
    checkState(valid, "This transaction has been deleted.");
    return items.size() == 1 ? items.first().getDescription() : "...";
  }

  public void setDescription(String description) {
    checkState(valid, "This transaction has been deleted.");
    checkState(items.size() == 1,
        "Description can only be set on transactions with exactly one line item.");
    items.first().setDescription(description);
  }

  public Category getCategory() {
    checkState(valid, "This transaction has been deleted.");
    return items.size() == 1 ? items.first().getCategory() : Category.NULL_CATEGORY;
  }

  public void setCategory(Category category) {
    checkState(valid, "This transaction has been deleted.");
    checkState(items.size() == 1,
        "Category can only be set on transactions with exactly one line item.");
    items.first().setCategory(category);
    setChanged();
    notifyObservers();
  }

  public BigDecimal getAmount() {
    checkState(valid, "This transaction has been deleted.");
    BigDecimal total = BigDecimal.ZERO;
    for (LineItem li : items) {
      total = total.add(li.getAmount());
    }
    return total;
  }

  public void setAmount(BigDecimal amount) {
    checkState(valid, "This transaction has been deleted.");
    checkState(items.size() == 1,
        "Amount can only be set on transaction with exactly one line item.");
    items.first().setAmount(amount);
    setChanged();
    notifyObservers();
  }

  @Override
  public int compareTo(Transaction o) {
    int ret = this.date.compareTo(o.date);
    if (ret == 0) return this.id == o.id ? 0 : this.id > o.id ? 1 : -1;
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Transaction)) return false;
    Transaction other = (Transaction) o;
    return this.id == other.id;
  }

  @Override
  public void update(Observable o, Object arg) {
    // Observes the account it belongs to
    if (o instanceof Account) {
      Account account = (Account) o;
      // The only change we care about is deletion
      if (!account.isValid()) {
        // In which case we delete this transaction
        deleteTransaction(this);
      }
    }

    // Observing its line items
    else if (o instanceof LineItem) {
      LineItem item = (LineItem) o;
      // The only change we care about is deletion
      if (!item.isValid()) {
        // In which case, we remove the item from the list
        removeItem(item);
      }
    }
  }
}

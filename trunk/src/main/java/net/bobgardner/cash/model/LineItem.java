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
import static com.google.common.base.Preconditions.checkState;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

/**
 * Split-able parts of a transaction.
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
public class LineItem extends Observable implements Comparable<LineItem>, Observer {
  /**
   * Record identifier. A negative number means that this is a new line item not
   * yet found in the database.
   */
  private final int id;
  BigDecimal amount;
  Category category;
  String description;

  /**
   * True if this transaction is present in the database.
   */
  private volatile boolean valid;

  /**
   * Creates a new line item with the given information. Creates the line item,
   * stores it in the database (thus retrieving an id), and adds it to the
   * {@link Transaction}.
   */
  public static LineItem newLineItem(Transaction transaction, BigDecimal amount, Category category,
      String description) {
    // TODO interact with database
    LineItem item = new LineItem(id_counter++, amount, category, description);
    item.valid = true;
    transaction.addItem(item);
    return item;
  }

  /**
   * Deletes a line item in the database. This invalidates the line item, and
   * all future operations on the line item will fail with
   * {@link IllegalStateException}.
   * 
   * @param item the line item to be deleted
   */
  public static void deleteLineItem(LineItem item) {
    // TODO interact with database
    item.valid = false;
    item.setChanged();
    item.notifyObservers();
  }

  // TODO these will go away, I just need them for current use/testing
  protected static int id_counter = 0;

  protected static void resetCounter() {
    id_counter = 0;
  }

  private LineItem(int id, BigDecimal amount, Category category, String description) {
    this.id = id;
    this.amount = checkNotNull(amount);
    this.category = checkNotNull(category);
    checkNotNull(description);
    this.description = description.trim();
  }

  public boolean isValid() {
    return valid;
  }

  public int getId() {
    checkState(valid, "This line item has been deleted.");
    return id;
  }

  public BigDecimal getAmount() {
    checkState(valid, "This line item has been deleted.");
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    // TODO Interact with database
    checkState(valid, "This line item has been deleted.");
    this.amount = checkNotNull(amount);
    setChanged();
    notifyObservers();
  }

  public Category getCategory() {
    checkState(valid, "This line item has been deleted.");
    return category;
  }

  public void setCategory(Category category) {
    // TODO Interact with database
    checkState(valid, "This line item has been deleted.");
    this.category = checkNotNull(category);
    setChanged();
    notifyObservers();
  }

  public String getDescription() {
    checkState(valid, "This line item has been deleted.");
    return description;
  }

  public void setDescription(String description) {
    // TODO Interact with database
    checkState(valid, "This line item has been deleted.");
    checkNotNull(description);
    this.description = description.trim();
    setChanged();
    notifyObservers();
  }

  @Override
  public int compareTo(LineItem o) {
    return this.id == o.id ? 0 : this.id > o.id ? 1 : -1;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LineItem)) return false;
    LineItem other = (LineItem) o;
    return this.id == other.id;
  }

  @Override
  public void update(Observable o, Object arg) {
    // Observes the transaction it belongs to
    if (o instanceof Transaction) {
      Transaction transaction = (Transaction) o;
      // The only change we care about is deletion
      if (!transaction.isValid()) {
        // In which case we delete this line item
        deleteLineItem(this);
      }
    }
  }
}

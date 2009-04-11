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

import org.joda.time.DateMidnight;

import java.util.Collections;
import java.util.SortedSet;

/**
 * A transaction is equivalent to one receipt. Contains one or multiple line
 * items, allowing the user to split the transaction easily into multiple
 * categories.
 * 
 * Setters are protected so that {@link Cashbox} can maintain package
 * invariants.
 * 
 * @author wrg007 (Bob Gardner)
 */
public class Transaction implements Comparable<Transaction> {
  /**
   * Record identifier. A negative number means that this is a new transaction
   * not yet found in the database.
   */
  private final int id;
  private DateMidnight date;
  private final SortedSet<LineItem> items = Sets.newTreeSet();

  public Transaction(int id, DateMidnight date) {
    this.id = id;
    this.date = checkNotNull(date);
  }

  public int getId() {
    return id;
  }

  public DateMidnight getDate() {
    return date;
  }

  protected void setDate(DateMidnight date) {
    this.date = checkNotNull(date);
  }

  public SortedSet<LineItem> getItems() {
    return Collections.unmodifiableSortedSet(items);
  }

  protected void addItem(LineItem item) {
    for (LineItem li : items) {
      if (li.getId() == item.getId())
        throw new IllegalArgumentException("A line item with this ID already exists.");
    }
    items.add(checkNotNull(item));
  }

  protected void removeItem(LineItem item) {
    // TODO mark deletion for database update
    items.remove(item);
  }

  public String getDescription() {
    return "...";
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
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
}

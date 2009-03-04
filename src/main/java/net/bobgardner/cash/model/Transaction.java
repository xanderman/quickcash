// Copyright 2008 Bob Gardner. All Rights Reserved.

package net.bobgardner.cash.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Comparators;
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
 * @author bobgardner (Bob Gardner)
 */
public class Transaction implements Comparable<Transaction> {
  /**
   * Record identifier. A negative number means that this is a new transaction
   * not yet found in the database.
   */
  private final int id;
  private DateMidnight date;
  private String description;
  private final SortedSet<LineItem> items = Sets.newTreeSet();

  public Transaction(int id, DateMidnight date, String description) {
    this.id = id;
    this.date = checkNotNull(date);
    this.description = checkNotNull(description);
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

  public String getDescription() {
    return description;
  }

  protected void setDescription(String description) {
    this.description = checkNotNull(description);
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Transaction o) {
    int ret = this.date.compareTo(o.date);
    if (ret == 0) return Comparators.compare(this.id, o.id);
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Transaction)) return false;
    Transaction other = (Transaction) o;
    return this.id == other.id;
  }
}

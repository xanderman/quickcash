// Copyright 2008 Bob Gardner. All Rights Reserved.

package net.bobgardner.cash.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Comparators;

import java.math.BigDecimal;

/**
 * Split-able parts of a transaction.
 * 
 * Setters are protected so that {@link Cashbox} can maintain package
 * invariants.
 * 
 * @author bobgardner (Bob Gardner)
 */
public class LineItem implements Comparable<LineItem> {
  /**
   * Record identifier. A negative number means that this is a new line item not
   * yet found in the database.
   */
  private final int id;
  BigDecimal amount;
  Category category;

  public LineItem(int id, BigDecimal amount, Category category) {
    this.id = id;
    checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Amount must be greater than 0.");
    this.amount = amount;
    this.category = checkNotNull(category);
  }

  public int getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  protected void setAmount(BigDecimal amount) {
    checkArgument(amount.compareTo(BigDecimal.ZERO) > 0, "Amount must be greater than 0.");
    this.amount = amount;
  }

  public Category getCategory() {
    return category;
  }

  protected void setCategory(Category category) {
    this.category = checkNotNull(category);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(LineItem o) {
    return Comparators.compare(this.id, o.id);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LineItem)) return false;
    LineItem other = (LineItem) o;
    return this.id == other.id;
  }
}

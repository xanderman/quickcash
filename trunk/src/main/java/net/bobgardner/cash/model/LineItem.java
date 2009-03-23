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

import java.math.BigDecimal;

/**
 * Split-able parts of a transaction.
 * 
 * Setters are protected so that {@link Cashbox} can maintain package
 * invariants.
 * 
 * @author wrg007 (Bob Gardner)
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
    return this.id == o.id ? 0 : this.id > o.id ? 1 : -1;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LineItem)) return false;
    LineItem other = (LineItem) o;
    return this.id == other.id;
  }
}

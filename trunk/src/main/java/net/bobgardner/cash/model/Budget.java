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
import java.util.EnumMap;


/**
 * Holds one year's worth of budget information for one category.
 * 
 * @author wrg007 (Bob Gardner)
 */
public class Budget implements Comparable<Budget> {
  public static enum Month {
    JAN, FEB, MARCH, APRIL, MAY, JUNE, JULY, AUG, SEP, OCT, NOV, DEC;
  }

  private int year;
  private Category category;
  private final EnumMap<Month, BigDecimal> deposits = new EnumMap<Month, BigDecimal>(Month.class);
  private final EnumMap<Month, BigDecimal> withdrawals =
      new EnumMap<Month, BigDecimal>(Month.class);

  public int getYear() {
    return year;
  }

  protected void setYear(int year) {
    checkArgument(year > 2000, "Year must be after 2000.");
    this.year = year;
  }

  public Category getCategory() {
    return category;
  }

  protected void setCategory(Category category) {
    this.category = checkNotNull(category);
  }

  public BigDecimal getDeposit(Month month) {
    return deposits.get(month);
  }

  protected void setDeposit(Month month, BigDecimal deposit) {
    checkNotNull(deposit);
    checkArgument(deposit.compareTo(BigDecimal.ZERO) >= 0, "Ammount cannot be negative.");
    deposits.put(month, deposit);
  }

  public BigDecimal getWithdrawal(Month month) {
    return withdrawals.get(month);
  }

  protected void setWithdrawal(Month month, BigDecimal withdrawal) {
    checkNotNull(withdrawal);
    checkArgument(withdrawal.compareTo(BigDecimal.ZERO) >= 0, "Ammount cannot be negative.");
    withdrawals.put(month, withdrawal);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Budget o) {
    int ret = this.year == o.year ? 0 : this.year > o.year ? 1 : -1;
    if (ret == 0) ret = this.category.compareTo(o.category);
    return ret;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Budget)) return false;
    Budget other = (Budget) o;
    return this.year == other.year && this.category.equals(other.category);
  }
}

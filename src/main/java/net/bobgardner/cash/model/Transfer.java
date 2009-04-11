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

import org.joda.time.DateMidnight;

/**
 * A transfer is a transaction between two Accounts
 * 
 * @author wrg007 (Bob Gardner)
 */
public class Transfer extends Transaction {
  private final Account destAccount;
  private final Transfer destTransfer;

  /**
   * Create a transfer between two accounts.
   * 
   * @param id the ID of this transaction in the database
   * @param date the date this transaction occurred
   * @param destAccount the {@link Account} the money from this transfer goes to
   * @param destTransfer the corresponding transfer in the destination
   *        {@link Account}
   */
  public Transfer(int id, DateMidnight date, Account destAccount, Transfer destTransfer) {
    super(id, date);
    this.destAccount = checkNotNull(destAccount);
    this.destTransfer = checkNotNull(destTransfer);
  }

  /**
   * Get the destination {@link Account}
   * 
   * @return the {@link Account} this transfer goes to
   */
  public Account getDestAccount() {
    return destAccount;
  }

  /**
   * Get the destination transfer
   * 
   * @return the corresponding transfer in the destination {@link Account}
   */
  public Transfer getDestTransfer() {
    return destTransfer;
  }

  @Override
  public String getDescription() {
    return "Transfer with " + destAccount.getName();
  }
}

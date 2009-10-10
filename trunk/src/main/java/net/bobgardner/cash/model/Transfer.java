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

import org.joda.time.DateMidnight;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A transfer is a transaction between two Accounts
 * 
 * @author wrg007 (Bob Gardner)
 */
public class Transfer extends Transaction {
  private final Account destAccount;
  private Transfer destTransfer;

  /**
   * Create a new transfer and its pair with the given information. Creates the
   * transfers, stores them in the database (thus retrieving ids), and adds them
   * to {@link Cashbox}.
   * 
   * @throws IllegalArgumentException if any uniqueness constraints are violated
   */
  public static Transfer newTransfer(Account account, Account destAccount, DateMidnight date,
      String payee, String checkNr) {
    // TODO interact with database
    // TODO possible chicken-and-egg problem
    Transfer destTransfer = new Transfer(date, account, null, payee, checkNr);
    Transfer transfer = new Transfer(date, destAccount, destTransfer, payee, checkNr);
    destTransfer.destTransfer = transfer;
    destAccount.addTransaction(destTransfer);
    account.addTransaction(transfer);
    return transfer;
  }

  /**
   * Create a transfer between two accounts.
   * 
   * @param id the ID of this transaction in the database
   * @param date the date this transaction occurred
   * @param destAccount the {@link Account} the money from this transfer goes to
   * @param destTransfer the corresponding transfer in the destination
   *        {@link Account}
   */
  private Transfer(DateMidnight date, Account destAccount, Transfer destTransfer, String payee,
      String checkNr) {
    super(date, payee, checkNr);
    this.destAccount = checkNotNull(destAccount);
    this.destTransfer = destTransfer;
  }

  /**
   * Get the destination {@link Account}
   * 
   * @return the {@link Account} this transfer goes to
   */
  public Account getDestAccount() {
    checkState(isValid(), "This transaction has been deleted.");
    return destAccount;
  }

  /**
   * Get the destination transfer
   * 
   * @return the corresponding transfer in the destination {@link Account}
   */
  public Transfer getDestTransfer() {
    checkState(isValid(), "This transaction has been deleted.");
    return destTransfer;
  }

  @Override
  public String getDescription() {
    checkState(isValid(), "This transaction has been deleted.");
    return "Transfer with " + destAccount.getName();
  }

  @Override
  public void setDescription(String description) {
    checkState(isValid(), "This transaction has been deleted.");
    throw new NotImplementedException();
  }
}

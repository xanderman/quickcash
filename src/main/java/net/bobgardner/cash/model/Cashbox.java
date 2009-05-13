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

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.SortedSet;

/**
 * Singleton representing the sum total of the application data, stored as a set
 * of accounts, a set of categories, and a set of budgets.
 * 
 * @author wrg007 (Bob Gardner)
 */
public enum Cashbox implements Observer {
  INSTANCE;

  private final SortedSet<Account> accounts = Sets.newTreeSet();
  private final SortedSet<Category> categories = Sets.newTreeSet();

  public SortedSet<Account> getAccounts() {
    return Collections.unmodifiableSortedSet(accounts);
  }

  /**
   * Adds an account to Cashbox.
   * 
   * Protected so that it is only called by
   * {@link Account#newAccount(String, String, String, Account.Type, String)}.
   * 
   * @param account the account to add (must be valid)
   * 
   * @throws IllegalArgumentException if the account is invalid
   */
  protected void addAccount(Account account) {
    // Validity check insures that database constraints are enforced
    checkNotNull(account);
    checkArgument(account.isValid(), "Account is invalid.");
    account.addObserver(this);
    accounts.add(account);
  }

  /**
   * Removes an account from Cashbox.
   * 
   * Protected so that it is only called by
   * {@link Account#deleteAccount(Account)}.
   * 
   * @param account the account to remove (must be invalid)
   * 
   * @throws IllegalArgumentException if the account is valid
   */
  protected void removeAccount(Account account) {
    checkNotNull(account);
    checkArgument(!account.isValid(), "Account is still valid.");
    account.deleteObserver(null);
    accounts.remove(account);
  }

  /**
   * Visible for testing.
   */
  protected void clearAccounts() {
    accounts.clear();
  }

  public SortedSet<Category> getCategories() {
    return Collections.unmodifiableSortedSet(categories);
  }

  /**
   * Adds a category to Cashbox.
   * 
   * Protected so that it is only called by
   * {@link Category#newCategory(String, String)}.
   * 
   * @param category the category to add (must be valid)
   * 
   * @throws IllegalArgumentException if the category is invalid
   */
  protected void addCategory(Category category) {
    // Validity check insures that database constraints are enforced
    checkNotNull(category);
    checkArgument(category.isValid(), "Category is invalid.");
    category.addObserver(this);
    categories.add(category);
  }

  /**
   * Removes a category from Cashbox.
   * 
   * Protected so that it is only called by
   * {@link Category#deleteCategory(Category)}.
   * 
   * @param category the category to remove (must be invalid)
   * 
   * @throws IllegalArgumentException if the category is valid
   */
  protected void removeCateory(Category category) {
    checkNotNull(category);
    checkArgument(!category.isValid(), "Category is still valid.");
    category.deleteObserver(this);
    categories.remove(category);
  }

  /**
   * Visible for testing.
   */
  protected void clearCategories() {
    categories.clear();
  }

  @Override
  public void update(Observable o, Object arg) {
    // Observes its accounts
    if (o instanceof Account) {
      Account account = (Account) o;
      // The only change we care about is deletion
      if (!account.isValid()) {
        // In which case we remove the account
        removeAccount(account);
      }
    }

    // Observes its categories
    else if (o instanceof Category) {
      Category category = (Category) o;
      // The only change we care about is deletion
      if (!category.isValid()) {
        // In which case we remove the category
        removeCateory(category);
      }
    }
  }
}

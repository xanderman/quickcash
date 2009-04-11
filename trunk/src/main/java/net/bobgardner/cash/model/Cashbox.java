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

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.SortedSet;

/**
 * Singleton enum representing the sum total of the application data, stored as
 * a set of accounts, a set of categories, and a set of budgets.
 * 
 * @author wrg007 (Bob Gardner)
 */
public enum Cashbox {
  INSTANCE;

  private final SortedSet<Account> accounts = Sets.newTreeSet();
  private final SortedSet<Category> categories = Sets.newTreeSet();

  public SortedSet<Account> getAccounts() {
    return Collections.unmodifiableSortedSet(accounts);
  }

  public void addAccount(Account account) {
    for (Account acc : accounts) {
      if (account.getId() != -1 && acc.getId() == account.getId())
        throw new IllegalArgumentException("An account with this ID already exists.");
      if (acc.getName().equals(account.getName()))
        throw new IllegalArgumentException("An account with this name already exists.");
      if (acc.getInstitution().equals(account.getInstitution())
          && acc.getNumber().equals(account.getNumber()))
        throw new IllegalArgumentException(
            "An account from this institution with this number already exists.");
    }
    accounts.add(account);
  }

  public void removeAccount(Account account) {
    // TODO mark deletion for database update
    accounts.remove(account);
  }

  public SortedSet<Category> getCategories() {
    return Collections.unmodifiableSortedSet(categories);
  }

  public void addCategory(Category category) {
    for (Category c : categories) {
      if (category.getId() != -1 && c.getId() == category.getId())
        throw new IllegalArgumentException("A category with this ID already exists.");
      if (c.getName().equals(category.getName()))
        throw new IllegalArgumentException("A category with this name already exists");
    }
    categories.add(category);
  }

  public void removeCateory(Category category) {
    // TODO mark deletion for database update
    categories.remove(category);
  }
}

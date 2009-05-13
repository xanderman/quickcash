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
import static com.google.common.base.Preconditions.checkState;

import java.util.Observable;

/**
 * For use in categorizing {@link LineItem}s.
 * 
 * All write operations will result in an immediate write to the database. This
 * has two effects: the operation will be slightly slower, and any operation
 * that violates a uniqueness constraint will fail with
 * {@link IllegalArgumentException}.
 * 
 * @author wrg007 (Bob Gardner)
 * 
 * @invariant id >= 0 and is unique across all valid categories
 * @invariant name is nonempty and unique across all valid categories
 */
public class Category extends Observable implements Comparable<Category> {
  public static final Category NULL_CATEGORY = new Category(Integer.MIN_VALUE, "...", "...");
  public static final Category NONE = new Category(Integer.MIN_VALUE + 1, "None", "");
  /**
   * Record identifier.
   */
  private final int id;
  private String name;
  private String description;

  /**
   * True if this category is present in the database.
   */
  private volatile boolean valid;

  /**
   * Create a new category with the given information. Creates the category,
   * stores it in the database (thus retrieving an id), and adds it to
   * {@link Cashbox}.
   * 
   * @throws IllegalArgumentException if any uniqueness constraints are violated
   */
  public static Category newCategory(String name, String description) {
    // TODO interact with the database
    Category category = new Category(id_counter++, name, description);
    category.valid = true;
    Cashbox.INSTANCE.addCategory(category);
    return category;
  }

  /**
   * Deletes a category in the database and removes it from {@link Cashbox}.
   * This invalidates the category, and all future operations on the category
   * will fail with {@link IllegalStateException}.
   * 
   * @param category the category to be deleted
   */
  public static void deleteCategory(Category category) {
    if (!category.valid) return; // Don't delete twice!
    // TODO interact with the database
    category.valid = false;
    category.setChanged();
    category.notifyObservers();
  }

  // TODO these will go away, I just need them for current use/testing
  private static int id_counter = 0;

  protected static void resetCounter() {
    id_counter = 0;
  }

  private Category(int id, String name, String description) {
    this.id = id;
    checkNotNull(name);
    checkArgument(!"".equals(name.trim()), "Name must not be empty.");
    this.name = name.trim();
    checkNotNull(description);
    this.description = description.trim();
  }

  public boolean isValid() {
    return valid;
  }

  public int getId() {
    checkState(valid, "This category has been deleted.");
    return id;
  }

  public String getName() {
    checkState(valid, "This category has been deleted.");
    return name;
  }

  public void setName(String name) {
    // TODO Interact with database
    checkState(valid, "This category has been deleted.");
    checkNotNull(name);
    checkArgument(!"".equals(name.trim()), "Name must not be empty.");
    this.name = name.trim();
    setChanged();
    notifyObservers();
  }

  public String getDescription() {
    checkState(valid, "This category has been deleted.");
    return description;
  }

  public void setDescription(String description) {
    // TODO Interact with database
    checkState(valid, "This category has been deleted.");
    checkNotNull(description);
    this.description = description.trim();
    setChanged();
    notifyObservers();
  }

  @Override
  public String toString() {
    checkState(valid, "This category has been deleted.");
    return this.name;
  }

  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object) This implementation
   *      is consistent with equals only when the constraint that category names
   *      and IDs are (individually) unique is not violated.
   */
  @Override
  public int compareTo(Category o) {
    return this.name.compareTo(o.name);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Category)) return false;
    Category other = (Category) o;
    return this.id == other.id;
  }
}

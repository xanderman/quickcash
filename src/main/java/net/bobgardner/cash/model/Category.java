// Copyright 2008 Bob Gardner. All Rights Reserved.

package net.bobgardner.cash.model;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * For use in categorizing {@link LineItem}s.
 * 
 * Setters are protected so that {@link Cashbox} can maintain package
 * invariants.
 * 
 * @author bobgardner (Bob Gardner)
 */
public class Category implements Comparable<Category> {
  /**
   * Record identifier. A negative number means that this is a new category not
   * yet found in the database.
   */
  private final int id;
  private String name;
  private String description;

  public Category(int id, String name, String description) {
    this.id = id;
    this.name = checkNotNull(name);
    this.description = checkNotNull(description);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = checkNotNull(name);
  }

  public String getDescription() {
    return description;
  }

  protected void setDescription(String description) {
    this.description = checkNotNull(description);
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

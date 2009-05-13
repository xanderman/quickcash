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

package net.bobgardner.cash.view;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * Disclosure icon that maintains its open/closed state and allows toggling
 * between two images.
 * 
 * @author wrg007 (Bob Gardner)
 */
class DisclosureIcon extends ImageIcon {
  private static java.net.URL OPEN_URL =
      DisclosureIcon.class.getResource("/disclosurePanelOpen.png");
  private static java.net.URL CLOSED_URL =
      DisclosureIcon.class.getResource("/disclosurePanelClosed.png");
  private static Image OPEN_IMG = Toolkit.getDefaultToolkit().createImage(OPEN_URL);
  private static Image CLOSED_IMG = Toolkit.getDefaultToolkit().createImage(CLOSED_URL);
  private static String OPEN_DESC = "Click to hide line items";
  private static String CLOSED_DESC = "Click to show line items";

  private boolean open;

  public DisclosureIcon(boolean open) {
    super(open ? OPEN_IMG : CLOSED_IMG, open ? OPEN_DESC : CLOSED_DESC);
    this.open = open;
  }

  public boolean isOpen() {
    return open;
  }

  public void toggle() {
    open = !open;
    setImage(open ? OPEN_IMG : CLOSED_IMG);
    setDescription(open ? OPEN_DESC : CLOSED_DESC);
  }
}

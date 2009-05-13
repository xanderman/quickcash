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

import javax.swing.JButton;

/**
 * Utility class for creating buttons.
 * 
 * @author wrg007 (Bob Gardner)
 */
class Buttons {
  private Buttons() {
    // Utility class should not be instantiated
  }

  public static JButton newAddTransactionButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }

  public static JButton newAccountDetailsButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }

  public static JButton newAddTransferButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }

  public static JButton newDeleteTransactionButton(String buttonText) {
    JButton button = new JButton(buttonText);
    return button;
  }
}

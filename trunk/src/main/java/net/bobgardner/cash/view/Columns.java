package net.bobgardner.cash.view;

import com.google.common.collect.Maps;

import net.bobgardner.cash.model.Cashbox;
import net.bobgardner.cash.model.Category;
import net.bobgardner.cash.model.LineItem;
import net.bobgardner.cash.model.Transaction;

import org.joda.time.DateMidnight;

import java.awt.Component;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * List of column names and configuration values.
 * 
 * @author wrg007 (Bob Gardner)
 */
enum Columns {
  DISCLOSURE("", false, ImageIcon.class, 20, new DisclosureCellRenderer(), null) {
    private final Map<Transaction, DisclosureIcon> icons = Maps.newHashMap();

    @Override
    public void configureColumn(TableColumn column) {
      super.configureColumn(column);
      column.setMaxWidth(20);
      column.setMinWidth(20);
    }

    @Override
    public Object getValue(Transaction item) {
      DisclosureIcon icon = icons.get(item);
      if (icon == null) {
        icon = new DisclosureIcon(false);
        icons.put(item, icon);
      }
      return icon;
    }

    @Override
    public Object getValue(LineItem item) {
      return null;
    }
  },
  DATE("Date", true, DateMidnight.class, 83, new DateCellRenderer(), new DateCellEditor()) {
    @Override
    public Object getValue(Transaction item) {
      return item.getDate();
    }

    @Override
    public Object getValue(LineItem item) {
      return null;
    }
  },
  CATEGORY("Category", true, Category.class, 100, null, new CategoryCellEditor()) {
    @Override
    public void configureColumn(TableColumn column) {
      super.configureColumn(column);
      column.setCellEditor(new CategoryCellEditor());
    }

    @Override
    public Object getValue(Transaction item) {
      return item.getCategory();
    }

    @Override
    public Object getValue(LineItem item) {
      return item.getCategory();
    }
  },
  PAYEE("Payee", true, String.class, 65, null, new PayeeCellEditor()) {
    @Override
    public Object getValue(Transaction item) {
      return item.getPayee();
    }

    @Override
    public Object getValue(LineItem item) {
      return "...";
    }
  },
  DESCRIPTION("Description", true, String.class, 339, null, new DescriptionCellEditor()) {
    @Override
    public Object getValue(Transaction item) {
      return item.getDescription();
    }

    @Override
    public Object getValue(LineItem item) {
      return item.getDescription();
    }
  },
  CHECK_NR("Check Nr", true, String.class, 61, null, new CheckNrCellEditor()) {
    @Override
    public Object getValue(Transaction item) {
      return item.getCheckNr();
    }

    @Override
    public Object getValue(LineItem item) {
      return "...";
    }
  },
  AMOUNT("Amount", true, BigDecimal.class, 72, new MoneyCellRenderer(), new AmountCellEditor()) {
    @Override
    public Object getValue(Transaction item) {
      return item.getAmount();
    }

    @Override
    public Object getValue(LineItem item) {
      return item.getAmount();
    }
  },
  TOTAL("Total", false, BigDecimal.class, 69, new MoneyCellRenderer(), null) {
    @Override
    public Object getValue(Transaction item) {
      // TODO Auto-generated method stub
      return BigDecimal.ZERO;
    }

    @Override
    public Object getValue(LineItem item) {
      // TODO Auto-generated method stub
      return BigDecimal.ZERO;
    }
  };

  private final String name;
  private final boolean editable;
  private final Class<?> colClass;
  private final int preferredWidth;
  private final TableCellRenderer renderer;
  private final TableCellEditor editor;

  Columns(String name, boolean editable, Class<?> colClass, int preferredWidth,
      TableCellRenderer renderer, TableCellEditor editor) {
    this.name = name;
    this.editable = editable;
    this.colClass = colClass;
    this.preferredWidth = preferredWidth;
    this.renderer = renderer;
    this.editor = editor;
  }

  public String getName() {
    return name;
  }

  public boolean isEditable() {
    return editable;
  }

  public Class<?> getColumnClass() {
    return colClass;
  }

  public int getPreferredWidth() {
    return preferredWidth;
  }

  public void configureColumn(TableColumn column) {
    column.setPreferredWidth(preferredWidth);
    if (renderer != null) column.setCellRenderer(renderer);
    if (editor != null) column.setCellEditor(editor);
  }

  public abstract Object getValue(Transaction item);

  public abstract Object getValue(LineItem item);

  /**
   * Custom renderer used to show the disclosure icon in column 0 with an
   * appropriate tooltip.
   * 
   * @author wrg007 (Bob Gardner)
   */
  private static class DisclosureCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      ImageIcon icon = (ImageIcon) value;
      setIcon(icon);
      setToolTipText(icon.getDescription());
      return super.getTableCellRendererComponent(table, "", isSelected, hasFocus, row, column);
    }
  }

  private static class DateCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      DateMidnight date = (DateMidnight) value;
      return super.getTableCellRendererComponent(table, date.toString("d-MMM-yy"), isSelected,
          hasFocus, row, column);
    }
  }

  private static class DateCellEditor extends DefaultCellEditor {
    public DateCellEditor() {
      super(new JTextField());
    }
  }

  private static class CategoryCellEditor extends DefaultCellEditor {
    public CategoryCellEditor() {
      super(new JComboBox(Cashbox.INSTANCE.getCategories().toArray()));
    }
  }

  private static class PayeeCellEditor extends DefaultCellEditor {
    public PayeeCellEditor() {
      super(new JTextField());
    }
  }

  private static class DescriptionCellEditor extends DefaultCellEditor {
    public DescriptionCellEditor() {
      super(new JTextField());
    }
  }

  private static class CheckNrCellEditor extends DefaultCellEditor {
    public CheckNrCellEditor() {
      super(new JTextField());
    }
  }

  private static class MoneyCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      BigDecimal amount = (BigDecimal) value;
      boolean negative = amount.compareTo(BigDecimal.ZERO) < 0;
      String absolute = amount.setScale(2).abs().toPlainString();
      String money = negative ? "(" + absolute + ")" : absolute;
      return super.getTableCellRendererComponent(table, money, isSelected, hasFocus, row, column);
    }
  }

  private static class AmountCellEditor extends DefaultCellEditor {
    public AmountCellEditor() {
      super(new JTextField());
    }
  }
}

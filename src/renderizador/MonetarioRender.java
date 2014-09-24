package renderizador;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MonetarioRender extends DefaultTableCellRenderer {
    private DecimalFormat df = new DecimalFormat(",##0.00");
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            value = 0;
        }
        if (value instanceof String) {
            setText(df.format(Double.parseDouble((String) value)) + " ");
        } else {
            setText(df.format(value) + " ");
        }
        setHorizontalAlignment(SwingConstants.RIGHT);
        return this;
    }
}

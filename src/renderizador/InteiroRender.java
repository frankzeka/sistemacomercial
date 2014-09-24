package renderizador;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class InteiroRender extends DefaultTableCellRenderer {

    private NumberFormat nf = NumberFormat.getIntegerInstance();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null) {
            value = 0;
        }
        if (value instanceof String) {
            setText(nf.format(Integer.parseInt((String) value)) + " ");
        } else {
            setText(nf.format(value) + " ");
        }
        setHorizontalAlignment(SwingConstants.RIGHT);
        return this;
    }
}
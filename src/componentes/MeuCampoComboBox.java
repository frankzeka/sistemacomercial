package componentes;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class MeuCampoComboBox extends JComboBox implements MeuComponente {
    private String[][] valores;
    private boolean podeHabilitar;
    private boolean obrigatorio;
    private String dica;

    public MeuCampoComboBox(String[][] valores, boolean podeHabilitar, boolean obrigatorio, String dica) {
        this.valores = valores;
        this.podeHabilitar = podeHabilitar;
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        preencher();
    }

    public void preencher() {
        removeAllItems();
        for (int i = 0; i < valores.length; i++) {
            addItem(valores[i][1]);
        }
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return (getSelectedIndex() == -1);
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    @Override
    public void limpar() {
        setSelectedIndex(-1);
    }

    @Override
    public Object getValor() {
        return valores[getSelectedIndex()][0];
    }

    @Override
    public void setValor(Object valor) {
        String v = (String) valor;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i][0].equals(v)) {
                setSelectedIndex(i);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, dica + " (" + v + ") não encontrado(a)");
    }
}
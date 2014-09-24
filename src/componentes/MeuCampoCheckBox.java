package componentes;

import javax.swing.JCheckBox;

public class MeuCampoCheckBox extends JCheckBox implements MeuComponente {
    public String dica;
    
    public MeuCampoCheckBox(String dica) {
        this.dica = dica;
    }
    
    @Override
    public boolean eObrigatorio() {
        return false;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eVazio() {
        return false;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(status);
    }

    @Override
    public void limpar() {
    }    

    @Override
    public Object getValor() {
        return (isSelected());      
    }

    @Override
    public void setValor(Object valor) {
        setSelected((Boolean) valor);
    }
}
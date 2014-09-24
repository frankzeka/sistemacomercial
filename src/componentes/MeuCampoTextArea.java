package componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MeuCampoTextArea extends JScrollPane implements MeuComponente, FocusListener {
    public JTextArea jta = new JTextArea();
    public boolean obrigatorio;
    public String dica;
    
    public MeuCampoTextArea(boolean obrigatorio, String dica, int largura, int altura) {
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        setPreferredSize(new Dimension(largura, altura));
        jta.setLineWrap(true);
        setViewportView(jta);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jta.addFocusListener(this);
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
        return jta.getText().trim().isEmpty();
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void habilitar(boolean status) {
        jta.setEnabled(status);
    }

    @Override
    public void limpar() {
        jta.setText("");
    }       

    @Override
    public void focusGained(FocusEvent fe) {
        jta.setBackground(Color.cyan);
        jta.setCaretPosition(0);
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (eObrigatorio() && eVazio()) {
            jta.setBackground(Color.yellow);
        } else {
            jta.setBackground(Color.white);
        }
        if (!eValido()) {
            jta.setBackground(Color.red);
        }
    }

    @Override
    public Object getValor() {
        return jta.getText();
    }

    @Override
    public void setValor(Object valor) {
        jta.setText("" + valor);
    }
}
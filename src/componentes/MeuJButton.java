package componentes;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MeuJButton extends JButton {
    public MeuJButton(String texto) {
        super(texto);
    }
    
    public MeuJButton(String localImagem, boolean areaFilled, boolean borderPainted, boolean focusable) {
        setIcon(new ImageIcon(getClass().getResource(localImagem)));
        setContentAreaFilled(areaFilled);
        setBorderPainted(borderPainted);
        setFocusable(focusable);
    }
}
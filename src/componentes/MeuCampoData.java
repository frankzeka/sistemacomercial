package componentes;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MeuCampoData extends MeuCampoFormatado {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MeuCampoData(int colunas, boolean obrigatorio, String dica, boolean podeHabilitar) {
        super(colunas, obrigatorio, dica, podeHabilitar);
        sdf.setLenient(false);
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível iniciar MeuCampoData");
        }
    }

    @Override
    public boolean eVazio() {
        return getText().replace("/", "").trim().isEmpty();
    }

    @Override
    public boolean eValido() {
        try {
            sdf.parse(getText());
            System.out.println("Ok");
            return true;
        } catch (Exception e) {
            System.out.println("Não ok");
            return false;
        }
    }

    @Override
    public void limpar() {
        setText("  /  /    ");
    }
    
    @Override
    public void setValor(Object valor) {
        setText(sdf.format((Date) valor));
    }
    
    public Date getValorDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter a data.");
            e.printStackTrace();
            return null;
        }
    }    
}
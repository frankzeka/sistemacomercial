package componentes;

public interface MeuComponente {
    public boolean eObrigatorio();
    public boolean eValido();
    public boolean eVazio();
    public String getDica();
    public void habilitar(boolean status);
    public void limpar();
    public Object getValor();
    public void setValor(Object valor);
}
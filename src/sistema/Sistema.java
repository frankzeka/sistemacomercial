package sistema;

import telas.TelaLogin;
import telas.TelaSistema;
import util.Dados;

public class Sistema {
    public static void main(String args[]) {
        TelaSistema telaSistema = new TelaSistema();
        //new TelaLogin();
        Dados.usuarioLogado.setId(1); //Forçando o usuario logado enquanto não temos a tela de login
    }
}
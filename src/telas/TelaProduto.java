/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package telas;

import componentes.MeuCampoInteiro;
import dao.ProdutoDao;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import pojo.Produto;

/**
 *
 * @author Usuario
 */
public class TelaProduto extends TelaCadastro {
    public static TelaProduto tela;
    public Produto produto = new Produto();
    public ProdutoDao produtoDao = new ProdutoDao(produto);
    public MeuCampoInteiro CampoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    
    public static TelaProduto getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaProduto();
            tela.addInternalFrameListener(new InternalFrameAdapter() { //Adiciona um listener para verificar quando a tela for fechada, fazendo assim a remoção da mesma junto ao JDesktopPane da TelaSistema e setando a variável tela = null para permitir que a tela possa ser aberta novamente em outro momento. Basicamente o mesmo controle efetuado pela tela de pesquisa, porém de uma forma um pouco diferente.
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.jdp.remove(tela);
                    tela = null;
                }
            });
            TelaSistema.jdp.add(tela);
        }
        //Depois do teste acima, independentemente dela já existir ou não, ela é selecionada e movida para frente
        TelaSistema.jdp.setSelectedFrame(tela);
        TelaSistema.jdp.moveToFront(tela);
        return tela;
    }
    
    public TelaProduto() {
        super("Cadastro de Produto");
        adicionaCampo(1, 1, 1, 1, CampoCodigo, ESQUERDA);
        pack();
        habilitarCampos(false);
    }
        
    
}

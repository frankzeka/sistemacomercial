package telas;

import componentes.MeuCampoCheckBox;
import componentes.MeuCampoDBComboBox;
import componentes.MeuCampoTexto;
import componentes.MeuCampoData;
import componentes.MeuCampoInteiro;
import dao.EstadoDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import pojo.Estado;
import static telas.TelaCadastro.ESQUERDA;
import util.Dados;

public class TelaCadastroEstado extends TelaCadastro {
    public static TelaCadastroEstado tela;
    public Estado estado = new Estado();
    public EstadoDao estadoDao = new EstadoDao(estado);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Nome");
    public MeuCampoTexto campoSigla = new MeuCampoTexto(2, true, true, "Sigla");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox("Ativo");
    public MeuCampoData campoData = new MeuCampoData(6, false, "Data Cadastro", false);

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroEstado();
            tela.addInternalFrameListener(new InternalFrameAdapter() { //Adiciona um listener para verificar quando a tela for fechada, fazendo assim a remoção da mesma junto ao JDesktopPane da TelaSistema e setando a variável tela = null para permitir que a tela possa ser aberta novamente em outro momento. Basicamente o mesmo controle efetuado pela tela de pesquisa, porém de uma forma um pouco diferente.
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.jdp.remove(tela);
                    tela = null;
                }
            });            TelaSistema.jdp.add(tela);
        }
        //Depois do teste acima, independentemente dela já existir ou não, ela é selecionada e movida para frente
        TelaSistema.jdp.setSelectedFrame(tela);
        TelaSistema.jdp.moveToFront(tela);
        return tela;
    }
    
    public TelaCadastroEstado() {
        super("Cadastro de Estado");
        adicionaCampo(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionaCampo(2, 1, 1, 2, campoNome, ESQUERDA);
        adicionaCampo(3, 1, 1, 1, campoSigla, ESQUERDA);
        adicionaCampo(5, 1, 1, 1, campoAtivo, ESQUERDA);
        adicionaCampo(8, 1, 1, 1, campoData, ESQUERDA);
        pack();
        habilitarCampos(false);
    }
    
    @Override
    public void incluir() {
        super.incluir();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        campoData.setText(sdf.format(new Date()));
    }
    
    @Override
    public void setPersistencia() {
        estado.setId((Integer) campoCodigo.getValor());
        estado.setNome((String) campoNome.getValor());
        estado.setAtivo((Boolean) campoAtivo.getValor());
        estado.setSigla((String) campoSigla.getValor());
        estado.setUltimaAlteracao(new Date());
    }
    
    public void getPersistencia() {
        campoCodigo.setValor(estado.getId());
        campoNome.setValor(estado.getNome());
        campoAtivo.setValor(estado.isAtivo());
        campoSigla.setValor(estado.getSigla());
        campoData.setValor(estado.getUltimaAlteracao());
    }
    
    @Override
    public boolean incluirBD() {
        super.incluirBD();
        return estadoDao.inserir();
    }
    
    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return estadoDao.alterar();
    }    

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return estadoDao.excluir();
    } 
    
    @Override
     public void consultarBD(int pk) {
        super.consultarBD(pk);
        estado.setId(pk);
        estadoDao.consultar();
        getPersistencia();
    }
    
    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Estado", estadoDao.PESQUISASQL, new String[] {"Código", "Estado", "Ativo"},this);
    }
}
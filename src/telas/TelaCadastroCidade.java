package telas;

import componentes.MeuCampoCheckBox;
import componentes.MeuCampoDBComboBox;
import componentes.MeuCampoTexto;
import componentes.MeuCampoData;
import componentes.MeuCampoInteiro;
import dao.CidadeDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import pojo.Cidade;
import static telas.TelaCadastro.ESQUERDA;

public class TelaCadastroCidade extends TelaCadastro {

    public static TelaCadastroCidade tela;
    public Cidade cidade = new Cidade();
    public CidadeDao cidadeDao = new CidadeDao(cidade);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Nome");
    public MeuCampoDBComboBox campoEstado = new MeuCampoDBComboBox(TelaCadastroEstado.class,
            "SELECT ID, NOME FROM ESTADO", true, true, "Estado");
    public MeuCampoCheckBox campoAtivo = new MeuCampoCheckBox("Ativo");
    public MeuCampoData campoData = new MeuCampoData(6, false, "Data Cadastro", false);

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCidade();
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

    public TelaCadastroCidade() {
        super("Cadastro de Cidade");
        adicionaCampo(1, 1, 1, 1, campoCodigo, ESQUERDA);
        adicionaCampo(2, 1, 1, 2, campoNome, ESQUERDA);
        adicionaCampo(3, 1, 1, 1, campoEstado, ESQUERDA);
        adicionaCampo(4, 1, 1, 1, campoAtivo, ESQUERDA);
        adicionaCampo(7, 1, 1, 1, campoData, ESQUERDA);
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
        cidade.setId((Integer) campoCodigo.getValor());
        cidade.getEstado().setId((Integer) campoEstado.getValor());
        cidade.setNome((String) campoNome.getValor());
        cidade.setAtivo((Boolean) campoAtivo.getValor());
        cidade.setUltimaAlteracao(new Date());
    }

    public void getPersistencia() {
        campoCodigo.setValor(cidade.getId());
        campoEstado.setValor(cidade.getEstado().getId());
        campoNome.setValor(cidade.getNome());
        campoAtivo.setValor(cidade.isAtivo());
        campoData.setValor(cidade.getUltimaAlteracao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        return cidadeDao.inserir();
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return cidadeDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return cidadeDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        cidade.setId(pk);
        cidadeDao.consultar();
        getPersistencia();
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Cidade", cidadeDao.PESQUISASQL, new String[] {"Código", "Cidade", "Ativo"}, this);
    }
}


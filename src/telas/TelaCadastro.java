package telas;

import componentes.MeuComponente;
import componentes.MeuJButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaCadastro extends JInternalFrame implements ActionListener {

    public ArrayList<MeuComponente> campos = new ArrayList();
    public final int PADRAO = 0;
    public final int INCLUINDO = 1;
    public final int ALTERANDO = 2;
    public final int EXCLUINDO = 3;
    public boolean temDadosNaTela = false;
    public int operacao = PADRAO;
    public MeuJButton jbIncluir = new MeuJButton("Incluir");
    public MeuJButton jbAlterar = new MeuJButton("Alterar");
    public MeuJButton jbExcluir = new MeuJButton("Excluir");
    public MeuJButton jbConsultar = new MeuJButton("Consultar");
    public MeuJButton jbConfirmar = new MeuJButton("Confirmar");
    public MeuJButton jbCancelar = new MeuJButton("Cancelar");
    public JPanel jpCampos = new JPanel();
    public JPanel jpBotoes = new JPanel();
    public static final int ESQUERDA = 0;
    public static final int CIMA = 1;

    public TelaCadastro(String titulo) {
        super(titulo, false, true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", jpCampos);
        getContentPane().add("South", jpBotoes);
        jpBotoes.setLayout(new GridLayout(1, 6));
        jpCampos.setLayout(new GridBagLayout());
        insereBotao(jbIncluir);
        insereBotao(jbAlterar);
        insereBotao(jbExcluir);
        insereBotao(jbConsultar);
        insereBotao(jbConfirmar);
        insereBotao(jbCancelar);
        setVisible(true);
        habilitarBotoes();
    }

    public void adicionaCampo(int linha, int coluna, int altura, int largura, JComponent campo, int alinhamento) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        if (alinhamento == ESQUERDA) {
            gbc.anchor = GridBagConstraints.EAST;
        } else {
            gbc.anchor = GridBagConstraints.WEST;
        }
        if (campo instanceof MeuComponente) {
            String nomeRotulo = "<html><body>" + ((MeuComponente) campo).getDica() + ": ";
            if (((MeuComponente) campo).eObrigatorio()) {
                nomeRotulo = nomeRotulo + "<font color=red>*</font>";
            }
            nomeRotulo = nomeRotulo + "</body></html>";
            JLabel rotulo = new JLabel(nomeRotulo);
            jpCampos.add(rotulo, gbc);
            if (alinhamento == ESQUERDA) {
                gbc.gridx++;
                gbc.insets = new Insets(5, 0, 5, 5);
            } else {
                gbc.gridy++;
                gbc.insets = new Insets(0, 0, 5, 5);
            }
            campos.add((MeuComponente) campo);
        }
        gbc.gridheight = altura;
        gbc.gridwidth = largura;
        gbc.anchor = GridBagConstraints.WEST;
        jpCampos.add((Component) campo, gbc);
    }

    public void insereBotao(MeuJButton botao) {
        jpBotoes.add(botao);
        botao.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbIncluir) {
            incluir();
        } else if (ae.getSource() == jbAlterar) {
            alterar();
        } else if (ae.getSource() == jbExcluir) {
            excluir();
        } else if (ae.getSource() == jbConsultar) {
            consultar();
        } else if (ae.getSource() == jbConfirmar) {
            confirmar();
        } else if (ae.getSource() == jbCancelar) {
            cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Operação não conhecida.");
        }
    }

    public void incluir() {
        operacao = INCLUINDO;
        habilitarBotoes();
        limparCampos();
        habilitarCampos(true);
    }

    public void alterar() {
        operacao = ALTERANDO;
        habilitarBotoes();
        habilitarCampos(true);
    }

    public void excluir() {
        operacao = EXCLUINDO;
        habilitarBotoes();
    }

    public void consultar() {
    }

    public void confirmar() {
        if (operacao == INCLUINDO) {
            if ((!validarCampos()) || (!incluirBD())) {
                return;
            }
            temDadosNaTela = true;
        } else if (operacao == ALTERANDO) {
            if ((!validarCampos()) || (!alterarBD())) {
                return;
            }
        } else if (operacao == EXCLUINDO) {
            int opcao = JOptionPane.showConfirmDialog(this, "Confirma a exclusão?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if ((opcao != JOptionPane.OK_OPTION) || (!excluirBD())) {
                return;
            }
            limparCampos();
            temDadosNaTela = false;
        }
        operacao = PADRAO;
        habilitarBotoes();
        habilitarCampos(false);
    }

    public void cancelar() {
        operacao = PADRAO;
        habilitarBotoes();
        limparCampos();
        habilitarCampos(false);
    }

    public void habilitarBotoes() {
        jbIncluir.setEnabled(operacao == PADRAO);
        jbAlterar.setEnabled(operacao == PADRAO && temDadosNaTela);
        jbExcluir.setEnabled(operacao == PADRAO && temDadosNaTela);
        jbConsultar.setEnabled(operacao == PADRAO);
        jbConfirmar.setEnabled(operacao != PADRAO);
        jbCancelar.setEnabled(operacao != PADRAO);
    }

    public void limparCampos() {
        for (int i = 0; i < campos.size(); i++) {
            campos.get(i).limpar();
        }
    }

    public void habilitarCampos(boolean status) {
        for (int i = 0; i < campos.size(); i++) {
            campos.get(i).habilitar(status);
        }
    }

    public boolean validarCampos() {
        String erroObrigatorio = "";
        for (int i = 0; i < campos.size(); i++) {
            if ((campos.get(i).eObrigatorio()) && (campos.get(i).eVazio())) {
                erroObrigatorio = erroObrigatorio + campos.get(i).getDica() + "\n";
            }
        }
        String erroValidacao = "";
        for (int i = 0; i < campos.size(); i++) {
            if (!campos.get(i).eValido()) {
                erroValidacao = erroValidacao + campos.get(i).getDica() + "\n";
            }
        }
        boolean retorno = true;
        if (!erroObrigatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos abaixo são obrigatórios e não foram informados:\n" + erroObrigatorio);
            retorno = false;
        }
        if (!erroValidacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos abaixo não são válidos:\n" + erroValidacao);
            retorno = false;
        }
        return retorno;
    }

    public boolean incluirBD() {
        setPersistencia();
        return true;
    }

    public boolean alterarBD() {
        setPersistencia();
        return true;
    }

    public boolean excluirBD() {
        setPersistencia();
        return true;
    }

    public void setPersistencia() {
    }

    public void consultarBD(int pk) {
        temDadosNaTela = true;
        habilitarBotoes();
    }
}
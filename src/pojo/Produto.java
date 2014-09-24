package pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Produto {
    private int id;
    private Usuario usuario = new Usuario();
    private String nome;
    private String descricao;
    private int qtde;
    private int qtdeMin;
    private int qtdeMax;
    private int pontoPedido;
    private BigDecimal valorCusto;
    private BigDecimal valorVenda;
    private boolean ativo;
    private String obs;
    private Date datacadastro; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtde() {
        return qtde;
    }

    public void setQtde(int qtde) {
        this.qtde = qtde;
    }

    public int getQtdeMin() {
        return qtdeMin;
    }

    public void setQtdeMin(int qtdeMin) {
        this.qtdeMin = qtdeMin;
    }

    public int getQtdeMax() {
        return qtdeMax;
    }

    public void setQtdeMax(int qtdeMax) {
        this.qtdeMax = qtdeMax;
    }

    public int getPontoPedido() {
        return pontoPedido;
    }

    public void setPontoPedido(int pontoPedido) {
        this.pontoPedido = pontoPedido;
    }

    public BigDecimal getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(BigDecimal valorCusto) {
        this.valorCusto = valorCusto;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getDataCadastro() {
        return datacadastro;
    }

    public void setDateCadastro(Date datacadastro) {
        this.datacadastro = datacadastro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
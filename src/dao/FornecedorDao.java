package dao;

import pojo.Fornecedor;

public class FornecedorDao extends DaoPadrao {
    public static final String PESQUISASQL = "SELECT ID, NOMERAZAOSOCIAL FROM FORNECEDOR ORDER BY NOMERAZAOSOCIAL";
    private Fornecedor fornecedor;
    
    public FornecedorDao(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }        
}
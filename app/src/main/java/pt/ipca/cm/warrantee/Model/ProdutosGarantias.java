package pt.ipca.cm.warrantee.Model;

/**
 * Created by Rafael on 26/01/2017.
 */

public class ProdutosGarantias {
    private String nomeProduto;
    private String fornecedor;
    private String periodo;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

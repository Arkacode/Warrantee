package pt.ipca.cm.warrantee.Model;


/**
 * Created by Rafael on 17/01/2017.
 */

public class Categoria{
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

}

package pt.ipca.cm.warrantee.Model;

/**
 * Created by Rafael on 17/01/2017.
 */
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class Produto  extends RealmObject{
    String nome;
    String serialNumber;
    String fotografia;
    RealmList<Categoria> categoria;
    RealmList<Garantia> garantia;
    RealmList<Utilizador> utilizador;

    public RealmList<Utilizador> getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(RealmList<Utilizador> utilizador) {
        this.utilizador = utilizador;
    }

    public RealmList<Garantia> getGarantia() {

        return garantia;
    }

    public void setGarantia(RealmList<Garantia> garantia) {
        this.garantia = garantia;
    }

    public RealmList<Categoria> getCategoria() {

        return categoria;
    }

    public void setCategoria(RealmList<Categoria> categoria) {
        this.categoria = categoria;
    }

    public String getFotografia() {

        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getSerialNumber() {
        return serialNumber;

    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

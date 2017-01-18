package pt.ipca.cm.warrantee.Model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rafael on 17/01/2017.
 */

public class Garantia extends RealmObject {
    @PrimaryKey
    private int id;
    private String periodo;
    private String localCompra;
    private Date dataCompra;
    private float preco;
    private String imagem;
}

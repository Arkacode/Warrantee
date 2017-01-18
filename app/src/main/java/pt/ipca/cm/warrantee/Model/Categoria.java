package pt.ipca.cm.warrantee.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rafael on 17/01/2017.
 */

public class Categoria extends RealmObject{
    @PrimaryKey
    private int id;
    private String descricao;
}

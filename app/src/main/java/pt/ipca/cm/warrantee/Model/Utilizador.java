package pt.ipca.cm.warrantee.Model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rafael on 17/01/2017.
 */

public class Utilizador {
    @PrimaryKey
    private String id;
    private String nome;
    private String email;
    private String password;
    private String lingua;
    private String dataNasc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }
}

package pt.ipca.cm.warrantee.Model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Rafael on 17/01/2017.
 */

public class Utilizador extends RealmObject {
    @PrimaryKey
    private int id;
    private String nome;
    private String email;
    private String password;
    private String pais;
    private int nrTelemovel;
    private String cidade;
    private String codPostal;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getCidade() {

        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getNrTelemovel() {

        return nrTelemovel;
    }

    public void setNrTelemovel(int nrTelemovel) {
        this.nrTelemovel = nrTelemovel;
    }

    public String getPais() {

        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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


    public static Utilizador add(final Utilizador newUtilizador, Realm realm){

        final RealmResults<Utilizador> utilizadoresResult;
        final Utilizador[] utilizador = new Utilizador[1];
        utilizadoresResult = realm.where(Utilizador.class).equalTo("nome", newUtilizador.getNome()).findAll();
        if (utilizadoresResult.size()==0){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Add a post
                    int id = 1;
                    utilizador[0] = realm.createObject(Utilizador.class, id);
                    utilizador[0].setNome(newUtilizador.getNome());
                    utilizador[0].setEmail(newUtilizador.getEmail());
                    utilizador[0].setPassword(newUtilizador.getPassword());
                }
            });
        }
        else {
            utilizador[0]=update(newUtilizador,realm);
        }
        return utilizador[0];
    }

    public static Utilizador update(final Utilizador newUtilizador, Realm realm){

        final RealmResults<Utilizador> utilizadorRealmResults;
        final Utilizador[] utlizador = new Utilizador[1];
        utilizadorRealmResults = realm.where(Utilizador.class).equalTo("id", newUtilizador.getId()).findAll();
        if (utilizadorRealmResults.size()>0) {
            //update
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    utilizadorRealmResults.first().setNome(newUtilizador.getNome());
                    utilizadorRealmResults.first().setEmail(newUtilizador.getEmail());
                    utilizadorRealmResults.first().setPassword(newUtilizador.getPassword());
                    utlizador[0]=utilizadorRealmResults.first();
                }
            });
        }

        return utlizador[0];
    }

}

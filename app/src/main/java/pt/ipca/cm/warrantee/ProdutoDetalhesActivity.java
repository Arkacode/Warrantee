package pt.ipca.cm.warrantee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pt.ipca.cm.warrantee.Model.Categoria;
import pt.ipca.cm.warrantee.Model.Moeda;

public class ProdutoDetalhesActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button provaCompra;
    DatabaseReference moedasRef = FirebaseDatabase.getInstance().getReference("moedas");
    List<Moeda> moedas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);
        provaCompra = (Button)findViewById(R.id.buttonConfimarDetalhes);
        provaCompra.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMoeda);
        moedasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot moeda : dataSnapshot.getChildren()){

                    int isDifferent = 0;
                    for (int i = 0; i < moedas.size(); i++){
                        if (moedas.get(i) != moeda.getValue(Moeda.class)){
                            isDifferent++;
                        }
                    }
                    if (isDifferent == moedas.size()){
                        moedas.add(moeda.getValue(Moeda.class));
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayAdapter<Moeda> adapter = new ArrayAdapter<Moeda>(this,
                android.R.layout.simple_spinner_item, moedas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.buttonConfimarDetalhes) {
            Intent myIntent = new Intent(ProdutoDetalhesActivity.this, ProvaCompraActivity.class);
            ProdutoDetalhesActivity.this.startActivity(myIntent);
        }
    }
}

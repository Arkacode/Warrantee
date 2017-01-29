package pt.ipca.cm.warrantee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import pt.ipca.cm.warrantee.Model.Moeda;
import pt.ipca.cm.warrantee.Model.Produto;

public class ProdutoDetalhesActivity extends AppCompatActivity  implements View.OnClickListener {
    Bundle data;
    private Button provaCompra;
    private String uid;
    private Produto produto;
    TextView textViewNomeProduto;
    TextView textViewMarcaProduto;
    EditText editTextPeriodoGarantia;
    EditText editTextFornecedor;
    EditText editTextLocalCompra;
    EditText editTextDataCompra;
    EditText editTextPreco;
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    DatabaseReference garantiasRef = FirebaseDatabase.getInstance().getReference("garantias");
    DatabaseReference moedasRef = FirebaseDatabase.getInstance().getReference("moedas");
    List<Moeda> moedas = new ArrayList<>();
    Spinner spinner;
    String codBarras;
    String codSerie;
    String categoria;
    String nomeProduto;
    String marca;
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);
        data = getIntent().getExtras();
        nomeProduto = data.getString("nome");
        marca = data.getString("marca");
        codBarras = data.getString("codBarras");
        codSerie = data.getString("codSerie");
        categoria = data.getString("categoria");
        uid = FirebaseDatabase.getInstance().getReference().push().getKey();
        provaCompra = (Button)findViewById(R.id.buttonConfimarDetalhes);
        textViewNomeProduto = (TextView) findViewById(R.id.nomeProdutoDetalhes);
        textViewMarcaProduto = (TextView) findViewById(R.id.textViewMarcaDetalhes);
        editTextPeriodoGarantia = (EditText) findViewById(R.id.editTextPeriodoGarantia);
        editTextFornecedor = (EditText) findViewById(R.id.editTextFornecedor);
        editTextLocalCompra = (EditText) findViewById(R.id.editTextLocalCompra);
        editTextDataCompra = (EditText) findViewById(R.id.editTextDataCompra);
        editTextPreco = (EditText) findViewById(R.id.editTextPreco);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editTextDataCompra.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProdutoDetalhesActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        textViewNomeProduto.setText(nomeProduto);
        textViewMarcaProduto.setText(marca);
        provaCompra.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinnerMoeda);
        final ArrayAdapter<Moeda> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, moedas);
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

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDataCompra.setText(sdf.format(myCalendar.getTime()));
    }
    public void onClick(View v) {
        if (v.getId() == R.id.buttonConfimarDetalhes) {
            data.putString("periodo",editTextPeriodoGarantia.getText().toString());
            data.putString("fornecedor",editTextFornecedor.getText().toString());
            data.putString("localCompra",editTextLocalCompra.getText().toString());
            data.putString("dataCompra",editTextDataCompra.getText().toString());
            data.putString("preco",editTextPreco.getText().toString());
            //produtosRef.child(Profile.getCurrentProfile().getId()).child(uid).setValue(produto);
            Intent myIntent = new Intent(ProdutoDetalhesActivity.this, ProvaCompraActivity.class);
            myIntent.putExtras(data);
            ProdutoDetalhesActivity.this.startActivity(myIntent);
        }
    }
}

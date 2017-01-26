package pt.ipca.cm.warrantee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pt.ipca.cm.warrantee.Model.Categoria;
import pt.ipca.cm.warrantee.com.google.zxing.integration.android.IntentIntegrator;
import pt.ipca.cm.warrantee.com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutoInsertActivity extends AppCompatActivity implements OnClickListener,AdapterView.OnItemSelectedListener {
    private Button scanBtn;
    private Button scanBtn2;
    private Button produtoDetalhes;
    private TextView editTextScanCodBarras;
    private TextView editTextScanCodSerie;
    private int click;
    DatabaseReference categoriasRef = FirebaseDatabase.getInstance().getReference("categorias");
    List<Categoria> categorias = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_insert);
        scanBtn = (Button)findViewById(R.id.buttonScanBarras);
        scanBtn2 = (Button)findViewById(R.id.buttonScanSerie);
        editTextScanCodBarras = (TextView)findViewById(R.id.editTextCodigoBarras);
        editTextScanCodSerie = (TextView)findViewById(R.id.editTextNumSerie);
        scanBtn.setOnClickListener(this);
        produtoDetalhes = (Button)findViewById(R.id.buttonConfirmarProduto);
        produtoDetalhes.setOnClickListener(this);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategoria);
        spinner.setOnItemSelectedListener(this);
        categoriasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categoria : dataSnapshot.getChildren()){

                    int isDifferent = 0;
                    for (int i = 0; i < categorias.size(); i++){
                        if (categorias.get(i) != categoria.getValue(Categoria.class)){
                            isDifferent++;
                        }
                    }
                    if (isDifferent == categorias.size()){
                        categorias.add(categoria.getValue(Categoria.class));
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this,
                android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,true);
    }
    public void onClick(View v){
        if(v.getId()==R.id.buttonConfirmarProduto) {
            Intent myIntent = new Intent(ProdutoInsertActivity.this, ProdutoDetalhesActivity.class);
            ProdutoInsertActivity.this.startActivity(myIntent);
        }
        //respond to clicks
        if(v.getId()==R.id.buttonScanBarras){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            click = 1;
        }

        if(v.getId() == R.id.buttonScanSerie){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            click = 2;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null && click == 1) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            /*formatTxt.setText("FORMAT: " + scanFormat);*/
            editTextScanCodBarras.setText(scanContent);
        }
        else if(scanningResult != null && click == 2){
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            /*formatTxt.setText("FORMAT: " + scanFormat);*/
            editTextScanCodSerie.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

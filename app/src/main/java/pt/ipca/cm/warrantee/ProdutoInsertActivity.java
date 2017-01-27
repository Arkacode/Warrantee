package pt.ipca.cm.warrantee;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pt.ipca.cm.warrantee.Model.Categoria;
import pt.ipca.cm.warrantee.Model.Produto;
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

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProdutoInsertActivity extends AppCompatActivity implements OnClickListener {
    private Button scanBtn;
    private Button scanBtn2;
    private Button produtoDetalhes;
    private TextView editTextNomeProduto;
    private TextView editTextMarca;
    private TextView editTextScanCodBarras;
    private TextView editTextScanCodSerie;
    private int click;
    Produto produto;
    DatabaseReference categoriasRef = FirebaseDatabase.getInstance().getReference("categorias");
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produtos");
    List<Categoria> categorias = new ArrayList<>();
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_insert);
        editTextNomeProduto = (TextView) findViewById(R.id.editTextNomeProduto);
        editTextMarca = (TextView) findViewById(R.id.editTextMarca);
        scanBtn = (Button)findViewById(R.id.buttonScanBarras);
        scanBtn2 = (Button)findViewById(R.id.buttonScanSerie);
        editTextScanCodBarras = (TextView)findViewById(R.id.editTextCodigoBarras);
        editTextScanCodSerie = (TextView)findViewById(R.id.editTextNumSerie);
        scanBtn.setOnClickListener(this);
        produtoDetalhes = (Button)findViewById(R.id.buttonConfirmarProduto);
        produtoDetalhes.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinnerCategoria);
        final ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categorias);
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

                        //categoriasString.add(categorias.get(isDifferent).getDescricao());

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
    public void onClick(View v){
        if(v.getId()==R.id.buttonConfirmarProduto) {



            String userUid = Profile.getCurrentProfile().getId();
            /*produto = new Produto();
            produto.setCodigoBarras(editTextScanCodBarras.getText().toString().trim());
            produto.setNome(editTextNomeProduto.getText().toString());
            produto.setMarca(editTextMarca.getText().toString());
            produto.setSerialNumber(editTextScanCodSerie.getText().toString().trim());
            //produtosRef.child(userUid).child(uid).setValue(produto);*/
            Intent myIntent = new Intent(ProdutoInsertActivity.this, ProdutoDetalhesActivity.class);
            Bundle data = new Bundle();
            data.putString("codBarras", editTextScanCodBarras.getText().toString());
            data.putString("nome", editTextNomeProduto.getText().toString());
            data.putString("marca", editTextMarca.getText().toString());
            data.putString("codSerie", editTextScanCodSerie.getText().toString());
            data.putString("categoria",spinner.getSelectedItem().toString());
            myIntent.putExtras(data);
            startActivity(myIntent);
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

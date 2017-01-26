package pt.ipca.cm.warrantee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pt.ipca.cm.warrantee.Model.Garantia;

public class ProdutoDetalhesActivity extends AppCompatActivity  implements View.OnClickListener {
    Bundle data = getIntent().getExtras();
    private Button provaCompra;
    Intent intent;
    private String uid;
    private Garantia garantia;
    EditText editTextPeriodoGarantia;
    EditText editTextFornecedor;
    EditText editTextLocalCompra;
    EditText editTextDataCompra;
    EditText editTextPreco;
    DatabaseReference garantiasRef = FirebaseDatabase.getInstance().getReference("garantias");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);
        intent  = getIntent();
        uid = intent.getExtras().getString("uid");
        provaCompra = (Button)findViewById(R.id.buttonConfimarDetalhes);
        editTextPeriodoGarantia = (EditText) findViewById(R.id.editTextPeriodoGarantia);
        editTextFornecedor = (EditText) findViewById(R.id.editTextFornecedor);
        editTextLocalCompra = (EditText) findViewById(R.id.editTextLocalCompra);
        editTextDataCompra = (EditText) findViewById(R.id.editTextDataCompra);
        editTextPreco = (EditText) findViewById(R.id.editTextPreco);
        provaCompra.setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.buttonConfimarDetalhes) {
            Intent myIntent = new Intent(ProdutoDetalhesActivity.this, ProvaCompraActivity.class);
            ProdutoDetalhesActivity.this.startActivity(myIntent);
            garantia = new Garantia();
            garantia.setPeriodo(editTextPeriodoGarantia.getText().toString());
            garantia.setFornecedor(editTextFornecedor.getText().toString());
            garantia.setLocalCompra(editTextLocalCompra.getText().toString());
            garantia.setDataCompra(editTextDataCompra.getText().toString());
            garantia.setPreco(editTextPreco.getText().toString());
            garantiasRef.child(Profile.getCurrentProfile().getId()).child(uid).setValue(garantia);

        }
    }
}

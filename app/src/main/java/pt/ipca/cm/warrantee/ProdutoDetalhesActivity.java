package pt.ipca.cm.warrantee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProdutoDetalhesActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button provaCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhes);
        provaCompra = (Button)findViewById(R.id.buttonConfimarDetalhes);
        provaCompra.setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.buttonConfimarDetalhes) {
            Intent myIntent = new Intent(ProdutoDetalhesActivity.this, ProvaCompraActivity.class);
            ProdutoDetalhesActivity.this.startActivity(myIntent);
        }
    }
}

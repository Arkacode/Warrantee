package pt.ipca.cm.warrantee;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import pt.ipca.cm.warrantee.Model.Produto;
import pt.ipca.cm.warrantee.Model.Utilizador;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEditarGarantia extends Fragment {
    String nome;
    String codigoBarras;
    String marca;
    String serialNumber;
    String periodo;
    String fornecedor;
    String localCompra;
    String dataCompra;
    String preco;
    String imagemCompraURL;
    Intent dataProduto;
    Produto produto;
    EditText editTextNome;
    EditText editTextCodigoBarras;
    EditText editTextMarca;
    EditText editTextSerialNumber;
    EditText editTextPeriodo;
    EditText editTextFornecedor;
    EditText editTextLocalCompra;
    EditText editTextDataCompra;
    EditText editTextPreco;
    String keyProduto;
    Button btnConfimar;
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    public FragmentEditarGarantia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_editar_garantia, container, false);
        // Inflate the layout for this fragment
        dataProduto = getActivity().getIntent();;
        nome = dataProduto.getStringExtra("nome");
        codigoBarras = dataProduto.getStringExtra("codigoBarras");
        marca = dataProduto.getStringExtra("marca");
        serialNumber = dataProduto.getStringExtra("serialNumber");
        periodo = dataProduto.getStringExtra("periodo");
        imagemCompraURL = dataProduto.getStringExtra("imagem");
        fornecedor = dataProduto.getStringExtra("fornecedor");
        localCompra = dataProduto.getStringExtra("localCompra");
        dataCompra = dataProduto.getStringExtra("dataCompra");
        preco = dataProduto.getStringExtra("preco");

        btnConfimar = (Button)rootView.findViewById(R.id.buttonConfimarDetalhes);
        editTextNome = (EditText)rootView.findViewById(R.id.editTextNomeProdutoEditar);
        editTextCodigoBarras = (EditText)rootView.findViewById(R.id.editTextCodigoBarrasEditar);
        editTextMarca = (EditText)rootView.findViewById(R.id.editTextMarcaEditar);
        editTextSerialNumber = (EditText)rootView.findViewById(R.id.editTextCodSerieEditar);
        editTextPeriodo = (EditText)rootView.findViewById(R.id.editTextPeriodoGarantiaEditar);
        editTextFornecedor = (EditText)rootView.findViewById(R.id.editTextFornecedorEditar);
        editTextLocalCompra = (EditText)rootView.findViewById(R.id.editTextLocalCompraEditar);
        editTextDataCompra = (EditText)rootView.findViewById(R.id.editTextDataCompraEditar);
        editTextPreco = (EditText)rootView.findViewById(R.id.editTextPrecoEditar);

        editTextNome.setText(nome);
        editTextCodigoBarras.setText(codigoBarras);
        editTextMarca.setText(marca);
        editTextSerialNumber.setText(serialNumber);
        editTextPeriodo.setText(periodo);
        editTextFornecedor.setText(fornecedor);
        editTextDataCompra.setText(dataCompra);
        editTextPreco.setText(preco);
        editTextLocalCompra.setText(localCompra);
        btnConfimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produto = new Produto();
                produto.setNome(editTextNome.getText().toString());
                produto.setCodigoBarras(editTextCodigoBarras.getText().toString());
                produto.setMarca(editTextMarca.getText().toString());
                produto.setSerialNumber(editTextSerialNumber.getText().toString());
                produto.setPeriodo(editTextPeriodo.getText().toString());
                produto.setFornecedor(editTextFornecedor.getText().toString());
                produto.setDataCompra(editTextDataCompra.getText().toString());
                produto.setPreco(editTextPreco.getText().toString());
                produto.setLocalCompra(editTextLocalCompra.getText().toString());
                produto.setImagem(imagemCompraURL);
                produtosRef.child(Profile.getCurrentProfile().getId()).child(keyProduto).setValue(produto);
                Toast.makeText(getContext(), "Editado com sucesso.", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });
        produtosRef.child(Profile.getCurrentProfile().getId()).orderByChild("codigoBarras").equalTo(editTextCodigoBarras.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    keyProduto = child.getKey();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return rootView;
    }

}

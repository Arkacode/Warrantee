package pt.ipca.cm.warrantee;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInformacaoDetalhes extends Fragment {
    String nome;
    String codigoBarras;
    String marca;
    String serialNumber;
    String periodo;
    String fornecedor;
    String localCompra;
    String dataCompra;
    String preco;
    Intent dataProduto;
    TextView dataCmp;
    TextView localCmp;
    TextView precoProduto;
    TextView codigoBarrasProduto;
    TextView numSerie;
    TextView periodoInfo;
    public FragmentInformacaoDetalhes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_informacao_detalhes, container, false);
        // Inflate the layout for this fragment
        dataProduto = getActivity().getIntent();
        nome = dataProduto.getStringExtra("nome");
        codigoBarras = dataProduto.getStringExtra("codigoBarras");
        marca = dataProduto.getStringExtra("marca");
        serialNumber = dataProduto.getStringExtra("serialNumber");
        periodo = dataProduto.getStringExtra("periodo");
        fornecedor = dataProduto.getStringExtra("fornecedor");
        localCompra = dataProduto.getStringExtra("localCompra");
        dataCompra = dataProduto.getStringExtra("dataCompra");
        preco = dataProduto.getStringExtra("preco");

        dataCmp = (TextView)rootView.findViewById(R.id.textViewDataCompra);
        localCmp = (TextView)rootView.findViewById(R.id.textViewLocalCompra);
        precoProduto = (TextView)rootView.findViewById(R.id.textViewPrecoProduto);
        codigoBarrasProduto = (TextView)rootView.findViewById(R.id.textViewCodigoBarrasProduto);
        numSerie = (TextView)rootView.findViewById(R.id.textViewNumSerie);
        periodoInfo = (TextView)rootView.findViewById(R.id.textViewPrecoProduto);

        dataCmp.setText(dataCompra);
        localCmp.setText(localCompra);
        precoProduto.setText(preco);
        codigoBarrasProduto.setText(codigoBarras);
        numSerie.setText(serialNumber);
        periodoInfo.setText(periodo);

        return rootView;
    }

}

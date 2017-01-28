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
public class FragmentInformacaoGarantia extends Fragment {

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
    TextView nomeGarantia;
    TextView periodoGarantia;
    public FragmentInformacaoGarantia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_informacao_garantia, container, false);
        nomeGarantia = (TextView)rootView.findViewById(R.id.textViewNomeInfoGarantia);
        periodoGarantia = (TextView)rootView.findViewById(R.id.textViewDiasInfoGarantia);
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


        nomeGarantia.setText(nome);
        periodoGarantia.setText(periodo);
        return rootView;
    }

}

package pt.ipca.cm.warrantee;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
    ProgressBar progressBarDias;
    private long days;
    float progress;
    public FragmentInformacaoGarantia() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_informacao_garantia, container, false);
        nomeGarantia = (TextView)rootView.findViewById(R.id.textViewNomeInfoGarantia);
        periodoGarantia = (TextView)rootView.findViewById(R.id.textViewDiasInfoGarantia);
        progressBarDias = (ProgressBar) rootView.findViewById(R.id.progressBarInfoGarantia);
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

        String dt = dataCompra;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, Integer.valueOf(periodo) * 365);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        String date = sdf.format(c.getTime());
        Date futureDate = null;
        try {
            futureDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        long diff = futureDate.getTime() - currentDate.getTime();
        days = diff / (24 * 60 * 60 * 1000);
        int totalDias = Integer.valueOf(periodo) * 365;
        float diferencaDias = totalDias - days;
        progress = (diferencaDias * 100) / (Integer.valueOf(periodo) * 365);
        progressBarDias.setProgress(Math.round(progress));
        nomeGarantia.setText(nome);
        periodoGarantia.setText(String.valueOf(days) + " dias restantes");
        return rootView;
    }

}

package pt.ipca.cm.warrantee;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pt.ipca.cm.warrantee.Model.Produto;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGarantias extends Fragment {
    ListViewAdapter adapter;
    ListView listViewGarantias;
    List<Produto> produtos = new ArrayList<>();
    MainActivity mainActivity = (MainActivity) getActivity();
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    private long days;
    float progress;
    public FragmentGarantias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_garantias, container, false);
        listViewGarantias=(ListView)rootView.findViewById(R.id.listViewGarantias);
        listViewGarantias.setItemsCanFocus(false);
        adapter=new ListViewAdapter();
        listViewGarantias.setAdapter(null);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabGarantias);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProdutoInsertActivity.class);
                startActivity(intent);
            }
        });



        listViewGarantias.setAdapter(adapter);

        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot currentUser : dataSnapshot.getChildren()){
                    if (currentUser.getKey().equals(String.valueOf(Profile.getCurrentProfile().getId()))){
                        for (DataSnapshot produto : currentUser.getChildren()){
                            Produto p = produto.getValue(Produto.class);

                            int isDiff = 0;
                            for (int i = 0; i < produtos.size(); i++){
                                if (p != produtos.get(i)){
                                    isDiff++;
                                }
                            }
                            if (isDiff == produtos.size()){
                                produtos.add(p);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }



    class ListViewAdapter extends BaseAdapter implements View.OnClickListener{

        LayoutInflater mInflater;

        public ListViewAdapter(){
            mInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return produtos.size();
        }

        @Override
        public Object getItem(int position) {
            return produtos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

           if (convertView == null)
                convertView = mInflater.inflate(R.layout.row_garantias, null);

            TextView textViewTitle=(TextView)convertView.findViewById(R.id.textViewTitulo);
            TextView textViewMarca =(TextView)convertView.findViewById(R.id.textViewMarca);
            TextView textViewDays =(TextView)convertView.findViewById(R.id.textViewDias);
            ProgressBar progressBarDias = (ProgressBar) convertView.findViewById(R.id.progressBarDias);
            textViewTitle.setText(produtos.get(position).getNome());
            textViewMarca.setText(produtos.get(position).getMarca());
            String dt = produtos.get(position).getDataCompra();  // Start date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(dt));
                c.add(Calendar.DATE, Integer.valueOf(produtos.get(position).getPeriodo()) * 365);
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
            int totalDias = Integer.valueOf(produtos.get(position).getPeriodo()) * 365;
            float diferencaDias = totalDias - days;
            progress = (diferencaDias * 100) / (Integer.valueOf(produtos.get(position).getPeriodo()) * 365);
            textViewDays.setText(String.valueOf(days) + " dias restantes");
            progressBarDias.setProgress(Math.round(progress));
            convertView.setTag(new Integer(position));
            convertView.setClickable(true);
            convertView.setOnClickListener(this);
            return convertView;
        }

        private void CalcularDiasRestantes(){

        }



        @Override
        public void onClick(View v) {
            Integer position=(Integer) v.getTag();
            Intent dataProduto = new Intent(getActivity().getApplicationContext(), InformacaoGarantiaActivity.class);
            dataProduto.putExtra("nome", produtos.get(position).getNome());
            dataProduto.putExtra("codigoBarras", produtos.get(position).getCodigoBarras());
            dataProduto.putExtra("serialNumber", produtos.get(position).getSerialNumber());
            dataProduto.putExtra("periodo", produtos.get(position).getPeriodo());
            dataProduto.putExtra("fornecedor", produtos.get(position).getFornecedor());
            dataProduto.putExtra("localCompra", produtos.get(position).getLocalCompra());
            dataProduto.putExtra("dataCompra", produtos.get(position).getDataCompra());
            dataProduto.putExtra("preco", produtos.get(position).getPreco());
            dataProduto.putExtra("imagem", produtos.get(position).getImagem());
            startActivity(dataProduto);
     }
    }
}

package pt.ipca.cm.warrantee;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pt.ipca.cm.warrantee.Model.Produto;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGarantias extends Fragment {
    ListViewAdapter adapter;
    ListView listViewGarantias;

    MainActivity mainActivity = (MainActivity) getActivity();
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    public FragmentGarantias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_garantias, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabGarantias);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "O Rodolfo é lindo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(),ProdutoInsertActivity.class);
                startActivity(intent);
            }
        });

        listViewGarantias=(ListView)rootView.findViewById(R.id.listViewGarantias);
        adapter=new ListViewAdapter();
        listViewGarantias.setAdapter(adapter);
        adapter.notifyDataSetChanged();


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
            return mainActivity.produtos.size();
        }

        @Override
        public Object getItem(int position) {
            return mainActivity.produtos.get(position);
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

            textViewTitle.setText(mainActivity.produtos.get(position).getNome());
            textViewMarca.setText(mainActivity.produtos.get(position).getMarca());
            textViewDays.setText(mainActivity.produtos.get(position).getPeriodo());

            convertView.setTag(new Integer(position));
            convertView.setClickable(true);
            convertView.setOnClickListener(this);
            return convertView;
        }



        @Override
        public void onClick(View v) {
           /*Integer position=(Integer) v.getTag();
            Log.d("NewsFeed","Desc:"+noticias.get(position).getDescription());
            Log.d("NewsFeed","Clicked:"+noticias.get(position).getCity());
            Log.d("NewsFeed","Clicked:"+noticias.get(position).getDatePub());
            Log.d("NewsFeed","Clicked:"+noticias.get(position).getLocation());
            Log.d("NewsFeed","Clicked:"+noticias.get(position).getImageLink());
            //inserir tudo num bundle a informação do publicação
            Bundle bundle = new Bundle();
            bundle.putString(InfoArticleFragment.EXTRA_TITLE,noticias.get(position).getTitle());
            bundle.putString(InfoArticleFragment.EXTRA_DESC,noticias.get(position).getDescription());
            bundle.putString(InfoArticleFragment.EXTRA_PUB_DATE,noticias.get(position).getDatePub().toString());
            bundle.putString(InfoArticleFragment.EXTRA_LINK_IMAGE,noticias.get(position).getImageLink());
            bundle.putString(InfoArticleFragment.EXTRA_CITY,noticias.get(position).getCity());
            bundle.putString(InfoArticleFragment.EXTRA_LOCATION,noticias.get(position).getLocation());
            //mudar de fragment
            InfoArticleFragment infoArticleFragment = new InfoArticleFragment();
            FragmentManager fragmentManager=getFragmentManager();
            infoArticleFragment.setArguments(bundle);
            fragmentManager.
                    beginTransaction().
                    replace(R.id.content_frame,infoArticleFragment).
                    setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).
                    addToBackStack(null).
                    commit();

        }*/

            Integer position=(Integer) v.getTag();
            Intent dataProduto = new Intent(getActivity().getApplicationContext(), InformacaoGarantiaActivity.class);
            dataProduto.putExtra("nome", mainActivity.produtos.get(position).getNome());
            dataProduto.putExtra("codigoBarras", mainActivity.produtos.get(position).getCodigoBarras());
            dataProduto.putExtra("serialNumber", mainActivity.produtos.get(position).getSerialNumber());
            dataProduto.putExtra("periodo", mainActivity.produtos.get(position).getPeriodo());
            dataProduto.putExtra("fornecedor", mainActivity.produtos.get(position).getFornecedor());
            dataProduto.putExtra("localCompra", mainActivity.produtos.get(position).getLocalCompra());
            dataProduto.putExtra("dataCompra", mainActivity.produtos.get(position).getDataCompra());
            dataProduto.putExtra("preco", mainActivity.produtos.get(position).getPreco());

            startActivity(dataProduto);
     }
    }
}

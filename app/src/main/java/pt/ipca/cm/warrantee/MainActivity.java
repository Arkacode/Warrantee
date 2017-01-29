package pt.ipca.cm.warrantee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pt.ipca.cm.warrantee.Model.Categoria;
import pt.ipca.cm.warrantee.Model.Moeda;
import pt.ipca.cm.warrantee.Model.Produto;
import pt.ipca.cm.warrantee.Model.Utilizador;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static List<Categoria> categorias = new ArrayList<>();
    String uid = Profile.getCurrentProfile().getId();
    Categoria categoria;
    Moeda moeda;
    Utilizador utilizadorC;
    Utilizador utilizador;
    DatabaseReference utilizadoresRef = FirebaseDatabase.getInstance().getReference("utilizadores");
    DatabaseReference categoriasRef = FirebaseDatabase.getInstance().getReference("categorias");
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    DatabaseReference garantiasRef = FirebaseDatabase.getInstance().getReference("garantias");
    DatabaseReference moedasRef = FirebaseDatabase.getInstance().getReference("moedas");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        navigationView.getMenu().getItem(0).setChecked(true);
        final TextView textViewNomeDrawer = (TextView) hView.findViewById(R.id.textViewNomeDrawer);
        final TextView textViewEmailDrawer = (TextView) hView.findViewById(R.id.textViewEmailDrawer);
        final CircleImageView imagePerfil = (CircleImageView) hView.findViewById(R.id.imageViewDrawer);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame, new FragmentGarantias());
        tx.commit();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (!extras.getString("nome").equals("") && !extras.getString("email").equals("")) {
                String nome = extras.getString("nome");
                String email = extras.getString("email");
                utilizadorC = new Utilizador();
                utilizadorC.setNome(nome);
                utilizadorC.setEmail(email);
                utilizadorC.setNumTel("");
                utilizadorC.setPais("");
                utilizadorC.setCodPostal("");
                utilizadoresRef.child(Profile.getCurrentProfile().getId()).setValue(utilizadorC);
            } else {

            }
        }
        iniciarDb();



       /* utilizadoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot currentUser : dataSnapshot.getChildren()){
                    if (currentUser.getKey().equals(String.valueOf(Profile.getCurrentProfile().getId()))){
                        for (DataSnapshot utilizador : currentUser.getChildren()){
                            Utilizador o = utilizador.getValue(Utilizador.class);

                            int isDiff = 0;
                            for (int i = 0; i < utilizadorArray.size(); i++){
                                if (o != utilizadorArray.get(i)){
                                    isDiff++;
                                }
                            }
                            if (isDiff == utilizadorArray.size()){
                                utilizadorArray.add(o);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

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



        utilizadoresRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    utilizador = dataSnapshot.getValue(Utilizador.class);
                    textViewEmailDrawer.setText(utilizador.getEmail());
                    textViewNomeDrawer.setText(utilizador.getNome());
                    Picasso.with(getApplicationContext())
                            .load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large")
                            .into(imagePerfil);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }



    public void iniciarDb(){
        categoria = new Categoria();
        String[] categoriaArray =  { "Computadores", "Eletrodomesticos", "Telemóveis", "Televisões", "Crianças & Bebés", "Automóveis", "Saúde", "Jardim", "Casa", "Outros"};
        for(int i = 0; i < categoriaArray.length; i++) {
            categoria.setDescricao(categoriaArray[i]);
            categoriasRef.child(String.valueOf(i + 1)).setValue(categoria);
        }
        moeda = new Moeda();
        String[] moedaArray =  { "USD", "EUR","GBP", "BRL"};
        int size =moedaArray.length;
        for(int i = 0; i < size; i++) {
            moeda.setDescricao(moedaArray[i]);
            moedasRef.child(String.valueOf(i+1)).setValue(moeda);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_garantias) {
            FragmentGarantias fragmentSobre = new FragmentGarantias();
            android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame, fragmentSobre);
            fragmentTransaction1.commit();
            // Handle the camera action
        } else if (id == R.id.nav_categorias) {
            FragmentCategorias fragmentCategoria = new FragmentCategorias();
            android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame, fragmentCategoria);
            fragmentTransaction1.commit();

        } else if (id == R.id.nav_perfil) {

            FragmentPerfil fragmentPerfil= new FragmentPerfil();
            android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame, fragmentPerfil);
            fragmentTransaction1.commit();

        } else if (id == R.id.nav_settings) {
            FragmentSettings fragmentSobre = new FragmentSettings();
            android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame, fragmentSobre);
            fragmentTransaction1.commit();


        } else if (id == R.id.nav_logout){
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}

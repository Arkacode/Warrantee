package pt.ipca.cm.warrantee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
import pt.ipca.cm.warrantee.Model.Utilizador;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent intent = getIntent();
    String uid = Profile.getCurrentProfile().getId();
    Utilizador utilizador;
    DatabaseReference utilizadoresRef = FirebaseDatabase.getInstance().getReference("utilizadores");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (uid == null){
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            finish();
        }
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
        final TextView textViewNomeDrawer = (TextView) hView.findViewById(R.id.textViewNomeDrawer);
        final TextView textViewEmailDrawer = (TextView) hView.findViewById(R.id.textViewEmailDrawer);
        final CircleImageView imagePerfil = (CircleImageView) hView.findViewById(R.id.imageViewDrawer);
       /* textViewNomeDrawer.setText();
        textViewEmailDrawer.setText();*/
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame, new FragmentGarantias());
        tx.commit();


        if (uid == null) {
            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        else
        {
            utilizadoresRef.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    utilizador = dataSnapshot.getValue(Utilizador.class);
                    textViewEmailDrawer.setText(utilizador.getEmail());
                    textViewNomeDrawer.setText(utilizador.getNome());

                    Picasso.with(getApplicationContext())
                            .load("https://graph.facebook.com/" + utilizador.getId() + "/picture?type=large")
                            .into(imagePerfil);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        } else if (id == R.id.nav_perfil) {

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



}

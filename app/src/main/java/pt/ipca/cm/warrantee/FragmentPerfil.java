package pt.ipca.cm.warrantee;


import android.graphics.Picture;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import pt.ipca.cm.warrantee.Model.Utilizador;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {
    DatabaseReference utilizadoresRef = FirebaseDatabase.getInstance().getReference("utilizadores");
    String uid = Profile.getCurrentProfile().getId();
    Utilizador utilizador;
    TextView textViewNome;
    ImageView imagemPerfil;
    EditText nome;
    EditText pais;
    EditText numTel;
    EditText codPost;
    Button btnConfirmar;
    public FragmentPerfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        imagemPerfil = (ImageView)rootView.findViewById(R.id.imageViewDrawerPerfil);
        textViewNome = (TextView)rootView.findViewById(R.id.textViewNomeCompleto);
        nome = (EditText)rootView.findViewById(R.id.editTextNome);
        pais = (EditText)rootView.findViewById(R.id.editTextPais);
        numTel = (EditText)rootView.findViewById(R.id.editTextNumTlm);
        codPost = (EditText)rootView.findViewById(R.id.editTextCodPostal);
        btnConfirmar =(Button)rootView.findViewById(R.id.buttonConfirmar);

        utilizadoresRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                utilizador = dataSnapshot.getValue(Utilizador.class);
                textViewNome.setText(utilizador.getNome());
                nome.setText(utilizador.getNome());
                pais.setText(utilizador.getPais());
                numTel.setText(utilizador.getNumTel());
                codPost.setText(utilizador.getCodPostal());
                Picasso.with(getApplicationContext())
                        .load("https://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large")
                        .into(imagemPerfil);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilizadoresRef.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        utilizador.setNome(nome.getText().toString());
                        utilizador.setPais(pais.getText().toString());
                        utilizador.setNumTel(numTel.getText().toString());
                        utilizador.setCodPostal(codPost.getText().toString());
                        utilizadoresRef.child(Profile.getCurrentProfile().getId()).setValue(utilizador);
                        Toast.makeText(getContext(), "Editado com sucesso.", Toast.LENGTH_SHORT).show();
                        FragmentGarantias fragmentSobre = new FragmentGarantias();
                        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.frame, fragmentSobre);
                        fragmentTransaction1.commit();
                    }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
        });




        return rootView;
    }

}

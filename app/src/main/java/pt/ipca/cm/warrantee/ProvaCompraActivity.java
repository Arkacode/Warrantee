package pt.ipca.cm.warrantee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class ProvaCompraActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_compra);
        this.imageView = (ImageView)this.findViewById(R.id.imageViewFotoComprovativo);
        Button photoButton = (Button) this.findViewById(R.id.buttonTirarFoto);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        btnNext = (Button)findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.buttonNext) {
            Intent myIntent = new Intent(ProvaCompraActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        }
    }
}
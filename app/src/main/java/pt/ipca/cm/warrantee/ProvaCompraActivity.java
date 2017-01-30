package pt.ipca.cm.warrantee;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.ipca.cm.warrantee.Model.Produto;

public class ProvaCompraActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    StorageReference storage;
    Button btnNext;
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference provasRef;
    ContentValues values;
    Bundle data;
    Produto produto;
    String codBarras;
    String nome;
    String marca;
    String codSerie;
    String categoria;
    String periodo;
    String fornecedor;
    String localCompra;
    String dataCompra;
    String preco;
    String uid;
    DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produto");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_compra);
        data = getIntent().getExtras();
        codBarras = data.getString("codBarras");
        nome = data.getString("nome");
        marca = data.getString("marca");
        categoria = data.getString("categoria");
        periodo = data.getString("periodo");
        fornecedor = data.getString("fornecedor");
        localCompra = data.getString("localCompra");
        dataCompra = data.getString("dataCompra");
        preco = data.getString("preco");
        codSerie = data.getString("codSerie");
        uid = FirebaseDatabase.getInstance().getReference().push().getKey();
        imageView = (ImageView)this.findViewById(R.id.imageViewFotoComprovativo);
        storage = FirebaseStorage.getInstance().getReference();
        provasRef =  storage.child(nome+codBarras+".jpg");
        Button photoButton = (Button) this.findViewById(R.id.buttonTirarFoto);
        progressDialog = new ProgressDialog(this);
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        btnNext = (Button)findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "pt.ipca.cm.warrantee.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private void rotate(float degree) {
        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(0);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            galleryAddPic();
            setPic();
            rotate(90);

        }
    }



    public void onClick(View v) {
        if (v.getId() == R.id.buttonNext) {
//            progressDialog.setMessage("A fazer upload da imagem...");
//            progressDialog.show();
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = provasRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                   // progressDialog.dismiss();
                    produto = new Produto();

                    produto.setCodigoBarras(codBarras);
                    produto.setNome(nome);
                    produto.setSerialNumber(codSerie);
                    produto.setMarca(marca);
                    produto.setPeriodo(periodo);
                    produto.setFornecedor(fornecedor);
                    produto.setLocalCompra(localCompra);
                    produto.setDataCompra(dataCompra);
                    produto.setPreco(preco);
                    produto.setImagem(downloadUrl.toString());
                    produtosRef.child(Profile.getCurrentProfile().getId()).child(uid).setValue(produto);
                }
            });
            Intent myIntent = new Intent(ProvaCompraActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();
        }
    }
}
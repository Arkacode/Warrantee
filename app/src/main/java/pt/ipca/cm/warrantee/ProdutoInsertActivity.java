package pt.ipca.cm.warrantee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pt.ipca.cm.warrantee.com.google.zxing.integration.android.IntentIntegrator;
import pt.ipca.cm.warrantee.com.google.zxing.integration.android.IntentResult;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProdutoInsertActivity extends AppCompatActivity implements OnClickListener{
    private Button scanBtn;
    private TextView editTextScanSerial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_insert);
        scanBtn = (Button)findViewById(R.id.buttonScanBarras);
        editTextScanSerial = (TextView)findViewById(R.id.editTextCodigoBarras);
        scanBtn.setOnClickListener(this);

    }
    public void onClick(View v){
        //respond to clicks
        if(v.getId()==R.id.buttonScanBarras){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            /*formatTxt.setText("FORMAT: " + scanFormat);*/
            editTextScanSerial.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

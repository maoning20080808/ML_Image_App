package simonhkswan.uploadapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class results extends Activity {
    public Button recapturebtn;
    public Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Results_Activity: ", "New Activity Created/Loaded.");

        setContentView(R.layout.activity_results);
        recapturebtn = (Button) findViewById(R.id.recaptureBtn);
        savebtn = (Button) findViewById(R.id.saveBtn);
        recapturebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returntoUpload();
            }
        });

        Intent intent = getIntent();
        Log.e("Results_Activity: ", "Retrieved array length: " + String.valueOf(intent.getIntArrayExtra("Pixels").length));
        int[] pixels = intent.getIntArrayExtra("Pixels");
        Log.e("Results: ", "Image Array Obtained: " + Arrays.toString(Arrays.copyOfRange(pixels,0,50)));
    }


    private void returntoUpload() {
        Intent intent = new Intent(this, upload.class);
        startActivity(intent);
        finish();
    }


}

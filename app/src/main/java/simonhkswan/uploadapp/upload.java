package simonhkswan.uploadapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


public class upload extends Activity {
    private static final int SELECT_FILE = 1;
    public Button uploadbtn;
    public Button capturebtn;
    public int[] pixels;
    public int[] rgbvals;
    public int IMG_WIDTH = 299;
    public int IMG_HEIGHT = 299;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadbtn = (Button) findViewById(R.id.uploadBtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK){
            Log.e("Data received", String.valueOf(data.getData()));
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        imageUri);
                Log.e("Bitmap obtained: ", String.valueOf(bitmap));
                pixels = new int[IMG_WIDTH*IMG_HEIGHT];
                rgbvals = new int[IMG_HEIGHT*IMG_WIDTH*3];
                Log.e("Bitmap width: ", String.valueOf(bitmap.getWidth()));
                Log.e("Bitmap Height: ", String.valueOf(bitmap.getHeight()));
                pixels = Bitman.getPixelSubset(bitmap, (bitmap.getWidth()-IMG_WIDTH)/2,
                        (bitmap.getHeight()-IMG_HEIGHT)/2, IMG_WIDTH, IMG_HEIGHT);
                Log.e("Pixels extracted: ", Arrays.toString(Arrays.copyOfRange(pixels,0,50)));

                for (int i = 0; i < pixels.length; ++i) {
                    final int val = pixels[i];
                    rgbvals[i * 3] = (((val >> 16) & 0xFF));
                    rgbvals[i * 3 + 1] = (((val >> 8) & 0xFF));
                    rgbvals[i * 3 + 2] = ((val & 0xFF));
                }
                Log.e("RGB values calculated: ", Arrays.toString(Arrays.copyOfRange(rgbvals,0,50)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent resultintent = new Intent(this, results.class);
            Log.e("Upload_Activity: ", "Created new intent.");
            resultintent.putExtra("Pixels", pixels);
            Log.e("Upload_Activity: ", "Put extra into intent.");

            startActivity(resultintent);
            Log.e("Upload_Activity", "Start Activity called.");
        }
    }
}

package com.example.hiddengems;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera_Activity extends AppCompatActivity {

    ImageView imageView;
    Button btOpen;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);



    }

    public void takePic (View v) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      try {
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
      } catch (ActivityNotFoundException e) {
          Log.d("Fail","Fail to take camera");
      }
    }

    //public void cameraView(View v) {

      //  if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
      //      Intent i = new Intent(this, Camera_Activity.class);
    //        startActivity(i);
    //    } else {
     //       Toast.makeText(this,"No Camera Available",Toast.LENGTH_SHORT);
  //      }

  //  }


  //  @Override
  //  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  //      if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
   //         Bundle extras = data.getExtras();
  //          Bitmap imageBitmap = (Bitmap) extras.get("data");
   //         imageView.setImageBitmap(imageBitmap);
   //     }
  //  }

}
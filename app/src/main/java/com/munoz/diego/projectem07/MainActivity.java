package com.munoz.diego.projectem07;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.munoz.diego.projectem07.modelo.Modelo;
import com.munoz.diego.projectem07.modelo.Post;
import com.munoz.diego.projectem07.ui.Perfil;
import com.munoz.diego.projectem07.ui.home.HomeFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final int APP_WRITE_EXTERNAL_STORAGE = 27;

    private AppBarConfiguration mAppBarConfiguration;

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Post.initNextId();
        HomeFragment.first_run = true;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_make_post, R.id.nav_map,
                R.id.nav_edit_profile, R.id.nav_settings, R.id.nav_about_us)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Here, thisActivity is the current activity
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, APP_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void handleAbrirPerfil(View view) {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }

    public void handleCerrarSesion(MenuItem item) {
        Modelo.getModelo().getFirebaseAuth().signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private String currentPhotoPath;

    private String yeet;

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
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void handleHacerFoto(View view){
        onLaunchCamera();
        //galleryAddPic();
    }

    public void onLaunchCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {}
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.munoz.diego.projectem07.fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                ImageView mImageView = findViewById(R.id.iv_foto);

                Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse("file://" + currentPhotoPath));

                ExifInterface ei = new ExifInterface(currentPhotoPath);
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(mImageBitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(mImageBitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(mImageBitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = mImageBitmap;
                }

                /*savePost(
                        rotatedBitmap,
                        new Post()
                            .setId(Post.getNextId())
                            .setDescripcion("asdsd")
                            .setTitulo("Titulo"));
                */

                m_currentFoto = rotatedBitmap;

                mImageView.setImageBitmap(rotatedBitmap);
            } catch (IOException e) {
                Log.e("fotoSet", Objects.requireNonNull(e.getMessage()));
            }
        }
    }

    private Bitmap m_currentFoto;

    public void handleSubirPost(View view){
        EditText m_new_post_animal = findViewById(R.id.et_new_post_animal);
        EditText m_new_post_titulo = findViewById(R.id.et_new_post_titulo);
        ImageView mImageView = findViewById(R.id.iv_foto);

        if (m_currentFoto != null){
            if(!m_new_post_titulo.getText().toString().equals("")){
                if(!m_new_post_animal.getText().toString().equals("")) {
                    savePost(
                            m_currentFoto,
                            new Post()
                                    .setId(Post.getNextId())
                                    .setDescripcion(m_new_post_animal.getText().toString())
                                    .setTitulo(m_new_post_titulo.getText().toString()));

                    m_new_post_animal.setText("");
                    m_new_post_titulo.setText("");
                    m_currentFoto = null;
                    mImageView.setImageBitmap(null);

                    Toast.makeText(getApplicationContext(), "S'ha pujat el post", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Has d'introduir un titol", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Has d'introduir el nom de l'animal", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Puja primer una foto", Toast.LENGTH_SHORT).show();
        }

    }

    public void savePost(Bitmap bitmap, Post p) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 33, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

        DatabaseReference ref;

        ref = FirebaseDatabase.getInstance()
                .getReference("posts")
                .child(p.getIdAsString(p.getId()))
                .child("titulo");
        ref.setValue(p.getTitulo());

        ref = FirebaseDatabase.getInstance()
                .getReference("posts")
                .child(p.getIdAsString(p.getId()))
                .child("desc");
        ref.setValue(p.getDescripcion());

        ref = FirebaseDatabase.getInstance()
                .getReference("posts")
                .child(p.getIdAsString(p.getId()))
                .child("imageUrl");
        ref.setValue(imageEncoded);

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}

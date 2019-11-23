package com.example.fastrest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class mod_nota extends AppCompatActivity {

    private String APP_DIRECTORY = "myPicture/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY+ "media";
    private String TEMPORAL_PICTURE_NAME = "temporal";
    private final int SELECT_PICTURE = 200;
    private ImageView imageView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri ubicacion;
    private Button guardarCat;
    private EditText des, nom, pre;
    private String nombre, descr, prec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_nota);
        imageView = findViewById(R.id.foto);
        guardarCat = findViewById(R.id.catGuardar);
        des = findViewById(R.id.descrip);
        nom = findViewById(R.id.catNombre);
        firebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Button button = findViewById(R.id.cargar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] opcion = {"Tomar foto","Galeria", "Salir"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(mod_nota.this);
                builder.setTitle("Elige una opción");
                builder.setItems(opcion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opcion[i] == "Tomar foto"){
                            dispatchTakePictureIntent();
                        }
                        else if(opcion[i] == "Galeria"){
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Selecciona app"), SELECT_PICTURE);
                        }
                        else if (opcion[i] == "Salir"){
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        guardarCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                date.getTime();
                descr = des.getText().toString();
                nombre = nom.getText().toString();
                prec = pre.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                //DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
                StorageReference imagen = storageReference.child(Long.toString(date.getTime())).child("fotoProducto");
                double a = (int) Math.random();

                String randomId = Double.toString(a);
                if(!nombre.isEmpty() && !descr.isEmpty() && !prec.isEmpty()){
                    UploadTask task = imagen.putFile(ubicacion);
                    task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(mod_nota.this, "Se ha subido la foto", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Map<String,Object> map = new HashMap();
                    map.put("descripcion", descr);
                    map.put("nombreProd", nombre);
                    map.put("prec", prec);
                    databaseReference.child("productos").child(Long.toString(date.getTime())).setValue(map);
                    Toast.makeText(mod_nota.this, "Se ha guardado la información", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(mod_nota.this, "Llene todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    ubicacion = data.getData();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK){
                    ubicacion = data.getData();
                    imageView.setImageURI(ubicacion);

                }
                break;
        }
    }

}

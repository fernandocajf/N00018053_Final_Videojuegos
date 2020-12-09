package com.cabrera.jorge.examfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cabrera.jorge.examfinal.entities.Pokemones;
import com.cabrera.jorge.examfinal.services.PokemonService;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarPokemones extends AppCompatActivity {
    static final int REQUEST_PICK_FROM_GALLERY = 2;

    private ImageView imgPokemonCrear;
    private String imageBase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pokemones);

        imgPokemonCrear = findViewById(R.id.imgPokemonCrear);
        EditText etxtNombre = findViewById(R.id.etxtNombre);
        EditText etxtTipo = findViewById(R.id.etxtTipo);
        EditText etxtLatitud = findViewById(R.id.etxtLatitud);
        EditText etxtLongitud = findViewById(R.id.etxtLongitud);
        Button btnGaleria = findViewById(R.id.btnGaleria);
        TextView txtAceptar = findViewById(R.id.txtAceptar);
        TextView txtCancelar = findViewById(R.id.txtCancelar);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService pokemonService = retrofit.create(PokemonService.class);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CAMERA}, 1000);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1001);
        }

        btnGaleria.setOnClickListener(v -> {
            galleryAddPic();
        });

        txtAceptar.setOnClickListener(v -> {
            Pokemones newPokemon = new Pokemones();

            newPokemon.setNombre(etxtNombre.getText().toString());
            newPokemon.setTipo(etxtTipo.getText().toString());
            newPokemon.setLatitude(Float.parseFloat(etxtLatitud.getText().toString()) * -1);
            newPokemon.setLongitude(Float.parseFloat(etxtLongitud.getText().toString()) * -1);
            newPokemon.setImagen(imageBase64);

            Call<Pokemones> call = pokemonService.createPokemones(newPokemon);

            call.enqueue(new Callback<Pokemones>() {
                @Override
                public void onResponse(Call<Pokemones> call, Response<Pokemones> response) {
                    Intent intent = new Intent(RegistrarPokemones.this, MostrarPokemones.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Pokemones> call, Throwable t) {

                }
            });
        });

        txtCancelar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarPokemones.this, MainActivity.class);
            startActivity(intent);
        });

    }
    private void galleryAddPic() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_PICK_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PICK_FROM_GALLERY && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            imgPokemonCrear.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            //Añadir a base64
            imageBase64 = base64(picturePath);

        }
    }
    public static String base64(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG,100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.NO_WRAP);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }
}
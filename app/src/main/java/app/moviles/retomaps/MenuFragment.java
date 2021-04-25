package app.moviles.retomaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{

    private static final int CODIGO_SELECCIONA = 10;
    private static final int CODIGO_TOMAR = 11;
    private static final String CARPETA_PRINCIPAL = "retomaps/";
    private static final String CARPETA_IMAGEN = "imagenes";
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;
    private String path;


    private EditText editTextTextPersonName;
    private TextView dirrecionText;
    private Button registrarBtn;
    private ImageButton agregarPicBtn, buscarLocacionbtn;
    private ImageView picView;
    private View root;
    private File file;
    private Bitmap bitmap;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_menu, container, false);
        editTextTextPersonName = root.findViewById(R.id.editTextTextPersonName);
        dirrecionText = root.findViewById(R.id.dirrecionText);
        registrarBtn = root.findViewById(R.id.registrarBtn);
        buscarLocacionbtn = root.findViewById(R.id.buscarLocacionbtn);
        registrarBtn.setOnClickListener(this);
        buscarLocacionbtn.setOnClickListener(this);
        agregarPicBtn = root.findViewById(R.id.agregarPicBtn);
        agregarPicBtn.setOnClickListener(this);
        picView = root.findViewById(R.id.picView);

        return root;
    }

    public void buscarLocacion() {
        new Thread(
                () ->{
                    try{
                        String location = editTextTextPersonName.getText().toString();
                        Geocoder geocoder = new Geocoder(root.getContext());
                        List<Address> direcciones = geocoder.getFromLocationName(location,5);
                        dirrecionText.setText(direcciones.get(0).getAddressLine(0));
                    }catch(IOException ioe){
                        System.err.println("IOEstream");
                    }
                }
        ).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buscarLocacionbtn:
                buscarLocacion();
                break;
            case R.id.agregarPicBtn:
                mostrarOpciones();
                break;    
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        switch (requestCode){
            case CODIGO_SELECCIONA:
                Uri miPath= data.getData();
                picView.setImageURI(miPath);
                break;
            case CODIGO_TOMAR:
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                picView.setImageBitmap(bitmap);
                break;
        }
    }

    private void mostrarOpciones(){
        final CharSequence[] opciones = {"Tomar foto", "elegir de galeria", "cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Tomar foto")){
                    abrirCamara();
                }else if(opciones[i].equals("elegir de galeria")){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent, "Seleccione"),CODIGO_SELECCIONA);
                }else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void abrirCamara(){
        /*File miFile = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_PICTURES);
        boolean isCreada = miFile.exists();
        if(isCreada == false){
            isCreada = miFile.mkdirs();
            System.err.println(miFile);
        }*/

            /*Long consecutivo = System.currentTimeMillis();
            String nombre = consecutivo.toString()+".jpg";

            path = Environment.getExternalStorageDirectory() +File.separator+DIRECTORIO_IMAGEN+File.separator+nombre;

            file = new File(path);*/

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getContext().getPackageManager()) != null){
                File imagenArchivo = null;

                try{
                    imagenArchivo = crearImagen();
                }catch (IOException e){
                    Log.e("Error", e.toString());
                }

                if(imagenArchivo !=null){
                    Uri fotoUri = FileProvider.getUriForFile(getContext(),"app.moviles.retomaps.fileprovider", imagenArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                }
            }
            //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

            startActivityForResult(intent, CODIGO_TOMAR);
    }

    private File crearImagen() throws IOException{
        String nombreImagen = "foto";
        File directorio = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        path = imagen.getAbsolutePath();
        return  imagen;
    }
}
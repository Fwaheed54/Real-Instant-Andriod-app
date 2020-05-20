package com.FYP.Adil.realinstant.PostFragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.FYP.Adil.realinstant.Models.Helper;
import com.FYP.Adil.realinstant.R;

import java.io.File;
import java.io.IOException;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;


public class CameraFregment extends Fragment {
    private int REQUEST_CODE= 1;

    private static final int RC_PIC_CODE = 1001;
    String mCurrentPhotoPath;

    //All private declatraions for this Fragmengts
    ImageView resultImage;
    File File;
    Bundle bundle;
    View view;
    ContentValues values;
    Uri imageUri;


    public CameraFregment() {
        bundle = new Bundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_camera_fregment, container, false);


            values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getActivity().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


            // Make Objects
            resultImage = view.findViewById(R.id.result_image);

            //intent to the Photo library in phone storage
            Intent TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            TakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(TakePictureIntent, RC_PIC_CODE);


        // is ko remove krna hai  yahan sa

        /*Intent TakePictureIntent = new Intent();
        TakePictureIntent.setType("image/*");
        TakePictureIntent.setAction(Intent.ACTION_GET_CONTENT);
        TakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(Intent.createChooser(TakePictureIntent, "Select Picture"), 1);*/

        //  yahan tk


        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //remove krna ha yahan sa

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);



                File = Helper.GetFile(bitmap,getContext());


                //Make bundle to send all fragement data to an other fragement
                bundle.putSerializable("file",File);
                bundle.putString("TypeOfFile","image");

                //Make new fregements object withe type of Category fregements
                Fragment CF =   new CetagoryFragment();
                CF.setArguments(bundle);
                //Trensfer Camera fregement into Category Fregemenet
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,CF);
                fragmentTransaction.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    // yahan tk

        // This line off code set image on Camera Fregement and get from Mobile camera and also set in bitmap global object
        //this for image capturing
        //this for image
        if(requestCode == RC_PIC_CODE){
            if(resultCode == RESULT_OK){

                try {
                   String  imageurl = getRealPathFromURI(imageUri);
                   File = new File(imageurl);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Make bundle to send all fragement data to an other fragement
                bundle.putSerializable("file",File);
                bundle.putString("TypeOfFile","image");

                //Make new fregements object withe type of Category fregements
                Fragment CF =   new CetagoryFragment();
                CF.setArguments(bundle);
                //Trensfer Camera fregement into Category Fregemenet
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_container,CF);
                fragmentTransaction.commit();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}

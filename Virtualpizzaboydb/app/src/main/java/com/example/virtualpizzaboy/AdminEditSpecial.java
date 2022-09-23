package com.example.virtualpizzaboy;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;

public class AdminEditSpecial extends Fragment {
    ImageView img;
    Button btnchoose,btnupload;
    EditText name,desc,price;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("Specials");
    StorageReference reference = FirebaseStorage.getInstance().getReference().child("specials");
    Uri imageuri;
    String n,d,p;
    public AdminEditSpecial(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_edit_specials, container, false);


        img = view.findViewById(R.id.imgv);
        name = view.findViewById(R.id.nameofproduct);
        desc = view.findViewById(R.id.descriptionofproduct);
        price = view.findViewById(R.id.priceofproduct);
        btnupload = view.findViewById(R.id.uploadtodb);



        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryintent = new Intent();
                galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,2);
            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageuri!=null){
                    uploadtofirebase(imageuri);
                }else{
                    Toast.makeText(getContext(),"Please check if everything is selected",Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    private void uploadtofirebase(Uri uri) {
        AdminAdded adminAdded = new AdminAdded(uri.toString(), n,d, p);
        n = adminAdded.setName(name.getText().toString());
        d = adminAdded.setDesc(desc.getText().toString());
        p = adminAdded.setPrice(price.getText().toString());



        StorageReference fileref = reference.child(System.currentTimeMillis() + "."+getFileExtension(uri));
        fileref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelid = root.push().getKey();
                        root.child(modelid).setValue(adminAdded);
                        Toast.makeText(getContext(),"Uploaded successfully",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(getContext(),"Uploading in progress",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(),"Uploading failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExtension(Uri muri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2 && resultCode==RESULT_OK && data!=null ){
            imageuri = data.getData();
            img.setImageURI(imageuri);

        }
    }
}

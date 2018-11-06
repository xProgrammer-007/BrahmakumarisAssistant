package com.bkassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp {
    private final SignupActivity activity;
    private FirebaseAuth oAuth;
    private PDialog progressDialog;

    public SignUp(SignupActivity activity) {
        this.activity = activity;
        progressDialog = new PDialog(activity);
    }


    public void createAccountUsingEmailPassword(String email , String password){
       progressDialog.progressDialog("Please wait ");

        oAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(activity, "Successfully account created", Toast.LENGTH_SHORT).show();
                         progressDialog.dismisss();
                         activity.startActivity(
                                 new Intent(
                                         activity,
                                         HomeActivity.class
                                 )
                         );
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Oops , please try again later", Toast.LENGTH_SHORT).show();

                        progressDialog.dismisss();
                    }
                });
    }
}

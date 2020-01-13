package com.moshiur.alapon.network;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.models.UserDataModel;

public class FirebaseHelper {

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;


    public FirebaseHelper() {
        //empty constructor
    }

    public void signUpNewUser(UserDataModel userDataModel) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child("users").child(userDataModel.getUserID()).setValue(userDataModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //write successful
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void checkUserExistance(FirebaseUser firebaseUser) {
        mFirebaseUser = firebaseUser;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(mFirebaseUser.getUid());
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // user not exists sign up the new user
                    String userID = mFirebaseUser.getUid();
                    String userName = mFirebaseUser.getDisplayName();
                    String userEmail = mFirebaseUser.getEmail();
                    String userPhotoURL = mFirebaseUser.getPhotoUrl().toString();

                    //for bigger size image
                    for (UserInfo userInfo : mFirebaseUser.getProviderData()) {
                        if (userInfo.getProviderId().equals("facebook.com")) {
                            //facebook user
                            //update url add ?type=large
                            userPhotoURL += "?type=large";
                        } else if (userInfo.getProviderId().equals("google.com")) {
                            //google user
                            //update photo url
                            //replace 's96-c' to 's400-c'
                            userPhotoURL = userPhotoURL.replace("s96-c", "s400-c");
                        }
                    }

                    UserDataModel userDataModel = new UserDataModel(userID, userName, userEmail, userPhotoURL);

                    //write data on realtime database
                    signUpNewUser(userDataModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}

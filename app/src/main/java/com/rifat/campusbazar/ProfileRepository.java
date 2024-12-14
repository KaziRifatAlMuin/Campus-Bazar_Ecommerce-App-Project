package com.rifat.campusbazar;

import androidx.annotation.NonNull;
import com.google.firebase.database.*;

public class ProfileRepository {
    private final DatabaseReference userRef;

    public ProfileRepository(String userEmail) {
        String key = userEmail.replace(".", ",");
        userRef = FirebaseDatabase.getInstance().getReference("users").child(key);
    }

    public interface OnProfileLoaded {
        void onProfile(ProfileInfo profileInfo);
        void onError(Exception e);
    }

    public interface OnProfileSaved {
        void onSuccess();
        void onError(Exception e);
    }

    public void getProfile(OnProfileLoaded callback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProfileInfo profileInfo = snapshot.getValue(ProfileInfo.class);
                callback.onProfile(profileInfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    public void saveProfile(ProfileInfo profileInfo, OnProfileSaved callback) {
        userRef.setValue(profileInfo)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onError);
    }
}

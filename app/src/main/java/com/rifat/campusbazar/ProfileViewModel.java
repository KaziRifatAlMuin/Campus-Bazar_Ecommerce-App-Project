package com.rifat.campusbazar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileInfo> profileInfo = new MutableLiveData<>();
    private ProfileRepository repository;
    private String email;

    public interface UpdateCallback {
        void onSuccess();
        void onError(Exception e);
    }

    public void init(String userEmail) {
        if (this.email == null) {
            this.email = userEmail;
            repository = new ProfileRepository(email);
            loadProfile();
        }
    }

    private void loadProfile() {
        repository.getProfile(new ProfileRepository.OnProfileLoaded() {
            @Override
            public void onProfile(ProfileInfo pInfo) {
                if (pInfo != null) {
                    profileInfo.setValue(pInfo);
                } else {
                    // If no profile found, create a default one
                    profileInfo.setValue(new ProfileInfo(
                            "Your Name",
                            email,
                            "Your Mobile",
                            "Your University",
                            "Your Hall",
                            "Your Address"
                    ));
                }
            }

            @Override
            public void onError(Exception e) {
                // Handle load error as needed, currently do nothing
            }
        });
    }

    public LiveData<ProfileInfo> getProfileInfo() {
        return profileInfo;
    }

    public void updateProfileInfo(ProfileInfo updatedInfo, UpdateCallback callback) {
        repository.saveProfile(updatedInfo, new ProfileRepository.OnProfileSaved() {
            @Override
            public void onSuccess() {
                profileInfo.setValue(updatedInfo);
                callback.onSuccess();
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}

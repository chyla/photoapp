package org.chyla.photoapp.Repository.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Event.ErrorEvent;
import org.chyla.photoapp.Model.Authenticator.Event.SuccessEvent;
import org.greenrobot.eventbus.EventBus;

public class LoginRepositoryImpl implements LoginRepository {

    @Override
    public void login(String username, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        EventBus.getDefault().post(new SuccessEvent());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseAuthException error = (FirebaseAuthException) e;
                        ErrorEvent event;

                        if (error.getErrorCode().equals("ERROR_INVALID_EMAIL")) {
                            event = new ErrorEvent(ErrorEvent.Type.MAIL_ERROR);
                        }
                        else {
                            event = new ErrorEvent(ErrorEvent.Type.PASSWORD_ERROR);
                        }

                        EventBus.getDefault().post(event);
                    }
                });
    }

    @Override
    public void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();
    }

    @Override
    public void register(String username, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        EventBus.getDefault().post(new SuccessEvent());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        EventBus.getDefault().post(new ErrorEvent(ErrorEvent.Type.PASSWORD_ERROR));
                    }
                });

    }

    @Override
    public boolean isUserLoggedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }

    @Override
    public User getLoggedUserInfo() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = auth.getCurrentUser();

        return new User(fbUser.getUid(), fbUser.getEmail());
    }

}

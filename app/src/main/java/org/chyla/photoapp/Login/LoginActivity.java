package org.chyla.photoapp.Login;

import org.chyla.photoapp.Login.DependencyInjection.DaggerLoginComponent;
import org.chyla.photoapp.Login.DependencyInjection.LoginComponent;
import org.chyla.photoapp.Login.DependencyInjection.LoginModule;
import org.chyla.photoapp.Login.Presenter.LoginPresenter;
import org.chyla.photoapp.R;
import org.chyla.photoapp.Synchronization.SynchronizationActivity;
import org.greenrobot.eventbus.EventBus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter presenter;

    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.login_progress) View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setupInjection();

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void setupInjection() {
        presenter = getPresenter();
    }

    public LoginPresenter getPresenter() {
        LoginComponent loginComponent = DaggerLoginComponent
                .builder()
                .loginModule(new LoginModule(this, EventBus.getDefault()))
                .build();

        return loginComponent.getPresenter();
    }

    @OnClick(R.id.email_sign_in_button)
    void attemptLogin() {
        presenter.authenticate(mEmailView.getText().toString(), mPasswordView.getText().toString());
    }

    @OnClick(R.id.email_register_button)
    void attemptRegister() {
        presenter.register(mEmailView.getText().toString(), mPasswordView.getText().toString());
    }

    @Override
    public void showLoginForm() {
        mLoginFormView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginForm() {
        mLoginFormView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate()
                .setDuration(shortAnimTime)
                .alpha(show ? 1 : 0)
                .setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void clearErrors() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void handleMailError() {
        mEmailView.setError(getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    @Override
    public void handlePasswordError() {
        mPasswordView.setError(getString(R.string.error_incorrect_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void startSynchronizeActivity() {
        startActivity(new Intent(this, SynchronizationActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

}

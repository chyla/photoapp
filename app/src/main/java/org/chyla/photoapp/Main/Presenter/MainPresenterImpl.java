package org.chyla.photoapp.Main.Presenter;

import android.util.Log;

import org.chyla.photoapp.Main.MainView;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Model.Authenticator.AuthenticatorImpl;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private final static String LOG_TAG = "MainPresenterImpl";

    Authenticator authenticator;
    InspectPhotosInteractor inspectPhotosInteractor;
    MainView view;

    public MainPresenterImpl(MainView view,
                             InspectPhotosInteractor inspectPhotosInteractor) {
        authenticator = new AuthenticatorImpl();
        this.inspectPhotosInteractor = inspectPhotosInteractor;
        this.view = view;
    }

    @Override
    public void logoutUser() {
        authenticator.logoutUser();
        view.startLoginActivity();
    }

    @Override
    public void inspectPhotos(final String tags) {
        final List<String> tagsList = splitTagsToList(tags);

        inspectPhotosInteractor.inspectPhotos(tagsList);
    }

    @Override
    public void showInspectedPhoto(final Photo photo) {
        view.showInspectedPhoto(photo);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowInspectedPhotosEvent(final ShowInspectedPhotosEvent event) {
        view.showInspectedPhotosGallery(event.getPhotos());
    }

    @Override
    public void showUserGallery() {

    }

    private List<String> splitTagsToList(final String tags) {
        Log.d(LOG_TAG, "Spliting tags...");

        List<String> tagsList = new LinkedList<>();

        for (final String tag : tags.split(",")) {
            tagsList.add(tag);
        }

        return tagsList;
    }

}

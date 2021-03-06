package org.chyla.photoapp.Main.Presenter;

import android.util.Log;

import org.chyla.photoapp.Main.MainView;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.LastPhotoInteractor;
import org.chyla.photoapp.Main.Model.NewPhotoInteractor;
import org.chyla.photoapp.Main.Model.UserGalleryInteractor;
import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private final static String LOG_TAG = "MainPresenterImpl";

    MainView view;
    Authenticator authenticator;
    LastPhotoInteractor lastPhotoInteractor;
    NewPhotoInteractor newPhotoInteractor;
    InspectPhotosInteractor inspectPhotosInteractor;
    UserGalleryInteractor userGalleryInteractor;

    public MainPresenterImpl(MainView view,
                             Authenticator authenticator,
                             LastPhotoInteractor lastPhotoInteractor,
                             NewPhotoInteractor newPhotoInteractor,
                             InspectPhotosInteractor inspectPhotosInteractor,
                             UserGalleryInteractor userGalleryInteractor) {
        this.view = view;
        this.authenticator = authenticator;
        this.lastPhotoInteractor = lastPhotoInteractor;
        this.newPhotoInteractor = newPhotoInteractor;
        this.inspectPhotosInteractor = inspectPhotosInteractor;
        this.userGalleryInteractor = userGalleryInteractor;

        newPhotoInteractor.setPresenter(this);
    }

    @Override
    public void logoutUser() {
        authenticator.logoutUser();
        view.startLoginActivity();
    }

    @Override
    public void showLastPhoto() {
        final Photo photo = lastPhotoInteractor.getLastPhoto();
        view.showPhoto(photo);
    }

    @Override
    public void addNewPhoto(String title, String description, String path) {
        newPhotoInteractor.addNewPhoto(title, description, path);
    }

    @Override
    public void showPhoto(final Photo photo) {
        view.showPhoto(photo);
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

        User user = authenticator.getLoggedUser();
        view.setUserMail(user.getEmail());
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
    public void addPhotoToGallery(Photo photo) {
        userGalleryInteractor.addPhotoToGallery(photo);
        showUserGallery();
    }

    @Override
    public void showUserGallery() {
        List<Photo> photos = userGalleryInteractor.getUserPhotos();

        view.showUserGallery(photos);
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

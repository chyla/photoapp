package org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail;

import android.util.Log;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.GetPhotosCallback;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.objects.FlickrPhoto;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.objects.FlickrResponse;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlickrGetPhotosCallback implements Callback<FlickrResponse> {

    private final static String LOG_TAG = "FlickrGetPhotosCallback";
    private final GetPhotosCallback callback;

    public FlickrGetPhotosCallback(GetPhotosCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
         if (response.isSuccessful()) {
             Log.i(LOG_TAG, "Received successful response.");

             final FlickrResponse flickrResponse = response.body();
             final List<FlickrPhoto> flickrPhotos = flickrResponse.getPhotos();

             List<Photo> appPhotos = new ArrayList<>(flickrPhotos.size());

             for (final FlickrPhoto flickrPhoto : flickrPhotos) {
                 try {
                     final Photo photo = new Photo(flickrPhoto.getTitle(),
                                                   flickrPhoto.getDescription(),
                                                   flickrPhoto.getUrl());
                     appPhotos.add(photo);
                 } catch (MalformedURLException e) {
                     Log.e(LOG_TAG, "Error while creating app photo URL...");
                     e.printStackTrace();
                 }
             }

             callback.getPhotosCallback(appPhotos);
         }
         else {
             Log.e(LOG_TAG, "Received error response:");
             Log.e(LOG_TAG, response.errorBody().toString());
         }
    }

    @Override
    public void onFailure(Call<FlickrResponse> call, Throwable t) {
        Log.e(LOG_TAG, "Failure:");
        t.printStackTrace();
    }

}

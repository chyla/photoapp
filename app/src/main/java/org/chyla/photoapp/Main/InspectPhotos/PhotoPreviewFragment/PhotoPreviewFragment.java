package org.chyla.photoapp.Main.InspectPhotos.PhotoPreviewFragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.chyla.photoapp.Main.InspectPhotos.PhotoPreviewFragment.detail.SwipeActionDetector;
import org.chyla.photoapp.Main.InspectPhotos.PhotoPreviewFragment.detail.SwipeActionListener;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.R;

public class PhotoPreviewFragment extends Fragment implements SwipeActionListener {

    private final static String LOG_TAG = "PhotoPreviewFragment";

    private Context mContext;
    private GestureDetectorCompat mDetector;
    private PhotoPreviewActionListener mPhotoActionListener;
    private ImageView imageView;
    private Photo photo;

    public void setPhotoActionListener(PhotoPreviewActionListener listener) {
        mPhotoActionListener = listener;
    }

    public void setPhoto(final Photo photo) {
        this.photo = photo;

        updatePhotoView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inspected_photo_preview, container, false);
        v.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (mDetector != null)
                    mDetector.onTouchEvent(event);

                return true;
            }
        });

        imageView = (ImageView) v.findViewById(R.id.imageView);
        updatePhotoView();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        createGestureDetectorCompat();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void createGestureDetectorCompat() {
        if (mContext != null) {
            mDetector = new GestureDetectorCompat(mContext.getApplicationContext(), new SwipeActionDetector(this));
        }
    }

    private void updatePhotoView() {
        if (imageView != null && photo != null) {
            Log.d(LOG_TAG, "Updating photo view...");
            Picasso.with(imageView.getContext()).load(photo.getUrl().toString()).into(imageView);
        }
    }

    @Override
    public void onSwipeUp() {
        Log.d(LOG_TAG, "onSwipeUp call");
        mPhotoActionListener.onPhotoSave(photo);
    }

    @Override
    public void onSwipeDown() {
        Log.d(LOG_TAG, "onSwipeDown call");
        mPhotoActionListener.onPhotoSave(photo);
    }

    @Override
    public void onSwipeLeft() {
        Log.d(LOG_TAG, "onSwipeLeft call");
        mPhotoActionListener.onPhotoDismiss();
    }

    @Override
    public void onSwipeRight() {
        Log.d(LOG_TAG, "onSwipeRight call");
        mPhotoActionListener.onPhotoDismiss();
    }

}

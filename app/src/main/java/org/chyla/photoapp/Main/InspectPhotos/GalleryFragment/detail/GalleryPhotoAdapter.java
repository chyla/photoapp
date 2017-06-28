package org.chyla.photoapp.Main.InspectPhotos.GalleryFragment.detail;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.R;

import java.util.List;

public class GalleryPhotoAdapter extends RecyclerView.Adapter<GalleryPhotoAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final static String LOG_TAG = "MyViewHolder";
        private final ImageButton imageButton;

        public MyViewHolder(final View view) {
            super(view);
            imageButton = (ImageButton) view.findViewById(R.id.image_button);
        }

        void setPhoto(final Photo photo) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d(LOG_TAG, "Photo clicked (URL: " + photo.getUrl().toString() + ")\"");
                    presenter.showInspectedPhoto(photo);
                }
            });

            String url = photo.getUrl().toString();
            Log.d(LOG_TAG, "Displaying photo: " + url);
            Picasso.with(imageButton.getContext()).load(url).into(imageButton);
        }
    }

    private final MainPresenter presenter;
    private final List<Photo> photos;

    public GalleryPhotoAdapter(final MainPresenter presenter, final List<Photo> photos) {
        this.presenter = presenter;
        this.photos = photos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_gallery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.setPhoto(photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}

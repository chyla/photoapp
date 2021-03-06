package org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.objects;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rsp", strict = false)
public class FlickrResponse {

    @ElementList(name = "photos")
    List<FlickrPhoto> photos;

    public List<FlickrPhoto> getPhotos() {
        return photos;
    }

}

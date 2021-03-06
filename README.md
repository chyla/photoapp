# PhotoApp

App to manage photos. Take a photo or find out one with Flickr and save them to view it later.

## Screenshots

![alt text](https://github.com/chyla/photoapp/raw/master/screenshots/PhotoApp-a1.png "Login screen")

Go to [screenshots](screenshots) directory to see more.

## Getting Started

1. Prepare settings for project in Google Firebase, Cloudinary and Flickr.
2. Place `google-services.json` file into `app/` directory.
3. Create `cloudinary.properties` and `flickr.properties` configuration files in `app/src/main/assets/` directory. You can see examples in that directory.

## Running the tests

To run tests from Android Studio please change the working directory value to `$MODULE_DIR$`. See [Robolectric - Building with Android Studio](http://robolectric.org/getting-started/) for more info.

## Deploy

Run this app with Android 7.1 (API 25). Older versions are not supported.

## Built With

* [greenDAO](http://greenrobot.org/greendao/)
* [Butter Knife](http://jakewharton.github.io/butterknife/)
* [EventBus](http://greenrobot.org/eventbus/)
* [Firebase](https://firebase.google.com/)
* [Retrofit](http://square.github.io/retrofit/)
* [Picasso](http://square.github.io/picasso/)
* [Cloudinary](http://cloudinary.com/)
* [Dagger](http://square.github.io/dagger/)
* [Robolectric](http://robolectric.org/)
* [Mockito](http://site.mockito.org/)
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)

## License

This project is licensed under the Apache License (v.2) - see the [LICENSE](LICENSE) file for details


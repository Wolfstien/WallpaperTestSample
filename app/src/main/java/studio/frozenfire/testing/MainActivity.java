package studio.frozenfire.testing;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    public PeriodicWorkRequest.Builder changeWallpaperBuilder;
    public PeriodicWorkRequest changeWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_person_black_24dp));
        Picasso.get()
                .load("https://picsum.photos/300/400/?random")
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);

        changeWallpaperBuilder = new PeriodicWorkRequest.Builder(WorkerTest.class, 16, TimeUnit.MINUTES);
//        try {
//            changeWallpaperBuilder.wait(1000);
//            changeWallpaper = changeWallpaperBuilder.build();
//            WorkManager.getInstance().cancelAllWork();
//            WorkManager.getInstance().enqueue(changeWallpaper);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        changeWallpaper = changeWallpaperBuilder.build();
        WorkManager.getInstance().cancelAllWork();
        WorkManager.getInstance().enqueue(changeWallpaper);
    }

    public void random(View view) {
        Picasso.get().invalidate("https://picsum.photos/300/400/?random");
        Picasso.get()
                .load("https://picsum.photos/300/400/?random")
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView);
    }

    public void apply(View view) {
        Picasso.get()
            .load("https://picsum.photos/300/400/?random")
            .into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        myWallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
    }


}

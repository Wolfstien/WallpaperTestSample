package studio.frozenfire.testing;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import androidx.work.Worker;

public class WorkerTest extends Worker {

    public Target mTarget;

    @NonNull
    @Override
    public Result doWork() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Picasso.get().invalidate("https://picsum.photos/300/400/?random");
                Picasso.get()
                    .load("https://picsum.photos/300/400/?random")
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                            try {
                                myWallpaperManager.setBitmap(bitmap);
                                Log.i("TEST", "success");
                            } catch (IOException e) {
                                Log.i("TEST", e.toString());
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.i("TEST", "bitmap fail: "+e.toString());
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    });
            }
        };
        mainHandler.post(myRunnable);
        return Result.SUCCESS;
    }
}

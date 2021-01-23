package com.example.curlanimation;



import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    CurlView curlBook;
    MediaPlayer pageFlipperAudio;
    MediaPlayer startsound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curlBook=findViewById(R.id.curlBook);

        pageFlipperAudio = MediaPlayer.create(getApplicationContext(),
                R.raw.sound);
        startsound = MediaPlayer.create(getApplicationContext(),
                R.raw.sound);

        int index = 0;


        curlBook.setPageProvider(new PageProvider());
        curlBook.setSizeChangedObserver(new SizeChangedObserver());
        curlBook.setSoundEffectsEnabled(true);
        curlBook.setCurrentIndex(index);
        curlBook.setBackgroundColor(Color.BLACK);
        curlBook.setAllowLastPageCurl(true);
    }

    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {

            if (w > h)
            {
                RelativeLayout landscape = (RelativeLayout)findViewById(R.id.rlCurl);
                landscape.setPadding(2,34,2,34);
                curlBook.setViewMode(CurlView.SHOW_TWO_PAGES);

            }

            else {

                curlBook.setViewMode(CurlView.SHOW_ONE_PAGE);

            }
        }
    }

    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
        private int[] mBitmapIds = {
                R.drawable.bucket,
                R.drawable.bucketlist,
                R.drawable.hair,
                R.drawable.k8,
                R.drawable.pic
        };

        @Override
        public int getPageCount() {
            return mBitmapIds.length;
        }

        @SuppressLint("NewApi")
        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);

            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            Drawable d = getResources().getDrawable(mBitmapIds[index]);

            int margin = 0;
            int border = 0;
            Rect r = new Rect(margin, margin, width - margin, height - margin);

            int imageWidth = r.width() - (border * 2);
            int imageHeight = imageWidth * d.getIntrinsicHeight()
                    / d.getIntrinsicWidth();
            if (imageHeight > r.height() - (border * 2)) {
                imageHeight = r.height() - (border * 2);
                imageWidth = imageHeight * d.getIntrinsicWidth()
                        / d.getIntrinsicHeight();
            }

            /*
             * r.left += ((r.width() - imageWidth) / 2) - border; r.right =
             * r.left + imageWidth + border + border; r.top += ((r.height() -
             * imageHeight) / 2) - border; r.bottom = r.top + imageHeight +
             * border + border;
             */

            Paint p = new Paint();
            p.setColor(0xFFC0C0C0);
            c.drawRect(r, p);
            r.left += border;
            r.right -= border;
            r.top += border;
            r.bottom -= border;

            d.setBounds(r);
            d.draw(c);
            /*mCurlView.playSoundEffect(R.raw.s1);*/
            //  b=codec(b, Bitmap.CompressFormat.JPEG, 70);

            return b;
        }


        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            Bitmap front = loadBitmap(width, height, index);
            page.setTexture(front, CurlPage.SIDE_BOTH);
            page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK);

            pageFlipperAudio.start();

            int ind = curlBook.getCurrentIndex();

            if (ind == 2){



            }



        }

    }


}

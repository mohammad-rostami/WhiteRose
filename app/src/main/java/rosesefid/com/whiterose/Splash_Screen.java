package rosesefid.com.whiterose;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


//**************************SPLASH SCREEN ACTIVITY (SCREEN THAT APPEARS AT BEGINING OF RUN)

public class Splash_Screen extends AppCompatActivity {
    public static ArrayList BANNERS = new ArrayList();
    public static ArrayList TARGETS = new ArrayList();
    public static JSONObject OBJECTS;
    public static String NEW_UPDATE = "no";
    public static String s;
    public static JSONObject routs;
    public static JSONArray cat_array;
    public static ArrayList SORT_NAMES = new ArrayList();
    public static ArrayList SORT_TARGETS = new ArrayList();
    public static JSONArray sort_final_array;
    public static Bitmap myBitmap;
    private String versionCode;
    public static String target;
    private JSONObject object;
    private ProgressDialog progress;
    private TextView ver_late;
    private View mLinearLayout_Root;
    private Animation topBottom;
    private Animation animEnd;
    private View mImageView_logo;
    private Intent intent;
    private ActivityOptionsCompat options;
    private String mStr_flightChartData;
    private ObjectAnimator animation;
    private ProgressBar progressBar;
    private LinearLayout progressBarLayout;
    private View mImageView_earth;
    private View mImageView_plane;
    private View mTextView_brand;
    private View mImageView_cover;

    @Override
    protected void onPause() {
        super.onPause();
//        G.activityPaused();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        G.activityResumed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new mAsync_flightChart().execute("http://ikia.airport.ir/schedule");

//        progressBarLayout = (LinearLayout) findViewById(R.id.progressBarLayout);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
////        animation.setDuration(); //in milliseconds
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.start();


        mLinearLayout_Root = findViewById(R.id.activity_splash_ll_root);
        mImageView_cover = findViewById(R.id.splash_cover);
        mImageView_earth = findViewById(R.id.splash_earth);
        mImageView_plane = findViewById(R.id.splash_plane);
        mTextView_brand = findViewById(R.id.splash_text);
        topBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
        animEnd = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim_end);
        mImageView_plane.startAnimation(topBottom);
        intent = new Intent(Splash_Screen.this, Activity_Main.class);
//        topBottom.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Toast.makeText(Splash_Screen.this, "end", Toast.LENGTH_SHORT).show();
////                mLinearLayout_Root.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        animEnd.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startActivity(intent, options.toBundle());
//                mImageView_logo.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        mImageView_logo.startAnimation(topBottom);

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                mImageView_logo.startAnimation(animEnd);
//
//                Intent intent = new Intent(Splash_Screen.this, Activity_Main.class);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Pair<View, String> pair1 = Pair.create(mLinearLayout_Root, mLinearLayout_Root.getTransitionName());
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Splash_Screen.this, pair1);
//                    startActivity(intent, options.toBundle());
//                } else {
//                    startActivity(intent);
//                }
//                finish();
//            }
//        }, 5000);

//        //SHOWING LOADING
//        progress = new ProgressDialog(Splash_Screen.this, R.style.MyTheme);
//        progress.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_gray));
//        progress.setCancelable(false);
//        progress.getWindow().setGravity(Gravity.BOTTOM);
//        progress.show();
//
//        if (G.isInternetAvailable() == true) {
//            // Get app version
//            String versionName = null;
//            versionCode = null;
//            try {
//                versionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
////            TextView ver = (TextView) findViewById(R.id.ver);
////            TextView ver_code = (TextView) findViewById(R.id.ver_code);
////            ver_code.setText(versionCode);
////            ver_late = (TextView) findViewById(R.id.ver_late);
////            ver_late.setVisibility(View.INVISIBLE);
//////     Execute WebService
////            Webservice.PostData asyncTask = new Webservice.PostData();
////            asyncTask.postDelegate = this;
////            asyncTask.execute("http://www.20decor.com/index.php?route=information/api");
//        } else {
////            G.isSplash = true;
////            Intent intent1 = new Intent(Splash_Screen.this, InternetConnection.class);
////            startActivity(intent1);
//        }
    }

    private class mAsync_flightChart extends Webservice.GetClass {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            G.STR_FLIGHT_CHART_DATA = s;
            new mAsync_PackageData().execute("http://rose.travel/api/v1/packagehotels/packagelist");
        }
    }

    private class mAsync_PackageData extends Webservice.GetClass {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            G.STR_PACKAGES = result;
            Log.d("state", "animation ended");
            final Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_splash);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mTextView_brand.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    Intent intent = new Intent(Splash_Screen.this, Activity_Main.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().getSharedElementEnterTransition().setDuration(100);
                        getWindow().getSharedElementReturnTransition().setDuration(100)
                                .setInterpolator(new DecelerateInterpolator());
                        Pair<View, String> pair1 = Pair.create(mLinearLayout_Root, mLinearLayout_Root.getTransitionName());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(Splash_Screen.this, pair1);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            final Animation getOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_splash);
            getOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
//                    progressBarLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    progressBar.setVisibility(View.GONE);
                    mImageView_plane.setVisibility(View.GONE);
                    mImageView_cover.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
//            progressBar.startAnimation(getOut);
            mImageView_cover.startAnimation(getOut);
            mTextView_brand.startAnimation(fadeIn);


        }
    }

}

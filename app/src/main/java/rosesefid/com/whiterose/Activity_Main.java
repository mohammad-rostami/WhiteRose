package rosesefid.com.whiterose;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import io.karim.MaterialTabs;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Main extends AppCompatActivity implements Fragment_Data.OnFragmentInteractionListener,
        Fragment_Insurance.OnFragmentInteractionListener, Fragment_Reserve.OnFragmentInteractionListener,
        Fragment_Know.OnFragmentInteractionListener, Fragment_Profile.OnFragmentInteractionListener,
        Fragment_Reserve_Flight.OnFragmentInteractionListener, Fragment_Reserve_Hotel.OnFragmentInteractionListener,
        Fragment_Reserve_Tour.OnFragmentInteractionListener, Fragment_Incoming.OnFragmentInteractionListener,
        Fragment_Weather.OnFragmentInteractionListener, Fragment_Time.OnFragmentInteractionListener,
        Fragment_Outgoing.OnFragmentInteractionListener, Fragment_WorldInfo.OnFragmentInteractionListener, Fragment_Currency.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    static DatePickerDialog sDatePickerDialog;
    static Context sContext;
    private static int currentPage;
    private CoordinatorLayout mCoordinator_Root;
    private DrawerLayout mNavigationDrawer;
    private String[] mStr_TabNames;
    private SharedPreferences.Editor mSharedPreferenceEdit_WorldClock;
    private SharedPreferences.Editor mSharedPreferenceEdit_Filler;
    private ImageView mImageView_Bagage;
    private ImageView mImageView_Insurance;
    private ImageView mImageView_Plane;
    private ImageView mImageView_Weather;
    private ViewPager.SimpleOnPageChangeListener mPageChangeListener;
    private ImageView mImageView_Dollar;
    private LinearLayout mLinearLayout_ToolbarIcon;
    private LinearLayout mLinearLayout_BottomNav;
    private RippleBackground rippleBackground;
    private LinearLayout mLinearLayout_Nav;
    private LinearLayout mLinearLayout_BottomNavBack;
    private LinearLayout mLinearLayout_Background;
    private LinearLayout mLinearLayout_LinearLayoutBack;
    private MaterialTabs tabLayout;
    private LinearLayout hiddenPanel;
    private RelativeLayout toolbar_main_search;
    private Animation bottomUp;
    private Animation bottomDown;
    private Animation fadeIn;
    private Animation fadeOut;
    private Animation fadeInButton;
    private Animation fadeOutButton;
    private TextView pageName;
    private ImageView toolbar_main_btn_search1;
    private ImageView toolbar_main_btn_search2;
    private ImageView toolbar_main_world_info;
    private static LinearLayout toolbar_search1;
    private static LinearLayout toolbar_search2;
    private ImageView mImageView_Profile1;
    private ImageView mImageView_Profile2;
    private ImageView mImageView_Profile3;
    private ImageView mImageView_Profile4;
    private Animation fading;
    private LinearLayout mLinearLayout_Profile;
    private LinearLayout mLinearLayout_Bagage;
    private LinearLayout mLinearLayout_Insurance;
    private LinearLayout mLinearLayout_Plane;
    private LinearLayout mLinearLayout_Weather;
    private String mStrChartData;

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (toolbar_main_search.getVisibility() == View.VISIBLE) {
            hiddenPanel.startAnimation(bottomDown);
            toolbar_main_search.startAnimation(fadeOut);
            toolbar_main_world_info.startAnimation(fadeInButton);
            pageName.startAnimation(fadeInButton);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_main);
        sContext = getApplicationContext();

//        // circular reveal method for opening activity
//        mCoordinator_Root = (CoordinatorLayout) findViewById(R.id.activity_main_cl_root);
//        if (savedInstanceState == null) {
//            mCoordinator_Root.setVisibility(View.INVISIBLE);
//            ViewTreeObserver viewTreeObserver = mCoordinator_Root.getViewTreeObserver();
//            if (viewTreeObserver.isAlive()) {
//                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        circularRevealActivity();
//                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                            mCoordinator_Root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        } else {
//                            mCoordinator_Root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }
//                    }
//                });
//            }
//        }

        mLinearLayout_Profile = (LinearLayout) findViewById(R.id.toolbar_icons_ll_profile);
        mLinearLayout_Bagage = (LinearLayout) findViewById(R.id.toolbar_icons_ll_bagage);
        mLinearLayout_Insurance = (LinearLayout) findViewById(R.id.toolbar_icons_ll_insurance);
        mLinearLayout_Plane = (LinearLayout) findViewById(R.id.toolbar_icons_ll_plane);
        mLinearLayout_Weather = (LinearLayout) findViewById(R.id.toolbar_icons_ll_weather);

        //set actionbar and status bar height and visibility
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        int mInt_statusBarHeight = getStatusBarHeight();
        pageName = (TextView) findViewById(R.id.toolbar_main_txt_header);
        pageName.setText("اپلیکیشن گردشگری");
        RelativeLayout toolbar_main_root = (RelativeLayout) findViewById(R.id.toolbar_main_tb_main);
        toolbar_main_search = (RelativeLayout) findViewById(R.id.toolbar_main_tb_search);
        LinearLayout toolbar_main = (LinearLayout) findViewById(R.id.toolbar_main_tb_main_root);
        LinearLayout toolbar_main_content = (LinearLayout) findViewById(R.id.toolbar_main_ll_content);
        LinearLayout toolbar_main_content_left = (LinearLayout) findViewById(R.id.toolbar_main_ll_content_left);
        toolbar_main_btn_search1 = (ImageView) findViewById(R.id.toolbar_main_btn_search1);
        toolbar_main_btn_search2 = (ImageView) findViewById(R.id.toolbar_main_btn_search2);
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float mFloat_toolbarBarHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 62, mDisplayMetrics);
        // Gets the layout params that will allow you to resize the layout
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toolbar_main_root.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = (int) (mInt_statusBarHeight + mFloat_toolbarBarHeight);
        toolbar_main_content.setPadding(0, mInt_statusBarHeight, 0, 0);
        toolbar_main_content_left.setPadding(0, mInt_statusBarHeight, 0, 0);
        toolbar_main_root.setLayoutParams(params);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.hidden_panel, new Fragment_WorldInfo()).commit();

        //calling shared preferences
        mSharedPreferenceEdit_WorldClock = G.SHARED_PREFERENCE_WORLD_CLOCK.edit();
        mSharedPreferenceEdit_Filler = G.SHARED_PREFERENCE_FILLER.edit();
        boolean x = G.SHARED_PREFERENCE_FILLER.getBoolean("XX", false);
        if (!x) {
            Set<String> set = new HashSet<String>();
            G.sArray_items.clear();
            set.addAll(G.sArray_items);
            mSharedPreferenceEdit_WorldClock.putStringSet("Favorite", set);
            mSharedPreferenceEdit_WorldClock.commit();
            mSharedPreferenceEdit_Filler.putBoolean("XX", true);
            mSharedPreferenceEdit_Filler.commit();
        }
        Set<String> set = G.SHARED_PREFERENCE_WORLD_CLOCK.getStringSet("Favorite", null);
        G.sArray_items = new ArrayList<String>(set);
        PersianCalendar persianCalendar = new PersianCalendar();
        sDatePickerDialog = DatePickerDialog.newInstance(
                Activity_Main.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );

        // Create the mRecyclerAdapter_Adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections mRecyclerAdapter_Adapter.
        final CustomViewPager mViewPager = (CustomViewPager) findViewById(R.id.activity_main_vp_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(5);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setPageMargin(50);

        // Tabs
        tabLayout = (MaterialTabs) findViewById(R.id.activity_main_toolbar_tab);
        tabLayout.setViewPager(mViewPager);
        tabLayout.setTypefaceSelected(G.TYPE_FACE_SANS);
        tabLayout.setTypefaceUnselected(G.TYPE_FACE_SANS);
        // Tab icons and texts
        mImageView_Bagage = (ImageView) findViewById(R.id.toolbar_icons_img_bagage);
        mImageView_Insurance = (ImageView) findViewById(R.id.toolbar_icons_img_insurance);
        mImageView_Dollar = (ImageView) findViewById(R.id.toolbar_icons_img_dollar);
        mImageView_Plane = (ImageView) findViewById(R.id.toolbar_icons_img_plane);
        mImageView_Weather = (ImageView) findViewById(R.id.toolbar_icons_img_weather);
        mImageView_Profile1 = (ImageView) findViewById(R.id.toolbar_icons_img_profile1);
        mImageView_Profile2 = (ImageView) findViewById(R.id.toolbar_icons_img_profile2);
        mImageView_Profile3 = (ImageView) findViewById(R.id.toolbar_icons_img_profile3);
        mImageView_Profile4 = (ImageView) findViewById(R.id.toolbar_icons_img_profile4);
        mLinearLayout_BottomNav = (LinearLayout) findViewById(R.id.activity_main_ll_bottom_navigation);
        mLinearLayout_BottomNavBack = (LinearLayout) findViewById(R.id.activity_main_ll_bottom_navigation_back);
        mLinearLayout_Nav = (LinearLayout) findViewById(R.id.activity_main_ll_navigation);
        final TextView mTextView_navigation_Reserve = (TextView) findViewById(R.id.toolbar_icons_txt_reserve);
        final TextView mTextView_navigation_Insurance = (TextView) findViewById(R.id.toolbar_icons_txt_insurance);
        final TextView mTextView_navigation_know = (TextView) findViewById(R.id.toolbar_icons_txt_know);
        final TextView mTextView_navigation_Profile = (TextView) findViewById(R.id.toolbar_icons_txt_profile);
        final TextView mTextView_navigation_Packages = (TextView) findViewById(R.id.toolbar_icons_txt_packages);
        //background circle color (used for changing toolbar color)
        mLinearLayout_BottomNavBack.setBackgroundColor(getResources().getColor(R.color.first_color));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        // if know tab selected
                        // calling below method to set icon and text colors
                        navigationIconColorChanger(mImageView_Profile1, mImageView_Weather, mImageView_Plane, mImageView_Insurance, mImageView_Bagage,
                                mTextView_navigation_Profile, mTextView_navigation_know, mTextView_navigation_Reserve, mTextView_navigation_Insurance, mTextView_navigation_Packages);
                        // setting animation for tab icon
                        Animation profile = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                        final Animation profile_getOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.get_out);
                        final Animation profile_getIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.get_in);

                        profile.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mImageView_Profile2.setVisibility(View.GONE);
                                mImageView_Profile4.setVisibility(View.VISIBLE);
                                mImageView_Profile4.startAnimation(profile_getOut);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        profile_getOut.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mImageView_Profile2.setVisibility(View.VISIBLE);
                                mImageView_Profile4.setVisibility(View.GONE);
                                mImageView_Profile2.startAnimation(profile_getIn);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        profile_getIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        mImageView_Profile2.startAnimation(profile);
                        // calling toolbar color change method
                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim0);
                        navigationBackgroundAnimation(R.color.colorPrimary, mLinearLayout_Nav, mLinearLayout_BottomNavBack);

                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }

                        Fragment_Profile.profileTopBannerAnimation();
                        break;
                    case 1:
                        // if know tab selected
                        // calling below method to set icon and text colors
                        navigationIconColorChanger(mImageView_Weather, mImageView_Plane, mImageView_Insurance, mImageView_Bagage, mImageView_Profile1,
                                mTextView_navigation_know, mTextView_navigation_Reserve, mTextView_navigation_Insurance, mTextView_navigation_Packages, mTextView_navigation_Profile);
                        // setting animation for tab icon
                        mImageView_Weather.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn));
                        // calling toolbar color change method
                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim1);
                        navigationBackgroundAnimation(R.color.forth_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);

                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                        Fragment_Profile.profileTopBannerAnimationClose();
                        Fragment_Incoming.filterBarAnimation();

                        break;
                    case 2:
                        // if reserve tab selected
                        // calling below method to set icon and text colors
                        navigationIconColorChanger(mImageView_Plane, mImageView_Insurance, mImageView_Bagage, mImageView_Weather, mImageView_Profile1,
                                mTextView_navigation_Reserve, mTextView_navigation_know, mTextView_navigation_Insurance, mTextView_navigation_Packages, mTextView_navigation_Profile);
                        // setting animation for tab icon
                        Animation s = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly);
                        s.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mImageView_Plane.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_in));
                            }
                        });
                        mImageView_Plane.startAnimation(s);
                        // calling toolbar color change method
                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim2);
                        navigationBackgroundAnimation(R.color.third_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                        Fragment_Profile.profileTopBannerAnimationClose();

                        break;
                    case 3:
                        // if insurance tab selected
                        // calling below method to set icon and text colors
                        navigationIconColorChanger(mImageView_Insurance, mImageView_Bagage, mImageView_Plane, mImageView_Weather, mImageView_Profile1,
                                mTextView_navigation_Insurance, mTextView_navigation_know, mTextView_navigation_Reserve, mTextView_navigation_Packages, mTextView_navigation_Profile);
                        // setting animation for tab icon(two part animation)
                        final Animation s1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drive_out);
                        final Animation s2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drive_in);
                        s2.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                                mImageView_Insurance.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in));
                                mImageView_Dollar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mImageView_Dollar.startAnimation(s1);
                                mImageView_Insurance.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out));
                            }
                        });
                        s1.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mImageView_Dollar.setVisibility(View.GONE);
                            }
                        });
                        mImageView_Dollar.startAnimation(s2);
                        // calling toolbar color change method
                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim3);
                        navigationBackgroundAnimation(R.color.second_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                        Fragment_Profile.profileTopBannerAnimationClose();
                        break;
                    case 4:
                        // if package tab selected
                        // calling below method to set icon and text colors
                        navigationIconColorChanger(mImageView_Bagage, mImageView_Insurance, mImageView_Plane, mImageView_Weather, mImageView_Profile1,
                                mTextView_navigation_Packages, mTextView_navigation_know, mTextView_navigation_Reserve, mTextView_navigation_Insurance, mTextView_navigation_Profile);
                        // setting animation for tab icon(two part animation)
                        mImageView_Bagage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrate));
                        // calling toolbar color change method
                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim4);
                        navigationBackgroundAnimation(R.color.first_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                        Fragment_Profile.profileTopBannerAnimationClose();
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //defining search bar view (seperated for each tab)
                toolbar_search1 = (LinearLayout) findViewById(R.id.toolbar_main_layout_search1);
                toolbar_search2 = (LinearLayout) findViewById(R.id.toolbar_main_layout_search2);
                toolbar_search1.setVisibility(View.GONE);
                toolbar_search2.setVisibility(View.VISIBLE);

                //weather section button on toolbar
                hiddenPanel = (LinearLayout) findViewById(R.id.hidden_panel);
                bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
                bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
                toolbar_main_world_info = (ImageView) findViewById(R.id.toolbar_main_btn_weather);
                fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
                fadeInButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                fadeOutButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        toolbar_main_search.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fadeInButton.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        toolbar_main_world_info.setVisibility(View.VISIBLE);
                        pageName.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fadeOutButton.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        toolbar_main_world_info.setVisibility(View.GONE);
                        pageName.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                bottomDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        hiddenPanel.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                toolbar_main_world_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Intent intent = new Intent(Activity_Main.this, Activity_WorldInfo.class);
//                Activity_Main.this.startActivity(intent);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);

                        } else {
                            hiddenPanel.startAnimation(bottomUp);
                            hiddenPanel.setVisibility(View.VISIBLE);
                            toolbar_main_search.setVisibility(View.VISIBLE);
                            toolbar_main_search.startAnimation(fadeIn);
                            toolbar_main_world_info.startAnimation(fadeOutButton);
                            pageName.startAnimation(fadeOutButton);
                        }
                    }
                });

                toolbar_main_btn_search1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Intent intent = new Intent(Activity_Main.this, Activity_WorldInfo.class);
//                Activity_Main.this.startActivity(intent);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);

                        } else {
                            hiddenPanel.startAnimation(bottomUp);
                            hiddenPanel.setVisibility(View.VISIBLE);
                            toolbar_main_search.setVisibility(View.VISIBLE);
                            toolbar_main_search.startAnimation(fadeIn);
                            toolbar_main_world_info.startAnimation(fadeOutButton);
                            pageName.startAnimation(fadeOutButton);
                        }
                    }
                });
                toolbar_main_btn_search2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Intent intent = new Intent(Activity_Main.this, Activity_WorldInfo.class);
//                Activity_Main.this.startActivity(intent);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);

                        } else {
                            hiddenPanel.startAnimation(bottomUp);
                            hiddenPanel.setVisibility(View.VISIBLE);
                            toolbar_main_search.setVisibility(View.VISIBLE);
                            toolbar_main_search.startAnimation(fadeIn);
                            toolbar_main_world_info.startAnimation(fadeOutButton);
                            pageName.startAnimation(fadeOutButton);
                        }
                    }
                });

                // setting page according to tab bar selected item
                mLinearLayout_Bagage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(4, true);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                    }
                });
                mLinearLayout_Insurance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(3, true);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                    }
                });
                mLinearLayout_Plane.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(2, true);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                    }
                });
                mLinearLayout_Weather.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(1, true);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                    }
                });
                mLinearLayout_Profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(0, true);
                        if (hiddenPanel.getVisibility() == View.VISIBLE) {
                            hiddenPanel.startAnimation(bottomDown);
                            toolbar_main_search.startAnimation(fadeOut);
                            toolbar_main_world_info.startAnimation(fadeInButton);
                            pageName.startAnimation(fadeInButton);
                        }
                    }
                });

            }
        });
    }

    // circular reveal animation method
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity() {

        int cx = mCoordinator_Root.getWidth() / 2;
        int cy = mCoordinator_Root.getHeight() / 2;
        float finalRadius = Math.max(mCoordinator_Root.getWidth(), mCoordinator_Root.getHeight());
        // create the animator for this view (the start radius is zero)
        Animator mAnimation_CircularReveal = ViewAnimationUtils.createCircularReveal(mCoordinator_Root, cx, cy, 0, finalRadius);
        mAnimation_CircularReveal.setDuration(500);
        // make the view visible and start the animation
        mCoordinator_Root.setVisibility(View.VISIBLE);
        mAnimation_CircularReveal.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up STR_BUTTON_NO, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    // date picker method (gets date from picker dialog and sets it)
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

        switch (G.mStr_DateFieldPointer) {
            case "insurance":
                Fragment_Insurance.TEXTVIEW_DATE.setText(date);
                Fragment_Insurance.STR_SELECTED_DATE = date;
                break;
//            case "FlightStartDate":
//                mEditText_FlightStartDate.setText(date);
//                break;
//            case "FlightEndDate":
//                mEditText_FlightEndDate.setText(date);
//                break;
//            case "HotelStartDate":
//                mEditText_HotelStartDate.setText(date);
//                break;
//            case "HotelEndDate":
//                mEditText_HotelEndDate.setText(date);
//                break;
//            case "TourStartDate":
//                mEditText_TourStartDate.setText(date);
//                break;
//            case "TourEndDate":
//                mEditText_TourEndDate.setText(date);
//                break;
        }
    }

    public void datePicker() {
        sDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");

    }


    //A placeholder fragment containing a simple view.

    // tab bar icon and text change method
    public void navigationIconColorChanger(ImageView first, ImageView Second, ImageView Third,
                                           ImageView Forth, ImageView Fifth, TextView numberOne, TextView numberTwo,
                                           TextView numberThree, TextView numberFour, TextView numberFive) {

        first.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        Second.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        Third.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        Forth.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        Fifth.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        first.setAlpha(1.0f);
        Second.setAlpha(0.5f);
        Third.setAlpha(0.5f);
        Forth.setAlpha(0.5f);
        Fifth.setAlpha(0.5f);
        numberOne.setAlpha(1.0f);
        numberTwo.setAlpha(0.5f);
        numberThree.setAlpha(0.5f);
        numberFour.setAlpha(0.5f);
        numberFive.setAlpha(0.5f);
    }

    //A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.

    // toolbar background animation method
    public void navigationBackgroundAnimation(final Integer colorCode, View view, final View background) {
        tabLayout.setRippleHighlightColor(colorCode);
        Animation color_change = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.back_color_change);
        color_change.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                background.setBackgroundColor(getResources().getColor(colorCode));
            }
        });
        view.startAnimation(color_change);
    }

    public static class PlaceholderFragment extends Fragment {

        //The fragment argument representing the section number for this fragment.

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        //Returns a new instance of this fragment for the given section number.

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        //sets tab names
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mStr_TabNames = new String[]{"پروفایا", "بدانید", "رزرو", "بیمه", "پکیج ها"};
        }

        // sets tab content (fragment)
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new Fragment_Profile();
                case 1:

                    return new Fragment_Know();
                case 2:

                    return new Fragment_Reserve();
                case 3:

                    return new Fragment_Insurance();
                case 4:

                    return new Fragment_Data();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mStr_TabNames[position];
        }
    }

    public static void worldInfoTabSelection(int position) {
        switch (position) {
            case 1:
                // if Time Tab
                toolbar_search1.setVisibility(View.GONE);
                toolbar_search2.setVisibility(View.VISIBLE);
                break;
            case 2:
                // if Weather Tab
                toolbar_search1.setVisibility(View.VISIBLE);
                toolbar_search2.setVisibility(View.GONE);

                break;
        }
    }

    private void shower(final LinearLayout view) {

    }

    private void showerCaller() {
        if (mLinearLayout_Insurance.getVisibility() == View.INVISIBLE) {
            shower(mLinearLayout_Insurance);
        } else if (mLinearLayout_Plane.getVisibility() == View.INVISIBLE) {
            shower(mLinearLayout_Plane);
        } else if (mLinearLayout_Weather.getVisibility() == View.INVISIBLE) {
            shower(mLinearLayout_Weather);
        } else if (mLinearLayout_Profile.getVisibility() == View.INVISIBLE) {
            shower(mLinearLayout_Profile);
        }

    }

}

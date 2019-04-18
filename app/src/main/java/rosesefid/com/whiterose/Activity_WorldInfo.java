package rosesefid.com.whiterose;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import io.karim.MaterialTabs;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_WorldInfo extends AppCompatActivity implements Fragment_Weather.OnFragmentInteractionListener, Fragment_Time.OnFragmentInteractionListener {

    static DatePickerDialog sDatePickerDialog;
    static Context sContext;

    private CoordinatorLayout mCoordinator_Root;
    private DrawerLayout mNavigationDrawer;
    private String[] mStr_TabNames;
    private ImageView mImageView_Bagage;
    private ImageView mImageView_Weather;
    private LinearLayout mLinearLayout_Nav;
    private LinearLayout mLinearLayout_BottomNavBack;
    private static LinearLayout toolbar_search;
    private static LinearLayout toolbar_search2;

    // setting default font for all texts
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_world_info);
        sContext = getApplicationContext();

        // calling circullar reveal animation method
        mCoordinator_Root = (CoordinatorLayout) findViewById(R.id.activity_flights_cl_root);
        if (savedInstanceState == null) {
            mCoordinator_Root.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = mCoordinator_Root.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mCoordinator_Root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mCoordinator_Root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }

        // page tab bar
        mLinearLayout_Nav = (LinearLayout) findViewById(R.id.activity_main_ll_navigation);
        mLinearLayout_BottomNavBack = (LinearLayout) findViewById(R.id.activity_main_ll_bottom_navigation_back);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the mRecyclerAdapter_Adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections mRecyclerAdapter_Adapter.
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_flights_vp_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(1);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setPageMargin(180);

        //defining search bar view (seperated for each tab)
//        toolbar_search = (LinearLayout) findViewById(R.id.toolbar_main_layout_search);
        toolbar_search2 = (LinearLayout) findViewById(R.id.toolbar_main_layout_search2);
        toolbar_search.setVisibility(View.GONE);
        toolbar_search2.setVisibility(View.VISIBLE);
        //defining tab layout views and setting icons
        LinearLayout mLinearLayout_Bagage = (LinearLayout) findViewById(R.id.toolbar_icons_ll_bagage);
        LinearLayout mLinearLayout_Insurance = (LinearLayout) findViewById(R.id.toolbar_icons_ll_insurance);
        LinearLayout mLinearLayout_Plane = (LinearLayout) findViewById(R.id.toolbar_icons_ll_plane);
        LinearLayout mLinearLayout_Weather = (LinearLayout) findViewById(R.id.toolbar_icons_ll_weather);
        final TextView mTextView_navigation_know = (TextView) findViewById(R.id.toolbar_icons_txt_know);
        final TextView mTextView_navigation_Packages = (TextView) findViewById(R.id.toolbar_icons_txt_packages);
        mLinearLayout_Insurance.setVisibility(View.GONE);
        mLinearLayout_Plane.setVisibility(View.GONE);
        mImageView_Bagage = (ImageView) findViewById(R.id.toolbar_icons_img_bagage);
        mImageView_Weather = (ImageView) findViewById(R.id.toolbar_icons_img_weather);
        mImageView_Bagage.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        mImageView_Weather.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorfilter));
        mImageView_Bagage.setImageResource(R.drawable.ic_time);
        mImageView_Weather.setImageResource(R.drawable.ic_weather);
        mTextView_navigation_know.setText("آب و هوا");
        mTextView_navigation_Packages.setText("ساعت جهانی");

        //setting listener for view pager changes
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("mohammad", "page selected " + position);
                switch (position) {
                    case 0:
                        // if Time Tab
                        toolbar_search.setVisibility(View.VISIBLE);
                        toolbar_search2.setVisibility(View.GONE);
                        mImageView_Weather.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn));
                        iconTabChanger(mImageView_Weather, mImageView_Bagage, mTextView_navigation_know, mTextView_navigation_Packages);
                        // calling toolbar color changer
//                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim2);
//                        navigationBackgroundAnimation(R.color.third_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);
                        break;
                    case 1:
                        // if Weather Tab
                        toolbar_search.setVisibility(View.GONE);
                        toolbar_search2.setVisibility(View.VISIBLE);
                        mImageView_Bagage.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.turn));
                        iconTabChanger(mImageView_Bagage, mImageView_Weather, mTextView_navigation_Packages, mTextView_navigation_know);
                        // calling toolbar color changer
//                        mLinearLayout_Nav.setBackgroundResource(R.drawable.back_color_anim1);
//                        navigationBackgroundAnimation(R.color.forth_color, mLinearLayout_Nav, mLinearLayout_BottomNavBack);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLinearLayout_Bagage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        mLinearLayout_Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        // Tabs
        MaterialTabs tabLayout = (MaterialTabs) findViewById(R.id.activity_flights_toolbar_tab);
        tabLayout.setViewPager(mViewPager);
        tabLayout.setTypefaceSelected(G.TYPE_FACE_SANS);
        tabLayout.setTypefaceUnselected(G.TYPE_FACE_SANS);
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

    // changing tab icon and text color
    public void iconTabChanger(ImageView first, ImageView Second, TextView first_text, TextView Second_text) {
        first.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.color_white));
        Second.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorfilter));
        first.setAlpha(1.0f);
        Second.setAlpha(0.5f);
        first_text.setAlpha(1.0f);
        Second_text.setAlpha(0.5f);
    }

    // toolbar color changer method
    public void navigationBackgroundAnimation(final Integer colorCode, View view, final View background) {
//        tabLayout.setRippleHighlightColor(colorCode);
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

    //A placeholder fragment containing a simple view.
    public static class PlaceholderFragment extends Fragment {
        //The fragment argument representing the section number for this
        //fragment.
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        //Returns a new instance of this fragment for the given section
        //number.
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_data, container, false);
            return view;
        }
    }

    //A {@link FragmentPagerAdapter} that returns a fragment corresponding to
    //one of the sections/tabs/pages.
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // setting tab names
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mStr_TabNames = new String[]{"آب و هوا", "ساعت جهانی"};
        }

        // setting tab contents(fragment)
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Weather();
                case 1:
                    return new Fragment_Time();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mStr_TabNames[position];
        }
    }



}

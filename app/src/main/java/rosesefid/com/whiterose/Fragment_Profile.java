package rosesefid.com.whiterose;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;


//************************************* NAVIGATION MENU FRAGMENT

public class Fragment_Profile extends Fragment {
    public static String name;
    private Boolean hasChild;
    private static RelativeLayout mNavigationBanner;
    private static Animation bottomUp;
    private static Animation bottomDown;
    private static FloatingActionButton Fab;
    private Animation grow;


    public Fragment_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mNavigationBanner = (RelativeLayout) view.findViewById(R.id.fragment_navigation_img_banner);
        Fab = (FloatingActionButton) view.findViewById(R.id.fragment_profile_fab);

        bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.profile_header_anim);
        grow = AnimationUtils.loadAnimation(getContext(), R.anim.grow);

        bottomUp.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Fab.startAnimation(grow);
            }
        });
        grow.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                Fab.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
        bottomDown = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_down);
        return view;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public static void profileTopBannerAnimation() {
        mNavigationBanner.setVisibility(View.VISIBLE);
        mNavigationBanner.startAnimation(bottomUp);
    }

    public static void profileTopBannerAnimationClose() {
        mNavigationBanner.setVisibility(View.VISIBLE);
        mNavigationBanner.startAnimation(bottomDown);
        Fab.setVisibility(View.GONE);
    }
}
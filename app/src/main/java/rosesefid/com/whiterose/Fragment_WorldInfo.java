package rosesefid.com.whiterose;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Fragment_WorldInfo extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int button = 0;
    private ValueAnimator mValueAnimator_ValueAnimator;
    private OnFragmentInteractionListener mListener;
    private LinearLayout mLinearLayout_Flight;
    private LinearLayout mLinearLayout_Hotel;
    private LinearLayout mLinearLayout_Tour;
    private LinearLayout mLinearLayout_Root;
    private FragmentTabHost mTabHost;

    public Fragment_WorldInfo() {
        // Required empty public constructor
    }

    public static Fragment_WorldInfo newInstance(String param1, String param2) {
        Fragment_WorldInfo fragment = new Fragment_WorldInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_world_info);

        View tabIndicatorToday = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("aaaa");
        View tabIndicatorLive = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("bbbb");

        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("ساعت جهانی");
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("آب و هوا");

//        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.today)).setIndicator(tabIndicatorToday), EntriesTodayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabIndicatorLive), Fragment_Weather.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabIndicatorToday), Fragment_Time.class, null);
        mTabHost.setCurrentTab(1);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "tab2":
                        // if Time Tab
                        Log.d("tab name", "tab 2");
                        Activity_Main.worldInfoTabSelection(2);
                        break;
                    case "tab1":
                        // if Weather Tab
                        Log.d("tab name", "tab 1");
                        Activity_Main.worldInfoTabSelection(1);

//                        toolbar_search.setVisibility(View.GONE);
//                        toolbar_search2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        return mTabHost;

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}

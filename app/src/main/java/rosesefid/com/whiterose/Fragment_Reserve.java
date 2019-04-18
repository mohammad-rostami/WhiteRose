package rosesefid.com.whiterose;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_Reserve extends Fragment {
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

    public Fragment_Reserve() {
        // Required empty public constructor
    }

    public static Fragment_Reserve newInstance(String param1, String param2) {
        Fragment_Reserve fragment = new Fragment_Reserve();
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

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_reserve);

        View tabIndicatorToday = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("aaaa");
        View tabIndicatorLive = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("bbbb");
        View tabIndicatorxxx = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorxxx.findViewById(R.id.tv_tab_txt)).setText("cccc");

        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("رزرو هتل");
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("رزرو تور");
        ((TextView) tabIndicatorxxx.findViewById(R.id.tv_tab_txt)).setText("رزرو بلیط");

//        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.today)).setIndicator(tabIndicatorToday), EntriesTodayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab0").setIndicator(tabIndicatorToday), Fragment_Reserve_Hotel.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabIndicatorLive), Fragment_Reserve_Tour.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabIndicatorxxx), Fragment_Reserve_Flight.class, null);
        mTabHost.setCurrentTab(2);
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

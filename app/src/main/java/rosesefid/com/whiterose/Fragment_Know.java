package rosesefid.com.whiterose;

import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Know extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int button = 0;
    private OnFragmentInteractionListener mListener;
    private ValueAnimator mValueAnimator_Animator;
    private FragmentTabHost mTabHost;

    public Fragment_Know() {
        // Required empty public constructor
    }

    public static Fragment_Know newInstance(String param1, String param2) {
        Fragment_Know fragment = new Fragment_Know();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTabHost = new FragmentTabHost(getActivity());

        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_reserve);

        View tabIndicatorToday = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator_know, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("aaaa");
        View tabIndicatorLive = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator_know, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("bbbb");
        View tabIndicatorxxx = LayoutInflater.from(getActivity()).inflate(R.layout.tab_indicator_know, mTabHost.getTabWidget(), false);
        ((TextView) tabIndicatorxxx.findViewById(R.id.tv_tab_txt)).setText("cccc");

        ((TextView) tabIndicatorToday.findViewById(R.id.tv_tab_txt)).setText("پروازهای ورودی");
        ((TextView) tabIndicatorLive.findViewById(R.id.tv_tab_txt)).setText("پروازهای خروجی");
        ((TextView) tabIndicatorxxx.findViewById(R.id.tv_tab_txt)).setText("نرخ ارز");

//        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.today)).setIndicator(tabIndicatorToday), EntriesTodayFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab0").setIndicator(tabIndicatorxxx), Fragment_Currency.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabIndicatorLive), Fragment_Outgoing.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabIndicatorToday), Fragment_Incoming.class, null);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

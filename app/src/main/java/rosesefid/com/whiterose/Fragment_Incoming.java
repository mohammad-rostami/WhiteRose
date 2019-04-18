package rosesefid.com.whiterose;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Fragment_Incoming extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<Struct> mArray_Structs = new ArrayList<>();
    public RecyclerView.Adapter mRecyclerAdapter_Adapter;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RecyclerView mDialogRecyclerView_country;
    private Adapter_Recycler sDialogAdapter_Adapter;
    private ArrayList<Struct> sArray_Struct = new ArrayList<>();
    private ProgressDialog progress;
    private static LinearLayout mFilterBar;
    private static Animation bottomUp;
    private String mStrChartData;
    private String mStr_flightChartData;


    public Fragment_Incoming() {
        // Required empty public constructor
    }

    public static Fragment_Incoming newInstance(String param1, String param2) {
        Fragment_Incoming fragment = new Fragment_Incoming();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progress = new ProgressDialog(getContext(), R.style.MyProgressTheme);
        progress.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
        progress.setCancelable(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incoming, container, false);
        mFilterBar = (LinearLayout) view.findViewById(R.id.fragment_incoming_rl_filter);
        bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_bar_up);

        mStr_flightChartData = G.STR_FLIGHT_CHART_DATA;
        InitializeRecyclerView(mStr_flightChartData);

        //Recycler View
        mDialogRecyclerView_country = (RecyclerView) view.findViewById(R.id.activity_clock_rv_times);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mDialogRecyclerView_country.setLayoutManager(mLinearLayoutManager);
        sDialogAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, sArray_Struct, new OnItemListener() {


            public int mInt_itemIndex;

            @Override
            public void onItemSelect(int position) {
            }

            @Override
            public void onItemClick(int position) {

            }
        }, 6, false);
        mDialogRecyclerView_country.setAdapter(sDialogAdapter_Adapter);
        return view;
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

    public static void filterBarAnimation() {
        mFilterBar.setVisibility(View.VISIBLE);
        mFilterBar.startAnimation(bottomUp);
    }

    private void InitializeRecyclerView(String mStrChartData) {
        try {


            Log.d("chart1", mStrChartData);
            Document document = Jsoup.parse(mStrChartData);
            Element table = document.select("table").first();
            Elements airline = table.getElementsByClass("airline");
            Elements number = table.getElementsByClass("number");
            Elements location = table.getElementsByClass("location");
            Elements status = table.getElementsByClass("status");
            Elements description = table.getElementsByClass("description");
            Elements time = table.getElementsByClass("time");
            Elements fulldate = table.getElementsByClass("fulldate");
            JSONObject jo = new JSONObject();
            ArrayList ff = new ArrayList();
            for (int i = 0; i < number.size(); i++) {
                String key = airline.get(i).text();
                String value = number.get(i).text();
                String value1 = location.get(i).text();
                String value2 = status.get(i).text();
                String value4 = time.get(i).text();
                String value5 = fulldate.get(i).text();

                ff.add(key + "#" + value + "@" + value1 + ":" + value2 + "?" + value4 + "%" + value5);
            }

            for (int i = 1; i < ff.size(); i++) {
                Struct struct = new Struct();

                String mStr_ArrayItem = String.valueOf(ff.get(i));
                struct.mStr_First = String.valueOf(mStr_ArrayItem).substring(0, String.valueOf(mStr_ArrayItem).indexOf("#"));
                struct.mStr_Second = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("#") + 1, String.valueOf(mStr_ArrayItem).indexOf("@"));
                struct.mStr_Third = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("@") + 1, String.valueOf(mStr_ArrayItem).indexOf(":"));
                struct.mStr_Forth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf(":") + 1, String.valueOf(mStr_ArrayItem).indexOf("?"));
                struct.mStr_Fifth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("?") + 1, String.valueOf(mStr_ArrayItem).indexOf("%"));
                struct.mStr_Sixth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("%") + 1, String.valueOf(mStr_ArrayItem).length());
                sArray_Struct.add(struct);
//            sDialogAdapter_Adapter.notifyDataSetChanged();
            }
        } catch (NullPointerException e) {

        }
    }

//    public static void profileTopBannerAnimationClose() {
//        mFilterBar.setVisibility(View.VISIBLE);
//        mFilterBar.startAnimation(bottomDown);
//    }

}

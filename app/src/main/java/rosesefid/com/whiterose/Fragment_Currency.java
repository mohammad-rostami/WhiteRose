package rosesefid.com.whiterose;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Fragment_Currency extends Fragment {
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


    public Fragment_Currency() {
        // Required empty public constructor
    }

    public static Fragment_Currency newInstance(String param1, String param2) {
        Fragment_Currency fragment = new Fragment_Currency();
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
        new mAsync_webData().execute("http://www.o-xe.com");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incoming, container, false);


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
        }, 8, false);
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

    private class mAsync_webData extends Webservice.GetClass {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();

            Document document = Jsoup.parse(s);
            Element table = document.select("table").first();
            Elements code = table.getElementsByClass("MonetaryCode");
            Elements text = table.getElementsByClass("MonetaryText");
            Elements buy = table.getElementsByClass("MonetaryBuy");
            Elements sell = table.getElementsByClass("MonetarySell");
//            Elements status = table.getElementsByClass("status");
//            Elements description = table.getElementsByClass("description");
//            Elements time = table.getElementsByClass("time");
//            Elements fulldate = table.getElementsByClass("fulldate");
            JSONObject jo = new JSONObject();
            ArrayList ff = new ArrayList();
            for (int i = 0; i < code.size(); i++) {
                String key = code.get(i).text();
                String name = text.get(i + 5).text();
                String value = buy.get(i + 5).text();
                String value1 = sell.get(i + 5).text();
//                String value2 = status.get(i).text();
//                String value4 = time.get(i).text();
//                String value5 = fulldate.get(i).text();

                ff.add(key + "#" + value + "@" + value1 + ":" + name);
            }

            for (int i = 0; i < ff.size(); i++) {
                Struct struct = new Struct();

                String mStr_ArrayItem = String.valueOf(ff.get(i));
                struct.mStr_First = String.valueOf(mStr_ArrayItem).substring(0, String.valueOf(mStr_ArrayItem).indexOf("#"));
                struct.mStr_Second = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("#") + 1, String.valueOf(mStr_ArrayItem).indexOf("@"));
                struct.mStr_Third = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("@") + 1, String.valueOf(mStr_ArrayItem).indexOf(":"));
                struct.mStr_Forth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf(":") + 1, mStr_ArrayItem.length());
//                struct.mStr_Fifth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("?") + 1, String.valueOf(mStr_ArrayItem).indexOf("%"));
//                struct.mStr_Sixth = String.valueOf(mStr_ArrayItem).substring(String.valueOf(mStr_ArrayItem).indexOf("%") + 1, String.valueOf(mStr_ArrayItem).length());
                sArray_Struct.add(struct);
                sDialogAdapter_Adapter.notifyDataSetChanged();
            }
        }
    }

}

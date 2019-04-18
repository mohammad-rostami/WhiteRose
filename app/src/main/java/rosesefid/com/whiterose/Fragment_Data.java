package rosesefid.com.whiterose;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Data extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<Struct> mArray_Structs = new ArrayList<>();
    public RecyclerView.Adapter mRecyclerAdapter_Adapter;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private String mStr_packages;

    public Fragment_Data() {
        // Required empty public constructor
    }

    public static Fragment_Data newInstance(String param1, String param2) {
        Fragment_Data fragment = new Fragment_Data();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mStr_packages = G.STR_PACKAGES;
        InitializeRecyclerView(mStr_packages);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        RecyclerView mRecyclerView_Table = (RecyclerView) view.findViewById(R.id.fragment_data_rv_table);
        mRecyclerAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, mArray_Structs, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {
//                mRecyclerAdapter_Adapter.notifyItemChanged(position, new Object());
            }

            @Override
            public void onItemClick(int position) {
                G.STR_PACKAGE_NAME = mArray_Structs.get(position).mStr_First;
                G.STR_TARGET = mArray_Structs.get(position).mStr_Target;
                Intent intent = new Intent(getContext(), Activity_Package.class);
                startActivity(intent);
            }
        }, 1, false);
        mRecyclerView_Table.setAdapter(mRecyclerAdapter_Adapter);
        RecyclerView.LayoutManager mLayoutManager_manager = new LinearLayoutManager(getContext());
        mRecyclerView_Table.setLayoutManager(mLayoutManager_manager);
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


    private void InitializeRecyclerView(String mStr_packages) {
        try {
            JSONObject json = new JSONObject(mStr_packages);
            JSONArray data = json.getJSONArray("PackgeList");
            for (int i = 0; i < data.length(); i++) {
                JSONObject Object = data.getJSONObject(i);
                Struct struct = new Struct();
                struct.mStr_Target = Object.getString("Idn");
                struct.mStr_First = Object.getString("PackageNameEn");
//                    struct.mStr_Second = Object.getString("Country");
                struct.mStr_Fifth = Object.getString("DescTitle");
                struct.mStr_Sixth = Object.getString("Description");
                struct.mStr_image = Object.getString("Image");
//                    struct.mStr_StartDate = Object.getString("GoDateMiladi");
                mArray_Structs.add(struct);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        mRecyclerAdapter_Adapter.notifyDataSetChanged();
    }
}

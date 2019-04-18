package rosesefid.com.whiterose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Activity_Package extends Activity {
    public ArrayList<Struct> mArray_Struct = new ArrayList<>();
    public RecyclerView.Adapter mRecyclerAdapter_Adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        new mAsync_HotelPackages().execute("http://rose.travel/api/v1/Packagehotels/getpackagehoteldetail?packageid=" + G.STR_TARGET);

        RelativeLayout mRelativeLayout_ToolbarRoot = (RelativeLayout) findViewById(R.id.toolbar_main_tb_main);
        mRelativeLayout_ToolbarRoot.setBackgroundColor(getResources().getColor(R.color.first_color));
        RecyclerView mRecyclerView_Table = (RecyclerView) findViewById(R.id.fragment_data_rv_table);
        mRecyclerAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, mArray_Struct, new OnItemListener() {

            @Override
            public void onItemSelect(int position) {

            }

            @Override
            public void onItemClick(int position) {
                G.STR_HOTEL_NAME = mArray_Struct.get(position).mStr_First;
                G.STR_DBL = mArray_Struct.get(position).mStr_DBL;
                G.STR_SGL = mArray_Struct.get(position).mStr_SGL;

                Intent intent = new Intent(getApplicationContext(), Activity_Request.class);
                startActivity(intent);
            }
        }, 2, false);
        mRecyclerView_Table.setAdapter(mRecyclerAdapter_Adapter);
        RecyclerView.LayoutManager mLayoutManager_manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView_Table.setLayoutManager(mLayoutManager_manager);
    }


    private class mAsync_HotelPackages extends Webservice.GetClass {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject data = new JSONObject(result);
                JSONArray singlePackage = data.getJSONArray("PackageDetails");
                for (int i = 0; i < singlePackage.length(); i++) {
                    JSONObject Object = singlePackage.getJSONObject(i);

                    Struct struct = new Struct();
                    struct.mStr_First = Object.getString("Name");
                    struct.mStr_Second = Object.getString("RoomName");
                    struct.mStr_Third = Object.getString("Quality");
                    struct.mStr_Forth = Object.getString("RoomService");
                    struct.mStr_Fifth = Object.getString("HotelDescription");
                    struct.mStr_DBL = Object.getString("Dbl");
                    struct.mStr_SGL = Object.getString("Sgl");
                    struct.mStr_CHD1 = Object.getString("ExtraBed");
                    struct.mStr_CHD2 = Object.getString("NoBed");
                    struct.mStr_INF = Object.getString("Infant");
                    mArray_Struct.add(struct);
                }
                mRecyclerAdapter_Adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

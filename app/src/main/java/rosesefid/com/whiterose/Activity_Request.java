package rosesefid.com.whiterose;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Activity_Request extends Activity {
    private static TextView TXT_INPUT_ADDRESS;
    private ArrayList<Struct> mArray_Structs = new ArrayList<>();
    private RecyclerView.Adapter mRecyclerAdapter_Adapter;
    private Dialog Dialog_Number;
    private int mInt_Box = 0;
    private int mInt_NC;
    private int mInt_Room;
    private int mInt_A;
    private int mInt_C;
    private int mInt_All;
    private EditText mEditText_Adult;
    private EditText mEditText_Child;
    private EditText mEditText_Infant;
    private EditText mEditText_Room;

    public static void address_setter(String address) {
        TXT_INPUT_ADDRESS.setText(address);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_request);

        Dialog_Number = new Dialog(this);
        Dialog_Number.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Number.setContentView(R.layout.dialog_number);
        Dialog_Number.setCancelable(true);

        TextView mTextView_Send = (TextView) findViewById(R.id.activity_request_txt_send);
        TextView mTextView_Confirm = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_confirm);
        TextView mTextView_Cancel = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_cancel);
        TextView mTextView_Header = (TextView) findViewById(R.id.toolbar_main_txt_header);
        mTextView_Header.setTypeface(G.TYPE_FACE_SANS);
        mTextView_Header.setText("ثبت درخواست");

        TextView mTextView_PackageInfo = (TextView) findViewById(R.id.activity_request_txt_package_info);
        TextView mTextView_PackageNameHeader = (TextView) findViewById(R.id.activity_request_txt_package_name_header);
        TextView mTextView_PackageName = (TextView) findViewById(R.id.activity_request_txt_package_name);
        TextView mTextView_HotelNameHeader = (TextView) findViewById(R.id.activity_request_hotel_name_header);
        TextView mTextView_HotelName = (TextView) findViewById(R.id.activity_request_txt_hotel_name);
        TextView mTextView_DblHeader = (TextView) findViewById(R.id.activity_request_txt_dbl_header);
        TextView mTextView_Dbl = (TextView) findViewById(R.id.activity_request_txt_dbl);
        TextView mTextView_SglHeader = (TextView) findViewById(R.id.activity_request_txt_sgl_header);
        TextView mTextView_Sgl = (TextView) findViewById(R.id.activity_request_txt_sgl);

        TextView mTextView_PackageDate = (TextView) findViewById(R.id.activity_request_txt_package_date);
        TextView mTextView_LoginInfo = (TextView) findViewById(R.id.activity_request_txt_login_info);

        TextInputLayout mTextInputLayout_Adult = (TextInputLayout) findViewById(R.id.activity_request_til_adl);
        TextInputLayout mTextInputLayout_Child = (TextInputLayout) findViewById(R.id.activity_request_til_chd);
        TextInputLayout mTextInputLayout_Infant = (TextInputLayout) findViewById(R.id.activity_request_til_inf);
        TextInputLayout mTextInputLayout_Date = (TextInputLayout) findViewById(R.id.activity_request_til_date);
        TextInputLayout mTextInputLayout_Room = (TextInputLayout) findViewById(R.id.input_layout_room);
        mEditText_Adult = (EditText) findViewById(R.id.activity_request_et_adl);
        mEditText_Child = (EditText) findViewById(R.id.activity_request_et_chd);
        mEditText_Infant = (EditText) findViewById(R.id.activity_request_et_inf);
        EditText mEditText_Date = (EditText) findViewById(R.id.activity_request_et_date);
        mEditText_Room = (EditText) findViewById(R.id.input_room);

        TextView mTextView_Name = (TextView) findViewById(R.id.activity_request_et_name);
        TextInputLayout mTextInputLayout_Name = (TextInputLayout) findViewById(R.id.activity_request_til_name);
        LinearLayout mLinearLayout_ChooseLocation = (LinearLayout) findViewById(R.id.activity_request_ll_choose_location);
        TextView mTextView_ChooseLocation = (TextView) findViewById(R.id.activity_request_txt_choose_location);
        TXT_INPUT_ADDRESS = (TextView) findViewById(R.id.activity_request_et_address);
        TextInputLayout mTextInputLayout_Address = (TextInputLayout) findViewById(R.id.activity_request_til_address);
        TextView mTextView_Tel = (TextView) findViewById(R.id.activity_request_et_tel);
        TextInputLayout mTextInputLayout_Tel = (TextInputLayout) findViewById(R.id.activity_request_til_tel);
        TextView mTextView_Email = (TextView) findViewById(R.id.activity_request_et_email);
        TextInputLayout mTextInputLayout_Email = (TextInputLayout) findViewById(R.id.activity_request_til_email);

        final TextView mDialogTextView_Number = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_number);
        final NumberPicker mDialogNumberPicker_NumberPicker = (NumberPicker) Dialog_Number.findViewById(R.id.dialog_number_np_picker);
        mDialogNumberPicker_NumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDialogNumberPicker_NumberPicker.setMinValue(1);
        mDialogNumberPicker_NumberPicker.setMaxValue(9);


        mEditText_Adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogNumberPicker_NumberPicker.setMinValue(1);
                mDialogNumberPicker_NumberPicker.setMaxValue(9);
                mInt_Box = 1;
                Dialog_Number.show();
                if (mEditText_Adult.getText().toString().length() == 0) {
                    mDialogNumberPicker_NumberPicker.setValue(1);
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                } else {
                    mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(mEditText_Adult.getText().toString()));
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                }
            }
        });
        mEditText_Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogNumberPicker_NumberPicker.setMinValue(0);
                mDialogNumberPicker_NumberPicker.setMaxValue(7);
                mInt_Box = 2;
                Dialog_Number.show();
                if (mEditText_Child.getText().toString().length() == 0) {
                    mDialogNumberPicker_NumberPicker.setValue(0);
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                } else {
                    mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(mEditText_Child.getText().toString()));
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                }
            }
        });
        mEditText_Infant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogNumberPicker_NumberPicker.setMinValue(0);
                mDialogNumberPicker_NumberPicker.setMaxValue(5);
                mInt_Box = 3;
                Dialog_Number.show();
                if (mEditText_Infant.getText().toString().length() == 0) {
                    mDialogNumberPicker_NumberPicker.setValue(0);
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                } else {
                    mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(mEditText_Infant.getText().toString()));
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                }
            }
        });
        mEditText_Room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = Integer.parseInt(mEditText_Room.getText().toString());
                mDialogNumberPicker_NumberPicker.setMinValue(min);
                mDialogNumberPicker_NumberPicker.setMaxValue(9);
                mInt_Box = 4;
                Dialog_Number.show();
                if (mEditText_Room.getText().toString().length() == 0) {
                    mDialogNumberPicker_NumberPicker.setValue(1);
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                } else {
                    mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(mEditText_Room.getText().toString()));
                    mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_NumberPicker.getValue()));
                }
            }
        });


        mDialogNumberPicker_NumberPicker.setWrapSelectorWheel(true);
        mDialogNumberPicker_NumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mDialogTextView_Number.setText(String.valueOf(newVal));
            }
        });

        mTextView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInt_Box == 1) {
                    mEditText_Adult.setText(mDialogTextView_Number.getText().toString());
                }
                if (mInt_Box == 2) {
                    mEditText_Child.setText(mDialogTextView_Number.getText().toString());
                }
                if (mInt_Box == 3) {
                    mEditText_Infant.setText(mDialogTextView_Number.getText().toString());
                }
                if (mInt_Box == 4) {
                    mEditText_Room.setText(mDialogTextView_Number.getText().toString());
                }
                Dialog_Number.dismiss();
                RoomCalculator();
            }
        });
        mTextView_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Number.dismiss();
            }
        });


        mLinearLayout_ChooseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Request.this, Activity_Map.class);
                Activity_Request.this.startActivity(intent);
            }
        });
//        new Async1().execute("http://185.129.168.251/api/HotelPackages", G.STR_TARGET);
//
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerAdapter_Adapter = new Adapter_Recycler(G.sContext, mArray_Struct, new OnItemListener() {
//
//            @Override
//            public void onItemSelect(int position) {
////                name = mArray_Struct.get(position).mStr_Third;
////                Directions.checkActivity(name);
//            }
//
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(getApplicationContext(), Activity_Request.class);
//                startActivity(intent);
//            }
//        }, 2, false);
//        recyclerView.setAdapter(mRecyclerAdapter_Adapter);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(manager);

        RecyclerView mRecyclerView_Suggest = (RecyclerView) findViewById(R.id.activity_request_rv_suggest);
        mRecyclerAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, mArray_Structs, new OnItemListener() {
            @Override
            public void onItemSelect(int position) {

//                name = mArray_Struct.get(position).mStr_Third;
//                Directions.checkActivity(name);
            }

            @Override
            public void onItemClick(int position) {
//                G.STR_PACKAGE_NAME = mArray_Struct.get(position).mStr_First;
//                G.STR_TARGET = mArray_Struct.get(position).mStr_Target;
//                Log.d("mohammad", mArray_Struct.get(position).mStr_Target);
//                Intent intent = new Intent(getContext(), Activity_Package.class);
//                startActivity(intent);
            }
        }, 3, false);
        mRecyclerView_Suggest.setAdapter(mRecyclerAdapter_Adapter);
        RecyclerView.LayoutManager mLayoutManager_manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView_Suggest.setLayoutManager(mLayoutManager_manager);
    }

//    private class Async1 extends Webservice.PostClassID {
//
//        @Override
//        protected void onPreExecute() {
////            progress1 = new ProgressDialog(Activity_ProductsList.this, R.style.MyTheme);
////            progress1.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
////            progress1.setCancelable(true);
////            progress1.show();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
////            progress1.dismiss();
//
//            try {
//                JSONArray data = new JSONArray(result);
//                for (int i = 0; i < data.length(); i++) {
//                    JSONObject Object = data.getJSONObject(i);
//                    Struct struct = new Struct();
//                    struct.mStr_First = Object.getString("Name");
//                    struct.mStr_Second = Object.getString("RoomName");
//                    struct.mStr_Fifth = Object.getString("Description");
//                    struct.strQuality = Object.getString("Quality");
//                    struct.strService = Object.getString("Service");
//                    struct.mStr_DBL = Object.getString("STR_DBL");
//                    struct.mStr_SGL = Object.getString("STR_SGL");
//                    struct.mStr_CHD1 = Object.getString("CHD1");
//                    struct.mStr_CHD2 = Object.getString("CHD2");
//                    mArray_Struct.add(struct);
//                }
//                mRecyclerAdapter_Adapter.notifyDataSetChanged();
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void RoomCalculator() {
        mInt_Room = 1;
        mInt_NC = 0;
        mInt_A = Integer.parseInt(mEditText_Adult.getText().toString());

        if (mInt_A < 4) {
            mInt_Room = 1;
            mInt_NC = (mInt_Room * 3) - mInt_A;
        } else if (mInt_A > 3) {
            float result = 0;
            result = (float) mInt_A / 3;
            if (result > 1 && result <= 2) {
                mInt_Room = mInt_Room + 1;
            } else if (result > 2 && result <= 3) {
                mInt_Room = mInt_Room + 2;
            }
            mInt_NC = (mInt_Room * 3) - mInt_A;
        }
        ChildCalculator();
    }

    public void ChildCalculator() {
        mInt_C = Integer.parseInt(mEditText_Child.getText().toString());
        int mInt_Result_C = mInt_C - mInt_NC;
        if (mInt_Result_C > 0 && mInt_Result_C < 4) {
            mInt_Room = mInt_Room + 1;
        } else if (mInt_Result_C > 3 && mInt_Result_C < 7) {
            mInt_Room = mInt_Room + 2;
        } else if (mInt_Result_C >= 7) {
            mInt_Room = mInt_Room + 3;
        }
        InfantCalculator();

    }

    public void InfantCalculator() {
        int mInt_N = Integer.parseInt(mEditText_Infant.getText().toString());
        int extra_place = (mInt_Room * 3) - (mInt_A + mInt_C);
        int NN = mInt_Room + extra_place;
        int mInt_Result_N = mInt_N - NN;
        if (mInt_Result_N > 0 && mInt_Result_N < 4) {
            mInt_Room = mInt_Room + 1;
        } else if (mInt_Result_N > 3 && mInt_Result_N < 7) {
            mInt_Room = mInt_Room + 2;
        }

        mEditText_Room.setText(String.valueOf(mInt_Room));

        if (mInt_Result_N > 0) {
            mInt_All = mInt_A + mInt_C + mInt_Result_N;
        } else if (mInt_Result_N < 0) {
            mInt_All = mInt_A + mInt_C;
        }
        RoomSuggestion(mInt_All, mInt_Room);
    }

    public void RoomSuggestion(int persons, int room) {
        ArrayList rooms = new ArrayList();
        ArrayList rooms1 = new ArrayList();
        int F = 0;
        int G = 0;
        for (int i = 1; i < room + 1; i++) {
            if (3 < ((persons + 1) - F)) {
                rooms.add(3);
                F = F + 3;
            } else {
                if (2 < ((persons + 1) - F)) {
                    rooms.add(2);
                    F = F + 2;
                } else {
                    if (1 < ((persons + 1) - F)) {
                        rooms.add(1);
                        F = F + 1;
                    }
                }
            }
        }

        for (int j = 1; j < room + 1; j++) {
            if (2 < ((persons + 1) - G)) {
                rooms1.add(2);
                G = G + 2;
            } else {
                if (1 < ((persons + 1) - G)) {
                    rooms1.add(1);
                    G = G + 1;
                }
            }
        }
        if (G != persons) {
            int length = rooms1.size();
            int last = (int) rooms1.get(length - 1);
            rooms1.remove(length - 1);
            rooms1.add(last + 1);
            G = G + 1;
            if (G != persons) {
                rooms1.clear();
            }
        }

        int arrays = 1;

        ArrayList mArray_RoomName = new ArrayList();
        ArrayList mArray_RoomPrice = new ArrayList();
        ArrayList mArray_RoomName1 = new ArrayList();
        ArrayList mArray_RoomPrice1 = new ArrayList();
        ArrayList mArray_FinalRoomName = new ArrayList();
        ArrayList mArray_FinalRoomPrice = new ArrayList();

        mArray_RoomName.clear();
        mArray_RoomName1.clear();
        mArray_RoomPrice.clear();
        mArray_RoomPrice1.clear();
        mArray_FinalRoomName.clear();
        mArray_FinalRoomPrice.clear();

        for (int x = 0; x < room; x++) {
            Collections.sort(rooms);
            int element = (int) rooms.get(x);

            if (element == 1) {
                mArray_RoomName.add("STR_SGL");
                mArray_RoomPrice.add("STR_SGL PRICE");
            }
            if (element == 2) {
                mArray_RoomName.add("STR_DBL");
                mArray_RoomPrice.add("STR_DBL PRICE");
            }
            if (element == 3) {
                mArray_RoomName.add("STR_DBL + ext bed");
                mArray_RoomPrice.add("TRP PRICE");
            }
        }
        String mStr_ListString = "";
        String mStr_ListPrice = "";
        for (int b = 0; b < mArray_RoomName.size(); b++) {
            mStr_ListString += mArray_RoomName.get(b) + "\n";
        }
        Log.d("room_names", mStr_ListString);

        for (int c = 0; c < mArray_RoomPrice.size(); c++) {
            mStr_ListPrice += mArray_RoomPrice.get(c) + "\n";
        }
        mArray_FinalRoomName.add(mStr_ListString);
        mArray_FinalRoomPrice.add(mStr_ListPrice);


        if (rooms1.size() == 0) {
        } else {
            Collections.sort(rooms1);
            if (equalLists(rooms, rooms1) == true) {

            } else {
                for (int q = 0; q < room; q++) {
                    int element1 = (int) rooms1.get(q);

                    if (element1 == 1) {
                        mArray_RoomName1.add("STR_SGL");
                        mArray_RoomPrice1.add("STR_SGL PRICE");
                    }
                    if (element1 == 2) {
                        mArray_RoomName1.add("STR_DBL");
                        mArray_RoomPrice1.add("STR_DBL PRICE");
                    }
                    if (element1 == 3) {
                        mArray_RoomName1.add("STR_DBL + ext bed");
                        mArray_RoomPrice1.add("TRP PRICE");
                    }
                }
                arrays = 2;
                String mStr_ListString1 = "";
                String mStr_ListPrice1 = "";
                for (int b = 0; b < mArray_RoomName1.size(); b++) {
                    mStr_ListString1 += mArray_RoomName1.get(b) + "\n";
                }
                Log.d("room_names1", mStr_ListString1);

                for (int c = 0; c < mArray_RoomPrice1.size(); c++) {
                    mStr_ListPrice1 += mArray_RoomPrice1.get(c) + "\n";
                }
                mArray_FinalRoomName.add(mStr_ListString1);
                mArray_FinalRoomPrice.add(mStr_ListPrice1);
            }
        }


        try {
            mArray_Structs.clear();
            mRecyclerAdapter_Adapter.notifyDataSetChanged();

            for (int i = 0; i < arrays; i++) {
                Struct struct = new Struct();
                struct.mStr_First = (String) mArray_FinalRoomName.get(i);
                struct.mStr_Second = (String) mArray_FinalRoomPrice.get(i);
                mArray_Structs.add(struct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRecyclerAdapter_Adapter.notifyDataSetChanged();
    }

    public boolean equalLists(List<String> one, List<String> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null) || one != null && two == null || one.size() != two.size()) {
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by mInt_A. R. S.
        one = new ArrayList<String>(one);
        two = new ArrayList<String>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }
}

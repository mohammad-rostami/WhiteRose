package rosesefid.com.whiterose;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class Fragment_Time extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private TextView mTextView_NowTime;
    private TextView mTextView_NowDate;
    private String mStr_DayName;
    private String mStr_MonthName;
    private TextView mTextView_NowDate_Christian;
    private String mStr_DayName_En;
    private String mStr_MonthName_En;
    private LinearLayout mLinearLayout_Root;
    private ArrayList<Struct> sArray_Struct = new ArrayList<>();
    private ArrayList sArray_AchievedStruct = new ArrayList<>();
    private String[] mStr_CityArray;
    private Adapter_Recycler sDialogAdapter_Adapter;
    private InputMethodManager imm;
    private SharedPreferences.Editor mSharedPreferenceEdit_WorldClock;
    private SharedPreferences.Editor mSharedPreferenceEdit_Filler;
    private RecyclerView mDialogRecyclerView_country;
    private ImageView mTextView_img_clear;
    private AppCompatAutoCompleteTextView mDialogAutoCompleteTextView;
    private View view = null;

    public Fragment_Time() {
        // Required empty public constructor
    }

    public static Fragment_Time newInstance(String param1, String param2) {
        Fragment_Time fragment = new Fragment_Time();
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

        //Defining shared prefrences editor
        mSharedPreferenceEdit_WorldClock = G.SHARED_PREFERENCE_WORLD_CLOCK.edit();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_clock, container, false);


            // Defining Views
            mDialogAutoCompleteTextView = (AppCompatAutoCompleteTextView) getActivity().findViewById(R.id.toolbar_main_txt_header2);
            mTextView_img_clear = (ImageView) getActivity().findViewById(R.id.toolbar_main_btn_navigation2);
            mTextView_img_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogAutoCompleteTextView.setText("");
                }
            });

            mLinearLayout_Root = (LinearLayout) view.findViewById(R.id.activity_clock_ll_root);
            mTextView_NowTime = (TextView) view.findViewById(R.id.activity_clock_txt_now_time);
            mTextView_NowDate = (TextView) view.findViewById(R.id.activity_clock_txt_now_date);
            mTextView_NowDate_Christian = (TextView) view.findViewById(R.id.activity_clock_txt_now_date_christian);


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
                    String mStr_CityName = sArray_Struct.get(position).mStr_Second;
                    Log.d("searched", mStr_CityName);
                    for (int i = 0; i < G.sArray_items.size(); i++) {
                        Log.d("item", String.valueOf(G.sArray_items.get(i)));
                        String x = String.valueOf(G.sArray_items.get(i));
                        if (x.contains(mStr_CityName)) {
                            mInt_itemIndex = i;
                        }
                    }
                    G.sArray_items.remove(mInt_itemIndex);
                    Set<String> set = new HashSet<String>();
                    set.addAll(G.sArray_items);
                    mSharedPreferenceEdit_WorldClock.putStringSet("Favorite", set);
                    mSharedPreferenceEdit_WorldClock.commit();

                    sArray_Struct.remove(position);
                    sDialogAdapter_Adapter.notifyItemRemoved(position);
                    sDialogAdapter_Adapter.notifyItemRangeChanged(position, sArray_Struct.size());
                    for (int i = 0; i < G.sArray_items.size(); i++) {
                        Log.d("items after proccess", String.valueOf(G.sArray_items.get(i)));
                    }
                }
            }, 5, false);
            mDialogRecyclerView_country.setAdapter(sDialogAdapter_Adapter);


// Initializing RecyclerView Data
            Set<String> set = G.SHARED_PREFERENCE_WORLD_CLOCK.getStringSet("Favorite", null);
            List<String> sample = new ArrayList<String>(set);

            try {
                for (int j = 0; j < sample.size(); j++) {
                    Struct struct = new Struct();
                    TimeZone mTimeZone = TimeZone.getTimeZone(String.valueOf(sample.get(j)).substring(0, String.valueOf(sample.get(j)).indexOf(":")));
                    Calendar mCalender = Calendar.getInstance(mTimeZone);

                    struct.mStr_First = String.format("%02d", mCalender.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", mCalender.get(Calendar.MINUTE));
                    struct.mStr_Second = String.valueOf(sample.get(j)).substring(String.valueOf(sample.get(j)).indexOf(":") + 1, String.valueOf(sample.get(j)).length());
                    struct.mStr_Third = (TimeZone.getTimeZone(mTimeZone.getID()).getDisplayName(false, TimeZone.SHORT));

                    sArray_Struct.add(struct);
                    sDialogAdapter_Adapter.notifyDataSetChanged();
                }
            } catch (NullPointerException e) {
            }


// Defining AutoComplete EditText and Soft Keyboard
            ArrayAdapter<String> mAdapter_CountryAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_autocomplete, G.ARRAY_CITY);
            mDialogAutoCompleteTextView.setAdapter(mAdapter_CountryAdapter);
//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mDialogAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogAutoCompleteTextView.setCursorVisible(true);
                }
            });

// AutoComplete Process
            final ArrayList<String> mArray_ResList = new ArrayList<String>();
            mDialogAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // getting selected item text
                    String mStr_SelectedItem = String.valueOf(parent.getItemAtPosition(position));
                    // check main array if selected item is in there
                    for (String curVal : G.ARRAY_CITY_CODE) {
                        // check for PERSIAN name
                        if (curVal.contains(":" + mStr_SelectedItem + "::")) {
//                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            mDialogAutoCompleteTextView.clearFocus();
                            mArray_ResList.clear();
                            mArray_ResList.add(curVal);
                            // get and pars string of item
                            String mStr_Item = mArray_ResList.get(0);
                            String mStr_TimeZone = String.valueOf(mStr_Item).substring(0, String.valueOf(mStr_Item).indexOf("+"));
                            String mStr_CityName = String.valueOf(mStr_Item).substring(String.valueOf(mStr_Item).indexOf(":") + 1, String.valueOf(mStr_Item).indexOf("::"));
                            // get time according to timezone
                            TimeZone mTimeZone = TimeZone.getTimeZone(mStr_TimeZone);
                            Calendar mCalender = Calendar.getInstance(mTimeZone);
                            String mStr_FinalTime = String.format("%02d", mCalender.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", mCalender.get(Calendar.MINUTE));
                            // check if selected city exist
                            if (G.sArray_items.contains(mStr_TimeZone + ":" + mStr_CityName)) {
                                Log.d("contains", "yes");
                                Toast.makeText(getContext(), "این شهر قبلا اضافه شده", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("contains", "no");
                                Struct struct = new Struct();
                                struct.mStr_First = mStr_FinalTime;
                                struct.mStr_Second = mStr_CityName;
                                struct.mStr_Third = (TimeZone.getTimeZone(mTimeZone.getID()).getDisplayName(false, TimeZone.SHORT));
                                G.sArray_items.add(0, mStr_TimeZone + ":" + mStr_CityName);

                                sArray_Struct.add(0, struct);
                                sDialogAdapter_Adapter.notifyItemInserted(0);
                                mDialogRecyclerView_country.scrollToPosition(0);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        sDialogAdapter_Adapter.notifyDataSetChanged();
                                    }
                                }, 500);
                                // save new data
                                Set<String> set = new HashSet<String>();
                                set.addAll(G.sArray_items);
                                mSharedPreferenceEdit_WorldClock.putStringSet("Favorite", set);
                                mSharedPreferenceEdit_WorldClock.commit();
                            }
                        }
                        // check for ENGLISH name
                        else if (curVal.contains("::" + mStr_SelectedItem)) {
//                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            mDialogAutoCompleteTextView.clearFocus();
                            mArray_ResList.clear();
                            mArray_ResList.add(curVal);
                            // get and pars string of item
                            String mStr_Item = mArray_ResList.get(0);
                            String mStr_TimeZone = String.valueOf(mStr_Item).substring(0, String.valueOf(mStr_Item).indexOf("+"));
                            String mStr_CityName = String.valueOf(mStr_Item).substring(String.valueOf(mStr_Item).indexOf(":") + 1, String.valueOf(mStr_Item).indexOf("::"));
                            // check if selected city exist
                            TimeZone mTimeZone = TimeZone.getTimeZone(mStr_TimeZone);
                            Calendar mCalender = Calendar.getInstance(mTimeZone);
                            String mStr_FinalTime = String.format("%02d", mCalender.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", mCalender.get(Calendar.MINUTE));
                            // check if selected city exist
                            if (G.sArray_items.contains(mStr_TimeZone + ":" + mStr_CityName)) {
                                Toast.makeText(getContext(), "این شهر قبلا اضافه شده", Toast.LENGTH_SHORT).show();
                            } else {
                                Struct struct = new Struct();
                                struct.mStr_First = mStr_FinalTime;
                                struct.mStr_Second = mStr_CityName;
                                struct.mStr_Third = (TimeZone.getTimeZone(mTimeZone.getID()).getDisplayName(false, TimeZone.SHORT));
                                G.sArray_items.add(mStr_TimeZone + ":" + mStr_CityName);

                                sArray_Struct.add(0, struct);
                                sDialogAdapter_Adapter.notifyItemInserted(0);
                                mDialogRecyclerView_country.scrollToPosition(0);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        sDialogAdapter_Adapter.notifyDataSetChanged();
                                    }
                                }, 500);
                                // save new data
                                Set<String> set = new HashSet<String>();
                                set.addAll(G.sArray_items);
                                mSharedPreferenceEdit_WorldClock.putStringSet("Favorite", set);
                                mSharedPreferenceEdit_WorldClock.commit();
                            }
                        }
                    }
                }
            });

            TimeZone tzs = TimeZone.getDefault();
            Calendar cx = Calendar.getInstance(tzs);
            String time = String.format("%02d", cx.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", cx.get(Calendar.MINUTE));
            mTextView_NowTime.setText(time);

            G.DATE_CHANGER = true;
            String mStr_SolarDate = PersianDate.todayShamsi();
            String month = String.valueOf(mStr_SolarDate).substring(String.valueOf(mStr_SolarDate).indexOf("/") + 1, String.valueOf(mStr_SolarDate).indexOf(":"));
            String day = String.valueOf(mStr_SolarDate).substring(String.valueOf(mStr_SolarDate).indexOf(":") + 1, String.valueOf(mStr_SolarDate).length());

            Calendar c = Calendar.getInstance();

            switch (c.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    mStr_DayName = "یکشنبه";
                    mStr_DayName_En = "Sunday";
                    break;
                case 2:
                    mStr_DayName = "دوشنبه";
                    mStr_DayName_En = "Monday";
                    break;
                case 3:
                    mStr_DayName = "سه شنبه";
                    mStr_DayName_En = "Tuesday";
                    break;
                case 4:
                    mStr_DayName = "چهارشنبه";
                    mStr_DayName_En = "Wednesday";
                    break;
                case 5:
                    mStr_DayName = "پنج شنبه";
                    mStr_DayName_En = "Thursday";
                    break;
                case 6:
                    mStr_DayName = "جمعه";
                    mStr_DayName_En = "Friday";
                    break;
                case 7:
                    mStr_DayName = "شنبه";
                    mStr_DayName_En = "Saturday";
                    break;
            }

            switch (Integer.parseInt(month)) {
                case 1:
                    mStr_MonthName = "فروردین";
                    mStr_MonthName_En = "فروردین";
                    break;
                case 2:
                    mStr_MonthName = "اردیبهشت";
                    break;
                case 3:
                    mStr_MonthName = "خرداد";
                    break;
                case 4:
                    mStr_MonthName = "تیر";
                    break;
                case 5:
                    mStr_MonthName = "مرداد";
                    break;
                case 6:
                    mStr_MonthName = "شهریور";
                    break;
                case 7:
                    mStr_MonthName = "مهر";
                    break;
                case 8:
                    mStr_MonthName = "آبان";
                    break;
                case 9:
                    mStr_MonthName = "آذر";
                    break;
                case 10:
                    mStr_MonthName = "دی";
                    break;
                case 11:
                    mStr_MonthName = "بهمن";
                    break;
                case 12:
                    mStr_MonthName = "اسفند";
                    break;
            }

            switch (c.get(Calendar.MONTH)) {
                case 1:
                    mStr_MonthName_En = "January";
                    break;
                case 2:
                    mStr_MonthName_En = "February";
                    break;
                case 3:
                    mStr_MonthName_En = "March";
                    break;
                case 4:
                    mStr_MonthName_En = "April";
                    break;
                case 5:
                    mStr_MonthName_En = "May";
                    break;
                case 6:
                    mStr_MonthName_En = "June";
                    break;
                case 7:
                    mStr_MonthName_En = "July";
                    break;
                case 8:
                    mStr_MonthName_En = "August";
                    break;
                case 9:
                    mStr_MonthName_En = "September";
                    break;
                case 10:
                    mStr_MonthName_En = "October";
                    break;
                case 11:
                    mStr_MonthName_En = "November";
                    break;
                case 12:
                    mStr_MonthName_En = "December";
                    break;
            }

            mTextView_NowDate.setText(mStr_DayName + " , " + day + " " + mStr_MonthName);
            mTextView_NowDate_Christian.setText(mStr_DayName_En + " , " + mStr_MonthName_En + " " + c.get(Calendar.DAY_OF_MONTH));

            CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {

                public void onTick(long millisUntilFinished) {
                    Calendar c = Calendar.getInstance();
//                mTextView_NowTime.setText(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
                }

                public void onFinish() {

                }
            };
            newtimer.start();
//
//        LinearLayout myButton = new LinearLayout(getContext());
//        myButton.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        myButton.setBackgroundColor(Color.parseColor("#ff0000"));
//
//        mLinearLayout_Root.addView(myButton);
//
//        TextView mytext = new TextView(getContext());
//        myButton.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        myButton.addView(mytext);
//        mytext.setText("nkljsahdjkahjkdaskl");
        } else {
        }
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
}

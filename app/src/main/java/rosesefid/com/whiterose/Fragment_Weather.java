package rosesefid.com.whiterose;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_Weather extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ArrayList<Struct> sArray_Struct = new ArrayList<>();
    public ArrayList<Struct> mArray_Structs = new ArrayList<>();
    public RecyclerView.Adapter mRecyclerAdapter_Adapter;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private LinearLayout mLinearLayout_Root;
    private ImageView mImageView_FirstObject;
    private ImageView mImageView_SecondObject;
    private ImageView mImageView_Main;
    private ImageView mImageView_Cover;
    private TextView mTextView_WeatherType;
    private TextView mTextView_Temperature;
    private ImageView mImageView_Sun;
    private DrawerLayout mNavigationDrawer;
    private TextView mTextView_Time;
    private TextView mTextView_FirstDay_min;
    private TextView mTextView_SecondDay_min;
    private TextView mTextView_ThirdDay_min;
    private TextView mTextView_ForthDay_min;
    private TextView mTextView_FifthDay_min;
    private Dialog Dialog_City;
    private String mStr_CityName;
    private String mStr_CityCode;
    private TextView mTextView_CityName;
    private ProgressDialog progress;
    private CardView mCardView_Widget;
    private CardView mCardView_WidgetForcast;
    private LinearLayout mLinearLayout_WidgetForcast;
    private InputMethodManager imm;
    private ImageView mImageView_Today;
    private ImageView mImageView_FirstDay;
    private ImageView mImageView_SecondDay;
    private ImageView mImageView_ThirdDay;
    private ImageView mImageView_ForthDay;
    private ImageView mTextView_img_clear;
    private View view = null;


    public Fragment_Weather() {
        // Required empty public constructor
    }

    public static Fragment_Weather newInstance(String param1, String param2) {
        Fragment_Weather fragment = new Fragment_Weather();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {

            view = inflater.inflate(R.layout.activity_weather, container, false);
            mCardView_Widget = (CardView) view.findViewById(R.id.activity_know_cv_widget);
            mCardView_WidgetForcast = (CardView) view.findViewById(R.id.activity_know_cv_widget_forcast);
            mLinearLayout_WidgetForcast = (LinearLayout) view.findViewById(R.id.activity_know_ll_widget_forcast);


            mImageView_Today = (ImageView) view.findViewById(R.id.activity_know_img_today_icon);
            mImageView_FirstDay = (ImageView) view.findViewById(R.id.activity_know_img_firstday_icon);
            mImageView_SecondDay = (ImageView) view.findViewById(R.id.activity_know_img_secondday_icon);
            mImageView_ThirdDay = (ImageView) view.findViewById(R.id.activity_know_img_thirdday_icon);
            mImageView_ForthDay = (ImageView) view.findViewById(R.id.activity_know_img_forthday_icon);

            progress = new ProgressDialog(getContext(), R.style.MyProgressTheme);
            progress.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
            progress.setCancelable(true);

            Dialog_City = new Dialog(getContext());
            Dialog_City.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Dialog_City.setContentView(R.layout.dialog_country);
            Dialog_City.setCancelable(true);
            mTextView_CityName = (TextView) view.findViewById(R.id.activity_know_txt_city_name);

            final ArrayList<String> resList = new ArrayList<String>();
            final String searchString = "ازمیر";

//        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final AppCompatAutoCompleteTextView mDialogAutoCompleteTextView = (AppCompatAutoCompleteTextView) getActivity().findViewById(R.id.toolbar_main_txt_header1);
        ArrayAdapter<String> mAdapter_CountryAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_autocomplete, G.ARRAY_CITY);
        mDialogAutoCompleteTextView.setAdapter(mAdapter_CountryAdapter);
        mDialogAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAutoCompleteTextView.setCursorVisible(true);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
        mTextView_img_clear = (ImageView) getActivity().findViewById(R.id.toolbar_main_btn_navigation1);
        mTextView_img_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAutoCompleteTextView.setText("");
            }
        });

        mDialogAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Fragment_Weather.this.isVisible()) {
                    Toast.makeText(getContext(), "weather", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "time", Toast.LENGTH_SHORT).show();

                }

                String mStr_SelectedItem = String.valueOf(parent.getItemAtPosition(position));

                for (String curVal : G.ARRAY_CITY_CODE) {
                    if (curVal.contains(mStr_SelectedItem)) {
                        resList.clear();
                        resList.add(curVal);
                        String itemCheck = resList.get(0);
                        String nameCheck = String.valueOf(itemCheck).substring(String.valueOf(itemCheck).indexOf(":") + 1, String.valueOf(itemCheck).length());

                        if (nameCheck.contains(mStr_SelectedItem)) {
//                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//                            resList.clear();
//                            resList.add(curVal);
                            String item = resList.get(0);
                            String code = String.valueOf(item).substring(String.valueOf(item).indexOf("+") + 1, String.valueOf(item).indexOf(":"));
                            String CITYNAME = String.valueOf(item).substring(String.valueOf(item).indexOf(":") + 1, String.valueOf(item).indexOf("::"));
//                            Toast.makeText(Activity_Weather.this, item, Toast.LENGTH_SHORT).show();
                            new mAsync_CurrentWeatherData().execute("http://dataservice.accuweather.com/currentconditions/v1/" + code + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
                            new mAsync_WeatherData().execute("http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + code + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
                            mTextView_CityName.setText(CITYNAME);
                            mDialogAutoCompleteTextView.clearFocus();
                        }
                    }
                }
            }
        });


//        TextView mTextView_Search = (TextView) findViewById(R.id.toolbar_main_txt_header);
//        ImageView mImageView_Search = (ImageView) findViewById(R.id.toolbar_main_btn_search);
//        mTextView_Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog_City.show();
//            }
//        });
//        mImageView_Search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dialog_City.show();
//            }
//        });

            for (int j = 0; j < G.ARRAY_CITY_CODE.length; j++) {
                Struct struct = new Struct();
                struct.mStr_Forth = String.valueOf(G.ARRAY_CITY_CODE[j]).substring(String.valueOf(G.ARRAY_CITY_CODE[j]).indexOf(":") + 1, String.valueOf(G.ARRAY_CITY_CODE[j]).length());
                struct.mStr_Third = String.valueOf(G.ARRAY_CITY_CODE[j]).substring(0, String.valueOf(G.ARRAY_CITY_CODE[j]).indexOf(":"));
                struct.mStr_Second = "empty";
                struct.mStr_First = "empty";
                sArray_Struct.add(struct);
            }

            RecyclerView mDialogRecyclerView_country = (RecyclerView) Dialog_City.findViewById(R.id.dialog_country_rv_country);
            Adapter_Recycler sDialogAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, sArray_Struct, new OnItemListener() {


                @Override
                public void onItemSelect(int position) {
//                name = mArray_Struct.get(position).mStr_Third;
//                Directions.checkActivity(name);
                }

                @Override
                public void onItemClick(int position) {

                    mStr_CityName = sArray_Struct.get(position).mStr_Forth;
                    mStr_CityCode = sArray_Struct.get(position).mStr_Third;
                    Dialog_City.dismiss();
                    new mAsync_CurrentWeatherData().execute("http://dataservice.accuweather.com/currentconditions/v1/" + mStr_CityCode + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
                    new mAsync_WeatherData().execute("http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + mStr_CityCode + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
                    mTextView_CityName.setText(mStr_CityName);

                }
            }, 4, false);
            mDialogRecyclerView_country.setAdapter(sDialogAdapter_Adapter);
            RecyclerView.LayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
            mDialogRecyclerView_country.setLayoutManager(mLinearLayoutManager);

            new mAsync_CurrentWeatherData().execute("http://dataservice.accuweather.com/currentconditions/v1/" + "210841" + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
            new mAsync_WeatherData().execute("http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + "210841" + "?apikey=moJDkN5ehXPxKeklLINgx89qc4ONLzlD");
            mTextView_CityName.setText("تهران");
            TextView mTextView_PageHeader = (TextView) view.findViewById(R.id.toolbar_main_txt_header);

            mImageView_Sun = (ImageView) view.findViewById(R.id.activity_know_img_sun);
            mImageView_FirstObject = (ImageView) view.findViewById(R.id.activity_know_img_first_object);
            mImageView_SecondObject = (ImageView) view.findViewById(R.id.activity_know_img_second_object);
            mImageView_Main = (ImageView) view.findViewById(R.id.activity_know_img_main);
            mImageView_Cover = (ImageView) view.findViewById(R.id.activity_know_img_cover);
            mLinearLayout_Root = (LinearLayout) view.findViewById(R.id.activity_know_ll_root);

            mTextView_WeatherType = (TextView) view.findViewById(R.id.activity_know_txt_weather_type);
            mTextView_Temperature = (TextView) view.findViewById(R.id.activity_know_txt_temperature);
            mTextView_Time = (TextView) view.findViewById(R.id.activity_know_txt_time);
            mTextView_FirstDay_min = (TextView) view.findViewById(R.id.activity_know_txt_first_day_min);
            mTextView_SecondDay_min = (TextView) view.findViewById(R.id.activity_know_txt_second_day_min);
            mTextView_ThirdDay_min = (TextView) view.findViewById(R.id.activity_know_txt_third_day_min);
            mTextView_ForthDay_min = (TextView) view.findViewById(R.id.activity_know_txt_forth_day_min);
            mTextView_FifthDay_min = (TextView) view.findViewById(R.id.activity_know_txt_fifth_day_min);

            TextView mTextView_FirstDay = (TextView) view.findViewById(R.id.activity_know_txt_first_day);
            TextView mTextView_SecondDay = (TextView) view.findViewById(R.id.activity_know_txt_second_day);
            TextView mTextView_ThirdDay = (TextView) view.findViewById(R.id.activity_know_txt_third_day);
            TextView mTextView_ForthDay = (TextView) view.findViewById(R.id.activity_know_txt_forth_day);

            Calendar sCalendar = Calendar.getInstance();
            String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            Log.d("day", dayLongName);
            switch (dayLongName) {
                case "Saturday":
                    mTextView_FirstDay.setText("یکشنبه");
                    mTextView_SecondDay.setText("دوشنبه");
                    mTextView_ThirdDay.setText("سه شنبه");
                    mTextView_ForthDay.setText("چهارشنبه");
                    break;
                case "Sunday":
                    mTextView_FirstDay.setText("دوشنبه");
                    mTextView_SecondDay.setText("سه شنبه");
                    mTextView_ThirdDay.setText("چهارشنبه");
                    mTextView_ForthDay.setText("پنج شنبه");
                    break;

                case "Monday":
                    mTextView_FirstDay.setText("سه شنبه");
                    mTextView_SecondDay.setText("چهارشنبه");
                    mTextView_ThirdDay.setText("پنج شنبه");
                    mTextView_ForthDay.setText("جمعه");
                    break;

                case "Tuesday":
                    mTextView_FirstDay.setText("چهارشنبه");
                    mTextView_SecondDay.setText("پنج شنبه");
                    mTextView_ThirdDay.setText("جمعه");
                    mTextView_ForthDay.setText("شنبه");
                    break;

                case "Wednesday":
                    mTextView_FirstDay.setText("پنج شنبه");
                    mTextView_SecondDay.setText("جمعه");
                    mTextView_ThirdDay.setText("شنبه");
                    mTextView_ForthDay.setText("یک شنبه");
                    break;

                case "Thursday":
                    mTextView_FirstDay.setText("جمعه");
                    mTextView_SecondDay.setText("شنبه");
                    mTextView_ThirdDay.setText("یک شنبه");
                    mTextView_ForthDay.setText("دوشنبه");
                    break;

                case "Friday":
                    mTextView_FirstDay.setText("شنبه");
                    mTextView_SecondDay.setText("یکشنبه");
                    mTextView_ThirdDay.setText("دوشنبه");
                    mTextView_ForthDay.setText("سه شنبه");
                    break;

            }

//        mNavigationDrawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//        NavigationView mNavigationView = (NavigationView) view.findViewById(R.id.activity_know_nv_menu);
//        mNavigationView.setNavigationItemSelectedListener(getContext());
//
//        ImageView mButton_Navigation = (ImageView) findViewById(R.id.toolbar_main_btn_navigation);
//        mButton_Navigation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialogAutoCompleteTextView.setText("");
//            }
//        });
//        Fragment squadFragment = new Fragment_Profile();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.activity_know_nv_menu, squadFragment, null);
//        fragmentTransaction.commit();
        } else {
//            ((ViewGroup) view.getParent()).removeView(view);
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

    private void NightRain() {
        mLinearLayout_WidgetForcast.setBackgroundColor(Color.parseColor("#ffffff"));
        mImageView_Main.setImageResource(R.drawable.night_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.night_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.light_rain);
        mImageView_SecondObject.setImageResource(R.drawable.light_rain);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float Height = mImageView_FirstObject.getHeight();
                final float translationY = Height * progress;
                mImageView_FirstObject.setTranslationX(0);
                mImageView_SecondObject.setTranslationX(0);
                mImageView_FirstObject.setTranslationY(translationY);
                mImageView_SecondObject.setTranslationY(translationY - Height);
            }
        });
        animator.start();
    }

    private void NightClear() {
        mImageView_Main.setImageResource(R.drawable.night_clear_main);
        mImageView_Cover.setImageResource(R.drawable.night_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
//        mImageView_FirstObject.setImageResource(R.drawable.cloud1);
//        mImageView_SecondObject.setImageResource(R.drawable.cloud1);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void NightCloud() {
        mImageView_Main.setImageResource(R.drawable.night_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.night_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.cloud1);
        mImageView_SecondObject.setImageResource(R.drawable.cloud1);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void NightFog() {
        mImageView_Main.setImageResource(R.drawable.night_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.night_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.fog);
        mImageView_SecondObject.setImageResource(R.drawable.fog);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void NightSnow() {
        mImageView_Main.setImageResource(R.drawable.night_snow_main);
        mImageView_Cover.setImageResource(R.drawable.night_snow);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.snow);
        mImageView_SecondObject.setImageResource(R.drawable.snow);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float Height = mImageView_FirstObject.getHeight();
                final float translationY = Height * progress;
                mImageView_FirstObject.setTranslationX(0);
                mImageView_SecondObject.setTranslationX(0);
                mImageView_FirstObject.setTranslationY(translationY);
                mImageView_SecondObject.setTranslationY(translationY - Height);
            }
        });
        animator.start();
    }

    private void DayRain() {
        mImageView_Main.setImageResource(R.drawable.day_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.day_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.light_rain);
        mImageView_SecondObject.setImageResource(R.drawable.light_rain);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float Height = mImageView_FirstObject.getHeight();
                final float translationY = Height * progress;
                mImageView_FirstObject.setTranslationX(0);
                mImageView_SecondObject.setTranslationX(0);
                mImageView_FirstObject.setTranslationY(translationY);
                mImageView_SecondObject.setTranslationY(translationY - Height);
            }
        });
        animator.start();
    }

    private void DayClear() {
        mImageView_Main.setImageResource(R.drawable.day_clear_main);
        mImageView_Cover.setImageResource(R.drawable.day_clear);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);


//        mImageView_FirstObject.setImageResource(R.drawable.cloud1);
//        mImageView_SecondObject.setImageResource(R.drawable.cloud1);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();


    }

    private void DayCloud() {
        mImageView_Main.setImageResource(R.drawable.day_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.day_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.cloud1);
        mImageView_SecondObject.setImageResource(R.drawable.cloud1);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void DayPartlySun() {
        mImageView_Main.setImageResource(R.drawable.day_clear_main);
        mImageView_Cover.setImageResource(R.drawable.day_clear);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.cloud1);
        mImageView_SecondObject.setImageResource(R.drawable.cloud1);
        mImageView_FirstObject.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_white));
        mImageView_SecondObject.setColorFilter(ContextCompat.getColor(getContext(), R.color.color_white));

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();
    }

    private void DayFog() {
        mImageView_Main.setImageResource(R.drawable.day_cloud_main);
        mImageView_Cover.setImageResource(R.drawable.day_cloud);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.fog);
        mImageView_SecondObject.setImageResource(R.drawable.fog);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(50000L);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = mImageView_FirstObject.getWidth();
                final float translationX = width * progress;
                mImageView_FirstObject.setTranslationX(translationX);
                mImageView_SecondObject.setTranslationX(translationX - width);
                mImageView_FirstObject.setTranslationY(0);
                mImageView_SecondObject.setTranslationY(0);
            }
        });
        animator.start();


    }

    private void DaySnow() {
        mImageView_Main.setImageResource(R.drawable.day_snow_main);
        mImageView_Cover.setImageResource(R.drawable.day_snow);
        mImageView_FirstObject.setImageResource(0);
        mImageView_SecondObject.setImageResource(0);
        mImageView_FirstObject.setImageResource(R.drawable.snow);
        mImageView_SecondObject.setImageResource(R.drawable.snow);

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float Height = mImageView_FirstObject.getHeight();
                final float translationY = Height * progress;
                mImageView_FirstObject.setTranslationX(0);
                mImageView_SecondObject.setTranslationX(0);
                mImageView_FirstObject.setTranslationY(translationY);
                mImageView_SecondObject.setTranslationY(translationY - Height);
            }
        });
        animator.start();

    }

    private String TemperatureConvertor(String FarenhaitTemp) {

        int mInt_FarenhaitTemp = Integer.parseInt(String.valueOf(FarenhaitTemp).substring(0, String.valueOf(FarenhaitTemp).length() - 2));
        int mInt_CelciusTemp = ((mInt_FarenhaitTemp - 32) * 5) / 9;
        String mStr_CelciusTemp = String.valueOf(mInt_CelciusTemp) + "°";
        return mStr_CelciusTemp;
    }

    private void weatherIconSelector(String weatherCondition, ImageView icon) {
        if (weatherCondition.equals("Showers") || weatherCondition.equals("Rain and snow")
                || weatherCondition.equals("Partly sunny w/ showers") || weatherCondition.equals("Mostly cloudy w/ showers")
                || weatherCondition.equals("Partly sunny w/ t-storms") || weatherCondition.equals("Thunderstorms")
                || weatherCondition.equals("Mostly cloudy w/ t-storms") || weatherCondition.equals("Flurries") || weatherCondition.equals("Rain")) {
            icon.setImageResource(R.drawable.icon_rain);

        } else if (weatherCondition.equals("Intermittent clouds") || weatherCondition.equals("Mostly cloudy")
                || weatherCondition.equals("Fog") || weatherCondition.equals("Dreary") || weatherCondition.equals("Cloudy")) {
            icon.setImageResource(R.drawable.icon_cloud);

        } else if (weatherCondition.equals("Partly sunny") || weatherCondition.equals("Mostly sunny")
                || weatherCondition.equals("Sunny") || weatherCondition.equals("Overcast")
                || weatherCondition.equals("Hazy sunshine")
                || weatherCondition.equals("Clear") || weatherCondition.equals("Mostly clear")) {
            icon.setImageResource(R.drawable.icon_sun);
        } else {
            Log.d("weatherText", weatherCondition);
            icon.setImageResource(0);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class mAsync_WeatherData extends Webservice.GetClass {

        @Override
        protected void onPreExecute() {
            Animation s = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_loading);
            s.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCardView_WidgetForcast.setAlpha(0.4f);
                }
            });
            mCardView_WidgetForcast.startAnimation(s);
        }

        @Override
        protected void onPostExecute(String result) {
            Animation s = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_loading);
            s.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCardView_WidgetForcast.setAlpha(1);
                }
            });
            mCardView_WidgetForcast.startAnimation(s);
            try {


                JSONObject mJsonObject_WeatherData = new JSONObject(result);
                JSONArray mJsonArray_WeatherData = mJsonObject_WeatherData.getJSONArray("DailyForecasts");
                JSONObject mJsonObject_FirstDay = mJsonArray_WeatherData.getJSONObject(0);
                JSONObject mJsonObject_FirstDay_Temperature = mJsonObject_FirstDay.getJSONObject("Temperature");
                JSONObject mJsonObject_FirstDay_Temperature_min = mJsonObject_FirstDay_Temperature.getJSONObject("Minimum");
                JSONObject mJsonObject_FirstDay_Temperature_max = mJsonObject_FirstDay_Temperature.getJSONObject("Maximum");
                String mStr_FirstDay_Date = mJsonObject_FirstDay.getString("Date");
                String mStr_FirstDay_Temperature_min_value = mJsonObject_FirstDay_Temperature_min.getString("Value");
                String mStr_FirstDay_Temperature_max_value = mJsonObject_FirstDay_Temperature_max.getString("Value");
                JSONObject mJsonObject_FirstDay_Condition = mJsonObject_FirstDay.getJSONObject("Day");
                String mStr_FirstDay_Condition_Identifier = mJsonObject_FirstDay_Condition.getString("IconPhrase");

                mTextView_FirstDay_min.setText(TemperatureConvertor(mStr_FirstDay_Temperature_min_value) + " / " + TemperatureConvertor(mStr_FirstDay_Temperature_max_value));
                weatherIconSelector(mStr_FirstDay_Condition_Identifier, mImageView_Today);

                JSONObject mJsonObject_SecondDay = mJsonArray_WeatherData.getJSONObject(1);
                JSONObject mJsonObject_SecondDay_Temperature = mJsonObject_SecondDay.getJSONObject("Temperature");
                JSONObject mJsonObject_SecondDay_Temperature_min = mJsonObject_SecondDay_Temperature.getJSONObject("Minimum");
                JSONObject mJsonObject_SecondDay_Temperature_max = mJsonObject_SecondDay_Temperature.getJSONObject("Maximum");
                String mStr_SecondDay_Date = mJsonObject_SecondDay.getString("Date");
                String mStr_SecondDay_Temperature_min_value = mJsonObject_SecondDay_Temperature_min.getString("Value");
                String mStr_SecondDay_Temperature_max_value = mJsonObject_SecondDay_Temperature_max.getString("Value");
                JSONObject mJsonObject_SecondDay_Condition = mJsonObject_SecondDay.getJSONObject("Day");
                String mStr_SecondDay_Condition_Identifier = mJsonObject_SecondDay_Condition.getString("IconPhrase");

                mTextView_SecondDay_min.setText(TemperatureConvertor(mStr_SecondDay_Temperature_min_value) + " / " + TemperatureConvertor(mStr_SecondDay_Temperature_max_value));
                weatherIconSelector(mStr_SecondDay_Condition_Identifier, mImageView_FirstDay);

                JSONObject mJsonObject_ThirdDay = mJsonArray_WeatherData.getJSONObject(2);
                JSONObject mJsonObject_ThirdDay_Temperature = mJsonObject_ThirdDay.getJSONObject("Temperature");
                JSONObject mJsonObject_ThirdDay_Temperature_min = mJsonObject_ThirdDay_Temperature.getJSONObject("Minimum");
                JSONObject mJsonObject_ThirdDay_Temperature_max = mJsonObject_ThirdDay_Temperature.getJSONObject("Maximum");
                String mStr_ThirdDay_Date = mJsonObject_ThirdDay.getString("Date");
                String mStr_ThirdDay_Temperature_min_value = mJsonObject_ThirdDay_Temperature_min.getString("Value");
                String mStr_ThirdDay_Temperature_max_value = mJsonObject_ThirdDay_Temperature_max.getString("Value");
                JSONObject mJsonObject_ThirdDay_Condition = mJsonObject_ThirdDay.getJSONObject("Day");
                String mStr_ThirdDay_Condition_Identifier = mJsonObject_ThirdDay_Condition.getString("IconPhrase");

                mTextView_ThirdDay_min.setText(TemperatureConvertor(mStr_ThirdDay_Temperature_min_value) + " / " + TemperatureConvertor(mStr_ThirdDay_Temperature_max_value));
                weatherIconSelector(mStr_ThirdDay_Condition_Identifier, mImageView_SecondDay);

                JSONObject mJsonObject_ForthDay = mJsonArray_WeatherData.getJSONObject(3);
                JSONObject mJsonObject_ForthDay_Temperature = mJsonObject_ForthDay.getJSONObject("Temperature");
                JSONObject mJsonObject_ForthDay_Temperature_min = mJsonObject_ForthDay_Temperature.getJSONObject("Minimum");
                JSONObject mJsonObject_ForthDay_Temperature_max = mJsonObject_ForthDay_Temperature.getJSONObject("Maximum");
                String mStr_ForthDay_Date = mJsonObject_ForthDay.getString("Date");
                String mStr_ForthDay_Temperature_min_value = mJsonObject_ForthDay_Temperature_min.getString("Value");
                String mStr_ForthDay_Temperature_max_value = mJsonObject_ForthDay_Temperature_max.getString("Value");
                JSONObject mJsonObject_ForthDay_Condition = mJsonObject_ForthDay.getJSONObject("Day");
                String mStr_ForthDay_Condition_Identifier = mJsonObject_ForthDay_Condition.getString("IconPhrase");

                mTextView_ForthDay_min.setText(TemperatureConvertor(mStr_ForthDay_Temperature_min_value) + " / " + TemperatureConvertor(mStr_ForthDay_Temperature_max_value));
                weatherIconSelector(mStr_ForthDay_Condition_Identifier, mImageView_ThirdDay);

                JSONObject mJsonObject_FifthDay = mJsonArray_WeatherData.getJSONObject(4);
                JSONObject mJsonObject_FifthDay_Temperature = mJsonObject_FifthDay.getJSONObject("Temperature");
                JSONObject mJsonObject_FifthDay_Temperature_min = mJsonObject_FifthDay_Temperature.getJSONObject("Minimum");
                JSONObject mJsonObject_FifthDay_Temperature_max = mJsonObject_FifthDay_Temperature.getJSONObject("Maximum");
                String mStr_FifthDay_Date = mJsonObject_FifthDay.getString("Date");
                String mStr_FifthDay_Temperature_min_value = mJsonObject_FifthDay_Temperature_min.getString("Value");
                String mStr_FifthDay_Temperature_max_value = mJsonObject_FifthDay_Temperature_max.getString("Value");
                JSONObject mJsonObject_FifthDay_Condition = mJsonObject_FifthDay.getJSONObject("Day");
                String mStr_FifthDay_Condition_Identifier = mJsonObject_FifthDay_Condition.getString("IconPhrase");

                mTextView_FifthDay_min.setText(TemperatureConvertor(mStr_FifthDay_Temperature_min_value) + " / " + TemperatureConvertor(mStr_FifthDay_Temperature_max_value));
                weatherIconSelector(mStr_FifthDay_Condition_Identifier, mImageView_ForthDay);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class mAsync_CurrentWeatherData extends Webservice.SecondGetClass {

        @Override
        protected void onPreExecute() {

            Animation s = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_loading);
            s.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCardView_Widget.setAlpha(0.4f);
                }
            });
            mCardView_Widget.startAnimation(s);
            progress.show();
        }

        @Override
        protected void onPostExecute(String result) {
            Animation s = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_loading);
            s.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCardView_Widget.setAlpha(1);
                }
            });
            mCardView_Widget.startAnimation(s);
            progress.dismiss();
            try {
                JSONArray mJsonArray_CurrentWeather = new JSONArray(result);
                JSONObject mJsonObject_CurrentWeather = mJsonArray_CurrentWeather.getJSONObject(0);
                String mStr_ConditionType = mJsonObject_CurrentWeather.getString("WeatherText");
                String mStr_Date_and_Time = mJsonObject_CurrentWeather.getString("LocalObservationDateTime");
                Boolean mBoolean_ConditionType = mJsonObject_CurrentWeather.getBoolean("IsDayTime");

                Log.d("mohammad", mStr_Date_and_Time);
                try {
                    String mStr_Time = String.valueOf(mStr_Date_and_Time).substring(String.valueOf(mStr_Date_and_Time).indexOf("T") + 1, String.valueOf(mStr_Date_and_Time).length() - 9);
                    if (mBoolean_ConditionType) {
                        mTextView_Time.setText(mStr_Time);
                    } else {
                        mTextView_Time.setText(mStr_Time);
                    }
                } catch (Exception e) {
                }

                JSONObject mJsonObject_CurrentWeatherTemperature = mJsonObject_CurrentWeather.getJSONObject("Temperature");
                JSONObject mJsonObject_CurrentWeatherTemperature_Metric = mJsonObject_CurrentWeatherTemperature.getJSONObject("Metric");
                String mStr_CurrentTemperature = mJsonObject_CurrentWeatherTemperature_Metric.getString("Value");

                mTextView_Temperature.setText(mStr_CurrentTemperature + "°");
                mTextView_WeatherType.setText(mStr_ConditionType);

                if (mStr_ConditionType.equals("Clouds and sun") || mStr_ConditionType.equals("Partly sunny")) {
                    if (mBoolean_ConditionType) {
                        mImageView_Sun.setVisibility(View.VISIBLE);
                        DayPartlySun();
                    } else {
                        mImageView_Sun.setVisibility(View.GONE);
                        NightClear();
                    }
                }
                if (mStr_ConditionType.equals("Sunny") || mStr_ConditionType.equals("Clear")) {
                    if (mBoolean_ConditionType) {
                        mImageView_Sun.setVisibility(View.VISIBLE);
                        DayClear();
                    } else {
                        mImageView_Sun.setVisibility(View.GONE);
                        NightClear();
                    }
                }
                if (mStr_ConditionType.equals("Mostly cloudy") || mStr_ConditionType.equals("Partly cloudy") || mStr_ConditionType.equals("Cloudy") || mStr_ConditionType.equals("Overcast")) {
                    mImageView_Sun.setVisibility(View.GONE);
                    if (mBoolean_ConditionType) {
                        DayCloud();
                    } else {
                        NightCloud();
                    }
                }
                if (mStr_ConditionType.equals("Light rain") || mStr_ConditionType.equals("Rain") || mStr_ConditionType.equals("A shower") || mStr_ConditionType.equals("Thunder and rain") || mStr_ConditionType.equals("Thunderstorm") || mStr_ConditionType.equals("Rain shower") || mStr_ConditionType.equals("Light rain shower")) {
                    mImageView_Sun.setVisibility(View.GONE);
                    if (mBoolean_ConditionType) {
                        DayRain();
                    } else {
                        NightRain();
                    }
                }
                if (mStr_ConditionType.equals("Snow") || mStr_ConditionType.equals("Light snow") || mStr_ConditionType.equals("Heavy snow") || mStr_ConditionType.equals("Light snow and fog") || mStr_ConditionType.equals("Light snow shower")) {
                    mImageView_Sun.setVisibility(View.GONE);
                    if (mBoolean_ConditionType) {
                        DaySnow();
                    } else {
                        NightSnow();
                    }
                }
                if (mStr_ConditionType.equals("Foggy") || mStr_ConditionType.equals("Light fog") || mStr_ConditionType.equals("Dense fog")) {
                    mImageView_Sun.setVisibility(View.GONE);
                    if (mBoolean_ConditionType) {
                        DayFog();
                    } else {
                        NightFog();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

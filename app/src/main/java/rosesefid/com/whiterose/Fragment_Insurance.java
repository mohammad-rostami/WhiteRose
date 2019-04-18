package rosesefid.com.whiterose;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class Fragment_Insurance extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static String STR_SELECTED_DATE = "";
    public static TextView TEXTVIEW_DATE;
    static Adapter_Recycler sDialogAdapter_Adapter;
    static ArrayList<Struct> sArray_Struct = new ArrayList<>();
    static ArrayList sArray_Countries = new ArrayList();
    private OnFragmentInteractionListener mListener;
    private Dialog Dialog_Country;
    private String mStr_CountryName = "";
    private String mStr_CountryEnglish = "";
    private Dialog Dialog_Number;
    private int mInt_Box;
    private EditText mEditText_DayNumber;
    private String mStr_CodeSaman;
    private String mStr_CodePasargad;
    private LinearLayout mLinearLayout_Country;
    private LinearLayout mLinearLayout_Date;
    private RelativeLayout mRelativeLayout_Saman;
    private RelativeLayout mRelativeLayout_Pasargad;
    private LinearLayout mLinearLayout_Saman;
    private LinearLayout mLinearLayout_Pasargad;
    private LinearLayout mLinearLayout_Line_Saman;
    private LinearLayout mLinearLayout_Line_Pasargad;
    private LinearLayout mLinearLayout_Form;
    private TextView mTextView_Country;
    private TextView mTextView_Saman;
    private TextView mTextView_Pasargad;
    private EditText mEditText_PassengerNumber;
    private String mStr_SolarDate;

    public Fragment_Insurance() {
        // Required empty public constructor
    }


    public static Fragment_Insurance newInstance(String param1, String param2) {
        Fragment_Insurance fragment = new Fragment_Insurance();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static void ScanSoapObject(SoapObject result) {
        for (int i = 0; i < result.getPropertyCount(); i++) {
            if (result.getProperty(i) instanceof SoapObject) {
                ScanSoapObject((SoapObject) result.getProperty(i));

            } else {
                //do something with the current property
                //get the current property name:
                PropertyInfo pi = new PropertyInfo();
                PropertyInfo pix = new PropertyInfo();
                if (i == 3) {
                    result.getPropertyInfo(3, pi);
                    result.getPropertyInfo(2, pix);
                    String name = String.valueOf(pi.getValue());
                    String namex = String.valueOf(pix.getValue());
                    Log.d("country Code", name + " " + namex);
                    sArray_Countries.add(name + ":" + namex);
                }
            }
        }
        sArray_Struct.clear();
//        mRecyclerAdapter_Adapter.notifyDataSetChanged();
//        for (int j = 0; j < sArray_Countries.size(); j++) {
//            Struct struct = new Struct();
//            struct.mStr_First = String.valueOf(sArray_Countries.get(j)).substring(0, String.valueOf(sArray_Countries.get(j)).indexOf(":"));
//            struct.mStr_Second = String.valueOf(sArray_Countries.get(j)).substring(String.valueOf(sArray_Countries.get(j)).indexOf(":") + 1, String.valueOf(sArray_Countries.get(j)).length());
//
//            mArray_Struct.add(struct);
//        }
//        mRecyclerAdapter_Adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Dialog_Country = new Dialog(getContext());
        Dialog_Country.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Country.setContentView(R.layout.dialog_country);
        Dialog_Country.setCancelable(true);

        Dialog_Number = new Dialog(getContext());
        Dialog_Number.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Number.setContentView(R.layout.dialog_number);
        Dialog_Number.setCancelable(true);

        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

        for (int j = 0; j < G.ARRAY_COUNTRY.length; j++) {
            Struct struct = new Struct();
            struct.mStr_First = String.valueOf(G.ARRAY_COUNTRY[j]).substring(0, String.valueOf(G.ARRAY_COUNTRY[j]).indexOf(":"));
            struct.mStr_Second = String.valueOf(G.ARRAY_COUNTRY[j]).substring(String.valueOf(G.ARRAY_COUNTRY[j]).indexOf(":") + 1, String.valueOf(G.ARRAY_COUNTRY[j]).indexOf("/"));
            struct.mStr_Third = String.valueOf(G.ARRAY_COUNTRY[j]).substring(String.valueOf(G.ARRAY_COUNTRY[j]).indexOf("/") + 1, String.valueOf(G.ARRAY_COUNTRY[j]).indexOf("_"));
            struct.mStr_Forth = String.valueOf(G.ARRAY_COUNTRY[j]).substring(String.valueOf(G.ARRAY_COUNTRY[j]).indexOf("_") + 1, String.valueOf(G.ARRAY_COUNTRY[j]).length());
            sArray_Struct.add(struct);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insurance, container, false);
        mLinearLayout_Form = (LinearLayout) view.findViewById(R.id.fragment_insurance_ll_insurance_form);

        mLinearLayout_Country = (LinearLayout) view.findViewById(R.id.fragment_insurance_ll_country);
        mLinearLayout_Date = (LinearLayout) view.findViewById(R.id.fragment_insurance_ll_date);
        mLinearLayout_Saman = (LinearLayout) view.findViewById(R.id.fragment_insurance_ll_saman);
        mLinearLayout_Pasargad = (LinearLayout) view.findViewById(R.id.fragment_insurance_ll_pasargad);

        final CardView mCardView_Saman = (CardView) view.findViewById(R.id.fragment_insurance_cv_saman);
        CardView mCardView_Pasargad = (CardView) view.findViewById(R.id.fragment_insurance_cv_pasargad);


        mTextView_Country = (TextView) view.findViewById(R.id.fragment_insurance_et_country);
        TEXTVIEW_DATE = (TextView) view.findViewById(R.id.fragment_insurance_et_date);
        mTextView_Saman = (TextView) view.findViewById(R.id.fragment_insurance_txt_saman);
        mTextView_Pasargad = (TextView) view.findViewById(R.id.fragment_insurance_txt_pasargad);

        TextInputLayout mTextInputLayout_DayNumber = (TextInputLayout) view.findViewById(R.id.fragment_insurance_til_day_number);
        TextInputLayout mTextInputLayout_PassengerNumber = (TextInputLayout) view.findViewById(R.id.fragment_insurance_til_passenger_number);
        mEditText_DayNumber = (EditText) view.findViewById(R.id.fragment_insurance_et_day_number);
        mEditText_PassengerNumber = (EditText) view.findViewById(R.id.fragment_insurance_et_passenger_number);

        final RadioButton mImageView_Saman = (RadioButton) view.findViewById(R.id.fragment_insurance_img_saman);
        final RadioButton mImageView_Pasargad = (RadioButton) view.findViewById(R.id.fragment_insurance_img_pasargad);

        mStr_SolarDate = PersianDate.todayShamsi();
        TEXTVIEW_DATE.setText(mStr_SolarDate);
        STR_SELECTED_DATE = mStr_SolarDate;

        TextView mDialogTextView_Confirm = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_confirm);
        TextView mDialogTextView_Cancel = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_cancel);
        AutoCompleteTextView mDialogAutoCompleteTextView = (AutoCompleteTextView) Dialog_Country.findViewById(R.id.dialog_country_act_autocomplete);
        ArrayAdapter<String> mAdapter_CountryAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_autocomplete, G.ARRAY_CITY);
        mDialogAutoCompleteTextView.setAdapter(mAdapter_CountryAdapter);

        final TextView mDialogTextView_Number = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_number);
        final NumberPicker mDialogNumberPicker_Picker = (NumberPicker) Dialog_Number.findViewById(R.id.dialog_number_np_picker);
        mDialogNumberPicker_Picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mDialogNumberPicker_Picker.setMinValue(1);
        mDialogNumberPicker_Picker.setMaxValue(12);
        mDialogNumberPicker_Picker.setWrapSelectorWheel(true);
        mDialogNumberPicker_Picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mDialogTextView_Number.setText(String.valueOf(newVal));
            }
        });

        mDialogTextView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInt_Box == 1) {
                    mEditText_DayNumber.setText(mDialogTextView_Number.getText().toString());
                }
                if (mInt_Box == 2) {
                    mEditText_PassengerNumber.setText(mDialogTextView_Number.getText().toString());
                }
                Dialog_Number.dismiss();
            }
        });
        mDialogTextView_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Number.dismiss();
            }
        });


        mLinearLayout_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Country.show();
            }
        });
        mTextView_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Country.show();
            }
        });
        mLinearLayout_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "insurance";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        TEXTVIEW_DATE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "insurance";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mTextInputLayout_DayNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInt_Box = 1;
                mDialogNumberPicker_Picker.setValue(Integer.parseInt(mEditText_DayNumber.getText().toString()));
                mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_Picker.getValue()));
                Dialog_Number.show();
            }
        });
        mTextInputLayout_PassengerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInt_Box = 2;
                mDialogNumberPicker_Picker.setValue(Integer.parseInt(mEditText_PassengerNumber.getText().toString()));
                mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_Picker.getValue()));
                Dialog_Number.show();
            }
        });
        mEditText_DayNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInt_Box = 1;
                mDialogNumberPicker_Picker.setValue(Integer.parseInt(mEditText_DayNumber.getText().toString()));
                mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_Picker.getValue()));
                Dialog_Number.show();
            }
        });
        mEditText_PassengerNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInt_Box = 2;
                mDialogNumberPicker_Picker.setValue(Integer.parseInt(mEditText_PassengerNumber.getText().toString()));
                mDialogTextView_Number.setText(String.valueOf(mDialogNumberPicker_Picker.getValue()));
                Dialog_Number.show();
            }
        });

        mLinearLayout_Saman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStr_CountryName.toString().equals("") && STR_SELECTED_DATE.toString() != "") {
                    mLinearLayout_Country.setBackgroundResource(R.drawable.view_warning);
                    mTextView_Country.setHintTextColor(Color.parseColor("#FF4040"));
                    mTextView_Country.setHint("کشور مقصد را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Country.setBackgroundResource(R.drawable.view_layout);
                            mTextView_Country.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Country.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);

                } else if (mStr_CountryName.toString() != "" && STR_SELECTED_DATE.toString() == "") {
                    mLinearLayout_Date.setBackgroundResource(R.drawable.view_warning);
                    TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#FF4040"));
                    TEXTVIEW_DATE.setHint("تاریخ شروع سفر را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Date.setBackgroundResource(R.drawable.view_layout);
                            TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Date.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);

                } else if (mStr_CountryName.toString() == "" && STR_SELECTED_DATE.toString() == "") {
                    mLinearLayout_Country.setBackgroundResource(R.drawable.view_warning);
                    mTextView_Country.setHintTextColor(Color.parseColor("#FF4040"));
                    mTextView_Country.setHint("کشور مقصد را انتخاب کنید");
                    mLinearLayout_Date.setBackgroundResource(R.drawable.view_warning);
                    TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#FF4040"));
                    TEXTVIEW_DATE.setHint("تاریخ شروع سفر را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            mLinearLayout_Country.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.warning));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Country.setBackgroundResource(R.drawable.view_layout);
                            mTextView_Country.setHintTextColor(Color.parseColor("#707070"));
                            mLinearLayout_Date.setBackgroundResource(R.drawable.view_layout);
                            TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Date.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);
                } else {
                    String duration = mEditText_DayNumber.getText().toString();
                    new mAsyncSaman().execute("http://samanservice.ir/TravisService.asmx", "ws@ros", "sefid#208", mStr_CodeSaman, "1984/12/2", duration, "getPlansWithDetail");
                }
            }
        });
        mLinearLayout_Pasargad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStr_CountryName.toString().equals("") && STR_SELECTED_DATE.toString() != "") {
                    mLinearLayout_Country.setBackgroundResource(R.drawable.view_warning);
                    mTextView_Country.setHintTextColor(Color.parseColor("#FF4040"));
                    mTextView_Country.setHint("کشور مقصد را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Country.setBackgroundResource(R.drawable.view_layout);
                            mTextView_Country.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Country.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);

                } else if (mStr_CountryName.toString() != "" && STR_SELECTED_DATE.toString() == "") {
                    mLinearLayout_Date.setBackgroundResource(R.drawable.view_warning);
                    TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#FF4040"));
                    TEXTVIEW_DATE.setHint("تاریخ شروع سفر را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Date.setBackgroundResource(R.drawable.view_layout);
                            TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Date.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);

                } else if (mStr_CountryName.toString() == "" && STR_SELECTED_DATE.toString() == "") {
                    mLinearLayout_Country.setBackgroundResource(R.drawable.view_warning);
                    mTextView_Country.setHintTextColor(Color.parseColor("#FF4040"));
                    mTextView_Country.setHint("کشور مقصد را انتخاب کنید");
                    mLinearLayout_Date.setBackgroundResource(R.drawable.view_warning);
                    TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#FF4040"));
                    TEXTVIEW_DATE.setHint("تاریخ شروع سفر را انتخاب کنید");
                    Animation mAnimation_Warning = AnimationUtils.loadAnimation(getContext(), R.anim.warning);
                    mAnimation_Warning.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            mLinearLayout_Country.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.warning));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mLinearLayout_Country.setBackgroundResource(R.drawable.view_layout);
                            mTextView_Country.setHintTextColor(Color.parseColor("#707070"));
                            mLinearLayout_Date.setBackgroundResource(R.drawable.view_layout);
                            TEXTVIEW_DATE.setHintTextColor(Color.parseColor("#707070"));
                        }
                    });
                    mLinearLayout_Date.startAnimation(mAnimation_Warning);
                    Vibrator mVibrator_Warning = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    mVibrator_Warning.vibrate(100);

                } else {
                    new mAsyncPasargad().execute("http://www.travelinsure.ir/api/authenticate", "info@mahbal.com", "09121021701", "", "", "token");
                }
            }
        });

        RecyclerView mDialogRecyclerView_country = (RecyclerView) Dialog_Country.findViewById(R.id.dialog_country_rv_country);
        sDialogAdapter_Adapter = new Adapter_Recycler(G.CONTEXT, sArray_Struct, new OnItemListener() {

            @Override
            public void onItemSelect(int position) {
//                name = mArray_Struct.get(position).mStr_Third;
//                Directions.checkActivity(name);
            }

            @Override
            public void onItemClick(int position) {

                mStr_CountryName = sArray_Struct.get(position).mStr_Forth;
                mStr_CountryEnglish = sArray_Struct.get(position).mStr_Third;
                mStr_CodePasargad = sArray_Struct.get(position).mStr_Second;
                mStr_CodeSaman = sArray_Struct.get(position).mStr_First;

                if (mStr_CodeSaman.equals("0")) {
                    mTextView_Saman.setTextColor(getResources().getColor(R.color.color_disabled));
                    mLinearLayout_Saman.setBackgroundColor(getResources().getColor(R.color.color_disabled));
                    mLinearLayout_Saman.setEnabled(false);

                    mLinearLayout_Pasargad.setBackgroundColor(getResources().getColor(R.color.second_color));
                    mLinearLayout_Pasargad.setEnabled(true);

                } else if (mStr_CodePasargad.equals("0")) {
                    mTextView_Pasargad.setTextColor(getResources().getColor(R.color.color_disabled));
                    mLinearLayout_Pasargad.setBackgroundColor(getResources().getColor(R.color.color_disabled));
                    mLinearLayout_Pasargad.setEnabled(false);

                    mLinearLayout_Saman.setBackgroundColor(getResources().getColor(R.color.second_color));
                    mLinearLayout_Saman.setEnabled(true);

                } else {
                    mLinearLayout_Saman.setBackgroundColor(getResources().getColor(R.color.second_color));
                    mTextView_Saman.setTextColor(Color.parseColor("#ffffff"));
                    mLinearLayout_Saman.setEnabled(true);
                    mLinearLayout_Pasargad.setBackgroundColor(getResources().getColor(R.color.second_color));
                    mTextView_Pasargad.setTextColor(Color.parseColor("#ffffff"));
                    mLinearLayout_Pasargad.setEnabled(true);
                }

                Dialog_Country.dismiss();
                mTextView_Country.setText(mStr_CountryName);
            }
        }, 4, false);

        mDialogRecyclerView_country.setAdapter(sDialogAdapter_Adapter);
        RecyclerView.LayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mDialogRecyclerView_country.setLayoutManager(mLinearLayoutManager);

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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        TEXTVIEW_DATE.setText(date);
        Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
    }

//    private class mAsyncPasargad extends Webservice_Soap.getData {
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
////            Toast.makeText(getContext(), "Responseiction" + result, Toast.LENGTH_LONG).show();
//            Log.d("output", result);
//            ScanSoapObject(Webservice_Soap.result);
//
////            mRecyclerAdapter_Adapter.notifyDataSetChanged();
//        }
//    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class mAsyncPasargad extends Webservice.PostClassID {

        private String token;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject object = new JSONObject(result);
                token = object.getString("token");
                Log.d("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String duration = mEditText_DayNumber.getText().toString();
            new mAsyncPasargad1().execute("http://www.travelinsure.ir/api/webservice/getprice?tokan=" + token, mStr_CodePasargad, mStr_CountryEnglish, duration, "1980/10/02", "price");
//            mRecyclerAdapter_Adapter.notifyDataSetChanged();
        }
    }

    private class mAsyncPasargad1 extends Webservice.PostClassID {

        private String token;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Response " + result, Toast.LENGTH_LONG).show();
            Log.d("error", result);
//          try {
//              JSONObject object = new JSONObject(result);
//              token = object.getString("token");
//          } catch (JSONException e) {
//              e.printStackTrace();
//          }
//          new mAsyncPasargad1().execute("http://www.travelinsure.ir/api/webservice/getprice?tokan=" + token, "info@mahbal.com", "09121021701");
//          mRecyclerAdapter_Adapter.notifyDataSetChanged();
        }
    }

    private class mAsyncSaman extends Webservice_Soap.getPriceData {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Response " + result, Toast.LENGTH_LONG).show();
            Log.d("error", result);
//            mRecyclerAdapter_Adapter.notifyDataSetChanged();
        }
    }
}

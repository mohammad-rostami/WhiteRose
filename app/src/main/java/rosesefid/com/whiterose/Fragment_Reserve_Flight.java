package rosesefid.com.whiterose;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

public class Fragment_Reserve_Flight extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private EditText mEditText_FlightStartDate;
    private EditText mEditText_FlightEndDate;
    private EditText mEditText_FlightADL;
    private EditText mEditText_FlightCHD;
    private EditText mEditText_FlightINF;
    private String mStr_DateFieldPointer;
    private String mStr_NumberFieldPointer;
    private TextView mDialogTextView_Number;
    private Dialog Dialog_Number;
    private DialogFragment sDatePickerDialog;
    private String mStr_SolarDate;
    private NumberPicker mDialogNumberPicker_NumberPicker;
    private TextView mTextView_Confirm;
    private TextView mTextView_Cancel;

    public Fragment_Reserve_Flight() {
    }

    public static Fragment_Reserve_Flight newInstance(String param1, String param2) {
        Fragment_Reserve_Flight fragment = new Fragment_Reserve_Flight();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dialog_Number = new Dialog(getContext());
        Dialog_Number.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Number.setContentView(R.layout.dialog_number);
        Dialog_Number.setCancelable(true);

        mDialogTextView_Number = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_number);
        mDialogNumberPicker_NumberPicker = (NumberPicker) Dialog_Number.findViewById(R.id.dialog_number_np_picker);
        mTextView_Confirm = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_confirm);
        mTextView_Cancel = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_cancel);
        mDialogNumberPicker_NumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mStr_SolarDate = PersianDate.todayShamsi();
        Log.d("mohammad", mStr_SolarDate);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserve_flight, container, false);


        RelativeLayout mLinearLayout_Flight = (RelativeLayout) view.findViewById(R.id.activity_reserve_ll_flight);
        Switch mSwitch_TicketType = (Switch) view.findViewById(R.id.activity_reserve_sw_flight_switch);
        final TextView mTextView_TicketType = (TextView) view.findViewById(R.id.activity_reserve_txt_flight_switch);

        TextInputLayout mTextInputLayout_FlightStartDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_flight_start_date);
        TextInputLayout mTextInputLayout_FlightEndDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_flight_end_date);
        mEditText_FlightStartDate = (EditText) view.findViewById(R.id.activity_reserve_et_flight_start_date);
        mEditText_FlightEndDate = (EditText) view.findViewById(R.id.activity_reserve_et_flight_end_date);

        TextInputLayout mTextInputLayout_FlightADL = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_flight_adl);
        TextInputLayout mTextInputLayout_FlightCHD = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_flight_chd);
        TextInputLayout mTextInputLayout_FlightINF = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_flight_inf);
        mEditText_FlightADL = (EditText) view.findViewById(R.id.activity_reserve_et_flight_adl);
        mEditText_FlightCHD = (EditText) view.findViewById(R.id.activity_reserve_et_flight_chd);
        mEditText_FlightINF = (EditText) view.findViewById(R.id.activity_reserve_et_flight_inf);

        mEditText_FlightStartDate.setText(mStr_SolarDate);
        mEditText_FlightEndDate.setText(mStr_SolarDate);

        mTextInputLayout_FlightStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "FlightStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mTextInputLayout_FlightEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "FlightEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_FlightStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "FlightStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_FlightEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "FlightEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        // Number Pickers Code
        mTextInputLayout_FlightADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightADL";
                NumberPickerSetter(mEditText_FlightADL, 1, 9);
            }
        });
        mTextInputLayout_FlightCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightCHD";
                NumberPickerSetter(mEditText_FlightCHD, 0, 6);
            }
        });
        mTextInputLayout_FlightINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightINF";
                NumberPickerSetter(mEditText_FlightINF, 0, 6);
            }
        });
        mEditText_FlightADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightADL";
                NumberPickerSetter(mEditText_FlightADL, 1, 9);
            }
        });
        mEditText_FlightCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightCHD";
                NumberPickerSetter(mEditText_FlightCHD, 0, 6);
            }
        });
        mEditText_FlightINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "FlightINF";
                NumberPickerSetter(mEditText_FlightINF, 0, 6);
            }
        });

        mTextView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStr_NumberFieldPointer.equals("FlightADL")) {
                    mEditText_FlightADL.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("FlightCHD")) {
                    mEditText_FlightCHD.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("FlightINF")) {
                    mEditText_FlightINF.setText(mDialogTextView_Number.getText().toString());
                }
                Dialog_Number.dismiss();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    }

    private void NumberPickerSetter(EditText view, int min, int max) {
        mDialogTextView_Number.setText(view.getText().toString());
        mDialogNumberPicker_NumberPicker.setMinValue(min);
        mDialogNumberPicker_NumberPicker.setMaxValue(max);
        mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(view.getText().toString()));
        Dialog_Number.show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

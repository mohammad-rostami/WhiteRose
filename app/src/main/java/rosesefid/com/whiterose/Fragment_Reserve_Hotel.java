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
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

public class Fragment_Reserve_Hotel extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private EditText mEditText_HotelStartDate;
    private EditText mEditText_HotelEndDate;
    private EditText mEditText_HotelADL;
    private EditText mEditText_HotelCHD;
    private EditText mEditText_HotelINF;
    private String mStr_DateFieldPointer;
    private String mStr_NumberFieldPointer;
    private TextView mDialogTextView_Number;
    private Dialog Dialog_Number;
    private DialogFragment sDatePickerDialog;
    private String mStr_SolarDate;
    private NumberPicker mDialogNumberPicker_NumberPicker;
    private TextView mTextView_Confirm;
    private TextView mTextView_Cancel;

    public Fragment_Reserve_Hotel() {
    }

    public static Fragment_Reserve_Hotel newInstance(String param1, String param2) {
        Fragment_Reserve_Hotel fragment = new Fragment_Reserve_Hotel();
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
        View view = inflater.inflate(R.layout.fragment_reserve_hotel, container, false);

        TextInputLayout mTextInputLayout_HotelStartDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_hotel_start_date);
        TextInputLayout mTextInputLayout_HotelEndDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_hotel_end_date);
        mEditText_HotelStartDate = (EditText) view.findViewById(R.id.activity_reserve_et_hotel_start_date);
        mEditText_HotelEndDate = (EditText) view.findViewById(R.id.activity_reserve_et_hotel_end_date);

        TextInputLayout mTextInputLayout_HotelADL = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_hotel_adl);
        TextInputLayout mTextInputLayout_HotelCHD = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_hotel_chd);
        TextInputLayout mTextInputLayout_HotelINF = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_hotel_inf);
        mEditText_HotelADL = (EditText) view.findViewById(R.id.activity_reserve_et_hotel_adl);
        mEditText_HotelCHD = (EditText) view.findViewById(R.id.activity_reserve_et_hotel_chd);
        mEditText_HotelINF = (EditText) view.findViewById(R.id.activity_reserve_et_hotel_inf);

        mEditText_HotelStartDate.setText(mStr_SolarDate);
        mEditText_HotelEndDate.setText(mStr_SolarDate);

        mTextInputLayout_HotelStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "HotelStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mTextInputLayout_HotelEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "HotelEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_HotelStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "HotelStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_HotelEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "HotelEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        // Number Pickers Code
        mTextInputLayout_HotelADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelADL";
                NumberPickerSetter(mEditText_HotelADL, 1, 9);
            }
        });
        mTextInputLayout_HotelCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelCHD";
                NumberPickerSetter(mEditText_HotelCHD, 0, 6);
            }
        });
        mTextInputLayout_HotelINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelINF";
                NumberPickerSetter(mEditText_HotelINF, 0, 6);
            }
        });
        mEditText_HotelADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelADL";
                NumberPickerSetter(mEditText_HotelADL, 1, 9);
            }
        });
        mEditText_HotelCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelCHD";
                NumberPickerSetter(mEditText_HotelCHD, 0, 6);
            }
        });
        mEditText_HotelINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "HotelINF";
                NumberPickerSetter(mEditText_HotelINF, 0, 6);
            }
        });

        mTextView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStr_NumberFieldPointer.equals("HotelADL")) {
                    mEditText_HotelADL.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("HotelCHD")) {
                    mEditText_HotelCHD.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("HotelINF")) {
                    mEditText_HotelINF.setText(mDialogTextView_Number.getText().toString());
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

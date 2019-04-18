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

public class Fragment_Reserve_Tour extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private EditText mEditText_TourStartDate;
    private EditText mEditText_TourEndDate;
    private EditText mEditText_TourADL;
    private EditText mEditText_TourCHD;
    private EditText mEditText_TourINF;
    private String mStr_DateFieldPointer;
    private String mStr_NumberFieldPointer;
    private TextView mDialogTextView_Number;
    private Dialog Dialog_Number;
    private DialogFragment sDatePickerDialog;
    private String mStr_SolarDate;
    private NumberPicker mDialogNumberPicker_NumberPicker;
    private TextView mTextView_Confirm;
    private TextView mTextView_Cancel;

    public Fragment_Reserve_Tour() {
    }

    public static Fragment_Reserve_Tour newInstance(String param1, String param2) {
        Fragment_Reserve_Tour fragment = new Fragment_Reserve_Tour();
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
        View view = inflater.inflate(R.layout.fragment_reserve_tour, container, false);

        TextInputLayout mTextInputLayout_TourStartDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_tour_start_date);
        TextInputLayout mTextInputLayout_TourEndDate = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_tour_end_date);
        mEditText_TourStartDate = (EditText) view.findViewById(R.id.activity_reserve_et_tour_start_date);
        mEditText_TourEndDate = (EditText) view.findViewById(R.id.activity_reserve_et_tour_end_date);

        TextInputLayout mTextInputLayout_TourADL = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_tour_adl);
        TextInputLayout mTextInputLayout_TourCHD = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_tour_chd);
        TextInputLayout mTextInputLayout_TourINF = (TextInputLayout) view.findViewById(R.id.activity_reserve_til_tour_inf);
        mEditText_TourADL = (EditText) view.findViewById(R.id.activity_reserve_et_tour_adl);
        mEditText_TourCHD = (EditText) view.findViewById(R.id.activity_reserve_et_tour_chd);
        mEditText_TourINF = (EditText) view.findViewById(R.id.activity_reserve_et_tour_inf);

        mEditText_TourStartDate.setText(mStr_SolarDate);
        mEditText_TourEndDate.setText(mStr_SolarDate);

        mTextInputLayout_TourStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "TourStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mTextInputLayout_TourEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "TourEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_TourStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "TourStartDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        mEditText_TourEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_DateFieldPointer = "TourEndDate";
                ((Activity_Main) getActivity()).datePicker();
            }
        });
        // Number Pickers Code
        mTextInputLayout_TourADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourADL";
                NumberPickerSetter(mEditText_TourADL, 1, 9);
            }
        });
        mTextInputLayout_TourCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourCHD";
                NumberPickerSetter(mEditText_TourCHD, 0, 6);
            }
        });
        mTextInputLayout_TourINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourINF";
                NumberPickerSetter(mEditText_TourINF, 0, 6);
            }
        });
        mEditText_TourADL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourADL";
                NumberPickerSetter(mEditText_TourADL, 1, 9);
            }
        });
        mEditText_TourCHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourCHD";
                NumberPickerSetter(mEditText_TourCHD, 0, 6);
            }
        });
        mEditText_TourINF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStr_NumberFieldPointer = "TourINF";
                NumberPickerSetter(mEditText_TourINF, 0, 6);
            }
        });

        mTextView_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStr_NumberFieldPointer.equals("TourADL")) {
                    mEditText_TourADL.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("TourCHD")) {
                    mEditText_TourCHD.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("TourINF")) {
                    mEditText_TourINF.setText(mDialogTextView_Number.getText().toString());
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

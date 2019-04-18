package rosesefid.com.whiterose;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Activity_Reserve extends Activity implements DatePickerDialog.OnDateSetListener {

    static DatePickerDialog sDatePickerDialog;
    private LinearLayout mLinearLayout_Root;
    private Dialog Dialog_Number;
    private String mStr_NumberFieldPointer;
    private EditText mEditText_FlightStartDate;
    private EditText mEditText_FlightEndDate;
    private EditText mEditText_FlightADL;
    private EditText mEditText_FlightCHD;
    private EditText mEditText_FlightINF;
    private EditText mEditText_HotelStartDate;
    private EditText mEditText_HotelEndDate;
    private EditText mEditText_HotelADL;
    private EditText mEditText_HotelCHD;
    private EditText mEditText_HotelINF;
    private EditText mEditText_TourStartDate;
    private EditText mEditText_TourEndDate;
    private EditText mEditText_TourADL;
    private EditText mEditText_TourCHD;
    private EditText mEditText_TourINF;
    private NumberPicker mDialogNumberPicker_NumberPicker;
    private TextView mDialogTextView_Number;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_reserve);


        String mStr_SolarDate = PersianDate.todayShamsi();
        Log.d("mohammad", mStr_SolarDate);

        Dialog_Number = new Dialog(this);
        Dialog_Number.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Dialog_Number.setContentView(R.layout.dialog_number);
        Dialog_Number.setCancelable(true);

        mDialogTextView_Number = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_number);
        mDialogNumberPicker_NumberPicker = (NumberPicker) Dialog_Number.findViewById(R.id.dialog_number_np_picker);
        TextView mTextView_Confirm = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_confirm);
        TextView mTextView_Cancel = (TextView) Dialog_Number.findViewById(R.id.dialog_number_txt_cancel);
        mDialogNumberPicker_NumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        LinearLayout mLinearLayout_Hotel = (LinearLayout) findViewById(R.id.activity_reserve_ll_hotel);
        LinearLayout mLinearLayout_Tour = (LinearLayout) findViewById(R.id.activity_reserve_ll_tour);

        LinearLayout mLinearLayout_Flight = (LinearLayout) findViewById(R.id.activity_reserve_ll_flight);
        Switch mSwitch_TicketType = (Switch) findViewById(R.id.activity_reserve_sw_flight_switch);
        final TextView mTextView_TicketType = (TextView) findViewById(R.id.activity_reserve_txt_flight_switch);

        TextInputLayout mTextInputLayout_FlightStartDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_flight_start_date);
        TextInputLayout mTextInputLayout_FlightEndDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_flight_end_date);
        mEditText_FlightStartDate = (EditText) findViewById(R.id.activity_reserve_et_flight_start_date);
        mEditText_FlightEndDate = (EditText) findViewById(R.id.activity_reserve_et_flight_end_date);

        TextInputLayout mTextInputLayout_FlightADL = (TextInputLayout) findViewById(R.id.activity_reserve_til_flight_adl);
        TextInputLayout mTextInputLayout_FlightCHD = (TextInputLayout) findViewById(R.id.activity_reserve_til_flight_chd);
        TextInputLayout mTextInputLayout_FlightINF = (TextInputLayout) findViewById(R.id.activity_reserve_til_flight_inf);
        mEditText_FlightADL = (EditText) findViewById(R.id.activity_reserve_et_flight_adl);
        mEditText_FlightCHD = (EditText) findViewById(R.id.activity_reserve_et_flight_chd);
        mEditText_FlightINF = (EditText) findViewById(R.id.activity_reserve_et_flight_inf);

        mEditText_FlightStartDate.setText(mStr_SolarDate);
        mEditText_FlightEndDate.setText(mStr_SolarDate);

        mTextInputLayout_FlightStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "FlightStartDate";
                datePicker();
            }
        });
        mTextInputLayout_FlightEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "FlightEndDate";
                datePicker();
            }
        });
        mEditText_FlightStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "FlightStartDate";
                datePicker();
            }
        });
        mEditText_FlightEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "FlightEndDate";
                datePicker();
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


        TextInputLayout mTextInputLayout_HotelStartDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_hotel_start_date);
        TextInputLayout mTextInputLayout_HotelEndDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_hotel_end_date);
        mEditText_HotelStartDate = (EditText) findViewById(R.id.activity_reserve_et_hotel_start_date);
        mEditText_HotelEndDate = (EditText) findViewById(R.id.activity_reserve_et_hotel_end_date);
        TextInputLayout mTextInputLayout_HotelADL = (TextInputLayout) findViewById(R.id.activity_reserve_til_hotel_adl);
        TextInputLayout mTextInputLayout_HotelCHD = (TextInputLayout) findViewById(R.id.activity_reserve_til_hotel_chd);
        TextInputLayout mTextInputLayout_HotelINF = (TextInputLayout) findViewById(R.id.activity_reserve_til_hotel_inf);
        mEditText_HotelADL = (EditText) findViewById(R.id.activity_reserve_et_hotel_adl);
        mEditText_HotelCHD = (EditText) findViewById(R.id.activity_reserve_et_hotel_chd);
        mEditText_HotelINF = (EditText) findViewById(R.id.activity_reserve_et_hotel_inf);

        TextInputLayout mTextInputLayout_TourStartDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_tour_start_date);
        TextInputLayout mTextInputLayout_TourEndDate = (TextInputLayout) findViewById(R.id.activity_reserve_til_tour_end_date);
        mEditText_TourStartDate = (EditText) findViewById(R.id.activity_reserve_et_tour_start_date);
        mEditText_TourEndDate = (EditText) findViewById(R.id.activity_reserve_et_tour_end_date);
        TextInputLayout mTextInputLayout_TourADL = (TextInputLayout) findViewById(R.id.activity_reserve_til_tour_adl);
        TextInputLayout mTextInputLayout_TourCHD = (TextInputLayout) findViewById(R.id.activity_reserve_til_tour_chd);
        TextInputLayout mTextInputLayout_TourINF = (TextInputLayout) findViewById(R.id.activity_reserve_til_tour_inf);
        mEditText_TourADL = (EditText) findViewById(R.id.activity_reserve_et_tour_adl);
        mEditText_TourCHD = (EditText) findViewById(R.id.activity_reserve_et_tour_chd);
        mEditText_TourINF = (EditText) findViewById(R.id.activity_reserve_et_tour_inf);

        mEditText_HotelStartDate.setText(mStr_SolarDate);
        mEditText_TourStartDate.setText(mStr_SolarDate);
        mEditText_HotelEndDate.setText(mStr_SolarDate);
        mEditText_TourEndDate.setText(mStr_SolarDate);


        PersianCalendar persianCalendar = new PersianCalendar();
        sDatePickerDialog = DatePickerDialog.newInstance(
                Activity_Reserve.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );

// Date Pickers Code


        mTextInputLayout_HotelStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "HotelStartDate";
                datePicker();
            }
        });
        mTextInputLayout_HotelEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "HotelEndDate";
                datePicker();

            }
        });
        mEditText_HotelStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "HotelStartDate";
                datePicker();

            }
        });
        mEditText_HotelEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "HotelEndDate";
                datePicker();
            }
        });
        mTextInputLayout_TourStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "TourStartDate";
                datePicker();
            }
        });
        mTextInputLayout_TourEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "TourEndDate";
                datePicker();

            }
        });
        mEditText_TourStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "TourStartDate";
                datePicker();

            }
        });
        mEditText_TourEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mStr_DateFieldPointer = "TourEndDate";
                datePicker();
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
                if (mStr_NumberFieldPointer.equals("HotelADL")) {
                    mEditText_HotelADL.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("HotelCHD")) {
                    mEditText_HotelCHD.setText(mDialogTextView_Number.getText().toString());
                }
                if (mStr_NumberFieldPointer.equals("HotelINF")) {
                    mEditText_HotelINF.setText(mDialogTextView_Number.getText().toString());
                }
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


        mSwitch_TicketType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mTextView_TicketType.setText("بلیط دو طرفه");
                } else if (!buttonView.isChecked()) {
                    mTextView_TicketType.setText("بلیط یک طرفه");
                }
            }
        });


        TextView mTextView_PageHeader = (TextView) findViewById(R.id.toolbar_main_txt_header);
        mLinearLayout_Root = (LinearLayout) findViewById(R.id.activity_request_ll_root);

        if (G.STR_BUTTON_NO.equals("flight")) {
            mLinearLayout_Flight.setVisibility(View.VISIBLE);
            mLinearLayout_Hotel.setVisibility(View.GONE);
            mLinearLayout_Tour.setVisibility(View.GONE);
            mTextView_PageHeader.setText("رزرو بلیط");

        } else if (G.STR_BUTTON_NO.equals("hotel")) {
            mLinearLayout_Flight.setVisibility(View.GONE);
            mLinearLayout_Hotel.setVisibility(View.VISIBLE);
            mLinearLayout_Tour.setVisibility(View.GONE);
            mTextView_PageHeader.setText("رزرو هتل");

        } else if (G.STR_BUTTON_NO.equals("tour")) {
            mLinearLayout_Flight.setVisibility(View.GONE);
            mLinearLayout_Hotel.setVisibility(View.GONE);
            mLinearLayout_Tour.setVisibility(View.VISIBLE);
            mTextView_PageHeader.setText("رزرو تور");
        }

        if (savedInstanceState == null) {
            mLinearLayout_Root.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = mLinearLayout_Root.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            mLinearLayout_Root.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        } else {
                            mLinearLayout_Root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity() {

        int cx = mLinearLayout_Root.getWidth() / 2;
        int cy = mLinearLayout_Root.getHeight() / 2;

        float finalRadius = Math.max(mLinearLayout_Root.getWidth(), mLinearLayout_Root.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mLinearLayout_Root, cx, cy, 0, finalRadius);
        circularReveal.setDuration(500);

        // make the view visible and start the animation
        mLinearLayout_Root.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    public void datePicker() {
        sDatePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        switch (G.mStr_DateFieldPointer) {
            case "FlightStartDate":
                mEditText_FlightStartDate.setText(date);
                break;
            case "FlightEndDate":
                mEditText_FlightEndDate.setText(date);
                break;
            case "HotelStartDate":
                mEditText_HotelStartDate.setText(date);
                break;
            case "HotelEndDate":
                mEditText_HotelEndDate.setText(date);
                break;
            case "TourStartDate":
                mEditText_TourStartDate.setText(date);
                break;
            case "TourEndDate":
                mEditText_TourEndDate.setText(date);
                break;
        }
    }

    private void NumberPickerSetter(EditText view, int min, int max) {
        mDialogTextView_Number.setText(view.getText().toString());
        mDialogNumberPicker_NumberPicker.setMinValue(min);
        mDialogNumberPicker_NumberPicker.setMaxValue(max);
        mDialogNumberPicker_NumberPicker.setValue(Integer.parseInt(view.getText().toString()));
        Dialog_Number.show();
    }
}
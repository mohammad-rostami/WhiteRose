package rosesefid.com.whiterose;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//*************************************************************** THIS CLASS IS THE ADAPTER OF RECYCLERVIEWS
public class Adapter_Recycler extends RecyclerView.Adapter<Adapter_Recycler.ViewHolder> {
    private static int sInt_Height;
    private static int sInt_Height1;
    private static int sInt_Height2;
    private OnItemListener onItemListener;
    private ArrayList<Struct> mArray_Struct;
    private int mInt_Tab;
    private Context mContext;
    private boolean isGrid;
    private ValueAnimator mValueAnimator_Animator;


    public Adapter_Recycler(Context context, ArrayList<Struct> structs, OnItemListener onItemListener, int Tab, boolean isGrid) {
        this.onItemListener = onItemListener;
        this.mContext = context;
        this.mArray_Struct = structs;
        this.isGrid = isGrid;
        this.mInt_Tab = Tab;
    }

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = targetHeight + (int) ((sInt_Height - targetHeight) * interpolatedTime);

//                v.getLayoutParams().height = interpolatedTime == 1
//                        ? LinearLayout.LayoutParams.WRAP_CONTENT
//                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {

                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = (sInt_Height) - (int) ((sInt_Height - sInt_Height2) * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void card_collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = (initialHeight) - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = G.CONTEXT.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void card_expand(final View v, int size) {
        final int mInt_Height;
        if (size == 0) {
            mInt_Height = sInt_Height1;
        } else if (size == 1) {
            mInt_Height = sInt_Height;
        } else {
            mInt_Height = dpToPx(size);
        }
        Log.d("measure", String.valueOf(mInt_Height));

        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (int) (mInt_Height * interpolatedTime);
//                v.getLayoutParams().height = interpolatedTime == 1
//                        ? LinearLayout.LayoutParams.WRAP_CONTENT
//                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {

                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //CHOOSE WITCH XML (CARD LAYOUT) TO SHOW (INFLATE) FOR EATCH RECYCLERVIEW
        View view = null;
        if (mInt_Tab == 1) {
            view = inflater.inflate(R.layout.item_packages, parent, false);
        }
        if (mInt_Tab == 2) {
            view = inflater.inflate(R.layout.item_packages_chart, parent, false);
        }
        if (mInt_Tab == 3) {
            view = inflater.inflate(R.layout.item_suggestions, parent, false);
        }
        if (mInt_Tab == 4) {
            view = inflater.inflate(R.layout.item_country, parent, false);
        }
        if (mInt_Tab == 5) {
            view = inflater.inflate(R.layout.item_clock, parent, false);
        }
        if (mInt_Tab == 6 || mInt_Tab == 7) {
            view = inflater.inflate(R.layout.item_flight, parent, false);
        }
        if (mInt_Tab == 8) {
            view = inflater.inflate(R.layout.item_currency, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //DEFINING VIEWS FOR EATCH RECYCLER VIEW

        if (mInt_Tab == 1) {
            holder.mTextView_First.setText(mArray_Struct.get(position).mStr_First);
//            holder.mTextView_Second.setText(mArray_Struct.get(position).mStr_Fifth);
            holder.mTextView_Desc.setText(mArray_Struct.get(position).mStr_Sixth);
//            holder.mTextView_StartDate.setText(mArray_Struct.get(position).mStr_StartDate);
//            holder.mTextView_EndDate.setText(mArray_Struct.get(position).mStr_EndDate);
            holder.mTextView_Idn.setText(mArray_Struct.get(position).mStr_Target);
            holder.mTextView_Idn.setVisibility(View.GONE);

            holder.mTextView_Learn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(position);
                }
            });
            holder.mImageView_Expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemSelect(position);
                    if (holder.mLinearLayout_DescriptionLayout.getVisibility() == View.GONE) {
                        holder.mLinearLayout_DescriptionLayout.setVisibility(View.VISIBLE);
                        holder.mImageView_Expand.setImageResource(R.drawable.ic_collapse);
//                        holder.more1.setVisibility(View.VISIBLE);
//                        holder.more2.setVisibility(View.VISIBLE);
//                        holder.more3.setVisibility(View.VISIBLE);
//                        holder.more4.setVisibility(View.VISIBLE);
//                        holder.more5.setVisibility(View.VISIBLE);
                        card_expand(holder.mLinearLayout_DescriptionLayout, 200);
//                        card_expand(holder.more1);
//                        card_expand(holder.more2);
//                        card_expand(holder.more3);
//                        card_expand(holder.more4);
//                        card_expand(holder.more5);
                    } else {
                        card_collapse(holder.mLinearLayout_DescriptionLayout);
                        holder.mImageView_Expand.setImageResource(R.drawable.ic_expand);
//                        card_collapse(holder.more1);
//                        card_collapse(holder.more2);
//                        card_collapse(holder.more3);
//                        card_collapse(holder.more4);
//                        card_collapse(holder.more5);
//                        holder.more1.setVisibility(View.GONE);
//                        holder.more2.setVisibility(View.GONE);
//                        holder.more3.setVisibility(View.GONE);
//                        holder.more4.setVisibility(View.GONE);
                    }
//                    if (onItemListener != null) {
//                        if (holder.mTextView_Second.getLayoutParams().height == LinearLayout.LayoutParams.MATCH_PARENT) {
//                            collapse(holder.mTextView_Second);
//                        } else {
////                            holder.mTextView_Desc.setVisibility(View.VISIBLE);
////                            holder.mTextView_StartDate.setVisibility(View.VISIBLE);
////                            holder.mTextView_EndDate.setVisibility(View.VISIBLE);
//                            expand(holder.mTextView_Second);
//                            final Handler handler1 = new Handler();
//                            handler1.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //Do something after 100ms
//                                    holder.mTextView_Second.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
//                                    holder.mTextView_Second.requestLayout();
//                                }
//                            }, 300);
//                        }
//                        onItemListener.onItemSelect(position);
//                    }
                }
            });

//            Glide
//                    .with(Activity_Main.sContext)
//                    .load("Http://rose.travel/Images/Tours/1.jpg")
//                    .placeholder(R.drawable.no_image)
//                    .fitCenter()
//                    .into(holder.mImageView_Ticket);

            Picasso.with(G.CONTEXT)
                    .load(mArray_Struct.get(position).mStr_image)
                    .placeholder(R.drawable.no_image)
//                    .error(R.drawable.user_placeholder_error)
                    .into(holder.mImageView_Ticket);

//            ViewTreeObserver mViewTreeObserver = holder.mLinearLayout_DescriptionLayout.getViewTreeObserver();
//            mViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    holder.mLinearLayout_DescriptionLayout.getViewTreeObserver().removeOnPreDrawListener(this);
//                    sInt_Height = holder.mLinearLayout_DescriptionLayout.getMeasuredHeight();
//                    return true;
//                }
//            });
//            final Handler handler1 = new Handler();
//            handler1.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //Do something after 100ms
////                    holder.mLinearLayout_More.setVisibility(View.GONE);
//                    card_collapse(holder.mLinearLayout_DescriptionLayout);
//
//                }
//            }, 200);

        }
        if (mInt_Tab == 2) {

            holder.mTextView_Hotel.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_Room.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_Grade.setText(mArray_Struct.get(position).mStr_Third + " ★" + " + " + mArray_Struct.get(position).mStr_Forth);
            holder.mTextView_Desc1.setText(mArray_Struct.get(position).mStr_Fifth);
            holder.mTextView_DBL.setText(mArray_Struct.get(position).mStr_DBL);
            holder.mTextView_SGL.setText(mArray_Struct.get(position).mStr_SGL);
            holder.mTextView_CHD1.setText(mArray_Struct.get(position).mStr_CHD1);
            holder.mTextView_CHD2.setText(mArray_Struct.get(position).mStr_CHD2);
            holder.mTextView_INF.setText(mArray_Struct.get(position).mStr_INF);

            holder.mTextView_Learn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(position);
                }
            });
            holder.mLinearLayout_Root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mLinearLayout_More.getVisibility() == View.GONE) {
                        holder.mLinearLayout_More.setVisibility(View.VISIBLE);
//                        holder.more1.setVisibility(View.VISIBLE);
//                        holder.more2.setVisibility(View.VISIBLE);
//                        holder.more3.setVisibility(View.VISIBLE);
//                        holder.more4.setVisibility(View.VISIBLE);
//                        holder.more5.setVisibility(View.VISIBLE);
                        card_expand(holder.mLinearLayout_More, 0);
//                        card_expand(holder.more1);
//                        card_expand(holder.more2);
//                        card_expand(holder.more3);
//                        card_expand(holder.more4);
//                        card_expand(holder.more5);
                    } else {
                        card_collapse(holder.mLinearLayout_More);
//                        card_collapse(holder.more1);
//                        card_collapse(holder.more2);
//                        card_collapse(holder.more3);
//                        card_collapse(holder.more4);
//                        card_collapse(holder.more5);
//                        holder.more1.setVisibility(View.GONE);
//                        holder.more2.setVisibility(View.GONE);
//                        holder.more3.setVisibility(View.GONE);
//                        holder.more4.setVisibility(View.GONE);
                    }
//                    if (onItemListener != null) {
//                        if (holder.mLinearLayout_Highlight.getLayoutParams().height == LinearLayout.LayoutParams.MATCH_PARENT) {
//                            collapse(holder.mLinearLayout_Highlight);
//                        } else {
//                            holder.mTextView_Desc.setVisibility(View.VISIBLE);
//                            expand(holder.mLinearLayout_Highlight);
//                            final Handler handler1 = new Handler();
//                            handler1.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //Do something after 100ms
//                                    holder.mLinearLayout_Highlight.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
//                                    holder.mLinearLayout_Highlight.requestLayout();
//                                }
//                            }, 300);
//                        }
//                        onItemListener.onItemSelect(position);
//                    }
                }
            });

//            Glide
//                    .with(G.sContext)
//                    .load(mArray_Struct.get(position).mStr_image)
//                    .fitCenter()
//                    .placeholder(R.drawable.no_image)
//                    .into(holder.mImageView_Ticket);


            ViewTreeObserver mViewTreeObserver = holder.mLinearLayout_More.getViewTreeObserver();
            mViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    holder.mLinearLayout_More.getViewTreeObserver().removeOnPreDrawListener(this);
                    sInt_Height1 = holder.mLinearLayout_More.getMeasuredHeight();
                    return true;
                }
            });
            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
//                    holder.mLinearLayout_More.setVisibility(View.GONE);
                    card_collapse(holder.mLinearLayout_More);
                }
            }, 200);

        }
        if (mInt_Tab == 3) {
            holder.mTextView_Type.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_Price.setText(mArray_Struct.get(position).mStr_Second);
        }
        if (mInt_Tab == 4) {
            holder.mTextView_SamanCode.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_PasargadCode.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_EnglishName.setText(mArray_Struct.get(position).mStr_Third);
            holder.mTextView_PersianName.setText(mArray_Struct.get(position).mStr_Forth);
            holder.mCardView_Header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(position);
                }
            });
        }
        if (mInt_Tab == 5) {
//            holder.mLinearLayout_Remove.setClickable(false);
            holder.mTextView_CityTime.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_CityName.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_TimeZoneOffset.setText(mArray_Struct.get(position).mStr_Third);
            holder.mLinearLayout_Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mTextView_State.getText().equals("open")) {
                        onItemListener.onItemClick(position);
                    } else {
                        holder.mLinearLayout_main.animate().x(-280).setDuration(100);
                        holder.mTextView_State.setText("open");
//                        holder.mLinearLayout_Remove.setClickable(true);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (holder.mTextView_State.getText().toString().equals("open")) {
                                    holder.mLinearLayout_main.animate().x(0).setDuration(200);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
//                                            holder.mLinearLayout_Remove.setClickable(false);
                                            holder.mTextView_State.setText("close");
                                        }
                                    }, 100);
                                }
                            }
                        }, 600);

                    }
                }
            });
        }
        if (mInt_Tab == 6) {
//            holder.mLinearLayout_Remove.setClickable(false);
            holder.mTextView_AireLineName.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_FlightNumber.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_FlightLocation.setText(mArray_Struct.get(position).mStr_Third);
            holder.mTextView_FlightDestination.setText("Tehran");
            String mStr_StatusChecker = mArray_Struct.get(position).mStr_Forth;
            holder.mTextView_FlightStatus.setText(mStr_StatusChecker);
            holder.mTextView_FlightTime1.setText(mArray_Struct.get(position).mStr_Fifth);
            holder.mTextView_FlightDate.setText(mArray_Struct.get(position).mStr_Sixth);

            if (mStr_StatusChecker.equals("Cancelled")) {
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#EF5350"));
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#EF5350"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#EF5350"));

            } else if (mStr_StatusChecker.equals("Delayed")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#FF5722"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#FF5722"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#FF5722"));

            } else if (mStr_StatusChecker.equals("Departed")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#9CCC65"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#9CCC65"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#9CCC65"));
            } else if (mStr_StatusChecker.equals("Arrived")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#9CCC65"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#9CCC65"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#9CCC65"));
            } else {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#5d9cec"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#5d9cec"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#5d9cec"));
            }
        }
        if (mInt_Tab == 7) {
//            holder.mLinearLayout_Remove.setClickable(false);
            holder.mTextView_AireLineName.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_FlightNumber.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_FlightDestination.setText(mArray_Struct.get(position).mStr_Third);
            holder.mTextView_FlightLocation.setText("Tehran");
            String mStr_StatusChecker = mArray_Struct.get(position).mStr_Forth;
            holder.mTextView_FlightStatus.setText(mStr_StatusChecker);
            holder.mTextView_FlightTime1.setText(mArray_Struct.get(position).mStr_Fifth);
            holder.mTextView_FlightDate.setText(mArray_Struct.get(position).mStr_Sixth);

            if (mStr_StatusChecker.equals("Cancelled")) {
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#f44236"));
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#f44236"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#f44236"));

            } else if (mStr_StatusChecker.equals("Delayed")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#FF5722"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#FF5722"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#FF5722"));

            } else if (mStr_StatusChecker.equals("Departed")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#8bc24a"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#8bc24a"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#8bc24a"));
            } else if (mStr_StatusChecker.equals("Arrived")) {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#8bc24a"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#8bc24a"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#8bc24a"));
            } else {
                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#5d9cec"));
                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#5d9cec"));
                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#5d9cec"));
            }
        }
        if (mInt_Tab == 8) {
//            holder.mLinearLayout_Remove.setClickable(false);
            holder.mTextView_AireLineName.setText(mArray_Struct.get(position).mStr_Forth);
            holder.mTextView_FlightLocation.setText(mArray_Struct.get(position).mStr_First);
            holder.mTextView_FlightNumber.setText(mArray_Struct.get(position).mStr_Second);
            holder.mTextView_FlightDestination.setText(mArray_Struct.get(position).mStr_Third);
//            holder.mTextView_FlightLocation.setText("Tehran");
//            String mStr_StatusChecker = mArray_Struct.get(position).mStr_Forth;
//            holder.mTextView_FlightStatus.setText(mStr_StatusChecker);
//            holder.mTextView_FlightTime1.setText(mArray_Struct.get(position).mStr_Fifth);
//            holder.mTextView_FlightDate.setText(mArray_Struct.get(position).mStr_Sixth);

//            if (mStr_StatusChecker.equals("Cancelled")) {
//                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#f44236"));
//                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#f44236"));
//                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#f44236"));
//
//            } else if (mStr_StatusChecker.equals("Delayed")) {
//                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#FF5722"));
//                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#FF5722"));
//                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#FF5722"));
//
//            } else if (mStr_StatusChecker.equals("Departed")) {
//                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#8bc24a"));
//                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#8bc24a"));
//                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#8bc24a"));
//            } else if (mStr_StatusChecker.equals("Arrived")) {
//                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#8bc24a"));
//                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#8bc24a"));
//                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#8bc24a"));
//            } else {
//                holder.mTextView_FlightTime1.setTextColor(Color.parseColor("#5d9cec"));
//                holder.mTextView_FlightDate.setTextColor(Color.parseColor("#5d9cec"));
//                holder.mTextView_FlightStatus_Color.setBackgroundColor(Color.parseColor("#5d9cec"));
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mArray_Struct.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTextView_INF;
        ImageView mImageView_Expand;
        TextView mTextView_FlightDestination;
        LinearLayout mLinearLayout_DescriptionLayout;
        LinearLayout mLinearLayout_Root;
        LinearLayout mLinearLayout_Highlight;
        CardView mCardView_Header;
        ImageView mImageView_Ticket;
        TextView mTextView_First;
        TextView mTextView_Second;
        TextView mTextView_Desc;
        TextView mTextView_Learn;
        TextView mTextView_Share;
        TextView mTextView_Idn;
        TextView mTextView_StartDate;
        TextView mTextView_EndDate;

        TextView mTextView_Hotel;
        TextView mTextView_Room;
        TextView mTextView_Grade;
        TextView mTextView_DBL;
        TextView mTextView_SGL;
        TextView mTextView_CHD1;
        TextView mTextView_CHD2;
        TextView mTextView_Desc1;
        LinearLayout mLinearLayout_More;

        TextView mTextView_Type;
        TextView mTextView_Price;

        TextView mTextView_PersianName;
        TextView mTextView_SamanCode;
        TextView mTextView_PasargadCode;
        TextView mTextView_CityTime;
        TextView mTextView_CityName;
        TextView mTextView_TimeZoneOffset;


        TextView mTextView_EnglishName;
        LinearLayout mLinearLayout_main;
        LinearLayout mLinearLayout_Remove;
        TextView mTextView_State;

        TextView mTextView_AireLineName;
        TextView mTextView_FlightNumber;
        TextView mTextView_FlightLocation;
        VerticalTextView mTextView_FlightStatus;
        LinearLayout mTextView_FlightStatus_Color;
        TextView mTextView_FlightTime1;
        TextView mTextView_FlightDate;


        public ViewHolder(View itemView) {
            super(itemView);
            mLinearLayout_Highlight = (LinearLayout) itemView.findViewById(R.id.item_package_ll_highlight);
            mLinearLayout_Root = (LinearLayout) itemView.findViewById(R.id.item_package_chart_ll_root);
            mLinearLayout_DescriptionLayout = (LinearLayout) itemView.findViewById(R.id.item_packages_ll_description);
            mCardView_Header = (CardView) itemView.findViewById(R.id.activity_package_ll_table_header);
            mTextView_First = (TextView) itemView.findViewById(R.id.item_package_txt_highlight);
            mTextView_Second = (TextView) itemView.findViewById(R.id.item_package_txt);
            mTextView_Desc = (TextView) itemView.findViewById(R.id.item_package_txt_description);
            mTextView_Learn = (TextView) itemView.findViewById(R.id.item_package_txt_learn);
            mTextView_Share = (TextView) itemView.findViewById(R.id.item_package_txt_share);
            mTextView_Idn = (TextView) itemView.findViewById(R.id.item_package_txt_idn);
            mImageView_Expand = (ImageView) itemView.findViewById(R.id.item_package_img_expand);

            mTextView_StartDate = (TextView) itemView.findViewById(R.id.item_package_txt_start_date);
            mTextView_EndDate = (TextView) itemView.findViewById(R.id.item_package_txt_end_date);

            mImageView_Ticket = (ImageView) itemView.findViewById(R.id.fragment_reserve_img_ticket);
            mTextView_Hotel = (TextView) itemView.findViewById(R.id.fragment_reserve_txt_hotel);

            mTextView_Room = (TextView) itemView.findViewById(R.id.item_package_chart_txt_room);
            mTextView_Grade = (TextView) itemView.findViewById(R.id.item_package_chart_txt_grade);
            mTextView_DBL = (TextView) itemView.findViewById(R.id.item_package_chart_txt_DBL);
            mTextView_SGL = (TextView) itemView.findViewById(R.id.item_package_chart_txt_SGL);
            mTextView_CHD1 = (TextView) itemView.findViewById(R.id.item_package_chart_txt_CHD1);
            mTextView_CHD2 = (TextView) itemView.findViewById(R.id.item_package_chart_txt_CHD2);
            mTextView_INF = (TextView) itemView.findViewById(R.id.item_package_chart_txt_INF);
            mTextView_Desc1 = (TextView) itemView.findViewById(R.id.item_package_chart_txt_desc);
            mLinearLayout_More = (LinearLayout) itemView.findViewById(R.id.item_package_chart_txt_more);

            mTextView_Type = (TextView) itemView.findViewById(R.id.item_suggestion_txt_type);
            mTextView_Price = (TextView) itemView.findViewById(R.id.item_suggestion_txt_price);

            mTextView_PersianName = (TextView) itemView.findViewById(R.id.item_country_txt_persian_name);
            mTextView_EnglishName = (TextView) itemView.findViewById(R.id.item_country_txt_english_name);
            mTextView_SamanCode = (TextView) itemView.findViewById(R.id.item_country_txt_saman_code);
            mTextView_PasargadCode = (TextView) itemView.findViewById(R.id.item_country_txt_pasargad_code);

            mTextView_CityTime = (TextView) itemView.findViewById(R.id.item_clock_city_time);
            mTextView_CityName = (TextView) itemView.findViewById(R.id.item_clock_city_name);
            mTextView_TimeZoneOffset = (TextView) itemView.findViewById(R.id.item_clock_time_zone_offset);
            mLinearLayout_main = (LinearLayout) itemView.findViewById(R.id.item_clock_ll_root);
            mLinearLayout_Remove = (LinearLayout) itemView.findViewById(R.id.item_clock_ll_remove);
            mTextView_State = (TextView) itemView.findViewById(R.id.item_clock_state);

            mTextView_AireLineName = (TextView) itemView.findViewById(R.id.item_flight_txt_air_line);
            mTextView_FlightNumber = (TextView) itemView.findViewById(R.id.item_flight_txt_flight_number);
            mTextView_FlightLocation = (TextView) itemView.findViewById(R.id.item_flight_txt_source);
            mTextView_FlightDestination = (TextView) itemView.findViewById(R.id.item_flight_txt_destination);
            mTextView_FlightStatus = (VerticalTextView) itemView.findViewById(R.id.item_flight_txt_status);
            mTextView_FlightStatus_Color = (LinearLayout) itemView.findViewById(R.id.item_flight_ll_status_color);
            mTextView_FlightTime1 = (TextView) itemView.findViewById(R.id.item_flight_txt_time);
            mTextView_FlightDate = (TextView) itemView.findViewById(R.id.item_flight_txt_date);

        }
    }
}

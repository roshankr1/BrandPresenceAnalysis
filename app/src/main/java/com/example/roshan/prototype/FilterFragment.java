package com.example.shubzz99.prototype;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.text1;
import static com.example.shubzz99.prototype.R.color.fontForBlack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {


    private BottomSheetBehavior mBottomSheetBehavior1;
    private StorageReference gsReference;
    private FirebaseStorage storage;
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;
    private static View rootView2;
    private ImageView tempImg;
    private FragmentActivity myContext;
    private static GraphView graph2;
    static PieChart pieChart;
    private static TextView maleTextView,femaleTextView;
    private static int var;
    private static int positive,negative,neutral,male,female,total;
    private static boolean male_check;
    private static boolean female_check;
    private DateClass startDate_check;
    private DateClass endDate_check;
    //GraphData g;
    static modalbottom_sheet bottom = new modalbottom_sheet();
    private float[] yData = {25.3f, 10.6f,23.5f};
    private static String[] xData = {"Positive", "Negative", "Neutral" };

    GenericTypeIndicator<List<GraphData>> genericTypeIndicator;
    static List<GraphData> taskDesList;
    HomeFragment hf;
    static DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
    static Query mconditionref;
    //Query mconditionref = rootref.child("Brands").child("Adidas").child("Location").equalTo("USA");
    // Query mconditionref = rootref.child("Brands").child("Adidas").orderByChild("Date").startAt("2017-07-02 23:59:55").endAt("2017-07-02 23:59:52");
    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        neutral=0;positive=0;negative=0;male=0;female=0; total=0;

        // Inflate the layout for this fragment
        rootView2 = inflater.inflate(R.layout.fragment_filter, container, false);

//        Button temp_bun = (Button) rootView2.findViewById(R.id.buncheck);

        mconditionref = rootref.child("Brands").child("Adidas_Graph");
        mconditionref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String text = dataSnapshot.getValue(String.class);
                //text1.setText("sup");
                //text1.setText(text);
                long value=dataSnapshot.getChildrenCount();
                Log.d("no of children: ", String.valueOf(value));

                GenericTypeIndicator<List<GraphData>> genericTypeIndicator =new GenericTypeIndicator<List<GraphData>>(){};

                taskDesList=dataSnapshot.getValue(genericTypeIndicator);
                Log.v("lalal", "value: " + dataSnapshot.getValue(genericTypeIndicator));


                for (int i = 0; i < taskDesList.size(); i++) {
                    //  Toast.makeText(MainActivity.this,"TaskTitle = "+taskDesList.get(i).getLocation(), Toast.LENGTH_LONG).show();
                    if(taskDesList.get(i)!= null) {
                        String x = taskDesList.get(i).getDate();
                        Log.v("STRING:" ,"value of i:" + x);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        graph2 = (GraphView) rootView2.findViewById(R.id.lineGraphToday);
        pieChart = (PieChart) rootView2.findViewById(R.id.idPieChart);
        maleTextView = (TextView) rootView2.findViewById(R.id.MaleCount);
        femaleTextView = (TextView) rootView2.findViewById(R.id.FemaleCount);
        ImageButton filter_bun = (ImageButton) rootView2.findViewById(R.id.fabbun2);
        filter_bun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.v("filter", "filter clicked");
                //here make every flag = 0
                modalbottom_sheet.male_flag = 0;
                modalbottom_sheet.female_flag = 0;
                modalbottom_sheet.start_flag = 0;
                modalbottom_sheet.end_flag = 0;
                BottomSheetDialogFragment bottomSheetDialogFragment = new modalbottom_sheet();
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());



            }
        });

        Button temp = (Button) rootView2.findViewById(R.id.Submit);
        temp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                var = modalbottom_sheet.start_flag;
                Log.v("please start falg:", String.valueOf(modalbottom_sheet.start_flag));
                Log.v("please end flag:", String.valueOf(modalbottom_sheet.end_flag));



                for(var= modalbottom_sheet.start_flag; var<= modalbottom_sheet.end_flag; var++) {
                    neutral += taskDesList.get(var).getNeutral();
                    negative += taskDesList.get(var).getNegative();
                    positive += taskDesList.get(var).getPositive();
                   if(modalbottom_sheet.male_flag!=0) male += taskDesList.get(var).getMale();
                    if(modalbottom_sheet.female_flag!=0)female += taskDesList.get(var).getFemale();
                }

                Log.v("please male:", String.valueOf(male));




                    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
                        new DataPoint(0,0),
                        new DataPoint(1,1),
                        new DataPoint(2,3),
                        new DataPoint(3,4),
                        new DataPoint(4,2),
                        new DataPoint(5,1),
                        new DataPoint(6,5),
                        new DataPoint(7,7),
                });
                graph2.addSeries(series2);
                graph2.setTitle("DATE-WISE ANALYSIS");
                graph2.setTitleTextSize(50);
                //graph2.setTitleColor(999999);
                //series2.setTitle("Date");
                //series2.setColor(0);

                graph2.getViewport().setScrollable(true); // enables horizontal scrolling
                graph2.getViewport().setScrollableY(true); // enables vertical scrolling
                graph2.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                graph2.getViewport().setScalableY(true);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph2);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"3jul","4jul","5jul","6jul","7jul","8jul","9jul"});
                staticLabelsFormatter.setVerticalLabels(new String[] {"0","200", "400", "600","800","1000"});
                graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                total =male+female;
                if(total!=0) { male*=100;male/=total;female*=100;female/=total; }
                maleTextView.setText("Male: " + String.valueOf(male)+ "%");
                femaleTextView.setText("Female: " + String.valueOf(female)+ "%");
                //pieChart.setRotationEnabled(true);
                pieChart.setHoleRadius(25f);
                pieChart.setTransparentCircleAlpha(0);
                pieChart.setCenterText("Sentiment Analysis");
                pieChart.setCenterTextSize(10);

                addDataSet();
            }
        });




        return rootView2;
    }

    public static void makeGraphs()
    {

//        mconditionref = rootref.child("Brands").child("Adidas_Graph");
//        mconditionref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //String text = dataSnapshot.getValue(String.class);
//                //text1.setText("sup");
//                //text1.setText(text);
//                long value=dataSnapshot.getChildrenCount();
//                Log.d("no of children: ", String.valueOf(value));
//
//                GenericTypeIndicator<List<GraphData>> genericTypeIndicator =new GenericTypeIndicator<List<GraphData>>(){};
//
//                taskDesList=dataSnapshot.getValue(genericTypeIndicator);
//                Log.v("lalal", "value: " + dataSnapshot.getValue(genericTypeIndicator));
//
//
//                for (int i = 0; i < taskDesList.size(); i++) {
//                    //  Toast.makeText(MainActivity.this,"TaskTitle = "+taskDesList.get(i).getLocation(), Toast.LENGTH_LONG).show();
//                    if(taskDesList.get(i)!= null) {
//                        String x = taskDesList.get(i).getDate();
//                        Log.v("STRING:" ,"value of i:" + x);
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        var = modalbottom_sheet.start_flag;


        for(var= modalbottom_sheet.start_flag; var<= modalbottom_sheet.end_flag; var++)
        {
            neutral+=taskDesList.get(var).getNeutral();
            negative+=taskDesList.get(var).getNegative();
            positive+=taskDesList.get(var).getPositive();
            if(modalbottom_sheet.male_flag!=0) male+=taskDesList.get(var).getMale();
            if(modalbottom_sheet.female_flag!=0)female+=taskDesList.get(var).getFemale();


        }

    }







    private static void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        int total2= positive+negative+neutral;
        if(total2!=0){ positive*=100;positive/=total2; negative*=100;negative/=total2;neutral*=100;neutral/=total2;}
        //positive=64; negative=32; neutral=100-64-32;


        yEntrys.add(new PieEntry(positive , 2));
        yEntrys.add(new PieEntry(negative , 1));
        yEntrys.add(new PieEntry(neutral , 0));



        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Count");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onStart()
    {

        super.onStart();


    }




    //make it a seprate class if required, i have made it an inner class..coz we want to use var of filter class in
    // this class
    public static class modalbottom_sheet  extends BottomSheetDialogFragment
    {
        View contentView;
        private FragmentActivity myContext;
        private static int male_flag = 0;
        private static int female_flag = 0;
        static int start_flag = 0;
        static int end_flag = 0;

//        FilterFragment aboveClass = new FilterFragment();

        private BottomSheetBehavior.BottomSheetCallback
                mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        };

        @Override
        public void setupDialog(final Dialog dialog, int style)
        {
            super.setupDialog(dialog, style);
            contentView = View.inflate(getContext(), R.layout.bottom_page, null);

            TextView exit_temp2 = (TextView) contentView.findViewById(R.id.f_exit);


            exit_temp2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //first we will call method

                    makeGraphs();
                    dismiss();
                    Log.v("filter","Apply filter");

                }
            });



            ImageButton cancel_bun =(ImageButton)contentView.findViewById(R.id.cancel);

            cancel_bun.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dismiss();
                    Log.v("filter","Apply filter");

                }
            });


            //following is the listener for checkboxes in bottom sheet





            CheckBox fb1 = (CheckBox)contentView.findViewById(R.id.fb_c);
            fb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                           {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                               {
                                                   TextView t1 = (TextView) contentView.findViewById(R.id.fb_txt);

                                                   if(isChecked)
                                                   {
                                                       Log.v("lalala", "fuck yeah");

                                                       t1.setTextColor(Color.parseColor("#ffffff"));
                                                   }
                                                   else
                                                   {
                                                       Log.v("lalala", "not checked");
                                                       t1.setTextColor(Color.parseColor("#4c4c4c"));
                                                   }
                                               }
                                           }
            );

            CheckBox twitter1 = (CheckBox)contentView.findViewById(R.id.twitter_c);
            twitter1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                                {

                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                    {
                                                        TextView t2 = (TextView) contentView.findViewById(R.id.twitter_txt);

                                                        if(isChecked)
                                                        {
                                                            Log.v("lalala", "fuck yeah2");
                                                            t2.setTextColor(Color.parseColor("#ffffff"));
                                                        }
                                                        else
                                                        {
                                                            Log.v("lalala", "not checked2");
                                                            t2.setTextColor(Color.parseColor("#4c4c4c"));
                                                        }
                                                    }
                                                }
            );

            CheckBox google1 = (CheckBox)contentView.findViewById(R.id.google_c);
            google1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                               {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                   {
                                                       TextView t3 = (TextView) contentView.findViewById(R.id.google_txt);

                                                       if(isChecked)
                                                       {
                                                           Log.v("lalala", "fuck yeah2");
                                                           t3.setTextColor(Color.parseColor("#ffffff"));
                                                       }
                                                       else
                                                       {
                                                           Log.v("lalala", "not checked2");
                                                           t3.setTextColor(Color.parseColor("#4c4c4c"));
                                                       }
                                                   }
                                               }
            );

            CheckBox other1 = (CheckBox)contentView.findViewById(R.id.other_c);
            other1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                              {

                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                  {
                                                      TextView t4 = (TextView) contentView.findViewById(R.id.other_txt);

                                                      if(isChecked)
                                                      {
                                                          Log.v("lalala", "fuck yeah2");
                                                          t4.setTextColor(Color.parseColor("#ffffff"));
                                                      }
                                                      else
                                                      {
                                                          Log.v("lalala", "not checked2");
                                                          t4.setTextColor(Color.parseColor("#4c4c4c"));
                                                      }
                                                  }
                                              }
            );

            CheckBox male1 = (CheckBox)contentView.findViewById(R.id.male_c);
            male1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                             {

                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                 {
                                                     TextView t5 = (TextView) contentView.findViewById(R.id.male_txt);

                                                     if(isChecked)
                                                     {
                                                         male_flag = 1;
                                                         //if(male_flag == 1)
                                                         Log.v("lalala", "fuck yeah2");
                                                         t5.setTextColor(Color.parseColor("#ffffff"));
                                                     }
                                                     else
                                                     {
                                                         male_flag = 0;
                                                         //if(male_flag == 1)
                                                         Log.v("lalala", "not checked2");
                                                         t5.setTextColor(Color.parseColor("#4c4c4c"));
                                                     }
                                                 }
                                             }
            );

            CheckBox female1 = (CheckBox)contentView.findViewById(R.id.female_c);
            female1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                               {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
                                                   {
                                                       TextView t6 = (TextView) contentView.findViewById(R.id.female_txt);

                                                       if(isChecked)
                                                       {
                                                           female_flag = 1;
                                                           Log.v("lalala", "fuck yeah2");
                                                           t6.setTextColor(Color.parseColor("#ffffff"));
                                                       }
                                                       else
                                                       {
                                                           female_flag = 0;
                                                           Log.v("lalala", "not checked2");
                                                           t6.setTextColor(Color.parseColor("#4c4c4c"));
                                                       }
                                                   }
                                               }
            );



            //Start and end date button //its action listener is in datepickerfragment.java, and datepickerfragment2

            Button start1 = (Button) contentView.findViewById(R.id.start_check);

            start1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DialogFragment newFragment = new DatePickerFragment();
                    FragmentManager fragManager = myContext.getFragmentManager();

                    newFragment.show(fragManager, "datePicker");

                }
            });

            Button end1 = (Button) contentView.findViewById(R.id.end_check);

            end1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    DialogFragment newFragment = new DatePickerFragment2();
                    FragmentManager fragManager = myContext.getFragmentManager();

                    newFragment.show(fragManager, "datePicker");

                }
            });

            dialog.setContentView(contentView);
        }

        @Override
        public void onAttach(Activity activity) {
            myContext=(FragmentActivity) activity;
            super.onAttach(activity);
        }

        public int getDay(int date)
        {
            if(date == 14)
                return 6;
            else if(date == 13)
                return 5;
            else if(date == 12)
                return 4;
            else if(date == 11)
                return 3;
            else if(date == 10)
                return 2;
            else if(date == 9)
                return 1;
            else if(date == 8)
                return 0;

            return 6;
        }

    }



//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    private void zoomImageFromThumb(final View thumbView, ImageView imageResId) {
//        if (mCurrentAnimatorEffect != null) {
//            mCurrentAnimatorEffect.cancel();
//        }
//
//        final ImageView expandedImageView = (ImageView) rootView2.findViewById(
//                R.id.expanded_image);
//        //expandedImageView.setImageResource(imageResId);
//
//        ImageView temp = imageResId;
//        expandedImageView.setImageDrawable(imageResId.getDrawable());
//
//        final Rect startBounds = new Rect();
//        final Rect finalBounds = new Rect();
//        final Point globalOffset = new Point();
//
//        thumbView.getGlobalVisibleRect(startBounds);
//        rootView2.findViewById(R.id.container)
//                .getGlobalVisibleRect(finalBounds, globalOffset);
//        startBounds.offset(-globalOffset.x, -globalOffset.y);
//        finalBounds.offset(-globalOffset.x, -globalOffset.y);
//
//        float startScale;
//        if ((float) finalBounds.width() / finalBounds.height()
//                > (float) startBounds.width() / startBounds.height()) {
//            // Extend start bounds horizontally
//            startScale = (float) startBounds.height() / finalBounds.height();
//            float startWidth = startScale * finalBounds.width();
//            float deltaWidth = (startWidth - startBounds.width()) / 2;
//            startBounds.left -= deltaWidth;
//            startBounds.right += deltaWidth;
//        } else {
//            // Extend start bounds vertically
//            startScale = (float) startBounds.width() / finalBounds.width();
//            float startHeight = startScale * finalBounds.height();
//            float deltaHeight = (startHeight - startBounds.height()) / 2;
//            startBounds.top -= deltaHeight;
//            startBounds.bottom += deltaHeight;
//        }
//
//        thumbView.setAlpha(0f);
//        expandedImageView.setVisibility(View.VISIBLE);
//
//        expandedImageView.setPivotX(0f);
//        expandedImageView.setPivotY(0f);
//
//        // scale properties (X, Y, SCALE_X, and SCALE_Y).
//        AnimatorSet set = new AnimatorSet();
//        set
//                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
//                        startBounds.left, finalBounds.left))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
//                        startBounds.top, finalBounds.top))
//                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
//                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
//                View.SCALE_Y, startScale, 1f));
//        set.setDuration(mShortAnimationDurationEffect);
//        set.setInterpolator(new DecelerateInterpolator());
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimatorEffect = null;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimatorEffect = null;
//            }
//        });
//        set.start();
//        mCurrentAnimatorEffect = set;
//
//        final float startScaleFinal = startScale;
//        expandedImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mCurrentAnimatorEffect != null) {
//                    mCurrentAnimatorEffect.cancel();
//                }
//
//                // back to their original values.
//                AnimatorSet set = new AnimatorSet();
//                set.play(ObjectAnimator
//                        .ofFloat(expandedImageView, View.X, startBounds.left))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.Y,startBounds.top))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_X, startScaleFinal))
//                        .with(ObjectAnimator
//                                .ofFloat(expandedImageView,
//                                        View.SCALE_Y, startScaleFinal));
//                set.setDuration(mShortAnimationDurationEffect);
//                set.setInterpolator(new DecelerateInterpolator());
//                set.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimatorEffect = null;
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        thumbView.setAlpha(1f);
//                        expandedImageView.setVisibility(View.GONE);
//                        mCurrentAnimatorEffect = null;
//                    }
//                });
//                set.start();
//                mCurrentAnimatorEffect = set;
//            }
//        });
//    }

}

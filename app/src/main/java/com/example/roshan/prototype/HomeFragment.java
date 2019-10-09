package com.example.shubzz99.prototype;


import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
//import com.mikepenz.materialdrawer.DrawerBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    public DatabaseReference rootref= FirebaseDatabase.getInstance().getReference();
    Query mconditionref;
    private int var,male_count, female_count,positive,negative,neutral,total;;
    private GraphView graph2;
    private int index;
    private float[] yData = {25.3f, 10.6f,23.5f};
    private String[] xData = {"Positive", "Negative", "Neutral" };
    PieChart pieChart;
    private TextView maleTextView,femaleTextView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private ChildEventListener mChildEventListener;


    ArrayList<Integer> date_count= new ArrayList<>();
    ArrayList<Long>gender_count= new ArrayList<>();
    List<GraphData> taskDesList;

    public HomeFragment() {
        // Required empty public constructor

    }

    public List<GraphData> getList()
    {
        return taskDesList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



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

                male_count=taskDesList.get(6).getMale();
                female_count=taskDesList.get(6).getFemale();
                positive= taskDesList.get(6).getPositive();
                negative=taskDesList.get(6).getNegative();
                neutral=taskDesList.get(6).getNeutral();
                for(int i=0;i<=6;i++) {
                    date_count.add(i, taskDesList.get(i).getCount());
                    Log.v("String", String.valueOf(date_count.get(i)));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        materialDesignFAM = (FloatingActionMenu)rootView.findViewById(R.id.material_design_android_floating_action_menu);
        maleTextView=(TextView) rootView.findViewById(R.id.MaleCount);
        femaleTextView= (TextView) rootView.findViewById(R.id.FemaleCount);

        floatingActionButton2 = (FloatingActionButton) rootView.findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton)rootView.findViewById(R.id.material_design_floating_action_menu_item3);

        graph2 = (GraphView) rootView.findViewById(R.id.lineGraph);
        pieChart = (PieChart) rootView.findViewById(R.id.idPieChart2);

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Log.v("MainActivity","B");
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

                Log.v("MainActivity","C");
            }
        });



        Button temp = (Button) rootView.findViewById(R.id.allGraphs);
        temp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

int x0=date_count.get(0);int x1=date_count.get(1);int x2=date_count.get(2);int x3=date_count.get(3);
int x4=date_count.get(4);int x5=date_count.get(5);int x6=date_count.get(6); int t= x0+x1+x2+x3+x4+x5+x6;
//                x0/=t;x1/=t;x2/=t;x3/=t;x4/=t;x5/=t;x6/=t;
//                x0*=100;x1*=100;x2*=100;x3*=100;x4*=100;x5*=100;x6*=100;

                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
                        new DataPoint(0,x0),
                        new DataPoint(1,x1),
                        new DataPoint(2,x2),
                        new DataPoint(3,x3),
                        new DataPoint(4,x4),
                        new DataPoint(5,x5),
                        new DataPoint(6,x6),
                        //new DataPoint(7,7),
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
                //graph2.getLegendRenderer().setVisible(true);
                //graph2.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                // use static labels for horizontal and vertical labels
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph2);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"3jul","4jul","5jul","6jul","7jul","8jul","9jul"});
                staticLabelsFormatter.setVerticalLabels(new String[] {"0","100", "200", "300","400","500","600","700","800","900","1000","1100"});
                graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

                total =male_count+female_count;
               if(total!=0) { male_count*=100;male_count/=total;female_count*=100;female_count/=total; }
                maleTextView.setText("Male: " + String.valueOf(male_count)+ "%");
                femaleTextView.setText("Female: " + String.valueOf(female_count)+ "%");
                //pieChart.setRotationEnabled(true);
                pieChart.setHoleRadius(25f);
                pieChart.setTransparentCircleAlpha(0);
                pieChart.setCenterText("Sentiment Analysis");
                pieChart.setCenterTextSize(10);

                addDataSet();
            }
        });



        return rootView;

    }

    @Override
    public void onStart()
    {
        super.onStart();




//        ArrayList<String> Date= new ArrayList<String>();
//
//        Date.add("2017-07-03 00:00:00");    Date.add("2017-07-03 23:59:59");
//        Date.add("2017-07-04 00:00:00");    Date.add("2017-07-04 23:59:59");
//        Date.add("2017-07-05 00:00:00");    Date.add("2017-07-05 23:59:59");
//        Date.add("2017-07-06 00:00:00");    Date.add("2017-07-06 23:59:59");
//        Date.add("2017-07-07 00:00:00");    Date.add("2017-07-07 23:59:59");
//        Date.add("2017-07-08 00:00:00");    Date.add("2017-07-08 23:59:59");
//        Date.add("2017-07-09 00:00:00");    Date.add("2017-07-09 23:59:59");
//
//        for(var=0;var<14;var+=2)
//        {
//            mconditionref = rootref.child("Brands").child("Adidas").orderByChild("Date").startAt(Date.get(var)).endAt(Date.get(var+1));
//            index=0;
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    long x=dataSnapshot.getChildrenCount();
//                    Log.v("E_VALUE", "children count:" + x);
//                    date_count.set(index,x);
//                    index++;
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        mconditionref = rootref.child("Brands").child("Adidas_Graph").child("6").child("Male");
//        mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    male_count+=y;
//                    Log.v("E_VALUE", "Gender Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        mconditionref = rootref.child("Brands").child("Adidas_Graph").child("6").child("Female");
//        mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    female_count+=y;
//                    Log.v("E_VALUE", "Female Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        mconditionref = rootref.child("Brands").child("Adidas_Graph").child("6").child("Positive");
//        mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int y=dataSnapshot.getValue(Integer.class);
//                positive+=y;
//                Log.v("E_VALUE", "Positive Value is:" + y);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mconditionref = rootref.child("Brands").child("Adidas_Graph").child("6").child("Negative");
//        mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int y=dataSnapshot.getValue(Integer.class);
//                negative+=y;
//                Log.v("E_VALUE", "Negative Value is:" + y);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        mconditionref = rootref.child("Brands").child("Adidas_Graph").child("6").child("Neutral");
//        mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int y=dataSnapshot.getValue(Integer.class);
//                neutral+=y;
//                Log.v("E_VALUE", "Neutral Value is:" + y);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//
//        for(var=0; var<7;var++)
//        {
//            String x= String.valueOf(var);
//            mconditionref = rootref.child("Brands").child("Adidas_Graph").child(x).child("Male");
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    male_count+=y;
//                    Log.v("E_VALUE", "Gender Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        for(var=0; var<7;var++)
//        {
//            String x= String.valueOf(var);
//            mconditionref = rootref.child("Brands").child("Adidas_Graph").child(x).child("Female");
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    female_count+=y;
//                    Log.v("E_VALUE", "Female Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        for(var=0; var<7;var++)
//        {
//            String x= String.valueOf(var);
//            mconditionref = rootref.child("Brands").child("Adidas_Graph").child(x).child("Positive");
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    positive+=y;
//                    Log.v("E_VALUE", "Positive Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        for(var=0; var<7;var++)
//        {
//            String x= String.valueOf(var);
//            mconditionref = rootref.child("Brands").child("Adidas_Graph").child(x).child("Negative");
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    negative+=y;
//                    Log.v("E_VALUE", "Negative Value is:" + y);
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//        for(var=0; var<7;var++)
//        {
//            String x= String.valueOf(var);
//            mconditionref = rootref.child("Brands").child("Adidas_Graph").child(x).child("Neutral");
//            index=0;
//            mconditionref.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int y=dataSnapshot.getValue(Integer.class);
//                    neutral+=y;
//                    Log.v("E_VALUE", "Neutral Value is:" + y);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }



    }


    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        int total2= positive+negative+neutral;
        if(total2!=0){ positive*=100;positive/=total2; negative*=100;negative/=total2;neutral*=100;neutral/=total2;}

        yEntrys.add(new PieEntry(positive , 2));
        yEntrys.add(new PieEntry(negative , 1));
        yEntrys.add(new PieEntry(neutral , 0));

        // for(int i = 0; i < yData.length; i++){
        //     yEntrys.add(new PieEntry(yData[i] , i));
        // }

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


}

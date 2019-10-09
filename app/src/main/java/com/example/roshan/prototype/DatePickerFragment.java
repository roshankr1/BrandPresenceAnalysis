package com.example.shubzz99.prototype;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by shubzz99 on 12/7/17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    FilterFragment.modalbottom_sheet filterFrag = new FilterFragment.modalbottom_sheet();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        // Do something with the date chosen by the user
        Toast.makeText(getActivity(), "Start Date "+day+ "-" +month +"-" + year,Toast.LENGTH_LONG).show();
        filterFrag.start_flag =  filterFrag.getDay(day);
       // Toast.makeText(getActivity(),String.valueOf(filterFrag.start_flag ),Toast.LENGTH_LONG).show();
    }
}
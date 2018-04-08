/*
 * DatePickerFragment
 *
 * March 9, 2018
 *
 * Copyright @ 2018 Team 17, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behaviour at the University of Alberta.
 * You can find a copy of the license in the github wiki for this project.
 */
package professional.team17.com.professional.Dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import professional.team17.com.professional.Activity.RequesterAddTaskActivity;
import professional.team17.com.professional.R;

/**
 * A dialog for picking dates. Used in the RequesterAddTask activity.
 * @author Lauren
 * @see RequesterAddTaskActivity
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    private String formattedDate;

    /**
     * Returns an instance of the date picking dialog fragment, with that day's date selected as
     * the default.
     * @param savedInstanceState The previously saved state.
     * @return An instance of the DatePickerDialog object.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Handles the date selected by the user by converting it to a string.
     * @param view The view object that represents the DatePickerDialog instance
     * @param year The user-selected year
     * @param month The user-selected month
     * @param day The user-selected day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        
        String formattedDate = "dd/MM/yyyy";
        TextView textualDateView = getActivity().findViewById(R.id.textualDateView);
        SimpleDateFormat dateFormat = new SimpleDateFormat(formattedDate, Locale.US);
        textualDateView.setText(dateFormat.format(c.getTime()));

    }

}

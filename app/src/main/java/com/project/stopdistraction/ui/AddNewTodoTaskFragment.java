package com.project.stopdistraction.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.project.stopdistraction.R;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.roomdb.TodoDao;
import com.project.stopdistraction.roomdb.TodoTask;
import com.project.stopdistraction.roomdb.UserDao;
import com.project.stopdistraction.roomdb.UserDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AddNewTodoTaskFragment extends Fragment {
    EditText edtask, edtaskcategory, eddate, edtime;
    Button btnAdd;
    HashMap<Integer, String> MonthNameHashMap = new HashMap<Integer, String>();
    private DailyTask new_dailyTask;
    UserDao userDao;
    private Boolean is_new;

    public AddNewTodoTaskFragment() {
        // Required empty public constructor
    }

    public static AddNewTodoTaskFragment newInstance() {
        AddNewTodoTaskFragment fragment = new AddNewTodoTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_todo_task, container, false);
        edtask = (EditText) view.findViewById(R.id.edTaskName);
        edtaskcategory = (EditText) view.findViewById(R.id.edTaskCategory);
        eddate = (EditText) view.findViewById(R.id.edTaskDate);
        edtime = (EditText) view.findViewById(R.id.edTaskTime);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);

        MonthNameHashMap.put(1, "January");
        MonthNameHashMap.put(2, "February");
        MonthNameHashMap.put(3, "March");
        MonthNameHashMap.put(4, "April");
        MonthNameHashMap.put(5, "May");
        MonthNameHashMap.put(6, "June");
        MonthNameHashMap.put(7, "July");
        MonthNameHashMap.put(8, "August");
        MonthNameHashMap.put(9, "September");
        MonthNameHashMap.put(10, "October");
        MonthNameHashMap.put(11, "November");
        MonthNameHashMap.put(12, "December");

        eddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                String month = MonthNameHashMap.get((monthOfYear + 1));
                                eddate.setText(dayOfMonth + " " + month + " " + year);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        edtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting our hourOfDay, minute.
                int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                String format = "";
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                edtime.setText(hourOfDay + ":" + minute + " " + format);
                            }
                        }, hourOfDay, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtask.getText().toString().isEmpty()) {
                    edtask.setError("Enter Task Name");
                } else if (edtaskcategory.getText().toString().isEmpty()) {
                    edtaskcategory.setError("Enter Task Category");
                } else if (eddate.getText().toString().isEmpty()) {
                    eddate.setError("Select Task Date");
                } else if (edtime.getText().toString().isEmpty()) {
                    edtime.setError("Select Task Time");
                }
                new adduserAsyncTask(edtask.getText().toString(),
                        edtaskcategory.getText().toString(), edtime.getText().toString(),
                        eddate.getText().toString(), false).execute();
            }
        });
        return view;
    }

    private class adduserAsyncTask extends AsyncTask<Void, Void, Boolean> {
        private String task;
        private String category;
        private String time;
        private String date;
        private boolean status;

        private TodoTask new_dailyTask;
        TodoDao userDao;
        private Boolean is_new;

        private adduserAsyncTask(String task, String category, String time, String date, boolean status) {
            this.task = task;
            this.category = category;
            this.time = time;
            this.date = date;
            this.status = status;

            UserDatabase db = UserDatabase.getInstance(getActivity().getApplication());
            this.userDao = db.todoDao();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            new_dailyTask = userDao.getUser(task);
            if (new_dailyTask == null) {
                userDao.insertUser(new TodoTask(this.task, this.category, this.time, this.date, this.status));
                eddate.setText("");
                edtask.setText("");
                edtaskcategory.setText("");
                edtime.setText("");
                is_new = true;
            } else {
                is_new = false;
            }
            return is_new;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (is_new) {
                Toast.makeText(getActivity(), "Daily Task Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Task Already Exists", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
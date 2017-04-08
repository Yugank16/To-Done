package com.example.yugank94.todo_ne;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import static java.text.DateFormat.*;

public class addTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText ename, edate,etime,Desc;
    TextView eprior;
    Button add, setprior;
    int id_rbtn;
    TaskArray taskinfo;
    String user_prrty="medium";
    Calendar calSet, calendar= Calendar.getInstance();  ///for date picker
    Button cal_icon, time_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        ename = (EditText) findViewById(R.id.title_edit_add);
        edate = (EditText) findViewById(R.id.date_edit_add);
        etime = (EditText) findViewById(R.id.time_edit_add);
        eprior = (TextView) findViewById(R.id.priority_display);
        Desc = (EditText) findViewById(R.id.desc_edit_add);

        //**************calender part***********
        cal_icon = (Button) findViewById(R.id.cal_icon);
//
        cal_icon.setOnClickListener(new View.OnClickListener() {               //Its for calender date picker
            @Override
            public void onClick(View v) {
                Toast.makeText(addTask.this, "calender", Toast.LENGTH_SHORT).show();
               new DatePickerDialog(addTask.this,addTask.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //**************
        //**************Time Buttons************
        time_icon= (Button)findViewById(R.id.time_icon);
        time_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(addTask.this,addTask.this, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), false).show();
            }
        });

//************ Add button****************
        add = (Button) findViewById(R.id.addbutton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String title_etext = ename.getText().toString();
                String edate_etext = edate.getText().toString();
                String etime_etext = etime.getText().toString();
                String desc = Desc.getText().toString();
                // String eprior_etext= eprior.getText().toString();
                if (title_etext.isEmpty()) {
                    ename.setError("Field cant be null");
                    return;
                }

                if (edate_etext.isEmpty()) {
                    edate.setError("Field cant be null");
                    return;
                }
                if (etime_etext.isEmpty()) {
                    etime.setError("Field cant be null");
                    return;
                }
                if (desc.isEmpty()) {
                    Desc.setError("Field cant be null");
                    return;
                } else {


                   taskinfo= new TaskArray(title_etext,user_prrty,edate_etext,etime_etext,desc);

                   taskinfo.save();
                    Log.i("TaskID " , "task id : " + taskinfo.getId());
//                    TododbHelper tododbHelper = new TododbHelper(addTask.this);
//                    SQLiteDatabase db = tododbHelper.getWritableDatabase();
//
//
//
//                    ContentValues cv = new ContentValues();
//
//                    cv.put(tododbHelper.TODO_COL_TITLE, title_etext);
//                    cv.put(tododbHelper.TODO_COL_DATE, edate_etext);
//                    cv.put(tododbHelper.TODO_COL_TIME, etime_etext);
//                    cv.put(tododbHelper.TODO_COL_PRIORITY, user_prrty);
//                    cv.put(tododbHelper.TODO_COL_DESC, desc);
//                    db.insert(tododbHelper.TODO_TABLE_NAME, null, cv);

                    Intent add_to_main = new Intent();
                    add_to_main.putExtra("Id",taskinfo.getId());
                    setResult(RESULT_OK, add_to_main);
                    finish();
                }


            }
        });
        //********************************************************
        //********************Set priority button*******************
        setprior = (Button) findViewById(R.id.set_priority);

        setprior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box = new AlertDialog.Builder(addTask.this);
                box.setCancelable(false);
                box.setTitle("Choose Priority");
                final View v1 = getLayoutInflater().inflate(R.layout.priority_dialog, null);
                box.setView(v1);

                box.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroup rgroup = (RadioGroup) v1.findViewById(R.id.radiogroup);
                        id_rbtn = rgroup.getCheckedRadioButtonId();
                        TextView disp = (TextView) findViewById(R.id.priority_display);
                        if (id_rbtn == R.id.low) {
                            disp.setText("Low");
                            user_prrty = "low";
                            disp.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                            disp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                        } else if (id_rbtn == R.id.high) {
                            disp.setText("High");
                            user_prrty = "high";
                            disp.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                            disp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                        } else {

                            disp.setText("Medium");
                            user_prrty = "medium";
                            disp.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                            disp.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                        }


                    }
                });

                box.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                box.create().show();

            }
        });

//**********************************************************************


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        edate.setText(dayOfMonth + "/" + month + "/" + year);
        Log.d("Year","year got"+ year);
        calSet = (Calendar) calendar.clone();
        calSet.set(Calendar.YEAR,year);
        calSet.set(Calendar.MONTH,month);
        calSet.set(Calendar.DAY_OF_MONTH,dayOfMonth);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        etime.setText(hourOfDay+ ":"+ String.format("%02d",minute) );

        boolean isCalset= true;

        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calSet.set(Calendar.MINUTE, minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calendar) < 0){
            //Today Set time passed, count to tomorrow
            //we could also use add method here (Check Androdi Developers)
            isCalset=false;
        }

        if(isCalset== true) {
            Toast.makeText(this, "alarm was set", Toast.LENGTH_SHORT).show();
            setAlarm(calSet);
        }
    }

    private void setAlarm(Calendar targetCal){



        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("title",ename.getText().toString());
        intent.putExtra("desc",Desc.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(this.ALARM_SERVICE);
      alarmManager.set(AlarmManager.RTC_WAKEUP,  targetCal.getTimeInMillis(), pendingIntent);

        //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 5*1000 , pendingIntent);
    }
}

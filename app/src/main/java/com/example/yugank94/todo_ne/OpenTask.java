package com.example.yugank94.todo_ne;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class OpenTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText title_edit, desc_edit;
    Button priority_btn, date_pencil, time_pencil,save;
    TextView date_display, time_display;
    Calendar calendar= Calendar.getInstance();  //for calender
    TaskArray taskinfo_edit;
    int id_rbtn;
    String user_prrty;
    TaskArray task_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);

        Intent i= getIntent();

        final long got_id= i.getLongExtra("db_id",-1);   //id is of long type
        Log.i("TaskID ", "opentask id " + got_id);
        if(got_id!=-1)
        {
            title_edit= (EditText)findViewById(R.id.title_edit);
            desc_edit=(EditText)findViewById(R.id.desc_edit_display);
            priority_btn=(Button) findViewById(R.id.priority_edit);
            priority_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder box = new AlertDialog.Builder(OpenTask.this);

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
                                priority_btn.setText("Low");
                                user_prrty = "low";
                                priority_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_green));
                                priority_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            } else if (id_rbtn == R.id.high) {
                                priority_btn.setText("High");
                                user_prrty = "high";
                                priority_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                                priority_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                            } else {

                                priority_btn.setText("Medium");
                                user_prrty = "medium";
                                priority_btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.yellow));
                                priority_btn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
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
            date_pencil=(Button) findViewById(R.id.date_pencil);
            date_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(OpenTask.this,OpenTask.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            time_pencil=(Button) findViewById(R.id.time_pencil);
            time_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TimePickerDialog(OpenTask.this,OpenTask.this, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), false).show();
                }
            });

            save=(Button) findViewById(R.id.save_change);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskArray task_item_update= TaskArray.load(TaskArray.class,got_id);
                    task_item_update.title= title_edit.getText().toString();
                    task_item_update.date= date_display.getText().toString();
                    task_item_update.time= time_display.getText().toString();
                    task_item_update.priority= user_prrty;
                    task_item_update.Description= desc_edit.getText().toString();
                    task_item_update.save();



                    Intent add_to_main = new Intent();
                    setResult(RESULT_OK, add_to_main);
                    finish();

                }
            });

            date_display=(TextView) findViewById(R.id.date_edit_display);
            time_display=(TextView) findViewById(R.id.time_edit_display);

            task_item= TaskArray.load(TaskArray.class,got_id);

            title_edit.setText(task_item.title);
            date_display.setText(task_item.date);
            time_display.setText(task_item.time);
            desc_edit.setText(task_item.Description);

            if(task_item.priority.equals("high"))          //  1 means highesst
            {

                priority_btn.setText("High");
                user_prrty="high";
                priority_btn.setTextColor(ContextCompat.getColor(OpenTask.this,R.color.white));
                priority_btn.setBackgroundColor(ContextCompat.getColor(OpenTask.this,R.color.red));
            }
            else if(task_item.priority.equals("low"))          //  1 means highesst
            {
                priority_btn.setText("Low");
                user_prrty="low";
                priority_btn.setTextColor(ContextCompat.getColor(OpenTask.this,R.color.white));
                priority_btn.setBackgroundColor(ContextCompat.getColor(OpenTask.this,R.color.dark_green));
            }
            else
            {
                priority_btn.setText("Medium");
                user_prrty="medium";
                priority_btn.setBackgroundColor(ContextCompat.getColor(OpenTask.this,R.color.yellow));
                priority_btn.setTextColor(ContextCompat.getColor(OpenTask.this,R.color.black));
            }
        }

        if(got_id==-1)
        {
            Intent add_to_main = new Intent();
            Toast.makeText(this, "result cancelled", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, add_to_main);
            finish();
        }


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        date_display.setText(dayOfMonth + "/" + month + "/" + year);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time_display.setText(hourOfDay+ ":"+ minute);
    }
}

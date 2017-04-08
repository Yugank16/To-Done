package com.example.yugank94.todo_ne;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView tasks;
    ArrayList<TaskArray> taskarray;
    TaskArrayAdapter task_adapter;

    LinearLayout empty_view;
    RelativeLayout main_view;
    String titles, dates, timings, priorities, descs;
    FloatingActionButton fab;

//    TododbHelper tododbhelper; //This is to access sqlitedatabse


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_view = (RelativeLayout) findViewById(R.id.activity_main);
        taskarray = new ArrayList<>();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_task();
            }
        });

        ActiveAndroid.initialize(this);  //foe active android

       // tododbhelper = new TododbHelper(this);

        tasks = (ListView) findViewById(R.id.lsview);
        task_adapter = new TaskArrayAdapter(this, taskarray);

        tasks.setAdapter(task_adapter);
        setUpViews();

        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TaskArrayAdapter temp_adptr = (TaskArrayAdapter) parent.getAdapter();
                //Toast.makeText(MainActivity.this, temp_adptr.getItem(position).getId()+" ", Toast.LENGTH_LONG).show();
                Intent edit_intent= new Intent(MainActivity.this, OpenTask.class);
                edit_intent.putExtra("db_id",temp_adptr.getItem(position).getId());
                startActivityForResult(edit_intent,3);
            }
        });

        if (taskarray.size() == 0) {
            Toast.makeText(this, "List is Empty", Toast.LENGTH_SHORT).show();

            empty_view= new LinearLayout(this);
                LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,0);
                params.setMargins(0,0,0,0);
                empty_view.setLayoutParams(params);
                main_view.addView(empty_view);
        }




    }

    public void setUpViews() {

        List<TaskArray> list = TaskArray.getData();
        if(list == null || list.size() == 0){
            return;
        }
        taskarray.clear();
        taskarray.addAll(list);
        task_adapter.notifyDataSetChanged();

       // SQLiteDatabase db = tododbhelper.getReadableDatabase();
        //taskarray.clear();
        //taskarray.addAll()

//        Cursor c = db.query(tododbhelper.TODO_TABLE_NAME, null, null, null, null, null, null);
//        while (c.moveToNext()) {
//            int id = c.getInt(c.getColumnIndex(tododbhelper.TODO_COL_ID));
//           // Toast.makeText(this, "id :"+id, Toast.LENGTH_SHORT).show();
//            String title = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_TITLE));
//            String date = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_DATE));
//            String time = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_TIME));
//            String priority = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_PRIORITY));
//            String desc = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_DESC));
//            TaskArray values = new TaskArray(title,priority, date, time,  desc);
//            taskarray.add(values);

        //}


    }
    private void updateList(long id) {

        if(id!= -1) {
            taskarray.add(TaskArray.getIndividual(id));
            task_adapter.notifyDataSetChanged();
        }
    }

//            SQLiteDatabase db = tododbhelper.getReadableDatabase();
//            Cursor c = db.query(tododbhelper.TODO_TABLE_NAME, null, null, null, null, null, null);
//            c.moveToLast();
//            int id = c.getInt(c.getColumnIndex(tododbhelper.TODO_COL_ID));
//        //Toast.makeText(this, "id :"+id, Toast.LENGTH_SHORT).show();
//            String title = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_TITLE));
//            String date = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_DATE));
//            String time = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_TIME));
//            String priority = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_PRIORITY));
//            String desc = c.getString(c.getColumnIndex(tododbhelper.TODO_COL_DESC));
//        TaskArray values = new TaskArray(id,title, priority,date, time, desc);
//        taskarray.add(values);
//
//
//        task_adapter.notifyDataSetChanged();
//        }




    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add) {
            add_task();
        } else if (id == R.id.mark_remove) {


            //remove

        } else if (id == R.id.removeAll) {
            //empty
            TaskArray.del_all();
            if (tasks != null) {
                taskarray.clear();
                tasks.setAdapter(task_adapter);
            }
        }
        else if (id == R.id.feedback) {
            //gmail.feedback
            Intent i = new Intent(Intent.ACTION_SENDTO);
            Uri urid = Uri.parse("mailto:gargya.shelly@gmail.com");
            i.setData(urid);
            i.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }

        } else if (id == R.id.about) {
            //about

            Intent i1 = new Intent((Intent.ACTION_VIEW));
            i1.setData(Uri.parse("http://www.GitHub.com/YugankJr"));
            if (i1.resolveActivity(getPackageManager()) != null) {
                startActivity(i1);
            }
        }
        return true;
    }

    public void add_task() {
        Intent add_task = new Intent();
        add_task.setClass(this, addTask.class);
        startActivityForResult(add_task, 2);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode==2)
            {
                long id = data.getLongExtra("Id",-1);

                Log.i("TaskID ", "onActivityResult id " + id);

                updateList(id);

            }
            else if(requestCode==3)
            {
                Log.d("Intent","requestcode3");
                Snackbar.make(main_view,"Changes updated",Snackbar.LENGTH_LONG).setAction("UNDO",null).show();
                setUpViews();


            }

        }
    }
}




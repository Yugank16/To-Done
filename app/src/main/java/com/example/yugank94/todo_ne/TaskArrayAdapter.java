package com.example.yugank94.todo_ne;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yugank94 on 20/2/17.
 */

public class TaskArrayAdapter extends ArrayAdapter<TaskArray> {
    Context mcontext;
    ArrayList<TaskArray> mtasks;

    public TaskArrayAdapter(Context context,ArrayList<TaskArray> tasks) {
        super(context,0, tasks);
        mcontext= context;
        mtasks= tasks;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= convertView;
        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
           // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = View.inflate(mcontext,R.layout.itemview_linear, null);
        }
        final TaskArray taskview= mtasks.get(position);

        TextView title= (TextView) v.findViewById(R.id.title);
        TextView date= (TextView) v.findViewById(R.id.date);
        TextView time= (TextView) v.findViewById(R.id.time);
        TextView priority= (TextView) v.findViewById(R.id.priority);
        Button tick= (Button) v.findViewById(R.id.complete);
       //tick.setClickable(false);
        Button trash= (Button) v.findViewById(R.id.delete);
       //trash.setClickable(false);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskArray.del_individual(taskview.getId());


               notifyDataSetChanged();

            }
        });




        title.setText(taskview.title);
        time.setText(taskview.time);
        date.setText(taskview.date);
        String pri_check= taskview.priority;

        if(pri_check.equals("high"))          //  1 means highesst
        {

            priority.setText("High");
            priority.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
            priority.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.red));
        }
        else if(pri_check.equals("low"))          //  1 means highesst
        {
            priority.setText("Low");
            priority.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
            priority.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.dark_green));
        }
        else
        {
            priority.setText("Medium");
            priority.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.yellow));
            priority.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        }



        return v;

    }
}

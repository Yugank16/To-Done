package com.example.yugank94.todo_ne;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yugank94 on 20/2/17.
 */
@Table(name="Tododb")
public class TaskArray extends Model{

    //in active android these members should be public
    @Column(name="title")
     public String title;
    @Column(name="priority")
    public String priority;
    @Column(name="date")
    public String date;
    @Column(name="time")
    public String time;
    @Column(name="description")
    public String Description;

    public TaskArray()
    {
        super();
    }

    public  TaskArray(String title, String priority, String date, String time,String Description)
    {
        super();

        this.title= title;
        this.priority= priority;
        this.date= date;
        this.time= time;
        this.Description= Description;
    }

    public static List<TaskArray> getData()
    {
        return new Select().from(TaskArray.class).execute();
    }


    public static TaskArray getIndividual(long id)
    {
        return new Select().from(TaskArray.class).where("id = ?", id).executeSingle();
    }

    public static void del_individual(long id){

        new Delete().from(TaskArray.class).where("id = ?",id).execute();
    }

    public static void del_all(){

        new Delete().from(TaskArray.class).execute();
    }





//    ****** This is a example of active android on github***********
//    public static List<Item> getAll(Category category) {
//
//        // This is how you execute a query
//
//        return new Select()
//
//                .from(Item.class)
//
//                .where("Category = ?", category.getId())
//
//                .orderBy("Name ASC")
//
//                .execute();


}

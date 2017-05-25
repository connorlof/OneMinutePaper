package com.loftydevelopment.oneminutepaper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by connorlof on 5/14/2017.
 */

public class PaperHistoryFragment extends Fragment{

    View view;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> mainIdeas = new ArrayList<>();
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<Integer> idList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView listView;

    SQLiteDatabase paperDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paper_history, container, false);

        listView = (ListView) view.findViewById(R.id.paperArchiveList);

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, titles);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), DisplayPaperActivity.class);
                intent.putExtra("titles", titles.get(i));
                intent.putExtra("mainIdeas", mainIdeas.get(i));
                intent.putExtra("questions", questions.get(i));


                startActivity(intent);

            }
        });

        //For deleting entries
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemToDelete = i;

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this paper?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int idToDelete = idList.get(itemToDelete);

                                paperDatabase.execSQL("DELETE FROM papers WHERE id = " + idToDelete);

                                updateListView();

                            }
                        }).setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        try{

            paperDatabase = getActivity().openOrCreateDatabase("Papers", MODE_PRIVATE, null);

            paperDatabase.execSQL("CREATE TABLE IF NOT EXISTS papers (subject VARCHAR, mainideas VARCHAR, questions VARCHAR, id INTEGER PRIMARY KEY)");

            //paperDatabase.execSQL("INSERT INTO papers (subject, mainideas, questions) VALUES ('Physics 3/14/17', 'Center of mass', 'Is the center of mass only good for constant velocity?')");


        }catch(Exception e){

            e.printStackTrace();

        }

        updateListView();

        return view;
    }


    public void updateListView(){

        Cursor c = paperDatabase.rawQuery("SELECT * FROM papers", null);

        int subjectIndex = c.getColumnIndex("subject");
        int mainideasIndex = c.getColumnIndex("mainideas");
        int questionsIndex = c.getColumnIndex("questions");
        int idIndex = c.getColumnIndex("id");

        titles.clear();
        mainIdeas.clear();
        questions.clear();
        idList.clear();

        //Display newest first
        if(c.moveToLast()){
            do {

                titles.add(c.getString(subjectIndex));
                mainIdeas.add(c.getString(mainideasIndex));
                questions.add(c.getString(questionsIndex));
                idList.add(c.getInt(idIndex));

            } while(c.moveToPrevious());

            Log.i("Titles List", titles.toString());

        }

        arrayAdapter.notifyDataSetChanged();

    }

}

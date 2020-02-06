package com.loftydevelopment.oneminutepaper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.loftydevelopment.oneminutepaper.adapters.PaperAdapter;
import com.loftydevelopment.oneminutepaper.model.Paper;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;
import com.loftydevelopment.oneminutepaper.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PaperHistoryFragment extends Fragment {

    private List<Paper> savedPapers = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    private PaperDatabase paperRoomDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paper_history, container, false);

        paperRoomDatabase = PaperDatabase.getInstance(getContext());

        ListView listView = view.findViewById(R.id.paperArchiveList);
        arrayAdapter = new PaperAdapter(getContext(), android.R.layout.simple_list_item_1, savedPapers);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DisplayPaperActivity.class);
                intent.putExtra("titles", savedPapers.get(i).getSubject());
                intent.putExtra("mainIdeas", savedPapers.get(i).getMainIdeas());
                intent.putExtra("questions", savedPapers.get(i).getQuestions());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Paper paperToDelete = savedPapers.get(i);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        paperRoomDatabase.paperDao().deletePaper(paperToDelete);
                    }
                });

                updateListView();

                Snackbar.make(view, "Your paper was deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            paperRoomDatabase.paperDao().insertPaper(paperToDelete);
                            updateListView();
                        }
                }).show();

                return true;
            }
        });

        updateListView();

        return view;
    }


    public void updateListView(){

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                savedPapers = paperRoomDatabase.paperDao().loadAllPapers();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

}

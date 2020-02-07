package com.loftydevelopment.oneminutepaper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.loftydevelopment.oneminutepaper.adapters.PaperAdapter;
import com.loftydevelopment.oneminutepaper.model.Paper;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;
import com.loftydevelopment.oneminutepaper.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PaperHistoryFragment extends Fragment implements PaperAdapter.ItemClickListener, PaperAdapter.OnItemLongClickListener {

    private View view;

    private List<Paper> savedPapers = new ArrayList<>();
    private List<String> paperNames = new ArrayList<>();
    private PaperAdapter paperAdapter;

    private PaperDatabase paperRoomDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paper_history, container, false);

        paperRoomDatabase = PaperDatabase.getInstance(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.rv_papers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        paperAdapter = new PaperAdapter(getContext(), paperNames);
        //paperAdapter.setClickListener(this);
        recyclerView.setAdapter(paperAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getContext(), DisplayPaperActivity.class);
//                intent.putExtra("titles", savedPapers.get(i).getSubject());
//                intent.putExtra("mainIdeas", savedPapers.get(i).getMainIdeas());
//                intent.putExtra("questions", savedPapers.get(i).getQuestions());
//                startActivity(intent);
//            }
//        });
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final Paper paperToDelete = savedPapers.get(i);
//
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        paperRoomDatabase.paperDao().deletePaper(paperToDelete);
//                    }
//                });
//
//                updateListView();
//
//                Snackbar.make(view, "Your paper was deleted", Snackbar.LENGTH_LONG)
//                        .setAction("Undo", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            paperRoomDatabase.paperDao().insertPaper(paperToDelete);
//                            updateListView();
//                        }
//                }).show();
//
//                return true;
//            }
//        });

        updateListView();

        return view;
    }

    public void updateListView(){

        paperNames.clear();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                savedPapers = paperRoomDatabase.paperDao().loadAllPapers();

                for(Paper paper : savedPapers) {
                    paperNames.add(paper.getSubject());
                }

                paperAdapter.notifyDataSetChanged();


                Log.d("PaperHistory", "Paper list appex: " + savedPapers.size());
//                arrayAdapter.setNewItems(savedPapers);

//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        arrayAdapter.setNewItems(savedPapers);
//                        //arrayAdapter.notifyDataSetChanged();
//
//                        Log.d("PaperHistory", "Paper list ui: " + savedPapers.size());
//                    }
//                });

            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), DisplayPaperActivity.class);

        intent.putExtra("titles", savedPapers.get(position).getSubject());
        intent.putExtra("mainIdeas", savedPapers.get(position).getMainIdeas());
        intent.putExtra("questions", savedPapers.get(position).getQuestions());

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClicked(int position) {

        final Paper paperToDelete = savedPapers.get(position);

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

        return false;
    }
}

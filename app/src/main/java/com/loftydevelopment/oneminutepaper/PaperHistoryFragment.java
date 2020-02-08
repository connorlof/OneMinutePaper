package com.loftydevelopment.oneminutepaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.loftydevelopment.oneminutepaper.adapters.PaperAdapter;
import com.loftydevelopment.oneminutepaper.model.Paper;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;
import com.loftydevelopment.oneminutepaper.util.AppExecutors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaperHistoryFragment extends Fragment implements PaperAdapter.OnPaperListener {

    private View view;

    private List<Paper> savedPapers = new ArrayList<>();
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
        paperAdapter = new PaperAdapter(this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(paperAdapter);

        updateListView();

        return view;
    }



    public void updateListView(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                savedPapers = paperRoomDatabase.paperDao().loadAllPapers();
                Collections.reverse(savedPapers);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        paperAdapter.setPapers(savedPapers);
                    }
                });

            }
        });
    }

    @Override
    public void onPaperClick(Paper paper) {
        Intent intent = new Intent(getContext(), DisplayPaperActivity.class);

        intent.putExtra("titles", paper.getSubject());
        intent.putExtra("mainIdeas", paper.getMainIdeas());
        intent.putExtra("questions", paper.getQuestions());

        startActivity(intent);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    final Paper paper = paperAdapter.getPaper(viewHolder.getAdapterPosition());
                    paperAdapter.removePaper(paper);

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            paperRoomDatabase.paperDao().deletePaper(paper);
                        }
                    });

                    updateListView();

                    Snackbar.make(view, "Your paper was deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            paperRoomDatabase.paperDao().insertPaper(paper);
                                        }
                                    });
                                    updateListView();
                                }
                            }).show();

                }
            };

}

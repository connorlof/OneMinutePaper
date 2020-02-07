package com.loftydevelopment.oneminutepaper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.loftydevelopment.oneminutepaper.model.Paper;
import com.loftydevelopment.oneminutepaper.persistence.PaperDatabase;
import com.loftydevelopment.oneminutepaper.util.AppExecutors;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class WritePaperFragment extends Fragment implements View.OnClickListener{

    private EditText editTextSubject, editTextMainIdeas, editTextQuestions;
    private RelativeLayout layoutSubject, layoutIdeas, layoutQuestions;
    private PaperDatabase paperRoomDatabase;

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write_paper, container, false);
        initializeWidgets();
        return view;
    }

    private void initializeWidgets() {
        layoutSubject =  view.findViewById(R.id.layoutSubject);
        layoutIdeas = view.findViewById(R.id.layoutIdeas);
        layoutQuestions = view.findViewById(R.id.layoutQuestions);

        editTextSubject = view.findViewById(R.id.etSubject);
        editTextMainIdeas = view.findViewById(R.id.etMainIdeas);
        editTextQuestions = view.findViewById(R.id.etQuestions);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "DansHandWriting.ttf");
        editTextSubject.setTypeface(font);
        editTextMainIdeas.setTypeface(font);
        editTextQuestions.setTypeface(font);

        editTextMainIdeas.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextMainIdeas.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        editTextQuestions.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextQuestions.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        TextView tvInfoTitle = view.findViewById(R.id.tvInfoTitle);
        TextView tvInfo1 = view.findViewById(R.id.tvInfo1);
        TextView tvInfo2 = view.findViewById(R.id.tvInfo2);
        TextView tvInfo3 = view.findViewById(R.id.tvInfo3);
        TextView tvInfo4 = view.findViewById(R.id.tvInfo4);

        tvInfoTitle.setTypeface(font);
        tvInfo1.setTypeface(font);
        tvInfo2.setTypeface(font);
        tvInfo3.setTypeface(font);
        tvInfo4.setTypeface(font);

        Button b1 = view.findViewById(R.id.toIdeasButton);
        Button b2 = view.findViewById(R.id.toQuestionsButton);
        Button b3 = view.findViewById(R.id.submitButton);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.toIdeasButton:
                String subjectStr = editTextSubject.getText().toString().trim();

                if(subjectStr.equals("")){
                    Snackbar.make(view, "You forgot to enter a class!", Snackbar.LENGTH_SHORT).show();
                }else{
                    layoutIdeas.setVisibility(View.VISIBLE);
                    layoutSubject.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.toQuestionsButton:
                String mainIdeasStr = editTextMainIdeas.getText().toString().trim();

                if(mainIdeasStr.equals("")){
                    Snackbar.make(view, "You didn't enter any main ideas!", Snackbar.LENGTH_SHORT).show();
                }else{
                    layoutQuestions.setVisibility(View.VISIBLE);
                    layoutIdeas.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.submitButton:
                String questionsStr = editTextQuestions.getText().toString().trim();

                if(questionsStr.equals("")){
                    Snackbar.make(view, "You didn't enter any questions!", Snackbar.LENGTH_SHORT).show();
                }else{
                    //Get current date
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                    String date = sdf.format(new Date(System.currentTimeMillis()));

                    subjectStr = editTextSubject.getText() + " (" + date + ")";
                    mainIdeasStr = String.valueOf(editTextMainIdeas.getText());
                    questionsStr = String.valueOf(editTextQuestions.getText());

                    final Paper paper = new Paper(subjectStr, mainIdeasStr, questionsStr);

                    paperRoomDatabase = PaperDatabase.getInstance(getContext());

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            paperRoomDatabase.paperDao().insertPaper(paper);
                        }
                    });

                    Intent intent = new Intent(getContext(), DisplayPaperActivity.class);
                    intent.putExtra("titles", subjectStr);
                    intent.putExtra("mainIdeas", mainIdeasStr);
                    intent.putExtra("questions", questionsStr);

                    startActivity(intent);

                }
                break;
            default:
                break;
        }

    }


}

package com.loftydevelopment.oneminutepaper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by connorlof on 5/14/2017.
 */

public class WritePaperFragment extends Fragment implements View.OnClickListener{

    EditText editTextSubject, editTextMainIdeas, editTextQuestions;
    RelativeLayout layoutSubject, layoutIdeas, layoutQuestions;
    String subjectStr, mainIdeasStr, questionsStr;
    TextView tvInfoTitle, tvInfo1, tvInfo2, tvInfo3, tvInfo4;


    SQLiteDatabase paperDatabase;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_write_paper, container, false);

        layoutSubject = (RelativeLayout) view.findViewById(R.id.layoutSubject);
        layoutIdeas = (RelativeLayout) view.findViewById(R.id.layoutIdeas);
        layoutQuestions = (RelativeLayout) view.findViewById(R.id.layoutQuestions);

        editTextSubject = (EditText) view.findViewById(R.id.etSubject);
        editTextMainIdeas = (EditText) view.findViewById(R.id.etMainIdeas);
        editTextQuestions = (EditText) view.findViewById(R.id.etQuestions);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "DansHandWriting.ttf");
        editTextSubject.setTypeface(font);
        editTextMainIdeas.setTypeface(font);
        editTextQuestions.setTypeface(font);

        editTextMainIdeas.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editTextMainIdeas.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        editTextQuestions.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextQuestions.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        tvInfoTitle = (TextView) view.findViewById(R.id.tvInfoTitle);
        tvInfo1 = (TextView) view.findViewById(R.id.tvInfo1);
        tvInfo2 = (TextView) view.findViewById(R.id.tvInfo2);
        tvInfo3 = (TextView) view.findViewById(R.id.tvInfo3);
        tvInfo4 = (TextView) view.findViewById(R.id.tvInfo4);

        tvInfoTitle.setTypeface(font);
        tvInfo1.setTypeface(font);
        tvInfo2.setTypeface(font);
        tvInfo3.setTypeface(font);
        tvInfo4.setTypeface(font);

        Button b1 = (Button) view.findViewById(R.id.toIdeasButton);
        Button b2 = (Button) view.findViewById(R.id.toQuestionsButton);
        Button b3 = (Button) view.findViewById(R.id.submitButton);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.toIdeasButton:

                subjectStr = String.valueOf(editTextSubject.getText());

                if(subjectStr == null || subjectStr.equals("")){

                    Toast.makeText(getContext(), "You forgot to enter a class!", Toast.LENGTH_SHORT).show();

                }else{

                    layoutIdeas.setVisibility(View.VISIBLE);
                    layoutSubject.setVisibility(View.INVISIBLE);

                }

                break;
            case R.id.toQuestionsButton:

                mainIdeasStr = String.valueOf(editTextMainIdeas.getText());

                if(mainIdeasStr == null || mainIdeasStr.equals("")){

                    Toast.makeText(getContext(), "You didn't enter any main ideas!", Toast.LENGTH_SHORT).show();

                }else{

                    layoutQuestions.setVisibility(View.VISIBLE);
                    layoutIdeas.setVisibility(View.INVISIBLE);

                }


                break;
            case R.id.submitButton:

                questionsStr = String.valueOf(editTextQuestions.getText());

                if(questionsStr == null || questionsStr.equals("")){

                    Toast.makeText(getContext(), "You didn't enter any questions!", Toast.LENGTH_SHORT).show();

                }else{

                    //Get current date
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                    String date = sdf.format(new Date(System.currentTimeMillis()));

                    subjectStr = String.valueOf(editTextSubject.getText() + " (" + date + ")");
                    mainIdeasStr = String.valueOf(editTextMainIdeas.getText());
                    questionsStr = String.valueOf(editTextQuestions.getText());

                    try{

                        paperDatabase = getActivity().openOrCreateDatabase("Papers", MODE_PRIVATE, null);

                        paperDatabase.execSQL("CREATE TABLE IF NOT EXISTS papers (subject VARCHAR, mainideas VARCHAR, questions VARCHAR)");

                        paperDatabase.execSQL("INSERT INTO papers (subject, mainideas, questions) VALUES (?, ?, ?)", new String[] { subjectStr, mainIdeasStr, questionsStr});

                    }catch(Exception e){

                        e.printStackTrace();

                    }

                    Toast.makeText(getContext(), "Paper saved!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), DisplayPaperActivity.class);
                    intent.putExtra("titles", subjectStr);
                    intent.putExtra("mainIdeas", mainIdeasStr);
                    intent.putExtra("questions", questionsStr);

                    startActivity(intent);

                    //layoutSubject.setVisibility(View.VISIBLE);
                    //layoutQuestions.setVisibility(View.INVISIBLE);

            }
                break;

        }

    }


}

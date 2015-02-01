package com.d3von.amortizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by devon on 1/29/15.
 */
public class ResultFragment extends Fragment {

    // might need to be in MainActivity.java
    protected boolean resultExists = false;
    private static TextView resultText;


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View view = null;
        // This block should print something like:
        //          Given those three variables, \nPrincipal is $50,000
        if(resultExists) {

            final Button clearButton = (Button) view.findViewById(R.id.button_ClearAll);
            final Button showAmortButton = (Button) view.findViewById(R.id.button_ShowAmort);
            view = inflater.inflate(R.layout.result_fragment, container, false);

            resultText = (TextView) view.findViewById(R.id.resultText);


            clearButton.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            //ToDo: do clear stuff, whatever that is.
                        }
                    }
            );


            showAmortButton.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            //ToDo: do ShowAmort stuff, whatever that is.
                            /*
                            * I'm guessing that means set up an intent and fire it off
                            * to create a new activity, which would handle presenting
                            * the amortization schedule.
                            *
                            *
                            *
                            * */
                        }
                    }
            );
        } // end if (resultExists)

        return view;
    }

    public void setResultText(String resultText)
}

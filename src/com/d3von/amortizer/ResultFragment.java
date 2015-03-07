package com.d3von.amortizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;
import android.widget.Button;

public class ResultFragment  extends Fragment {

    public static TextView theResultText;
    public static String resultTxt = "";
    public static View view;

    private ResultFragmentListener activityDoButtons;

    // theNewBoston's way for the above interface:
    public interface ResultFragmentListener{
        public void clearResult();
        /* to be implemented by MainActivity.java
         * responsible for receiving those text values from InputFragment.java,
         * processing the values input by the user, and passing the result to
         * the ResultFragment.java
         * */

        void createAmortization();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityDoButtons = (ResultFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ResultFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.result_fragment, container, false);
        final Button clearButton = (Button) view.findViewById(R.id.button_ClearAll);
        final Button showAmortButton = (Button) view.findViewById(R.id.button_ShowAmort);

        /* 			Oh		My		Gods!
         * 	This is how I finally was able to update a TextView with a value
         * 	originating in another Fragment, then passed to the Activity, then
         * 	finally, using the Bundle method getArguments(), I was able to
         * 	access the value I stored in that Bundle back in the Activity.
         *
         *         NOTE THAT THERE ARE OTHER WAYS TO SEND INFO TO A FRAGMENT
         */
        theResultText = (TextView) view.findViewById(R.id.resultText);
        resultTxt = (getArguments() != null) ? getArguments().getString(ResultFragment.resultTxt) : "";

        theResultText.setText(resultTxt);

        clearButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        onClearButtonClicked(v);
                    }
                }
        );

        showAmortButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        onAmortButtonClicked(v);
                    }
                }
        );
        return view;

    }

    /*called when the "Clear All" button is clicked
     */
    public void onClearButtonClicked(View v) {
        //clearResult();
        // send the data to MainActivity.java
        // NOTE: this obj and method are implemented in MainActivity.java, though
        // the interface and method signature are declared in this class.
        activityDoButtons.clearResult();
    }


    public void onAmortButtonClicked(View v){
        // I've decided this needs to be a Fragment (a dynamic one)
        // trigger loading the next dynamic fragment

        // this thing should produce data for the whole page
        // either as a big string (of html?) or as data fields for the layout to present
        //AmortSched amortSched = new AmortSched();

        activityDoButtons.createAmortization();

    }


    public void setResultText(String resultString) {
        theResultText = (TextView) view.findViewById(R.id.resultText);
        try{
            // I shouldn't need the above because it's a static data member to this class.  Right?
            theResultText.setText(resultString);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void clearResult() {
        this.isHidden();  // I never knew, or forgot, what this is.
        //resultText.setText("");
    }

}
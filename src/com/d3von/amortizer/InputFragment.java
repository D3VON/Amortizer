package com.d3von.amortizer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class InputFragment extends Fragment {

    protected static final String TAG = "Amortizer";
    protected Amortization AmortObj = new Amortization();

    // Initialize UI elements (assign values in onCreateView)
    private static EditText principal;
    private static EditText rate;
    private static EditText term;
    private static EditText payment;

    private InputFragmentListener activityCommander;


    // theNewBoston's way for the above interface:
    public interface InputFragmentListener{
        public void createResult(String resultPhrase, String prin, String intr, String peri, String paym);
        /* to be implemented by MainActivity.java
         * responsible for receiving those text values from InputFragment.java,
         * processing the values input by the user, and passing the result to
         * the ResultFragment.java
         * */
    }

    public InputFragment() {
        // Default constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander = (InputFragmentListener) activity; // theNewBoston's naming convention
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement InputFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        principal 	= (EditText) view.findViewById(R.id.principal);
        rate 		= (EditText) view.findViewById(R.id.rate);
        term 		= (EditText) view.findViewById(R.id.term);
        payment 	= (EditText) view.findViewById(R.id.payment);
        final Button button_calc = (Button)   view.findViewById(R.id.button_calc);

        // set up a listener on that button
        button_calc.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        onCalcButtonClicked(v);
                    }
                }
        );

        return view;

    }// end onCreateView()


    // called when the "Calculate" button is clicked
    /*
     * actually do the calculations,
     * then assemble the resultText value (to be show by ResultFragment.java)
     */
    public void onCalcButtonClicked(View v){


        String prin = principal.getText().toString();
        String intr = rate.getText().toString();
        String peri = term.getText().toString();
        String paym = payment.getText().toString();
        String resultPhrase = "";


        boolean A = prin.trim().equals("");
        boolean B = intr.trim().equals("");
        boolean C = peri.trim().equals("");
        boolean D = paym.trim().equals("");
        // True means EMPTY
        if (A && (B||C||D)){
            Toast.makeText(getActivity().getBaseContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
        } else if (B && (A||C||D)){
            Toast.makeText(getActivity().getBaseContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
        } else if (C && (A||B||D)){
            Toast.makeText(getActivity().getBaseContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
        } else if (D && (A||B||C)){
            Toast.makeText(getActivity().getBaseContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
        } else  if (!(A||B||C||D)){
            Toast.makeText(getActivity().getBaseContext(), "Please Enter only 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getBaseContext(), "OK.  Good.", Toast.LENGTH_LONG).show();
            if(A){
                double interest = Double.parseDouble(intr);
                int period 	= Integer.parseInt(peri);
                double pmt 		= Double.parseDouble(paym);
                double princ 	= AmortObj.calculatePrincipal(interest, pmt, period);
                prin = String.format( "%.2f", princ ); // format to financial style
                Log.v(TAG, prin); // for testing satisfaction
                principal.setText(prin); // update what's displayed to the user
                resultPhrase = "Principal Amount is " + principal.getText().toString();
            }
            else if(B){
                double princ 	= Double.parseDouble(prin);
                int period 	= Integer.parseInt(peri);
                double pmt 		= Double.parseDouble(paym);
                double interest	= AmortObj.calculateInterest(princ, period, pmt);
                intr = String.format("%.2f", interest ); // format to financial style
                Log.v(TAG, intr); // for testing satisfaction
                rate.setText(intr); // update what's displayed to the user
                resultPhrase = "Result: Interest Rate is " + rate.getText().toString();
            }
            else if(C){
                double princ 	= Double.parseDouble(prin);
                double interest = Double.parseDouble(intr);
                double pmt 		= Double.parseDouble(paym);
                int period	= AmortObj.numPmts(princ, interest, pmt);
                peri = String.format( "%d", period ); // format to financial style
                Log.v(TAG, peri); // for testing satisfaction
                term.setText(peri); // update what's displayed to the user
                resultPhrase = "Result: Term (in months) is " + term.getText().toString();
            }
            else {
                double princ 	= Double.parseDouble(prin);
                double interest = Double.parseDouble(intr);
                int period 	= Integer.parseInt(peri);
                double pmt 	= AmortObj.calcPmtAmt(princ, interest, period);
                paym = String.format( "%.2f", pmt ); // format to financial style
                Log.v(TAG, paym); // for testing satisfaction
                payment.setText(paym); // update what's displayed to the user
                resultPhrase = "Result: Payment is " + payment.getText().toString();
            }
        }

        // send the data to MainActivity.java
        // NOTE: this obj and method are implemented in MainActivity.java, though
        // the interface and method signature are declared in this class.
        activityCommander.createResult(resultPhrase, prin, intr, peri, paym);
    }

    public void clearFields(){
        principal.setText("");
        payment.setText("");
        term.setText("");
        rate.setText("");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCommander = null;
    }


}
package com.d3von.amortizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.net.Uri;
=


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment {


    // Initialize UI elements (assign variables in onCreateView)
    public static EditText principal;
    public static EditText rate;
    public static EditText term;
    public static EditText payment;

    private InputFragmentListener activityCommander; // theNewBoston has it this way instead
    // private OnFragmentInteractionListener mListener; // Android Studio's default version

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
     */

    // theNewBoston's way for the above interface:
    public interface InputFragmentListener{
        public void createResult(String princ, String term, String rate, String pmt);
        /* to be implemented by MainActivity.java
         * responsible for receiving those text values from InputFragment.java,
         * processing the values input by the user, and passing the result to
         * the ResultFragment.java
         * */
    }






    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputFragment newInstance(String param1, String param2) {
        InputFragment fragment = new InputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout. fragment_input, container, false);

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
    } // end onCreateView()

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
            }
            else if(B){
                double princ 	= Double.parseDouble(prin);
                int period 	= Integer.parseInt(peri);
                double pmt 		= Double.parseDouble(paym);
                double interest	= AmortObj.calculateInterest(princ, period, pmt);
                intr = String.format( "%.2f", interest ); // format to financial style
                Log.v(TAG, intr); // for testing satisfaction
                rate.setText(intr); // update what's displayed to the user
            }
            else if(C){
                double princ 	= Double.parseDouble(prin);
                double interest = Double.parseDouble(intr);
                double pmt 		= Double.parseDouble(paym);
                int period	= AmortObj.numPmts(princ, interest, pmt);
                peri = String.format( "%d", period ); // format to financial style
                Log.v(TAG, peri); // for testing satisfaction
                term.setText(peri); // update what's displayed to the user
            }
            else {
                double princ 	= Double.parseDouble(prin);
                double interest = Double.parseDouble(intr);
                int period 	= Integer.parseInt(peri);
                double pmt 	= AmortObj.calcPmtAmt(princ, interest, period);
                paym = String.format( "%.2f", pmt ); // format to financial style
                Log.v(TAG, paym); // for testing satisfaction
                payment.setText(paym); // update what's displayed to the user
            }
        }













        // send the data to MainActivity.java
        // NOTE: this obj and method are implemented in MainActivity.java, though
        // the interface and method signature are declared in this class.
        activityCommander.createResult(principal.toString(),term.toString(),rate.toString(),payment.toString());
    }
/* COOL: THEIR VERSION OF onCalcButtonClicked() see right above here
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Android Studio's default: mListener = (OnFragmentInteractionListener) activity;
            activityCommander = (InputFragmentListener) activity; // theNewBoston's naming convention
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}

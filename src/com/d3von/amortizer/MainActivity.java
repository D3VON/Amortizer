package com.d3von.amortizer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements InputFragment.InputFragmentListener,
        ResultFragment.ResultFragmentListener, AmortFragment.AmortFragmentCommunicator {

    private FragmentManager manager;
    private String prin;
    private String intr;
    private String peri;
    private String paym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //theTable=(TableLayout)findViewById(R.id.the_table);

        if(savedInstanceState == null){
            Fragment inputFragment  = new InputFragment();

            manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container_top,inputFragment,"inputfrag").commit();
        }
    }

    //This gets called by the Input Fragment when the user clicks the Calculate button
    @Override
    public void createResult(String resultPhrase, String princ, String intre, String perio, String payme) {
        prin = princ;
        intr = intre;   // store data from user input and calculated 4th value
        peri = perio;   // for use in AmortFragment.java
        paym = payme;

        ResultFragment resultFragment = (ResultFragment) manager.findFragmentByTag("resultfragtag");

        if (resultFragment != null){
			/* if resultFragment is available, we can modify it's TextView
			 * by calling the method to do the updating
			 */
            //ResultFragment.theResultText.setText(resultPhrase);
            resultFragment.setResultText(resultPhrase);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(resultFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
			/*...otherwise the fragment has to be created */
            ResultFragment newResultFragment = new ResultFragment();
            Bundle args = new Bundle();
            args.putString(ResultFragment.resultTxt, resultPhrase);
            newResultFragment.setArguments(args);
            //newResultFragment.setResultText(resultPhrase);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container_bottom, newResultFragment,"resultfragtag");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


    //This gets called by the Result Fragment when the user clicks the Show Amortization button
    @Override
    public void createAmortization() {

			/*... the fragment always has to be created to be safe */
            AmortFragment newAmortFragment = new AmortFragment();
            newAmortFragment.setAmortInputValues(prin, intr, peri, paym);
            //criticism: tightly coupled. There are like 3 other ways to do this.

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(getFragmentManager().findFragmentByTag("inputfrag"));
            transaction.hide(getFragmentManager().findFragmentByTag("resultfragtag"));
            transaction.add(R.id.fragment_container_top, newAmortFragment,"amortfragtag");
            transaction.addToBackStack(null);
            transaction.commit();
            //newAmortFragment.doAmortTable(prin, intr,peri,paym);

    }



    //This gets called in the ResultFragment when the user clicks the Clear All button
    @Override
    public void clearResult() {
        InputFragment inp_frag = (InputFragment) getFragmentManager().findFragmentByTag("inputfrag");
        inp_frag.clearFields();

        Fragment res_frag = getFragmentManager().findFragmentByTag("resultfragtag");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //res_frag.clearResult();
        transaction.hide(res_frag);
        transaction.commit();
    }



    @Override
    public void backButton() {
        InputFragment inp_frag = (InputFragment) getFragmentManager().findFragmentByTag("inputfrag");
        ResultFragment res_frag = (ResultFragment) getFragmentManager().findFragmentByTag("resultfragtag");
        AmortFragment amort_frag = (AmortFragment) getFragmentManager().findFragmentByTag("amortfragtag");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(amort_frag);
        transaction.show(inp_frag);
        transaction.show(res_frag);
        transaction.commit();

    }

    @Override
    public void shareButton() {
        Toast.makeText(this, "NOT YET WORKING. \n To be implemented in next version.", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_help:
                Toast.makeText(this, "It doesn't matter which you leave empty \n-- that's the one the app will calculated.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "Next version will allow saving, and sharing (via email).", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}



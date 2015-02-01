package com.d3von.amortizer;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements InputFragment.InputFragmentListener{

	protected static final String TAG = "Amortizer";
	protected Amortization AmortObj = new Amortization();

    // implementing method from interface InputFragment.InputFragmentListener
    // This gets called by the InputFragment when the user clicks the "Calculate" button.
    @Override
    public void createResult(String princ, String term, String rate, String pmt) {

    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
/* PUT INTO InputFragment.java
		// Initialize UI elements
		final EditText principal 	= (EditText)findViewById(R.id.principal);
		final EditText rate 		= (EditText)findViewById(R.id.rate);
		final EditText term 		= (EditText)findViewById(R.id.term);
		final EditText payment 		= (EditText)findViewById(R.id.payment);
		final Button   button_calc 	= (Button)findViewById(R.id.button_calc);
		
*/
		
		// Link UI elements to actions
		button_calc.setOnClickListener(
                new Button.OnClickListener(){
			@SuppressLint("DefaultLocale") // locale info makes formating decimal wonkie regionally
			@Override
			public void onClick(View v){

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
					Toast.makeText(getApplicationContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
				} else if (B && (A||C||D)){
					Toast.makeText(getApplicationContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
				} else if (C && (A||B||D)){
					Toast.makeText(getApplicationContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
				} else if (D && (A||B||C)){
					Toast.makeText(getApplicationContext(), "Please Enter 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
				} else  if (!(A||B||C||D)){
					Toast.makeText(getApplicationContext(), "Please Enter only 3 pieces of information.  The app will calculate the one left empty.", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "OK.  Good.", Toast.LENGTH_LONG).show();
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

			}

		});
			
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_help:
	            Toast.makeText(this, "It doesn't matter which you leave empty--that's the one the app will calcualte.", Toast.LENGTH_LONG).show();
	            return true;
	        case R.id.action_about:
	            Toast.makeText(this, "Amortization schedule (as a spreadsheet) will be added in a forthcoming version.", Toast.LENGTH_LONG).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	

}

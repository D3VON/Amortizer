package com.d3von.amortizer;

import java.text.DecimalFormat;
import java.util.Iterator;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class AmortFragment extends Fragment {
	
    public static View view;
    TableLayout table;
    public static String prin;
    public static String intr;
    public static String peri;
    public static String paym;
    public static String principal;  //better formated string like: "$4,321.00
    public static String payment;    //better formated string like: "$4,321.00


    private AmortFragmentCommunicator buttonCommunicator;

    // establish interface for communication to/from this Fragment
    public interface AmortFragmentCommunicator{
        public void backButton();
        /* to be implemented by MainActivity.java */
		void shareButton();
    }
    
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
	        buttonCommunicator = (AmortFragmentCommunicator) activity;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(activity.toString()
	                + " must implement AmortFragmentCommunicator");
	    }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_amort, container, false);
        final Button backButton = (Button) view.findViewById(R.id.button_back);
        final Button shareButton = (Button) view.findViewById(R.id.button_share);
        
        doAmortTable();
        backButton.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v){
                    onBackButtonClicked(v);
                }
            }
        );

        shareButton.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v){
                    onShareButtonClicked(v);
                }
            }
        );
        
        return view;
    }
    

    /*called when the "Clear All" button is clicked
     */
    public void onBackButtonClicked(View v) {
        //clearResult();
        // send the data to MainActivity.java
        // NOTE: this obj and method are implemented in MainActivity.java, though
        // the interface and method signature are declared in this class.
    	buttonCommunicator.backButton();
    }


    public void onShareButtonClicked(View v){
        // I've decided this needs to be a Fragment (a dynamic one)
        // trigger loading the next dynamic fragment

        // this thing should produce data for the whole page
        // either as a big string (of html?) or as data fields for the layout to present
        //AmortSched amortSched = new AmortSched();		
    	
    	buttonCommunicator.shareButton();

    }
    
    
    

	public void doAmortTable() {

        // Create and populate TextViews for each line in the title
		TextView t1 = (TextView) view.findViewById(R.id.heading1);
        t1.setText("Amortization Schedule");        

		t1 = (TextView) view.findViewById(R.id.heading2);
        t1.setText("Original Loan Amount: " + AmortFragment.principal);

		t1 = (TextView) view.findViewById(R.id.heading3);
        t1.setText(AmortFragment.intr + "% Interest (compounded monthly)");

		t1 = (TextView) view.findViewById(R.id.heading4);
        t1.setText(AmortFragment.peri + " Months");
        
		t1 = (TextView) view.findViewById(R.id.heading5);
        t1.setText(AmortFragment.payment + " Monthly Payment");
        
        Spreadsheet s = new Spreadsheet(	Integer.parseInt(AmortFragment.peri), 
							        		Double.parseDouble(AmortFragment.prin), 
							        		Double.parseDouble(AmortFragment.paym), 
							        		Double.parseDouble(AmortFragment.intr)
        								);
		
		TableLayout tl;
        tl = (TableLayout) view.findViewById(R.id.the_table);
        
        // MAKE TABLE COLUMN HEADINGS
    	// TABLE ROW
        int i = 0;
        TableRow tr0 = new TableRow(getActivity());
        tr0.setId(i);
        //tr.setBackgroundResource(Color.MAGENTA);
        tr0.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            // CELL: MONTH
            TextView tv1 = new TextView(getActivity());
            tv1.setText(" ");
            tv1.setId(i);
            tv1.setTextColor(Color.BLUE);
            tv1.setTextSize(15);
            tv1.setPadding(3, 3, 3, 3);
            tr0.addView(tv1);

            // CELL: PRINCIPAL PORTION
            TextView tv2 = new TextView(getActivity());
            tv2.setText(" ");
            tv2.setId(i+500);
            tv2.setTextColor(Color.BLUE);
            tv2.setTextSize(15);
            tv2.setPadding(3, 3, 3, 3);
            tr0.addView(tv2);

            // CELL: INTEREST PORTION
            TextView tv3 = new TextView(getActivity());
            tv3.setText(" ");
            tv3.setId(i+1000);
            tv3.setTextColor(Color.BLUE);
            tv3.setTextSize(15);
            tv3.setPadding(3, 3, 3, 3);
            tr0.addView(tv3);

            // CELL: REMAINING BALANCE
            TextView tv4 = new TextView(getActivity());
            tv4.setText("Balance");
            tv4.setId(i+1500);
            tv4.setTextColor(Color.BLUE);
            tv4.setTextSize(15);
            tv4.setPadding(3, 3, 3, 3);
            tr0.addView(tv4);
            
            i++;
        tl.addView(tr0, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        TableRow tr1 = new TableRow(getActivity());
        tr1.setId(i);
        //tr.setBackgroundResource(Color.MAGENTA);
        tr1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            // CELL: MONTH
            TextView tv5 = new TextView(getActivity());
            tv5.setText(" ");
            tv5.setId(i);
            tv5.setTextColor(Color.BLUE);
            tv5.setTextSize(15);
            tv5.setPadding(3, 3, 3, 3);
            tr1.addView(tv5);

            // CELL: PRINCIPAL PORTION
            TextView tv6 = new TextView(getActivity());
            tv6.setText("Principal");
            tv6.setId(i+500);
            tv6.setTextColor(Color.BLUE);
            tv6.setTextSize(15);
            tv6.setPadding(3, 3, 3, 3);
            tr1.addView(tv6);

            // CELL: INTEREST PORTION
            TextView tv7 = new TextView(getActivity());
            tv7.setText("Interest");
            tv7.setId(i+1000);
            tv7.setTextColor(Color.BLUE);
            tv7.setTextSize(15);
            tv7.setPadding(3, 3, 3, 3);
            tr1.addView(tv7);

            // CELL: REMAINING BALANCE
            TextView tv8 = new TextView(getActivity());
            tv8.setText("After This");
            tv8.setId(i+1500);
            tv8.setTextColor(Color.BLUE);
            tv8.setTextSize(15);
            tv8.setPadding(3, 3, 3, 3);
            tr1.addView(tv8);
            
            i++;
        tl.addView(tr1, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        TableRow tr2 = new TableRow(getActivity());
        tr2.setId(i);
        //tr.setBackgroundResource(Color.MAGENTA);
        tr2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            // CELL: MONTH
            TextView tv9 = new TextView(getActivity());
            tv9.setText("Month");
            tv9.setId(i);
            tv9.setTextColor(Color.BLUE);
            tv9.setTextSize(15);
            tv9.setPadding(3, 3, 3, 3);
            tr2.addView(tv9);

            // CELL: PRINCIPAL PORTION
            TextView tv10 = new TextView(getActivity());
            tv10.setText("Portion");
            tv10.setId(i+500);
            tv10.setTextColor(Color.BLUE);
            tv10.setTextSize(15);
            tv10.setPadding(3, 3, 3, 3);
            tr2.addView(tv10);

            // CELL: INTEREST PORTION
            TextView tv11 = new TextView(getActivity());
            tv11.setText("Portion");
            tv11.setId(i+1000);
            tv11.setTextColor(Color.BLUE);
            tv11.setTextSize(15);
            tv11.setPadding(3, 3, 3, 3);
            tr2.addView(tv11);

            // CELL: REMAINING BALANCE
            TextView tv12 = new TextView(getActivity());
            tv12.setText("Payment");
            tv12.setId(i+1500);
            tv12.setTextColor(Color.BLUE);
            tv12.setTextSize(15);
            tv12.setPadding(3, 3, 3, 3);
            tr2.addView(tv12);
            
            i++;
        tl.addView(tr2, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        //amortizationSchedule
        Iterator<ScheduleRow> itr = s.amortizationSchedule.iterator();
        ScheduleRow row;
        while(itr.hasNext()) {
        	
        	row = itr.next();

        	// TABLE ROW
            TableRow tr5 = new TableRow(getActivity());
            tr5.setId(i);
            //tr.setBackgroundResource(Color.MAGENTA);
            tr5.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
	            // CELL: MONTH
	            TextView tv13 = new TextView(getActivity());
	            tv13.setText(Integer.toString(i-2));
	            tv13.setId(i);
	            tv13.setTextColor(Color.BLUE);
	            tv13.setTextSize(15);
	            tv13.setPadding(3, 3, 3, 3);
	            tr5.addView(tv13);
	
	            // CELL: PRINCIPAL PORTION
	            TextView tr14 = new TextView(getActivity());
	            tr14.setText(DecimalFormat.getCurrencyInstance().format(row.princPortion));
	            tr14.setId(i+500);
	            tr14.setTextColor(Color.BLUE);
	            tr14.setTextSize(15);
	            tr14.setPadding(3, 3, 3, 3);
	            tr5.addView(tr14);
	
	            // CELL: INTEREST PORTION
	            TextView tr15 = new TextView(getActivity());
	            tr15.setText(DecimalFormat.getCurrencyInstance().format(row.intPortion));
	            tr15.setId(i+1000);
	            tr15.setTextColor(Color.BLUE);
	            tr15.setTextSize(15);
	            tr15.setPadding(3, 3, 3, 3);
	            tr5.addView(tr15);
	
	            // CELL: REMAINING BALANCE
	            TextView tr16 = new TextView(getActivity());
	            tr16.setText(DecimalFormat.getCurrencyInstance().format(row.remainingPrinc));
	            tr16.setId(i+1500);
	            tr16.setTextColor(Color.BLUE);
	            tr16.setTextSize(15);
	            tr16.setPadding(3, 3, 3, 3);
	            tr5.addView(tr16);
	            
	            i++;


            tl.addView(tr5, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
	}


	public void setAmortInputValues(String prin2, String intr2, String peri2, String paym2) {
		
		// kind of a 'back-flip' to get format right to present to the humans
		AmortFragment.principal = DecimalFormat.getCurrencyInstance().format(Double.parseDouble(prin2));
		AmortFragment.payment   = DecimalFormat.getCurrencyInstance().format(Double.parseDouble(paym2));
		
		// get the 4 variables needed to generate the amortization schedule
		AmortFragment.prin = prin2;
		AmortFragment.intr = intr2;
		AmortFragment.peri = peri2;
		AmortFragment.paym = paym2;		
	}
}

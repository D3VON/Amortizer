package com.d3von.amortizer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ListIterator;

//import static com.example.amortizersandbox.R.id.schedule;

/**
 * Created by devon on 2/11/15.
 */
public class AmortSched {

    private static TextView schedText;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_amort, container, false);
/*
        int term = Integer.parseInt(view.findViewById(R.id.term).toString());
        double principal = Double.parseDouble(view.findViewById(R.id.term).toString());
        double payment = Double.parseDouble(view.findViewById(R.id.term).toString());
        double rate = Double.parseDouble(view.findViewById(R.id.term).toString());
        Spreadsheet ss = new Spreadsheet(term, principal, payment, rate);// shouldn't this be able to get data from strings in strings.xml?


        schedText = (TextView) view.findViewById(R.id.schedule);
        schedText.setText("This is where the Amortization Schedule will go. So fuck you.");
      */  
        //setSchedText(ss, principal, rate, term, payment);
        return view;

    };

    public void setSchedText(Spreadsheet ss, double princ, double rate, int term, double pmt) {

        String theSchedule = "        Principal   Interest   Remaining\n";
        theSchedule +=       "Month    Portion    Portion     Balance\n";

        theSchedule +=       "----------------------------------------";

        ListIterator<ScheduleRow> listIterator = ss.amortizationSchedule.listIterator();

        ScheduleRow row;
        int month = 0;
        while (listIterator.hasNext()) {
            month++;
            row = listIterator.next();
            theSchedule += month + "_,_" + row.princPortion
                                 + "_,_" + row.intPortion
                                 + "_,_" + row.remainingPrinc
                                 + "\n";
        }

/*

$50,000.00 Loan

4.25% interest, compounded monthly

120 month term

$508.63 monthly payment

Month    Principal    Interest     Balance\\n   1     344.55     885.44   49,654.44

  */

        schedText.setText(theSchedule);
    }






}

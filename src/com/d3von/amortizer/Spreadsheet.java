package com.d3von.amortizer; /**
 * 
 */

import java.util.LinkedList;


/**
 * @author devon
 *
 */
public class Spreadsheet {

	LinkedList<ScheduleRow> amortizationSchedule = new LinkedList<ScheduleRow>();
	
	
	/*
	 * prolly don't need these fields, but they do keep a record of the original 
	 * amounts, so they'll be accessible here -- though it might be redundant
	 */
	int term;
	double principal, payment, rate;
	
	
	/**
	 * CONSTRUCTOR
	 * 
	 * @param term in months
	 * @param principal amount of the whole loan
	 * @param payment per month
	 * @param rate of the loan -- human readable
	 */
	Spreadsheet(int term, double principal, double payment, double rate) {

		this.term = term;
		this.principal = principal;
		this.payment = payment;
		this.rate = rate;
		
		// Build the entire amortization schedule, save via function as data member of this object
		buildSpreadsheet(term, principal, payment, rate);

	}

	
	// calculate the data of a single row and return a ScheduleRow object with that data
	ScheduleRow calculateRow(double principal, double interest, double payment) {
		
		double i;
		i = principal*(interest/100/12);
		double p;
		p = payment - i;
		double newPrinc;
		newPrinc = principal - p;
		
		if(newPrinc<0){
			p += newPrinc;
			newPrinc = 0;			
		}
		
		ScheduleRow row = new ScheduleRow(i, p, newPrinc);
		
		return row;
		
	}
	
	
	
	// put each row into the linked list that is a field in this class/object
	void buildSpreadsheet(int term, double principal, double payment, double rate){

		for(int x = 0; x<term; x++){
			amortizationSchedule.add(calculateRow(principal, rate, payment));
			principal = amortizationSchedule.getLast().remainingPrinc;
			//System.out.printf("interest: %.2f, principal: %.2f, remaining bal: %.2f \n",
			//					amortizationSchedule.getLast().intPortion, amortizationSchedule.getLast().princPortion, amortizationSchedule.getLast().remainingPrinc
			//				);
		}
	}
	
	
	
}

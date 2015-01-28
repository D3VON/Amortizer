package com.d3von.amortizer;
/**
 * @author Devon McBride
 * 
 * Amortization basic info calculator
 * 
 * If you provide 3 pieces of data, calculate the 4th.  
 * 
 * example 1, give principal,      interest,       period,         get payment amount.
 * example 2, give principal,      interest,       payment amount, get period
 * example 3, give principal,      payment amount, period,         get interest.
 * example 4, give payment amount, interest,       period,         get principal.
 *
 */
public class Amortization {
	
	/**
	 * 
	 * 
	 * */
	public double interestPerPeriod(double principal,double rate)
	{
		return principal*rate/100/12;		
	}

	
	/**
	 * 
	 * 
	 * */
	public double runningPrincipal(double principal,double principalPortion)
	{
		return principal-principalPortion;		
	}

	
	/**
	 * example 1, give principal,      interest,       period,         get pmt amt.
	 * 
	 * */
	public double calcPmtAmt(double principal, double interestrate, double numPmts)
	{
		double rate = calcRatePerMonth(interestrate);
		return principal*rate*(Math.pow((1+rate),numPmts))/((Math.pow((1+rate),numPmts)-1));
				//=   B4*          B6*((1+B6          )^B8     )/((1+B6          )^     B8-1)
	}
	
	/**
	 * 
	 * 
	 * */
	public double calcRatePerMonth(double rate)
	{
		return rate/100/12;
		
	}
	
	
	/**
	 * return principal portion of a monthly payment amount
	 * 
	 * */
	public double principalPortion(double interest,double payment)
	{
		return payment-interest;		
	}
	
	/**
	 * example 4, give payment amount, interest,       period,         get principal.
	 * 
	 * */
	public double calculatePrincipal(double interestrate,double payment,int numPmts)
	{		
		double rate = calcRatePerMonth(interestrate);
		return payment*(1-(Math.pow((1+rate),-numPmts)))/rate;		
	}
	
	
	/**
	 * example 2, give principal,      interest,       payment amount, get period
	 * 
	 * */
	public int numPmts(double principal, double interestrate,double payment)
	{
		double rate = calcRatePerMonth(interestrate);	
		// ATTENTION: NOT sure what's going on with rounding.  So far, adding 1/2 to total gets it
		// to round to appropriate whole, "round" number.  Not at all fully tested though. 
		
			  // Math.log is base e, natural log, ln
        return (int) (0.5 - (Math.log(1-(principal/payment)*(rate)))/Math.log(1+(rate)));
		// -(      LN(1-(B/m              )*(r           )))/      LN(1+(r           ))	
        //r = interest rate per month
        //B = principal
        //m = payment amount
	}
		
		
	/**
	 * 
	 * 
	 * */
	/*
		public double payment(double interest,double payment){
			=
			return ;		
		}
	*/	
		
	/**
	 * example 3, give principal,      payment amount, period,         get interest.
	 * 
	 * Given: principal, number of payments (e.g., 360 payments in a 30 year loan), 
	 * and payment amount (frequently a borrower will know how much they can pay, 
	 * what a reasonable interest rate is, and how long they want the term to be), 
	 * Calculate: how much principal can you borrow.   
	 * 
	 * I thought this was strange when I discovered it.  Never the less, it's the best
	 * I could find to handle this problem.  I found it at:
	 * http://www.hughcalc.org/formula.php
	 * 
	 * It does a heuristic loop to discover a very near interest rate given the other 
	 * constraints.
	 * 
	 * */
	public double calculateInterest(double principal, double numPmts, double payment){
		
	   double min_rate = 0, max_rate = 100, mid_rate = 0; // Set Maximum and minimum rate
	   
	   while (min_rate < max_rate - 0.000001)
	   {
	      mid_rate = (min_rate + max_rate) / 2; // Divide by 2 to find midpoint
	      double J = mid_rate / 1200; // Convert to monthly decimal percentage
	      // calculate payment based on this interest, term of F and loan_amt
	      double guessed_pmt = principal*(J/(1-Math.pow((1 + J),-numPmts)));
	      if (guessed_pmt > payment)
	        { 
	           max_rate = mid_rate; // current rate is new maximum 
	        }
	      else
	      {
	    	  min_rate = mid_rate; // current rate is new minimum
        }
	   }
	   return mid_rate;
	}
	
}

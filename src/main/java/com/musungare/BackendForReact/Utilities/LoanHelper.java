package com.musungare.BackendForReact.Utilities;

import com.musungare.BackendForReact.BankAccout.Loan.LoanType;

public class LoanHelper {
    public static double getInterestRate(int years, long amount, LoanType loanType) {
        // Base rate varies by loan type
        double baseRate = switch (loanType) {
            case PERSONAL_LOAN -> 7.0; // Higher base rate for personal loans
            case MORTGAGE_LOAN -> 4.5; // Lower base rate for home loans
            case AUTO_LOAN -> 5.5; // Medium base rate for car loans
            default -> 6.0; // Default base rate for other types
        };

        // Adjust rate based on the number of years
        if (years <= 1) {
            baseRate += 1.0; // Increase for shorter terms
        } else if (years > 5) {
            baseRate -= 0.5; // Discount for longer terms
        }

        // Adjust rate based on the loan amount
        if (amount < 10000) {
            baseRate += 2.0; // Higher rate for smaller amounts
        } else if (amount > 100000) {
            baseRate -= 1.0; // Lower rate for larger amounts
        }

        // Ensure the interest rate stays within a reasonable range
        baseRate = Math.max(baseRate, 1.0); // Minimum 1%
        baseRate = Math.min(baseRate, 15.0); // Maximum 15%

        return baseRate;
    }

    public static double calculateMonthlyInstallment(long amount, double annualRate, int years) {

        double monthlyRate = annualRate / 12 / 100;


        int totalMonths = years * 12;

        // Calculate EMI using the formula

        return (amount * monthlyRate * Math.pow(1 + monthlyRate, totalMonths))
                / (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }



}

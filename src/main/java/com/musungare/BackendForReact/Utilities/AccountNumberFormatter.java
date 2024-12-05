package com.musungare.BackendForReact.Utilities;

public class AccountNumberFormatter {

    /**
     * Formats a 10-digit bank account number into the desired format.
     * Example: 1234567890 -> 1234-567890
     *
     * @param accountNumber The 10-digit account number to format.
     * @return A formatted account number as a String.
     */
    public static String formatAccountNumber(long accountNumber) {
        String accountStr = String.valueOf(accountNumber);


        if (accountStr.length() < 10) {
            accountStr = String.format("%010d", accountNumber);
        }


        return accountStr.substring(0, 4) + "-" + accountStr.substring(4);
    }

}

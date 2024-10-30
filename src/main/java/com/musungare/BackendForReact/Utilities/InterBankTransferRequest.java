package com.musungare.BackendForReact.Utilities;

public class InterBankTransferRequest {
    private Long senderAccount;
    private Long receiverAccount;
    private Long amount;
    private String bankName;

    // Getters and Setters

    public Long getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Long senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Long getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Long receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}

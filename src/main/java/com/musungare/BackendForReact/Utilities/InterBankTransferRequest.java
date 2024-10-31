package com.musungare.BackendForReact.Utilities;

import lombok.Data;

@Data
public class InterBankTransferRequest {
    private Long senderAccount;
    private Long receiverAccount;
    private Long amount;
    private String bankName;

}

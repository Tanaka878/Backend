package com.musungare.BackendForReact.BankAccout.Controller;

import com.musungare.BackendForReact.BankAccout.Service.BankAccountService;
import com.musungare.BackendForReact.BankAccout.repo.BankAccountRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banking")
@Getter
@Setter
public class BankAccountController {

    BankAccountService bankAccountService;
    BankAccountRepo bankAccountRepo;


}

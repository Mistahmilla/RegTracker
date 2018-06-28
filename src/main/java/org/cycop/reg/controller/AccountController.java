package org.cycop.reg.controller;

import org.cycop.reg.dao.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountDAO accountDAO;

    @GetMapping("/{accountID}")
    public List getAccount(@PathVariable long accountID){
        return accountDAO.getAccounByID(accountID);
    }

    @GetMapping
    public List getAllAccount(){
        return null;
    }
}

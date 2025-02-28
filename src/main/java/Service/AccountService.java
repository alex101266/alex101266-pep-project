package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;


public class AccountService {
    private AccountDAO accountDAO;

    /**
     * No argument constructor for creating an AccountService with a new AccountDAO
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for AccountService when AccountDAO is known
     * @param accountDAO to be used to create this AccountService
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Verify that username isn't blank or already used and that password is at least 4 characters long.
     * If valid, use accountDAO to persist the account.
     * @param account
     * @return
     */
    public Account registerAccount(Account account){
        String username = account.getUsername().strip();
        String password = account.getPassword().strip();
        if( (username.length()>0) && (password.length()>3) && (accountDAO.getAccountByUsername(username)==null) ){
            Account returnedAccount = accountDAO.insertAccount(account);
            return returnedAccount;
        } else{
            return null;
        }
    }

    /**
     * Authorize login if account credentials are authenticated by accountDAO
     * @param account to be authenticated
     * @return account if credentials are valid for login
     */
    public Account authorizeLogin(Account account){
        Account returnedAccount = accountDAO.authenticateLogin(account);
        return returnedAccount;
    }


}

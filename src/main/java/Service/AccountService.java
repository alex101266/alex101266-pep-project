package Service;

import Model.Account;
import DAO.AccountDAO;


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
     * @param account to be registered to the app
     * @return account if it was registered successfully, otherwise null if the registration failed
     */
    public Account registerAccount(Account account){
        String username = account.getUsername().strip();
        String password = account.getPassword().strip();
        if( (username.length()>0) && (password.length()>3) && (accountDAO.getAccountByUsername(username)==null) ){
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    /**
     * Authorize login if account credentials are authenticated by accountDAO
     * @param account to be authenticated
     * @return account if credentials are authorized for login, otherwise null if the authentication failed
     */
    public Account authorizeLogin(Account account){
        return accountDAO.authenticateLogin(account);
    }

    /**
     * Checks if such an account exists.
     * @param id of the account being validated to exist
     * @return true if this account exists, false if it doesn't exist
     */
    public boolean userExists(int id){
        return accountDAO.validateUserExists(id);
    }
}

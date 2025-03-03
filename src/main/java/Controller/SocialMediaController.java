package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * Initialized services for account and message
     */
    AccountService accountService;
    MessageService messageService;

    /**
     * Constructor for SocialMediaController
     */
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService(accountService);
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageAtIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageAtIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageAtIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesAtAccountIdHandler);

        return app;
    }

    /**
     * Handler to post (register) a new account
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post (authenticate and authorize) account login credentials
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postLoginHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post a new message
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to post a new message
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to get a message given the message id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageAtIdHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to delete a message given the message id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageAtIdHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to patch a message given the message id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void patchMessageAtIdHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to get all messages of an account by account id
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessagesAtAccountIdHandler(Context context) {
        context.json("sample text");
    }
}
package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * Initialized services for account and message.
     */
    AccountService accountService;
    MessageService messageService;

    /**
     * Constructor for SocialMediaController.
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
     * Handler to post (register) a new account.
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registerAccount = accountService.registerAccount(account);
        if(registerAccount!=null){
            ctx.json(mapper.writeValueAsString(registerAccount));
        } else{
            ctx.status(400);
        }

    }

    /**
     * Handler to post (authenticate and authorize) account login credentials.
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.authorizeLogin(account);
        if(loginAccount!=null){
            ctx.json(mapper.writeValueAsString(loginAccount));
        } else{
            ctx.status(401);
        }
    }

    /**
     * Handler to post a new message.
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage!=null){
            ctx.json(mapper.writeValueAsString(newMessage));
        } else{
            ctx.status(400);
        }
    }

    /**
     * Handler to post a new message
     * @param ctx which is the context object that manages the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to get a message given the message id
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void getMessageAtIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if(message!=null){
            ctx.json(mapper.writeValueAsString(message));
        } else{
            ctx.result("");
        }
    }

    /**
     * Handler to delete a message given the message id
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void deleteMessageAtIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(id);
        if(message!=null){
            ctx.json(mapper.writeValueAsString(message));
        } else{
            ctx.result("");
        }
    }

    /**
     * Handler to patch a message given the message id
     * Note: Couldn't find any method to do this patch request in prior coding labs or mini projects so I had
     * to check google to find some method of extracting the "message_text" from the body. Link:
     * https://www.baeldung.com/jackson-object-mapper-tutorial      (3.3)
     * @param ctx which is the context object that manages the HTTP request and response.
     * @throws JsonProcessingException thrown if there is an issue converting JSON.
     */
    private void patchMessageAtIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        String json = ctx.body();
        JsonNode jsonNode = mapper.readTree(json);
        String text = jsonNode.get("message_text").asText();
        Message message = messageService.updateMessage(id, text);
        if(message!=null){
            ctx.json(mapper.writeValueAsString(message));
        } else{
            ctx.status(400);
        }
    }

    /**
     * Handler to get all messages of an account by account id
     * @param ctx which is the context object that manages the HTTP request and response.
     */
    private void getMessagesAtAccountIdHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromAccount(id);
        ctx.json(messages);
    }
}
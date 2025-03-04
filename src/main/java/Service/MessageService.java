package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    /**
     * No argument constructor for creating a MessageService with a new MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for creating a MessageService with a new MessageDAO and an existing AccountService.
     * Realistically, the AccountService is only necessary for verifying an account exists.
     * @param accountService passed to MessageService to allow interactions with accounts
     */
    public MessageService(AccountService accountService){
        messageDAO = new MessageDAO();
        this.accountService = accountService;
    }

    /**
     * Constructor for creating a MessageService if MessageDAO and AccountService already exist.
     * @param messageDAO passed to MessageService
     * @param accountService passed to MessageService
     */
    public MessageService(MessageDAO messageDAO, AccountService accountService){
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    /**
     * Retrieves a list of all existing messages.
     * @return list of messages if the retrieval was successful, otherwise an empty list if the retrieval failed
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Creates a message.
     * @param message contents of the message to be created
     * @return Message contents if the creation was successful, otherwise null if the creation failed
     */
    public Message createMessage(Message message){
        String text = message.message_text.strip();
        int id = message.posted_by;
        if( (text.length()>0) && (text!=null) && (text.length()<255) && (accountService.userExists(id))){
            Message returnedMessage = messageDAO.insertMessage(message);
            return returnedMessage;
        }
        return null;
    }

    /**
     * Retrieves a message using its message_id.
     * @param id of the message being searched for
     * @return Message contents if the search was successful, otherwise null if the search failed
     */
    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    /**
     * Retrieves the message and deletes it using a message's message_id.
     * @param id of the message to be deleted
     * @return Message contents if the delete was successful, otherwise null if the delete failed
     */
    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }

    /**
     * Updates the message using its message_id.
     * @param id of the message to be updated
     * @param message that is empty besides the message_text which should be used to update the message
     * @return Message contents if the update was successful, otherwise null if the update failed
     */
    public Message updateMessage(int id, String updatedText){
        String text = updatedText.strip();
        if( (text!=null) && (text.length()>0) && (text.length()<255) && (messageDAO.validateMessageExists(id)) ){
            return messageDAO.updateMessage(id,text);
        }
        return null;
    }

    /**
     * Retrieves a list of all existing messages for a specified account.
     * @param id of the account whose messages are being retrieved
     * @return list of messages for the account if the retrieval was successful, otherwise an empty list if
     * the retrieval failed
     */
    public List<Message> getAllMessagesFromAccount(int id){
        return messageDAO.getAllMessagesFromAccountId(id);
    }
}

package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.ArrayList;
import java.util.List;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(AccountService accountService){
        messageDAO = new MessageDAO();
        this.accountService = accountService;
    }

    public MessageService(MessageDAO messageDAO, AccountService accountService){
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message createMessage(Message message){
        String text = message.message_text.strip();
        int id = message.posted_by;
        if( (text!=null) && (text.length()<255) && (accountService.userExists(id))){
            Message returnedMessage = messageDAO.insertMessage(message);
            return returnedMessage;
        }
        return null;
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(int id, String updatedMessageText){
        String text = updatedMessageText.strip();
        if( (messageDAO.validateMessageExists(id)) && (text!="") && (text.length()<255) ){
            Message returnedMessage = messageDAO.updateMessage(id,text);
            return returnedMessage;
        }
        return null;
    }

    public List<Message> getAllMessagesFromAccount(int id){
        return messageDAO.getAllMessagesFromAccountId(id);
    }
}

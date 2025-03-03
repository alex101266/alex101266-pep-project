package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MessageDAO {
    /**
     * Inserts a message into the Message database.
     * @param message to be inserted into the database
     * @return message with its message_id if it successfully persisted, null if insertion failed
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2,message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            ResultSet keyrs = preparedStatement.getGeneratedKeys();
            if(keyrs.next()){
                int key = keyrs.getInt(1);
                return new Message(key,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Creates a list of all the messages within the Message database.
     * @return messages list of all messages within the database, otherwise an empty list if there are none
     */
    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Retrieves the message from the Message database with this message_id.
     * @param id of the message that is being searched for
     * @return message that contains this message_id, otherwise null if it doesn't exist
     */
    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                return new Message(id,rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves the message to be deleted and then deletes it from the Message database.
     * @param id of the message to retrieve and delete.
     * @return message if deletion was persisted, otherwise null if it failed
     */
    public Message deleteMessage(int id){
        Message deletedMessage = getMessageById(id);
        if(deletedMessage==null){
            return null;
        }
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM Message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected>0){
                return deletedMessage;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Updates the message in the Message database under the message_id to have the new message_text.
     * @param id of the message to be updated in the database
     * @param text that is to replace the message_text of the message being updated in the database
     * @return message if the update persisted, othrwise null if it failed
     */
    public Message updateMessage(int id, String text){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Message SET message_text=? WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,text);
            preparedStatement.setInt(2,id);
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected>0){
                return getMessageById(id);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Determines if a message within the Message database exists with this message_id.
     * @param id of the message whose existance is being checked
     * @return true if a message with this message_id exists, false if doesn't exist
     */
    public boolean validateMessageExists(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM Message WHERE message_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}

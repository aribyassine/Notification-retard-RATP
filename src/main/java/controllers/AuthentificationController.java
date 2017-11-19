package controllers;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.entities.Client;

/**
 * @author Mohamed T. KASSAR
 */

public class AuthentificationController {

    public Client checkLogin(String userName, String password) throws DataException {
        Client client = DAOFactory.userDAO().getByName(userName);
        if (client == null || userName.isEmpty() || password.isEmpty() || !client.getPassword().equals(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()))
            throw new DataException("Invalid client name or password");
        return client;
    }

    public Client registerUser(String userName, String email, String phoneNumber, String password)
            throws DataException {

        if (userName.isEmpty())
            throw new DataException("Client name cannot be empty");

        if (password.isEmpty())
            throw new DataException("Password cannot be empty");

        if (!email.matches("[a-zA-Z0-9._-]{1,64}@[a-zA-Z0-9-]{2,252}(\\.[a-zA-Z]{2,6})+"))
            throw new DataException("Invalid mail address");

        if (!validatePhoneNumber(phoneNumber))
            throw new DataException("Invalid phone number");

        if (DAOFactory.userDAO().getByMail(email) != null)
            throw new DataException("E-Mail already used");

        if (DAOFactory.userDAO().getByPhoneNumber(phoneNumber) != null)
            throw new DataException("Phone number already used");

        Client client = DAOFactory.userDAO().getByName(userName);

        if (client != null)
            throw new DataException("client name alredy existant");

        String hashedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();

        client = new Client();
        client.setEmail(email);
        client.setUserName(userName);
        client.setPhoneNumber(phoneNumber);
        client.setPassword(hashedPassword);

        DAOFactory.userDAO().save(client);
        return client;
    }

    /*
     * Code copied from www.journaldev.com
     */
    private static boolean validatePhoneNumber(String phoneNo) {
        // validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}"))
            return true;
            // validating phone number with -, . or spaces
        else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
            // validating phone number with extension length from 3 to 5
        else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
            return true;
            // validating phone number where area code is in braces ()
        else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
            return true;
            // return false if nothing matches the input
        else
            return false;

    }
}

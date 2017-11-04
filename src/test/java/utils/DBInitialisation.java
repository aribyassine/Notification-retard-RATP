package utils;

import controllers.AuthentificationController;
import controllers.exceptions.DataException;
import model.dao.DAOFactory;
import model.dao.LineDAO;
import model.entities.Line;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class DBInitialisation {
	
	public static void initLines() {
		LineDAO ld = DAOFactory.lineDAO();

		Line line11 = new Line();
		line11.setLineName("11express");
		line11.setLineType(Line.LineType.tramway);
		ld.save(line11);

		Line lineB = new Line();
		lineB.setLineName("bb");
		lineB.setLineType(Line.LineType.rer);
		ld.save(lineB);

		Line lineD = new Line();
		lineD.setLineName("dd");
		lineD.setLineType(Line.LineType.rer);
		ld.save(lineD);
	}

	public static void initUsers() throws DataException {
		AuthentificationController ac = new AuthentificationController();
		ac.registerUser("user0", "ktarek1994@gmail.com", "0769128000", "pwd01");
		ac.registerUser("user1", "ktiko1994@yahoo.fr", "0769128020", "pwd02");
		ac.registerUser("user2", "ktarek1994@outlook.com", "0769128019", "pwd03");
		ac.registerUser("user3", "mtkassar@altirc.com", "0769128021", "pwd04");
	}
}

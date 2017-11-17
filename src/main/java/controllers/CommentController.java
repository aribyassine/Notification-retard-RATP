package controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import model.dao.DAOFactory;
import model.entities.Comment;

public class CommentController {
	
	
	
	public void addComment(String comment, String user, int notification) {
		Comment co = new Comment();
		co.setCommentText(comment);
		co.setNotification(DAOFactory.notificationDAO().getById(notification));
		co.setUser(DAOFactory.userDAO().getById(user));
		co.setDate(LocalDateTime.now(ZoneId.of("GMT+01:00")));
		DAOFactory.commentDAO().save(co);
	}

}

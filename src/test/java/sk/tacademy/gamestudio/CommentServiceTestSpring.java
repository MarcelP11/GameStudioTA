package sk.tacademy.gamestudio;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.service.CommentService;
import sk.tacademy.gamestudio.service.CommentServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringClient.class)

public class CommentServiceTestSpring {
    @Autowired
    private CommentService commentService;


    @Test
    public void testReset(){
        commentService.addComment(new Comment("Minesweeper", "Peter", "first comment", new Date()));
        commentService.reset();
        assertEquals(0, commentService.getComments("Minesweeper").size());
    }

    @Test
    public void testAddComment(){
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("Minesweeper","Peter","first comment", date));
        var comments = commentService.getComments("Minesweeper");
        assertEquals(1, comments.size());

        assertEquals("Minesweeper",comments.get(0).getGame());
        assertEquals("Peter",comments.get(0).getUsername());
        assertEquals("first comment",comments.get(0).getComment());
        assertEquals(date,comments.get(0).getCommentedOn());
    }

    @Test
    public void testGetComments(){
        commentService.reset();
        var date1 = new Date();
        //Thread.sleep(1000);
        var date2 = new Date();
        // Thread.sleep(1000);
        var date3 = new Date();
        // Thread.sleep(1000);
        var date4 = new Date();
        // Thread.sleep(1000);
        commentService.addComment(new Comment("Minesweeper","Peter", "first comment", date1));
        commentService.addComment(new Comment("Minesweeper","Jozef", "second comment", date2));
        commentService.addComment(new Comment("Tiles","Anna", "third comment", date3));
        commentService.addComment(new Comment("Minesweeper","Petra", "fourth comment", date4));

        var comments = commentService.getComments("Minesweeper");

        assertEquals(3,comments.size());

        assertEquals("Minesweeper", comments.get(0).getGame());
        assertEquals("Peter", comments.get(0).getUsername());
        assertEquals("first comment", comments.get(0).getComment());
        assertEquals(date1, comments.get(0).getCommentedOn());

        assertEquals("Minesweeper", comments.get(1).getGame());
        assertEquals("Jozef", comments.get(1).getUsername());
        assertEquals("second comment", comments.get(1).getComment());
        assertEquals(date2, comments.get(1).getCommentedOn());

        assertEquals("Minesweeper", comments.get(2).getGame());
        assertEquals("Petra", comments.get(2).getUsername());
        assertEquals("fourth comment", comments.get(2).getComment());
        assertEquals(date4, comments.get(2).getCommentedOn());
    }
}

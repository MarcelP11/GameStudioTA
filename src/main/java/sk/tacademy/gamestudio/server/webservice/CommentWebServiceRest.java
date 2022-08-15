package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentWebServiceRest {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{game}")
    public List<Comment> getComments(@PathVariable String game) {
        return commentService.getComments(game);
    }
    @PostMapping
    public void addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
    }
}

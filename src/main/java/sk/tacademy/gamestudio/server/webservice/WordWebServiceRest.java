package sk.tacademy.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tacademy.gamestudio.entity.Comment;
import sk.tacademy.gamestudio.entity.Words;
import sk.tacademy.gamestudio.service.WordService;

import java.util.List;

@RestController
@RequestMapping("/api/words")
public class WordWebServiceRest {

    @Autowired
    private WordService wordService;

    @PostMapping
    public void addWord(@RequestBody Words word){
        wordService.addWord(word);
    }

    @GetMapping("/{length}")
    public List<Words> getWords(@PathVariable int length) {
        return wordService.getWords(length);
    }


}

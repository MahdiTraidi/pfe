package com.mtraidi.demo.Controlleur;

import com.mtraidi.demo.dao.ArticleRepository;
import com.mtraidi.demo.models.Article;
import com.mtraidi.demo.models.Niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/users/article")
@CrossOrigin("*")
public class ArticleController {
    private final Path rootLocation = Paths.get("upload-dir");

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getArticles() {
        return new ResponseEntity<>(articleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        return new ResponseEntity<>(articleRepository.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Article> saveArticle(@RequestParam("file") MultipartFile file, Article article) {
        article.setShow(false);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            article.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL Image exception !");
        }
        return new ResponseEntity<Article>(articleRepository.save(article), HttpStatus.CREATED);
    }

    @PutMapping("/update_image/{id}")
    public ResponseEntity<Article> updateArticleImage(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        Article currentArticle = articleRepository.findOne(id);
        try {
            String fileName = Integer.toString(new Random().nextInt(1000000000));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + fileName + ext;
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            currentArticle.setImage(original);

        } catch (Exception e) {
            throw new RuntimeException("FAIL Image exception !");
        }
        return new ResponseEntity<Article>(articleRepository.saveAndFlush(currentArticle), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article, @PathVariable Long id) {
        Article currentArticle = articleRepository.findOne(id);
        currentArticle.setTitle(article.getTitle());
        currentArticle.setDescription(article.getDescription());
        return new ResponseEntity<Article>(articleRepository.saveAndFlush(currentArticle), HttpStatus.CREATED);
    }

    @PutMapping("/show/{id}")
    public ResponseEntity<Article> showArticle(@PathVariable Long id) {
        Article currentArticle = articleRepository.findOne(id);
        if (currentArticle.isShow()) {
            currentArticle.setShow(false);
        } else {
            currentArticle.setShow(true);
        }
        return new ResponseEntity<Article>(articleRepository.saveAndFlush(currentArticle), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String, String> deleteArticle(@PathVariable Long id) {
        HashMap response = new HashMap();
        try {
            articleRepository.delete(id);
            response.put("Response :", "Article with id : " + id + " Deleted Successfully !");
            return response;
        } catch (Exception e) {
            response.put("Response :", "Article with id : " + id + " Not found !");
            return response;
        }
    }
}

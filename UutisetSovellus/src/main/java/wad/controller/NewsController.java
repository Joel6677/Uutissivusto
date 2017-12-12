/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Category;
import wad.domain.NewsItem;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.repository.WriterRepository;
import wad.service.NewsService;

/**
 *
 * @author Jolle
 */
@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private WriterRepository writerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String home(Model model) {
        newsService.getSideNews(model);
        newsService.get5Latest(model);
        model.addAttribute("categories", categoryRepository.findAll());
        return "home";
    }

    @PostMapping("/news")
    @Transactional
    public String addNewsItem(@RequestParam Map<String, String> requestParams,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime publishdate,
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") Long[] categoryIds,
            @RequestParam("writerId") Long[] writerIds) throws IOException {
        newsService.add(requestParams, publishdate, file, categoryIds, writerIds);
        return "redirect:/";
    }

    @GetMapping("/news/{id}")
    @Transactional
    public String getNewsItem(@PathVariable Long id, Model model) {
        NewsItem newsItem = newsRepository.getOne(id);
        newsItem.increaseCount();
        newsRepository.save(newsItem);

        model.addAttribute("newsItem", newsRepository.getOne(id));
        model.addAttribute("categories", categoryRepository.findAll());
        
        newsService.getLatestAndHot(model);

        return "newsItem";
    }

    @GetMapping(path = "/images/{id}/content", produces = "image/jpg")
    @ResponseBody
    @Transactional
    public byte[] getImage(@PathVariable Long id) {
        NewsItem newsItem = newsRepository.getOne(id);
        return newsItem.getImage();
    }

    @GetMapping("/news/new")
    public String add(Model model) {
        model.addAttribute("writers", writerRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "new";
    }

    @GetMapping("/hot")
    public String hot(Model model) {
        newsService.getHot(model);
        model.addAttribute("categories", categoryRepository.findAll());
        return "hot";
    }

    @PostMapping("/news/{id}/edit")
    @Transactional
    public String updateNewsItem(@PathVariable Long id, @RequestParam Map<String, String> requestParams, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime publishdate,
            @RequestParam("file") MultipartFile file) throws IOException {
        newsService.edit(id, requestParams, publishdate, file);
        return "redirect:/news/" + id;
    }

    @GetMapping("/news/{id}/edit")
    @Transactional
    public String editNewsItem(@PathVariable Long id, Model model) {
        model.addAttribute("newsItem", newsRepository.getOne(id));
        return "edit";
    }

    @GetMapping("/{name}")
    @Transactional
    public String getNewsInCategory(@PathVariable String name, Model model) {
        Category category = categoryRepository.findByNameIgnoreCase(name);
        model.addAttribute("news", category.getNews());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("category", category);
        return "category";
    }

    @PostMapping("/news/{id}/delete")
    @Transactional
    public String deleteNewsItem(@PathVariable Long id, Model model) {
        newsRepository.deleteById(id);
        newsService.get5Latest(model);
        model.addAttribute("categories", categoryRepository.findAll());
        return "redirect:/";
    }
}
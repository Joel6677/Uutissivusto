/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.Category;
import wad.domain.NewsItem;
import wad.domain.Writer;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.repository.WriterRepository;

/**
 *
 * @author Jolle
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private WriterRepository writerRepository;

    @PostConstruct
    public void init() {
        if (categoryRepository.findAll().isEmpty() && writerRepository.findAll().isEmpty()) {
            categoryRepository.save(new Category("Kotimaa"));
            categoryRepository.save(new Category("Ulkomaat"));
            categoryRepository.save(new Category("Urheilu"));
            categoryRepository.save(new Category("Kulttuuri"));
            categoryRepository.save(new Category("Talous"));
            categoryRepository.save(new Category("Politiikka"));

            writerRepository.save(new Writer("Hevoskauppias"));
            writerRepository.save(new Writer("Seppo Taalasmaa"));
            writerRepository.save(new Writer("Lordi"));
            writerRepository.save(new Writer("Pekka Puupää"));
        }
    }

    @Transactional
    public void add(Map<String, String> params, LocalDateTime publishdate, MultipartFile file,
            Long[] categoryIds, Long[] writerIds) throws IOException {
        NewsItem newsItem = new NewsItem();
        newsItem.setHeading(params.get("heading"));
        newsItem.setLead(params.get("lead"));
        newsItem.setBody(params.get("body"));

        newsItem.setImage(file.getBytes());
        newsItem.setPublishDate(publishdate);

        for (Long i : categoryIds) {
            newsItem.getCategories().add(categoryRepository.getOne(i));
        }

        for (Long i : writerIds) {
            newsItem.getWriters().add(writerRepository.getOne(i));
        }
        newsRepository.save(newsItem);
    }

    public void edit(Long id, Map<String, String> params, LocalDateTime publishdate, MultipartFile file) throws IOException {
        NewsItem newsItem = newsRepository.getOne(id);
        newsItem.setHeading(params.get("heading"));
        newsItem.setLead(params.get("lead"));
        newsItem.setBody(params.get("body"));
        newsItem.setPublishDate(publishdate);
        if (!file.isEmpty()) {
            newsItem.setImage(file.getBytes());
        }
        newsRepository.save(newsItem);
    }

    @Transactional
    public void getHot(Model model) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "count");
        model.addAttribute("hot", newsRepository.findAll(pageable));
    }

    @Transactional
    public void get5Latest(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "publishDate");
        model.addAttribute("latest", newsRepository.findAll(pageable));
    }

    @Transactional
    public void getSideNews(Model model) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.DESC, "publishDate");
        model.addAttribute("sideNews", newsRepository.findAll(pageable));
    }

    @Transactional
    public void getLatestAndHot(Model model) {
        get5Latest(model);
        getHot(model);
    }

}

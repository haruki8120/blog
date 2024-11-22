package com.example.blog.BlogApplicationjava.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.blog.BlogApplicationjava.entity.Blog;
import com.example.blog.BlogApplicationjava.form.BlogForm;
import com.example.blog.BlogApplicationjava.service.BlogService;

@Controller
public class Blogcontroller {

    @Autowired
    private BlogService blogService;

  

    // ブログ一覧を表示する
    @GetMapping("/blogs")
    public String getAllBlogs(Model model) {
        List<Blog> blogs = blogService.list();
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

    // 新しいブログ作成フォームを表示する
    @GetMapping("/blogs/new")
    public String getCreateForm(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("actionUrl", "/blogs"); // 新規作成用のURL
        return "blog/form";
    }

    // フォームから送信されたブログを保存する
    @PostMapping("/blogs")
    public String saveBlog(@ModelAttribute BlogForm blogForm) {
        blogService.create(blogForm);
        return "redirect:/blogs";
    }
 
    public String getBlogById(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.detail(id);  
        if (blog == null) {
            throw new IllegalArgumentException("ブログが存在しません: ID = " + id);
        }
        model.addAttribute("blog", blog);
        return "blog/detail";
      
    }
    @GetMapping("/blogs/{id}/edit")
    public String getBlogupdate(@PathVariable("id") Long id, Model model) {
        Blog blog = blogService.detail(id);
        model.addAttribute("blog", blog);
        return "blog/form";
    }
    @PostMapping("/blogs/{id}")
    public String updateBlog(@PathVariable Long id, @ModelAttribute BlogForm blogForm) {
        blogService.update(id, blogForm);
        return "redirect:/blogs";
    }
    @PostMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id) {
        blogService.delete(id);
        return "redirect:/blogs";
   
   }
}

        
    
    
    

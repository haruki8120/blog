package com.example.blog.BlogApplicationjava.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.BlogApplicationjava.entity.Blog;
import com.example.blog.BlogApplicationjava.form.BlogForm;
import com.example.blog.BlogApplicationjava.repository.BlogRepository;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;
    
 
    

    
    public List<Blog> list() {
        return blogRepository.findAll();
    }

    

   
    public Blog create(BlogForm blogForm) {
        Blog blog = new Blog();
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());
        blog.setCreatedAt(blogForm.getCreatedAt());
        Blog savedBlog = blogRepository.save(blog);
        System.out.println("Saved Blog ID: " + savedBlog.getId());
        return blogRepository.save(blog);
    }
    
  
   public Blog detail(Long id) {
       Blog blog = blogRepository.findById(id);
       System.out.println("Blog: " + blog);
       if (blog == null || blog.getDeletedAt() != null) {
           return null; 
       }
       return blog; 
}
   /**
    * 指定されたIDを持つブログのタイトルと内容を更新します。
    * @param id 更新対象のブログID
    * @param blogForm 更新するフォームデータ
    */
   public void update(Long id, BlogForm blogForm) {
       // 既存のブログを取得
       Blog existingBlog = blogRepository.findById(id);
       if (existingBlog == null) {
           throw new IllegalArgumentException("指定されたIDのブログが存在しません: " + id);
       }

      
       existingBlog.setTitle(blogForm.getTitle());
       existingBlog.setContent(blogForm.getContent());

      
       blogRepository.update(existingBlog);
      
}    
   /**
    * 指定されたIDのブログを削除します。
    * @param id 削除対象のブログID
    */
   public void delete(Long id) {
  
       blogRepository.delete(id);
       
       
    
}
}

    
    
    
    



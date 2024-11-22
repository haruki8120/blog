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
    public Blog existingBlog;
    
 
    

    
    public List<Blog> list() {
        return blogRepository.findAll();
    }

    

   
    public Blog create(BlogForm blogForm) {
        Blog blog = new Blog();
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());
        blog.setCreatedAt(blogForm.getCreatedAt());
        
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
   public void update(BlogForm blogForm) {
       // フォームに含まれるIDを基に既存のブログを取得
       Blog existingBlog = blogRepository.findById(blogForm.getId());
       if (existingBlog == null) {
           throw new IllegalArgumentException("指定されたブログが存在しません: ID = " + blogForm.getId());
       }

       // フォームデータで既存ブログを更新
       existingBlog.setTitle(blogForm.getTitle());
       existingBlog.setContent(blogForm.getContent());
       
       blogRepository.update(existingBlog);
   }

      
   /**
    * 指定されたIDのブログを削除します。
    * @param id 削除対象のブログID
    */
   public void delete(Long id) {
       Blog blog = blogRepository.findById(id);
       if (blog == null || blog.getDeletedAt() != null) {
           throw new IllegalArgumentException("削除対象のブログが見つからない、または既に削除されています: ID = " + id);
       }

       blogRepository.delete(id);
       System.out.println("削除が完了しました: ID = " + id);
   }

       
       
    
}






    



    
    
    
    



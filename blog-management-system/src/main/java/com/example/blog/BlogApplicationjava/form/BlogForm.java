package com.example.blog.BlogApplicationjava.form;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public class BlogForm {

    
  

    private String title;

    private String content;


    private LocalDateTime createdAt;

    @Column(name = "update_date") 
    private LocalDateTime updateDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
        public BlogForm() {
            
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
        public LocalDateTime getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
        
}
        public LocalDateTime getDeletedAt() {
            return updateDate;
        }

        public void setDeletedAt(LocalDateTime deletedAt) {
            this.deletedAt = deletedAt;
}
}
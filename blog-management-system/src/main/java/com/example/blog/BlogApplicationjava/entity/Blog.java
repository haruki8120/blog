package com.example.blog.BlogApplicationjava.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主キーを自動生成
    private Long id;

    private String title;

    private String content;


    private LocalDateTime createdAt;

    @Column(name = "update_date") 
    private LocalDateTime updateDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // コンストラクタ（id を省略）
    public Blog(String title, String content, LocalDateTime createdAt, LocalDateTime updateDate, LocalDateTime deletedAt) {
        this.title = title; 
        this.content = content;
        this.createdAt = createdAt;
        this.updateDate = updateDate;
        this.deletedAt = deletedAt;
    }

    // デフォルトコンストラクタ（必須）
    public Blog() {
    }

    // Getter と Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
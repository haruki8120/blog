package com.example.blog.BlogApplicationjava.repository;

import java.util.List;

import com.example.blog.BlogApplicationjava.entity.Blog;

public interface BlogRepository {

    /**
     * 全てのブログを取得します。
     * @return ブログリスト
     */
    List<Blog> findAll();

    /**
     * ブログを保存します。
     * @param blog 保存するブログ
     * @return 保存されたブログ
     */
    Blog save(Blog blog);

    /**
     * 指定されたIDを持つブログを取得します。
     * @param id ブログのID
     * @return 指定されたIDに対応するBlogオブジェクト（存在しない場合はnull）
     */
    Blog findById(Long id);
    
    /**
     * 指定されたブログのタイトルと内容を更新します。
     * @param blog 更新するブログオブジェクト
     * @return 
     */
   void update(Blog blog);
   /**
    * 指定されたIDのブログを削除します（削除日時を現在時刻に設定）。
    * 
    * @param id 削除対象のブログID
    */
   void delete(Long id);
   
}
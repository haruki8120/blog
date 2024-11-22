package com.example.blog.BlogApplicationjava.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.blog.BlogApplicationjava.entity.Blog;
import com.example.blog.util.DatabaseUtil;

@Repository
public class BlogRepositoryImp implements BlogRepository {

    private final DatabaseUtil databaseUtil;

    @Autowired
    public BlogRepositoryImp(DatabaseUtil databaseUtil) {
        this.databaseUtil = databaseUtil;
    }

    @Override
    public List<Blog> findAll() {
        List<Blog> bloglist = new ArrayList<>();

        String SQL = "SELECT id, title, content, created_at, update_date, deleted_at FROM blog";

       
        try (Connection connection = databaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                // createdAt を LocalDateTime に変換
                Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
                LocalDateTime createdAt = createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null;

                // update_date を LocalDateTime に変換
                Timestamp updateDateTimestamp = resultSet.getTimestamp("update_date");
                LocalDateTime updateDate = updateDateTimestamp != null ? updateDateTimestamp.toLocalDateTime() : null;

                // deleted_at を LocalDateTime に変換
                Timestamp deletedAtTimestamp = resultSet.getTimestamp("deleted_at");
                LocalDateTime deletedAt = deletedAtTimestamp != null ? deletedAtTimestamp.toLocalDateTime() : null;

                Blog blog = new Blog();
                blog.setId(id);
                blog.setTitle(title);
                blog.setContent(content);
                blog.setCreatedAt(createdAt);
                blog.setUpdateDate(updateDate);
                blog.setDeletedAt(deletedAt);

                bloglist.add(blog);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error fetching blogs: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }

        return bloglist;
    }

    
    @Override
    public Blog save(Blog blog) {
        String query = "INSERT INTO blog (title, content) VALUES (?, ?)";

        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            
            preparedStatement.setString(1, blog.getTitle());
            preparedStatement.setString(2, blog.getContent());

         
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " 行がデータベースに挿入されました");

            
            

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    blog.setId(generatedKeys.getLong(1));
                    System.out.println("Saved Blog ID: " + blog.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("データベースの保存中にエラーが発生しました: " + e.getMessage());
        }

        return blog;
    }
    @Override
    public Blog findById(Long id) {
        String query = "SELECT id, title, content, created_at, update_date, deleted_at " +
                       "FROM blog WHERE id = ? AND deleted_at IS NULL";

        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Blog blog = new Blog();
                    blog.setId(resultSet.getLong("id"));
                    blog.setTitle(resultSet.getString("title"));
                    blog.setContent(resultSet.getString("content"));

                    // created_at を LocalDateTime に変換
                    Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
                    blog.setCreatedAt(createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null);

                    // update_date を LocalDateTime に変換
                    Timestamp updateDateTimestamp = resultSet.getTimestamp("update_date");
                    blog.setUpdateDate(updateDateTimestamp != null ? updateDateTimestamp.toLocalDateTime() : null);

                    // deleted_at を LocalDateTime に変換（null の場合も考慮）
                    Timestamp deletedAtTimestamp = resultSet.getTimestamp("deleted_at");
                    blog.setDeletedAt(deletedAtTimestamp != null ? deletedAtTimestamp.toLocalDateTime() : null);
                    
                    

                    return blog;
                    
                }
            }
        } catch (SQLException e) {
            System.out.println(
 
"SQL Error fetching blog by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // データが存在しない場合
       
    }


    @Override
   public  void update(Blog blog) {
        String query = "UPDATE blog SET title = ?, content = ?, update_date = CURRENT_TIMESTAMP WHERE id = ?";

        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // プレースホルダに値を設定
            preparedStatement.setString(1, blog.getTitle());
            preparedStatement.setString(2, blog.getContent());
            preparedStatement.setLong(3, blog.getId());

            // クエリを実行
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " 行が更新されました");
            System.out.println("Updated rows: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("ブログの更新中にエラーが発生しました: " + e.getMessage());
        }
        
    }
    @Override
    public void delete(Long id) {
        String query = "UPDATE blog SET deleted_at = ? WHERE id = ?";

        try (Connection connection = databaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // 現在時刻を取得して削除日時に設定
            Timestamp currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

            preparedStatement.setTimestamp(1, currentTimestamp); // deleted_at に現在時刻を設定
            preparedStatement.setLong(2, id); // WHERE 条件に id を設定

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("ブログが削除されました (ID: " + id + ")");
            } else {
                System.out.println("削除対象のブログが見つかりません (ID: " + id + ")");
            }

        } catch (SQLException e) {
            System.out.println("ブログ削除中にエラーが発生しました: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("データベースエラー: 削除処理が失敗しました");
        }
    }
            
             
       
    
    
}

              

        
        
    
    


   

  
    

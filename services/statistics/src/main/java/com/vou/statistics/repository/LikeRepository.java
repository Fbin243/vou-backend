package com.vou.statistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vou.statistics.model.Like;

import java.util.List;

public interface LikeRepository extends MongoRepository<Like, String> {
    // Tìm tất cả các lượt like của một đối tượng
    List<Like> findByLikeableIdAndLikeableType(String likeableId, String likeableType);
    
    // Kiểm tra nếu user đã like đối tượng
    boolean existsByUserIdAndLikeableIdAndLikeableType(String userId, String likeableId, String likeableType);

    // Tìm tất cả các lượt like của một user cho một loại đối tượng
    List<Like> findByUserIdAndLikeableType(String userId, String likeableType);

    // Xóa like
    void deleteByUserIdAndLikeableIdAndLikeableType(String userId, String likeableId, String likeableType);
}

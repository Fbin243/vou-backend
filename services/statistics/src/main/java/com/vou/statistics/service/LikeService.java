package com.vou.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.statistics.model.Like;
import com.vou.statistics.repository.LikeRepository;

import java.util.List;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;
    
    public Like like(String userId, String likeableType, String likeableId) {
        // Kiểm tra nếu user đã like đối tượng
        if (likeRepository.existsByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType)) {
            throw new RuntimeException("User has liked this type already!");
        }
        
        // Tạo like mới
        Like newLike = new Like(userId, likeableType, likeableId);
        return likeRepository.save(newLike);
    }

    public List<Like> getLikesForLikeable(String likeableId, String likeableType) {
        // Lấy tất cả các like của một đối tượng
        return likeRepository.findByLikeableIdAndLikeableType(likeableId, likeableType);
    }

    public List<Like> getLikesForUser(String userId, String likeableType) {
        // Lấy tất cả các like của một user cho một loại đối tượng
        return likeRepository.findByUserIdAndLikeableType(userId, likeableType);
    }

    public void unlike(String userId, String likeableType, String likeableId) {
        // Kiểm tra nếu user đã like đối tượng
        if (!likeRepository.existsByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType)) {
            throw new RuntimeException("User has not liked this type yet!");
        }
        
        // Xóa like
        likeRepository.deleteByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType);
    }

    public boolean isLiked(String userId, String likeableType, String likeableId) {
        // Kiểm tra nếu user đã like đối tượng
        return likeRepository.existsByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType);
    }
}

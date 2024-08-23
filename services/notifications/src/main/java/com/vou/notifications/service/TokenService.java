package com.vou.notifications.service;

import com.vou.notifications.model.UserTokenId;
import com.vou.notifications.repository.UserTokenRepository;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.vou.notifications.entity.UserToken;

@Service
@AllArgsConstructor
public class TokenService {

    private UserTokenRepository userTokenRepository;
    private final Firestore firestore;

    public void saveOrUpdateToken(String userId, String fcmToken) {
        CollectionReference tokensRef = firestore.collection("users_tokens");

        DocumentReference userTokenRef = tokensRef.document(userId);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", fcmToken);
        tokenData.put("updatedAt", FieldValue.serverTimestamp());

        userTokenRef.set(tokenData);
    }

    public String getTokenByUserId(String userId) throws Exception {
        DocumentReference userTokenRef = firestore.collection("users_tokens").document(userId);

        UserToken userToken = userTokenRef.get().get().toObject(UserToken.class);

        if (userToken == null) {
            throw new Exception("Token not found");
        }

        return userToken.getToken();
    }

}

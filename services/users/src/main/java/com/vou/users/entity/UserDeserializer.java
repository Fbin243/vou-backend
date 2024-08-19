package com.vou.users.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class UserDeserializer extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);
        String role = node.get("role").asText();

        User user;
        switch (UserRole.valueOf(role)) {
            case player:
                user = new Player();
                break;
            case brand:
                user = new Brand();
                break;
            case admin:
                user = new Admin();
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }

        // Manually set fields to avoid recursive deserialization
        user.setFullName(node.get("fullName").asText());
        user.setUsername(node.get("username").asText());
        user.setAccountId(node.get("accountId").asText());
        user.setEmail(node.get("email").asText());
        user.setPhone(node.get("phone").asText());
        user.setStatus(node.get("status").asBoolean());
        user.setRole(UserRole.valueOf(role));

        // Set specific fields for each user type
        if (user instanceof Player) {
            Player player = (Player) user;
            player.setGender(node.get("gender").asText());
            player.setFacebookAccount(node.get("facebookAccount").asText());
            player.setDateOfBirth(node.get("dateOfBirth").asText());
            player.setAvatar(node.get("avatar").asText());
            player.setTurns(node.get("turns").asInt());
        } else if (user instanceof Brand) {
            Brand brand = (Brand) user;
            brand.setBrandName(node.get("brandName").asText());
            brand.setField(node.get("field").asText());
            brand.setAddress(node.get("address").asText());
            brand.setLatitude(node.get("latitude").asDouble());
            brand.setLongitude(node.get("longitude").asDouble());
        }

        return user;
    }
}

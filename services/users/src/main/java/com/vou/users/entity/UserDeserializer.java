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

        if (node.has("fullName")) {
            user.setFullName(node.get("fullName").asText());
        }
        if (node.has("username")) {
            user.setUsername(node.get("username").asText());
        }
        if (node.has("accountId")) {
            user.setAccountId(node.get("accountId").asText());
        }
        if (node.has("email")) {
            user.setEmail(node.get("email").asText());
        }
        if (node.has("phone")) {
            user.setPhone(node.get("phone").asText());
        }
        if (node.has("status")) {
            user.setStatus(node.get("status").asBoolean());
        }
        user.setRole(UserRole.valueOf(role));

        if (user instanceof Player) {
            Player player = (Player) user;
            if (node.has("gender")) {
                player.setGender(node.get("gender").asText());
            }
            if (node.has("facebookAccount")) {
                player.setFacebookAccount(node.get("facebookAccount").asText());
            }
            if (node.has("dateOfBirth")) {
                player.setDateOfBirth(node.get("dateOfBirth").asText());
            }
            if (node.has("avatar")) {
                player.setAvatar(node.get("avatar").asText());
            }
            if (node.has("turns")) {
                player.setTurns(node.get("turns").asInt());
            }
        } else if (user instanceof Brand) {
            Brand brand = (Brand) user;
            if (node.has("brandName")) {
                brand.setBrandName(node.get("brandName").asText());
            }
            if (node.has("field")) {
                brand.setField(node.get("field").asText());
            }
            if (node.has("address")) {
                brand.setAddress(node.get("address").asText());
            }
            if (node.has("latitude")) {
                brand.setLatitude(node.get("latitude").asDouble());
            }
            if (node.has("longitude")) {
                brand.setLongitude(node.get("longitude").asDouble());
            }
        }

        return user;
    }
}

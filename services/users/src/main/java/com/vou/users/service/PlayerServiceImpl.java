package com.vou.users.service;

import com.vou.users.client.NotificationsServiceClient;
import com.vou.users.common.ActiveStatus;
import com.vou.users.dao.PlayerRepository;
import com.vou.users.dto.AddUsersRequestDto;
import com.vou.users.entity.Player;
import com.vou.users.model.NotificationInfo;
import com.vou.users.model.NotificationRelatedPairId;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import com.vou.users.model.NotificationData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository thePlayerRepository, KafkaTemplate<String, NotificationData> kafkaTemplateNotificationInfo, NotificationsServiceClient notificationsServiceClient) {
        playerRepository = thePlayerRepository;
        this.kafkaTemplateNotificationInfo = kafkaTemplateNotificationInfo;
        this.notificationsServiceClient = notificationsServiceClient;
    }

    @Autowired
    private KafkaTemplate<String, NotificationData> kafkaTemplateNotificationInfo;

    private final NotificationsServiceClient notificationsServiceClient;


    @Override
    @Transactional
    public void savePlayer(Player thePlayer) {
        playerRepository.save(thePlayer);
    }

    @Override
    public Player findPlayerById(String theId) {
        Optional<Player> result = playerRepository.findById(theId);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw new RuntimeException("Did not find player id - " + theId);
        }
    }

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    @Transactional
    public void updatePlayer(Player player) {
        Player existingPlayer = playerRepository.findById(player.getId())
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + player.getId()));

        // Update only fields that are provided
        if (player.getFullName() != null && !player.getFullName().isEmpty()) {
            existingPlayer.setFullName(player.getFullName());
        }
        if (player.getUsername() != null && !player.getUsername().isEmpty()) {
            existingPlayer.setUsername(player.getUsername());
        }
        if (player.getAccountId() != null && !player.getAccountId().isEmpty()) {
            existingPlayer.setAccountId(player.getAccountId());
        }
        if (player.getEmail() != null && !player.getEmail().isEmpty()) {
            existingPlayer.setEmail(player.getEmail());
        }
        if (player.getPhone() != null && !player.getPhone().isEmpty()) {
            existingPlayer.setPhone(player.getPhone());
        }
        if (player.getRole() != null) {
            existingPlayer.setRole(player.getRole());
        }
        if (player.isStatus()) {
            existingPlayer.setStatus(player.isStatus());
        }
        if (player.getGender() != null && !player.getGender().isEmpty()) {
            existingPlayer.setGender(player.getGender());
        }
        if (player.getFacebookAccount() != null && !player.getFacebookAccount().isEmpty()) {
            existingPlayer.setFacebookAccount(player.getFacebookAccount());
        }
        if (player.getDateOfBirth() != null && !player.getDateOfBirth().isEmpty()) {
            existingPlayer.setDateOfBirth(player.getDateOfBirth());
        }
        if (player.getAvatar() != null && !player.getAvatar().isEmpty()) {
            existingPlayer.setAvatar(player.getAvatar());
        }
        existingPlayer.setTurns(player.getTurns());
        playerRepository.save(existingPlayer);
    }

    @Override
    @Transactional
    public void deletePlayerById(String theId) {
        playerRepository.deleteById(theId);
    }

    @Override
    public Player findPlayerByPhone(String phone) {
        return playerRepository.findByPhone(phone);
    }

    @Override
    public Player findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    @Override
    public List<Player> findManyPlayersByManyIds(List<String> theIds) {
        return playerRepository.findManyPlayersByManyIds(theIds);
    }

    @Override
    public String requestTurns(String id, String email, int turns) {
        Player player1 = findPlayerById(id);
        Player player2 = findByEmail(email);

        if (player2 == null) {
            return "Player with email " + email + " not found.";
        }

        String title = player1.getFullName() + " has just sent a request asking for " + turns + " game turns from you.";
        String description = "You can accept or decline the request.";

        NotificationInfo notificationInfo = new NotificationInfo(title, description, "fa-check");
        NotificationData notificationData = new NotificationData(notificationInfo, Collections.singletonList(player2.getId()));
        String notificationId = notificationsServiceClient.addUsersToNotification(new AddUsersRequestDto(notificationInfo, Collections.singletonList(player2.getId())));

        notificationsServiceClient.addMoreDataToNotification(Collections.singletonList(new NotificationRelatedPairId(notificationId, "senderId", player1.getId(), ActiveStatus.ACTIVE)));

        kafkaTemplateNotificationInfo.send("event-notification", notificationData);

        System.out.println("Notification IDDD: " + notificationId);

        return "Request sent to " + player2.getFullName();
    }

    @Override
    @Transactional
    public String acceptTurns(String id1, String id2, int turns) {
        Player player1 = findPlayerById(id1);
        Player player2 = findPlayerById(id2);

        if (player1 == null || player2 == null) {
            return "Player not found.";
        }

        if (player2.getTurns() < turns) {
            return "Not enough turns to give.";
        }

        player1.setTurns(player1.getTurns() + turns);
        player2.setTurns(player2.getTurns() - turns);

        savePlayer(player1);
        savePlayer(player2);

        return "Turns transferred successfully.";
    }
}
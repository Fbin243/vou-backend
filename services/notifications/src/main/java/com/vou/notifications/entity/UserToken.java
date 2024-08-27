// package com.vou.notifications.entity;

// import com.vou.pkg.entity.Base;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// import com.vou.notifications.model.UserTokenId;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "users_tokens")
// @NoArgsConstructor
// @AllArgsConstructor
// @Getter
// @Setter
// public class UserToken extends Base {

//     @EmbeddedId
//     private UserTokenId id;
// }

package com.vou.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserToken {
    
    @Id
    @DocumentId
    private String documentId;  // Firestore document ID

    private String user_id;
    private String token;

    @ServerTimestamp
    private Date updatedAt;
}

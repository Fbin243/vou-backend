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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserToken {
    
    @DocumentId
    private String userId;  // Firestore document ID

    private String token;
    private Date updatedAt;
}

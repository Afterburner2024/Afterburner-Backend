package com.afterburner.user.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    @Getter
    @Setter
    public static class CreateUserRequest {
        private String userName;
        private String userPhoneNumber;
        private String userEmail;
        private String note;
        private List<String> userTechStacks;
        private String userImage;
    }

    @Getter
    @Setter
    public static class UpdateUserRequest {
        private String userName;
        private String userPhoneNumber;
        private String note;
        private List<String> userTechStacks;
        private String userImage;
    }

    @Getter
    @Setter
    public static class UserResponse {
        private Integer userId;
        private String userName;
        private String userPhoneNumber;
        private String userEmail;
        private LocalDateTime registeredAt;
        private LocalDateTime modifiedAt;
        private LocalDateTime deletedAt;
        private String note;
        private List<String> userTechStacks;
        private String userImage;

        public UserResponse(User user) {
            this.userId = user.getUserId();
            this.userName = user.getUserName();
            this.userPhoneNumber = user.getUserPhoneNumber();
            this.userEmail = user.getUserEmail();
            this.registeredAt = user.getRegisteredAt();
            this.modifiedAt = user.getModifiedAt();
            this.deletedAt = user.getDeletedAt();
            this.note = user.getNote();
            this.userTechStacks = user.getUserTechStacks();
            this.userImage = user.getUserImage();
        }
    }
}

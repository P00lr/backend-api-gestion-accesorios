package com.universidad.tecno.api_gestion_accesorios.dto;

public class PasswordChangeDto {
    private Long userId;
    private String currentPassword;
    private String newPassword;

    
    public PasswordChangeDto() {
    }
    
    public PasswordChangeDto(Long userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}

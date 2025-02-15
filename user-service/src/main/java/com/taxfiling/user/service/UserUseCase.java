
package com.taxfiling.user.service;

import com.taxfiling.user.model.User;

public interface UserUseCase {
    User registerUser(User user);
    String login(String pan, String password);
    User getUser(Long id);
    User updateUser(User user);
    void deleteUser(Long id);
    boolean validateToken(String token);
    String refreshToken(String oldToken);
}

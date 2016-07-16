package com.hantsylabs.restexample.springmvc.test;

import com.hantsylabs.restexample.springmvc.domain.Post;
import com.hantsylabs.restexample.springmvc.domain.User;
import com.hantsylabs.restexample.springmvc.model.PasswordForm;
import com.hantsylabs.restexample.springmvc.model.UserForm;

public class Fixtures {

    public static User createUser(String username, String password) {
        return User.builder()
                .username(username)
                .password(password)
                .name("test user")
                .email("hantsy@test.com")
                .build();
    }

    public static UserForm createUserForm(String username, String password) {
        return UserForm.builder()
                .username(username)
                .password(password)
                .name("test user")
                .email("hantsy@test.com")
                .build();
    }

    public static PasswordForm createPasswordForm(String oldPassword, String newPassword) {
        return PasswordForm.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
    }

    public static Post createPost(Long id, String title, String content) {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .status(Post.Status.DRAFT)
                .build();
    }
}

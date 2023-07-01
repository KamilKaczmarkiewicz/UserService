package com.kams.UserService.auth;

record RegisterRequest(
        String username,
        int age,
        String password
) {
}

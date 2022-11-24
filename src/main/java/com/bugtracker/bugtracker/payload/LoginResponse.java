package com.bugtracker.bugtracker.payload;

import com.bugtracker.bugtracker.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private int id;
    private Role role;
}

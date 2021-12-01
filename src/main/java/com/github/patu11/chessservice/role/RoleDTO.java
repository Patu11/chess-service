package com.github.patu11.chessservice.role;

import com.github.patu11.chessservice.user.UserDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
	private String name;

	public RoleDTO(Role role) {
		this.name = role.getName();
	}
}

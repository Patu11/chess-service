package com.github.patu11.chessservice.role;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public Role getRole(String name) {
		return this.roleRepository.findRoleByName(name).orElseThrow(() -> new NotFoundException("Role not found."));
	}
}

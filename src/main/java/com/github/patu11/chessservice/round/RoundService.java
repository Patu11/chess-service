package com.github.patu11.chessservice.round;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

	private final RoundRepository roundRepository;

	@Autowired
	public RoundService(RoundRepository roundRepository) {
		this.roundRepository = roundRepository;
	}
}

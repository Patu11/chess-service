package com.github.patu11.chessservice.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketController {

	private final WebsocketService websocketService;

	@Autowired
	public WebsocketController(WebsocketService websocketService) {
		this.websocketService = websocketService;
	}

	@MessageMapping("/send/{roomCode}")
	@SendTo("/game/{roomCode}")
	public String test(@DestinationVariable String roomCode,String data) {
		return data;
	}
}

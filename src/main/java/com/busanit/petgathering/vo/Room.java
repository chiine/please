package com.busanit.petgathering.vo;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class Room {
	
	private Long userCount;
	int roomNumber; 
	String roomName;
	
	
	public enum MessageType{
		ENTER,QUIT,TALK
	}
	
	
	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomName=" + roomName + "]";
	}
}

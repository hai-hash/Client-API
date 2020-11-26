package com.InviteClient.model;


import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;



@Data
public class Invitee {
	private Long id;
	private String name;
	private String cmt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private List<Room> room;
}

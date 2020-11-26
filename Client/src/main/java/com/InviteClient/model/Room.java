package com.InviteClient.model;





import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data

@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class Room {
	private Long id;
	private String roomNumber;
	private final Type type;
	public static enum Type {
		NORMAL,VIP
	}	
	private Double price;
	private Integer capacity;
	
	
	private List<Student> listStudent;

}

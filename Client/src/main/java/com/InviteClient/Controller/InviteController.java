package com.InviteClient.Controller;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import com.InviteClient.model.Invitee;
import com.InviteClient.model.Room;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/invite") 
public class InviteController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	public String ShowForm(Model model) {
		List<Room> rooms = Arrays.asList(rest.getForObject("http://localhost:8080/room-api",Room[].class));
		model.addAttribute("rooms",rooms);
		model.addAttribute("invite", new Invitee());
		return "inviteForm";
		
	}
	@PostMapping
	public String SaveForm(Invitee invite,@RequestParam("phong") String roomNumber) {
		Room room = rest.getForObject("http://localhost:8080/room-api/{roomNumber}",Room.class,roomNumber);
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		invite.setRoom(rooms);
		rest.postForObject("http://localhost:8080/invitee-api",invite,Invitee.class);
		
		return "redirect:/invite/show";
	}
	@GetMapping("/show")
	public String ShowFormDanhSach(Model model) {
		List<Invitee> invites = Arrays.asList(rest.getForObject("http://localhost:8080/invitee-api",Invitee[].class));
		model.addAttribute("invites", invites);
		return "hienthikhach";
	}
	@GetMapping("/edit")
	public String EditInvite(@ModelAttribute("id") String id,Model model) {
		Invitee invite = rest.getForObject("http://localhost:8080/invitee-api/{id}",Invitee.class,id);
		model.addAttribute("invite", invite);
		String roomNumber = invite.getRoom().get(0).getRoomNumber();
		model.addAttribute("roomNumber", roomNumber);
		List<Room> rooms = Arrays.asList(rest.getForObject("http://localhost:8080/room-api",Room[].class));
		List<Room> room1s = new ArrayList<Room>();
		for (Room room : rooms) {
			
			if(room.getRoomNumber().equalsIgnoreCase(roomNumber) == false) {
				room1s.add(room);
			}
		}
		model.addAttribute("rooms", room1s);
		return "editInvite";
	}
	@PostMapping("/edit")
	public String SaveEditForm(Invitee invite,@RequestParam("phong") String roomNumber){
		Room room = rest.getForObject("http://localhost:8080/room-api/{roomNumber}",Room.class,roomNumber);
		List<Room> rooms = new ArrayList<>();
		rooms.add(room);
		invite.setRoom(rooms);
		rest.put("http://localhost:8080/invitee-api",invite, Invitee.class);
		return "redirect:/invite/show";
	}
	@GetMapping("/delete")
	public String DeleteInvite(@ModelAttribute("id") String id) {
		rest.delete("http://localhost:8080/invitee-api/{id}", id);
		return "redirect:/invite/show";
	}
	@PostMapping("/search")
	public String SearchInviteByName(@RequestParam("timkiem") String name,Model model) {
		List<Invitee> invites = Arrays.asList(rest.getForObject("http://localhost:8080/invitee-api/searchByName/{name}",Invitee[].class,name));
		model.addAttribute("invites", invites);
		return "hienthikhach";
	}
	@GetMapping("/sort")
	public String Sort(Model model) {
		List<Invitee> invites = Arrays.asList(rest.getForObject("http://localhost:8080/invitee-api/sort", Invitee[].class));
		model.addAttribute("invites", invites);
		return "hienthikhach";
	}
	

}

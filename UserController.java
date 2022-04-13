package com.citi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.citi.dto.Stocks;
import com.citi.dto.User;
import com.citi.repository.UserRepository;



@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/addUser")
	public String addUser(@RequestBody User user) {
		List<Stocks> list=user.getWatchList();
		System.out.println(list);
		list.add(new Stocks("ppp"));
		
		 userRepository.save(user);
		 
		 
		 return "Added user successfully :"+user.getUserid();
	}
	
	
	
	
	@GetMapping("/add/{userid}/{stocks}")
	public String addToWatchlist(@PathVariable String userid,Stocks stocks) {
		Optional<User> optional=userRepository.findByuserid(userid);
		User user=optional.get();
		List<Stocks> list=user.getWatchList();
//		list.add(stocks);
		System.out.println(list);
		list.add(new Stocks("ppp"));
		
		user.setWatchList(list);
		
		userRepository.save(user);
		 return "saved";
	}
	
	@GetMapping("/delete/{userid}/{stocks}")
	public String deleteFromWatchlist(@PathVariable String userid,String stocks) {
		Optional<User> optional=userRepository.findByuserid(userid);
		User user=optional.get();
		List<Stocks> list=user.getWatchList();
		System.out.println(list);

				
		list.remove(0);
		user.setWatchList(list);
		System.out.println("Deleted");
		System.out.println(list);
		
		userRepository.save(user);
		 return "deleted";
	}
	
	
	
	
	@GetMapping("/findAllUsers")
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/getWatchlists")
	public List<User> getWatchlists(){
		List<User> listOfUsers=userRepository.findAll();
		List watchlistsList=new ArrayList<>();
		for(int i=0;i<listOfUsers.size();i++) {
			watchlistsList.add(listOfUsers.get(i).getWatchList());
		}
		return watchlistsList;
	}
	
	@GetMapping("/save/{userid}")
	public  List saveStocks(@PathVariable String userid) {
		Optional<User> optional=getUser(userid);
		User user=optional.get();
//		user.getWatchList().add();
		
		System.out.println(user.getWatchList());
		
		return user.getWatchList();
		
	}
	
	@GetMapping("/{userid}")
	public Optional<User> getUser(@PathVariable("userid") String userid) {
		return userRepository.findByuserid(userid);
	}
	
	
	@GetMapping("/login/{userid}/{password}")
	public String authenticate(@PathVariable("userid") String userid,@PathVariable("password")String password) {
		Optional<User> optional=getUser(userid);
		User user=optional.get();
		
		System.out.println(password);
		System.out.println(user.getPassword());
		
		if(password.equals(user.getPassword())) {
			return "success";
		}
		return "fail";
	}
	
	
}

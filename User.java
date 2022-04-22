package com.citi.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString

@Document(collection = "login")
public class User {
	@Id
	private String userid;
	private String password;
	private List<Stocks> watchList=new ArrayList<Stocks>();
}

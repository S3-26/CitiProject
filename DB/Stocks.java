package com.citi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString


public class Stocks {
	
	 private String name;
      
	  public Stocks(final String name) {
	    this.name = name;
	  }
}

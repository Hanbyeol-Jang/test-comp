package com.mimi.Dto;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sigun")
public class Sigun {
	private String si;
	private String gun;
}

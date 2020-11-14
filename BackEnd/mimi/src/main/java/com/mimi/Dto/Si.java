package com.mimi.Dto;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "si")
public class Si {
	private String si;
}

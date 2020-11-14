package com.mimi.Dto;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "sigundong")
public class Sigundong {
	private String si;
	private String gun;
	private String dong;
}

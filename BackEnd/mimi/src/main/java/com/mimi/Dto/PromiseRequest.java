package com.mimi.Dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Valid
@Data
public class PromiseRequest {
	@ApiModelProperty(required = true)
	String storeid;
	
	@ApiModelProperty(required = true)
	String pid;
	
	@ApiModelProperty(required = true)
	int year;
	
	@ApiModelProperty(required = true)
	@Max(12)
	@Min(1)
	int month;
	
	@ApiModelProperty(required = true)
	@Max(31)
	@Min(1)
	int day;
	
	@ApiModelProperty(required = true)
	@Max(23)
	@Min(0)
	int hour;
	
	@ApiModelProperty(required = true)
	@Max(59)
	@Min(0)
	int min;
}

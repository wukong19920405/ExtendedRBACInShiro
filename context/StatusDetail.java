package com.wukong.context;

import java.util.List;

import com.wukong.attribute.MissingAttributeDetail;


public class StatusDetail {

	
	private List<MissingAttributeDetail> missingattributedetails;
	
	public StatusDetail(List<MissingAttributeDetail> missingattributedetails){
		this.missingattributedetails=missingattributedetails;
	}
	
	public List<MissingAttributeDetail> getMissingAttributeDetails(){
		return missingattributedetails;
	}
}

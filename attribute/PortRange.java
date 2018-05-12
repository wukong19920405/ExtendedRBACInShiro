package com.wukong.attribute;

public class PortRange {

	public static int UNBOUND=-1;
	
	int lowerbound;
	int upperbound;
	
	public PortRange(){
		lowerbound=UNBOUND;
		upperbound=UNBOUND;
	}
	
	public PortRange(int lowerbound,int upperbound){
		this.lowerbound=lowerbound;
		this.upperbound=upperbound;
	}
	
	
	//single port
	public PortRange(int port){
		this(port,port);
	}
	
	public static PortRange getInstance(String value){
		int lowerbound=UNBOUND;
		int upperbound=UNBOUND;
		
		if(value=="-"||value.length()==0)
			return new PortRange();
		
		int dashpos=value.indexOf('-');
		
		if(dashpos==-1)
			lowerbound=upperbound=Integer.parseInt(value);
		else if(dashpos==0)
			upperbound=Integer.parseInt(value);
		else {
			lowerbound=Integer.parseInt(value.substring(0, dashpos));
			
			if(dashpos!=value.length()-1)
				upperbound=Integer.parseInt(value.substring(dashpos+1));
		}
		
		return new PortRange(lowerbound,upperbound);
	}
	
	public int getLowerBound(){
		return lowerbound;
	}
	
	public int getUpperBound(){
		return upperbound;
	}
	
	public boolean isLowerBounded(){
		return lowerbound!=-1;
	}
	
	public boolean isUpperBounded(){
		return upperbound!=-1;
	}
	
	public boolean isSinglePort(){
		return ((lowerbound==upperbound)&&(lowerbound!=-1));
	}
	
	public boolean isUnbound(){
		return ((lowerbound==UNBOUND)&&(upperbound==UNBOUND));
	}
	
	public boolean equals(Object o){
		if(!(o instanceof PortRange))
			return false;
		
		PortRange other=(PortRange) o;
		
		if(lowerbound!=other.lowerbound)
			return false;
		if(upperbound!=other.upperbound)
			return false;
		return true;
	}
	
	public String encode(){
		if(isUnbound())
			return "";
		if(isSinglePort())
			return String.valueOf(lowerbound);
		
		if(!isLowerBounded()){
			return "-" + String.valueOf(upperbound);
		}
		
		if(!isUpperBounded()){
			return String.valueOf(lowerbound)+'-';
		}
		
		return String.valueOf(lowerbound)+'-'+String.valueOf(upperbound);
	}
}

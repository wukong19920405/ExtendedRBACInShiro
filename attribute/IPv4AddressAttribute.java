package com.wukong.attribute;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class IPv4AddressAttribute extends IPAddressAttribute{
	
	public IPv4AddressAttribute(InetAddress value,InetAddress mask,PortRange range){
		super(value,mask,range);
	}
	
	public IPv4AddressAttribute(InetAddress value){
		this(value,null,new PortRange());
	}
	
	public IPv4AddressAttribute(InetAddress value,PortRange range){
		this(value,null,range);
	}
	
	
	public IPv4AddressAttribute(InetAddress value,InetAddress mask){
		this(value,mask,new PortRange());
	}
	
	public static IPv4AddressAttribute getV4Instance(String value) throws UnknownHostException{
		
		InetAddress addr=null;
		InetAddress mask=null;
		PortRange range=null;
		
		int maskPos=value.indexOf("/");
		int rangePos=value.indexOf(":");
		if(maskPos==rangePos){
			addr=InetAddress.getByName(value);
		}else if(maskPos!=-1){
			addr=InetAddress.getByName(value.substring(0, maskPos));
			if(rangePos!=-1){
				mask=InetAddress.getByName(value.substring(maskPos+1, rangePos));
				range=PortRange.getInstance(value.substring(rangePos+1,value.length()));
			}else{
				mask=InetAddress.getByName(value.substring(maskPos+1,value.length()));
				range=new PortRange();
			}
		}else{
			addr=InetAddress.getByName(value.substring(0, rangePos));
			range=PortRange.getInstance(value.substring(rangePos+1,value.length()));
		}
		return new IPv4AddressAttribute(addr,mask,range);
	}
	
	public String encode() {
        String str = getAddress().getHostAddress();

        if (getMask() != null)
            str += getMask().getHostAddress();

        if (!getRange().isUnbound())
            str += ":" + getRange().encode();

        return str;
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}

package com.wukong.attribute;

import java.net.InetAddress;
import java.net.UnknownHostException;



public class IPv6AddressAttribute extends IPAddressAttribute{

	public IPv6AddressAttribute(InetAddress value,InetAddress mask,PortRange range){
		super(value,mask,range);
	}
	
	public IPv6AddressAttribute(InetAddress value){
		this(value,null,new PortRange());
	}
	
	public IPv6AddressAttribute(InetAddress value,PortRange range){
		this(value,null,range);
	}
	
	
	public IPv6AddressAttribute(InetAddress value,InetAddress mask){
		this(value,mask,new PortRange());
	}
	
	public static IPv6AddressAttribute getV6Instance(String value) throws UnknownHostException{
		
		InetAddress address = null;
        InetAddress mask = null;
        PortRange range = null;
        int len = value.length();

        // get the required address component
        int endIndex = value.indexOf(']');
        address = InetAddress.getByName(value.substring(1, endIndex));

        // see if there's anything left in the string
        if (endIndex != (len - 1)) {
            // if there's a mask, it's also an IPv6 address
            if (value.charAt(endIndex + 1) == '/') {
                int startIndex = endIndex + 3;
                endIndex = value.indexOf(']', startIndex);
                mask = InetAddress.getByName(value.substring(startIndex, endIndex));
            }

            // finally, see if there's a port range, if we're not finished
            if ((endIndex != (len - 1)) && (value.charAt(endIndex + 1) == ':'))
                range = PortRange.getInstance(value.substring(endIndex + 2, len));
        }

        // if the range is null, then create it as unbound
        range = new PortRange();

        return new IPv6AddressAttribute(address, mask, range);
	}
	
	public String encode() {
        String str = "[" + getAddress().getHostAddress() + "]";

        if (getMask() != null)
            str += "/[" + getMask().getHostAddress() + "]";

        if (!getRange().isUnbound())
            str += ":" + getRange().encode();

        return str;
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}

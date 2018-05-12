package com.wukong.attribute;

import java.net.URI;
import java.util.Arrays;

import org.w3c.dom.Node;

import com.wukong.exception.*;

public class HexBinaryAttribute extends AttributeValue{

	public static final String identifier="http://www.w3.org/2001/XMLSchema#hexBinary";
	
	private static URI identifierURI;
	
	private static RuntimeException earlyException;
	
	static {
        try {
            identifierURI = new URI(identifier);
        } catch (Exception e) {
            earlyException = new IllegalArgumentException();
            earlyException.initCause(e);
        }
    };
    
	private byte[] value;
	/**
     * The value returned by toString(). Cached, but only generated if needed.
     */
	private String strValue;
	
	public HexBinaryAttribute(byte[] value){
		super(identifierURI);
		if(earlyException!=null)
			throw earlyException;
		this.value=(byte[])value.clone();
	}
	
	public static HexBinaryAttribute getInstance(Node root) throws ParsingException{
		return getInstance(root.getFirstChild().getNodeValue());
	}
	
	public static HexBinaryAttribute getInstance(String value) throws ParsingException{
		int len=value.length();
		byte [] bytes=hexToBin(value);
		
		if(bytes==null)
			throw new ParsingException("Couldn't parse purported " + "hex string: " + value);
		return new HexBinaryAttribute(bytes);
	}
	
	private static int hexToBinNibble(char c){
		int result=-1;
		if(c>'0'&&c<'9')
			result= c-'0';
		if(c>'a'&&c<'f')
			result= c-'a'+10;
		if(c>'A'&&c<'F')
			result= c-'A'+10;
		return result;
	}
	
	private static byte[] hexToBin(String value){
		int len=value.length();
		if(len%2!=0)
			return null;
		int bytelen=len/2;
		byte[] bytes=new byte[bytelen];
		int charIndex=0;
		for(int byteIndex=0;byteIndex<bytelen;byteIndex++){
			int left=hexToBinNibble(value.charAt(charIndex++));
			int right=hexToBinNibble(value.charAt(charIndex++));
			if(left<0||right<-1)
				return null;
			bytes[byteIndex]=(byte)(left*16+right);
		}
		return bytes;
	}
	
	public byte[] getValue(){
		return value.clone();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof HexBinaryAttribute))
			return false;
		HexBinaryAttribute other=(HexBinaryAttribute) o;
		return Arrays.equals(value, other.value);
	}
	
	private static char BinToHexNibble(int nibble){
		char result=(char) 0;
		if(nibble<10)
			result=(char)(nibble+'0');
		else
			result=(char)(nibble-10+'A');
		return result;
	}
	
	private static String binToHex(byte[] bytes){
		int bytelen=bytes.length;
		char [] chars=new char[bytelen*2];
		int charIndex=0;
		for(int byteIndex=0;byteIndex<bytelen;byteIndex++){
			byte b=bytes[byteIndex];
			chars[charIndex++]=BinToHexNibble((b >> 4) & 0xf);
			chars[charIndex++]=BinToHexNibble(b & 0xf);
		}
		return new String(chars);
	}
	
	public String toString(){
		if(strValue==null)
			strValue=binToHex(value);
		return "HexBinaryAttribute: [\n"+strValue+"\n]";
	}
	
	public String encode() {
        if (strValue == null)
            strValue = binToHex(value);

        return strValue;
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}

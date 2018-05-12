package com.wukong.attribute;

import java.util.HashMap;

import com.wukong.attribute.proxy.AnyURIAttributeProxy;
import com.wukong.attribute.proxy.BooleanAttributeProxy;
import com.wukong.attribute.proxy.DNSNameAttributeProxy;
import com.wukong.attribute.proxy.DateAttributeProxy;
import com.wukong.attribute.proxy.DateTimeAttributeProxy;
import com.wukong.attribute.proxy.DayTimeDurationAttributeProxy;
import com.wukong.attribute.proxy.DoubleAttributeProxy;
import com.wukong.attribute.proxy.HexBinaryAttributeProxy;
import com.wukong.attribute.proxy.IPAddressAttributeProxy;
import com.wukong.attribute.proxy.IntegerAttributeProxy;
import com.wukong.attribute.proxy.StringAttributeProxy;
import com.wukong.attribute.proxy.TimeAttributeProxy;
import com.wukong.attribute.proxy.YearMonthDurationAttributeProxy;





public class StandardAttributeFactory extends BaseAttributeFactory{

	private static volatile StandardAttributeFactory factoryInstance=null;
	
	private static HashMap supportedDatatypes=null;
	
	private StandardAttributeFactory(){
		super(supportedDatatypes);
	}
	
	private static void iniDataTypes(){
		
		supportedDatatypes=new HashMap();
		supportedDatatypes.put(BooleanAttribute.identifier, new BooleanAttributeProxy());
		supportedDatatypes.put(StringAttribute.identifier, new StringAttributeProxy());
        supportedDatatypes.put(DateAttribute.identifier, new DateAttributeProxy());
        supportedDatatypes.put(TimeAttribute.identifier, new TimeAttributeProxy());
        supportedDatatypes.put(DateTimeAttribute.identifier, new DateTimeAttributeProxy());
        supportedDatatypes.put(DayTimeDurationAttribute.identifier,
                new DayTimeDurationAttributeProxy());
        supportedDatatypes.put(YearMonthDurationAttribute.identifier,
                new YearMonthDurationAttributeProxy());
        supportedDatatypes.put(DoubleAttribute.identifier, new DoubleAttributeProxy());
        supportedDatatypes.put(IntegerAttribute.identifier, new IntegerAttributeProxy());
        supportedDatatypes.put(AnyURIAttribute.identifier, new AnyURIAttributeProxy());
        supportedDatatypes.put(HexBinaryAttribute.identifier, new HexBinaryAttributeProxy());

        supportedDatatypes.put(DNSNameAttribute.identifier, new DNSNameAttributeProxy());
        supportedDatatypes.put(IPAddressAttribute.identifier, new IPAddressAttributeProxy());
	}
	
	public static StandardAttributeFactory getFactory(){
		if(factoryInstance==null){
			synchronized(StandardAttributeFactory.class){
				if(factoryInstance==null){
					iniDataTypes();
					factoryInstance=new StandardAttributeFactory();
				}
				
			}
		}
		return factoryInstance;
	}
}

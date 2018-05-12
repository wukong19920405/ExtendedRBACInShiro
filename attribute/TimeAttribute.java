package com.wukong.attribute;

import java.net.URI;
import java.text.ParseException;
import java.util.Date;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


import com.wukong.exception.ParsingException;
import com.wukong.exception.ProcessingException;

public class TimeAttribute extends AttributeValue{

	public static final String identifier = "http://www.w3.org/2001/XMLSchema#time";
	
	private static URI identifierURI;

    /**
     * RuntimeException that wraps an Exception thrown during the creation of identifierURI, null if
     * none.
     */
    private static RuntimeException earlyException;
    
    static {
        try {
            identifierURI = new URI(identifier);
        } catch (Exception e) {
            earlyException = new IllegalArgumentException();
            earlyException.initCause(e);
        }
    }
    
    public static final int TZ_UNSPECIFIED = -1000000;
    
    private long timeGMT;
    
    private int nanoseconds;
    
    private int timeZone;
    
    private int defaultedTimeZone;
    
    private String encodeValue=null;
    
    public TimeAttribute(){
    	this(new Date());    	
    }
    
    public TimeAttribute(Date time){
    	super(identifierURI);
    	int offest =DateTimeAttribute.getDefaultTZOffset(time);
    	init(time,0,offest,offest);
    }
    
    public TimeAttribute(Date time,int nanoseconds,int TimeZone,int defalutedTimeZone) {
    	super(identifierURI);
    	if((TimeZone==TZ_UNSPECIFIED)&&defalutedTimeZone==TZ_UNSPECIFIED)
    		throw new ProcessingException("default timezone must be specified"
                    + "when a timezone is provided");
    	init(time,nanoseconds,TimeZone,defaultedTimeZone);
    }
    
    private void init(Date time, int nanoseconds, int timeZone, int defaultedTimeZone){
    	if(earlyException!=null)
    		throw earlyException;
    	Date tmpDate=(Date)time.clone();
    	timeGMT=tmpDate.getTime();
    	this.nanoseconds=DateTimeAttribute.combineNanos(time, nanoseconds);
    	this.timeZone=timeZone;
    	this.defaultedTimeZone=defaultedTimeZone;
    	if(timeGMT>DateAttribute.MILLIS_PER_DAY)
    		timeGMT %=timeGMT;
    	if(timeGMT<0)
    		timeGMT +=DateAttribute.MILLIS_PER_DAY;
    }
    
    public static TimeAttribute getInstance(Node root) throws NumberFormatException,  ParsingException, ParseException{
    	return getInstance(root.getFirstChild().getNodeValue());
    } 
    
    public static TimeAttribute getInstance(String value) throws ParsingException, NumberFormatException, ParseException{
    	value="1970-01-01T"+value;
    	DateTimeAttribute date=DateTimeAttribute.getInstance(value);
		Date dateValue=date.getValue();
		int defaultedTimeZone = date.getDefaultedTimeZone();
		if (date.getTimeZone() == TZ_UNSPECIFIED) {
            int newDefTimeZone = DateTimeAttribute.getDefaultTZOffset(new Date());
            dateValue = new Date(dateValue.getTime() - (newDefTimeZone - defaultedTimeZone)
                    * DateAttribute.MILLIS_PER_MINUTE);
            defaultedTimeZone = newDefTimeZone;
        }
    	return new TimeAttribute(dateValue,date.getNanoseconds(),date.getTimeZone(),date.getDefaultedTimeZone());
    }
    
    
    public Date getValue(){
    	return new Date(timeGMT);
    }
    
    public long getMilliseconds(){
    	return timeGMT;
    }
    
    public int getNanoseconds(){
    	return nanoseconds;
    }
    
    public int getTimeZone(){
    	return timeZone;
    }
    
    public int getDefaultedTimeZone(){
    	return defaultedTimeZone;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof TimeAttribute))
            return false;

        TimeAttribute other = (TimeAttribute) o;

        return (timeGMT == other.timeGMT && (nanoseconds == other.nanoseconds));
    }
    
    @Override
    public String toString(){
    	StringBuffer sb= new StringBuffer();
    	sb.append("TimeAttribute: [\n");
    	long secsGMT=timeGMT/1000;
    	long minsGMT=secsGMT/60;
    	secsGMT %=60;
    	long hoursGMT=minsGMT/60;
    	minsGMT %=60;
    	
    	String hoursStr=(hoursGMT<10) ? "0"+hoursGMT : "" + hoursGMT;
    	String minsStr=(minsGMT<10) ? "0"+minsGMT : "" + minsGMT;
    	String secsStr=(secsGMT<10) ? "0"+secsGMT : "" +secsGMT;
    	
    	sb.append(" Time GMT :"+hoursStr+":"+minsStr+":"+secsStr);
    	sb.append(" nanoseconds :"+nanoseconds);
    	sb.append(" TimeZone :"+ timeZone);
    	sb.append(" defaultedTimeZone :"+defaultedTimeZone);
    	
    	sb.append("]");
    	return sb.toString();
    }
    
    public String encode(){
    	if(encodeValue!=null)
    		return encodeValue;
    	int millis=(int)timeGMT;
    	StringBuffer sb=new StringBuffer(27);
    	if(defaultedTimeZone==TZ_UNSPECIFIED)
    		millis=millis+defaultedTimeZone*DateAttribute.MILLIS_PER_MINUTE;
    	else
    		millis=millis+timeZone*DateAttribute.MILLIS_PER_MINUTE;
    	
    	int hour = millis / DateAttribute.MILLIS_PER_HOUR;
        millis = millis % DateAttribute.MILLIS_PER_HOUR;
        sb.append(DateAttribute.zeroPadInt(hour, 2));
        sb.append(':');
        int minute = millis / DateAttribute.MILLIS_PER_MINUTE;
        millis = millis % DateAttribute.MILLIS_PER_MINUTE;
        sb.append(DateAttribute.zeroPadInt(minute, 2));
        sb.append(':');
        int second = millis / DateAttribute.MILLIS_PER_SECOND;
        sb.append(DateAttribute.zeroPadInt(second, 2));

        // add any nanoseconds
        if (nanoseconds != 0) {
            sb.append('.');
            sb.append(DateAttribute.zeroPadInt(nanoseconds, 9));
        }

        // if there is a specified timezone, then include that in the encoding
        if (timeZone != TZ_UNSPECIFIED) {
            int tzNoSign = timeZone;
            if (timeZone < 0) {
                tzNoSign = -tzNoSign;
                sb.append('-');
            } else
                sb.append('+');
            int tzHours = tzNoSign / 60;
            sb.append(DateAttribute.zeroPadInt(tzHours, 2));
            sb.append(':');
            int tzMinutes = tzNoSign % 60;
            sb.append(DateAttribute.zeroPadInt(tzMinutes, 2));
        }

        // remember the encoding for later
        encodeValue = sb.toString();

        return encodeValue;
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}
}

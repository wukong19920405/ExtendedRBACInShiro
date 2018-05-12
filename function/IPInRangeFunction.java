package com.wukong.function;

import java.net.InetAddress;
import java.util.List;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.attribute.IPAddressAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class IPInRangeFunction extends FunctionBase{

	 /**
     * The identifier for this function
     */
    public static final String NAME = "urn:org.wso2.balana:function:ip-in-range";


    /**
     * Default constructor.
     */
    public IPInRangeFunction() {
        super(NAME, 0, IPAddressAttribute.identifier, false, 3, BooleanAttribute.identifier, false);
    }

    /**
     * Evaluates the ip-in-range function, which takes three <code>IPAddressAttribute</code> values.
     * This function return true if the first value falls between the second and third values
     *
     * @param inputs a <code>List</code> of <code>Evaluatable</code> objects representing the
     *            arguments passed to the function
     * @param context the respresentation of the request
     *
     * @return an <code>EvaluationResult</code> containing true or false
     */
    public EvaluationResult evaluate(List inputs, EvaluationCtx context) {


        AttributeValue[] argValues = new AttributeValue[inputs.size()];
        EvaluationResult result = evalArgs(inputs, context, argValues);

        // check if any errors occured while resolving the inputs
        if (result != null)
            return result;

        // get the three ip values
        long ipAddressToTest = ipToLong(((IPAddressAttribute)argValues[0]).getAddress());
        long ipAddressMin = ipToLong(((IPAddressAttribute)argValues[1]).getAddress());
        long ipAddressMax = ipToLong(((IPAddressAttribute)argValues[2]).getAddress());

        if(ipAddressMin > ipAddressMax){
            long temp = ipAddressMax;
            ipAddressMax = ipAddressMin;
            ipAddressMin = temp;
        }

        // we're in the range if the middle is now between min and max ip address
        return EvaluationResult.getInstance(ipAddressToTest >= ipAddressMin && ipAddressToTest <= ipAddressMax);
    }


    /**
     * Helper method
     * @param ip
     * @return
     */
    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }

}

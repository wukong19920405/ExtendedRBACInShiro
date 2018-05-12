package com.wukong.function;

import java.util.List;

import com.wukong.attribute.AttributeValue;
import com.wukong.attribute.BooleanAttribute;
import com.wukong.attribute.TimeAttribute;
import com.wukong.context.EvaluationCtx;
import com.wukong.context.EvaluationResult;



public class TimeInRangeFunction extends FunctionBase{

	/**
     * The identifier for this function
     */
    public static final String NAME = FUNCTION_NS_2 + "time-in-range";

    /**
     * The number of milliseconds in a minute
     */
    public static final long MILLIS_PER_MINUTE = 1000 * 60;

    /**
     * The number of milliseconds in a day
     */
    public static final long MILLIS_PER_DAY = MILLIS_PER_MINUTE * 60 * 24;

    /**
     * Default constructor.
     */
    public TimeInRangeFunction() {
        super(NAME, 0, TimeAttribute.identifier, false, 3, BooleanAttribute.identifier, false);
    }

    /**
     * Evaluates the time-in-range function, which takes three <code>TimeAttribute</code> values.
     * This function return true if the first value falls between the second and third values (ie.,
     * on or after the second time and on or before the third time). If no time zone is specified
     * for the second and/or third time value, then the timezone from the first time value is used.
     * This lets you say time-in-range(current-time, 9am, 5pm) and always have the evaluation happen
     * in your current-time timezone.
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

        // get the three time values
        TimeAttribute attr = (TimeAttribute) (argValues[0]);
        long middleTime = attr.getMilliseconds();
        long minTime = resolveTime(attr, (TimeAttribute) (argValues[1]));
        long maxTime = resolveTime(attr, (TimeAttribute) (argValues[2]));

        // first off, if the min and max are the same, then this can only
        // be true is the middle is also the same value
        if (minTime == maxTime)
            return EvaluationResult.getInstance(middleTime == minTime);

        // shift the minTime to 00:00:00 so we can do a normal comparison,
        // taking care to shift in the correct direction (left if the
        // maxTime is bigger, otherwise right), and making sure that we
        // handle any wrapping values for the middle time (the maxTime will
        // never wrap around 00:00:00 GMT as long as we're dealing with
        // windows of less than 24 hours)                                              ur

        // the amount we're shifting
        long shiftSpan;

        // figure out the right direction and get the shift amount
        if (minTime < maxTime)
            shiftSpan = -minTime;
        else
            shiftSpan = MILLIS_PER_DAY - minTime;

        // shift the maxTime and the middleTime
        maxTime = maxTime + shiftSpan;
        middleTime = handleWrap(middleTime + shiftSpan);

        // we're in the range if the middle is now between 0 and maxTime
        return EvaluationResult.getInstance((middleTime >= 0) && (middleTime <= maxTime));
    }

    /**
     * Private helper method that is used to resolve the correct values for min and max. If an
     * explicit timezone is provided for either, then that value gets used. Otherwise we need to
     * pick the timezone the middle time is using, and move the other time into that timezone.
     */
    private long resolveTime(TimeAttribute middleTime, TimeAttribute otherTime) {
        long time = otherTime.getMilliseconds();
        int tz = otherTime.getTimeZone();

        // if there's no explicit timezone, then the otherTime needs to
        // be shifted to the middleTime's timezone
        if (tz == TimeAttribute.TZ_UNSPECIFIED) {
            // the other time didn't specify a timezone, so we use the
            // timezone specified in the middle time...
            int middleTz = middleTime.getTimeZone();

            // ...and we get the default timezone from the otherTime
            tz = otherTime.getDefaultedTimeZone();

            // if there was no specified timezone for the middleTime, use
            // the default timezone for that too
            if (middleTz == TimeAttribute.TZ_UNSPECIFIED)
                middleTz = middleTime.getDefaultedTimeZone();

            // use the timezone to offset the time value, if the two aren't
            // already in the same timezone
            if (middleTz != tz) {
                time -= ((middleTz - tz) * MILLIS_PER_MINUTE);
                time = handleWrap(time);
            }
        }

        return time;
    }

    /**
     * Private helper method that handles when a time value wraps no more than 24 hours either above
     * 23:59:59 or below 00:00:00.
     */
    private long handleWrap(long time) {
        if (time < 0) {
            // if it's negative, add one day
            return time + MILLIS_PER_DAY;
        }

        if (time > MILLIS_PER_DAY) {
            // if it's more than 24 hours, subtract one day
            return time - MILLIS_PER_DAY;
        }

        return time;
    }
}
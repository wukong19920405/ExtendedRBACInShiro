package com.wukong.attribute;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;



public class BagAttribute extends AttributeValue{
	
	private List<AttributeValue> bag;
	
	public BagAttribute(URI type,List<AttributeValue> bags){
		super(type);
		if(type==null){
			throw new IllegalArgumentException("bag require no-null type provided" );
		}
		if(bags==null||bags.size()==0)
			this.bag=new ArrayList();
		else{
			Iterator it=bags.iterator();
			while(it.hasNext()){
				AttributeValue attrValue=(AttributeValue)it.next();
				if(attrValue.isBag())
					throw new IllegalArgumentException("bag cann't contain bag");
				if(!attrValue.getType().toString().equals(type.toString())){
					throw new IllegalArgumentException("bag's type must same with inputed type");
				}
			}
			this.bag=bags;
		}
		
	}
	
	public boolean isBag(){
		return true;
	}
	
	public static BagAttribute createEmptyBag(URI type){
		return new BagAttribute(type,null);
	}
	
	public boolean isEmpty(){
		return bag.size()==0;
	}
	
	public int size(){
		return bag.size();
	}
	
	public boolean contains(AttributeValue value){
		return bag.contains(value);
	}
	
	public boolean containsAll(BagAttribute bag){
		return bag.containsAll(bag);
	}
	
	public Iterator iterator(){
		return new ImmutableIterator(bag.iterator());
	}
	private static class ImmutableIterator implements Iterator {

        // the iterator we're wrapping
        private Iterator iterator;

        /**
         * Create a new ImmutableIterator
         */
        public ImmutableIterator(Iterator iterator) {
            this.iterator = iterator;
        }

        /**
         * Standard hasNext method
         */
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Standard next method
         */
        public Object next() throws NoSuchElementException {
            return iterator.next();
        }

        /**
         * Makes sure that no one can remove any elements from the bag
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

    }
	public String encode() {
        throw new UnsupportedOperationException("Bags cannot be encoded");
    }

	@Override
	public void encode(StringBuilder sb) {
		// TODO Auto-generated method stub
		
	}

}

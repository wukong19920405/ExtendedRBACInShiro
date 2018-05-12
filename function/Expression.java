package com.wukong.function;

import java.net.URI;

public interface Expression {

	public URI getType();
	
	public boolean returnsBag();
	
	public void encode(StringBuilder sb);
}

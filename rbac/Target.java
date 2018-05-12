package com.wukong.rbac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wukong.context.EvaluationCtx;
import com.wukong.exception.ParsingException;

public class Target {

	private List<Match> matches;
	
	//private int match;	TODO
	
	public Target(List<Match> matches){
		if(matches==null)
			this.matches=Collections.unmodifiableList(new ArrayList<Match>());
		else
			this.matches=Collections.unmodifiableList(new ArrayList<Match>(matches));
	}
	public static Target getInstance(Node root) throws ParsingException{
		List<Match> matches=new ArrayList<Match>();
		NodeList children=root.getChildNodes();
		for(int i=0;i<children.getLength();++i){
			Node node=children.item(i);
			String name=DomHelper.getLocalName(node);
			if(name.equals("Match")){
				matches.add(Match.getInstance(node));
			}
		}
		return new Target(matches);
	}
	public MatchResult match(EvaluationCtx context) throws Exception{
		MatchResult result=new MatchResult(MatchResult.MATCH);
		if(matches.isEmpty())
			return new MatchResult(MatchResult.MATCH);
		for(Match match:matches){
			result=match.match(context);
			if(result.getResult()!=MatchResult.MATCH)
				break;
		}
		return result;
	}
}

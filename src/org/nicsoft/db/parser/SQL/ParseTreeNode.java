package org.nicsoft.DB.Parser.SQL;

import java.util.LinkedList;

public class ParseTreeNode extends ExpressionToken {
	
	private Token token;
	private ParseTreeNode parent;
	private LinkedList<ParseTreeNode> children;
	
	private boolean addNewChildrentToExpression;
	
	private static ParseTreeNode nullNode =
		new ParseTreeNode(
			Token.createRootToken(),
			null
		);
	
	public ParseTreeNode(Token token, ParseTreeNode parent) {
		
		super(token);
		
		this.token = token;
		this.parent = parent;
			
		this.children = new LinkedList<ParseTreeNode>();
		
		this.addNewChildrentToExpression = false;
		
	}
	
	public Token token() {
		return this.token;
	}
	
	public KeyWord keyWord() {
		return this.token.keyWord();
	}
	
	public ParseTreeNode getParent() {
		return this.parent;
	}
	
	public ParseTreeNode addChild(ParseTreeNode child) {
		
		this.children.add(child);
		
		return child;
	}
	
	public LinkedList<ParseTreeNode> getChildren() {
		return this.children;
	}
	
	public ParseTreeNode getNthChild(int n) {
		if(n > this.childCount()) {
			return ParseTreeNode.nullNode;
		}
		return this.children.get(n);
	}
	
	public ParseTreeNode getFirstChild() {
		return this.children.peekFirst();
	}
	
	public ParseTreeNode getLastChild() {
		return this.children.peekLast();
	}
	
	public int childCount() {
		return this.children.size();
	}
	
	public void setAddNewChildrentToExpression(boolean addNewChildrentToExpression) {
		this.addNewChildrentToExpression = addNewChildrentToExpression;
	}
	
	public boolean addNewChildrentToExpression() {
		return this.addNewChildrentToExpression;
	}
	
	public String toString() {
		
		if(this.token == null) {
			
			return "[ROOT]";
			
		}
		
		if(this.keyWord() == KeyWord.SET_FUNCTION_NAME) {
			return this.token().toString() +
					" (" + this.keyWord().keyWordType() + ")";
		}
		
		return this.keyWord().toString() +
				" (" + this.keyWord().keyWordType() + ")";
	}
}
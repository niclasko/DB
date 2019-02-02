package org.nicsoft.DB.Query.Expression;

import org.nicsoft.DB.Parser.SQL.KeyWord;
import org.nicsoft.DB.Parser.SQL.OperatorType;

/**
 * Created by nkjalloh on 11/11/2016.
 */
public class Atom {

    private OperatorType operatorType;
    private KeyWord keyWord;
    private Atom parent;
    private Atom lhs;
    private Atom rhs;

    private String alias;

    private Class valueMethodReturnType;

    protected Object signature;

    public Atom(OperatorType operatorType, KeyWord keyWord) {
        this.operatorType = operatorType;
        this.keyWord = keyWord;
        this.parent = null;
        this.lhs = null;
        this.rhs = null;

        try {
            this.valueMethodReturnType =
                this.getClass().getMethod("value").getReturnType();
        } catch(Exception e) {
            ;
        }
    }

    public void setValueMethodReturnType(Class valueMethodReturnType) {
        this.valueMethodReturnType = valueMethodReturnType;
    }

    public OperatorType operatorType() {
        return this.operatorType;
    }
    public KeyWord keyWord() {
        return this.keyWord;
    }
    public Atom lhs() {
        return this.lhs;
    }
    public Atom rhs() {
        return this.rhs;
    }
    public void setLeafNodes(Atom rhs, Atom lhs) {
        this.lhs = lhs;
        this.lhs.parent(this);
        this.rhs = rhs;
        this.rhs.parent(this);
    }
    public Atom parent() {
        return this.parent;
    }
    public void parent(Atom parent) {
        this.parent = parent;
    }
    public Object value() {
        return null;
    }
    public Class getValueMethodReturnType() {
        return this.valueMethodReturnType;
    }
    public String alias() { return this.alias; }
    public void alias(String alias) {
        this.alias = alias;
    }

    public boolean isOperator() {
        return this.operatorType != OperatorType.NONE;
    }

    public Object signature() {
        return this.signature;
    }
}

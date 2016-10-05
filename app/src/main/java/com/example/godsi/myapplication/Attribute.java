package com.example.godsi.myapplication;

/**
 * Super class of variable and constant
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class Attribute {

    private int id;

    public Attribute(int identifier){
        id = identifier;
    }

    public int getId(){
        return id;
    }

    public void setId(int identifier){
        id = identifier;
    }

    @Override
    //Overriding equals() to compare two Attribute objects
    public boolean equals(Object a){
        //if the object is compared with itself then return true
        if(a == this){
            return true;
        }

        //check if c is an instance of Attribute
        if(!(a instanceof Attribute)){
            return false;
        }

        //Typecast a to Attribute so that we can compare data members
        Attribute other = (Attribute) a;
        if(this.id == other.id){
            return true;
        }

        return false;
    }

    @Override
    //Overriding Hashcode() to compare two Attribute objects
    public int hashCode(){
        int hash = 17;
        hash = 31 * hash + id;
        return hash;
    }
}

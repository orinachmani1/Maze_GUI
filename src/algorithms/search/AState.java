package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {
    AState myFather;
    double cost;
    String stateName;

    public AState(){
        cost = 0;
        myFather = null;
        stateName = null;
    }

    public AState(String name){
        cost = 0;
        myFather = null;
        stateName = name;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public AState getMyFather() {
        return myFather;
    }

    public double getCost() {
        return cost;
    }

    public void setMyFather(AState myFather) {
        this.myFather = myFather;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public abstract boolean equals(Object other);
}

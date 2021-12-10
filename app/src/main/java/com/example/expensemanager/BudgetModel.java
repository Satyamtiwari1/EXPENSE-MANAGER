package com.example.expensemanager;

//With the help of this class we are gonna make object of every Single Budget which have some attributes and a Constructor
class BudgetModel {
    String id="";
    String name="";
    String Amount="";


    public BudgetModel(String id, String name, String Amount) {
        this.id = id;
        this.name = name;
        this.Amount = Amount;


    }

    public BudgetModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }



}

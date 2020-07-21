package com.stanley.budgetwatch.models;

public class Budget {
    public final String name;
    public final int max;
    public final int current;

    public Budget(final String name, final int max, final int current){
        this.name = name;
        this.max = max;
        this.current = current;
    }
}

/**
 * @author ?
 * 3/30/2021
 */
package edu.isu.cs.cs2263.todoListManager.sort;

public class SortOrder {
    //Instance Variable
    private String category;
    private Order order;

    //Constructors
    public SortOrder(){}
    public SortOrder(String category, Order order){}

    //Methods

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
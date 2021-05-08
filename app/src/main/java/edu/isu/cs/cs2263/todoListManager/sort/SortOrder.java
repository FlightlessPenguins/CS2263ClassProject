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

    public SortOrder setCategory(String category) {
        this.category = category;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public SortOrder setOrder(Order order) {
        this.order = order;
        return this;
    }
}

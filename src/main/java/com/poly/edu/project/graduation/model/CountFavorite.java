package com.poly.edu.project.graduation.model;

import javax.persistence.Column;

public class CountFavorite {

    private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CountFavorite(int quantity) {
		super();
		this.quantity = quantity;
	}

	public CountFavorite() {
		super();
	}
    
}

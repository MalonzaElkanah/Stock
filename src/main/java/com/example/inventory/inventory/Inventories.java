package com.example.inventory.inventory;

import java.util.List;

public class Inventories {
	private List<Inventory> inventories;

	private Integer totalItems = 0;
    private Integer totalSoldItems = 0;
    private Integer totalDiscrepancyItems = 0;
    private Integer totalAvailableItems = 0;

    private Double totalBuyingPrice = 0.0;
    private Double totalSoldBuyingPrice = 0.0;
    private Double totalSoldSellingPrice = 0.0;
    private Double totalAvailableBuyingPrice = 0.0;
    private Double totalAvailableSellingPrice = 0.0;
    private Double totalDiscrepancyBuyingPrice = 0.0;
    private Double totalDiscrepancySellingPrice = 0.0;
    
	public Inventories(List<Inventory> inventories) {
		this.inventories = inventories;

		for (Inventory inventory: this.inventories) {
			this.totalItems += inventory.getAllItems();
			this.totalBuyingPrice += inventory.getTotalBuyingPrice();

			this.totalSoldItems += inventory.getSoldItems();
			this.totalSoldSellingPrice += inventory.getSoldSellingPrice();

			this.totalDiscrepancyItems += inventory.getDiscrepancyItems();
			this.totalDiscrepancySellingPrice += inventory.getDiscrepancySellingPrice();

			this.totalAvailableItems += inventory.getAvailableItems();
			this.totalAvailableSellingPrice += inventory.getAvailableSellingPrice();
		}
	}

	public List<Inventory> getInventoryList() {
		return inventories;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

    public Integer getTotalSoldItems() {
    	return totalSoldItems;
    }

    public Integer getTotalDiscrepancyItems() {
    	return totalDiscrepancyItems;
    }

    public Integer getTotalAvailableItems() {
    	return totalAvailableItems;
    }

    public Double getTotalBuyingPrice() {
    	return totalBuyingPrice;
	}

    public Double getTotalSoldSellingPrice() {
    	return totalSoldSellingPrice;
    }

    public Double getTotalDiscrepancySellingPrice() {
    	return totalDiscrepancySellingPrice;
    }

    public Double getTotalAvailableSellingPrice() {
    	return totalAvailableSellingPrice;
    }
}

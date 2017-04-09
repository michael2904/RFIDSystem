package com.rfidsystem;

import java.util.ArrayList;

/**
 * Created by michaelAM on 2017-03-23.
 */
public class RFIDSystem {

	private static RFIDSystem instance = null;
	private static ArrayList<Item> items = new ArrayList<Item>();

	protected RFIDSystem() {
		// Exists only to defeat instantiation.
	}

	public static RFIDSystem getInstance() {
		if(instance == null) {
			instance = new RFIDSystem();
		}
		return instance;
	}

	public void addItem(Item item){
		items.add(item);
	}

	public boolean checkItem(String uid){
		for (Item item: items) {
			if(item.getUid().equals(uid)){
				return true;
			}
		}
		return false;
	}

	public Item getItem(String uid){
		for (Item item: items) {
			if(item.getUid().equals(uid)){
				return item;
			}
		}
		return null;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
}

package com.asiantech.mp3pro.bean;

public class ItemNavigation {
	 public String itemName;
	 public int itemIcon;
	 
	public ItemNavigation (int itemIcon, String itemName){
	 this.itemIcon = itemIcon;
	 this.itemName = itemName;
	 }
	 public String getItemName() {
	 return itemName;
	 }
	 
	public void setItemName(String itemName) {
	 this.itemName = itemName;
	 }
	 
	public int getItemIcon() {
	 return itemIcon;
	 }
	 
	public void setItemIcon(int itemIcon) {
	 this.itemIcon = itemIcon;
	 }
	 
	}

public class Item {


	
	private String name;
	private String imagePath;
	private float quantity;
	private String unit;
	private float unitPrice;
	
	public Item(String name, String imagePath, float quantity, String unit, float initPrice) 
	{
		this.name = name;
		this.imagePath = imagePath;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = initPrice;
	}
	public Item()
	{
		
	}
	
	@Override
	public String toString() {
		return "Item [name=" + name + ", imagePath=" + imagePath + ", quantity=" + quantity + ", unit=" + unit
				+ ", unitPrice=" + unitPrice + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}

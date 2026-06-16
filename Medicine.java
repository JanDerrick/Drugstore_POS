
//Class for Medicine
public class Medicine {
    
    String name;
    private double price;
    private boolean vatExempt;
    
    Medicine(String name, double price, boolean vatExempt){
        
        this.name = name;
        this.price = price;
        this.vatExempt = vatExempt;
        
    }
    
    public String getName(){
        return name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public boolean isVatExempt(){
        return vatExempt;
    }
    
}

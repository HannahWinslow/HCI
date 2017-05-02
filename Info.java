package my.vaadin.app;

public class Info {
	
	public String info1;
    public String info2;
    public String info3;
    
    public Info()
    {
    	
    }
    public Info(String n, String a, String t)
    {
    	info1 = n;
    	info2 = a;
    	info3 = t;
    }
    
    public Info getInfo()
    {
    	return this;
    }
    
    public String getInfo1()
    {
    	return info1; 
    }
    
    public void setInfo1(String _info1)
    {
    	info1 = _info1; 
    }
    
    
    public String getInfo2()
    {
    	return info2;
    }
    public void setInfo2(String _info2)
    {
    	info2 = _info2; 
    }
    
    
    public String getInfo3()
    {
    	return info3;
    }
    public void setInfo3(String _info3)
    {
    	 info3 = _info3; 
    }
}
package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        String hr = "";
        String min = "";
        boolean t = false;
        for(int i=0;i<deliveryTime.length();i++){

            char c = deliveryTime.charAt(i);
            if(!t && c!=':'){
                hr += c;
            }
            else if(c != ':'){
                min += c;
            }
            else{
                t = true;
            }
        }
        int ans = Integer.parseInt(hr)*60 + Integer.parseInt(min);
        this.deliveryTime = ans;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}

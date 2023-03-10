package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    HashMap<String,Order> orders = new HashMap<>();
    HashMap<String,DeliveryPartner> partners = new HashMap<>();
    HashMap<String,String> delivery_partners = new HashMap<>();

    HashMap<String,List<String>> partnerOrderDb = new HashMap<>();

    public void addOrder(Order order){
        orders.put(order.getId(),order);
    }

    public void addPartner(String id){
        DeliveryPartner dp = new DeliveryPartner(id);
        partners.put(id,dp);
    }
    public void addOrdersToPartners(String o_id,String p_id){
        if(orders.containsKey(o_id) && partners.containsKey(p_id)) {
            delivery_partners.put(o_id,p_id);

            List<String> currentOrders = new ArrayList<>();

            if(partnerOrderDb.containsKey(p_id))
                currentOrders = partnerOrderDb.get(p_id);

            currentOrders.add(o_id);
            partnerOrderDb.put(p_id,currentOrders);

            partners.get(p_id).setNumberOfOrders(currentOrders.size());
        }

    }
    public Order getOrderById(String o_id){

        if(orders.containsKey(o_id)){
            return orders.get(o_id);
        }
        return  null;
    }

    public DeliveryPartner getPartnerById(String id){
        if(partners.containsKey(id)){
            return partners.get(id);
        }
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {

        if(!partnerOrderDb.containsKey(partnerId)) return null;

        return partnerOrderDb.get(partnerId).size();

    }

    public List<String> getAllOrders() {
        List<String> orderList = new ArrayList<>();
        for(String o:orders.keySet()){
            orderList.add(o);
        }
        return orderList;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> ans = new ArrayList<>();

        if(partnerOrderDb.containsKey(partnerId)){
            ans = partnerOrderDb.get(partnerId);
            return ans;
        }

        return ans;
    }

    public Integer getCountOfUnassignedOrders() {
        return orders.size()-delivery_partners.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time,String partnerId) {
        int count = 0;

        if(!partnerOrderDb.containsKey(partnerId)) return 0;
        List<String> myOrders = partnerOrderDb.get(partnerId);
        for(String o_id:myOrders){
            if(orders.get(o_id).getDeliveryTime() > time){
                count++;
            }
        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime = 0;
        if(!partnerOrderDb.containsKey(partnerId)) return 0;
        List<String> myOrders = partnerOrderDb.get(partnerId);
        for(String o_id:myOrders){
            if(orders.get(o_id).getDeliveryTime() > maxTime)
                maxTime = orders.get(o_id).getDeliveryTime();
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {

        if(!partners.containsKey(partnerId)) return;
        partners.remove(partnerId);//removed from partners hashmap

        List<String> listOfOrders = partnerOrderDb.get(partnerId);
        partnerOrderDb.remove(partnerId);//removed from partnerOrderDb hashmap after seeing all the orders affiliated to the partner

        for(String order:listOfOrders){
            delivery_partners.remove(order);//removed from delivery_partners hashmap
        }
        partners.get(partnerId).setNumberOfOrders(partnerOrderDb.size());
    }

    public void deleteOrderById(String orderId) {

        if(!orders.containsKey(orderId)) return;
        orders.remove(orderId);

        String p_id = delivery_partners.get(orderId);
        delivery_partners.remove(orderId);

        partnerOrderDb.get(p_id).remove(orderId);

        partners.get(p_id).setNumberOfOrders(partnerOrderDb.size());
    }
}

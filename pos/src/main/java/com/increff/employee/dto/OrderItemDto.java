package com.increff.employee.dto;
import com.increff.employee.dto.helper.OrderItemDtoHelper;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.ApiException;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.helper.OrderItemDtoHelper.*;
import static com.increff.employee.util.StringUtil.isEmpty;

@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private OrderService orderService;
    @Transactional(rollbackFor = ApiException.class)
    public int addNew(List<OrderItemForm> orderItemForms) throws ApiException {
        validate(orderItemForms);
        OrderPojo orderPojo = orderService.add();

        for(OrderItemForm f:orderItemForms) {
            ProductPojo productPojo = productService.getIdByBarcode(f.getBarcode());
            orderItemService.add(OrderItemDtoHelper.productToOrderItem(productPojo, f,orderPojo.getId()));
        }
        return orderPojo.getId();
    }
    private void validate(List<OrderItemForm> orderItemForms) throws ApiException {
        for(int i=0;i<orderItemForms.size();i++){
            //empty checks
            if(isEmpty(orderItemForms.get(i).getBarcode()) || orderItemForms.get(i).getQuantity() <= 0){
                throw new ApiException("Barcode or Quantity is invalid");
            }
            orderItemForms.get(i).setBarcode(StringUtil.toLowerCase(orderItemForms.get(i).getBarcode()));
            //inventory check
            ProductPojo productPojo = productService.getIdByBarcode(orderItemForms.get(i).getBarcode());
            int availableQuantity = inventoryService.get(productPojo.getId()).getQuantity();
            if(availableQuantity < orderItemForms.get(i).getQuantity()){
                throw new ApiException("Invalid " +  orderItemForms.get(i).getBarcode() +" Count. Available Items = " + availableQuantity);
            }
        }
    }
    public void add(OrderItemForm orderItemForm) throws ApiException {

        if(isEmpty(orderItemForm.getBarcode())){
            throw new ApiException("Barcode cannot be empty");
        }
        if(orderItemForm.getQuantity() <= 0){
            throw new ApiException("Quantity should be a natural number");
        }
        ProductPojo productPojo = productService.getIdByBarcode(orderItemForm.getBarcode());
        int availableQuantity = inventoryService.get(productPojo.getId()).getQuantity();
        if(availableQuantity < orderItemForm.getQuantity()){
            throw new ApiException("Not enough items. Available Items = " + availableQuantity);
        }
        orderItemService.add(productToOrderItem(productPojo,orderItemForm, orderItemForm.getOrderId()));
    }

    public void delete(int id) throws ApiException {
        int orderId = orderItemService.get(id).getOrderId();
        if(orderItemService.getAllWithId(orderId).size() == 1){
            throw new ApiException("Order can't be empty");
        }
        orderItemService.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = orderItemService.get(id);
        return convert(p);
    }
public void update(int id,int newQuantity) throws ApiException {
        if(newQuantity <= 0){
            throw new ApiException("Invalid Quantity");
        }
        OrderItemPojo oldPojo = orderItemService.get(id);
        if(newQuantity > inventoryService.get(oldPojo.getProductId()).getQuantity()){
            throw new ApiException("Not enough quantity in inventory");
        }
        OrderItemPojo newPojo = new OrderItemPojo();
        updateHelper(newPojo,newQuantity,oldPojo);
        orderItemService.update(id,newPojo);
    }

    private void updateHelper(OrderItemPojo newPojo, int newQuantity, OrderItemPojo oldPojo) {
        newPojo.setOrderId(oldPojo.getOrderId());
        newPojo.setQuantity(newQuantity);
        newPojo.setId(oldPojo.getId());
        newPojo.setMrp(oldPojo.getMrp());
        newPojo.setProductId(oldPojo.getProductId());
    }

    public List<OrderItemData> getAll() throws ApiException {
        return getAllConverter();
    }
    public List<OrderItemData> getAllWithId(int id) throws ApiException {
        return getAllWithIdConverter(id);
    }
    private List<OrderItemData> getAllConverter() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
    private List<OrderItemData> getAllWithIdConverter(int id) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAllWithId(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
    private OrderItemData convert(OrderItemPojo orderItemPojo) throws ApiException {
        ProductPojo productPojo = productService.getCheck(orderItemPojo.getProductId());//todo pass product pojo into a helper function
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setMrp(orderItemPojo.getMrp());
        orderItemData.setBarcode(productPojo.getBarcode());
        return orderItemData;
    }
}

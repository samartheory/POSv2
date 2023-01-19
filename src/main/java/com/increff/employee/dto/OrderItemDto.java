package com.increff.employee.dto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.OrderItemDtoHelper.*;

@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    public void add(OrderItemForm orderItemForm) throws ApiException {
        if(StringUtil.isEmpty(orderItemForm.getBarcode())){
            throw new ApiException("Barcode cannot be empty");
        }
        if(orderItemForm.getQuantity() == 0){
            throw new ApiException("Quantity cannot be 0");
        }
        ProductPojo productPojo = productService.getIdByBarcode(orderItemForm.getBarcode());
        orderItemService.add(productToOrderItem(productPojo,orderItemForm));
    }

    public void delete(int id) {
        orderItemService.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = orderItemService.get(id);
        return convert(p);
    }

    public List<OrderItemData> getAll() {
        return getAllConverter(orderItemService);
    }
    public List<OrderItemData> getAllWithId(int id){
        return getAllWithIdConverter(orderItemService,id);
    }
}

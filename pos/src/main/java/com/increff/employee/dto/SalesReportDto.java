package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.ProductDtoHelper.convert;

@Service
public class SalesReportDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    public List<SalesReportData> getSalesReport(SalesReportForm form) throws ApiException{
        validate(form);
        String startDate = form.getStart_date();
        String endDate = form.getEnd_date();
        String brand = form.getBrand();
        String category = form.getCategory();
        List<OrderPojo> orderList = orderService.getByDate(startDate,endDate);
        List<OrderItemPojo> orderItemList = new ArrayList<OrderItemPojo>();
        for(OrderPojo orderPojo:orderList){
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAllWithId(orderPojo.getId());
            orderItemList.addAll(orderItemPojoList);
        }

        List<BrandPojo> brandList = new ArrayList<BrandPojo>();
        if(brand.equals("")&&category.equals("")){
            brandList = brandService.getAll();
        }
        else if(brand.equals("")){
            brandList = brandService.getByCategory(category);
        }
        else if(category.equals("")){
            brandList = brandService.getByBrand(brand);
        }
        else{
            BrandPojo brandCategoryPojo = brandService.getByBrandAndCat(brand,category);
            brandList.add(brandCategoryPojo);
        }
        List<SalesReportData> salesReportDataList = getSalesReportData(brandList,orderItemList);
        return salesReportDataList;
    }
    private List<SalesReportData> getSalesReportData(List<BrandPojo> brandCategoryPojos,
                                                     List<OrderItemPojo> orderItemPojos) throws ApiException {
        List<SalesReportData> salesReportDataList = new ArrayList<SalesReportData>();
        for(BrandPojo brandCategoryPojo:brandCategoryPojos){
            SalesReportData salesReportData = new SalesReportData();
            salesReportData.setCategory(brandCategoryPojo.getCategory());
            salesReportData.setBrand(brandCategoryPojo.getBrand());
            int quantity = 0;
            double revenue = 0.0;
            for(OrderItemPojo orderItemPojo:orderItemPojos){
                ProductPojo productPojo = productService.get(orderItemPojo.getProductId());
                if(productPojo.getBrand_category()==brandCategoryPojo.getId()){
                    quantity += orderItemPojo.getQuantity();
                    revenue += (orderItemPojo.getQuantity())*(orderItemPojo.getMrp());
                }
            }
            salesReportData.setQuantity(quantity);
            salesReportData.setTotal(revenue);
            salesReportDataList.add(salesReportData);
        }
        return salesReportDataList;
    }
    public static void validate(SalesReportForm salesReportForm) throws ApiException{
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand()));
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory()));
        String startDate = salesReportForm.getStart_date();
        String endDate = salesReportForm.getEnd_date();
        String brand = salesReportForm.getBrand();
        String category = salesReportForm.getCategory();
        if(startDate != "" && endDate != ""){
            if(startDate.compareTo(endDate) == 1){
                throw new ApiException("Start Date cannot be greater than End date");
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now();
        String currenDate = now.format(dtf);
        if(startDate != ""){
            if(startDate.compareTo(currenDate) == 1){
                throw new ApiException("Start date cannot be greater than current date");
            }
        }
        if(endDate != ""){
            if(endDate.compareTo(currenDate) == 1){
                throw new ApiException("End date cannot be greater than current date");
            }
        }
//        if(salesReportForm.getEnd_date()==null) {
//            salesReportForm.setEnd_date(new String());
//        }
//        if(salesReportForm.getStart_date()==null) {
//            salesReportForm.setStart(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
//        }
//        salesReportForm.setStart(getStartOfDay(salesReportForm.getStart(),Calendar.getInstance()));
//        salesReportForm.setEnd(getEndOfDay(salesReportForm.getEnd(),Calendar.getInstance()));
    }
}

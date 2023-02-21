$(document).ready(function() {
	$("#add").click(function() {
		$("#datatable").append(
			' <tr> <td> <input type="text" class="itemBarcode" style="width:60%"/></td> <td> <input type="number" class="itemQuantity" style="width:60%"/></td> </tr>'
		);
	});
});
function getNewOrderUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/orderitem/new";
}
function addOrder() {
	var itemBarcodes = document.getElementsByClassName("itemBarcode");
	var itemQuantities = document.getElementsByClassName("itemQuantity");
	let orderItems = [];

	// JSONObj

	for (let i = 0; i < itemBarcodes.length; i++) {
//	    if (!itemBarcodes[i].value && !itemQuantities[i].value) continue;
//		var orderItem = {
//			barcode: itemBarcodes[i].value,
//			quantity: parseInt(itemQuantities[i].value),
//		};
		// var jsonOrderItem = JSON.stringify(orderItem);
		var val = itemQuantities[i].value;
        if(val.includes('.') || val.includes('-')){
       	    alert("Invalid Quantity");
       	    return;
       	}
		let jsonOrderItem = new Object();
		jsonOrderItem = {
			barcode: itemBarcodes[i].value,
			quantity: itemQuantities[i].value,
			orderId : 0
		};
		orderItems.push(jsonOrderItem);
	}
	// var $form = $("#brand-form");
	// var json = toJson($form);
	var url = getNewOrderUrl();
    console.log(JSON.stringify(orderItems))
	$.ajax({
		url: url,
		type: "POST",
		data: JSON.stringify(orderItems),
		dataType: "json",
		headers: {
			"Content-Type": "application/json",
		},
		success: function(data) {
		    handleSuccess("Order Made");
            var url = $("meta[name=baseUrl]").attr("content") + "/ui/orderitem/" + data;
            window.location.replace(url);
		},
		error: handleAjaxError,
//		statusCode: {
//			200: function() {
//				alert("order saved");
//				var url = $("meta[name=baseUrl]").attr("content") + "/ui/orders";
//				window.location.replace(url);
//			},
//			400: handleAjaxError
//		}
	});

	return false;
}
function myDeleteFunction() {
  document.getElementById("datatable").deleteRow(-1);
}
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem/new";
}

//BUTTON ACTIONS
function addNewItem(){
     var rowCount = $("#item-details tr").length;
     console.log(rowCount);
     var table = document.getElementById("item-details");
     var row = table.insertRow(rowCount);
//     var cell1 = row.insertCell(0);
     row.innerHTML = '<td><label for="barcode">Barcode</label><input type="text" id="barcode" placeholder="Enter Barcode"></td><td ><label for="quantity">Quantity</label><input type="text" id="quantity" placeholder="Enter Quantity"></td>';
}
function submits(event){
    	var $form = $("#form");
    	var json = toJson($form);
    	console.log($form);
    	console.log(json);
//    	var url = getOrderItemUrl();
//    	$.ajax({
//    	   url: url,
//    	   type: 'POST',
//    	   data: json,
//    	   headers: {
//           	'Content-Type': 'application/json'
//           },
//    	   success: function(response) {
//    	   		alert("Successfully Submitted")
//    	   },
//    	   error: handleAjaxError
//    	});
//
//    	return false;
}
function addOrderItem(event){
	//Set the values to update
	var $form = $("#order-item-form");
	document.getElementById("inputId").value = orderIdFromUrl();
	var json = toJson($form);
	console.dir(json);
	var url = getOrderItemUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderItemList(Number(orderIdFromUrl()));
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateOrderItem(event){
	$('#edit-order-item-modal').modal('toggle');
	//Get the ID
	var id = $("#order-item-edit-form input[name=id]").val();
	var url = getOrderItemUrl() + "/" + id;

	//Set the values to update
	var $form = $("#order-item-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderItemList(id);
	   },
	   error: handleAjaxError
	});

	return false;
}

function getOrderItemListHelper(){
    getOrderItemList(Number(orderIdFromUrl()));
}
function getOrderItemList(id){
	var url = getOrderItemUrl()+"/with/"+id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderItemList(Number(orderIdFromUrl()));
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderItemFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getOrderItemUrl();
    console.log(json);
	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}
function redirect(id){
    window.location.href();//uncompleted code
}
//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#order-item-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
        var buttonHtml = '<button type="button" class="btn btn-primary btn-sm" onclick="displayEditOrderItem(' + e.id + ')">Edit</button>  '
            buttonHtml += '<button type="button" class="btn btn-primary btn-sm" onclick="deleteOrderItem(' + e.id + ')">Delete</button>  '

        var row = '<tr>'
                + '<td>' + e.id + '</td>'
                + '<td>' + e.barcode + '</td>'
                + '<td>' + e.quantity + '</td>'
                + '<td>' + e.mrp + '</td>'
                + '<td>' + buttonHtml + '</td>'
                + '</tr>';
        $tbody.append(row);
	}
}

function displayEditOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderItemFile');
	$file.val('');
	$('#OrderItemFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderItemFile');
	var fileName = $file.val();
	$('#OrderItemFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-order-item-modal').modal('toggle');
}

function displayOrderItem(data){
	$("#order-item-edit-form input[name=barcode]").val(data.barcode);
	$("#order-item-edit-form input[name=quantity]").val(data.quantity);
	$('#edit-order-item-modal').modal('toggle');
}

//INITIALIZATION CODE
//function init(){
//console.log("inside init");
//	$('#add-data').click(addOrderItem);
//	$('#update-order-item').click(updateOrderItem);
//	$('#refresh-data').click(getOrderItemListHelper);
//	$('#upload-data').click(displayUploadData);
//	$('#process-data').click(processData);
//	$('#download-errors').click(downloadErrors);
//    $('#orderItemFile').on('change', updateFileName)
//}
//
//$(document).ready(init);
//$(document).ready(getOrderItemListHelper);

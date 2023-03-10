
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}

function getPlaceUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orders/";
}
function orderIdFromUrl(){
        var queryString = window.location.href;
        console.log(queryString);
        // Get the value of a specific parameter
        var idfromurl =  queryString.split("orderitem/")[1];
        return Number(idfromurl);
}
function orderIdTitle(){
    // Get the query string from the URL
    var queryString = window.location.href;
    console.log(queryString);
    // Get the value of a specific parameter
    var idfromurl =  queryString.split("orderitem/")[1];
    console.log(idfromurl);
    var myElement = document.getElementById("order-item-title");
    myElement.innerHTML = "Order #" + idfromurl;
}
//BUTTON ACTIONS
function place(){
    var url = getPlaceUrl() + orderIdFromUrl();
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		var url = $("meta[name=baseUrl]").attr("content") + "/ui/orderitemplaced/" +  orderIdFromUrl();
                window.location.replace(url);
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}
function addOrderItem(event){
	//Set the values to update
	var $form = $("#order-item-form");
	document.getElementById("inputId").value = orderIdFromUrl();
	var json = toJson($form);
	console.log(json);
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
	$('#order-item-edit-form').modal('toggle');
	//Get the ID
	var id = $("#order-item-edit-form input[name=id]").val();
	var url = getOrderItemUrl() + "/" + id;
    var newQ = $("#order-item-edit-form input[name=quantity]").val();
    console.log(newQ)
	//Set the values to update
//	var $form = $("#order-item-edit-form");
//	var json = toJson($form);
    var jsontext = '{"quantity":"'+newQ+'"}'
    const json = JSON.parse(jsontext);
    console.log(jsontext)
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: newQ,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		location.reload()
	   },
	   error: handleAjaxError
	});

	return false;
}

function getOrderItemListHelper(){
    getOrderItemList(Number(orderIdFromUrl()));
}
function getOrderItemList(id){
    console.log("atl")
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
console.log("disp")
	var $tbody = $('#order-item-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
        var buttonHtml = '<button type="button" class="btn btn-primary btn-sm" onclick="displayEditOrderItem(' + e.id + ')">Edit</button>  '
            buttonHtml += '<button type="button" class="btn btn-primary btn-sm" onclick="deleteOrderItem(' + e.id + ')">Delete</button>  '
        var mrps = e.mrp.toString();
        		if(mrps.indexOf('.') == -1){
        		    mrps += ".00"
        		}
        var row = '<tr>'
                + '<td>' + e.id + '</td>'
                + '<td>' + e.barcode + '</td>'
                + '<td>' + e.quantity + '</td>'
                + '<td>' + mrps + '</td>'
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
console.log(data)
	$("#order-item-edit-form input[name=id]").val(data.id);
	$("#order-item-edit-form input[name=quantity]").val(data.quantity);
	$('#order-item-edit-form').modal('toggle');
}

//INITIALIZATION CODE
function init(){
console.log("inside init");
	$('#add-data').click(addOrderItem);
	$('#update-order-item').click(updateOrderItem);
	$('#refresh-data').click(getOrderItemListHelper);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderItemFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderItemListHelper);
$(document).ready(orderIdTitle);

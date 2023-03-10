
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem";
}
function orderIdFromUrl(){
        var queryString = window.location.href;
        console.log(queryString);
        // Get the value of a specific parameter
        var idfromurl =  queryString.split("orderitemplaced/")[1];
        return Number(idfromurl);
}
function orderIdTitle(){
    // Get the query string from the URL
    var queryString = window.location.href;
    console.log(queryString);
    // Get the value of a specific parameter
    var idfromurl =  queryString.split("orderitemplaced/")[1];
    console.log(idfromurl);
    var myElement = document.getElementById("order-item-title");
    myElement.innerHTML = "Order #" + idfromurl + " [Placed]";
}
function onlyBaseUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}
//BUTTON ACTIONS
function downloadInvoice(){
    var url = onlyBaseUrl() + "/api/orders/invoice/" + orderIdFromUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		downloadPDF(data);
    	   },
    	   error: handleAjaxError
    	});
}
function downloadPDF(pdf) {
    const linkSource = `data:application/pdf;base64,${pdf}`;
    const downloadLink = document.createElement("a");
    const fileName = "invoice.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
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

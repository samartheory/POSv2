function navbarSelect(){
    var role = $("meta[name=role]").attr("content");
    if(role == "admin"){
//        document.getElementById("navselect").innerHTML = '<nav th:replace="snippets.html :: name_app_navbar"></nav>'
//    $('#navselect').html('<nav th:replace="snippets.html :: name_app_navbar"></nav>');
        document.getElementById("foradmin").style.display = "block";
    }
    else{
        document.getElementById("forstandard").style.display = "block";
    }


}
$(document).ready(navbarSelect);
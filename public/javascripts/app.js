$.get("/ajax", function(data) {
    $("#info").append("Welcome to your Play application's JavaScript!");
})

var specialKeys = new Array();
specialKeys.push(8); //Backspace
specialKeys.push(9); //Tab
specialKeys.push(46); //Delete
specialKeys.push(36); //Home
specialKeys.push(35); //End
specialKeys.push(37); //Left
specialKeys.push(39); //Right
specialKeys.push(13); //Enter
function IsAlphaNumeric(e) {
    var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = ((keyCode >= 48 && keyCode <= 57)
            || (keyCode >= 65 && keyCode <= 90)
            || (keyCode >= 97 && keyCode <= 122)
            || (specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode));
//    document.getElementById("error").style.display = ret ? "none" : "inline";
    return ret;
}

$(document).ready(function() {
    var pressed = false;
    var chars = [];
    $(window).keypress(function(e) {
        if (IsAlphaNumeric(e)) {
            $("#input").append(String.fromCharCode(e.which));
        }
        if (e.which == 13) {
            //submit code to server
            var checkData = $("#input").text();
            $.ajax({
                url: "/check",
                type: "POST",
                data: checkData,
                success: function(data, textStatus, jqXHR) {

                },
                error: function(jqXHR, textStatus, errorThrown) {

                }
            });

        }
    });
});

(function($) {

})(jQuery);
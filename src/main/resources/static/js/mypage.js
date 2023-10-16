function userInfoUpdate() {
    var btn = document.getElementsByClassName("modMember")[0];
    var chbtn = document.getElementsByClassName("updateBtn");
    var info = document.getElementsByClassName("userInfo");

    for (var i = 0; i < info.length; i++) {
        if (info[i].readOnly) {
            info[i].readOnly = false;
        } else {
            info[i].readOnly = true;
        }
    }
    btn.style.display = (btn.style.display === 'none') ? 'block' : 'none';

    for (var i = 0; i < chbtn.length; i++) {
        if (chbtn[i].style.display === "block") {
            chbtn[i].style.display = "none";
        } else {
            chbtn[i].style.display = "block";
        }
    }
}
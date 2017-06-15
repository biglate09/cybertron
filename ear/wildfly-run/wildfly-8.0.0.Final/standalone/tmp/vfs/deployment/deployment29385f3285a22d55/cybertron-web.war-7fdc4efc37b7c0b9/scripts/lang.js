var cn = 'lang',lan =  getCookie(cn)||setCookie(cn,'th');
document.getElementsByTagName('body')[0].setAttribute("lang", lan);

function selectLang(lan){
	document.getElementsByTagName('body')[0].setAttribute("lang", setCookie(cn,lan||'th'));
	changePageholder();
}
function setCookie(cname, cvalue, exdays) {
	cvalue = /^(th|en)$/.test(cvalue)?cvalue:'th';
    var d = new Date();
    d.setTime(d.getTime() + ((exdays||365)*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
    return cvalue;
}
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length,c.length);
        }
    }
    return "";
}
var getLang = getCookie.bind(this,cn);
//var pho = $('[placeholder-en],[placeholder-th]');
//function changePageholder(){
//	var lang = getLang();
//	$.each(pho,function(i,o){$(o).attr('placeholder',$(o).attr('placeholder-'+lang)); });
//}
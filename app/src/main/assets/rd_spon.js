

function hidebuttons(){
var y = document.getElementsByTagName('span')

for (var i = 0; i < y.length; i++) {

      if(y[i].innerHTML.indexOf('Desktop Site') !== -1){
     y[i].parentElement.parentElement.style.cssText = 'display:none !important';
     }
     if(y[i].innerHTML.indexOf('Ask To Open') !== -1){
          y[i].parentElement.parentElement.style.cssText = 'display:none !important';
          }
          if(y[i].innerHTML.indexOf('Reddit Rules') !== -1){
                    y[i].parentElement.parentElement.style.cssText = 'display:none !important';
                    }
                    if(y[i].innerHTML.indexOf('About Reddit') !== -1){
                                        y[i].parentElement.parentElement.style.cssText = 'display:none !important';
                                        }

}

}
function killads() {
/*
var links = document.getElementsByTagName("a");
    for (var i = 0, l = links.length; i < l; i++) {
            //links[i].href = ""
            links[i].target = "_parent";
    }*/

var y = document.getElementsByTagName('span')

for (var i = 0; i < y.length; i++) {
     if (y[i].innerHTML.indexOf('promoted') !== -1 || y[i].innerHTML.indexOf('PROMOTED') !== -1) {
       y[i].parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.style.cssText = 'display:none !important';

     }
}}


function showFoo() {
var y = document.getElementsByClassName("opened")
for (var i = 0; i < y.length; i++) {

}
if (y.length==0){
   killads();
}
else {hidebuttons();}
}

function MakeMenuLinksOpenInNewWindow() {
    var links = document.getElementsByTagName("a");
    for (var i = 0, l = links.length; i < l; i++) {

            links[i].target = "_self";
    }
}

MakeMenuLinksOpenInNewWindow()
killads();
setInterval(showFoo, 500);









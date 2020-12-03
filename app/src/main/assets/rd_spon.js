

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
var y = document.getElementsByTagName('span')

for (var i = 0; i < y.length; i++) {
     if (y[i].innerHTML.indexOf('PROMOTED') !== -1) {
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


killads();
setInterval(showFoo, 500);










function killads() {
var elespan = document.getElementsByTagName('span')

var sponsoredTexts = [
  'Propagováno',
  '赞助内容', // Chinese Simplified
  '贊助', // Chinese (Traditional)
  'Propagated', // Czech
  'Sponsorisé', // French
  'Gesponsert', // German
  'प्रायोजित', // Hindi
  'Patrocinado', // Portuguese (Brazil)
  'Publicidad', // Spanish
  'Sponsorlu', // Turkish
  'Được tài trợ', // Vietnamese
];

for (var i = 0; i < elespan.length; i++) {
for (var i2 = 0; i2 < sponsoredTexts.length; i2++) {

     if (elespan[i].innerHTML.indexOf(sponsoredTexts[i2]) !== -1) {
       elespan[i].parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.style.cssText = 'display:none !important';

     }}
}}



setInterval(killads, 750);






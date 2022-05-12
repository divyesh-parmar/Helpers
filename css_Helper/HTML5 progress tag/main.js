// when the window loads, we want to call the "addTenPercent" function
window.onload = addTenPercent();
// start the "add ten percent" function
function addTenPercent() {
    // locate the progress bar and store it in a variable
    var bar = document.getElementById("progressBar");
    // set a timer to call the function every 3 seconds using setInterval()
    setInterval(addTenPercent, 2000);
    // add 10 to the value
    bar.value += 10;
};

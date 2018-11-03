var startSendingButton = document.getElementById('startSending');
Rx.DOM.click(startSendingButton)
    .subscribe(startSending);
var subscription;
var numberSent = 0;
function startSending() {
    if (subscription != null) {
        subscription.dispose();
    }
    document.getElementById("message").innerHTML = "";
    subscription = Rx.DOM.fromEventSource('/sendMeasurements')
        .subscribe(function (n) { return addToLog(n); });
}
function addToLog(messageNumber) {
    numberSent++;
    document.getElementById("message").innerHTML = "Sent message " + messageNumber + " Total number sent: " + numberSent;
}

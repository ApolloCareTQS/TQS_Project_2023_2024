const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/callscreen'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/pushmessages', (message) => {
        console.log(JSON.parse(message.body));
        showMessage(JSON.parse(message.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function showMessage(message) {
    const postMessageId = `#${message.callPost}-message`;
    const postMessageElement = $(postMessageId);
    if (postMessageElement.length) {
        postMessageElement.html(`
            <strong>Call Type:</strong> ${message.callType} <br>
            <strong>Call Number:</strong> ${message.callNumber} <br>
            <strong>Timestamp:</strong> ${message.timestamp}
        `);
    } else {
        console.error(`Post ${message.callPost} not found.`);
    }
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
});

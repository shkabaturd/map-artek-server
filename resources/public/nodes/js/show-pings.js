var server_ip = document.getElementById("server-ip").className
var ws = new WebSocket("ws://" + server_ip + ":80/ws");
var node_name = document.getElementById("name").className
var b = []
b.push(node_name)
var chart = c3.generate({
    bindto: '#chart',
    data: {
      columns: [
        b

      ]
    }
});

ws.onmessage = function(e) {
	let nodes = JSON.parse(e.data);
	nodes.forEach( (n) => {
		if(n.name == node_name) {
			b.push(n.ping)
		}
	})
	chart.load({
        columns: [
            b
        ]
    });
}

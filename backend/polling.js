var Parse = require('parse').Parse;
var figlet = require("figlet");
var arDrone = require('ar-drone');
var client  = arDrone.createClient({ip: '192.168.43.238'});

Parse.initialize("PARSE_API", "PARSE_API");

var polling_interval = 400; //1 sec

var TestObject = Parse.Object.extend("Commands");
var query = new Parse.Query(TestObject);

setInterval(function() {
	query
	.limit(1)
	.ascending("createdAt")
	.find({
	  success: function(commands) {
	  	if (commands.length > 0) {
	  		process(commands[0]);	
	  	}
	  }
	});
}, polling_interval);


// Constants for supported commands
var DRON_FWD = "forward";
var DRON_BACK = "back";

var process = function (dronCmd) {
	switch (dronCmd.get('cmd')) {
		case DRON_FWD:
	        dron_fwd();
	        break;

        case DRON_BACK:
	        dron_back();
	        break;

	    default: 
	    	console.error("Unsupported command: " + dronCmd.get('cmd'));
	}


	// destroy object after processing the command
	dronCmd.destroy({
	  success: function(myCmd) {
	    // The object was deleted from the Parse Cloud.
	    console.log("deleted ["+myCmd.get('cmd')+"]");

	  },
	  error: function(myCmd, error) {
	    // The delete failed.
	    console.log("error deleting " + myCmd.id);
	  }
	});
} 


function dron_fwd () {
	console.log("Dron FORWARD");
	fwd();
}

function dron_back () {
	console.log("Dron BACK");
	bck();
}


function fwd () {
client.disableEmergency();
  client.takeoff();
  client.up(0.3);
  client
      .after(8000, function() {
        this.animate('flipLeft', 700);
      })
      .after (1500, function () {
        client.front(0.5)

      })
      .after (2000, function () {
      this.stop();
      this.land();
    });
}


function bck () {
  client.disableEmergency();
  client.takeoff();
  client.up(0.3);
  client
      .after(8000, function() {
        client.animateLeds('blinkRed', 5, 2)
      })
      .after (1000, function () {
        client.front(-0.5)

      })
      .after (2000, function () {
      this.stop();
      this.land();
    });
}


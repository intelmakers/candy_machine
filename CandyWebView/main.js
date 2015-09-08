/*jslint node:true, vars:true, bitwise:true, unparam:true */
/*jshint unused:true */
/*global */
/*
A simple node.js application intended to read data from Analog pins on the Intel based development boards 
such as the Intel(R) Galileo and Edison with Arduino breakout board, and display it in a browser running on the client.

This demonstrates use of http.createServer, and fs.

MRAA - Low Level Skeleton Library for Communication on GNU/Linux platforms
Library in C/C++ to interface with Galileo & other Intel platforms, in a structured and sane API with port nanmes/numbering that match boards & with bindings to javascript & python.
  
Steps for installing MRAA & UPM Library on Intel IoT Platform with IoTDevKit Linux* image
Using a ssh client: 
1. echo "src maa-upm http://iotdk.intel.com/repos/1.1/intelgalactic" > /etc/opkg/intel-iotdk.conf
2. opkg update
3. opkg upgrade

Article: https://software.intel.com/en-us/xdk-sample-creating-a-web-server
*/

// Set this to the ip address of your board (not 127.0.0.1)
var ipAddress = '1.2.2.137'; 

var mraa = require('mraa'); //require mraa
console.log('MRAA Version: ' + mraa.getVersion()); //write the mraa version to the console

// Start by loading in some data
var fs = require('fs');
imageDir = __dir;

var CandyWebPage = fs.readFileSync('/node_app_slot/candyWebPage.html');

// Insert the ip address in the code in the page

CandyWebPage = String(CandyWebPage).replace(/<<ipAddress>>/, ipAddress);

var http = require('http');
http.createServer(function (req, res) {
    pic = req.image;
    var value;
    // This is a very quick and dirty way of detecting a request for the page
    // versus a request for light values
	if (typeof pic === 'undefined') {
		if (req.url.indexOf('candyWebPage') != -1) {
			res.writeHead(200, {'Content-Type': 'text/html'});
			res.end(CandyWebPage);
		}
		else {
			value = 1;
			res.writeHead(200, {'Content-Type': 'text/json'});
			res.end(JSON.stringify({rawValue:value}));
		}
	}
	else {
        //read the image using fs and send the image content back in the response
        fs.readFile(imageDir + pic, function (err, content) {
            if (err) {
                res.writeHead(400, {'Content-type':'text/html'})
                console.log(err);
                res.end("No such image");    
            } else {
                //specify the content type in the response will be an image
                res.writeHead(200,{'Content-type':'image/png'});
                res.end(content);
            }
        });
    }
	
}).listen(1337, ipAddress);


//get the list of jpg files in the image dir
function getImages(imageDir, callback) {
    var fileType = '.png',
        files = [], i;
    fs.readdir(imageDir, function (err, list) {
        for(i=0; i<list.length; i++) {
            if(path.extname(list[i]) === fileType) {
                files.push(list[i]); //store the file name into the array files
            }
        }
        callback(err, files);
    });
}
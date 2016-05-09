
var express     =    require('express'),
    app         =    express(),
    exec        =    require('child_process').exec,
    fs          =    require('fs');

app.use(express.static(__dirname));

app.get('/',function(req,res){
      res.sendFile(__dirname + "/index.html");
});

app.post('/',[
  function(req, res){
    child = exec("touch /home/root/events/candy_PUT.txt");
}]);

app.listen(8080,function(){
    console.log("Working on port 8080");
});


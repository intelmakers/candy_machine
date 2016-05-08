
/*
color("red")
translate([-10.5,0.93,0]) 
rotate([0,0,90])
 // bring to origin
    import("test.stl", convexity = 5);
*/

x_dist = 100;
y_dist = 50;
z_dist = 15;
margin = 12;

// hub - 50 x 79
difference() { 
    union() 
    {
        translate([0,4,0])
           cube([x_dist+margin, y_dist+margin+4, 1.6],center=true);
        //legs(7.4/2+1.4,z_dist,x_dist,y_dist,30);
    }
 

    translate([0,0,-5])
    legs(7.4/2,z_dist+10,x_dist,y_dist,30);
}

translate([-4,2,0])   
rotate([0,0,90])
import("usb_hub_hole.stl");


module legs(radius, hight, length, width, fn=30)
{
    $fn = fn;
    translate([-length/2, -width/2, 0])
    {    translate([0.5,2,0])
            cylinder(r=radius, h=hight);
        color("blue") translate([length+0.5,width+5.5,0])
           cylinder(r=radius, h=hight);
       color("green")  translate([0,width,0])
           cylinder(r=radius, h=hight);
       color("white") 
        translate([length+0.5,3,0])
           cylinder(r=radius, h=hight);
    }
}
    
/*
// hole for USB Power
color("blue")
        translate([5,-1,5])
          cube([6,20,30]);

// hole for servo and sensor connection
color("red")

    translate([16,-1,5])
          cube([21,15,17]);


// hole for switch
color("green")
    translate([-1,37,7])
          cube([20,12,17]);



// hole for servo
color("orange")
    translate([12,65,5])
          cube([15,13,17]);
    
    }
    */
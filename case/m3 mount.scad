
$fn = 30;

wall_with = 3.3;
thinkness = 1.6;

highet = 30;
width = 20;

cut_highet = 18;

screw_radious = 3.1;

difference() {
    color ("green") cube([wall_with+thinkness*2,width,highet], center=true);
    
    // The slot
    translate([0,0,-thinkness])
      cube([wall_with,width+1,highet], center = true);
      
    // cut so we can place screw
    color("red")
    translate([thinkness,0,cut_highet*.5-highet/2])
      cube([wall_with+thinkness,width+1,cut_highet], center = true);
    
    
    color("blue")
    translate([0,0,cut_highet*.5-highet/2])
      rotate([0,90,0])
       cylinder(r=screw_radious, h = 5 * (wall_with + 2*thinkness), center = true);
    }

$fn = 30;

wall_with = 3.4;
thinkness = 4;

highet = 20;
width = 20;
cut_highet = 8;

base_width = 145;

stand_shift = 30;
stand_low = 10;
stand_length = 60;
angle = 50;
stand_high = stand_length * tan(angle);

echo ("stand_high = ", stand_high);




difference() {
    color ("green") cube([wall_with+thinkness*2,width,highet], center=true);
    
    // The slot
    translate([0,0,-thinkness])
      cube([wall_with,width+1,highet], center = true);
      
    // cut so we can place screw
    color("red")
    translate([thinkness,0,cut_highet*.5-highet/2])
      cube([wall_with+thinkness,width+1,cut_highet], center = true);
    
 
    }
   
// the part that gaps over both sides of box 
color ("blue") 
  translate([-(wall_with+thinkness*2)/2,-width/2,-thinkness+highet/2])
    cube([base_width,width,thinkness]);

// the part that holds the stand on the other side   
translate([base_width-(wall_with+3*thinkness)/2,0,0]) 
   cube([thinkness,width,highet], center=true);
    
    
// the part that stops the tablet from moving
    // the part that holds the stand on the other side   
color("magenta")
translate([stand_shift,0,highet/2+stand_low/2-thinkness]) 
   cube([thinkness,width,stand_low], center=true);
    
translate([stand_shift+stand_length,0,highet/2+stand_high/2-thinkness]) 
   cube([thinkness,width,stand_high], center=true);
    
    
    
    
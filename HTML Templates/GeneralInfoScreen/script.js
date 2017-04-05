var noteDiv = jQuery("#noteDiv");
var image = jQuery("#image");
var tl = new TimelineMax();

jQuery(window).load(function($) {				
			tl.fromTo(image, 2, {
				scale: 0,
				rotation: -360,
				opacity: 0
			}, {
				scale: 1,
				rotation: 0,
				opacity: 1,
				ease: Circ.easeOut
			});
			
			tl.fromTo(noteDiv, 4, {
				transformOrigin: "50% 50% 0",
				scale: 0.8,
				opacity: 0
			}, {
				transformOrigin: "50% 50% 0",
				scale: 1,
				opacity: 1,
				ease: Back.easeOut,
				yoyo:true,
				repeat: -1
			});
			
			
        });
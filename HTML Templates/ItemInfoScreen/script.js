var titleDiv = jQuery("#titleDiv");
var title = jQuery("#title");
var descriptionDiv = jQuery("#descriptionDiv");
var description = jQuery("#description");
var image = jQuery("#image");
var priceDiv = jQuery("#priceDiv");
var price = jQuery("#price");
var tl = new TimelineMax();

jQuery(window).load(function($) {
            var xmlDoc = loadXMLDoc("Data.xml");
			console.log(xmlDoc);
            var titleXML = xmlDoc.getElementsByTagName("title")[0];
            var descriptionXML = xmlDoc.getElementsByTagName("description")[0];
            var imageXML = xmlDoc.getElementsByTagName("imageURL")[0];
            var priceXML = xmlDoc.getElementsByTagName("price")[0];
			
			title.text(titleXML.textContent);
			description.text(descriptionXML.textContent);
			image.attr("src", imageXML.textContent);
			price.text("Price: " + priceXML.textContent + "CAD");
			
			tl.fromTo(titleDiv, 2, {
				y: -200,
				opacity: 0
			}, {
				y: 0,
				opacity: 1,
				ease: Bounce.easeOut
			});
			
			tl.fromTo(image, 2, {
				scale: 0,
				rotation: -360,
				opacity: 0
			}, {
				scale: 1,
				rotation: -3,
				opacity: 1,
				ease: Circ.easeOut
			});
			
			tl.fromTo(descriptionDiv, 1, {
				transformOrigin: "50% 50% 0",
				scale: 0,
				opacity: 0
			}, {
				transformOrigin: "50% 50% 0",
				scale: 1,
				opacity: 1,
				ease: Back.easeOut
			});
			
			tl.fromTo(priceDiv, 1, {
				transformOrigin: "50% 50% 0",
				scale: 0,
				opacity: 0
			}, {
				transformOrigin: "50% 50% 0",
				scale: 1,
				opacity: 1,
				ease: Back.easeOut
			},'-=0.5');

			 
			function loadXMLDoc(filename) {
                if (window.XMLHttpRequest) {
                    xhttp = new XMLHttpRequest();
                } else // code for IE5 and IE6
                {
                    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                xhttp.open("GET", filename, false);
                xhttp.send();
                return xhttp.responseXML;
            }
        });
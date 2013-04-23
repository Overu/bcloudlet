function content(elm) {
	var $elm = $(elm);
	if ($elm.attr("aria-expanded")) {
		return;
	}
	$("div.center").text($elm.text());
}

function onSelect(e) {
	content(e.item);
}


$(function() {
	var gridDataSource = new kendo.data.DataSource({
		transport: {
			read: {
				url: "http://localhost:5051/books",
				dataType: json
			}
		}
	});
	
	
	$("#splitter").kendoSplitter({
		orientation : "horizontal",
		panes : [ {
			collapsible : true,
			size : "240px"
		}, {
			collapsible : false,
			resizable : false
		}, ]
	});
	$("#panelBar").kendoPanelBar({

		select : onSelect
	});
});
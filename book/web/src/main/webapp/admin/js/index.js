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
		transport : {
			read : {
				url : "/books", // the remove service url
				dataType : "json" // JSONP (JSON with padding) is required for
			},
			parameterMap : function(data, type) {
				if (type == "read") {
					// send take as "$top" and skip as "$skip"
					return {
						limit : data.pageSize,
						start : data.skip
					};
				}
			}
		},
		schema : { // describe the result format
			data : "items", // the data which the data source will be bound to
			// is in the "results" field
			model : {
				fields : {
					id : {
						type : "string"
					},
					title : {
						type : "string"
					},
					summary : {
						type : "string"
					}
				}
			},
			total : "count"
		},
		pageSize : 5,
		serverPaging: true
	});
	$("#grid").kendoGrid({
		dataSource : gridDataSource,
		height : 430,
		sortable : true,
		pageable : true,
		columns : [ {
			field : "id",
			title : "Id",
		}, {
			field : "title",
			title : "Title",
			width : 260
		}, {
			field : "summary",
			title : "Summary",
			width : 260
		} ]
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
	// select : onSelect
	});
	var panelBar = $("#panelBar").data("kendoPanelBar");
});
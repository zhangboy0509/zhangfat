/**
 * 地址联动JS
 * 使用方法：
 * 1.在页面中引用JS：
 *		数据源：<script src="${urlFormatUtil.getBaseUrlDomain()}/datacache/region_json_data" type="text/javascript"></script>
 *		JS代码：<script src="${urlFormatUtil.getJsBasePath()}/region_select.js" type="text/javascript"></script>--JS
 * 2.页面中加入JS代码：
 * 		jQuery(document).ready(function (){
 *			var a = new RegionSelect({
 *				regionD1:regionD1,
 *				regionD2:regionD2,
 *				regionD3:regionD3,
 *				regionD3:regionD4,
 *				regionD1Id:"region_D1",
 *				regionD2Id:"region_D2",
 *				regionD3Id:"region_D3",
 *				regionD4Id:"region_D4",
 *				regionD1Value:"1000000",
 *				regionD2Value:"1040000",
 *				regionD3Value:"1040500",
 *				regionD4Value:""
 *			});
 *		});
 *	说明：
 *	regionD1,regionD2,regionD3,regionD4为数据源如果数据源在JS代码之前引用则不需要再添加数据源
 *	regionD1Id,regionD1Id,regionD1Id,regionD1Id为联动select的ID，如果与默认ID相同，则不需要再添加ID数据
 *	regionD1Value,regionD2Value,regionD3Value,regionD4Value为联动的初始数据，若无初始数据则不需要添加
 * @author 张博洋
 * @param config
 * @returns {RegionSelect}
 */
RegionSelect = function( config ){
	var this_ = this;
	var props = {
		regionD1:regionD1,
		regionD2:regionD2,
		regionD3:regionD3,
		regionD4:regionD4,
		regionD1Id:"region_D1",
		regionD2Id:"region_D2",
		regionD3Id:"region_D3",
		regionD4Id:"region_D4",
		regionD1Value:"",
		regionD2Value:"",
		regionD3Value:"",
		regionD4Value:"",
		regionValue:""
	};
	
	for (var p in props) {
		props[p] = (typeof config[p] == 'undefined') ? props[p] : config[p];
	}
	
	if( props.regionValue!="" ){
		var r1Value = "";
		var r2Value = "";
		var r3Value = "";
		var r4Value = "";

		if( props.regionD4!=undefined && props.regionD4!="" ){
			r4Value = props.regionValue;
			$.each(props.regionD4,function(key,value){
				for( var i=0;i<value.length;i++ ){
					if( value[i].id==r4Value ){
						props.regionD3Value = key;
						props.regionD4Value = r4Value;
						break;
					}
				}
			});
		}

		if( props.regionD3!=undefined && props.regionD3!="" ){
			if( props.regionD3Value!="" ){
				r3Value = props.regionD3Value;
			}else{
				r3Value = props.regionValue;
			}
			$.each(props.regionD3,function(key,value){
				for( var i=0;i<value.length;i++ ){
					if( value[i].id==r3Value ){
						props.regionD2Value = key;
						props.regionD3Value = r3Value;
						break;
					}
				}
			});
		}

		if( props.regionD2!=undefined && props.regionD2!="" ){
			if( props.regionD2Value!="" ){
				r2Value = props.regionD2Value;
			}else{
				r2Value = props.regionValue;
			}
			$.each(props.regionD2,function(key,value){
				for( var i=0;i<value.length;i++ ){
					if( value[i].id==r2Value ){
						props.regionD1Value = key;
						props.regionD2Value = r2Value;
						break;
					}
				}
			});
		}

		if( props.regionD1!=undefined && props.regionD1!="" ){
			if( props.regionD1Value!="" ){
				r1Value = props.regionD1Value;
			}else{
				r1Value = props.regionValue;
			}
			$.each(props.regionD1,function(key,value){
				for( var i=0;i<value.length;i++ ){
					if( value[i].id==r1Value ){
						props.regionD1Value = r1Value;
						break;
					}
				}
			});
		}
	}
	
	//绑定一级联动
	jQuery("#"+props.regionD1Id).bind("change",function(){
		this_.rebuildRgionOption(props.regionD2Id,props.regionD2,props.regionD1Id);
		this_.rebuildRgionOption(props.regionD3Id,props.regionD3,props.regionD2Id);
		this_.rebuildRgionOption(props.regionD4Id,props.regionD4,props.regionD3Id);
	});

	//绑定二级联动
	jQuery("#"+props.regionD2Id).bind("change",function(){
		this_.rebuildRgionOption(props.regionD3Id,props.regionD3,props.regionD2Id);
		this_.rebuildRgionOption(props.regionD4Id,props.regionD4,props.regionD3Id);
	});

	//绑定三级联动
	jQuery("#"+props.regionD3Id).bind("change",function(){
		this_.rebuildRgionOption(props.regionD4Id,props.regionD4,props.regionD3Id);
	});
	
	this_.preRegionSelect(props.regionD1Id,props.regionD1,props.regionD1Value);//初始化一级地区数据
	this_.preRegionSelect(props.regionD2Id,props.regionD2,props.regionD2Value,props.regionD1Id);//初始化二级地区数据
	this_.preRegionSelect(props.regionD3Id,props.regionD3,props.regionD3Value,props.regionD2Id);//初始化三级地区数据
	this_.preRegionSelect(props.regionD4Id,props.regionD4,props.regionD4Value,props.regionD3Id);//初始化四级地区数据
	
	this.props=props;
	
	return this;
};

/**
 * 生成联动数据
 * @param regionId		当前select框ID
 * @param regionData	当前select框数据
 * @param parentId		上级select框ID
 */
RegionSelect.prototype.rebuildRgionOption = function( regionId,regionData,parentId ){
	var select = document.getElementById(regionId);
	if( select!=undefined ){
		select.options.length = 0;
		select.add(new Option("请选择",""));
		if( regionData!=undefined ){
			if( parentId==undefined || parentId=="" ){
				//无上级select
				for( var i=0;i<regionData.length;i++ ){
					select.add(new Option(regionData[i].name,regionData[i].id));
				}
			}else{
				//有上级select
				$.each(regionData,function(key,value){
					if( key==$("#"+parentId).val() ){
						for( var i=0;i<value.length;i++ ){
							select.add(new Option( value[i].name,value[i].id ));
						}
					}
				});
			}
		}
	}
};

/**
 * 数据初始化
 * @param regionId		当前select框ID
 * @param regionData	当前select框数据
 * @param regionValue	当前select框的值
 * @param parentId		上级select框ID
 */
RegionSelect.prototype.preRegionSelect = function( regionId,regionData,regionValue,parentId ){
	var this_ = this;
	this_.rebuildRgionOption( regionId,regionData,parentId );
	if( regionValue!=undefined && regionValue!="" ){
		if( document.getElementById(regionId)!=undefined ){
			for(var i=0;i<$("#"+regionId+" option").length;i++){
				if( $("#"+regionId+" option").eq(i).val()==regionValue ){
					$("#"+regionId+" option").eq(i).attr("selected",true);
					break;
				}
			}
		}
	}
	
};

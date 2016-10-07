UploadifyInit = function( config ){
	var this_ = this;
	var props = {
			basePath:"",
			uploadifyId:"uploadify",
			submitId:"uploadifySubmit"
	};
	
	for (var p in props) {
		props[p] = (typeof config[p] == 'undefined') ? props[p] : config[p];
	}
	
	var uploadifyObj = $("#"+props.uploadifyId).uploadify({
    	'buttonText'		:'click to upload',
    	'removeCompleted'	:false,
        'height'			: 30,
        'swf'				: props.basePath+'/static/js/upload/uploadify.swf',
        'uploader'			: props.basePath+'/fileupload',
        'width'				: 120,
        'auto'				: false,
        'multi'				: false,
        'queueSizeLimit'	:1,
        'fileSizeLimit'		:'2MB',
        'fileTypeExts'		:'*.gif;*.jpg;*.png;*.bmp;*.jepg', 
        'method'			:'post',
        'onSelectError'		: function(file, errorCode, errorMsg){
								switch(errorCode) {
									case -100:
										alert("上传的文件数量已经超出系统限制的"+$('#'+props.uploadifyId).uploadify('settings','queueSizeLimit')+"个文件！");
										break;
									case -110:
										alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#'+props.uploadifyId).uploadify('settings','fileSizeLimit')+"大小！");
										break;
									case -120:
										alert("文件 ["+file.name+"] 大小异常！");
										break;
									case -130:
										alert("文件 ["+file.name+"] 类型不正确！");
										break;
								}
							},//检测FLASH失败调用       
        'onFallback'		:function(){
								alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
							},
		'overrideEvents'	: ['onDialogClose','onCancel'],
		'onCancel'			: function(file){
								alert('The file ' + file.name + ' was cancelled.');
							}
    });
	
	$("#"+props.submitId).html("<a href=\"javascript:$('#"+props.uploadifyId+"').uploadify('upload')\">start upload</a>");
	
	this.props=props;
	
	return this;
};

$(document).ready(function(){
	$("div[id^='SWFUpload'] > div.cancel").live("click",function(){
		var id = $(this).parent().attr("id")
		var objId = id.substr(0,id.lastIndexOf('_'));
		var uploadifyId = $("#"+objId).parent().attr("id");
		
		$('#'+uploadifyId).uploadify('settings','buttonClass', 'hiddenbtn');
		
		$('#'+uploadifyId).uploadify('disable', false);
		var swfu = $('#'+uploadifyId).data('uploadify');
		for( var file in swfu.queueData.files ){
			if( swfu.queueData.files[file].id==$(this).parent().attr("id") ){
				swfu.queueData.files[file].name = "";
			}
		}
		var stats = swfu.getStats();
	    stats.successful_uploads--;
	    swfu.setStats(stats);
	    
		$('#'+uploadifyId).uploadify('cancel', $(this).parent().attr("id"));
	});
});

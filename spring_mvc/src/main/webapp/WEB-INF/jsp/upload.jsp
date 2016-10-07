<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html public "-//W3C//DTD HTML 4.01//EN' 'http://www.w3.org/TR/html4/strict.dtd">
<html>  
<head>  
<meta http-equiv="content-type" content="text/html; charset=utf-8"/>  
<title>JS模拟上传进度条</title>  
</head>  
<body>  
</body>  
<script type="text/javascript">  
var bind = function(obj,func){  
    return function(){
        func.apply(obj,arguments);  
    };  
};  
var uploadBAR=function(container){  
    container=(!document.getElementById(container))?this.createContainer(container):document.getElementById(container);  
    this.ostart = container.getElementsByTagName('input')[0];
    this.oinit = container.getElementsByTagName('input')[1];
    this.container=container;  
    this.nBar=container.id+'_uploadBAR';  
    this.total=95;  
};  
uploadBAR.prototype = {  
    addEventHandler:function(obj, type, func) {  
        if(!obj){return;}  
        if(obj.addEventListener){obj.addEventListener(type, func, false);}  
        else if(obj.attachEvent){obj.attachEvent("on" + type, func);}  
        else{obj["on" + type] = func;}  
    },  
    on:function(e){  
        this.addEventHandler(this.ostart,'click',bind(this,this.start));  
        this.addEventHandler(this.oinit,'click',bind(this,this.init));  
    },  
    init:function(e){  
        this.oinit.blur();  
        if(this.up!=undefined){  
            clearInterval(this.up);  
        }  
        this.x=this.y=0;  
        this.ostart.value='start';  
        this.ostart.disabled=false;  
        if(document.getElementById(this.nBar)){  
            var pBar=document.getElementById(this.nBar).parentNode;  
            pBar.removeChild(document.getElementById(this.nBar));  
            if(pBar.getElementsByTagName('div').length==0){document.body.removeChild(pBar);}  
        }  
    },  
    setTime:function(){  
            this.x++;  
    },  
    createContainer:function(oName){  
        var buildStart=document.createElement('input');  
            buildStart.value='start';  
            buildStart.type='button';  
        var buildInit=document.createElement('input');  
            buildInit.value='init';  
            buildInit.type='button';  
        var oDIV=document.createElement('div');  
            oDIV.id=oName;  
            oDIV.appendChild(buildStart);  
            oDIV.appendChild(buildInit);  
            document.body.appendChild(oDIV);  
            buildStart=buildInit=null;  
            return oDIV;  
    },  
    createBAR:function(){  
        if(document.getElementById(this.nBar)!=undefined){return;}  
        if(document.getElementById('bar_container')==undefined){  
            var Barcontainer=document.createElement('div');  
            Barcontainer.id="bar_container";  
            Barcontainer.style.width="200px";  
            Barcontainer.style.border="1px solid #999";  
            Barcontainer.style.backgroundColor="#ccc";  
            Barcontainer.style.overflowX="hidden";  
            Barcontainer.style.position=(document.all)?"absolute":"fixed";  
            var bHeight=(document.documentElement.clientHeight-18)/2+"px",  
            bWeight=(document.documentElement.clientWidth-parseInt(Barcontainer.style.width))/2+"px";  
            Barcontainer.style.top=bHeight;  
            Barcontainer.style.left=bWeight;  
            document.body.appendChild(Barcontainer);  
        }
        var newBar=document.createElement('div');  
            newBar.innerHTML='&nbsp;';  
            newBar.id=this.nBar;  
            newBar.style.border="5px solid #ccc";  
            document.getElementById('bar_container').appendChild(newBar);  
    },  
    setBAR:function(xx){  
        var BAR=document.getElementById(this.nBar);  
            BAR.style.backgroundColor=(xx==100)?"#00ff00":"#333";  
            BAR.style.width = xx+"%";  
            BAR.style.textAlign="center";  
            BAR.style.color = "#FFF";  
            BAR.style.fontWeight = "bold";  
            if(xx==this.total){  
                BAR.innerHTML = "complete";  
                this.ostart.value='start';  
                this.ostart.disabled=true;  
                setTimeout(bind(this,this.init),1000);
            }  
    },  
    go:function(xx){  
        var ostart=this.ostart,x=this.x,total=this.total,_x=bind(this,this.setTime),_bar=bind(this,this.setBAR);  
        this.up=setInterval(function(){  
                if(x<total){  
                    x++;  
                    _x();  
                }  
                else{  
                    var _up=bind(this,this.up);  
                    clearInterval(_up);  
                    this.value=='start';  
                    this.disabled=true;  
                }  
                _bar(x);  
        },10);  
    },  
    run:function(){  
        this.ostart.blur();  
        if(this.x==undefined||this.x==this.total){this.x=0;this.y=0;}  
        this.ostart.value=(this.ostart.value=='start')?"pause":"start";  
        this.y++;  
        clearInterval(this.up);  
        if(this.y>1){this.y=0;return;}  
        this.go(this.x);  
    },  
    start:function(){  
        var createBAR=bind(this,this.createBAR);  
        var run=bind(this,this.run);  
        createBAR();  
        run();  
    }  
};  
var bar1=new uploadBAR('upload');  
    bar1.on();  
var bar2=new uploadBAR('upload1');  
    bar2.on();  
</script>  
</html>
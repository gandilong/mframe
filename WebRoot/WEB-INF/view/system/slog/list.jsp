<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  
      <table id="grid"></table>
      
      <div id="tb" style="padding:5px;height:auto">
		<div>
		       用户：<sub><input type="text" id="operator" name="operator" style="height:12px;width:90px;font-size:12px" /></sub>
                          操作：<sub><input type="text" id="action" name="action" style="height:12px;width:180px;font-size:12px"/></sub>
			日期: <input class="easyui-datebox" id="startTime" name="startTime" data-options="formatter:dateFormatter,parser:dateParser" style="width:120px">
			到: <input class="easyui-datebox" id="endTime" name="endTime" data-options="formatter:dateFormatter,parser:dateParser" style="width:120px">
			<a href="javascript:void(0)" id="query" class="btn" iconCls="icon-search">查&nbsp;&nbsp;询</a>
			<a href="javascript:void(0)" id="clearLog" class="btn btn-danger" iconCls="icon-search">清空日志</a>
		</div>
	  </div>
      
<script type="text/javascript">
$(function(){
		
		    //初始化数据列表
		$('#grid').datagrid({
			    title:'日志列表',
			    iconCls:'icon-camera',
			    fit:true,
			    striped:true,
			    sortName:'id',
			    sortOrder:'desc',
			    collapsible:false,
			    singleSelect:true,
			    loadMsg:'数据加载中...',
			    remoteSort:true,
			    pageSize:30,
			    pageList:[15,30,50],
			    queryParams:{pageNow:1,pageSize:30},
			    idField:'id',
			    url:'system/slog/listData',
			    pagination:true,
			    rownumbers:true,
			    columns:[[
			              {field:'id',title:'编号',width:80,hidden:true},
			              {field:'operator',title:'用户',width:300,sortable:false},
			              {field:'action',title:'操作',width:480,sortable:false},
			              {field:'time',title:'时间',width:130,sortable:true}
			              ]],
			    toolbar:'#tb'
			});
			
			
			var p = $('#grid').datagrid('getPager');
		    $(p).pagination({
		          pageSize:30,
		          beforePageText: '第',
		          afterPageText: '页    共 {pages} 页',
		          displayMsg:'显示 {from} 到 {to} 共 {total} 条',
		          onSelectPage:function(pageNumber,pageSize){
				      $('#grid').datagrid("reload",{pageNow:pageNumber,pageSize:pageSize});
			      },
			      onBeforeRefresh:function(pageNumber,pageSize){
				      $('#grid').datagrid("reload",{pageNow:pageNumber,pageSize:pageSize});
			      },
			      onChangePageSize:function(pageSize){
				      $('#grid').datagrid("reload",{pageNow:1,pageSize:pageSize});
			      }
		    });
		    
		    
		    $('#query').click(function(){
		             query();
		    });
		    
		    $('#clearLog').click(function(){
		          clearLog();
		    });
			
});
		
		//查询方法
		function query(){
		    var params={};
		    if(''!=$('#startTime').datebox('getValue')){
		        params.startTime=$('#startTime').datebox('getValue');
		    }
		    if(''!=$('#endTime').datebox('getValue')){
		       params.endTime=$('#endTime').datebox('getValue');
		    }
		    
		    if(''!=$('#operator').val()){
		        params.operator=$('#operator').val();
		    }
		    
		    if(''!=$('#action').val()){
		        params.action=$('#action').val();
		    }
		    
		    $('#grid').datagrid('load',params);
		}
		
		function clearLog(){
		   layer.confirm('确定要清空所有记录吗？',function(){
		       $.ajax({
                  type: "POST",
                  url: "system/slog/clear",
                  success: function(msg){
                      //layer.alert('清除成功！', 1,'提示');
                      layer.msg('清除成功！');
                      query();
                  }
               });
		   });
		   
		}
						
      </script>
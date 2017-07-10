<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../head.jsp" %>
<link href="static/css/userDetail.css" type="text/css" rel="stylesheet">
<script src="static/js/userDetail.js" charset="utf8" type="text/javascript" ></script>
<!-- 
<link href="static/lib/dataTable/css/jquery.dataTables.min.css" charset="utf8" type="text/css" rel="stylesheet">
<script src="static/lib/dataTable/js/jquery.dataTables.min.js" charset="utf8" type="text/javascript" ></script> -->
<title>我的必易为</title>
</head>
<body>
<%@include file="../nav.jsp" %>
<section class="bigbox">
	<div class="user_nav_bar">
		
	</div><!-- user_nav_bar -->
	<div class="main">
		<div class="menu main_left">
			<div class="account">
				<h3><a href="user/id/${user.userId }#account">账号设置</a></h3>
				<ul>
					<li><a href="javascript:;" role="userInfo">个人信息</a></li>
	                <li><a href="javascript:;" role="accountSafe">账户安全</a></li>
	                <li><a href="javascript:;" role="accountAttach">账号绑定</a></li>
	                <li><a href="javascript:;" role="addresses">收货地址</a></li>
				</ul>
			</div>
			
			
			<div class="order_nav">
				<h3><a href="user/id/${user.userId }#order">订单管理</a></h3>
				<ul>
					<li><a href="javascript:;" role="nowOrder">我的订单</a></li>
					<li><a href="javascript:;" role="history">历史订单</a></li>
					<li><a href="javascript:;" role="invalid">无效订单</a></li>
					<li><a href="javascript:;" role="allOrder">全部订单</a></li>
				</ul>
			</div>
			
			<div class="borrow_nav">
				<h3><a href="user/id/${user.userId }#borrow">我的借阅</a></h3>
				<ul>
					<li><a href="javascript:;" role="nowBorrow">当下借阅</a></li>
					<li><a href="javascript:;" role="hisBorrow">历史借阅</a></li>
				</ul>
			</div>
			
			<div class="care_nav">
			<h3><a href="user/id/${user.userId }#care">我的关注</a></h3>
				<ul><li><a href="javascript:;" role="careObj">我的关注</a></li></ul>
			</div>
			
			
		</div><!-- "main_left" -->
		
		<div class="main_right">
		
			<div class="main_right_top">
				
			</div><!--main_right_top  -->
			
			<div class="main_right_down">
				<div class="slide main_right_down_left">
					<div class="account">
						<!-- 专用锚记法 -->
						<a id="account" class="target"><span class="placeholder"></span></a>
						<div class="title">账号设置</div>
						<div id="userInfo" class="switch">
							<h5>个人信息</h5>
							<div class="contentM10">
								 <div class="headpic">
								 	<form  id="headForm" method="post" enctype="multipart/form-data">
									  <div class="form-group  fileinput">
										  <label for="exampleInputFile">换一个头像吧</label>
										  <input id="headFile" name='headFile' type="file" accept="image/*" />
									  </div>
									  <input type="button" id="button"  class="btn btn-info" value="点击保存"/>
									  <p class="blue">仅支持jpg/png/gif/bmp格式文件,大小限制为：600kb.</p>
									  <!-- static/img/common/noHeadPic.png -->
									  <div class="preview" style="background-image:url(static/img/common/noHeadPic.png)">
									  <img src="file/display?find=userhead&name=${user.userId }" alt="没有头像"/></div>
									</form>
									
								</div>
								
								<div class="addbtn">
									<a id="addbtn" class="btn btn-link btn-sm" href="javascript:;">编辑和添加信息</a>
								</div>
								
								<form id="user_form" class="form-horizontal">
									<div id="addMore" >
									  <div class="form-group">
									    <label  class="col-sm-2 control-label">用户名</label>
									     <div class="col-sm-10">
									    	<input type="text" name="uname" class="form-control" value=${user.uname}>
									    </div>
									  </div>
									  
									  <div class="form-group">
									    <label  class="col-sm-2 control-label">真实姓名</label>
									     <div class="col-sm-10">
									    	<input type="text" name="rname" class="form-control" value="${user.rname}" placeholder="还没填写哦">
									    </div>
									  </div>
									  <div hidden id="sexhidden" role=${user.sex}></div>
									  <div class="form-group">
										<label class="col-sm-2 control-label">性别：</label>
									    	<div class="col-sm-10">
									     	男： <input type="radio" name="sex" value="1">
									     	女： <input type="radio" name="sex" value="0">
											</div>
									 </div>
									  
									  <div hidden id="birthHidden" role="${user.birthday}"></div>
									  <div class="form-group">
									    <label  class="col-sm-2 control-label">出生日期</label>
									     <div class="col-sm-10">
									    	<input id="birthday" type="date" name="birthdayStr" class="form-control"/>
									    </div>
									  </div>
									 
									 <div hidden id="cityHidden" role="${user.birthCity}">${user.birthCity}</div>
									 <div class="form-group">
									    <label  class="col-sm-2 control-label">出生地</label>
									     <div class="col-sm-10">
									    	<div class="info">
										     	<div id="area">
													<select id="s_province" name="s_province"></select>  
												    <select id="s_city" name="s_city" ></select>  
												    <select id="s_county" name="s_county"></select>
												   <!--  <script src="static/js/area.js" charset="utf8" type="text/javascript"></script>
												    <script type="text/javascript">_init_area();</script> -->
											    </div>
											    <div id="show"></div>
										    </div>
										    
									    </div>
									  </div>
									  
									  <div class="form-group">
									    <label  class="col-sm-2 control-label">出生地详细</label>
									     <div class="col-sm-10">
									    	<input type="text" name="birthCity" class="form-control" placeholder="还没填写哦"/>
									    </div>
									  </div>
									  
									</div><!-- addMore -->
									
									
									<div  id="save">
									 	<input type="button" class="btn btn-success" name="save" value="点击保存用户信息"/>
									 </div>
									 <span id="tip"></span>
									
								</form>
							</div>
							
						</div><!-- userInfo -->
						
						<div id="accountSafe" class="switch">
							<h5>账户安全</h5>
							<div class="contentM20">
								<div id="phone">
									<c:if test="${not empty user.phone}">
										<div class="rowbox">
											<label>验证的手机</label>
											<span class="upPhone">${user.phone}</span>
											<a id="changePhone" class="btn btn-info">去更换</a>
										</div>
										
										
									</c:if>
									<c:if test="${empty user.phone}">
										<div class="rowbox">
											<label>验证的手机</label>
											<span class="upPhone">还没有验证</span>
											<a id="checkPhone" class="btn btn-info">去验证</a>
										</div>
										
									</c:if>
								</div><!-- phone -->
								
								<div id="email">
									<c:if test="${not empty user.email}">
										<div class="rowbox">
											<label>更换邮箱</label>
											<span class="upEmail">${user.email}</span>
											<a id="changeEmail" class="btn btn-info">去更换</a>
										</div>
										
									</c:if>
									<c:if test="${empty user.email}">
										<div class="rowbox">
											<label>验证的邮箱</label>
											<span class="upEmail">还没有验证</span>
											<a id="checkEmail" class="btn btn-info">去验证</a>
										</div>
										
									</c:if>
								</div><!--email  -->
								
								<div id="pwd" class="rowbox">
									<label>修改密码</label>
									<form id="pwdForm" class="form-inline">
										<div class="form-group">
											<span class="label">原始密码</span>
											<input id="oldPwd" name="oldPwd" class="form-control" type="password"/>
										</div>
										<div class="tip">
										</div>
										<div>&nbsp;</div>
										<div class="form-group">
											<span class="label">新密码</span>
											<input id="newPwd" name="newPwd" class="form-control" type="password" />
										</div>
										<div class="tip">
										</div>
										<div>&nbsp;</div>
										<div class="form-group">
											<span class="label">确认新密码</span>
											<input id="newPwdCP" name="newPwdCP" class="form-control" type="password" />
										</div>
										<div class="tip">
										</div>
										<div>&nbsp;</div>
										
										<a id="askForMFPwd" href="javascript:;" class="btn btn-info">递交申请</a>
									</form>
									
									<div id="phoneExt">
										<button class="btn btn-warning">确认修改</button>
									</div>
									
								</div><!-- pwd -->
								
						 	</div><!-- contentM20 -->
						</div><!--  accountSafe-->
						
						<div id="accountAttach" class="switch">
							<h5>账号绑定</h5>
						</div>
						
						<div id="addresses" class="switch">
							<h5>收货地址</h5>
							<div class="table_form contentM20">
								<div class="table_head_box">
									<div  class="table_find form-inline">
										<div class="form-group">
											<input class="form-control" size="10" type="text" size="40" name="keywords" id="sbtn"/>
										</div>
										<div class="form-group">
											<input type="button" class="btn btn-info" value="搜索"/>
										</div>
									</div>
									<button data-toggle="modal" data-target="#addrModal" class="btn btn-info">添加</button>
								</div>
								<table id="address" class="table table-border" width="100%" >
									<thead>
										<tr>
											<th>收货人</th>
											<th>联系方式</th>
											<th>收货地址信息</th>
											<th>默认地址</th>
											<th colspan="2">操作</th>
										</tr>
									<thead>
									<tbody>
										<tr v-for="(addr,index) in addressData" v-if="index>=begin&&index<end">
											<div hidden>{{index}}</div>
											<div class="addrId" hidden>{{addr.addrId}}</div>
											<td>{{addr.userName}}</td>
											<td>{{addr.tel}}</td>
											<td>{{addr.info}}</td>
											<td v-if="addr.isdefault==1">默认地址</td>
											<td v-if="addr.isdefault==0">
												<a href="javascript:;">设置成默认地址</a>
											</td>
											<td><a @click="edit(addr.addrId)" class='edit smicon' href="javascript:;">
											<img src="static/img/common/edit.png" alt="编辑"></a></td>
											<td><a @click="del(addr.addrId)" class='delete smicon' href="javascript:;">
											<img src="static/img/common/delete.png" alt="删除"></a></td>
										</tr>
									</tbody>
									<tfoot>
										<tr>
										<td>
										<button @click="prePage()">上一页</button>
										&nbsp;{{pageNo}}&nbsp;
										<button @click="nextPage()">下一页</button></td>
										<td>总计页数：{{allPage}}</td>
										<td>
										<input type="text" size="3" name="pageNo" value="1" />
										<button>跳</button></td></tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div><!-- account -->
					
					
					
					
					<!--订单    订单    订单    订单    订单    订单    订单    订单    订单    订单    订单    订单    -->
					
					
					
					<div class="order">
						<a id="order" class="target"><span class="placeholder"></span></a>
						<div class="title">订单管理</div>
						
						<div id="nowOrder" class="switch">
							<h5>当前订单</h5>
							<div id="currentOrder" class="table_form contentM20">
								<div id="circl-item" v-for="(settlement,index) in settlementList">
									<div class="setHead">
										<div class="time">
											订单生成时间：{{settlement.order.generateDatetime}}
										</div>
										<div class="orderId" >
											订单号：{{settlement.order.orderId}}
										</div>
										<div class="action">
											<button @click="jiesuan(settlement.order.orderId)" class="btn btn-info" href="javascript:;">结算</button>
										</div>
									</div>
									
									<div  v-for="cartForm in settlement.cartForms">
										<div class="cartForm">
											<div class="column goods">
												<img :src="url(cartForm.folder)" :alt="cartForm.folder" />
											</div>
											<div class="info">
												<div class="aWordIntro" >{{cartForm.orderItem.goodsName}}</div>
												<div class="column quantity">{{cartForm.orderItem.count}}</div>
											</div>
										</div>
									</div>	 
								</div>
							</div>
						</div>
						
						<div id="history" class="switch">
							<h5>历史订单</h5>
							
						</div>
						<div id="invalid" class="switch">
							<h5>无效订单</h5>
						</div>
						<div id="allOrder" class="switch">
							<h5>全部订单</h5>
						</div>
					</div><!-- order -->
					
					
					
					<!--借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   借阅   -->
					
					
					<div class="borrow">
						<a id="borrow" class="target"><span class="placeholder"></span></a>
						<div class="title">我的借阅</div>
						<div id="nowBorrow" class="switch">
							<h5>当前借阅</h5>
						</div>
						<div id="hisBorrow" class="switch">
							<h5>借阅历史</h5>
						</div>
					</div><!-- borrow -->
					
					
					
					
					
					
					<!--关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  关注  -->
					
					<div class="care">
						<a id="care" class="target"><span class="placeholder"></span></a>
						<div class="title">我的关注</div>
						<div id="careObj" class="switch">
							<h5>我关注的物品</h5>
						</div>
					</div><!-- care -->
					
				</div><!-- main_right_down_left -->
				
				<div class="main_right_down_rigth">
					
				</div><!--  main_right_down_rigth-->
			</div><!-- main_right_down -->
		</div><!-- main_right -->
	</div><!--main  -->
</section>
</body>
<%-- <%@include file="../footer.jsp" %> --%>


<!-- Modal -->
<div class="modal fade" id="addrModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">新增收货地址</h4>
      </div>
      <div class="modal-body">
      	<form id="newAddr" class="form-horizontal">
      	
		  <div class="form-group">
		    <label for="text" class="col-sm-2 control-label">收货人</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="text">
		    </div>
		  </div>
		  
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">所在地区</label>
		    <div class="col-sm-10">
			    <div class="info">
			     	<div id="area2">
						<select id="s_province" name="s_province"></select>  
					    <select id="s_city" name="s_city" ></select>  
					    <select id="s_county" name="s_county"></select>
				    </div>
				    <div id="show2"></div>
			    </div>
			</div><!-- col-sm-10 -->
		  </div><!-- form-group -->
		  
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">详细地址</label>
		    <div class="col-sm-10">
		      <input type="text" class="form-control" id="inputPassword3" >
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="" class="col-sm-2 control-label">手机号码</label>
		    <div class="col-sm-10">
		      <input type="phone" class="form-control" id="inputPassword3" >
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button @click="addAddr()" type="button" data-dismiss="modal" class="btn btn-primary">保存</button>
      </div>
    </div>
  </div>
</div><!--model  -->

////////////////////////////地址
<script src="static/js/area.js" charset="utf8" type="text/javascript"></script>
<script type="text/javascript">_init_area();</script>

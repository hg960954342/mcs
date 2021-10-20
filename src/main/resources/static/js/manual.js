/*
 * name: AGV信息模块数据配置
 * date: 2019-07-02
 * author: fengq
 */

layui.use(['jquery','form','layer'],function(){
  var $ = layui.$,
    form = layui.form,
    layer = layui.layer;

  //函数：前后端数据交互接口
  function dataApi(postData,func) {
    $.ajax({
      type: 'post',
      url: 'return.json',
      async: true,
      data: postData,
      dataType: 'json',
      contentType: 'application/json',
      success: func
    })
  }

  //函数：动态渲染小车编号下拉框
  function getAgvNum(data){
    $.each(data.infoPost.info, function (index, item) {
      //给小车编号下拉框动态添加数据
      $('#number').append(new Option(item.aid, item.aid));
    });
    form.render('select');
  }

  //函数：获取小车当前坐标、小车状态、AGV状态信息、移动-xy轴值、充电-xy轴值、搬运货架-xy轴值、盘点货架编号-xy轴值
  function getAgvInfo(data){
    if(data.infoPost.msg == '获取成功！'){
      var info = data.infoPost.info;
      $('.iptCoord').val(info[1].currCoord); //当前坐标
      $('input[name="auto"]').removeAttr("disabled"); //小车状态开关启用
      if(info[1].auto == true){ //小车状态
        $('.agvState').prop('checked', true);
      }else{
        $('.agvState').prop('checked', false);
      }
      if(info[1].isLeisure == true){ //工作状态
        $('.isLeisure').text('是');
      }else{
        $('.isLeisure').text('否');
      }
      if(info[1].isUp == true){ //是否托举
        $('.isUp').text('是');
      }else{
        $('.isUp').text('否');
      }
      $('.dumpEnergy').text(info[1].dumpEnergy);//剩余电量
      $('.shelfId').text(info[1].shelfId);//货架编号
      $('.currCoord').text(info[1].currCoord);//当前坐标
      $('.agvDirection').text(info[1].agvDirection);//朝向
      $('.iptMovey').val(info[1].y);
      $('.iptMovex').val(info[1].x);
      $('.iptChargey').val(info[1].y);
      $('.iptChargex').val(info[1].x);
      $('.caScy').val(info[1].y);
      $('.caScx').val(info[1].x);
      $('.ckSry').val(info[1].y);
      $('.ckSrx').val(info[1].x);
      form.render();
    }else {
      layer.msg('获取数据失败！', {icon: 5});
    }
  }

  //函数：功能按钮操作时，向后台请求数据时的回调函数
  function success(data){
    var msg = data.btnPost;
    if(msg.success == true){
      layer.msg(msg.message, {icon: 6});
    }else{
      layer.msg('获取后台数据失败！', {icon: 5})
    }
    return data;
  }

  //页面加载完毕后渲染小车编号下拉框
  $(function(){
    var agvId = '{"agvId":""}';
    dataApi(agvId, getAgvNum);
  });

  //获取AGV小车编号，并加载当前坐标、小车状态、AGV状态信息
  form.on('select(number)',function(data){
    var elem = data.elem;
    agvId = elem[elem.selectedIndex].text;
    var postData = agvId;
    dataApi(postData, getAgvInfo);
  });

  //小车状态switch按钮监听事件
  form.on('switch(isAuto)', function (data) {
    var agvState = data.elem.checked,
      isAutoData;
    if (agvState == true) {
      isAutoData = '{"agvId":"' + agvId + '","type":"13","isOk":"true"}';
    } else {
      isAutoData = '{"agvId":"' + agvId + '","type":"13","isOk":"false"}';
    }
    dataApi(isAutoData, success);
  });

  //函数：规范y轴坐标值
  function normCoordY(y){
    if(y.length == 1){
      y = '000' + y;
    }else if(y.length == 2){
      y = '00' + y;
    }
    return y;
  }

  //函数：规范x轴坐标值
  function normCoordX(x){
    if(x.length == 1){
      x = '000' + x;
    }else if(x.length == 2){
      x = '00' + x;
    }
    return x;
  }

  //对象：声明按钮触发事件
  var active = {
    //AGV信息 - 清空任务
    clear: function() {
      //拼接【清空任务】按钮所传给后台的字符串  --  {"agvId":"","type":"1","isOk":"true"}
      try{
        var clearData = '{"agvId":"' + agvId + '","type":"1","isOk":"true"}';
        dataApi(clearData, success);
      }
      catch(err){
        layer.msg('暂无任务，无需清除！', {icon: 5});
      }

    },

    //交通管制 - 停止
    agvStop: function(){
      try{
        var stopData = '{"agvId":"' + agvId + '","type":"2","isOk":"false"}';
        dataApi(stopData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //交通管制 - 前进
    agvMove: function(){
      try{
        var moveData = '{"agvId":"' + agvId + '","type":"2","isOk":"true"}';
        dataApi(moveData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //原地升降 - 顶升
    agvUp: function() {
      try{
        var upData = '{"agvId":"' + agvId + '","type":"3","isOk":"false"}';
        dataApi(upData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //原地升降 - 下降
    agvDown: function(){
      try{
        var downData = '{"agvId":"' + agvId + '","type":"3","isOk":"true"}';
        dataApi(downData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //校准 - 坐标
    sureCoord: function(){
      try{
        var coordData = '{"agvId":"' + agvId + '","type":"4","isOk":"false"}';
        dataApi(coordData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //校准 - 货架
    sureShelf: function(){
      try{
        var shelfData = '{"agvId":"' + agvId + '","type":"4","isOk":"true"}';
        dataApi(shelfData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 车体左转90
    agvLeft: function(){
      try{
        var agvLeftData = '{"agvId":"' + agvId + '","type":"5","Angle":"1"}';
        dataApi(agvLeftData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 车体右转90
    agvRight: function(){
      try{
        var agvRightData = '{"agvId":"' + agvId + '","type":"5","Angle":"2"}';
        dataApi(agvRightData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 车体旋转180
    agvRotate: function(){
      try{
        var agvRotateData = '{"agvId":"' + agvId + '","type":"5","Angle":"3"}';
        dataApi(agvRotateData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 货架左转90
    shelfLeft: function(){
      try{
        var shelfLeftData = '{"agvId":"' + agvId + '","type":"6","Angle":"1"}';
        dataApi(shelfLeftData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 货架右转90
    shelfRight: function(){
      try{
        var shelfRightData = '{"agvId":"' + agvId + '","type":"6","Angle":"2"}';
        dataApi(shelfRightData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //小车方向 - 货架旋转180
    shelfRotate: function(){
      try{
        var shelfRotateData = '{"agvId":"' + agvId + '","type":"6","Angle":"3"}';
        dataApi(shelfRotateData, success);
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //移动 - 开始（移动到目标）/（移动顶起）/（移动放下）
    moveAgv: function(){
      try{
        var y = $('.iptMovey').val(),
          x = $('.iptMovex').val();
        y = normCoordY(y);
        x = normCoordX(x);
        if(y == "" ){
          layer.msg("请输入 y 坐标值！")
        }else if(x == ""){
          layer.msg("请输入 x 坐标值！")
        }else {
          var target = y + x;
          var mode =$('input[name = "mode"]:checked').val();
          var btnPoData = '{"agvId":"' + agvId + '","type":"'+ mode +'","Target":"' + target + '"}';
          dataApi(btnPoData, success);
        }

      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //充电 - 立即充电
    startCharge: function(){
      try{
        var y = $('.iptChargey').val(),
          x = $('.iptChargex').val();
        y = normCoordY(y);
        x = normCoordX(x);
        if(y == "" ){
          layer.msg("请输入 y 坐标值！")
        }else if(x == ""){
          layer.msg("请输入 x 坐标值！")
        }else {
          var target = y + x;
          var angle =$('input[name = "chargeAngle"]:checked').val();
          var startCgData = '{"agvId":"' + agvId + '","type":"10","isOk":"true","Target":"' + target + '","Angle":"' + angle + '","Distance":"200"}';
          dataApi(startCgData, success);
        }
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //充电 - 停止充电
    stopCharge: function(){
      try{
        var y = $('.iptChargey').val(),
          x = $('.iptChargex').val();
        y = normCoordY(y);
        x = normCoordX(x);
        if(y == "" ){
          layer.msg("请输入 y 坐标值！")
        }else if(x == ""){
          layer.msg("请输入 x 坐标值！")
        }else {
          var target = y + x;
          var angle =$('input[name = "chargeAngle"]:checked').val();
          var stopCgData = '{"agvId":"' + agvId + '","type":"10","isOk":"false","Target":"' + target + '","Angle":"' + angle + '","Distance":"200"}';
          dataApi(stopCgData, success);
        }
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //搬运(货架) - 开始
    moveShelf: function(){
      try{
        var sourcey = $('.caScy').val(),
          sourcex = $('.caScx').val(),
          targety = $('.caTgy').val(),
          targetx = $('.caTgx').val();
        sourcey = normCoordY(sourcey);
        targety = normCoordY(targety);
        sourcex = normCoordX(sourcex);
        targetx = normCoordX(targetx);
        if(sourcey == "" ){
          layer.msg("请输入源点 y 坐标值！")
        }else if(targety == ""){
          layer.msg("请输入目标点 y 坐标值！")
        }else if(sourcex == ""){
          layer.msg("请输入源点 x 坐标值！")
        }else if(targetx == ""){
          layer.msg("请输入目标点 x 坐标值！")
        }else {
          var target = targety + targetx,
            source = sourcey + sourcex;
          var angle =$('input[name = "moveAngle"]:checked').val();
          var stopCgData = '{"agvId":"' + agvId + '","type":"11","Source":"'+ source +'","Target":"' + target + '","Angle":"' + angle + '"}';
          dataApi(stopCgData, success);
        }
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    },

    //盘点货架编号 - 开始
    checkShelf: function(){
      try{
        var sourcey = $('.ckSry').val(),
          sourcex = $('.ckSrx').val(),
          targety = $('.ckTgy').val(),
          targetx = $('.ckTgx').val();
        sourcey = normCoordY(sourcey);
        targety = normCoordY(targety);
        sourcex = normCoordX(sourcex);
        targetx = normCoordX(targetx);
        if(sourcey == "" ){
          layer.msg("请输入源点 y 坐标值！")
        }else if(targety == ""){
          layer.msg("请输入目标点 y 坐标值！")
        }else if(sourcex == ""){
          layer.msg("请输入源点 x 坐标值！")
        }else if(targetx == ""){
          layer.msg("请输入目标点 x 坐标值！")
        }else {
          var target = targety + targetx,
            source = sourcey + sourcex;
          var stopCgData = '{"agvId":"' + agvId + '","type":"12","Source":"'+ source +'","Target":"' + target + '"}';
          dataApi(stopCgData, success);
        }
      }
      catch(err){
        layer.msg('请选择需要动作的小车，再执行此动作！', {icon: 5});
      }
    }
  };

  //功能按钮绑定单击事件
  $('button').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : null;
  });

  //搬运(货架)的交换按钮事件  [待优化]
  $('.layui-extend-exchange').on('click',function(){
    var sourcey = $('input[name = "sourcey"]') ,
      sourcex = $('input[name = "sourcex"]'),
      targety = $('input[name = "targety"]'),
      targetx = $('input[name = "targetx"]');

    var srValy = sourcey.val(),
      srValx = sourcex.val(),
      tgValy = targety.val(),
      tgValx = targetx.val();

    sourcey.val(tgValy);
    sourcex.val(tgValx);
    targety.val(srValy);
    targetx.val(srValx);
  });

  //移动路径  [待开发]
});
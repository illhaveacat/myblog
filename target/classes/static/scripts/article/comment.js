function subcomment(){
   var contentId= $('#content-id').val();
   var comment= $('#comment-content').val();
   var nickname=$('#nickname').val();
   var email=$('#email').val();
     $.ajax({
          type:'post',
          cache:false,
          dataType:"json",
          url:basepath+'/comment/save',
          data:{
          "contentId":contentId,
          "comment":comment,
          "nickname":nickname,
          "email":email
          },
          success:function (data) {
            alert(data.success)
              if(data.success){
                location.href=basepath+"/homepage/article/"+contentId;
              }

          }
       });

}
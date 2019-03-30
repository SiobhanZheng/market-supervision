<template>
  <div class="fail">
    <div class="content-bg">
      <div class="conbody">
        <img src="../assets/img/500.png" height="202" width="400"/>
        <p>抱歉！出错啦，请稍后加载！</p>
        <p> <Button type="primary" class="emBtn" @click="returnIndex">返回首页</Button></p>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data() {
      return{}
    },
    methods:{
      returnIndex(){
        this.$router.push({
          path:'/index',
        });
      }
    }
  }
</script>
<style lang="less" type="text/less">
  .content-bg {
    background-color: #fff;
    float: left;
    width: 100%;
    .conbody{
      width: 1200px;
      margin: 50px auto 100px;
      text-align: center;
      img{

      }
      p{
        font-size: 18px;
        color: #4A4A4A;
        letter-spacing: 0;
        margin: 20px 0 40px;
       button{
         background-color: #fff;
         color: #004A98;
         border: 1px solid #004A98;
         width: 112px;
         height: 36px;
       }
      }
    }
  }
</style>

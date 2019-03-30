<template>
  <div class="contain">
    <div class="login">
      <div class="label">Login Form</div>
      <div class="error" v-html="showText" v-show="ifShow">{{showText}}</div>
      <Form :model="formLogin" ref="formLogin">
        <FormItem prop="userCode">
          <input v-model="formLogin.userCode" placeholder="会员名" type="text" v-on:keyup.13="login">
        </FormItem>
        <FormItem prop="userPw">
          <input v-model="formLogin.userPw" type="password" placeholder="密码" v-on:keyup.13="login">
        </FormItem>
        <FormItem prop="captcha">
          <input class="pcode" v-model="formLogin.captcha" placeholder="输入验证码" type="text" v-on:keyup.13="login">
          <img id="captcha" class="captcha" @click.prevent.stop="editCaptcha" :src="captcha">
        </FormItem>
        <FormItem>
          <Button type="primary" @click="login" v-on:keyup.13="login">登录</Button>
        </FormItem>
      </Form>
    </div>
  </div>
</template>
<script>
  export default {
    data () {
      return {
        formLogin: {
          userCode: '',
          userPw: '',
          captcha: '',
        },
        showText:'请输入正确的用户名或密码！',
        ifShow:false,
        captcha: '/supervision/getCaptcha?d=' + Math.random()
      }
    },
    methods: {
      login () {
        let mySelf = this.formLogin;
        var userCode = mySelf.userCode;
        var userPw = mySelf.userPw;
        if (userCode == "" || userPw == ""){
          this.ifShow=true;
          return;
        }
        if(mySelf.captcha == ""){
          this.ifShow=true
          this.showText='请输入验证码！';
          return;
        }
        this.$http.post("/supervision/loginCheck", this.formLogin).then(
          function (res) {
            if (res.status === 200) {
              if(res.body.code == 1) {
                this.ifShow=false;
                this.$Message.success('登录成功');
                this.$router.push({
                  path: "/index"
                })
              }else{
                this.showText = res.body.message;
                this.ifShow=true;
              }
            } else {
              this.$Message.error('登录失败')
            }
          }
        )
      },
      //更新验证码
      editCaptcha () {
        let captcha = '/supervision/getCaptcha?d=' + Math.random()
        this.captcha = captcha
      },
      //勾起选中记住账户信息
      doRememberPassword (event){
        let mySelf = this.formLogin
        let rememberStatus = mySelf.remember;
        // event.preventDefault();
        mySelf.remember = !rememberStatus;
        //如果去掉勾选，则删掉cookie
        Cookie.delCookie('accountInfo')
      },
      //加载账号信息
      loadAccountInfo (){
        let mySelf = this.formLogin
        let accountInfo = Cookie.getCookie('accountInfo');
        //如果cookie里没有账号信息
        if(Boolean(accountInfo) == false){
          //console.log('cookie中没有检测到账号信息！');
          return false;
        }
        else{
          //如果cookie里有账号信息
          //console.log('cookie中检测到账号信息！现在开始预填写！');
          let userCode = "";
          let userPw = "";
          let index = accountInfo.indexOf("&");
          userCode = accountInfo.substring(0,index);
          userPw = accountInfo.substring(index+1);
          mySelf.userCode = userCode;
          mySelf.userAesPw = userPw;
          mySelf.remember = true;
        }
      }
    }

  }
</script>
<style type="text/css">
  .contain{
    width:100%;
    height:100%;
    background-image: url(../assets/img/1.jpg);
  }
  .login{
    width: 400px;
    height: 370px;
    position: absolute;
    left:50%;
    top:50%;
    margin-top: -185px;
    margin-left: -200px;
    background-color: #fff;
    text-align: center;
  }
  .login .label{
    font-size: 16px;
    font-weight: 300;
    text-align: center;
    padding: 30px 0;
  }
  .login .error{
    position: static;
    width: 280px;
    text-align: left;
    color: red;
    margin: -10px auto 10px;
  }
  .login input{
    width: 280px;
    height: 43px;
    font-size: 13px;
    border: 1px solid #CBCBCB;
    padding-left: 13px;
    border-radius: 0 !important;
  }
  .login input.pcode{
    width: 167px;
    margin-left: -113px;
  }
  .captcha{
    background-color: transparent;
    position: absolute;
    z-index: 11;
    height: 43px;
    border-radius: 0;
    width: 113px;
    cursor: pointer;
  }
  .login button{
    width: 284px;
    height: 43px;
    color: #fff;
    margin-top: 10px;
    background-color: #004A98;
    border: 1px solid #004A98;
  }
</style>

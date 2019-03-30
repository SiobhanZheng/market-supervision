<template>
  <div class="netProductConfig">
    <!--<NavHead></NavHead>-->
    <div style="height: 200px;">
      <img style="width: 100%;height: 190px;" src="../assets/img/title.jpg" />
    </div>
    <div class="content-bg">
      <div class="con">
       <!-- <LeftMenu></LeftMenu>-->
        <div class="search_key">
          <input type="text" class = "keyword" placeholder="请输入关键字" v-model="searchName">
          <Button type="primary" class = "keyword_btn" @click = "findCompany">搜索</Button>
          <Button type="primary" class="emBtn" @click="uploadExcel">
            <img src="../assets/img/icon-upload.png">
            上传表格</Button>
          <input @change="onFileChange($event)" style="visibility: hidden;" id="company_excel" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" type="file" >
          <input type="submit" id="insurance_ok" style="visibility: hidden;" value="确认上传">
          <div class="top-right">欢迎您， {{userCode}}   <a href="javascript:;" @click.prevent.stop="toLogout" class="logout"> 退出</a></div>
        </div>
        <div class="con-right">
          <div class="company">
            <div class="com-tal">
              <table>
                <tr class="title">
                  <td width="40%">企业名称</td>
                  <td width="30%">企业网址</td>
                  <td width="15%">企业状态</td>
                </tr>
                <tr :class="{'isClick':pointIndex === index}" v-for="(item,index) in allPointList" style="cursor: pointer;">
                  <td><a :href="'http://' + item.companyUrl" target="_blank">{{item.companyName}}</a></td>
                  <td>{{item.companyUrl}}</td>
                  <td>{{item.status}} </td>
                </tr>
              </table>
            </div>
            <Page :total="pointRecordDTO.totalCount" v-if="pointRecordDTO.totalCount >0" @on-change="pointCurrentIndex" :current="pointRecordDTO.page" :page-size="pointRecordDTO.size" show-total></Page>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        allPointList:[],
        pointIndex:-1,
        // 模糊查询
        searchName: '',
        userCode: '',
        // 公司分页的dto
        pointRecordDTO:{
          page:1,
          size:10,
          totalCount:0
        }
      }
    },
    created(){
//      this.userCode = this.$route.query.userCode;
      this.init();
      window.scrollTo(0, 0);
    },
    methods: {
      init(){
          if (this.searchName.trim() != ''){
            this.pointRecordDTO.searchName = this.searchName;
          }
        this.$http.post("/supervision/getAllCompany",this.pointRecordDTO).then((res) => {
          //成功的请求
          let response = res.body;
            if (res.status == 200) {
            if(response.code == '1'){
              this.allPointList = response.body.content;
              this.pointRecordDTO.totalCount = response.body.totalElements;
              if (this.pointRecordDTO.hasOwnProperty("searchName")) {
                this.pointRecordDTO.searchName = '';
              }
              this.userCode = response.message;
            }
          }
        },(error) => {
          //请求失败
          console.log('error.body:'+error);
        });
      },
      findCompany(){
/*        if(this.searchName.trim() == ''){
         this.$Message.error('查询项不能为空！');
         return;
         }*/
        this.init();
      },
      toLogout(){
        this.$http.post("/supervision/loginOut").then((res) => {
          let response = res.body;
          if (res.status === 200) {
            if (response.code == 1) {
              this.userCode == '';
              this.$Message.success('退出成功');
              this.$router.push({
                path: "/login"
              });
            }
          }
        });
      },
      //网点分页
      pointCurrentIndex(currentPage){
        this.pointRecordDTO.page = currentPage;
        this.init();
        //点击下一页清空之前的数据
        this.pointIndex = -1;
      },
      // 上传excel
      uploadExcel(){
        document.getElementById("company_excel").click();
      },
      // 上传excel
      onFileChange(e){
        var files = e.target.files || e.dataTransfer.files;
        if (!files.length)
          return;
        var url = "/supervision/uploadCompanyExcel";
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
        let formData = new FormData();
        formData.append('file', files[0]);
        this.$http.post(url,formData, config).then((res) => {
          //成功的请求
          let response = res.body;
          if(res.status == 200){
            if(response.code == '1'){
              this.init();
            }else if(response.code == '0'){
                alert(response.message);
            }else{
              this.$Message.error(response.message);
            }
          }
          //清空上传文件
          e.target.value='';
        },(error) => {
          console.log('error.body:'+error);
        });
      }
    }
  }
</script>
<style lang="less" type="text/less">
  .netProductConfig {
    background-color: #F5F5F5;
    .cursor {
      cursor: pointer;
    }
    .content-bg {
      background-color: #F5F5F5;
      height: auto;
      overflow: hidden;
      width: 100%;
    }
    .con {
      width: 1000px;
      margin: 15px auto 0;
      display: block;
      overflow: hidden;
      .search_key{
        width: 960px;
        height: 40px;
        .emBtn{
          height: 40px;
          background: #fff;
          color:#004A98;
          margin-left:10px;
        }
        .keyword{
          height: 40px;
          float: left;
          border: 1px solid #ccc;
          border-radius: 3px;
        }
        .keyword_btn{
          float: left;
          width: 100px;
          height: 40px;
          border-width: 0px;
          border-radius: 3px;
          background: #004A98;
          cursor: pointer;
          outline: none;
          color: #FFFFFF;
        }
        .top-right{
          float: right;
          width: 200px;
          height: 40px;
          line-height:40px;
          margin-left: 10px;
          .logout{
            padding-left: 30px;
          }
        }
      }
      .con-right {
        float: left;
        .company {
          height: auto;
          width: 960px;
          background: #FFFFFF;
          box-shadow: 0 1px 0 0 #ECECEC;
          margin-bottom: 20px;
          overflow: hidden;
          .ivu-page {
            text-align: center;
            float: right;
            margin: 0 15px 20px 0;
          }
          .com-tal {
            height: auto;
            table {
              width: 100%;
              text-align: left;
              margin: 0 auto;
              tr {
                height: 50px;
                border-bottom: 1px solid #EBEBEB;
                td{
                  padding-left: 10px;
                }
              }
              tr:hover{
                background:#eaf4fe;
              }
              tr.isClick{
                background:#D2DEC1;
                border-bottom: 1px solid #F3E1E1;
              }
              tr.title{
                background: #004A98;
                font-size: 12px;
                color: #FFFFFF;
              }
              tr.title:hover{
                background: #004A98;
              }
            }
          }
        }
        .record{
          height: auto;
          width: 960px;
          background: #FFFFFF;
          box-shadow: 0 1px 0 0 #ECECEC;
          margin-bottom: 20px;
          overflow: hidden;
          .ivu-page {
            text-align: center;
            float: right;
            padding-top: 0px;
            margin: 0 15px 20px 0;
          }
          h4{
            text-align: center;
            line-height: 100px;
            color: #FF6800;
          }
          .re-tal {
            height: auto;
            padding: 20px 0 20px 0;
            table {
              width: 96%;
              text-align: left;
              margin: 0 auto;
              border: 1px solid #C0C0C0;
              tr {
                height: 50px;
                border-bottom: 1px solid #EBEBEB;
                td.fontStyle{
                  text-align: center;
                  color: #F70623;
                }
              }
              td {
                padding-left: 10px;
                span {
                  color: #C0C0C0;
                }
                span.operate{
                  cursor: pointer;
                  color: #004A98;
                }
                span.add {
                  cursor: pointer;
                  color: #004A98;
                  border: 1px solid #004A98;
                  margin-left: 50px;
                  padding: 6px;
                }
              }
              .title{
                background: #D8D8D8;
                font-size: 12px;
                color: #585858;
              }
            }
          }
        }
      }
    }
  }
</style>

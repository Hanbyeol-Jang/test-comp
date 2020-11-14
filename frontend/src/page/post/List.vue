<template>
  <div>
    <div id="mainimg">

    </div>
    <div class="container">
      <div id="maintext" class="row row-cols-2">
        <div id="maintable" class="main-table mt-5 col-12 col-md-12">

          <div>
            <b-card no-body>
              <b-tabs card>
                <b-tab no-body title="경매 목록">
                  <table width="100%" id="list">
                    <colgroup>
                    <col width=20%>
                    <col width=20%>
                    <col width=20%>
                    <col width=10%>
                    <col width=10%>
                    <col width=20%>
                    <col width=10%>
                    </colgroup>
                    <thead>
                        <tr>
                            <th col>회식명</th>
                            <th>위치</th>
                            <th>날짜</th>
                            <th>인원수</th>
                            <th>1인당 가격</th>
                            <th>메뉴</th>
                            <th>입찰</th>
                        </tr>
                    </thead>
                    <tbody id="actionlist">
                        <tr v-for="(dining,i) in DiningList" v-bind:key="i" >
                          
                            <td >{{dining.dnName}}</td>
                            <td >{{dining.dnLocation}}</td>
                            <td >{{dining.dnDate}}</td>
                            <td>{{dining.dnPeople}}</td>
                            <td >{{dining.dnPrice}}</td>
                            <td>{{dining.dnMenu}}</td>
                            <td><b-button
                          variant="link"
                          @click="sendDiningID(dining.id)"
                          v-b-modal.modal-1
                          size="sm"
                          class="border-0"
                        >입찰</b-button></td>
                        </tr>
                    </tbody>
                  </table>
                </b-tab>
                
                <b-tab no-body title="입찰 목록">
                  <table width="100%"  id="list">
                    <colgroup>
                    <col width=20%>
                    <col width=20%>
                    <col width=20%>
                    <col width=10%>
                    <col width=10%>
                    <col width=20%>
                    <col width=10%>
                    </colgroup>
                    <thead>
                        <tr>
                            <th>회식명</th>
                            <th>위치</th>
                            <th>날짜</th>
                            <th>인원수</th>
                            <th>1인당 가격</th>
                            <th>메뉴</th>
                        </tr>
                    </thead>
                    <tbody id="salelist">
                        <tr v-for="(dining,i) in AuctionList" v-bind:key="i" >
                            <td>{{dining.dnName}}</td>
                            <td>{{dining.dnLocation}}</td>
                            <td>{{dining.dnDate}}</td>
                            <td>{{dining.dnPeople}}</td>
                            <td>{{dining.dnPrice}}</td>
                            <td>{{dining.dnMenu}}</td>
                        </tr>
                    </tbody>
                  </table>
                </b-tab>

                <b-tab no-body title="낙찰 목록">
                  <table width="100%" id="list">
                    <colgroup>
                    <col width=20%>
                    <col width=20%>
                    <col width=20%>
                    <col width=10%>
                    <col width=10%>
                    <col width=20%>
                    <col width=10%>
                    </colgroup>
                    <thead color="red">
                        <tr>
                            <th>회식명</th>
                            <th>위치</th>
                            <th>날짜</th>
                            <th>인원수</th>
                            <th>1인당 가격</th>
                            <th>메뉴</th>
                        </tr>
                    </thead>
                    <tbody id="soldlist">
                        <tr v-for="dining in SuccessList" v-bind:key="dining.id" >
                            <td>{{dining.dnName}}</td>
                            <td>{{dining.dnLocation}}</td>
                            <td>{{dining.dnDate}}</td>
                            <td>{{dining.dnPeople}}</td>
                            <td>{{dining.dnPrice}}</td>
                            <td>{{dining.dnMenu}}</td>
                        </tr>
                    </tbody>
                  </table>
                </b-tab>
                
                <b-modal @ok="TenderSend(tempDiningID)" id="modal-1" title="입찰" ok-only>
                  <div class="mb-2">
                    <small style="font-family: 'Do Hyeon', sans-serif;">제시할 가격</small>
                    <b-form-input v-model="tenderInfo.price" placeholder="인당 가격을 제시해 주세요"></b-form-input>
                  </div>
                  <div class="mb-2">
                    <small style="font-family: 'Do Hyeon', sans-serif;">추가정보</small>
                    <b-form-input v-model="tenderInfo.memo" placeholder="추가로 기입할 말을 입력해주세요"></b-form-input>
                  </div>
                </b-modal>

                <b-tab title="Text">
                  <b-card-title>This tab does not have the <code>no-body</code> prop set</b-card-title>
                  <b-card-text>
                    Quis magna Lorem anim amet ipsum do mollit sit cillum voluptate ex nulla tempor. Laborum
                    consequat non elit enim exercitation cillum aliqua consequat id aliqua. Esse ex
                    consectetur mollit voluptate est in duis laboris ad sit ipsum anim Lorem. Incididunt
                    veniam velit elit elit veniam Lorem aliqua quis ullamco deserunt sit enim elit aliqua
                    esse irure.
                  </b-card-text>
                </b-tab>
              </b-tabs>
            </b-card>
          </div>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import "../../assets/css/post.scss";
import axios from "axios";
import constants from "../../lib/constants";
import "sweetalert2/dist/sweetalert2.min.css";
import swal from "sweetalert";
import { Tabs, Tab } from 'vue-slim-tabs'

const SERVER_URL = constants.ServerUrl;

export default {
  name: "",
  components: {
    constants, Tabs, Tab
  },
  data: () => {
    return {
      DiningList:[
        ],
      AuctionList:[
      ],
      SuccessList:[],
      tenderInfo:{
        dnID:"",
        boID:"",
        price:0,
        memo:"",
      },
      userid:"bossID",
      tempDiningID:"",
    };
  },
  created() {
    // this.addprofileInfo();
    // this.allStudy();
    
      this.createDining()
      this.createAuction()
      this.createSuccess()
  },
  methods: 
  {
    sendDiningID(id){
      this.tempDiningID = id
    },
    createDining(test)
    {
         axios
        .get(SERVER_URL + "/boss/대전광역시 유성구/0")
        .then((res) => {
          console.log(res)
          this.DiningList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createSuccess()
    {
          axios
        .get(SERVER_URL + "/boss/대전광역시 유성구/3")
        .then((res) => {
          console.log(res.data)
          this.SuccessList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createAuction()
    {
          axios
        .get(SERVER_URL + `/dining/getboss/${this.userid}`)
        .then((res) => {
          console.log("AuctionData")
          console.log(res.data)
          
          this.AuctionList = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    TenderSend(i){
      this.tenderInfo.dnID = i
      this.tenderInfo.boID = "bossID"
      console.log(this.tenderInfo)
      axios
        .post(SERVER_URL + "/boss/tender",this.tenderInfo)
        .then((res) => {
          
          console.log(res.data)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    handleScroll() {
      var d = document.documentElement;
      var offset = d.scrollTop + window.innerHeight;
      var height = d.offsetHeight + 685;
      if (offset >= height) {
        this.scrolled += 3;
      }
    },
    moveTop() {
      var location = document.querySelector("#mainimg").offsetTop;
      window.scrollTo({ top: location, behavior: "smooth" });
    },
    moveBottom() {
      var location = document.querySelector("#maintext").offsetTop;
      window.scrollTo({ top: location - 71.8, behavior: "smooth" });
    },
  },
};
</script>

<style scoped>
* {
  font-family: "IBMPlexSansKR-Text";
}
.about{
  border:1px solid rgba(255,255,255,0.4);
  background-color:rgba(255,255,255,0.2);
  color:rgba(255,165,0,0.7);
  font-family: 'Do Hyeon',sans-serif;
}
.about:hover{
  background-color:rgba(255,255,255,0.8);
  font-family: 'Do Hyeon',sans-serif;
  color:rgba(0,0,0,0.5)
}
#mainimg {
  position: absolute;
  top: 0 !important;
  min-width: 100%;
  width: 100%;
  right: 0;
  height: 50vh !important;
  line-height: 50vh;
  background-image: url("../../assets/img/mimi.jpg");
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center center;
  z-index: 6;

}
#maintext {
  position: relative;
  top: 50vh;
}
.main-text {
  padding-top: 40vh;
  background-color: #000000;
  background-color: rgba(0, 0, 0, 0.8);
  height: 50vh;
}
input[type="radio"] {
  position: absolute;
}
.radiolabel {
  position: relative;
  /* margin-right: 1em; */
  padding-left: 3em;
  padding-right: 1em;
  line-height: 2;
  cursor: pointer;
}
.radiolabel::before {
  box-sizing: border-box;
  content: " ";
  position: absolute;
  top: 0.3em;
  left: 1em;
  display: block;
  width: 1.4em;
  height: 1.4em;
  border: 2px solid orange;
  border-radius: 0.25em;
  z-index: -1;
}
input[type="radio"] + .radiolabel::before {
  border-radius: 1em;
}
/* Checked */
input[type="radio"]:checked + .radiolabel {
  /* padding-left: 1em; */
  transform: translateX(-13%);
  color: black;
}
input[type="radio"]:checked + .radiolabel::before {
  top: 0;
  left: 1.7em;
  width: 80%;
  height: 2em;
  background: orange;
}
/* Transition */
.radiolabel,
.radiolabel::before {
  -webkit-transition: 0.25s all ease;
  -o-transition: 0.25s all ease;
  transition: 0.25s all ease;
}
.input-group.md-form.form-sm.form-2 input {
  border: 1px solid #bdbdbd;
  border-top-left-radius: 0.25rem;
  border-bottom-left-radius: 0.25rem;
}
.input-group.md-form.form-sm.form-2 input.amber-border {
  border: 1px solid orange;
}
select.form-control {
  border-right: 1px solid orange;
}
.searchinput {
  border: 2px solid orange;
  border-radius: 100px;
}
input::placeholder {
  font-size: small;
}
</style>
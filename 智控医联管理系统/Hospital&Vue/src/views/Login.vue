<template>
  <div
      style="height: 100vh;
  display: flex; align-items: center;
  justify-content: center;
 background-color: #427cb3;;
"
      class="login-wrap"
  >
    <div
        style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden"
    >
      <div style="flex: 1">
        <img src="@/assets/17.jpg" alt="" style="width: 400px;height: 400px" />
      </div>
      <div
          style="flex: 1; display: flex; align-items: center; justify-content: center"
      >
        <el-form
            :model="loginForm"
            style="width: 80%"
            :rules="loginRules"
            ref="ruleForm"
        >
          <div
              style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px"
          >
            欢迎登录医院管理系统
          </div>
          <el-form-item prop="id">
            <!--必须绑定v-model输入框才能输入字符---->
            <el-input v-model="loginForm.id">
              <i slot="prefix" class="el-input__icon el-icon-user"></i>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="loginForm.password"
                size="medium"
                placeholder="请输入密码"
                clearable
                show-password
            >
              <i slot="prefix" class="el-input__icon el-icon-lock"></i>
            </el-input>
          </el-form-item>
          <!-- 角色单选框 -->
<!--          <el-form-item class="role" style="margin-left: 50px">-->
<!--            <el-radio-group v-model="role" label-width="100%">-->
<!--              <el-radio-button label="患者"></el-radio-button>-->
<!--              <el-radio-button label="医生"></el-radio-button>-->
<!--              <el-radio-button label="管理员"></el-radio-button>-->
<!--            </el-radio-group>-->
<!--          </el-form-item>-->
          <!-- 角色单选框 -->
          <el-form-item class="role">
            <el-radio-group v-model="role" size="small">
              <el-radio label="患者"></el-radio>
              <el-radio label="医生"></el-radio>
              <el-radio label="管理员"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                style="width: 100%"
                @click="submitLoginForm('ruleForm')"
            >登 录</el-button
            >
          </el-form-item>

          <div style="display: flex">
            <div style="flex: 1">
              还没有账号？请
              <span
                  style="color: #0f9876; cursor: pointer"
                  @click="registerFormVisible = true"
              >注册</span
              >
            </div>

          </div>
        </el-form>
      </div>
    </div>
    <!-- 注册对话框 -->
    <el-dialog title="患者注册" :visible.sync="registerFormVisible">
      <el-form
          class="findPassword"
          :model="registerForm"
          :rules="registerRules"
          ref="registerForm"
      >
        <el-form-item label="账号" label-width="80px" prop="pId">
          <el-input v-model.number="registerForm.pId"></el-input>
        </el-form-item>
        <el-form-item label="性别" label-width="80px">
          <el-radio v-model="registerForm.pGender" label="男">男</el-radio>
          <el-radio v-model="registerForm.pGender" label="女">女</el-radio>
        </el-form-item>
        <el-form-item label="密码" label-width="80px" prop="pPassword">
          <el-input v-model="registerForm.pPassword"></el-input>
        </el-form-item>
        <el-form-item label="姓名" label-width="80px" prop="pName">
          <el-input v-model="registerForm.pName"></el-input>
        </el-form-item>
        <el-form-item label="出生日期" label-width="80px" prop="pBirthday">
          <el-date-picker
              v-model="registerForm.pBirthday"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="手机号" label-width="80px" prop="pPhone">
          <el-input v-model="registerForm.pPhone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱号" label-width="80px" prop="pEmail">
          <el-input v-model="registerForm.pEmail"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" label-width="80px" prop="pCard">
          <el-input v-model="registerForm.pCard"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="registerFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="registerClick('registerForm')"
        >确 定</el-button
        >
      </div>
    </el-dialog>

  </div>

</template>

<script>
import request from "@/utils/request.js";
import { setToken } from "@/utils/storage.js";
export default {
  name: "Login",
  data() {
    var validateMoblie = (rule, value, callback) => {
      if (value === undefined) {
        callback(new Error("请输入手机号"));
      } else {
        let reg = /^1(3[0-9]|4[5,7]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0,1,7,8]|8[0-9]|9[1,8,9])\d{8}$/;
        if (!reg.test(value)) {
          callback(new Error("请输入合法的手机号"));
        }
        callback();
      }
    };
    var validateCard = (rule, value, callback) => {
      if (value === undefined) {
        callback(new Error("请输入身份证号"));
      } else {
        let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!reg.test(value)) {
          callback(new Error("请输入合法的身份证号码"));
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.findForm.checkPassword !== "") {
          this.$refs.findForm.validateField("checkPassword");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.findForm.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      //背景图片
      backgroundDiv: {
        backgroundImage: "url(" + require("../assets/doctor.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      },
      loginForm: {
        id: "202301",
        password: "123456"
      },
      loginRules: {
        id: [
          { required: true, message: "请输入账号编号", trigger: "blur" },
          { min: 3, max: 50, message: "长度在 3到 50 个字符", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      role: "患者",
      findRole: "患者",
      //找回密码
      findFormVisible: false,
      findForm: {
        code: "",
        newPassword: "",
        checkPassword: "",
        pEmail: ""
      },

      findRules: {
        pEmail: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
        newPassword: [{ validator: validatePass, trigger: "blur" }],
        checkPassword: [{ validator: validatePass2, trigger: "blur" }]
      },
      totalTime: 60,
      content: "发送验证码",
      canClick: true,
      //注册
      registerFormVisible: false,
      registerForm: {
        pGender: "男"
      },
      registerRules: {
        pId: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { type: "number", message: "账号必须数字类型", trigger: "blur" }
        ],
        pPassword: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 4, max: 50, message: "长度在 4到 50 个字符", trigger: "blur" }
        ],
        pName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 2, max: 8, message: "长度在 2到 8 个字符", trigger: "blur" }
        ],
        pEmail: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        pPhone: [{ validator: validateMoblie }],
        pCard: [{ validator: validateCard }],
        pBirthday: [
          { required: true, message: "选择出生日期", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    //点击注册确认按钮
    registerClick(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          request
              .get("patient/addPatient", {
                params: {
                  pId: this.registerForm.pId,
                  pName: this.registerForm.pName,
                  pPassword: this.registerForm.pPassword,
                  pGender: this.registerForm.pGender,
                  pEmail: this.registerForm.pEmail,
                  pPhone: this.registerForm.pPhone,
                  pCard: this.registerForm.pCard,
                  pBirthday: this.registerForm.pBirthday
                }
              })
              .then(res => {
                if (res.data.status !== 200)
                  return this.$message.error("账号或邮箱已被占用！");
                this.registerFormVisible = false;
                this.$message.success("注册成功！");
                console.log(res);
              });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 点击找回密码确认按钮
    findPassword(findForm) {
      this.$refs[findForm].validate(valid => {
        if (valid) {
          //如果是选中患者
          if (this.findRole === "患者") {
            request
                .get("patient/findPassword", {
                  params: {
                    pEmail: this.findForm.pEmail,
                    pPassword: this.findForm.newPassword,
                    code: this.findForm.code
                  }
                })
                .then(res => {
                  if (res.data.status !== 200)
                    return this.$message.error("验证码错误或者已过期！！！");
                  this.$message.success("密码修改成功！！请登录");
                  this.findFormVisible = false;
                });
          }
          //如果是选中管理员
          if (this.findRole === "管理员") {
            request
                .get("admin/findPassword", {
                  params: {
                    aEmail: this.findForm.pEmail,
                    aPassword: this.findForm.newPassword,
                    code: this.findForm.code
                  }
                })
                .then(res => {
                  if (res.data.status !== 200)
                    return this.$message.error("验证码错误或者已过期！！！");
                  this.$message.success("密码修改成功！！请登录");
                  this.findFormVisible = false;
                });
          }
          //如果是选中患者
          if (this.findRole === "医生") {
            request
                .get("doctor/findPassword", {
                  params: {
                    dEmail: this.findForm.pEmail,
                    dPassword: this.findForm.newPassword,
                    code: this.findForm.code
                  }
                })
                .then(res => {
                  if (res.data.status !== 200)
                    return this.$message.error("验证码错误或者已过期！！！");
                  this.$message.success("密码修改成功！！请登录");
                  this.findFormVisible = false;
                });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //点击发送验证码按钮
    sendEmail() {
      //倒计时
      if (!this.canClick) return; //改动的是这两行代码
      this.canClick = false;
      this.content = this.totalTime + "s后重新发送";
      let clock = window.setInterval(() => {
        this.totalTime--;
        this.content = this.totalTime + "s后重新发送";
        if (this.totalTime < 0) {
          window.clearInterval(clock);
          this.content = "重新发送验证码";
          this.totalTime = 10;
          this.canClick = true; //这里重新开启
        }
      }, 1000);

      //如果是选中患者
      if (this.findRole === "患者") {
        request
            .get("patient/sendEmail", {
              params: {
                pEmail: this.findForm.pEmail
              }
            })
            .then(res => {
              console.log(this.findForm.pEmail);
              console.log(res);
              if (res.data.status !== 200)
                return this.$message.error("该邮箱暂未注册！请先注册！");
              this.$message.success("验证码发送成功！");
            });
      }
      //如果是选中管理员
      if (this.findRole === "管理员") {
        request
            .get("admin/sendEmail", {
              params: {
                aEmail: this.findForm.pEmail
              }
            })
            .then(res => {
              console.log(this.findForm.pEmail);
              console.log(res);
              if (res.data.status !== 200)
                return this.$message.error("该邮箱暂未注册！请先注册！");
              this.$message.success("验证码发送成功！");
            });
      }
      //如果是选中医生
      if (this.findRole === "医生") {
        request
            .get("doctor/sendEmail", {
              params: {
                dEmail: this.findForm.pEmail
              }
            })
            .then(res => {
              console.log(this.findForm.pEmail);
              console.log(res);
              if (res.data.status !== 200)
                return this.$message.error("该邮箱暂未注册！请先注册！");
              this.$message.success("验证码发送成功！");
            });
      }
    },
    //提交表单
    submitLoginForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.role === "管理员") {
            var params = new URLSearchParams();
            params.append("aId", this.loginForm.id);
            params.append("aPassword", this.loginForm.password);

            request
                .post("admin/login", params)
                .then(res => {
                  console.log(res);
                  if (res.data.status != 200)
                    return this.$message.error("用户名或密码错误");
                  setToken(res.data.data.token);
                  this.$router.push("/adminLayout");
                })
                .catch(err => {
                  this.$message.error("用户名或密码错误");
                  console.error(err);
                });
          }
          if (this.role === "医生") {
            var params1 = new URLSearchParams();
            params1.append("dId", this.loginForm.id);
            params1.append("dPassword", this.loginForm.password);

            request
                .post("doctor/login", params1)
                .then(res => {
                  console.log(res);
                  if (res.data.status != 200)
                    return this.$message.error("用户名或密码错误");
                  setToken(res.data.data.token);
                  this.$router.push("/doctorLayout");
                })
                .catch(err => {
                  this.$message.error("用户名或密码错误");
                  console.error(err);
                });
          }
          if (this.role === "患者") {
            var params2 = new URLSearchParams();
            params2.append("pId", this.loginForm.id);
            params2.append("pPassword", this.loginForm.password);

            request
                .post("patient/login", params2)
                .then(res => {
                  console.log(res);
                  if (res.data.status != 200)
                    return this.$message.error("用户名或密码错误");
                  setToken(res.data.data.token);
                  this.$router.push("/patientLayout");
                })
                .catch(err => {
                  this.$message.error("用户名或密码错误");
                  console.error(err);
                });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style lang="scss">
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background: #0f9876;
  background-image: url("../assets/img/login-bg.svg");
  background-size: 100% 100%;
}
</style>

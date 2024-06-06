const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        IDUser:{type:String,required:true},
        Email:{type:String,required:true},
        Image:{type:String,required:true},
        Money:{type:Number,required:true},
        Time:{type:String,required:true},
        Status:{type:Number,required:true}
    },
    {
        //tên của bảng
        collection:"Recharge"
    }
)
let Recharge = db.mongoose.model("Recharge",scheme);
module.exports = {Recharge}
const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        IDUser:{type:String,required:true},
        IDSeller:{type:String,required:true},
        IDProduct:{type:String,required:true}
    },
    {
        //tên của bảng
        collection:"Bill"
    }
)
let Bill = db.mongoose.model("Bill",scheme);
module.exports = {Bill}
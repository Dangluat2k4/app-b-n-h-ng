const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        IDUser:{type:String,required:true},
        IDProduct:{type:String,required:true}
    },
    {
        //tên của bảng
        collection:"Cart"
    }
)
let Cart = db.mongoose.model("Cart",scheme);
module.exports = {Cart}
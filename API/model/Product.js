const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        NameProduct:{type:String,required:true},
        Image:{type:String,required:true},
        Price:{type:Number,required:true}
    },
    {
        //tên của bảng
        collection:"Product"
    }
)
let Product = db.mongoose.model("Product",scheme);
module.exports = {Product}
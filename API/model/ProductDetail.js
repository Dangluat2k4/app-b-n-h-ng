const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        IDProduct:{type:String,required:true},
        Size:{type:Array,required:true},
        Date:{type:String,required:true},
        Amount:{type:Number,required:true}

    },
    {
        //tên của bảng
        collection:"ProductDetail"
    }
)
let ProductDetail = db.mongoose.model("ProductDetail",scheme);
module.exports = {ProductDetail}
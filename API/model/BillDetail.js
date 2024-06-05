const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        IDUser:{type:String,required:true},
        IDBill:{type:String,required:true},
        Status:{type:Number,required:true},
        Date:{type:String,required:true},
        Amount:{type:Number,required:true},
        Total:{type:Number,required:true}

    },
    {
        //tên của bảng
        collection:"BillDetail"
    }
)
let BillDetail = db.mongoose.model("BillDetail",scheme);
module.exports = {BillDetail}
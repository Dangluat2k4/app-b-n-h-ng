const db = require("./db")
const scheme = new db.mongoose.Schema(
    {
        // trường dữ liệu
        NameCategory:{type:String,required:true}
    },
    {
        //tên của bảng
        collection:"Category"
    }
)
let Category = db.mongoose.model("Category",scheme);
module.exports = {Category}
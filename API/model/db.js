const mongoose = require("mongoose")
mongoose.connect('mongodb://localhost:27017/Data')
        .catch((loi)=>{
                console.log("Lỗi kêt noi cơ sở dữ liệu");
                console.log(loi);
        })
 module.exports = {mongoose}     
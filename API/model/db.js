const mongoose = require("mongoose")
const linkOFf = "mongodb://localhost:27017/Data"
const linkOn = 'mongodb+srv://thuy:123456Aa%40@agilepma101.umvhval.mongodb.net/Data'
mongoose.connect(linkOFf)
        .catch((loi)=>{
                console.log("Lỗi kêt noi cơ sở dữ liệu");
                console.log(loi);
        })
 module.exports = {mongoose}     
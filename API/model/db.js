const mongoose = require("mongoose")
const linkOnline = 'mongodb+srv://thuy:123456Aa%40@agilepma101.umvhval.mongodb.net/Data'
const linkOffline = "mongodb://localhost:27017/Data"
mongoose.connect(linkOffline)
        .catch((loi)=>{
                console.log("Lỗi kêt noi cơ sở dữ liệu");
                console.log(loi);
        })
 module.exports = {mongoose}     
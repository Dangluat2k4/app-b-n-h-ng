const mongoose = require("mongoose")
mongoose.connect('mongodb+srv://thuy:123456Aa%40@agilepma101.umvhval.mongodb.net/Data')
        .catch((loi)=>{
                console.log("Lỗi kêt noi cơ sở dữ liệu");
                console.log(loi);
        })
 module.exports = {mongoose}     
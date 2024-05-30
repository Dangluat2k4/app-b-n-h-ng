var myProduct = require('../model/Product')

// exports.DanhSach =async  (req, res, next) => {

//    // let list = await myProduct.Product.find().sort({name:1});
//    // res.render('product/list-product', { listSP: list })
// }
exports.XemChiTiet = (req, res, next) => {
   res.send("<h1>Xem chi tiết sản phẩm</h1>");
}

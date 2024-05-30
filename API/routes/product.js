var express = require('express');
var router = express.Router();

var productCtrl = require('../controller/product.crl');
// khu vực viết code định nghia các link ở đây


// http://localhost:3000/product
router.get('/',   productCtrl.DanhSach );
// http://localhost:3000/products/view/123456
router.get('/view/:id', productCtrl.XemChiTiet);
// http://localhost:3000/products/add
router.get('/add', (req, res, next)=>{
res.send("<h1>Thêm mới</h1>");
});


// http://localhost:3000/products/edit/123456
router.get('/edit/:id', (req, res, next)=>{
res.send("<h1>Sửa sản phẩm</h1>");
});
// http://localhost:3000/products/delete/123456
router.get('/delete/:id', (req, res, next)=>{
res.send("<h1>Xóa sản phẩm</h1>");
});




// chú ý: không được thiếu export
module.exports = router;

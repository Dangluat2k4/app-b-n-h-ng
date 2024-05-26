var express = require('express');
var router = express.Router();
var ctrl = require("../controller/ctrlUser")
var mdw = require('../midleware/api_authen');
var multer = require('multer');
var objUpload = new multer({dest:'./tmp'});

router.get("/product",ctrl.XemDanhSachSanPham)
router.get("/product/:id",ctrl.XemSanPham)
router.get("/product/category/:id",ctrl.XemDanhSachSanPhamTheoLoai)
router.get("/category",ctrl.XemDanhSachLoai)
router.get("/productdetail/:id",ctrl.XemSanPhamCT)
router.get("/productdetail",ctrl.XemDanhSachSPCT)
module.exports = router;

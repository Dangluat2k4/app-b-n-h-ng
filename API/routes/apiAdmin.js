var express = require('express');
var router = express.Router();
var ctrl = require("../controller/ctrlAdmin")
var mdw = require('../midleware/api_authen');
var multer = require('multer');
var objUpload = new multer({dest:'./tmp'});

router.get("/product",ctrl.XemDanhSachSanPham)
router.post("/product/add",objUpload.single("imgAnh"),ctrl.ThemSanPham)
router.put("/product/edit/:id",objUpload.single("imgAnh"),ctrl.SuaSanPham)
router.delete("/product/delete/:id",objUpload.single("imgAnh"),ctrl.XoaSanPham)

router.get("/category",ctrl.XemLoai)
router.post("/category/add",ctrl.ThemLoai)
router.put("/category/edit/:id",ctrl.SuaLoai)
router.delete("/category/edit/:id",ctrl.Xoaloai)
module.exports = router;

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
router.get("/user/cart/:id",ctrl.DanhSachCart)
router.post("/user/cart/add",ctrl.ThemCart)
router.put("/cart/edit/:id",ctrl.SuaCart)
router.delete("/cart/delete/:id",ctrl.XoaCart)
router.post("/bill/add",ctrl.ThemHoaDon)
router.get("/user/bill/:id",ctrl.DanhSachBill)
router.get("/user/billdetail/:id",ctrl.DanhSachBillDetail)
router.delete("/user/bill/delete/:id",ctrl.XoaBill)

router.post("/login", ctrl.Login);
router.post("/Reg", ctrl.Reg);
router.put("/changepassword",ctrl.changePassword)
module.exports = router;

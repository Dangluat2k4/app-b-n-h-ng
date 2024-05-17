var express = require('express');
var router = express.Router();
var ctrl = require("../controller/ctrlUser")
var mdw = require('../midleware/api_authen');
var multer = require('multer');
var objUpload = new multer({dest:'./tmp'});

router.get("/product",ctrl.XemDanhSachSanPham)
module.exports = router;

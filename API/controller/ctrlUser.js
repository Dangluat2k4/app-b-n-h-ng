
const Bill = require("../model/Bill")
const BillDetail = require("../model/BillDetail")
const Cart = require("../model/Cart")
const Category = require("../model/Category")
const Product = require("../model/Product")
const ProductDetail = require("../model/ProductDetail")
const fs = require('fs');// thư viện sử lý file
const {Account} = require("../model/Account");
const bcrypt = require("bcrypt");

//get Acount
exports.Login = async (req, res, next)=>{
    try {
        const user = await Account
                    .findByCredentials(req.body.Email, req.body.Password)
        if (!user) {
            return res.status(401)
                    .json({error: 'Sai thông tin đăng nhập'})
        }
        // đăng nhập thành công, tạo token làm việc mới
        const token = await user.generateAuthToken()
        user.token = token;
        return res.status(200).send(user)
    } catch (error) {
        console.log(error+" Pass"+req.body.Password)
        return res.status(400).send(error)
    }
  }
exports.Reg = exports.doReg = async (req, res, next)=>{
    try {
        const salt = await bcrypt.genSalt(10);
        const user = new Account(req.body);
        user.Password = await bcrypt.hash(req.body.Password, salt);
        user.FullName = req.body.FullName;
        user.Credit = 0;
        user.Status = 1;
        const token = await user.generateAuthToken();
        let new_u = await user.save()
        return res.status(201).send({ user: new_u, token })
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
    res.status(200).json( {status: 1, msg: 'Trang đăng ký'});
 }
 // Product
 exports.XemDanhSachSanPham = async (req,res,next)=>{
    try {
        let list = await Product.Product.find().sort({ Name: 1 })
        res.status(200).json(list);
       } catch (error) {
        console.log(error)
        return res.status(400).send(error)
       }
 }
 exports.XemDanhSachSanPhamTheoLoai =  async (req, res, next) => {
    try {
     let list = await Product.Product.find({IDCategory:req.params.id}).sort({ Name: 1 })
     res.status(200).json(list);
    } catch (error) {
     console.log(error)
     return res.status(400).send(error)
    }
 }
 exports.XemSanPham = async (req, res, next) => {
    let obj = null;
    let smg = '';
    try {
        obj = await Product.Product.findOne({ _id: req.params.id });
        smg = "Lấy dữ liệu thành công"
        if(obj==null){
            smg = "Sản phẩm không tồn tại"
         return res.status(400).json({  smg: smg });
        }
    } catch (error) {
        smg = error.message;
    }
    res.status(200).json(obj);
};

exports.XemDanhSachLoai = async (req,res,next)=>{
    try {
        let obj = await Category.Category.find()
        res.status(200).json(obj);
       } catch (error) {
        console.log(error)
        return res.status(400).send(error)
       }
 }

 exports.XemDanhSachSPCT = async (req,res,next)=>{
    try {
        let obj = await ProductDetail.ProductDetail.find()
        res.status(200).json(obj);
       } catch (error) {
        console.log(error)
        return res.status(400).send(error)
       }
 }
 exports.XemSanPhamCT = async (req,res,next)=>{
    let obj = null;
    let smg = '';
    try {
        
        obj = await ProductDetail.ProductDetail.findOne({IDProduct:req.params.id  });
        smg = "Lấy dữ liệu thành công"
        if(obj==null){
            smg = "Sản phẩm không tồn tại"
         return res.status(400).json({  smg: smg });
        }
    } catch (error) {
        smg = error.message;
    }
    res.status(200).json(obj);
 }

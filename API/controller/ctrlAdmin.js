
const Bill = require("../model/Bill")
const BillDetail = require("../model/BillDetail")
const Cart = require("../model/Cart")
const Category = require("../model/Category")
const Product = require("../model/Product")
const ProductDetail = require("../model/ProductDetail")
const fs = require('fs');// thư viện sử lý file
const { Account } = require("../model/Account");
const bcrypt = require("bcrypt");



exports.ThemSanPham = async (req, res, next) => {
    let smg = ''
    try {
        if (req.method == "POST") {
            let { NameProduct, Price, Size, Date, IDCategory, Image, Amount } = req.body;
            if (NameProduct == '' || Price == '' || Size == '', Date == '' || IDCategory == "" || Image == "" || Amount == "") {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            if (isNaN(Price)) {
                smg = "Giá phải là số"
                return res.status(400).json({ smg: smg })
            }

            let objProduct = new Product.Product;
            let objProductDetail = new ProductDetail.ProductDetail;
            if (req.file != undefined) {
                let file_path = './public/uploads/' + req.file.originalname;
                if (fs.readFileSync(req.file.path)) {
                    // file có tồn tại
                    console.log(req.file);
                    if (req.file.mimetype.indexOf('image')) {
                        smg = 'Anh khong đúng định dạng'
                        console.log(smg)
                        return res.status(400).json({ smg: smg })
                    }
                    fs.renameSync(req.file.path, file_path);
                    objProduct.Image = 'uploads/' + req.file.originalname;
                }
            } else {
                objProduct.Image = Image;
            }

            // đưa đối tượng vào cơ sở dữ liệu
            objProduct.NameProduct = NameProduct;
            objProduct.Price = Number(Price);
            objProductDetail.IDProduct = objProduct.id;
            objProduct.IDCategory = IDCategory;
            objProductDetail.Size = Size.split(",");
            objProductDetail.Date = Date;
            objProductDetail.Amount = Number(Amount);
            await objProduct.save();
            await objProductDetail.save();
            smg = 'Thêm thành công, id mới = ' + objProduct._id
            return res.status(200).json({ smg: smg })
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }

    res.render('product/add-product', { smg: smg });

};

exports.SuaSanPham = async (req, res, next) => {
    let smg = ''
    let obj = null;
    let objDT = null;
    try {
        obj = await Product.Product.findOne({ _id: req.params.id });
        objDT = await ProductDetail.ProductDetail.findOne({ IDProduct: req.params.id });
        smg = 'Lấy dữ liệu thành công !!!'
        console.log(req.query);
        if (req.method == "POST") {
            //console.log(req.query);
            let { NameProduct, Price, Size, Date, IDCategory, Image, Amount } = req.body;
            if (NameProduct == '' || Price == '' || IDCategory == "" || Image == "") {
                smg = "Không được để trống"
                return res.render('product/update-product', { smg: smg, obj: obj ,objDT: objDT });
            }
            if (Size == '', Date == '' || Amount == "") {
                return res.render('product/update-product', { smg: smg, obj: obj ,objDT: objDT });
            }
            console.log(Price);
            if (isNaN(Price)) {
                smg = "Giá phải là số"
                return res.render('product/update-product', { smg: smg, obj: obj ,objDT: objDT });
            }
            let objProduct = {};
            let objProductDetail = {};
            if (req.file != undefined) {
                let file_path = './public/uploads/' + req.file.originalname;
                if (fs.readFileSync(req.file.path)) {
                    // file có tồn tại
                    console.log(req.file);
                    if (req.file.mimetype.indexOf('image')) {
                        smg = 'Anh khong đúng định dạng'
                        console.log(smg)
                        return res.render('product/update-product', { smg: smg, obj: obj ,objDT: objDT });
                    }
                    fs.renameSync(req.file.path, file_path);
                    objProduct.Image = 'uploads/' + req.file.originalname;
                }
            } else {
                objProduct.Image = Image;
            }
            // đưa đối tượng vào cơ sở dữ liệu
            objProduct.NameProduct = NameProduct;
            objProduct.Price = Number(Price);
            objProductDetail.IDProduct = objProduct.id;
            objProduct.IDCategory = IDCategory;
            objProductDetail.Size = Size.split(",");
            objProductDetail.Date = Date;
            objProductDetail.Amount = Number(Amount);
            await Product.Product.findByIdAndUpdate(req.params.id, objProduct);
            await ProductDetail.ProductDetail.updateOne({ IDProduct: req.params.id }, objProductDetail);
            smg = 'Sửa thành công, id mới = ' + objProduct._id
            //return res.status(200).json({ smg: smg })

            res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
        }
        else{
            res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
        }


        if (obj == null) {
            smg = "Sản phẩm không tồn tại"
            //return res.status(400).json({  smg: smg });
        }
        //return res.render('product/update-product', {smg: smg, obj:obj});


        // if (req.method == "PUT") {
        //     let { NameProduct, Price, Size, Date, IDCategory,Image,Amount } = req.body;
        //     if (NameProduct == '' || Price == '' || Size == '', Date == ''||IDCategory==""||Image==""||Amount=="") {
        //         smg = "Không được để trống"
        //         return res.status(400).json({ smg: smg })
        //     }
        //     if (isNaN(Price)) {
        //         smg = "Giá phải là số"
        //         return res.status(400).json({ smg: smg })
        //     }
        //     let objProduct = {};
        //     let objProductDetail = {};
        //     if (req.file!=undefined) {
        //         let file_path = './public/uploads/' + req.file.originalname;
        //         if (fs.readFileSync(req.file.path)) {
        //             // file có tồn tại
        //             console.log(req.file);
        //             if (req.file.mimetype.indexOf('image')) {
        //                 smg = 'Anh khong đúng định dạng'
        //                 console.log(smg)
        //                 return res.status(400).json({ smg: smg })
        //             }
        //             fs.renameSync(req.file.path, file_path);
        //             objProduct.Image = 'uploads/' + req.file.originalname;
        //         }
        //     }else{
        //         objProduct.Image = Image;
        //     }
        //     // đưa đối tượng vào cơ sở dữ liệu
        //     objProduct.NameProduct = NameProduct;
        //     objProduct.Price = Number(Price);
        //     objProductDetail.IDProduct = objProduct.id;
        //     objProduct.IDCategory = IDCategory;
        //     objProductDetail.Size = Size.split(",");
        //     objProductDetail.Date = Date;
        //     objProductDetail.Amount = Number(Amount);
        //     await Product.Product.findByIdAndUpdate(req.params.id,objProduct);
        //     await ProductDetail.ProductDetail.updateOne({IDProduct:req.params.id},objProductDetail);
        //     smg = 'Sửa thành công, id mới = ' + objProduct._id
        //     return res.status(200).json({ smg: smg })
        // }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }


};
exports.XoaSanPham = async (req, res, next) => {
    let smg = '';
    let objProduct = null;
    let objProductDetail = null;
    try {
        objProduct = await Product.Product.findOne({ _id: req.params.id });
        objProductDetail = await ProductDetail.ProductDetail.findOne({ IDProduct: req.params.id });
        
        if (objProduct == null || objProductDetail == null) {
            smg = "Sản phẩm không tồn tại";
            return res.status(404).json({ message: smg });
        }

        await Product.Product.findByIdAndDelete(req.params.id);
        await ProductDetail.ProductDetail.deleteOne({ IDProduct: req.params.id });
        smg = 'Xóa thành công';
        
        res.render('product/delete-product');
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(500).json({ message: smg });
    }
};


// exports.Xoa = async (req, res, next) => {
//     console.log("Da xoa");  
//     try {
//         // Lấy id của sản phẩm cần xóa từ URL
//         const productId = req.params.id;
//         await Product.Product.findByIdAndDelete(productId);
//         await ProductDetail.ProductDetail.deleteOne({IDProduct: productId});

//         // Thực hiện xóa sản phẩm từ CSDL
//        // await myMD.spModel.findByIdAndDelete(productId);

//         // Gửi thông báo thành công về phía người dùng
//         res.send('Xóa sản phẩm thành công!');
//     } catch (error) {
//         // Xử lý lỗi nếu có
//         console.error('Lỗi khi xóa sản phẩm:', error);
//         res.status(500).send('Đã xảy ra lỗi khi xóa sản phẩm.');
//     }

//     res.render('product/delete-product');
// };



exports.XemDanhSachSanPham = async (req, res, next) => {
    try {
        let list = await Product.Product.find().sort({ Name: 1 })
        res.render('product/list-product', { listSP: list });
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemDanhSachSanPhamTheoLoai = async (req, res, next) => {
    try {
        let list = await Product.Product.find({ CateID: req.params.id }).sort({ Name: 1 })
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
        if (obj == null) {
            smg = "Sản phẩm không tồn tại"
            return res.status(400).json({ smg: smg });
        }
    } catch (error) {
        smg = error.message;
    }
    res.status(200).json(obj);
};

exports.XemLoai = async (req, res, next) => {
    let obj = null;
    let smg = '';
    try {
        obj = await Category.Category.findOne({ _id: req.params.id });
        smg = "Lấy dữ liệu thành công"
    } catch (error) {
        smg = error.message;
    }
    return res.status(200).json(obj);
};

exports.ThemLoai = async (req, res, next) => {
    let smg = ''
    try {
        if (req.method == "POST") {
            let { NameCategory } = req.body;
            if (NameCategory == '') {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            // đưa đối tượng vào cơ sở dữ liệu
            let objCate = new Category.Category;
            objCate.NameCategory = NameCategory;
            console.log("Catename= " + objCate);
            await objCate.save();
            smg = 'Thêm thành công'
            return res.status(200).json({ smg: smg })
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }
    res.status(400).json({ smg: smg })
};
exports.SuaLoai = async (req, res, next) => {
    let smg = ''
    let obj = null;
    try {
        obj = await Category.Category.findOne({ _id: req.params.id });
        smg = 'Lấy dữ liệu thành công'
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.status(400).json({ smg: smg });
        }
        if (req.method == "PUT") {
            let { NameCategory } = req.body;
            if (NameCategory == '') {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            let objCate = {};
            objCate.NameCategory = NameCategory;
            await Category.Category.findByIdAndUpdate(req.params.id, objCate);
            smg = 'Sửa thành công'
        }
        return res.status(200).json({ smg: smg, obj: obj });


    } catch (error) {
        smg = error.message;

    }
    return res.status(400).json({ smg: smg });
};
exports.Xoaloai = async (req, res, next) => {
    let smg = '';
    try {
        obj = await Category.Category.findOne({ _id: req.params.id });
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.status(400).json({ smg: smg });
        }
        smg = 'Lấy dữ liệu thành công'
        if (req.method == 'DELETE') {
            await Category.Category.findByIdAndDelete(req.params.id);
            smg = 'Xóa thành công'
        }
        return res.status(200).json({ smg: smg });
    } catch (error) {
        smg = "Lỗi: " + error.message;
    }
    return res.status(400).json({ smg: smg });
};


const Bill = require("../model/Bill")
const BillDetail = require("../model/BillDetail")
const Cart = require("../model/Cart")
const Category = require("../model/Category")
const Product = require("../model/Product")
const ProductDetail = require("../model/ProductDetail")
const fs = require('fs');// thư viện sử lý file
const { Account } = require("../model/Account");
const bcrypt = require("bcrypt");


exports.renderAddProductForm = async (req, res, next) => {
    try {
        let categories = await Category.Category.find();
        res.render('product/add-product', { categories: categories });
    } catch (error) {
        console.log(error.message);
        res.status(500).send("Internal Server Error");
    }
};

exports.ThemSanPham = async (req, res, next) => {
    let smg = ''
    try {
        if (req.method == "POST") {
            let { NameProduct, Price, Size, Date, IDCategory, Image, Amount } = req.body;
            if (!NameProduct || NameProduct.trim() === '') {
                return res.status(400).json({ smg: "Tên sản phẩm không được để trống" });
            }
        
            if (!Price || Price.trim() === '') {
                return res.status(400).json({ smg: "Giá sản phẩm không được để trống" });
            }
        
            if (!Size || Size.trim() === '') {
                return res.status(400).json({ smg: "Kích thước sản phẩm không được để trống" });
            }
        
            const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
            if (!Date || Date.trim() === '') {
                return res.status(400).json({ smg: "Ngày không được để trống" });
            } else if (!dateRegex.test(Date)) {
                return res.status(400).json({ smg: "Ngày không đúng định dạng yyyy-mm-dd" });
            }
        
            if (!IDCategory || IDCategory.trim() === '') {
                return res.status(400).json({ smg: "Danh mục sản phẩm không được để trống" });
            }
            if (!Amount || Amount.trim() === '') {
                return res.status(400).json({ smg: "Số lượng sản phẩm không được để trống" });
            }
        
            if (isNaN(Price)) {
                smg = "Giá phải là số"
                return res.status(400).json({ smg: smg })
            }
            if (isNaN(Amount)) {
                smg = "Số lượng phải là số"
                return res.status(400).json({ smg: smg })
            }
            // Validate the date using regex
            
            let objProduct = new Product.Product;
            let objProductDetail = new ProductDetail.ProductDetail;

            if (req.file && fs.existsSync(req.file.path)) {
                let file_path = './public/uploads/' + req.file.originalname;

                // Kiểm tra định dạng tập tin
                if (!req.file.mimetype.startsWith('image')) {
                    smg = 'Ảnh không đúng định dạng';
                    fs.unlinkSync(req.file.path); // Xóa tập tin tải lên tạm thời
                    return res.status(400).json({ smg: smg });
                }

                fs.renameSync(req.file.path, file_path);
                objProduct.Image = '/uploads/' + req.file.originalname;
            } else {
                smg = 'ảnh không được trống ';
                return res.status(400).json({ smg: smg });
            }

            // đưa đối tượng vào cơ sở dữ liệu
            objProduct.NameProduct = NameProduct;
            objProduct.Price = Number(Price);
            objProduct.IDCategory = IDCategory;

            objProductDetail.IDProduct = objProduct.id;
            objProductDetail.Size = Size.split(",");
            objProductDetail.Date = Date;
            objProductDetail.Amount = Number(Amount);

            await objProduct.save();
            await objProductDetail.save();
            smg = 'Thêm thành công, id mới = ' + objProduct._id
            return res.redirect('/apiAdmin/product');
       //     return res.status(200).json({ smg: smg })
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }

    let categories = await Category.Category.find();
    res.render('product/add-product', { smg: smg, categories: categories });

};

exports.SuaSanPham = async (req, res, next) => {
    let smg = ''
    let obj = null;
    let objDT = null;
    try {
        obj = await Product.Product.findOne({ _id: req.params.id });
        objDT = await ProductDetail.ProductDetail.findOne({ IDProduct: req.params.id });
        console.log(req.query);
        if (req.method == "POST") {
            //console.log(req.query);
            let { NameProduct, Price, Size, Date, IDCategory, Image, Amount } = req.body;
            // if (NameProduct == '' || Price == '' || IDCategory == "" || Image == "") {
            //     smg = "Không được để trống"
            //     return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            // }


            if (!NameProduct || NameProduct.trim() === '') {
                smg= "Tên sản phẩm không được để trống"
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }
        
            if (!Price || Price.trim() === '') {
                smg= "Giá sản phẩm không được để trống" 
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }
        
            if (!Size || Size.trim() === '') {
                smg = "Kích thước sản phẩm không được để trống"
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }

            if (Date == '' || Amount == "") {
                smg = "ngày và số lượng không được bỏ trống "
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }
            console.log(Price);
            if (isNaN(Price)) {
                smg = "Giá phải là số"
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }
            if (isNaN(Size)) {
                smg = "Kích thước phải là số"
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }

            if (isNaN(Amount)) {
                smg = "Số lượng phải là số"
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
            }

            // Validate the date using regex
            const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
            if (!dateRegex.test(Date)) {
                smg = "Ngày không hợp lệ, định dạng phải là YYYY-MM-DD";
                return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
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
                        return res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
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

            return res.redirect('/apiAdmin/product');
        }
        else {
            res.render('product/update-product', { smg: smg, obj: obj, objDT: objDT });
        }


        if (obj == null) {
            smg = "Sản phẩm không tồn tại"
            //return res.status(400).json({  smg: smg });
        }
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
        return res.redirect('/apiAdmin/product');
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(500).json({ message: smg });
    }
};


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

exports.getCategory = async (req, res, next) => {
    console.log("lay du lieu thanh cong")
    try {
        let list = await Category.Category.find()
        res.render('category/list-category', { listCTG: list });
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}

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
            console.log(req.body)
            let { NameCategory } = req.body;
            NameCategory = NameCategory.trim();
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
            return res.redirect('/apiAdmin/category');
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }
    res.render('category/add-category', { smg: smg });
};
exports.SuaLoai = async (req, res, next) => {
    let smg = ''
    let obj = null;
    try {
        obj = await Category.Category.findOne({ _id: req.params.id });
        smg = 'Lấy dữ liệu thành công'
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.render('category/update-category', { smg: smg, obj: obj });
        }
        if (req.method == "POST") {
            let { NameCategory } = req.body;
            NameCategory = NameCategory.trim();
            if (NameCategory == '') {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            let objCate = {};
            objCate.NameCategory = NameCategory;
            await Category.Category.findByIdAndUpdate(req.params.id, objCate);
            smg = 'Sửa thành công'
            return res.redirect('/apiAdmin/category');
        }
        // return res.status(200).json({ smg: smg, obj: obj });


    } catch (error) {
        smg = error.message;

    }
    return res.render('category/update-category', { smg: smg, obj: obj });
};
exports.Xoaloai = async (req, res, next) => {
    let smg = '';
    try {
        obj = await Category.Category.findOne({ _id: req.params.id });
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.status(400).json({ smg: smg });
        }

        await Category.Category.findByIdAndDelete(req.params.id);
        smg = 'Xóa thành công'
        return res.redirect('/apiAdmin/category')
    } catch (error) {
        smg = "Lỗi: " + error.message;
    }
};
exports.getHoaDonDangXuLy = async (req, res, next) => {
    console.log("lay du lieu thanh cong")
    try {
        let list = await BillDetail.find({  Status: 0 }).sort({ Date: 1 }).select('ID IDUser IDBill Status Date Total Amount');
        // Lấy danh sách các ID của người dùng từ các hóa đơn
        let userIds = list.map(bill => bill.IDUser);

        // Tìm tất cả các người dùng tương ứng với các ID đã lấy được
        let users = await Account.find({ _id: { $in: userIds } }).select('FullName');

        // Mapping dữ liệu vào listBill
        list = list.map(bill => {
            let user = users.find(user => user._id.toString() === bill.IDUser.toString());
            return {
                _id: bill._id,
                FullName: user ? user.FullName : 'Không có dữ liệu',
                IDBill: bill.IDBill,
                Status: bill.Status,
                Date: bill.Date,
                Total: bill.Total,
                Amount: bill.Amount
            };
        });

        res.render('billdetail/bill-dangcho', { listBillCho: list });
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}

exports.getHoaDonDuyet = async (req, res, next) => {
    console.log("lay du lieu thanh cong")
    try {
        let list = await BillDetail.find({  Status: 1 }).sort({ Date: 1 }).select('ID IDUser IDBill Status Date Total Amount');
        // Lấy danh sách các ID của người dùng từ các hóa đơn
        let userIds = list.map(bill => bill.IDUser);

        // Tìm tất cả các người dùng tương ứng với các ID đã lấy được
        let users = await Account.find({ _id: { $in: userIds } }).select('FullName');

        // Mapping dữ liệu vào listBill
        list = list.map(bill => {
            let user = users.find(user => user._id.toString() === bill.IDUser.toString());
            return {
                _id: bill._id,
                FullName: user ? user.FullName : 'Không có dữ liệu',
                IDBill: bill.IDBill,
                Status: bill.Status,
                Date: bill.Date,
                Total: bill.Total,
                Amount: bill.Amount
            };
        });

        res.render('billdetail/bill-daduyet', { listBillDuyet: list });
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.getHoaDonTuChoi = async (req, res, next) => {
    console.log("lay du lieu thanh cong")
    try {
        let list = await BillDetail.find({  Status: 2 }).sort({ Date: 1 }).select('ID IDUser IDBill Status Date Total Amount');
        // Lấy danh sách các ID của người dùng từ các hóa đơn
        let userIds = list.map(bill => bill.IDUser);

        // Tìm tất cả các người dùng tương ứng với các ID đã lấy được
        let users = await Account.find({ _id: { $in: userIds } }).select('FullName');

        // Mapping dữ liệu vào listBill
        list = list.map(bill => {
            let user = users.find(user => user._id.toString() === bill.IDUser.toString());
            return {
                _id: bill._id,
                FullName: user ? user.FullName : 'Không có dữ liệu',
                IDBill: bill.IDBill,
                Status: bill.Status,
                Date: bill.Date,
                Total: bill.Total,
                Amount: bill.Amount
            };
        });

        res.render('billdetail/bill-tuchoi', { listBillTuChoi: list });
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.getAllHoaDon = async (req, res, next) => {
    console.log("Lấy dữ liệu thành công");
    try {
        let list = await BillDetail.find().select('ID IDUser IDBill Status Date Total Amount');

        // Lấy danh sách các ID của người dùng từ các hóa đơn
        let userIds = list.map(bill => bill.IDUser);

        // Tìm tất cả các người dùng tương ứng với các ID đã lấy được
        let users = await Account.find({ _id: { $in: userIds } }).select('FullName');

        // Mapping dữ liệu vào listBill
        list = list.map(bill => {
            let user = users.find(user => user._id.toString() === bill.IDUser.toString());
            return {
                _id: bill._id,
                FullName: user ? user.FullName : 'Không có dữ liệu',
                IDBill: bill.IDBill,
                Status: bill.Status,
                Date: bill.Date,
                Total: bill.Total,
                Amount: bill.Amount
            };
        });

        res.render('billdetail/list-billdetail', { listBill: list });
    } catch (error) {
        console.log(error);
        return res.status(400).send(error);
    }
};




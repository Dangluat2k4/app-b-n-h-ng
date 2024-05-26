
const Bill = require("../model/Bill")
const BillDetail = require("../model/BillDetail")
const Cart = require("../model/Cart")
const Category = require("../model/Category")
const Product = require("../model/Product")
const ProductDetail = require("../model/ProductDetail")
const fs = require('fs');// thư viện sử lý file
const { Account } = require("../model/Account");
const bcrypt = require("bcrypt");

//get Acount
exports.Login = async (req, res, next) => {
    try {
        const user = await Account
            .findByCredentials(req.body.Email, req.body.Password)
        if (!user) {
            return res.status(401)
                .json({ error: 'Sai thông tin đăng nhập' })
        }
        // đăng nhập thành công, tạo token làm việc mới
        const token = await user.generateAuthToken()
        user.token = token;
        return res.status(200).send(user)
    } catch (error) {
        console.log(error + " Pass" + req.body.Password)
        return res.status(400).send(error)
    }
}
exports.Reg = exports.doReg = async (req, res, next) => {
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
    res.status(200).json({ status: 1, msg: 'Trang đăng ký' });
}
// Product
exports.XemDanhSachSanPham = async (req, res, next) => {
    try {
        let list = await Product.Product.find().sort({ Name: 1 })
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemDanhSachSanPhamTheoLoai = async (req, res, next) => {
    try {
        let list = await Product.Product.find({ IDCategory: req.params.id }).sort({ Name: 1 })
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
exports.XemDanhSachLoai = async (req, res, next) => {
    try {
        let obj = await Category.Category.find()
        res.status(200).json(obj);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemDanhSachSPCT = async (req, res, next) => {
    try {
        let obj = await ProductDetail.ProductDetail.find()
        res.status(200).json(obj);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemSanPhamCT = async (req, res, next) => {
    let obj = null;
    let smg = '';
    try {

        obj = await ProductDetail.ProductDetail.findOne({ IDProduct: req.params.id });
        smg = "Lấy dữ liệu thành công"
        if (obj == null) {
            smg = "Sản phẩm không tồn tại"
            return res.status(400).json({ smg: smg });
        }
    } catch (error) {
        smg = error.message;
    }
    res.status(200).json(obj);
}
exports.DanhSachCart = async (req, res, next) => {
    try {
        let list = await Cart.Cart.find({ IDUser: req.params.id });
        return res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemCart = async (req, res, next) => {
    let obj = null;
    let smg = '';
    try {
        obj = await Cart.Cart.findOne({ IDProduct: req.params.idSp, UserID: req.params.id });
        smg = "Lấy dữ liệu thành công"
    } catch (error) {
        smg = error.message;
        return req.status(400).json({ smg: smg })
    }
    return res.status(200).json(obj);
};
exports.ThemCart = async (req, res, next) => {
    let smg = ''
    try {
        if (req.method == "POST") {
            let { IDUser, IDProduct, Amount, Size } = req.body;
            if (IDUser == '' || IDProduct == '' || Amount == '' || Size == '') {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            if (isNaN(Amount)) {
                smg = "Số lượng phải là số"
                return res.status(400).json({ smg: smg })
            }
            // đưa đối tượng vào cơ sở dữ liệu
            let obj = new Cart.Cart;
            obj.IDProduct = IDProduct;
            obj.IDUser = IDUser;
            obj.Amount = Number(Amount);
            obj.Size = Size
            let objOld = await Cart.Cart.findOne({ IDProduct: IDProduct, IDUser: IDUser, Size: Size })
            if (objOld != null) {
                console.log()
                let i = Number(Amount)
                i += Number(objOld.Amount)
                let objNew = {};
                objNew.IDUser = IDUser;
                objNew.IDProduct = IDProduct;
                objNew.Amount = Number(i);
                objNew.Size = Size
                await Cart.Cart.updateOne({ IDProduct: IDProduct, IDUser: IDUser, Size: Size },objNew);
                smg = 'Sản phẩm đã được cập nhật số lượng'
                return res.status(200).json(smg)
            }
            await obj.save();
            smg = 'Thêm thành công'
            return res.status(200).json(smg)
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }
    return res.status(400).json({ smg: smg })
};
exports.SuaCart = async (req, res, next) => {
    let smg = ''
    let obj = null;
    try {
        obj = await Cart.Cart.findOne({ _id: req.params.id });
    
        smg = 'Lấy dữ liệu thành công'
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.status(400).json({ smg: smg });
        }
        if (req.method == "PUT") {
            let { IDUser, IDProduct, Amount, Size } = req.body;
            if (IDUser == '' || IDProduct == '' || Amount == '' || Size == '') {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            if (isNaN(Amount)) {
                smg = "Số lượng phải là số"
                return res.status(400).json({ smg: smg })
            }
            let obj = {};
            obj.IDUser = IDUser;
            obj.IDProduct = IDProduct;
            obj.Amount = Amount;
            obj.Size = Size
            await Cart.Cart.findByIdAndUpdate(req.params.id, obj);
            smg = 'Sửa thành công'
        }
        return res.status(200).json(smg);


    } catch (error) {
        smg = error.message;

    }
    return res.status(400).json({ smg: smg });
};
exports.XoaCart = async (req, res, next) => {
    let smg = '';
    try {
        obj = await Cart.Cart.findOne({ _id: req.params.id });
        if (obj == null) {
            smg = "Loại không tồn tại"
            return res.status(400).json({ smg: smg });
        }
        smg = 'Lấy dữ liệu thành công'
        if (req.method == 'DELETE') {
            await Cart.Cart.findByIdAndDelete(req.params.id);
            smg = 'Xóa thành công'
        }
        return res.status(200).json(smg);
    } catch (error) {
        smg = "Lỗi: " + error.message;
    }
    res.status(400).json({ smg: smg });
};
exports.DanhSachBill = async (req, res, next) => {
    try {
        let list = await Bill.Bill.find({ UserID: req.params.id }).sort({ Name: 1 })
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.XemHoaDon = async (req, res, next) => {
    let obj = null;
    let smg = '';
    try {
        obj = await Bill.Bill.findOne({ ProductID: req.params.idSP, UserID: req.params.idUser });
        smg = "Lấy dữ liệu thành công"
    } catch (error) {
        smg = error.message;
    }
    res.status(200).json(obj);
};
exports.ThemHoaDon = async (req, res, next) => {
    let smg = ''
    try {
        if (req.method == "POST") {
            let { IDUser, IDSeller, IDProduct, Status, Date, Amount } = req.body;
            if (IDUser == '' || IDSeller == '' || IDProduct == ''
                || Status == '' || Date == '' || Amount == ''
            ) {
                smg = "Không được để trống"
                return res.status(400).json({ smg: smg })
            }
            if (isNaN(Status) || isNaN(Amount)) {
                smg = "Status và Amount phải là số"
                return res.status(400).json({ smg: smg })
            }
            // đưa đối tượng vào cơ sở dữ liệu
            let objBill = new Bill.Bill;
            let objBillDetail = new BillDetail.BillDetail;
            objBill.IDUser = IDUser;
            objBill.IDSeller = IDSeller;
            objBill.IDProduct = IDProduct;
            objBillDetail.IDUser = IDUser
            objBillDetail.IDProduct = IDProduct
            objBillDetail.Status = Number(Status)
            objBillDetail.Date = Date
            objBillDetail.Amount = Number(Amount)
            await objBill.save();
            await objBillDetail.save();
            smg = 'Thêm thành công'
            return res.status(200).json(objBill)
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }
    res.status(400).json({ smg: smg })
};

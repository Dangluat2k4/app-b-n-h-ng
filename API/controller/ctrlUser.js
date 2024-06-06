
const Bill = require("../model/Bill")
const BillDetail = require("../model/BillDetail")
const Cart = require("../model/Cart")
const Category = require("../model/Category")
const Product = require("../model/Product")
const ProductDetail = require("../model/ProductDetail")
const Recharge = require("../model/Recharge")
const fs = require('fs');// thư viện sử lý file
const { Account } = require("../model/Account");
const bcrypt = require("bcrypt");
const { Console } = require("console")
const { TIMEOUT } = require("dns")

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
        console.log(error + " Pass " + req.body.Password)
        return res.status(400).send({error:error,pass: "Sai pass"})
    }
}
exports.Reg = exports.doReg = async (req, res, next) => {
    try {
        const salt = await bcrypt.genSalt(10);
        const user = new Account(req.body);
        tempUser = await Account.findOne({Email:user.Email})
        if(tempUser!=null)
            return res.status(400).send( "Đã tồn tại tài khoản " )
        user.Password = await bcrypt.hash(req.body.Password, salt);
        user.FullName = req.body.FullName;
        user.NumberPhone= "8798798"
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

exports.changePassword = async (req, res, next) => {
    try {
        const { email, newPassword } = req.body;

        // Kiểm tra xem email có tồn tại trong database hay không
        const user = await Account.findOne({ Email: email });

        if (!user) {
            return res.status(404).json({ error: 'Email không tồn tại' });
        }

        // Mã hóa mật khẩu mới
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(newPassword, salt);

        // Cập nhật mật khẩu mới vào database
        user.Password = hashedPassword;
        await user.save();

        return res.status(200).json({ message: 'Đổi mật khẩu thành công' });
    } catch (error) {
        console.log(error);
        return res.status(500).json({ error: 'Đã xảy ra lỗi trong quá trình đổi mật khẩu' });
    }
};
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
exports.XemLoai = async (req, res, next) => {
    try {
        let obj = await Category.Category.findById(req.params.id)
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
            return res.status(400).json( smg);
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
    res.status(400).json(smg);
};
exports.DanhSachBill = async (req, res, next) => {
    try {
        let list = await Bill.Bill.find({ IDUser: req.params.id }).sort({ Date: -1 })
        console.log(list)
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.GetBill = async (req,res,next)=>{
    try {
        let obj = await Bill.Bill.findById(req.params.id )
        console.log("mádfksjd")
        res.status(200).json(obj);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    } 
}
exports.DanhSachBillDetail = async (req,res,next)=>{
    try {
        let list = await BillDetail.BillDetail.find({ IDUser: req.params.id }).sort({ Date: 1 })
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.DanhSachBillDetailToMonth = async (req,res,next)=>{
    try {
        let list = await BillDetail.BillDetail.find({ IDUser: req.params.id,
            Date:{$gte:req.params.firt,$lt:req.params.end},
            Status:1 })
       return res.status(200).json(list);
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
            let { IDUser, IDSeller, IDProduct, Status, Date,IDCart } = req.body;
           
            if (IDUser == '' || IDProduct.length==0 || Date == '' 
            ) {
                smg = "Không được để trống"
                
                return res.status(400).json(smg)
            }
            console.log(req.body);
            if (isNaN(Status)) {
                smg = "Status và Total phải là số"
                return res.status(400).json(smg)
            }
            console.log(IDProduct)
            // đưa đối tượng vào cơ sở dữ liệu
            let objBill = new Bill.Bill;
            let objBillDetail = new BillDetail.BillDetail;
            objBill.IDUser = IDUser;
            if(IDSeller!='')objBill.IDSeller = IDSeller;
            objBill.IDProduct = IDProduct;
            objBillDetail.IDUser = IDUser
            objBillDetail.IDBill = objBill.id
            objBillDetail.Status = Number(Status)
            objBillDetail.Date = Date
            let tempAmount = 0;
            let tempPrice = 0;
            for (let [key, {Amount, IDProduct,Price}] of Object.entries(objBill.IDProduct)) {
                tempAmount += Amount; 
                tempPrice+=(Price*Amount) // Thay đổi 'item.Amount' thành 'Amount'
            }
            objBillDetail.Total = Number(tempPrice)
            objBillDetail.Amount = Number(tempAmount)
            await objBill.save();
            await objBillDetail.save();
            smg = 'Thêm thành công'
            for (let id of IDCart) {
               await Cart.Cart.findByIdAndDelete(id);
            }
            
            return res.status(200).json(smg)
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }
    res.status(400).json(smg)
};
exports.UpdateAccount = async (req, res, next) => {
    try {
        let { Email, FullName, NumberPhone,Address,MyAddress } = req.body
        if (req.method == "PUT") {
            let obj = {}
            console.log(Address)
            if (FullName != '') obj.NumberPhone = NumberPhone
            if (NumberPhone != '') obj.FullName = FullName
            if(Address!=[]) obj.Address = Address
            if(MyAddress!='')obj.MyAddress = MyAddress
            if (Email != '') obj.Email = Email
            if (req.file && fs.existsSync(req.file.path)) {
                let file_path = './public/uploads/' + req.file.originalname;

                // Kiểm tra định dạng tập tin
                if (!req.file.mimetype.startsWith('image')) {
                    smg = 'Ảnh không đúng định dạng';
                    fs.unlinkSync(req.file.path); // Xóa tập tin tải lên tạm thời
                    console.log(smg)
                    return res.status(400).json( smg);
                }

                fs.renameSync(req.file.path, file_path);
                obj.Avatar = '/uploads/' + req.file.originalname;
            }
            await Account.findByIdAndUpdate(req.params.id,obj)
           return res.status(200).json("Sửa thành công")
        }
    } catch (error) {
        return res.status(400).json("Lỗi " + error + " xảy ra")
    }
}
exports.XemDanhSachAccount = async (req, res, next) => {
    try {
        let list = await Account.find().sort({ FullName: 1  })
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}
exports.DanhSachBillChuaGiao = async (req, res, next) => {
    try {
        let list = await BillDetail.BillDetail.find({  Status: 0 }).sort({ Date: 1 });
        res.status(200).json(list);
    } catch (error) {
        console.log(error);
        return res.status(400).send(error);
    }
};
exports.Xoahoadon = async (req, res, next) => {
    let smg = '';
    try {
        // Tìm kiếm và kiểm tra hóa đơn có tồn tại không
        let {IDSeller} = req.body
        const billDetail = await BillDetail.BillDetail.findOne({IDBill:req.params.id});
        const bill = await Bill.Bill.findById(req.params.id);
        if (!bill||!billDetail) {
            smg = "Hóa đơn không tồn tại";
            return res.status(400).json(smg);
        }

        // Cập nhật thuộc tính Status từ 0 thành 2
        await BillDetail.BillDetail.findOneAndUpdate({IDBill:req.params.id}, { Status: 2 });
        await Bill.Bill.findByIdAndUpdate(req.params.id, {IDSeller:IDSeller});
        
        smg = 'Cập nhật trạng thái hóa đơn thành công';
        return res.status(200).json(smg);
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(400).json(smg);
    }
};
exports.chapnhanhoadon = async (req, res, next) => {
    let smg = '';
    try {
        // Tìm kiếm và kiểm tra hóa đơn có tồn tại khôngg
          let {IDSeller} = req.body
          const billDetail = await BillDetail.BillDetail.findOne({IDBill:req.params.id});
          const bill = await Bill.Bill.findById(req.params.id);
          if (!bill||!billDetail) {
              smg = "Hóa đơn không tồn tại";
              return res.status(400).json(smg);
          }
  
          // Cập nhật thuộc tính Status từ 0 thành 2
          await BillDetail.BillDetail.findOneAndUpdate({IDBill:req.params.id}, { Status: 1 });
          await Bill.Bill.findByIdAndUpdate(req.params.id, {IDSeller:IDSeller});
          
          smg = 'Cập nhật trạng thái hóa đơn thành công';
          return res.status(200).json(smg);
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(400).json(smg);
    }
};

exports.YeuCauNapTien = async (req,res,next)=>{
    try {
        let {IDUser,Email,Money,Time} = req.body
       
        if(req.method =="POST"){
            if(IDUser==''||Email==''||Time==''){
                return res.status(400).json("Lỗi "+"không được để trống")
            }
            if(isNaN(Money)){
               
                return res.status(400).json("Lỗi "+"Tiền phải là số")
            }
            let obj = new Recharge.Recharge
            if (req.file && fs.existsSync(req.file.path)) {
                let file_path = './public/uploads/' + req.file.originalname;

                // Kiểm tra định dạng tập tin
                if (!req.file.mimetype.startsWith('image')) {
                    fs.unlinkSync(req.file.path); // Xóa tập tin tải lên tạm thời
                    
                    return res.status(400).json( 'Ảnh không đúng định dạng');
                }
              
                fs.renameSync(req.file.path, file_path);
                obj.Image = '/uploads/' + req.file.originalname;
            }else{
              
                return res.status(400).json("Lỗi "+"Ảnh sai định dạng")
            }
            obj.IDUser = IDUser
            obj.Email=Email
            obj.Money = Number(Money)
            obj.Time = Time
            obj.Status = 0
            await obj.save()
            return res.status(200).json("Xin hãy chờ vài phút để hệ thống kiểm tra")
        }
    } catch (error) {
       
        return res.status(400).json("Lỗi "+error)
    }
}
exports.DanhSachNap = async (req,res,next)=>{
    try {
        let list = await Recharge.Recharge.find({IDUser:req.params.id}).sort({ FullName: 1  })
        res.status(200).json(list);
    } catch (error) {
        console.log(error)
        return res.status(400).send(error)
    }
}

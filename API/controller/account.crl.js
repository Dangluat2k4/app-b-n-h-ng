const { Account } = require("../model/Account");

const ac = require("../model/Account");

exports.ListAccout = async (req, res, next) => {
    try {
        let list = await Account.find({ Status: { $ne: 0 } });
        res.render('account/list-account', { listAC: list });
    } catch (error) {
        console.log(error);
        return res.status(400).send(error);
    }
}

exports.ListAccoutNV = async (req, res, next) => {
    try {
        let list = await Account.find({ Level: { $ne: 1 } });
        res.render('account/list-nhanVien', { listNV: list });
    } catch (error) {
        console.log(error);
        return res.status(400).send(error);
    }
}

exports.ListAccoutKH = async (req, res, next) => {
    try {
        let list = await Account.find({ Level: { $ne: 0 } });
        res.render('account/list-khachHang', { listKH: list });
    } catch (error) {
        console.log(error);
        return res.status(400).send(error);
    }
}

const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
};

const validatePassword = (password) => {
    return password.length >= 6;
};

const validatePhoneNumber = (phoneNumber) => {
    return /^\d{10,15}$/.test(phoneNumber);
};


exports.addAccount = async (req, res, next) => {
    let smg = '';
    try {
        if (req.method === "POST") {
            console.log(req.body); // Kiểm tra dữ liệu req.body
            let { Email, Password, FullName, NumberPhone, Level } = req.body;

            if (!Email || !Password || !FullName || !Level) {
                smg = "Không được để trống";
                return res.status(400).json({ smg: smg });
            }
            if (!validateEmail(Email)) {
                smg = "Email không hợp lệ";
                return res.status(400).json({ smg: smg });
            }
        
            if (!validatePassword(Password)) {
                smg = "Password phải có ít nhất 6 ký tự";
                return res.status(400).json({ smg: smg });
            }
        
            if (!validatePhoneNumber(NumberPhone)) {
                smg = "Số điện thoại không hợp lệ";
                return res.status(400).json({ smg: smg });
            }

            let objAC = new Account;

            // Đưa đối tượng vào cơ sở dữ liệu
            objAC.Email = Email;
            objAC.Password = Password;
            objAC.FullName = FullName;
            objAC.NumberPhone = NumberPhone;
            objAC.Level = Number(Level);


            objAC.Status = 1;
            smg = 'Thêm thành công, id mới = ' + objAC._id;
            await objAC.save();
            return res.redirect('/account/account');
        }
    } catch (error) {
        console.log(error.message);
        smg = error.message;
    }

    res.render('account/add-account', { smg: smg });
};

exports.updateAC = async (req, res, next) => {
    let smg = ''
    let obj = null;
    try {
        obj = await Account.findOne({ _id: req.params.id });
        smg = 'Lấy dữ liệu thành công'
        if (obj == null) {
            smg = "Bạn cần nhập vào đầy đủ thông tin"
            return res.render('account/update-account', { smg: smg, obj: obj });
        }
        
        if (req.method == "POST") {
            let { Email, Password, FullName, NumberPhone, Level } = req.body;

            if (!Email || !Password || !FullName || !Level) {
                smg = "Không được để trống";
                return res.status(400).json({ smg: smg });
            }
            let objAC = {};
            objAC.Email = Email;
            objAC.Password = Password;
            objAC.FullName = FullName;
            objAC.NumberPhone = NumberPhone;
            objAC.Level = Level;


            await Account.findByIdAndUpdate(req.params.id, objAC);
            smg = 'Sửa thành công'
            return res.redirect('/account/account');
        }


    } catch (error) {
        smg = error.message;
    }
    return res.render('account/update-account', { smg: smg, obj: obj });
};

exports.deleteAccount = async (req, res, next) => {
    try {
        await Account.findByIdAndUpdate(req.params.id, { Status: 0 });
        return res.redirect('/account/account');
        res.status(200).send();
    } catch (error) {
        console.error(error);
        res.status(500).send();
    }
};



